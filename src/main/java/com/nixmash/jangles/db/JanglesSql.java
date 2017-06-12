package com.nixmash.jangles.db;

import com.nixmash.jangles.dto.JanglesConnection;
import com.nixmash.jangles.dto.JanglesUser;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.enums.JanglesProfile;

import java.sql.*;
import java.util.List;

public abstract class JanglesSql {

    // region Constants and Properties

    private static final String MYSQL_PROVIDER = "com.nixmash.jangles.db.JanglesSqlDB";
    private static final String MYSQL_CLASSNAME = "com.mysql.jdbc.Driver";

    private static JanglesSql provider;
    private static JanglesConnection janglesConnection;
    public String dbUser;

    private Connection connection;
    private Statement statement;
    private CallableStatement callablestatement;

    // endregion

    // region LoadProvider by JanglesProfile

    public static JanglesSql loadProvider(JanglesProfile janglesProfile) {
        janglesConnection = getProfileConnection(janglesProfile);
        try {
            provider = (JanglesSql) Class.forName(MYSQL_PROVIDER).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return provider;
    }

    private static JanglesConnection getProfileConnection(JanglesProfile janglesProfile) {
        JanglesConnection janglesConnection = JanglesConnections.getMySqlConnection();
        switch (janglesProfile) {
            case TESTING:
                janglesConnection = JanglesConnections.getTestConnection();
                break;
            case MYSQL:
                break;
        }
        return janglesConnection;
    }

    // endregion

    // region MySQL Connection and Query Processes

    private Connection sqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName(MYSQL_CLASSNAME);
        return DriverManager.getConnection(janglesConnection.getUrl(),
                janglesConnection.username, janglesConnection.password);
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

    // region Abstract Methods

    public abstract List<JanglesUser> getJanglesUsers() throws SQLException;
    public abstract Long createJanglesUser(JanglesUser janglesUser) throws SQLException;

    // endregion

    // region Populate List Objects from ResultSets

    public static void populateJanglesUser(ResultSet rs, JanglesUser janglesUser) throws SQLException {
        janglesUser.setUserId(rs.getInt("user_id"));
        janglesUser.setUserName(rs.getString("username"));
        janglesUser.setPassword(rs.getString("password"));
        janglesUser.setDisplayName(rs.getString("display_name"));
        janglesUser.setDateCreated(rs.getTimestamp("date_created"));
        janglesUser.setIsActive(rs.getBoolean("is_active"));
    }

    // endregion

}
