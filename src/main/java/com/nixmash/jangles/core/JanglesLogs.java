package com.nixmash.jangles.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

public final class JanglesLogs {

	private static Log log;
    private static JanglesLogs instance;
    
    // region Create Instance
    
    private JanglesLogs() {
		try {
			JanglesLogs.log = getLog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static JanglesLogs instance() {
        synchronized (JanglesLogs.class) {
            if (instance == null) {
                instance = new JanglesLogs();
            }
        }

        return instance;
    }
    
	public static Log getLog() {

		String log4jConfigurationFile = JanglesGlobals.get().log4jConfigurationFile;
		PropertyConfigurator.configure(JanglesLogs.class.getResourceAsStream(log4jConfigurationFile));
		Log log = LogFactory.getLog("Jangles");

		return log;
	}

	// endregion
	
	// region Log Actions
	
	public void logError(String msg) {
		String _msg = msg;
		log.error(msg);
	}
	
	public void logWarning(String msg) {
		log.warn(msg);
	}
	
	public void logDebug(String msg) {
		log.debug(msg);
	}
	
	public void logInfo(String msg) {
		log.info(msg);
	}
	// endregion
	
}
