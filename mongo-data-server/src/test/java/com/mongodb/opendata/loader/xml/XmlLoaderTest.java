package com.mongodb.opendata.loader.xml;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.Mongo;
import com.mongodb.opendata.loader.MetadataLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:infrastructure.xml")
public class XmlLoaderTest {
    
    @Autowired
    XmlLoader loader;
    
    @Autowired
    Mongo mongo;
    
    @Autowired 
    MetadataLoader metadataLoader;

    @Test
    public void test() throws IOException, XMLStreamException, FactoryConfigurationError {
        
        String dbName = "hospital_readmission";
        String collectionName = "hospital_readmission";
        String recordName = "row";
        String recordNamespace = null;
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, dbName);
        mongoTemplate.dropCollection(collectionName);
        
        InputStream input = new ClassPathResource("HospitalReadmissionReduction.xml").getInputStream();
        
        loader.load(input, dbName, collectionName, recordName, recordNamespace);
        
        long recordCount = mongoTemplate.count(null, collectionName);
        assertEquals(10311, recordCount);
        
    }

}
