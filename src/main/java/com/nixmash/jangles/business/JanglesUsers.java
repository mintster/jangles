package com.nixmash.jangles.business;

import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.core.JanglesLogs;
import com.nixmash.jangles.db.JanglesMySql;
import com.nixmash.jangles.db.JanglesPostgreSql;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class JanglesUsers {

	// region MySql Users
	
		public static List<JanglesUser> getMysqlUsers() {
			return getMysqlUsers(true);
		}

		public static List<JanglesUser> getMysqlUsers(boolean useCached) {

			String key = InputListCacheKey();

			@SuppressWarnings("unchecked")
			List<JanglesUser> janglesUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
			if (janglesUsers == null || !useCached) {
				JanglesMySql db = JanglesMySql.loadProvider();
				try {
					janglesUsers = db.getJanglesUsers();
				} catch (SQLException e) {
					JanglesLogs.instance().logError(e.getMessage());
				}
				JanglesCache.getInstance().put(key, (Serializable) janglesUsers);
			}

			return janglesUsers;
		}

		public static JanglesUser getMysqlUser(int userID) {
			return getMysqlUsers().get(userID);
		}

		private static String InputListCacheKey() {
			return String.format("JanglesMysqlUserList-%s", JanglesConfiguration.get().mysqlDbConnectionName);
		}
		
		// endregion
		
	// region PostgreSql Users
	
	public static List<JanglesUser> getJanglesUsers() {
		return getJanglesUsers(true);
	}

	public static List<JanglesUser> getJanglesUsers(boolean useCached) {

		String key = OutputListCacheKey();

		@SuppressWarnings("unchecked")
		List<JanglesUser> janglesUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
		if (janglesUsers == null || !useCached) {
			JanglesPostgreSql db = JanglesPostgreSql.loadProvider();
			try {
				janglesUsers = db.getJanglesUsers();
			} catch (SQLException e) {
				JanglesLogs.instance().logError(e.getMessage());
			}
			JanglesCache.getInstance().put(key, (Serializable) janglesUsers);
		}

		return janglesUsers;
	}

	public static JanglesUser getJanglesUsers(int userID) {
		return getJanglesUsers().get(userID);
	}

	private static String OutputListCacheKey() {
		return String.format("JanglesUserList-%s", JanglesConfiguration.get().pgDbConnectionName);
	}
	
	// endregion
	
}
