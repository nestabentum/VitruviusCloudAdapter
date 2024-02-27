package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.view.Cache;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;

public class ViewEndpoint implements Endpoint.Post {

	private final VitruvClient client;
	private final ObjectMapper mapper = new ObjectMapper();

	public ViewEndpoint(VitruvClient client) {
		this.client = client;
		//this.mapper = mapper;
	}

	@Override
	public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
		var viewTypeName = wrapper.getRequestHeader(Header.VIEW_TYPE);
		if (viewTypeName == null) {
			return null; // TODO
		}
		var viewType = getViewType(viewTypeName);
		var selector = createSelector(viewType);

		var view = selector.createView().withChangeRecordingTrait();

		String viewId = UUID.randomUUID().toString();
		Cache.addView(viewId, view);
		wrapper.setContentType(ContentType.APPLICATION_JSON);
		wrapper.addResponseHeader(Header.VIEW_UUID, viewId);

		var resources = view.getRootObjects().stream().map(EObject::eResource).distinct().toList();
		
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			resources.get(0).save(outputStream,null);
		} catch(IOException ex)
		{
			return null;
		}
		
		
		try {
			return mapper.writeValueAsString(new ViewResponse(viewId, outputStream.toString())) ;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	record ViewResponse (String id, String view) {}

	private ViewSelector createSelector(ViewType<?> viewType) {
		var selector = client.createSelector(viewType);
		selector.getSelectableElements().forEach(el -> selector.setSelected(el, true));
		return selector;
	}

	private ViewType<?> getViewType(String viewTypeName) {
		return client.getViewTypes().stream().filter(type -> viewTypeName.equals(type.getName())).findFirst()
				.orElseThrow();
	}

}
