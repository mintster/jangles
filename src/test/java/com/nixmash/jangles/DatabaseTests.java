package com.nixmash.jangles;

import com.nixmash.jangles.model.JanglesUser;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static com.nixmash.jangles.utils.JanglesUtils.configureTestDb;

/**
 * Created by daveburke on 6/11/17.
 */
@RunWith(JUnit4.class)
public class DatabaseTests   {

    // region Properties and Local Variables

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTests.class);

    private  List<JanglesUser> users;
    private boolean isSetup = false;

    // endregion

    // region @BeforeClass and @AfterClass

    @BeforeClass
    public static void setup(){
        try {
            configureTestDb("populate.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            configureTestDb("clear.sql");
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // endregion


    @Test
    public void tempTest() {
        Assert.assertTrue(1 == 1);
    }
}