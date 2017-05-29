package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.JanglesConnections;

import java.sql.*;
import java.util.List;

public abstract class JanglesPostgreSql extends JanglesSql {

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

}
