package com.nixmash.jangles.business;

import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.core.JanglesGlobals;
import com.nixmash.jangles.core.JanglesLogs;
import org.apache.commons.logging.Log;

public class JanglesApi {

    private Log log = JanglesLogs.getLog();

    public String showConfiguration() {


        String _configuration = JanglesConfiguration.get().configFileId + " : "
                + "GLOBAL APIKEY: " + JanglesGlobals.get().apiKey + " : "
                + "GLOBAL PROPFILE: " + JanglesConfiguration.get().globalPropertiesFile + " : ";
        return _configuration;
    }
}
