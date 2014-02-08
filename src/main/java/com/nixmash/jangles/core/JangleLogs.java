package com.nixmash.jangles.core;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.nixmash.jangles.Jangles;
import com.nixmash.jangles.enums.JangleEnvironment;

public class JangleLogs {

	public static Log getLog() {
		JangleEnvironment _jangleEnvironment = JangleGlobals.Get().CurrentEnvironment;
		Log logger = LogFactory.getLog("Jangles");
		File propertiesFile = null;
		switch (_jangleEnvironment) {
		case CONSOLE:
			propertiesFile = new File(JangleGlobals.Get().RootDirectory,
					"/log4j.properties");
			break;
		case WEBDEVELOPMENT:
			propertiesFile = new File(JangleGlobals.Get().RootDirectory,
					"/conf/logdev4j.properties");
			break;
		case WEBPRODUCTION:
			propertiesFile = new File(JangleGlobals.Get().RootDirectory,
					"/conf/logproduction4j.properties");
			break;
		default:
			break;
		}
		/*if (_jangleEnvironment == JangleEnvironment.CONSOLE) {
			propertiesFile = new File(JangleGlobals.Get().RootDirectory,
					"/log4j.properties");
		}
		else
		{
			propertiesFile = new File(JangleGlobals.Get().RootDirectory,
					"/conf/log4j.properties");
		}*/
		PropertyConfigurator.configure(propertiesFile.toString());

		return logger;
	}
}
