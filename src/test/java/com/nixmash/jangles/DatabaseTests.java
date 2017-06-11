package com.nixmash.jangles;

import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

/**
 * Created by daveburke on 6/11/17.
 */
@RunWith(JUnit4.class)
public class DatabaseTests {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    @Before
    public void setup() throws FileNotFoundException, SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        Statement st = conn.createStatement();
        File script = new File(getClass().getResource("/data.sql").getFile());
        RunScript.execute(conn, new FileReader(script));
    }

    @Test
    public void getUsersTest() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("select user_name from jangles_users");
        while (rset.next()) {
            String name = rset.getString(1);
            System.out.println(name);
        }
    }
}