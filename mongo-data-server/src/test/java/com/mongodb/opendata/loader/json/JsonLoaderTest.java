package com.mongodb.opendata.loader.json;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;
import com.mongodb.opendata.repository.DatasetRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:infrastructure.xml")
public class JsonLoaderTest {
    
    @Autowired
    JsonLoader loader;
    
    @Autowired
    DatasetRepository datasetRepository;
    
    @Autowired
    Mongo mongo;

    @Test
    public void testLoadProductRecalls() throws IOException {
        String dbName = "product_recalls";
        String collectionName = "recalls";
        String recordsArrayName = "results";
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, dbName);
        mongoTemplate.dropCollection(collectionName);
        
        InputStream input = new ClassPathResource("recalls.json").getInputStream();
        loader.load(input, dbName, collectionName, recordsArrayName);
    }

}
