package com.nixmash.jangles;

import com.nixmash.jangles.core.JanglesConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
