package com.nixmash.jangles.db.connections;

import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.model.JanglesConnection;

/**
 * Created by daveburke on 6/17/17.
 */
public class MySqlConnection implements IConnection {

    @Override
    public JanglesConnection get() {
        return JanglesConnections.getMySqlConnection();
    }
}
