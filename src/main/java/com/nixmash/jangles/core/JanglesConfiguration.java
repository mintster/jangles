package com.nixmash.jangles.core;

import java.io.IOException;
import java.util.Properties;

public class JanglesConfiguration implements java.io.Serializable {

	// region properties

	private static final long serialVersionUID = -720348471534321068L;

	public String configFileId;
	public String janglesRoot;
	public String mysqlDbConnectionName;
	public String pgDbConnectionName;
	public String connectionXmlPath;
	public String globalPropertiesFile;
	
	// endregion

	// region get()

	public static JanglesConfiguration get() {

		JanglesConfiguration config = (JanglesConfiguration) JanglesCache.getInstance().get("JanglesConfiguration");
		if (config == null) {
			config = new JanglesConfiguration();
			JanglesCache.getInstance().put("JanglesConfiguration", config);
		}
		return config;
	}

	public JanglesConfiguration() {

		String rootDirectory = System.getProperty("catalina.base");
		if (rootDirectory == null)
			rootDirectory = System.getProperty("user.dir");

		this.janglesRoot = rootDirectory + "/";

		Properties properties = new Properties();

		try {
			properties.load(getClass().getResourceAsStream("/jangles.properties"));
		} catch (IOException e) {
			JanglesLogs.instance().logError(e.getMessage());
		}

		this.configFileId = properties.getProperty("configfileid");
		this.janglesRoot = rootDirectory + "/";
		this.mysqlDbConnectionName = properties.getProperty("mysql.db.connection");
		this.pgDbConnectionName = properties.getProperty("pg.db.connection");
		this.connectionXmlPath = properties.getProperty("connection.xml.path");
		this.globalPropertiesFile = properties.getProperty("global.properties.file");

		
	}

	// endregion

}
