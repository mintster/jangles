package com.nixmash.jangles;

import com.nixmash.jangles.containers.JanglesConnection;
import com.nixmash.jangles.core.JanglesConnections;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.newString;
import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.stripMargin;

/**
 * Created by daveburke on 5/29/17.
 */

@RunWith(JUnit4.class)
public class JanglesTests {

    // region Connections

    @Test
    public void getMySqlConnection() {
        JanglesConnection janglesConnection = JanglesConnections.getMySqlConnection();
        Assert.assertEquals(janglesConnection.environment, "mysql");
    }

    @Test
    public void getPgConnection() {
        JanglesConnection janglesConnection = JanglesConnections.getPgConnection();
        Assert.assertEquals(janglesConnection.environment, "postgresql");
    }

    // endregion


    // region Multiline Strings

    @Test
    public void multilineTests() {
        final String expected = "\n" +
                "This is the first line\n" +
                "of a multiline string\n" +
                "that we are testing with Multiline Strings.";

        String msg = stripMargin(/*
        |This is the first line
        |of a multiline string
        |that we are testing with Multiline Strings.*/);

        Assertions.assertThat(msg).isEqualTo(expected);

        msg = newString(/*
This is the first line
of a multiline string
that we are testing with Multiline Strings.*/);

        Assertions.assertThat(msg).isEqualTo(expected);

        String s = newString(/* aaa */);
        Assertions.assertThat(s).isEqualTo(" aaa ");

        s = newString(/*aaa*/);
        Assertions.assertThat(s).isEqualTo("aaa");

    }

    // endregion
}
