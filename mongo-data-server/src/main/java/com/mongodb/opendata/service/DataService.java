package com.mongodb.opendata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.opendata.domain.Collection;
import com.mongodb.opendata.domain.DatasetMapping;
import com.mongodb.opendata.repository.DatasetMappingRepository;

@Service
public class DataService {
	
	@Autowired
	DatasetMappingRepository datasetMappingRepository;
	
	public Collection findCollection(String dataSet, String collectionName) {
		DatasetMapping dataset = datasetMappingRepository.findOne(dataSet);
		for (Collection collection : dataset.getCollections()) {
			if (collection.getName().equals(collectionName)) {
				return collection;
			}
		}
		return null;
	}
	
	public void getXml() {
		
	}

}
