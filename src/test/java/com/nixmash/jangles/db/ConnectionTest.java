package com.nixmash.jangles.db;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.guice.GuiceJUnit4Runner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static com.nixmash.jangles.utils.JanglesUtils.configureTestDb;

/**
 * Created by daveburke on 6/11/17.
 */
@RunWith(GuiceJUnit4Runner.class)
public class ConnectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionTest.class);

    @Inject
    private JanglesConfiguration janglesConfiguration;

    // region @BeforeClass and @AfterClass

    @BeforeClass
    public static void setup(){
        try {
            configureTestDb("populate.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // endregion


    @Test
    public void testConnectionNames() {
        Assert.assertNotEquals(
                janglesConfiguration.testDbConnectionName,
                janglesConfiguration.mysqlDbConnectionName);
    }
}