package edu.kit.ipd.sdq.vitruvius.cloud.adapter.client;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.VitruvAdapter;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.VitruvServer;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;

public class ClientStarter {
	private static Logger logger = LogManager.getLogger(ClientStarter.class);

	record ClientConfiguration(String url, int port, String projectRootPath) {
	}

	public static ClientConfiguration clientConfiguration = new ClientConfiguration(VitruvServer.getVitruvServerHost(),
			VitruvServer.getVitruvServerPort(), "adapter/temp");

	public VitruvClient startClient() {
		VitruvClient client = VitruvClientFactory.create(clientConfiguration.url, clientConfiguration.port,
				Path.of(clientConfiguration.projectRootPath));
		return client;

	}
}
