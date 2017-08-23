package com.nixmash.jangles.core;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nixmash.jangles.db.UsersDb;
import com.nixmash.jangles.db.UsersDbImpl;
import com.nixmash.jangles.db.cn.IConnection;
import com.nixmash.jangles.db.cn.MySqlConnection;
import com.nixmash.jangles.service.UserService;
import com.nixmash.jangles.service.UserServiceImpl;

/**
 * Created by daveburke on 6/17/17.
 */
public class JanglesModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(IConnection.class).to(MySqlConnection.class);
        binder.bind(UsersDb.class).to(UsersDbImpl.class);
        binder.bind(UserService.class).to(UserServiceImpl.class);
    }
}
