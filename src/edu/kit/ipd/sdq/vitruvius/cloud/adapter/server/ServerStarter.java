package edu.kit.ipd.sdq.vitruvius.cloud.adapter.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Set;

import com.sun.net.httpserver.HttpServer;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.AllowAllOriginsFilter;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.RequestHandler;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.ViewHandler;
import tools.vitruv.framework.remote.client.VitruvClient;

public class ServerStarter {

	private final VitruvClient vitruvClient;

	public ServerStarter(VitruvClient vitruvClient) {
		this.vitruvClient = vitruvClient;
	}

	public void startServer() throws IOException {
		var server = HttpServer.create(new InetSocketAddress(8070), 0);

		Set<RequestHandler> handlers = Set.of(new ViewHandler(vitruvClient));
		handlers.forEach(handler -> {
			handler.init(null);
			server.createContext(handler.getPath(), handler).getFilters().add(new AllowAllOriginsFilter());
		});

		server.start();
	}

}
