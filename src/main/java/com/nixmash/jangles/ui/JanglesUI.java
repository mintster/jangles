package com.nixmash.jangles.ui;

import com.nixmash.jangles.business.JanglesApi;
import com.nixmash.jangles.business.JanglesUsers;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesConnections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JanglesUI {

    private static final Logger logger = LoggerFactory.getLogger(JanglesUI.class);

    private JanglesUsers janglesUsers = new JanglesUsers();

    public void init() {
        showAllProperties();
    }

    // region users

    public int addJanglesUser() {
        JanglesUser janglesUser = new JanglesUser("harry", "password", "Harry Harwood");
        return janglesUsers.addJanglesUser(janglesUser);
    }

    public void displayPostgresUsers() {
        JanglesConnections.clearOutputConnectionCache();
        displayUsers(janglesUsers.getJanglesUsers());
    }

    public void displayMySqlUsers() {
        logger.info("Displaying MySQL Users...");
        JanglesConnections.clearInputConnectionCache();
        displayUsers(janglesUsers.getMysqlUsers());
    }

    private void displayUsers(List<JanglesUser> janglesUsers) {
        System.out.println();

        for (JanglesUser janglesUser : janglesUsers) {
            System.out.println("User: " + janglesUser.displayName);
        }
    }

    public JanglesUser getMySqlUser(int userId) {
        return janglesUsers.getMysqlUser(userId);
    }

    public JanglesUser getOutputUser(int userId) {
        return janglesUsers.getJanglesUser(userId);
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
