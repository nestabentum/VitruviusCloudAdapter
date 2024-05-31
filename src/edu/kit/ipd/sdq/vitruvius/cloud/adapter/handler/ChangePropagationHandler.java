package edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.ChangePropagationEndpoint;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.common.util.JsonMapper;

public class ChangePropagationHandler extends RequestHandler{
	private final VitruvClient client;

	public ChangePropagationHandler(VitruvClient client) {
	    super("/vsum/view");
	    this.client = client;
	}

	@Override
	public void init( JsonMapper mapper) {
	    this.patchEndpoint = new ChangePropagationEndpoint() ; // TODO delete this or remove from other handler
	}
}
