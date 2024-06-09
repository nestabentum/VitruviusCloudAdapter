package edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VitruvServer {
	private static final int PORT = 8069;
	private static Logger logger = LogManager.getLogger(VitruvServer.class);
	private static final String ENV_SERVER_HOST = "VITRUV_SERVER_HOST";
	private static final String ENV_SERVER_PORT = "VITRUV_SERVER_PORT";

	public static String getUrl() {
		return "http://" + getVitruvServerHost() + ":" + getVitruvServerPort();
	}

	public static String getVitruvServerHost() {
		String systemEnvVal = System.getenv(ENV_SERVER_HOST);
		logger.debug("Found environment variable " + ENV_SERVER_HOST + " " + systemEnvVal);
		return systemEnvVal == null ? "localhost" : systemEnvVal;
	}

	public static int getVitruvServerPort() {
		String systemEnvVal = System.getenv(ENV_SERVER_PORT);
		if (systemEnvVal == null) {
			logger.warn("Environment variable not found " + ENV_SERVER_PORT + " " + systemEnvVal);
			return PORT;
		}
		logger.debug("Found environment variable " + ENV_SERVER_PORT + " " + systemEnvVal);
		int port;
		try {
			port = Integer.valueOf(systemEnvVal);
		} catch (NumberFormatException e) {
			logger.error("Vitruv Server Port is not a number but" + systemEnvVal);
			port = PORT;
		}
		return port;
	}
}
