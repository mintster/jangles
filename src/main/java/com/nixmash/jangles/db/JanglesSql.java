package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesConnection;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.enums.JanglesProfile;

import java.sql.*;
import java.util.List;

public abstract class JanglesSql {

    final static String sqlProvider = "com.nixmash.jangles.db.JanglesSqlDB";
    private static JanglesSql provider;
    private static String driverClassName;
    private static JanglesConnection janglesConnection;
    public String dbUser;

    public static JanglesSql loadProvider(JanglesProfile janglesProfile) {

        janglesConnection = getProfileConnection(janglesProfile);
        driverClassName = getClassName(janglesProfile);

        try {
            provider = (JanglesSql) Class.forName(sqlProvider).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return provider;
    }

    private static String getClassName(JanglesProfile janglesProfile) {
        String className = "com.mysql.jdbc.Driver";
        switch (janglesProfile) {
            case H2:
                className = "org.h2.Driver";
                break;
            case MYSQL:
                break;
        }
        return className;
    }
    private static JanglesConnection getProfileConnection(JanglesProfile janglesProfile) {
        JanglesConnection janglesConnection = JanglesConnections.getMySqlConnection();
        switch (janglesProfile) {
            case H2:
                janglesConnection = JanglesConnections.getH2Connection();
                break;
            case MYSQL:
                break;
        }
        return janglesConnection;
    }

    // region Properties

    private Connection connection;
    private Statement statement;
    private CallableStatement callablestatement;

    // endregion

    // region MySQL Connection and Query Processes
    private Connection sqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
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


    private CallableStatement sqlCall(String statement) {
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

    // endregion

    // region Populate List Objects from ResultSets

    public static void populateJanglesUserList(ResultSet rs, JanglesUser janglesUser) throws SQLException {
        janglesUser.setUserId(rs.getInt("user_id"));
        janglesUser.setUserName(rs.getString("user_name"));
        janglesUser.setPassword(rs.getString("password"));
        janglesUser.setDisplayName(rs.getString("display_name"));
        janglesUser.setDateCreated(rs.getTimestamp("date_created"));
        janglesUser.setIsActive(rs.getBoolean("is_active"));
    }

    // endregion


}
