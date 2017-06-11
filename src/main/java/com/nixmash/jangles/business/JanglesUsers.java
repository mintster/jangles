package com.nixmash.jangles.business;

import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.db.JanglesMySql;
import com.nixmash.jangles.db.JanglesPostgreSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public class JanglesUsers {

	private static final Logger logger = LoggerFactory.getLogger(JanglesUsers.class);
	private JanglesPostgreSql db = JanglesPostgreSql.loadProvider();

	// region MySql Users
	
		public List<JanglesUser> getMysqlUsers() {
			return getMysqlUsers(true);
		}

		public List<JanglesUser> getMysqlUsers(boolean useCached) {

			String key = InputListCacheKey();

			@SuppressWarnings("unchecked")
			List<JanglesUser> janglesUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
			if (janglesUsers == null || !useCached) {
				JanglesMySql db = JanglesMySql.loadProvider();
				try {
					janglesUsers = db.getJanglesUsers();
				} catch (SQLException e) {
					logger.error(e.getMessage());
				}
				JanglesCache.getInstance().put(key, (Serializable) janglesUsers);
			}

			return janglesUsers;
		}

		public JanglesUser getMysqlUser(int userID) {
			return getMysqlUsers().get(userID);
		}

		private String InputListCacheKey() {
			return String.format("JanglesMysqlUserList-%s", JanglesConfiguration.get().mysqlDbConnectionName);
		}
		
		// endregion
		
	// region PostgreSql Users

    public int addJanglesUser(JanglesUser janglesUser) {
        int userId = -1;
        try {
            userId = db.addJanglesUser(janglesUser);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return userId;

    }

    public List<JanglesUser> getJanglesUsers() {
		return getJanglesUsers(true);
	}

	public List<JanglesUser> getJanglesUsers(boolean useCached) {

		String key = OutputListCacheKey();

		@SuppressWarnings("unchecked")
		List<JanglesUser> janglesUsers = (List<JanglesUser>) JanglesCache.getInstance().get(key);
		if (janglesUsers == null || !useCached) {
			JanglesPostgreSql db = JanglesPostgreSql.loadProvider();
			try {
				janglesUsers = db.getJanglesUsers();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			JanglesCache.getInstance().put(key, (Serializable) janglesUsers);
		}

		return janglesUsers;
	}

	public JanglesUser getJanglesUser(int userID) {
		return getJanglesUsers().get(userID);
	}

	private String OutputListCacheKey() {
		return String.format("JanglesUserList-%s", JanglesConfiguration.get().pgDbConnectionName);
	}
	
	// endregion
	
}
