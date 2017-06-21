package com.nixmash.jangles;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nixmash.jangles.core.JanglesTestModule;
import com.nixmash.jangles.core.TestApiServiceImpl;
import com.nixmash.jangles.core.TestConnection;
import com.nixmash.jangles.db.connections.IConnection;
import com.nixmash.jangles.service.base.JanglesApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by daveburke on 6/19/17.
 */
@RunWith(JUnit4.class)
public class GuiceTests  {

    @Inject
    private JanglesApiService janglesApiService;

    @Inject
    private IConnection iConnection;

    @Before
    public void setup() {
        Injector injector = Guice.createInjector(new JanglesTestModule());
        injector.injectMembers(this);
    }

    @Test
    public void apiInjectionTest() {
        assertThat(janglesApiService, is(instanceOf(TestApiServiceImpl.class)));
    }

    @Test
    public void connectionInjectionTest() {
        assertThat(iConnection, is(instanceOf(TestConnection.class)));
    }

}
