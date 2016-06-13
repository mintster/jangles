package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.JanglesConnections;

import java.sql.*;
import java.util.List;

public abstract class JanglesPostgreSql {

	// region loadProvider

	final static String postgreSqlProvider = "com.nixmash.jangles.db.JanglesPostgreSqlDB";

	private static JanglesPostgreSql provider;
	protected static JanglesConnection janglesConnection;
	public String dbUser;

	public static JanglesPostgreSql loadProvider() {
		janglesConnection = JanglesConnections.getPgConnection();

		try {
			provider = (JanglesPostgreSql) Class.forName(postgreSqlProvider).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return provider;
	}

	// endregion

	// region Abstract Methods

    public abstract List<JanglesUser> getJanglesUsers() throws SQLException;
    public abstract int addJanglesUser(JanglesUser janglesUser) throws SQLException;

	// endregion

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
