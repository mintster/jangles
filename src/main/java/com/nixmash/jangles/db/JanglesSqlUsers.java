package com.nixmash.jangles.db;

import com.nixmash.jangles.db.base.JanglesSql;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.model.JanglesUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JanglesSqlUsers extends JanglesSql {

    public JanglesSqlUsers(IConnection iConnection) {
        super(iConnection);
    }

    // region janglesUsers

    @Override
    public JanglesUser getJanglesUser(Long userId) throws SQLException {
        {
            CallableStatement cs = sqlCall("SELECT * FROM jangles_users WHERE user_id  = ?");
            cs.setLong(1, userId);
            ResultSet rs = cs.executeQuery();

            JanglesUser janglesUser = null;
            while (rs.next()) {
                janglesUser = new JanglesUser();
                populateJanglesUser(rs, janglesUser);
            }
            sqlCallClose();
            return janglesUser;
        }
    }

    @Override
    public List<JanglesUser> getJanglesUsers() throws SQLException {
        {
            List<JanglesUser> janglesUserList = new ArrayList<JanglesUser>();
            ResultSet rs = sqlQuery("SELECT * FROM jangles_users ORDER BY user_id");
            JanglesUser janglesUser = null;
            while (rs.next()) {
                janglesUser = new JanglesUser();
                populateJanglesUser(rs, janglesUser);
                janglesUserList.add(janglesUser);
            }
            sqlClose();
            return janglesUserList;
        }
    }

    @Override
    public Long createJanglesUser(JanglesUser janglesUser) {
        Long userId = -1L;
        try (
                CallableStatement cs = sqlCall("{call p_janglesusers_insert(?, ?, ?, ?, ?)}");
        ) {

            cs.setString(1, janglesUser.getUserName());
            cs.setString(2, janglesUser.getPassword());
            cs.setString(3, janglesUser.getDisplayName());
            cs.setBoolean(4, janglesUser.getIsActive());
            cs.registerOutParameter(5, Types.BIGINT);

            cs.execute();
            userId = cs.getLong(5);
            cs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userId;
    }

    // endregion

    // region Populate List Objects from ResultSets

    public void populateJanglesUser(ResultSet rs, JanglesUser janglesUser) throws SQLException {
        janglesUser.setUserId(rs.getInt("user_id"));
        janglesUser.setUserName(rs.getString("username"));
        janglesUser.setPassword(rs.getString("password"));
        janglesUser.setDisplayName(rs.getString("display_name"));
        janglesUser.setDateCreated(rs.getTimestamp("date_created"));
        janglesUser.setIsActive(rs.getBoolean("is_active"));
    }

    // endregion

}
