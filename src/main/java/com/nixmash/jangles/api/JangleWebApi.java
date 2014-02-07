package com.nixmash.jangles.api;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.JangleConfiguration;
import com.nixmash.jangles.core.JangleGlobals;

public class JangleWebApi {

	public String getHello(String _user) {

		String fileID = JangleConfiguration.Get().ConfigFileID;
		return fileID;
	}

}
