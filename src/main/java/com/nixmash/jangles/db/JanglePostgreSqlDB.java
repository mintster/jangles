package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JangleUser;
import com.nixmash.jangles.core.JangleConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daveburke
 * Date: 11/5/13
 * Time: 12:03 PM
 */

public class JanglePostgreSqlDB extends JangleDB {

    // region Properties
    private Connection conn;
    private Statement stmt;
    // endregion

    // region PostgreSQL Connection and Query Processes
    protected Connection PostgreSqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        JangleConfiguration config = JangleConfiguration.Get();
        return DriverManager.getConnection(config.PostgreSqlUrl, config.PostgreSqlUser, config.PostgreSqlPassword);
    }

    private ResultSet PostgreSqlQuery(String query) {
        ResultSet rs = null;
        try {

            this.conn = PostgreSqlConnection();
            this.stmt = this.conn.createStatement();
            rs = this.stmt.executeQuery(query);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void PostgreSqlClose() throws SQLException {
        if (!this.conn.isClosed())
            this.conn.close();
        if (!this.stmt.isClosed())
            this.stmt.close();
    }
    // endregion

    // region Override Methods

    // region JangleUsers
    @Override
    public List<JangleUser> GetJangleUsers() throws SQLException {
        {

            List<JangleUser> JangleUserList = new ArrayList<JangleUser>();

            ResultSet rs = PostgreSqlQuery("SELECT * FROM jangle_users ORDER BY userid");

            JangleUser _JangleUser = null;
            while (rs.next()) {
                _JangleUser = new JangleUser();
                PopulateJangleUserList(rs, _JangleUser);
                JangleUserList.add(_JangleUser);
            }
            PostgreSqlClose();

            return JangleUserList;
        }
    }

    // endregion

    // endregion
}
