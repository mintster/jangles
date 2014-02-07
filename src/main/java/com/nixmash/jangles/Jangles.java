package com.nixmash.jangles;

import com.nixmash.jangles.core.*;
import com.nixmash.jangles.dev.*;

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
		System.out.println("CONFIG: " + JangleConfiguration.Get().ConfigFileID + "\n");
		
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
