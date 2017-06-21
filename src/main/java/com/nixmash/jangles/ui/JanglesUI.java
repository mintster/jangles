package com.nixmash.jangles.ui;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.model.JanglesUser;
import com.nixmash.jangles.service.base.JanglesApiService;
import com.nixmash.jangles.service.base.JanglesUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JanglesUI {

    private static final Logger logger = LoggerFactory.getLogger(JanglesUI.class);
    private final JanglesUserService janglesUserService;
    private final JanglesApiService janglesApiService;
    private final IConnection iConnection;

    @Inject
    public JanglesUI(IConnection iConnection, JanglesUserService janglesUserService, JanglesApiService janglesApiService) {
        this.iConnection = iConnection;
        this.janglesUserService = janglesUserService;
        this.janglesApiService = janglesApiService;
    }

    public void init() {
        displayJanglesUsers();
        apiSayHello();
    }

    // region users

    public void displayJanglesUsers() {
        logger.info("Displaying MySQL Users...");
        JanglesConnections.clearInputConnectionCache();
        displayUsers(janglesUserService.getJanglesUsers());
    }

    private void displayUsers(List<JanglesUser> janglesUsers) {
        System.out.println();

        for (JanglesUser janglesUser : janglesUsers) {
            System.out.println("User: " + janglesUser.getDisplayName());
        }
    }

    public JanglesUser getJanglesUser(Long userId) {
        return janglesUserService.getJanglesUser(userId);
    }

    // endregion

    // region properties

    public void showAllProperties() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        Properties systemProperties = System.getProperties();
        Enumeration<?> enuProp = systemProperties.propertyNames();
        while (enuProp.hasMoreElements()) {
            String propertyName = (String) enuProp.nextElement();
            String propertyValue = systemProperties.getProperty(propertyName);
            System.out.println(propertyName + ": " + propertyValue);
        }
    }

    // endregion

    // region api

    public void apiSayHello() {
        System.out.println("\n\n" +  janglesApiService.sayHello());
    }

    // endregion

}
