package edu.kit.ipd.sdq.vitruvius.cloud.adapter.handler;

import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.ChangePropagationEndpoint;
import edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint.ViewEndpoint;
import tools.vitruv.framework.remote.client.VitruvClient;
import tools.vitruv.framework.remote.common.util.JsonMapper;


public class ViewHandler extends RequestHandler{
private final VitruvClient client;

public ViewHandler(VitruvClient client) {
    super("/vsum/view");
    this.client = client;
}

@Override
public void init( JsonMapper mapper) {
    this.postEndpoint = new ViewEndpoint(client) ;
    this.patchEndpoint = new ChangePropagationEndpoint();
}
}