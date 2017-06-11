package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.JanglesUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: daveburke Date: 11/5/13 Time: 12:03 PM
 */

public class JanglesPostgreSqlDB extends JanglesPostgreSql {

	private static final Logger logger = LoggerFactory.getLogger(JanglesPostgreSqlDB.class);

	// region Properties
	private Connection connection;
	private Statement statement;
	private CallableStatement callableStatement;
	private PreparedStatement preparedStatement;

	// endregion

	// region PostgreSQL Connection and Query Processes
	private Connection postgreSqlConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		return DriverManager.getConnection(janglesConnection.getUrl(), janglesConnection.getUsername(),
				janglesConnection.getPassword());
	}

	private ResultSet postgreSqlQuery(String query) {
		ResultSet rs = null;
		try {

			this.connection = postgreSqlConnection();
			this.statement = this.connection.createStatement();
			rs = this.statement.executeQuery(query);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	private int postgreSqlUpdate(String query) {
		int result = 0;
		try {

			this.connection = postgreSqlConnection();
			this.statement = this.connection.createStatement();
			result = this.statement.executeUpdate(query);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private CallableStatement postgreSqlCall(String statement) {
		try {

			this.connection = postgreSqlConnection();
			this.callableStatement = this.connection.prepareCall(statement);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return callableStatement;
	}
	
	private PreparedStatement postgreSqlPrepare(String statement) {
		try {

			this.connection = postgreSqlConnection();
			this.preparedStatement = this.connection.prepareStatement(statement);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return preparedStatement;
	}
	
	private void postgreSqlCallClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.callableStatement.isClosed())
			this.callableStatement.close();
	}

	private void postgreSqlPrepareClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.preparedStatement.isClosed())
			this.preparedStatement.close();
	}
	
	private void postgreSqlClose() throws SQLException {
		if (!this.connection.isClosed())
			this.connection.close();
		if (!this.statement.isClosed())
			this.statement.close();
	}

	// endregion

	// region JanglesUsers
	@Override
	public List<JanglesUser> getJanglesUsers() throws SQLException {
		{

			List<JanglesUser> janglesUserList = new ArrayList<JanglesUser>();

			ResultSet rs = postgreSqlQuery("SELECT * FROM jangles_users ORDER BY user_id");

			JanglesUser janglesUser = null;
			while (rs.next()) {
				janglesUser = new JanglesUser();
				populateJanglesUserList(rs, janglesUser);
				janglesUserList.add(janglesUser);
			}
			postgreSqlClose();

			return janglesUserList;
		}
	}


    @Override
    public int addJanglesUser(JanglesUser janglesUser) throws SQLException {
        CallableStatement cs = postgreSqlCall("{call p_jangles_users_add( ?, ?, ?)}");
        int userId = -1;

        try {

            cs.setString(1, janglesUser.userName);
            cs.setString(2, janglesUser.password);
            cs.setString(3, janglesUser.displayName);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                userId = rs.getInt(1);
            }
        } catch (SQLException e) {
			logger.error(e.getMessage());
        }

        postgreSqlCallClose();
        return userId;
    }



    // endregion


}
