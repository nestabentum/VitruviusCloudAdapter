package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.Constants;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.VitruvServer;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.JsonMapper;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;

public class ChangePropagationEndpoint implements Endpoint.Patch {

	private final HttpClient httpClient;
	private final JsonMapper mapper;

	public ChangePropagationEndpoint() {
		this.mapper = new JsonMapper(Path.of("/cloud-vsum"));
		this.httpClient = HttpClient.newHttpClient();

	}

	@Override
	public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
		var viewId = wrapper.getRequestHeader(Constants.HttpHeaders.VIEW_UUID);

		String unserializedChanges = "";
		try {
			unserializedChanges = wrapper.getRequestBodyAsString();
			var changes = mapper.deserialize(unserializedChanges, VitruviusChange.class);
			System.out.println(changes);
			// var changeResolver =
			// VitruviusChangeResolver.forHierarchicalIds(remoteView.get.getViewSource();)
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpRequest request = null;
		request = HttpRequest.newBuilder().uri(URI.create(VitruvServer.getUrl() + "/vsum/view"))
				.header(Header.CONTENT_TYPE, ContentType.APPLICATION_JSON)
				.header(Constants.HttpHeaders.VIEW_UUID, viewId)
				.method("PATCH", BodyPublishers.ofString(unserializedChanges)).build();
		;
		try {
			var propagationResponse = httpClient.send(request, BodyHandlers.ofString());
			return propagationResponse.body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
