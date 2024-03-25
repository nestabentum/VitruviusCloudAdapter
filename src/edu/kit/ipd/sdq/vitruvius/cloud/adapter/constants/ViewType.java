package edu.kit.ipd.sdq.vitruvius.cloud.adapter.constants;

import edu.kit.ipd.sdq.metamodels.families.FamiliesPackage;
import edu.kit.ipd.sdq.metamodels.persons.PersonsPackage;

public enum ViewType {
	persons(PersonsPackage.eNAME, "persons"), families(FamiliesPackage.eNAME, "families");

	private final String epackageName;
	private final String fileEnding;

	private ViewType(String epackageName, String fileEnding) {
		this.epackageName = epackageName;
		this.fileEnding = fileEnding;
	}

	public String getEpackageName() {
		return this.epackageName;
	}
	public String getFileEnding() {
		return this.fileEnding;
	}
	
}
