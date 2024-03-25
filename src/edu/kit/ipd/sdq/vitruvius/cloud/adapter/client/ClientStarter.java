package edu.kit.ipd.sdq.vitruvius.cloud.adapter.client;

import java.nio.file.Path;

import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;

public class ClientStarter {
	record ClientConfiguration(String url, int port, String projectRootPath) {
	}

	public static ClientConfiguration clientConfiguration = new ClientConfiguration("localhost", 8069, "adapter/temp");

	public VitruvClient startClient() {
		VitruvClient client = VitruvClientFactory.create(clientConfiguration.url, clientConfiguration.port,
				Path.of(clientConfiguration.projectRootPath));
		return client;

	}
}
