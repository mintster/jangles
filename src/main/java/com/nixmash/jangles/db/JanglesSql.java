package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesUser;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by daveburke on 5/29/17.
 */
public class JanglesSql {

    // region Populate List Objects from ResultSets

    public static void populateJanglesUserList(ResultSet rs, JanglesUser janglesUser) throws SQLException {
        janglesUser.setUserId(rs.getInt("user_id"));
        janglesUser.setUserName(rs.getString("user_name"));
        janglesUser.setPassword(rs.getString("password"));
        janglesUser.setDisplayName(rs.getString("display_name"));
        janglesUser.setDateCreated(rs.getTimestamp("date_created"));
        janglesUser.setIsActive(rs.getBoolean("is_active"));
    }

    // endregion
}
