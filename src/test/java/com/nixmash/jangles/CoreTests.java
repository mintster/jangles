package com.nixmash.jangles;

import com.nixmash.jangles.business.JanglesUsers;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.enums.JanglesProfile;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by daveburke on 6/6/17.
 */
@RunWith(JUnit4.class)
public class CoreTests  {

    private static final Logger logger = LoggerFactory.getLogger(CoreTests.class);

    @Test
    public void loggingTest() {
        assertNotNull(logger.isInfoEnabled());
    }

    @Test
    public void configurationTest() {
        String globalPropertiesFile = JanglesConfiguration.get().globalPropertiesFile;
        Assertions.assertThat(globalPropertiesFile.endsWith("global.properties"));
    }
}
