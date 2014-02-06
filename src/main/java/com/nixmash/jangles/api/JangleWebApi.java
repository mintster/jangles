package com.nixmash.jangles.api;

import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.JangleGlobals;

public class JangleWebApi {

	public String getHello(String _user) {
		JangleUser _jangleUser = JangleUsers.GetJangleUser(1);
		return configurationFile();

	}

	private String configurationFile() {
		return JangleGlobals.Get().ConfigurationFile;
	}
}
