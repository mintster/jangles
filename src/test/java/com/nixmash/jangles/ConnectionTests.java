package com.nixmash.jangles;

import com.nixmash.jangles.model.JanglesConnection;
import com.nixmash.jangles.core.JanglesConnections;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by daveburke on 5/29/17.
 */

@RunWith(JUnit4.class)
public class ConnectionTests {

    // region Connections

    @Test
    public void getMySqlConnection() {
        JanglesConnection janglesConnection = JanglesConnections.getMySqlConnection();
        Assert.assertEquals(janglesConnection.getEnvironment(), "mysql");
    }

    @Test
    public void getTestConnection() {
        JanglesConnection janglesConnection = JanglesConnections.getTestConnection();
        Assert.assertTrue(janglesConnection.getDatabase().contains("test"));
    }

    // endregion

}
