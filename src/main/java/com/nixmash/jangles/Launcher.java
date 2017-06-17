package com.nixmash.jangles;

import com.nixmash.jangles.ui.JanglesUI;

import java.sql.SQLException;

public class Launcher {

	public static void main(String[] argv) throws SQLException {
		JanglesUI janglesUI = new JanglesUI();
		janglesUI.init();
	}

}
