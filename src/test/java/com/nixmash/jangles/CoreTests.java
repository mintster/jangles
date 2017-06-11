package com.nixmash.jangles;

import com.nixmash.jangles.business.JanglesUsers;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by daveburke on 6/6/17.
 */
@RunWith(JUnit4.class)
public class CoreTests {

    private static final Logger logger = LoggerFactory.getLogger(CoreTests.class);
    private JanglesUsers janglesUsers = new JanglesUsers();
    private String key = InputListCacheKey();
    private  List<JanglesUser> users;

    private String InputListCacheKey() {
        return String.format("JanglesMysqlUserList-%s", JanglesConfiguration.get().mysqlDbConnectionName);
    }

    @Test
    public void loggingTest() {
        assertNotNull(logger.isInfoEnabled());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void cacheDbRetrievalTest() {

        users = janglesUsers.getMysqlUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertEquals(users, cachedUsers);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void clearCacheTest() {

        users = janglesUsers.getMysqlUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);

        JanglesCache cache = JanglesCache.getInstance();
        cache.remove(key);

        List<JanglesUser> cachedUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        assertNull(cachedUsers);
    }

    @Test
    public void configurationTest() {
        String globalPropertiesFile = JanglesConfiguration.get().globalPropertiesFile;
        Assertions.assertThat(globalPropertiesFile.endsWith("global.properties"));
    }
}
