package edu.kit.ipd.sdq.vitruvius.cloud.adapter.cache;

import java.util.HashMap;
import java.util.Map;

import tools.vitruv.framework.views.View;

public class Cache {
	public static final Map<String, View> viewCache = new HashMap<>();

	public static void addView(String uuid, View view) {
		viewCache.put(uuid, view);
	}

	public static View getView(String uuid) {
		return viewCache.get(uuid);
	}

	public static View removeView(String uuid) {
		return viewCache.remove(uuid);
	}
}
