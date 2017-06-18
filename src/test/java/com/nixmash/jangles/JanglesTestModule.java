package com.nixmash.jangles;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nixmash.jangles.db.IConnection;

/**
 * Created by daveburke on 6/18/17.
 */
public class JanglesTestModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IConnection.class).to(TestConnection.class);
    }
}
