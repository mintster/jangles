package com.nixmash.jangles.core;

import com.nixmash.jangles.enums.JangleEnvironment;

public class JangleGlobals implements java.io.Serializable {

	public String ConfigurationFile;
	public JangleEnvironment CurrentEnvironment;

	public static JangleGlobals Get() {

		JangleGlobals globals = (JangleGlobals) JangleCache.GetInstance().Get(
				"JangleGlobals");
		if (globals == null) {
			globals = new JangleGlobals();
			JangleCache.GetInstance().Put("JangleGlobals", globals);
		}
		return globals;
	}

	public JangleGlobals() {

		String tomcatRoot = System.getProperty("catalina.base");
		if (tomcatRoot == null || tomcatRoot.length() == 0) {
			this.CurrentEnvironment = JangleEnvironment.CONSOLE;
			this.ConfigurationFile = System.getProperty("user.dir") + "/config.properties";
		} else {
			if (tomcatRoot.indexOf(".metadata") > 0) {
				this.CurrentEnvironment = JangleEnvironment.WEBDEVELOPMENT;
				this.ConfigurationFile = tomcatRoot.substring(0,
						tomcatRoot.indexOf(".metadata"))
						+ "config.properties";
			} else {
				this.CurrentEnvironment = JangleEnvironment.WEBPRODUCTION;
				this.ConfigurationFile = tomcatRoot
						+ "/jangles/conf/jangles.properties";
			}
		}
	}
}
