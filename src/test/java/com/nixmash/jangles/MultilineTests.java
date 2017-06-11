package com.nixmash.jangles;

import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.MalformedURLException;
import java.net.URL;

import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.newString;
import static com.github.alessiosantacroce.multilinestring.MultilineStringLiteral.stripMargin;
import static org.junit.Assert.*;

/**
 * Created by daveburke on 5/29/17.
 */

@RunWith(JUnit4.class)
public class MultilineTests {

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


    // region Multiline String with JSoup Parsing

    // region The Multiline String

    private String msg = newString(/*
                        <p class="mashupintro">
                        <span class="mashupimage"><img alt=""
                        src="http://nixmash.com/x/pics/mashup/marion010114.jpg"
                        /></span>Greetings from Vermont and welcome to the launch of NixMashup!
                        In each NixMashup I'll be covering geeky topics I find interesting or
                        helpful in my current work. Most NixMashup links can also be found on the
                        NixMash Twitter feed at <a
                        href="http://twitter.com/nixmash">http://twitter.com/nixmash.</a>
                        </p>
                        <p class="mashupintroplus">
                        In the inaugural NixMashup we'll cover several Java and Eclipse topics,
                        working with JAR files, a bash command or two, Don Henley, and underwater
                        power producing kites.
                        </p>
                        <p/>
                        <div class="mashuplinks"> <div class="mashup">
                        <p class="linktitle">
                        <a href="http://getdeb.net/updates/Ubuntu/13.10#how_to_install">Get the
                        Latest Ubuntu Apps on GetDeb</a>
                        </p>
                        <p class="linktext">
                        Here are instructions on <a href="something">configuring your Ubuntu</a>
                        to get the latest open source and freeware applications from GetDeb, a
                        repository which extends the official Ubuntu repositories with the latest
                        versions and new applications.
                        </p>
                        <p class="hashtags">
                        <a href="http://nixmash.com/links/?linux">#linux</a>, <a
                        href="http://nixmash.com/links/?getdeb">#getdeb</a>, <a
                        href="http://nixmash.com/links/?ppa">#ppa</a>, <a
                        href="http://nixmash.com/links/?ubuntu">#ubuntu</a>
                        </p>
                        <p/>
                        </div> <div class="mashup">
                        <p class="linktitle">
                        <a
                        href="http://www.vogella.com/articles/JavaServerFaces/article.html#jsf"
                        >JSF JavaServer Faces Tutorial</a>
                        </p>
                        <p class="linktext">
                        Lars Vogel has written many excellent tutorials in Java, Eclipse, Android
                        development and related topics. This JSF with <a href="somelink.com">Eclipse
                        Tutorial</a> is one of them.
                        </p>
                        <p class="hashtags">
                        <a href="http://nixmash.com/links/?java">#java</a>, <a
                        href="http://nixmash.com/links/?jsf">#jsf</a>, <a
                        href="http://nixmash.com/links/?eclipse">#eclipse</a>
                        </p>
                        <p/>
                        </div> </div>
                        */);

    // endregion

    @Test
    public  void displayParsedHTML() {
        Document doc = Jsoup.parse(msg);
        Element intro = doc.select("p.mashupintro").first();
        assertNotNull(intro.text());

        Elements links = doc.select("div.mashup");
        for (Element link : links) {

            Element title = link.select("p.linktitle a").first();
            assertNotNull(title.text());

            URL aURL = null;
            try {
                aURL = new URL(title.attr("href"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            assertTrue(aURL.getHost().toLowerCase().contains("."));

            Element text = link.select("p.linktext").first();
            assertFalse(text.text().contains("</a>"));
            assertTrue(text.html().contains("</a>"));

            Elements tags = link.select("p.hashtags a");
            Assertions.assertThat(tags.size()).isGreaterThan(0);

/*
            System.out.println(String.format("intro: %s\n", intro.text()));
            System.out.println(String.format("title:%s\nurl:%s\n", title.attr("href"), title.text()));
            System.out.println(String.format("domain: %s\n", StringUtils.remove(aURL.getHost().toLowerCase(), "www.")));
            System.out.println(aURL.getHost().toLowerCase());
            System.out.println(String.format("text:%s\nhtml: %s\n", text.text(), text.html()));
*/
        }

        // System.out.println(doc.text());
    }

    public void displayMultiLineString() {
        System.out.println(msg);
    }

    // endregion

}
