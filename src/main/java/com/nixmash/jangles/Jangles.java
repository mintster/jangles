package com.nixmash.jangles;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.nixmash.jangles.core.*;
import com.nixmash.jangles.dev.*;

public class Jangles {

	public static void main(String[] argv) {

	
		JangleDev.ApiSayHello("Main Dude");
		
		JangleDev.WriteToLog("log4j properties file located again!");
	
//		Map<String, String> env = System.getenv();
//		for (String envName : env.keySet()) {
//			System.out.format("%s=%s%n", envName, env.get(envName));
//		}
//
//		Properties systemProperties = System.getProperties();
//		Enumeration<?> enuProp = systemProperties.propertyNames();
//		while (enuProp.hasMoreElements()) {
//			String propertyName = (String) enuProp.nextElement();
//			String propertyValue = systemProperties.getProperty(propertyName);
//			System.out.println(propertyName + ": " + propertyValue);
//		}

	}

}

