package com.nixmash.jangles.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class JanglesUtils {

	public static String pluralize(String singular) {
		String plural = singular;
		int singularLength = StringUtils.length(singular);
		if (StringUtils.right(singular, 1) == "y")
			plural = StringUtils.left(singular, singularLength - 1) + "ies";
		else
			plural = singular + "s";
		return plural;
	}

	public static String lowerPluralize(String singular) {
		return StringUtils.uncapitalize(pluralize(singular));
	}

	public static boolean isInTestingMode() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		List<StackTraceElement> list = Arrays.asList(stackTrace);
		for (StackTraceElement element : list) {
			if (element.getClassName().startsWith("org.junit.")) {
				return true;
			}
		}
		return false;
	}

	public static void configureTestDb(String sql) throws FileNotFoundException, SQLException {
		Properties properties = new Properties();
		ClassLoader classLoader = JanglesUtils.class.getClassLoader();
		InputStream inputStream =
				classLoader.getResourceAsStream("test.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = properties.getProperty("testdb.url");
		String dbuser = properties.getProperty("testdb.user");
		String dbpassword = properties.getProperty("testdb.password");
		Connection conn = DriverManager.getConnection(url,dbuser, dbpassword);
		Statement st = conn.createStatement();
		File script = new File(classLoader.getResource(sql).getFile());
		ScriptRunner sr = new ScriptRunner(conn);
		sr.setLogWriter(null);
		Reader reader = new BufferedReader(new FileReader(script));
		sr.runScript(reader);

	}

}
