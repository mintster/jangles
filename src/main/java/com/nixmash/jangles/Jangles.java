package com.nixmash.jangles;

import com.nixmash.jangles.core.JangleConfiguration;
import com.nixmash.jangles.dev.JangleDev;
import com.nixmash.jangles.ui.JangleUI;


public class Jangles {

    public static void main(String[] argv) {

        System.out.println("\n\tCurrent Data Provider:\t" +
                JangleConfiguration.Get().DBProvider + "\n");
        JangleUI.ListUsers();

        JangleDev.GetConfigurationProperty();
        JangleDev.WriteToLog();
        
    }

}


