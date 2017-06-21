package com.nixmash.jangles;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nixmash.jangles.core.*;
import com.nixmash.jangles.model.JanglesConnection;
import com.nixmash.jangles.model.JanglesUser;
import com.nixmash.jangles.service.JanglesUserServiceImpl;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * Created by daveburke on 6/11/17.
 */
@RunWith(JUnit4.class)
public class DatabaseTests   {

    // region Properties and Local Variables

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    private String key = userListCacheKey();
    private  List<JanglesUser> users;
    private boolean isSetup = false;

    private String userListCacheKey() {
        return String.format("JanglesUserList-%s", JanglesConfiguration.get().configFileId);
    }

    private static JanglesUserServiceImpl janglesUserServiceImpl;

    // endregion

    // region @BeforeClass and @AfterClass

    @BeforeClass
    public static void setup(){
        try {
            Injector injector = Guice.createInjector(new JanglesTestModule());
            janglesUserServiceImpl = injector.getInstance(JanglesUserServiceImpl.class);
            configureTestDb("/populate.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            configureTestDb("/clear.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void configureTestDb(String sql) throws FileNotFoundException, SQLException {

        // @formatter:off

        JanglesConnection janglesConnection = JanglesConnections.getTestConnection();
        Connection conn = DriverManager.getConnection(
                janglesConnection.getUrl(),
                janglesConnection.getUsername(),
                janglesConnection.getPassword());
        Statement st = conn.createStatement();
        File script = new File(DatabaseTests.class.getResource(sql).getFile());
        ScriptRunner sr = new ScriptRunner(conn);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader(script));
        sr.runScript(reader);

        // @formatter:on
    }

    // endregion

    @Test
    public void connectionTest() {
        assertTrue(JanglesConfiguration.get().configFileId.contains("test"));
    }

    // region JanglesUserServiceImpl CRUD and Cache Tests

    @Test
    public void createJanglesUserTest() {
        int before = janglesUserServiceImpl.getJanglesUsers().size();
        JanglesUser newUser = TestUtils.createJanglesUser("usertest");
        JanglesUser janglesUser = janglesUserServiceImpl.createJanglesUser(newUser);
        assertThat(janglesUser.getUserId(), greaterThan(0));
        int after = janglesUserServiceImpl.getJanglesUsers().size();
        assertEquals(before + 1, after);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cacheDbRetrievalTest() {

        users = janglesUserServiceImpl.getJanglesUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);


/*        for (JanglesUser user : users) {
            System.out.println(user);
        }*/

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertEquals(users, cachedUsers);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void clearCacheTest() {

        users = janglesUserServiceImpl.getJanglesUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);

        JanglesCache cache = JanglesCache.getInstance();
        cache.remove(key);

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertNull(cachedUsers);
    }

    // endregion

}