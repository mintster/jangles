package com.nixmash.jangles.dev;

import com.nixmash.jangles.business.JanglesApi;
import com.nixmash.jangles.business.JanglesUsers;
import com.nixmash.jangles.containers.JanglesConnection;
import com.nixmash.jangles.containers.JanglesUser;
import com.nixmash.jangles.core.JanglesCache;
import com.nixmash.jangles.core.JanglesConfiguration;
import com.nixmash.jangles.core.JanglesConnections;
import com.nixmash.jangles.core.JanglesLogs;
import org.adrianwalker.multilinestring.Multiline;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JanglesDev {

	public static void main(String[] argv) {
	}

	// region Multiline Strings

	// @formatter:off

	/**
	 * <p class="mashupintro">
	 * <span class="mashupimage"><img alt=""
	 * src="http://nixmash.com/x/pics/mashup/marion010114.jpg"
	 * /></span>Greetings from Vermont and welcome to the launch of NixMashup!
	 * In each NixMashup I'll be covering geeky topics I find interesting or
	 * helpful in my current work. Most NixMashup links can also be found on the
	 * NixMash Twitter feed at <a
	 * href="http://twitter.com/nixmash">http://twitter.com/nixmash.</a>
	 * </p>
	 * <p class="mashupintroplus">
	 * In the inaugural NixMashup we'll cover several Java and Eclipse topics,
	 * working with JAR files, a bash command or two, Don Henley, and underwater
	 * power producing kites.
	 * </p>
	 * 
	 * <div class="mashuplinks"> <div class="mashup">
	 * <p class="linktitle">
	 * <a href="http://getdeb.net/updates/Ubuntu/13.10#how_to_install">Get the
	 * Latest Ubuntu Apps on GetDeb</a>
	 * </p>
	 * <p class="linktext">
	 * Here are instructions on <a href="something">configuring your Ubuntu</a>
	 * to get the latest open source and freeware applications from GetDeb, a
	 * repository which extends the official Ubuntu repositories with the latest
	 * versions and new applications.
	 * </p>
	 * <p class="hashtags">
	 * <a href="http://nixmash.com/links/?linux">#linux</a>, <a
	 * href="http://nixmash.com/links/?getdeb">#getdeb</a>, <a
	 * href="http://nixmash.com/links/?ppa">#ppa</a>, <a
	 * href="http://nixmash.com/links/?ubuntu">#ubuntu</a>
	 * </p>
	 * 
	 * </div> <div class="mashup">
	 * <p class="linktitle">
	 * <a
	 * href="http://www.vogella.com/articles/JavaServerFaces/article.html#jsf"
	 * >JSF JavaServer Faces Tutorial</a>
	 * </p>
	 * <p class="linktext">
	 * Lars Vogel has written many excellent tutorials in Java, Eclipse, Android
	 * development and related topics. This JSF with Eclipse Tutorial is one of
	 * them.
	 * </p>
	 * <p class="hashtags">
	 * <a href="http://nixmash.com/links/?java">#java</a>, <a
	 * href="http://nixmash.com/links/?jsf">#jsf</a>, <a
	 * href="http://nixmash.com/links/?eclipse">#eclipse</a>
	 * </p>
	 * 
	 * </div> </div>
	 */
	@Multiline
	private static String msg;

	// @formatter:on

	public static void displayParsedHTML() {
		Document doc = Jsoup.parse(msg);
		Element intro = doc.select("p.mashupintro").first();
		print("intro: %s\n", intro.text());

		Elements links = doc.select("div.mashup");
		for (Element link : links) {

			Element title = link.select("p.linktitle a").first();
			print("title:%s\nurl:%s\n", title.attr("href"), title.text());

			URL aURL = null;
			try {
				aURL = new URL(title.attr("href"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			print("domain: %s\n",
					StringUtils.remove(aURL.getHost().toLowerCase(), "www."));
			Element text = link.select("p.linktext").first();
			print("text:%s\nhtml: %s\n", text.text(), text.html());

			Elements tags = link.select("p.hashtags a");
			for (Element tag : tags) {
				print("tag: %s", tag.text());
			}
			print("%s", "\n");
		}

		// System.out.println(doc.text());
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	public static void displayMultiLineString() {
		System.out.println(msg);
	}

	// endregion

	// region configuration

	public static void displayConfigurationProperty() {
		System.out.println(JanglesConfiguration.get().janglesRoot);
	}

	// endregion

	// region logs

	public static void writeToLog() {
		JanglesLogs.instance().logWarning(
				"Writing a Warning Message from Code Johnny.");
		if (JanglesLogs.getLog().isDebugEnabled()) {
			JanglesLogs.instance().logDebug(
					"Writing a Debug Message from Code Johnny.");
		}
	}

	public static void writeToLog(String message) {
		JanglesLogs.instance().logInfo(message);
	}

	// endregion

	// region users

	public static void displayPostgresUsers() {
		JanglesConnections.clearOutputConnectionCache();
		displayUsers(JanglesUsers.getJanglesUsers());
	}

	public static void displayMySqlUsers() {
		JanglesConnections.clearInputConnectionCache();
		displayUsers(JanglesUsers.getMysqlUsers());
	}

	private static void displayUsers(List<JanglesUser> janglesUsers) {
        System.out.println();

        for (JanglesUser janglesUser : janglesUsers) {
			System.out.println("User: " + janglesUser.firstName + " "
					+ janglesUser.lastName);
		}
	}

	public static JanglesUser getMySqlUser(int userId) {
		return JanglesUsers.getMysqlUser(userId);
	}

	public static JanglesUser getOutputUser(int userId) {
		return JanglesUsers.getJanglesUsers(userId);
	}

	// endregion

	// region caching

	public static void clearJanglesCache(String cachedObject) {
		JanglesCache cache = JanglesCache.getInstance();
		cache.remove(cachedObject);
		cache.clear();
	}

    public static void clearJangleCache() {
        JanglesCache cache = JanglesCache.getInstance();
        cache.remove("JangleConfiguration");
        cache.clear();
    }

	// endregion

	// region Connnections

	public static JanglesConnection getConnection(String name) {
		JanglesConnections janglesConnections = null;
		JanglesConnection currentConnection = null;

		try {
			JAXBContext jc = JAXBContext
					.newInstance(JanglesConnections.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			File xml = new File(JanglesConfiguration.get().connectionXmlPath);
			janglesConnections = (JanglesConnections) unmarshaller
					.unmarshal(xml);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		for (JanglesConnection janglesConnection : janglesConnections
				.getConnections()) {
			if (janglesConnection.name.equalsIgnoreCase(name))
				currentConnection = janglesConnection;
		}

		return currentConnection;
	}

	public static void displayMySqlConnection() {
		JanglesConnection janglesConnection = JanglesConnections
				.getMySqlConnection();
		System.out.println(janglesConnection.getUrl());
	}

	public static void displayPgConnection() {
		JanglesConnection janglesConnection = JanglesConnections
				.getPgConnection();
		System.out.println(janglesConnection.getUrl());
	}

	// endregion

	// region plugins

	public static String sayHello(String hello) {
		return "NixMashupDev says " + hello + "!";
	}

	// endregion

	// region properties

	public static void CreatePropertiesFile(String rootPath) {
		// create .niximport properties file in user home directory -- NOT USED

		File propertiesFile = new File(System.getProperty("user.home")
				+ "/test.properties");
		FileInputStream stream = null;
		if (propertiesFile.isFile()) {
			try {
				stream = new FileInputStream(propertiesFile);
				Properties p = new Properties(System.getProperties());
				p.load(stream);
				p.getProperty("niximport.rootpath", rootPath);
			} catch (IOException e) {
				JanglesLogs.instance().logError(e.getMessage());
			}
		}
	}

    public static void showAllProperties()
    {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        Properties systemProperties = System.getProperties();
        Enumeration<?> enuProp = systemProperties.propertyNames();
        while (enuProp.hasMoreElements()) {
            String propertyName = (String) enuProp.nextElement();
            String propertyValue = systemProperties.getProperty(propertyName);
            System.out.println(propertyName + ": " + propertyValue);
        }
    }

	// endregion

    // region logging

    private static Log log = JanglesLogs.getLog();

    public static void WriteToLog() {
        log.warn("Writing a Warning Message from JangleDev.");
        if (log.isDebugEnabled()) {
            log.debug("Writing a debug Message from JangleDev");
        }
    }

    public static void WriteToLog(String message) {
        log.info(message);
    }

    // endregion

    // region api

    public static void apiSayHello(String _user) {
        JanglesApi _janglesApi = new JanglesApi();
        System.out.println("\n\n" + _janglesApi.showConfiguration());
    }

    // endregion

}
