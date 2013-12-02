package com.mongodb.opendata.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DatasetMapping {
	
	@Id
	private String name;
	
	private String displayName;
	
	private String description;
	
	
	
	private List<Collection> collections = new ArrayList<Collection>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	
	public void addCollection(Collection collection) {
		collections.add(collection);
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Collection> getCollections() {
		return collections;
	}

	public void setCollections(List<Collection> collections) {
		this.collections = collections;
	}

}
