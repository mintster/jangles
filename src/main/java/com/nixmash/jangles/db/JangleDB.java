package com.nixmash.jangles.db;

import com.nixmash.jangles.containers.*;
import com.nixmash.jangles.core.JangleConfiguration;
import com.nixmash.jangles.enums.JangleDBProvider;

import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: daveburke
 * Date: 10/22/13
 * Time: 11:30 AM
 */
public abstract class JangleDB {

    final static String mySqlProvider = "com.nixmash.jangles.db.JangleMySqlDB";
    final static String postgreSqlProvider = "com.nixmash.jangles.db.JanglePostgreSqlDB";

    private static JangleDB _provider;
    public String dbUser;

    public static JangleDB Provider() {
        LoadProvider();
        return _provider;
    }


    public static JangleDB LoadProvider() {
        JangleDBProvider jangleConfigurationDbProvider =
                JangleDBProvider.valueOf(JangleConfiguration.Get().DBProvider.toUpperCase());

        String dbProvider = mySqlProvider;
        try {
            switch (jangleConfigurationDbProvider)        {
                case POSTGRESQL: dbProvider = postgreSqlProvider;
                         break;
                case MYSQL:
                    break;
                default:
                    break;

            }

            _provider = (JangleDB) Class.forName(dbProvider).newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return _provider;
    }


    //region Abstract Methods

    public abstract List<JangleUser> GetJangleUsers() throws SQLException;

    //endregion

    //region Populate List Objects from ResultSets

    public static void PopulateJangleUserList(ResultSet rs, JangleUser _jangleUser)
            throws SQLException {
        _jangleUser.UserID = rs.getInt("userid");
        _jangleUser.FirstName = rs.getString("firstname");
    }

    //endregion

}
