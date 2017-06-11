package com.nixmash.jangles;

import com.nixmash.jangles.containers.JanglesConnection;
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
        Assert.assertEquals(janglesConnection.environment, "mysql");
    }

    @Test
    public void getH2Connection() {
        JanglesConnection janglesConnection = JanglesConnections.getH2Connection();
        Assert.assertEquals(janglesConnection.environment, "h2");
    }

    // endregion

}
