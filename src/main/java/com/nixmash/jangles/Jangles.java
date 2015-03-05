package com.nixmash.jangles;

import com.nixmash.jangles.dev.JanglesDev;

public class Jangles {

	public static void main(String[] argv) {
		displayDev();
    }

	private static void displayDev() {

//        JanglesDev.apiSayHello("Main Dude");

        JanglesDev.displayPostgresUsers();
        JanglesDev.displayMySqlUsers();


    }

}
