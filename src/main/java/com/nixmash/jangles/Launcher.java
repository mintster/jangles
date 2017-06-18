package com.nixmash.jangles;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nixmash.jangles.core.JanglesModule;
import com.nixmash.jangles.ui.JanglesUI;

import java.sql.SQLException;

public class Launcher {

	public static void main(String[] argv) throws SQLException {
		Injector injector = Guice.createInjector(new JanglesModule());
		JanglesUI janglesUI = injector.getInstance(JanglesUI.class);
		janglesUI.init();
	}

}
