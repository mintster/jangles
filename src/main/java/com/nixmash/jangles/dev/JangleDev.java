package com.nixmash.jangles.dev;

import com.nixmash.jangles.api.JangleWebApi;
import com.nixmash.jangles.core.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JangleDev {

	public static void GetConfigurationProperty() {
		System.out.println("\n\n" + System.getProperty("user.dir"));
		System.out.println(JangleConfiguration.Get().MySqlPassword);
	}

	private static Log log = JangleLogs.getLog();


	public static void WriteToLog() {
		log.warn("Writing a Warning Message from JangleDev.");
		if (log.isDebugEnabled()) {
			log.debug("Writing a Debug Message from JangleDev.");
		}
	}

	public static void WriteToLog(String message) {
		
		log.info(message);
	}

	public static void ClearJangleCache() {
		JangleCache cache = JangleCache.GetInstance();
		cache.Remove("JangleConfiguration");
		cache.Clear();
	}

	public static void ApiSayHello(String _user) {
		JangleWebApi _jangleWebApi = new JangleWebApi();
		System.out.println("\n\n" + _jangleWebApi.getHello(_user));
	}
}
