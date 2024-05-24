package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.Constants;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.client.HasRemoteUuid;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

public class ViewEndpoint implements Endpoint.Post {

	private final VitruvClient client;
	private final ObjectMapper mapper;

	public ViewEndpoint(VitruvClient client) {
		this.client = client;
		this.mapper = new ObjectMapper();
	}

	@Override
	public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
		var viewTypeName = wrapper.getRequestHeader(Constants.HttpHeaders.VIEW_TYPE);
		if (viewTypeName == null) {
			return null; // TODO
		}
		var viewType = getViewType(viewTypeName);
		var selector = createSelector(viewType);

		var view = selector.createView().withChangeRecordingTrait();
		if (!(view instanceof HasRemoteUuid)) {
			return null;
		}
		
		String viewId = ((HasRemoteUuid) view).getRemoteUuid();
		wrapper.setContentType(ContentType.APPLICATION_JSON);
		wrapper.addResponseHeader(Constants.HttpHeaders.VIEW_UUID, viewId);

		// var resources =
		// view.getRootObjects().stream().map(EObject::eResource).distinct().toList();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
		ResourceSet set = new ResourceSetImpl();
		set.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		ResourceCopier.copyViewResources(resources, set);

		set.getResources().forEach(res -> {
			try {

				res.save(outputStream, Map.of(XMIResource.OPTION_SAVE_TYPE_INFORMATION, true));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
		URI resourceURI = set.getResources().stream().map(res -> res.getURI()).findFirst().orElseThrow();
		
			return mapper.writeValueAsString(new ViewResponse(viewId, outputStream.toString(), getFileEnding(viewType), resourceURI.toFileString()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private String getFileEnding(ViewType<?> viewType) {
		var viewTypeEnum = edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.ViewType.valueOf(viewType.getName());
		return viewTypeEnum.getFileEnding();
	}

	record ViewResponse(String id, String view, String fileEnding, String resourceURI) {
	}

	private ViewSelector createSelector(ViewType<?> viewType) {
		var viewTypeEnum = edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.ViewType.valueOf(viewType.getName());
		var selector = client.createSelector(viewType);
		boolean foundFirst = false;
		selector.getSelectableElements().forEach(el -> {
			if (!foundFirst && viewTypeEnum.getEpackageName().equals(el.eClass().getEPackage().getName()) ){
				selector.setSelected(el, true);
			} else {
				selector.setSelected(el,false);
			}
		});
		return selector;
	}

	private ViewType<?> getViewType(String viewTypeName) {
		return client.getViewTypes().stream().filter(type -> viewTypeName.equals(type.getName())).findFirst()
				.orElseThrow();
	}

}
