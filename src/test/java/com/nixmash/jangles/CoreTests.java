package com.nixmash.jangles;

import com.nixmash.jangles.core.JanglesLogs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertNotNull;

/**
 * Created by daveburke on 6/6/17.
 */
@RunWith(JUnit4.class)
public class CoreTests {

    @Test
    public void loggingTest() {
        assertNotNull(JanglesLogs.getLog().isInfoEnabled());
    }
}
