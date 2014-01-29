package com.nixmash.jangles.business;

import com.nixmash.jangles.containers.JangleUser;
import com.nixmash.jangles.db.JangleDB;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daveburke
 * Date: 10/24/13
 * Time: 2:09 PM
 */
public class JangleUsers {

    public static List<JangleUser> GetJangleUsers()  {
        JangleDB db = JangleDB.LoadProvider();
        List<JangleUser> jangleUsers = null;
        try {
            jangleUsers = db.GetJangleUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jangleUsers;
    }
    
    public static JangleUser GetJangleUser(int userID)
    {
    	return GetJangleUsers().get(userID);
    }
}
