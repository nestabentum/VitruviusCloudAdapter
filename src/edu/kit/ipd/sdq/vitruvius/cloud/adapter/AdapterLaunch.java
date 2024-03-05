package edu.kit.ipd.sdq.vitruvius.cloud.adapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.AllowAllOriginsFilter;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.RequestHandler;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.ViewHandler;
import com.sun.net.httpserver.HttpServer;

public class AdapterLaunch {

	public static void main(String[] args) throws IOException {
		registerEPackages();
		var vitruvClient = startClient();
		startServer(vitruvClient);

	}

	private static void startServer(VitruvClient vitruvClient) throws IOException {
		var server = HttpServer.create(new InetSocketAddress(8070), 0);

		Set<RequestHandler> handlers = Set.of(new ViewHandler(vitruvClient));
		handlers.forEach(handler -> {
			handler.init(null);
			server.createContext(handler.getPath(),handler).getFilters().add(new AllowAllOriginsFilter());
		});
		
		server.start();
	}

	private static void registerEPackages() {
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackage.eINSTANCE);
	}

	private static VitruvClient startClient() {
		VitruvClient client = VitruvClientFactory.create("localhost", 8069, Path.of("adapter/temp"));
		 return client;

	}
}
