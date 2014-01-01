package com.nixmash.jangles.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.*;

/**
 * Created with IntelliJ IDEA.
 * JangleUser: daveburke
 * Date: 10/14/13
 * Time: 12:03 PM
 */
public class JangleMySqlDB extends JangleDB {


    // region Properties
    private Connection conn;
    private Statement stmt;
    // endregion

    // region MySQL Connection and Query Processes
    protected Connection MySqlConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        JangleConfiguration config = JangleConfiguration.Get();
        return DriverManager.getConnection(config.MySqlUrl, config.MySqlUser, config.MySqlPassword);
    }

    private ResultSet MySqlQuery(String query) {
        ResultSet rs = null;
        try {

            this.conn = MySqlConnection();
            this.stmt = this.conn.createStatement();
            rs = this.stmt.executeQuery(query);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void MySqlClose() throws SQLException {
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

            ResultSet rs = MySqlQuery("SELECT * FROM jangles_users ORDER BY userid");

            JangleUser _JangleUser = null;
            while (rs.next()) {
                _JangleUser = new JangleUser();
                PopulateJangleUserList(rs, _JangleUser);
                JangleUserList.add(_JangleUser);
            }
            MySqlClose();

            return JangleUserList;
        }
    }
    // endregion

    // endregion
}
