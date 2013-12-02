package com.mongodb.opendata.domain;

import java.util.ArrayList;
import java.util.List;

public class Collection {
	
	private String name;
	private String displayName;
	private String description;
	
	private List<String> fields = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void addField(String name) {
		fields.add(name);
	}
	
	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	

}
