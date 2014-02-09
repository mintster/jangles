package com.nixmash.jangles.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nixmash.jangles.core.JangleConfiguration;
import com.nixmash.jangles.core.JangleGlobals;
import com.nixmash.jangles.core.JangleLogs;
import com.nixmash.jangles.dev.JangleDev;

public class JangleWebApi {

	// private Log log = LogFactory.getLog(JangleWebApi.class);

	private Log log = JangleLogs.getLog();

	public String getHello(String _user) {

		log.info(JangleConfiguration.Get().ConfigFileID + " from "
				+ JangleGlobals.Get().CurrentEnvironment);
		String fileID = JangleConfiguration.Get().ConfigFileID + " : "
				+ "CONFIG: " + JangleGlobals.Get().ConfigurationFile + " : "
				+ "ROOT: " + JangleGlobals.Get().RootDirectory + " : "
				+ "CATALINA.BASE: " + System.getProperty("catalina.base")
				+ " : " + "USER.DIR: " + System.getProperty("user.dir");
		return fileID;
	}

}
