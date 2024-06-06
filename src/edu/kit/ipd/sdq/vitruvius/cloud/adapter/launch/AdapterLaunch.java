package edu.kit.ipd.sdq.vitruvius.cloud.adapter.launch;

import java.io.IOException;
import org.apache.logging.log4j.*;
import org.eclipse.emf.ecore.EPackage;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.client.ClientStarter;
import tools.vitruv.change.atomic.AtomicPackage;
import tools.vitruv.framework.remote.client.VitruvClient;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.server.ServerStarter;

public class AdapterLaunch {
	private static Logger logger = LogManager.getLogger(AdapterLaunch.class);

	public static void main(String[] args) throws IOException {
		registerEPackages();

		ClientStarter clientStarter = new ClientStarter();
		VitruvClient vitruvClient = clientStarter.startClient();

		ServerStarter serverStarter = new ServerStarter(vitruvClient);
		logger.info("Starting Server");
		serverStarter.startServer();

	}

	private static void registerEPackages() {
		EPackage.Registry.INSTANCE.put(FamiliesPackage.eNS_URI, FamiliesPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(PersonsPackage.eNS_URI, PersonsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(AtomicPackage.eNS_URI, AtomicPackage.eINSTANCE);
	}

}
