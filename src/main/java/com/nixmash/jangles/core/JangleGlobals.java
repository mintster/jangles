package com.nixmash.jangles.core;

import com.nixmash.jangles.enums.JangleEnvironment;

public class JangleGlobals implements java.io.Serializable {

	private static final long serialVersionUID = -5262833103399133397L;
	
	public String ConfigurationFile;
	public JangleEnvironment CurrentEnvironment;
	public String RootDirectory;

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

		String rootDirectory = "";
		String tomcatRoot = System.getProperty("catalina.base");
		
		if (tomcatRoot == null || tomcatRoot.length() == 0) {
			rootDirectory = System.getProperty("user.dir");
			this.CurrentEnvironment = JangleEnvironment.CONSOLE;
			this.ConfigurationFile = rootDirectory + "/config.properties";
		} else {

			if (tomcatRoot.indexOf(".metadata") > 0) {
				rootDirectory = tomcatRoot.substring(0,
						tomcatRoot.indexOf(".metadata"))
						+ "janglesweb";
				this.CurrentEnvironment = JangleEnvironment.WEBDEVELOPMENT;
				this.ConfigurationFile = rootDirectory + "/conf/dev.properties";
		
			} else {
			
				rootDirectory = tomcatRoot + "/jangles";
				this.CurrentEnvironment = JangleEnvironment.WEBPRODUCTION;
				this.ConfigurationFile = tomcatRoot
						+ "/webapps/jangles/conf/production.properties";
			}
		}

		this.RootDirectory = rootDirectory;
	}
}
