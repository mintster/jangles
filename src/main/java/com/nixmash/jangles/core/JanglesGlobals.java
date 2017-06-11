package com.nixmash.jangles.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JanglesGlobals implements java.io.Serializable {

	private static final long serialVersionUID = -5262833103399133397L;

	private static final Logger logger = LoggerFactory.getLogger(JanglesGlobals.class);

	public String applicationId;
	public String apiNoSearchResults;
	public String apiBadKeyTitle;
	public String apiKey;
	
	public static JanglesGlobals get() {

		JanglesGlobals globals = (JanglesGlobals) JanglesCache.getInstance().get("JanglesGlobals");
		if (globals == null) {
			globals = new JanglesGlobals();
			JanglesCache.getInstance().put("JanglesGlobals", globals);
		}
		return globals;
	}

	public JanglesGlobals() {
		
		Properties properties = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream(JanglesConfiguration.get().globalPropertiesFile);
			properties.load(input);
			this.applicationId = properties.getProperty("applicationid");
			
			this.apiNoSearchResults = properties.getProperty("api.no.search.results");
			this.apiBadKeyTitle = properties.getProperty("api.bad.key.title");
			this.apiKey= properties.getProperty("api.key");
			

		} catch (IOException ex) {
			logger.error(ex.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}



}
