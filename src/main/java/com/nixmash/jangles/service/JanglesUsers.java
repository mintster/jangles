package com.nixmash.jangles.service;

import com.nixmash.jangles.dto.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.db.JanglesSql;
import com.nixmash.jangles.enums.JanglesProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class JanglesUsers {

    private static final Logger logger = LoggerFactory.getLogger(JanglesUsers.class);
    private final JanglesSql db;

    public JanglesUsers(JanglesProfile janglesProfile) {
        this.db = JanglesSql.loadProvider(janglesProfile);
    }

    public List<JanglesUser> getJanglesUsers() {
        return getJanglesUsers(true);
    }

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

    public JanglesUser getJanglesUser(Long userID) {
        return getJanglesUsers().get(userID.intValue() - 1);
    }

    public JanglesUser createJanglesUser(JanglesUser janglesUser) {
        Long userId = -1L;
        try {
            userId = db.createJanglesUser(janglesUser);
            JanglesCache.getInstance().remove(janglesUsersCacheKey());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getJanglesUser(userId);
    }

    private String janglesUsersCacheKey() {
        return String.format("JanglesUserList-%s", JanglesConfiguration.get().configFileId);
    }


}
