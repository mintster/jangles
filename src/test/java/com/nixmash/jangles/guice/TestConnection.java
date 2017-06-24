package com.nixmash.jangles.guice;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.db.IConnection;
import com.nixmash.jangles.model.JanglesConnection;

/**
 * Created by daveburke on 6/17/17.
 */
public class TestConnection implements IConnection {

    JanglesConnections janglesConnections;

    @Inject
    public TestConnection(JanglesConnections janglesConnections) {
        this.janglesConnections = janglesConnections;
    }

    @Override
    public JanglesConnection get() {
        return janglesConnections.getTestConnection();
    }
}
