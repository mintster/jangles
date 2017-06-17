package com.nixmash.jangles.db;

import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.dto.JanglesConnection;

/**
 * Created by daveburke on 6/17/17.
 */
public class MySqlConnection implements IConnection {

    @Override
    public JanglesConnection get() {
        return JanglesConnections.getMySqlConnection();
    }
}
