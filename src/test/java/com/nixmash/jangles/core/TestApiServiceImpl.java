package com.nixmash.jangles.core;

import com.nixmash.jangles.service.base.JanglesApiService;

/**
 * Created by daveburke on 6/19/17.
 */
public class TestApiServiceImpl implements JanglesApiService{

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
        return "Hello from TestAPIServiceImpl";
    }
}
