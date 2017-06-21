package com.nixmash.jangles.service;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.db.JanglesSqlUsers;
import com.nixmash.jangles.model.JanglesUser;
import com.nixmash.jangles.service.base.JanglesUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class JanglesUserServiceImpl implements JanglesUserService {

    private static final Logger logger = LoggerFactory.getLogger(JanglesUserServiceImpl.class);
    private final JanglesSqlUsers db;

    @Inject
    public JanglesUserServiceImpl(IConnection iConnection) {
        this.db = new JanglesSqlUsers(iConnection);
    }

    @Override
    public List<JanglesUser> getJanglesUsers() {
        return getJanglesUsers(true);
    }

    @Override
    public List<JanglesUser> getJanglesUsers(boolean useCached) {

        String key = janglesUsersCacheKey();

        @SuppressWarnings("unchecked")
        List<JanglesUser> janglesUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
        if (janglesUsers == null || !useCached) {
            try {
                janglesUsers = db.getJanglesUsers();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
            JanglesCache.getInstance().put(key, (Serializable) janglesUsers);
        }
        return janglesUsers;
    }

    @Override
    public JanglesUser getJanglesUser(Long userId) {
        JanglesUser janglesUser = null;
        try {
            janglesUser = db.getJanglesUser(userId);
        } catch (SQLException e) {
            logger.error("JanglesUser with id " + userId + " not found!");
            return null;
        }
        return janglesUser;
    }

    @Override
    public JanglesUser createJanglesUser(JanglesUser janglesUser) {
        Long userId = -1L;
        userId = db.createJanglesUser(janglesUser);
        JanglesCache.getInstance().remove(janglesUsersCacheKey());
        return getJanglesUser(userId);
    }

    private String janglesUsersCacheKey() {
        return String.format("JanglesUserList-%s", JanglesConfiguration.get().configFileId);
    }


}
