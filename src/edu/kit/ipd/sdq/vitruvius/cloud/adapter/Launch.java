package edu.kit.ipd.sdq.vitruvius.cloud.adapter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.Set;

import org.eclipse.emf.ecore.EPackage;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.RequestHandler;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.VitruvClientFactory;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler.ViewHandler;
import com.sun.net.httpserver.HttpServer;

public class Launch {

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
			server.createContext(handler.getPath(),handler);
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
		// var viewType = client.getViewTypes().stream().filter(eleme ->
		// "identity-mapping".equals(eleme.getName()))
		// .findFirst().get();
		// var selector = client.createSelector(viewType);
		// selector.getSelectableElements().forEach(el -> selector.setSelected(el,
		// true));
		//
		// var view = selector.createView();
		// var changeRecordingView = view.withChangeRecordingTrait();
		// Collection<EObject> rootObjects = changeRecordingView.getRootObjects();
		// FamilyRegister register = (FamilyRegister) rootObjects.stream().filter(elem
		// -> elem instanceof FamilyRegister)
		// .findFirst().get();
		// var fam = FamiliesFactory.eINSTANCE.createFamily();
		// fam.setLastName("Adpater");
		// register.getFamilies().add(fam);
		// changeRecordingView.commitChanges();

	}
}
