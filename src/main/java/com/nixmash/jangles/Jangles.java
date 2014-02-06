package com.nixmash.jangles;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.*;
import com.nixmash.jangles.dev.*;
import com.nixmash.jangles.ui.*;

public class Jangles {

	public static void main(String[] argv) {

		/*
		 * System.out.println("\n\tCurrent Data Provider:\t" +
		 * JangleConfiguration.Get().DBProvider + "\n"); JangleUI.ListUsers();
		 * 
		 * JangleUser jangleUser = JangleUsers.GetJangleUser(1);
		 * System.out.println("\n\tUser:\t" + jangleUser.FirstName + "\n");
		 */

		JangleDev.ApiSayHello("Main Dude");
		/*
		 * JangleDev.GetConfigurationProperty(); JangleDev.WriteToLog();
		 * 
		 * System.out.println("CONFIG: " + System.getenv("CONFIG") + "\n");
		 * 
		 * Map<String, String> env = System.getenv(); for (String envName :
		 * env.keySet()) { System.out.format("%s=%s%n", envName,
		 * env.get(envName)); }
		 */
		
/*		Properties systemProperties = System.getProperties();
		Enumeration enuProp = systemProperties.propertyNames();
		while (enuProp.hasMoreElements()) {
			String propertyName = (String) enuProp.nextElement();
			String propertyValue = systemProperties.getProperty(propertyName);
			System.out.println(propertyName + ": " + propertyValue);
		}
		*/
	}

}
