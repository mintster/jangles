package com.nixmash.jangles.db;

import com.nixmash.jangles.dto.JanglesUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class JanglesSqlDB extends JanglesSql {

    private static final Logger logger = LoggerFactory.getLogger(JanglesSqlDB.class);

    // region janglesUsers

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

}
