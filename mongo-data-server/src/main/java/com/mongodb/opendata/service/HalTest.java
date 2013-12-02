package com.mongodb.opendata.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class HalTest {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		//ObjectMapper mapper = new ObjectMapper();
		
		JacksonXmlModule module = new JacksonXmlModule();
		// and then configure, for example:
		module.setDefaultUseWrapper(true);
		module.setXMLTextElementName("results");
		//XmlMapper xmlMapper = new XmlMapper();
		
		//TextNode rootNode = new TextNode("results");
		//Node node = mapper.readValue("<node id=\"id\">text</node>", Node.class);
		
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = CsvSchema.builder()
	            .addColumn("firstName")
	            .addColumn("lastName")
	            .addColumn("address")
	            .build();
		
		
		
		XmlMapper xmlMapper = new XmlMapper();
		ObjectNode rootNode = xmlMapper.createObjectNode();
		rootNode.put("firstName", "John");
		rootNode.put("lastName", "Doe");
		rootNode.with("address").put("street", "123 Main St.");
		System.out.println(xmlMapper.writer().withRootName("someOtherRootName").writeValueAsString(rootNode));
		
	}

}
