package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesConnection;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesConnections;

import java.sql.SQLException;
import java.util.List;

public abstract class JanglesMySql extends JanglesSql {

	final static String mySqlProvider = "com.nixmash.jangles.db.JanglesMySqlDB";
	private static JanglesMySql provider;
	protected static JanglesConnection janglesConnection;
	public String dbUser;

	public static JanglesMySql loadProvider() {
		janglesConnection = JanglesConnections.getMySqlConnection();
		try {
			provider = (JanglesMySql) Class.forName(mySqlProvider).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return provider;
	}

	// region Abstract Methods


	public abstract List<JanglesUser> getJanglesUsers() throws SQLException;

	// endregion


}
