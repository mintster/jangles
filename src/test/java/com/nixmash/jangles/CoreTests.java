package com.nixmash.jangles;

import com.google.inject.Inject;
import com.nixmash.jangles.core.JanglesGlobals;
import com.nixmash.jangles.guice.GuiceJUnit4Runner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Created by daveburke on 6/6/17.
 */
@RunWith(GuiceJUnit4Runner.class)
public class CoreTests  {

    private static final Logger logger = LoggerFactory.getLogger(CoreTests.class);

    @Inject
    private JanglesGlobals janglesGlobals;

    @Test
    public void loggingTest() {
        assertNotNull(logger.isInfoEnabled());
    }

    @Test
    public void globalsTests() {
        String cloudApplicationId = janglesGlobals.cloudApplicationId;
        assertNotNull(cloudApplicationId);
    }

}
