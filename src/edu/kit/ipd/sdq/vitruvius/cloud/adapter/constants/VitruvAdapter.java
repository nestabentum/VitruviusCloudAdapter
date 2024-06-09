package edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VitruvAdapter {
	private static Logger logger = LogManager.getLogger(VitruvAdapter.class);
	private static final int PORT = 8070;
	private static final String ENV_ADAPTER_HOST = "VITRUV_ADAPTER_HOST";
	private static final String ENV_ADAPTER_PORT = "VITRUV_ADAPTER_PORT";

	public static String getAdapterHost() {
		String systemEnvVal = System.getenv(ENV_ADAPTER_HOST);
		logger.debug("Found environment variable " + ENV_ADAPTER_HOST + " " + systemEnvVal);
		return systemEnvVal == null ? "localhost" : systemEnvVal;
	}

	public static int getAdapterPort() {
		String systemEnvVal = System.getenv(ENV_ADAPTER_PORT);
		if (systemEnvVal == null) {
			logger.warn("Environment variable " + ENV_ADAPTER_PORT + " not found");
			return PORT;
		}
		logger.debug("Found environment variable " + ENV_ADAPTER_HOST + " " + systemEnvVal);
		int port;
		try {
			port = Integer.valueOf(systemEnvVal);
		} catch (NumberFormatException e) {
			logger.error("Vitruv Adapter Port is not a number but" + systemEnvVal);
			port = PORT;
		}
		return port;
	}

}