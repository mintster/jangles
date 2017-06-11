package com.nixmash.jangles;

import com.nixmash.jangles.business.JanglesUsers;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.enums.JanglesProfile;
import org.assertj.core.api.Assertions;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by daveburke on 6/11/17.
 */
@RunWith(JUnit4.class)
public class DatabaseTests   {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    private JanglesUsers janglesUsers = new JanglesUsers(JanglesProfile.H2);
    private String key = userListCacheKey();
    private  List<JanglesUser> users;
    private boolean isSetup = false;

    private String userListCacheKey() {
        return String.format("JanglesUserList-%s", JanglesConfiguration.get().configFileId);
    }

    @Before
    public void setup(){
        try {
            setH2State("/create.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            setH2State("/drop.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setH2State(String sql) throws FileNotFoundException, SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
        Statement st = conn.createStatement();
        File script = new File(getClass().getResource(sql).getFile());
        RunScript.execute(conn, new FileReader(script));
    }

    @Test
    public void getJanglesUsers() {

        for (JanglesUser janglesUser : janglesUsers.getJanglesUsers()) {
            logger.info("jangles user: " + janglesUser);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cacheDbRetrievalTest() {

        users = janglesUsers.getJanglesUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertEquals(users, cachedUsers);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void clearCacheTest() {

        users = janglesUsers.getJanglesUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);

        JanglesCache cache = JanglesCache.getInstance();
        cache.remove(key);

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertNull(cachedUsers);
    }

}