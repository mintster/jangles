package com.nixmash.jangles.ui;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.db.IConnection;
import com.nixmash.jangles.dto.JanglesUser;
import com.nixmash.jangles.service.JanglesApi;
import com.nixmash.jangles.service.JanglesUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JanglesUI {

    private static final Logger logger = LoggerFactory.getLogger(JanglesUI.class);
    private JanglesUserServiceImpl janglesUserServiceImpl;

    private final IConnection iConnection;

    @Inject
    public JanglesUI(IConnection iConnection) {
        this.iConnection = iConnection;
       janglesUserServiceImpl = new JanglesUserServiceImpl(iConnection);
    }

    public void init() {
        displayJanglesUsers();
        apiSayHello("bob");
    }

    // region users

    private void displayJanglesUsers() {
        logger.info("Displaying MySQL Users...");
        JanglesConnections.clearInputConnectionCache();
        displayUsers(janglesUserServiceImpl.getJanglesUsers());
    }

    private void displayUsers(List<JanglesUser> janglesUsers) {
        System.out.println();

        for (JanglesUser janglesUser : janglesUsers) {
            System.out.println("User: " + janglesUser.getDisplayName());
        }
    }

    public JanglesUser getJanglesUser(Long userId) {
        return janglesUserServiceImpl.getJanglesUser(userId);
    }

    // endregion


    // region plugins

    public String sayHello(String hello) {
        return "JanglesUI says " + hello + "!";
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

    public void apiSayHello(String _user) {
        JanglesApi _janglesApi = new JanglesApi();
        System.out.println("\n\n" + _janglesApi.showConfiguration());
    }

    // endregion

}
