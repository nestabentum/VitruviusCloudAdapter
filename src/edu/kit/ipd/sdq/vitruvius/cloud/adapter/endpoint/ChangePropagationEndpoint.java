package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants.Constants;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.common.util.HttpExchangeWrapper;
import tools.vitruv.framework.remote.common.util.constants.ContentType;
import tools.vitruv.framework.remote.common.util.constants.Header;
import tools.vitruv.framework.remote.server.exception.ServerHaltingException;

public class ChangePropagationEndpoint implements Endpoint.Patch {
	
	private final HttpClient httpClient;

	public ChangePropagationEndpoint() {
		
		this.httpClient = HttpClient.newHttpClient();

	}

	@Override
	public String process(HttpExchangeWrapper wrapper) throws ServerHaltingException {
		var view = wrapper.getRequestHeader(Constants.HttpHeaders.VIEW_UUID);
		
		HttpRequest request = null;
		try {
			request = HttpRequest.newBuilder(URI.create("http://localhost:8069/vsum/view"))
					.header(Header.CONTENT_TYPE, ContentType.APPLICATION_JSON).header(Constants.HttpHeaders.VIEW_UUID, view)
					.method("PATCH", BodyPublishers.ofString(wrapper.getRequestBodyAsString())).build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		try {
			return httpClient.send(request, BodyHandlers.ofString()).body();
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
