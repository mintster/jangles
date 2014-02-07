package com.nixmash.jangles.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nixmash.jangles.core.JangleConfiguration;
import com.nixmash.jangles.core.JangleGlobals;
import com.nixmash.jangles.core.JangleLogs;
import com.nixmash.jangles.dev.JangleDev;

public class JangleWebApi {

	//private Log log = LogFactory.getLog(JangleWebApi.class);

	private Log log = JangleLogs.getLog();

	public String getHello(String _user) {

		log.info(JangleConfiguration.Get().ConfigFileID + " from web");
		String fileID = JangleConfiguration.Get().ConfigFileID + " : "
				+ JangleGlobals.Get().ConfigurationFile + " : "
				+ JangleGlobals.Get().RootDirectory + " : " +
				System.getProperty("catalina.base");
		return fileID;
	}

}
