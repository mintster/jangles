package com.nixmash.jangles.service;

import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.core.JanglesGlobals;

public class JanglesApi {

    public String showConfiguration() {

        String _configuration = JanglesConfiguration.get().configFileId + " : "
                + "GLOBAL APIKEY: " + JanglesGlobals.get().apiKey + " : "
                + "GLOBAL PROPFILE: " + JanglesConfiguration.get().globalPropertiesFile + " : ";
        return _configuration;
    }
}
