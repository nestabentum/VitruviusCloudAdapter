package edu.kit.ipd.sdq.vitruvius.cloud.adapter.launch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.client.ClientStarter;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.AllowAllOriginsFilter;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.RequestHandler;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.ViewHandler;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.server.ServerStarter;

import com.sun.net.httpserver.HttpServer;

public class AdapterLaunch {

	public static void main(String[] args) throws IOException {
		registerEPackages();

		ClientStarter clientStarter = new ClientStarter();
		VitruvClient vitruvClient = clientStarter.startClient();

		ServerStarter serverStarter = new ServerStarter(vitruvClient);
		serverStarter.startServer();

	}

	private static void registerEPackages() {
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackage.eINSTANCE);
	}

}
