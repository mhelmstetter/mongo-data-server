package com.mongodb.opendata.parser.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.mongodb.DBObject;

public class JsonDataParserTest {
    
    private static final Logger logger = LoggerFactory.getLogger(JsonDataParserTest.class);
    
    @Test
    public void testSimple() throws IOException {
        File jsonFile = new ClassPathResource("simple.json").getFile();
        SimpleJsonParser loader = new SimpleJsonParser();
        
        List<DBObject> records = loader.parse(new FileInputStream(jsonFile));
        for (DBObject record : records) {
            logger.debug(record.toString());
        }
        assertEquals(1, records.size());
        DBObject record = records.get(0);
        assertEquals(2, record.keySet().size());
    }
    
    @Test
    @Ignore("Currently duplicate keys are not handled, this is probably ok?")
    public void testDuplicateKeys() throws IOException {
        InputStream input = new ClassPathResource("productRecallsDuplicateKeys.json").getInputStream();
        SimpleJsonParser loader = new SimpleJsonParser();
        loader.setRecordsArrayName("PRODUCT");
        List<DBObject> records = loader.parse(input);
        for (DBObject record : records) {
            logger.debug(record.toString());
        }
        assertEquals(6, records.size());
    }
    
    @Test
    public void testMulti() throws IOException {
        InputStream input = new ClassPathResource("productRecalls.json").getInputStream();
        SimpleJsonParser loader = new SimpleJsonParser();
        loader.setRecordsArrayName("PRODUCT");
        List<DBObject> records = loader.parse(input);
        for (DBObject record : records) {
            logger.debug(record.toString());
        }
        assertEquals(6, records.size());
        DBObject record = records.get(0);
        assertEquals(2, record.keySet().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testLoadJsonContainingMultipleRecords() throws IOException {
        File jsonFile = new ClassPathResource("recalls.json").getFile();
        SimpleJsonParser loader = new SimpleJsonParser();
        loader.setRecordsArrayName("results");
        List<DBObject> recallRecords = loader.parse(new FileInputStream(jsonFile));
        for (DBObject record : recallRecords) {
            logger.debug(record.toString());
            List<DBObject> subRecordsList = (List<DBObject>)record.get("records");
            assertTrue(subRecordsList.size() >= 1);
        }
        assertEquals(10, recallRecords.size());
        
    }
    
    @Test
    public void testArraysAndNestedObjects() throws IOException {
        File jsonFile = new ClassPathResource("arraysAndNested.json").getFile();
        SimpleJsonParser loader = new SimpleJsonParser();
        
        List<DBObject> records = loader.parse(new FileInputStream(jsonFile));
        for (DBObject record : records) {
            logger.debug(record.toString());
        }
        assertEquals(1, records.size());
        DBObject record = records.get(0);
        assertEquals(7, record.keySet().size());
    }

}
