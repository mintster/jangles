package com.nixmash.jangles;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.*;
import com.nixmash.jangles.dev.*;
import com.nixmash.jangles.ui.*;


public class Jangles {

    public static void main(String[] argv) {

        System.out.println("\n\tCurrent Data Provider:\t" +
                JangleConfiguration.Get().DBProvider + "\n");
        JangleUI.ListUsers();
        
        JangleUser jangleUser = JangleUsers.GetJangleUser(1);
        System.out.println("\n\tUser:\t" + jangleUser.FirstName + "\n");

        JangleDev.GetConfigurationProperty();
        JangleDev.WriteToLog();
        
    }

}


