package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AllowAllOriginsFilter extends Filter {

	@Override
	public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
		exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
		exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "DELETE, POST, GET, OPTIONS"); // TODO check this
		exchange.getResponseHeaders().add("Access-Control-Allow-Headers",
				"Content-Type, Authorization, X-Requested-With, View-Type, selector-uuid"); // TODO check custom header conventions
		exchange.getResponseHeaders().add("Access-Control-Expose-Headers", "selector-uuid, view-uuid");
		if ("OPTIONS".equals(exchange.getRequestMethod())) {
			exchange.sendResponseHeaders(204, -1);
			return;
		}

		chain.doFilter(exchange);
	}

	@Override
	public String description() {
		return "Allows all origins";
	}

}
