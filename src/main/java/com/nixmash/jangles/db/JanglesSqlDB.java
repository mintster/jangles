package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JanglesSqlDB extends JanglesSql {

	// region janglesUsers

	@Override
	public List<JanglesUser> getJanglesUsers() throws SQLException {
		{

			List<JanglesUser> janglesUserList = new ArrayList<JanglesUser>();

			ResultSet rs = sqlQuery("SELECT * FROM jangles_users ORDER BY user_id");

			JanglesUser janglesUser = null;
			while (rs.next()) {
				janglesUser = new JanglesUser();
				populateJanglesUserList(rs, janglesUser);
				janglesUserList.add(janglesUser);
			}
			sqlClose();

			return janglesUserList;
		}
	}

	// endregion

}
