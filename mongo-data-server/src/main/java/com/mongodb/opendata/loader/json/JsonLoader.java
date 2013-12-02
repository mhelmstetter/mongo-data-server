package com.mongodb.opendata.loader.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.opendata.loader.MetadataLoader;
import com.mongodb.opendata.parser.json.KeyCompressingJsonParser;

@Service
public class JsonLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonLoader.class);
    
    //@Autowired
    //MongoTemplate mongo;
    
    //@Autowired
    //MongoFactoryBean mongoFactory;
    
    @Autowired
    Mongo mongo;
    
    @Autowired 
    MetadataLoader metadataLoader;

    public void load(InputStream input, String dbName, String collectionName, String recordsArrayName) throws JsonParseException, IOException {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, dbName);
        
        KeyCompressingJsonParser parser = new KeyCompressingJsonParser();
        parser.setRecordsArrayName(recordsArrayName);
        
        List<DBObject> recallRecords = parser.parse(input);
        
        
        Set<String> keySet = parser.getKeyToCompressedKeyMap().keySet();
        
        metadataLoader.loadMetadata(keySet, dbName, collectionName);
        
        
        for (DBObject record : recallRecords) {
            mongoTemplate.getCollection(collectionName).insert(record);
            
        }
    }
}
