package com.mongodb.opendata.loader.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.opendata.loader.MetadataLoader;
import com.mongodb.opendata.parser.xml.XmlDataParser;

@Service
public class XmlLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(XmlLoader.class);
    
    @Autowired
    Mongo mongo;
    
    @Autowired 
    MetadataLoader metadataLoader;

    public void load(InputStream input, String dbName, String collectionName, String recordName, String recordNamespace) throws IOException, XMLStreamException, FactoryConfigurationError {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, dbName);
        
        XmlDataParser parser = new XmlDataParser();
        parser.setRecordName(recordName);
        parser.setRecordNamespace(recordNamespace);
        
        List<DBObject> recallRecords = parser.parse(input);
        
        Set<String> keySet = parser.getKeyToCompressedKeyMap().keySet();
        
        metadataLoader.loadMetadata(keySet, dbName, collectionName);
        
        
        for (DBObject record : recallRecords) {
            mongoTemplate.getCollection(collectionName).insert(record);
            
        }
    }
}
