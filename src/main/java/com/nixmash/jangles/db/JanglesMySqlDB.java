package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JanglesMySqlDB extends JanglesMySql {

	// region Properties
	private Connection connection;
	private Statement statement;
	private CallableStatement callablestatement;

	// endregion

	// region MySQL Connection and Query Processes
	protected Connection mySqlConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(janglesConnection.getUrl(),
				janglesConnection.username, janglesConnection.password);
	}

	private ResultSet mySqlQuery(String query) {
		ResultSet rs = null;
		try {

			this.connection = mySqlConnection();
			this.statement = this.connection.createStatement();
			rs = this.statement.executeQuery(query);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	

	private CallableStatement mySqlCall(String statement) {
		try {

			this.connection = mySqlConnection();
			this.callablestatement = this.connection.prepareCall(statement);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return callablestatement;
	}

	private void mySqlClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.statement.isClosed())
			this.statement.close();
	}

	private void mySqlCallClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.callablestatement.isClosed())
			this.callablestatement.close();
	}

	// endregion


	
	// region janglesUsers
	@Override
	public List<JanglesUser> getJanglesUsers() throws SQLException {
		{

			List<JanglesUser> janglesUserList = new ArrayList<JanglesUser>();

			ResultSet rs = mySqlQuery("SELECT * FROM jangles_users ORDER BY user_id");

			JanglesUser janglesUser = null;
			while (rs.next()) {
				janglesUser = new JanglesUser();
				populateJanglesUserList(rs, janglesUser);
				janglesUserList.add(janglesUser);
			}
			mySqlClose();

			return janglesUserList;
		}
	}

	// endregion

}
