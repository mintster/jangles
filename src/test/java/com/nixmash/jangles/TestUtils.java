package com.nixmash.jangles;

import com.nixmash.jangles.dto.JanglesUser;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by daveburke on 6/12/17.
 */
public class TestUtils {

    private static final String PASSWORD = "password";

    public static JanglesUser createJanglesUser(String username) {
       return new JanglesUser(
                username,
                PASSWORD,
                String.format("%s Smith", StringUtils.capitalize(username)));
    }
}
