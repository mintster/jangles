package com.nixmash.jangles.db;

import com.nixmash.jangles.dto.JanglesConnection;
import com.nixmash.jangles.dto.JanglesUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JanglesSql {

    // region Constants and Properties

    private Connection connection;
    private Statement statement;
    private CallableStatement callablestatement;
    private final IConnection iConnection;

    public JanglesSql(IConnection iConnection) {
        this.iConnection = iConnection;
    }

    // endregion


    // region MySQL Connection and Query Processes

    private Connection sqlConnection() throws ClassNotFoundException, SQLException {
        JanglesConnection janglesConnection = new JanglesConnection();
        JanglesConnection cn = iConnection.get();
        return DriverManager.getConnection(cn.getUrl(), cn.getUsername(), cn.getPassword());
    }

    protected ResultSet sqlQuery(String query) {
        ResultSet rs = null;
        try {

            this.connection = sqlConnection();
            this.statement = this.connection.createStatement();
            rs = this.statement.executeQuery(query);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    protected CallableStatement sqlCall(String statement) {
        try {
            this.connection = sqlConnection();
            this.callablestatement = this.connection.prepareCall(statement);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return callablestatement;
    }

    void sqlClose() throws SQLException {
        if (!this.connection.isClosed())
            this.connection.close();
        if (!this.statement.isClosed())
            this.statement.close();
    }

    private void sqlCallClose() throws SQLException {
        if (!this.connection.isClosed())
            this.connection.close();
        if (!this.callablestatement.isClosed())
            this.callablestatement.close();
    }

    // endregion

    // region janglesUsers

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
