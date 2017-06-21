package com.nixmash.jangles.core;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.service.base.JanglesApiService;

/**
 * Created by daveburke on 6/18/17.
 */
public class JanglesTestModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IConnection.class).to(TestConnection.class);
        binder.bind(JanglesApiService.class).to(TestApiServiceImpl.class);
    }

}
