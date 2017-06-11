package com.nixmash.jangles;

import java.sql.*;

public class Jangles {


	public static void main(String[] argv) throws SQLException {
/*		JanglesUI janglesUI = new JanglesUI();
		janglesUI.init();*/

		Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
		Statement st = conn.createStatement();
		st.execute("create table customer(id integer, name varchar(10))");
		st.execute("insert into customer values (1, 'Thomas')");
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("select name from customer");
		while (rset.next()) {
			String name = rset.getString(1);
			System.out.println(name);
		}
	}



}
