package com.nixmash.jangles.core;

import com.nixmash.jangles.containers.JanglesConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

@XmlRootElement(name = "connections")
@XmlAccessorType(XmlAccessType.FIELD)
public class JanglesConnections {

	private static final Logger logger = LoggerFactory.getLogger(JanglesConnections.class);

	public JanglesConnections() {
	};

	@XmlElement(name = "connection")
	private List<JanglesConnection> connections = null;

	public List<JanglesConnection> getConnections() {
		return connections;
	}

	public void setConnections(List<JanglesConnection> connections) {
		this.connections = connections;
	}

	public static JanglesConnection getMySqlConnection() {
		return getConnection
                (JanglesConfiguration.get().mysqlDbConnectionName);
	}
	
	public static JanglesConnection getPgConnection() {
		return getConnection(
                JanglesConfiguration.get().pgDbConnectionName);
	}
	
	private static JanglesConnection getConnection(String name) {

		JanglesConnections janglesConnections = null;
		JanglesConnection currentConnection = null;
		
		String cacheKey = connectionCacheKey(name);
		currentConnection = (JanglesConnection) JanglesCache.getInstance().get(cacheKey);
		if (currentConnection == null) {
			try {
				JAXBContext jc = JAXBContext.newInstance(JanglesConnections.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				File xml = new File(JanglesConfiguration.get().connectionXmlPath);
				janglesConnections = (JanglesConnections) unmarshaller.unmarshal(xml);
			} catch (JAXBException e) {
				e.printStackTrace();
			}

			currentConnection = janglesConnections.getConnections().stream()
					.filter(s -> s.name.equalsIgnoreCase(name)).findFirst().get();

			JanglesCache.getInstance().put(cacheKey, currentConnection);
		}
		return currentConnection;
	}
	

	private static String connectionCacheKey(String name) {
		return String.format("JanglesConnection-%s-%s", JanglesGlobals.get().applicationId, name);
	}

	public static void clearOutputConnectionCache()
	{
		clearConnectionCache(JanglesConfiguration.get().pgDbConnectionName);
	}
	
	public static void clearInputConnectionCache()
	{
		clearConnectionCache(JanglesConfiguration.get().mysqlDbConnectionName);
	}
	
	private static void clearConnectionCache(String name) {
		try {
			JanglesCache.getInstance().remove(connectionCacheKey(name));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
