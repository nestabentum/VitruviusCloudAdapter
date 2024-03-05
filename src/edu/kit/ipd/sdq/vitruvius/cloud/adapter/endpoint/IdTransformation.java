package edu.kit.ipd.sdq.vitruvius.cloud.adapter.endpoint;

import java.util.List;

import org.eclipse.emf.common.util.URI;

import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.framework.remote.common.deserializer.IdTranformer;

public class IdTransformation implements IdTranformer{

	@Override
	public void allToLocal(List<? extends EChange<HierarchicalId>> eChanges) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void allToGlobal(List<? extends EChange<HierarchicalId>> eChanges) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public URI toGlobal(URI local) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI toLocal(URI global) {
		// TODO Auto-generated method stub
		return null;
	}

}
