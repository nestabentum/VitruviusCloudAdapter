package edu.kit.ipd.sdq.vitruvius.cloud.adapter.view;

import java.util.HashMap;
import java.util.Map;

import tools.vitruv.framework.views.View;

public class Cache {
	private static Map<String, View> viewMap = new HashMap<String,View>();

	private Cache() {

	}
	
	public static void addView(String id, View view) {
		viewMap.put(id, view);
	}
}
