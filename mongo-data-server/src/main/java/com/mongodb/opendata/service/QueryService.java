package com.mongodb.opendata.service;

import java.io.PrintWriter;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Service
public class QueryService {
	
	
	public void queryJson(String dataSet, String collection, PrintWriter writer) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB(dataSet);
		
		
		writer.println("{\"results\": [");
		DBCursor cursor = db.getCollection(collection).find(null).limit(1000);
		try {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				
				//writer.print(JSON.serialize(obj));
				writer.print(obj.toString());
			
				if (cursor.hasNext()) {
					writer.println(",");
				}
				
			}
		} finally {
			cursor.close();
		}
		
		writer.println("]}");
	}
	
	public void queryXml(String dataSet, String collection, PrintWriter writer) throws UnknownHostException {
		ObjectMapper mapper = new ObjectMapper();
		
		JacksonXmlModule module = new JacksonXmlModule();
		// and then configure, for example:
		module.setDefaultUseWrapper(false);
		module.setXMLTextElementName("results");
		XmlMapper xmlMapper = new XmlMapper(module);
		XmlMapper xml = new XmlMapper();
		//xml.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
		ObjectNode rootNode = mapper.createObjectNode();
	
		MongoClient client = new MongoClient();
		DB db = client.getDB(dataSet);
		
		
		writer.println("{\"results\": [");
		DBCursor cursor = db.getCollection(collection).find(null).limit(1000);
		try {
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				
				//writer.print(JSON.serialize(obj));
				writer.print(obj.toString());
			
				if (cursor.hasNext()) {
					writer.println(",");
				}
				
			}
		} finally {
			cursor.close();
		}
		
		writer.println("]}");
		

		
		rootNode.put("firstName", "Mark");
		rootNode.put("lastName", "Helmstetter");
		rootNode.with("address").put("street", "42882 Deleon Dr");
		
		
		
		//System.out.println(mapper.writeValueAsString(rootNode));
		
		//System.out.println(xml.writeValueAsString(rootNode));
	}

}
