package com.nixmash.jangles.service;

import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.core.JanglesGlobals;
import com.nixmash.jangles.service.base.JanglesApiService;

/**
 * Created by daveburke on 6/19/17.
 */
public class JanglesApiServiceImpl implements JanglesApiService {

    @Override
    public String showConfiguration() {
        {
            String _configuration = JanglesConfiguration.get().configFileId + " : "
                    + "GLOBAL APIKEY: " + JanglesGlobals.get().apiKey + " : "
                    + "GLOBAL PROPFILE: " + JanglesConfiguration.get().globalPropertiesFile + " : ";
            return _configuration;
        }
    }

    @Override
    public String sayHello() {
        return "Hello from JanglesAPIServiceImpl";
    }
}
