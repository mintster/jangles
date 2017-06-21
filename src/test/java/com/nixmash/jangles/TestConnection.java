package com.nixmash.jangles;

import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.model.JanglesConnection;

/**
 * Created by daveburke on 6/17/17.
 */
public class TestConnection implements IConnection {

    @Override
    public JanglesConnection get() {
        return JanglesConnections.getTestConnection();
    }
}
