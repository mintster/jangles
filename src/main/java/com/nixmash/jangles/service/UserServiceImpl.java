package com.nixmash.jangles.service;

import com.google.inject.Inject;
import com.nixmash.jangles.db.UsersDb;
import com.nixmash.jangles.dto.CurrentUser;
import com.nixmash.jangles.dto.Role;
import com.nixmash.jangles.dto.User;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String CURRENT_USER = "CurrentUser";

    private UsersDb usersDb;

    @Inject
    public UserServiceImpl(UsersDb usersDb) {
        this.usersDb = usersDb;
    }

    @Override
    public User getUser(String username) {
        User user = null;
        try {
            user = usersDb.getUser(username);
        } catch (SQLException e) {
            logger.error("Exception retrieving user by username: " + username);
        }
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = null;
        try {
            users = usersDb.getUsers();
        } catch (SQLException e) {
            logger.error("Exception retrieving all users");}
        return users;
    }

    @Override
    public User createUser(User user) {
        user.setPassword(new Sha256Hash(user.getPassword()).toHex());
        logger.info("User with email:" + user.getEmail() + " hashedPassword:" +user.getPassword());
        try {
            user = usersDb.addUser(user);
        } catch (SQLException e) {
            logger.error("Exception adding user with username: " + user.getUsername());}
        return user;
    }

    @Override
    public List<Role> getRoles(Long userId) {
        List<Role> roles = null;
        try {
            roles =  usersDb.getRoles(userId);
        }
        catch (SQLException e) {
            logger.error("Exception retrieving all roles");}
        return roles;
    }

    @Override
    public CurrentUser getCurrentUser(Subject subject) {
        User user = this.getUser(subject.getPrincipals().toString());
        CurrentUser currentUser = new CurrentUser(user);
        List<Role> roles = this.getRoles(user.getUserId());

        for (Role role : roles) {
            if (role.getRoleName().equals("admin")) {
                currentUser.setAdministrator(true);
            }
            currentUser.getRoles().add(role.getRoleName());
            currentUser.getPermissions().add(role.getPermission());
        }
        return currentUser;
    }
}
