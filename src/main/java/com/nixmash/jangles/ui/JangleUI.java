package com.nixmash.jangles.ui;

import com.nixmash.jangles.business.*;
import com.nixmash.jangles.containers.*;

/**
 * Created with IntelliJ IDEA.
 * User: daveburke
 * Date: 10/15/13
 * Time: 3:15 PM
 */
public class JangleUI {

    public static void ListUsers()  {
        for (JangleUser user : JangleUsers.GetJangleUsers())
        {
            System.out.println("\tuserid = " + user.UserID + "\tfirstname = " + user.FirstName);
        }
    }
}
