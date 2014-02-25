package com.nixmash.jangles.core;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.nixmash.jangles.enums.JangleEnvironment;

public class JangleLogs {

	public static Log getLog() {

		//JangleEnvironment _jangleEnvironment = JangleGlobals.Get().CurrentEnvironment;
		String log4jConfigurationFile = JangleGlobals.Get().Log4jConfigurationFile;
		PropertyConfigurator.configure(JangleLogs.class.getResourceAsStream(log4jConfigurationFile));
		Log logger = LogFactory.getLog("Jangles");
	/*	File propertiesFile = null;
		switch (_jangleEnvironment) {
		case CONSOLE:
			propertiesFile = new File(root, "/log4j.properties");
			break;
		case WEBDEVELOPMENT:
			propertiesFile = new File(root, "/conf/logdev4j.properties");
			break;
		case WEBPRODUCTION:
			propertiesFile = new File(root, "/conf/logproduction4j.properties");
			break;
		default:
			break;
		}*/
		//PropertyConfigurator.configure(propertiesFile.toString());
		//PropertyConfigurator.configure(JangleLogs.class.getResourceAsStream("/log4jconsole.properties"));
		return logger;
	}
}

