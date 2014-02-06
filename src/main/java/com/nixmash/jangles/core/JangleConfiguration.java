package com.nixmash.jangles.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class JangleConfiguration implements java.io.Serializable {

	//private static final String CONSOLECONFIG  =  System.getProperty("user.dir") + "/config.properties";
	
	private static final long serialVersionUID = -720348471534321068L;
	public String MySqlUser;
    public String MySqlPassword;
    public String MySqlUrl;
    public String DBProvider;

    public String PostgreSqlUser;
    public String PostgreSqlPassword;
    public String PostgreSqlUrl;

    public String ConfigFileID;
    
//    private static JCS JangleCache;

    public static JangleConfiguration Get() {

        JangleConfiguration config = (JangleConfiguration)
                JangleCache.GetInstance().Get("JangleConfiguration");
        if (config == null) {
            config = new JangleConfiguration();
            JangleCache.GetInstance().Put("JangleConfiguration", config);
        }
        return config;
    }

    public JangleConfiguration() {

        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream("/ubuntuland/files/eclipse/jangles/config.properties"));

            this.DBProvider = properties.getProperty("dbprovider");

            this.MySqlUrl = properties.getProperty("mysqlurl");
            this.MySqlUser = properties.getProperty("mysqluser");
            this.MySqlPassword = properties.getProperty("mysqlpassword");

            this.PostgreSqlUrl = properties.getProperty("pgurl");
            this.PostgreSqlUser = properties.getProperty("pguser");
            this.PostgreSqlPassword = properties.getProperty("pgpassword");
            this.ConfigFileID = properties.getProperty("configfileid");
            	

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

