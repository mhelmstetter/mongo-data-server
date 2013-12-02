package com.mongodb.opendata.parser.xml;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.mongodb.DBObject;

public class XmlDataParserTest {
    
    private static final Logger logger = LoggerFactory.getLogger(XmlDataParserTest.class);
    
    @Test
    public void testParseNestedRecordElementsWithSameName() throws IOException, XMLStreamException {
        XmlDataParser parser = new XmlDataParser();
        parser.setRecordName("row");
        InputStream input = new ClassPathResource("HospitalReadmissionReduction_small.xml").getInputStream();
        List<DBObject> records = parser.parse(input);
        assertEquals(5, records.size());
    }
    
    @Test
    public void testParseSimple() throws IOException, XMLStreamException {
        XmlDataParser parser = new XmlDataParser();
        InputStream input = new ClassPathResource("simple.xml").getInputStream();
        List<DBObject> recallRecords = parser.parse(input);
        logger.debug(recallRecords.get(0).toString());
    }

    @Test
    public void testParseWithDefaultOptions() throws IOException, XMLStreamException {
        XmlDataParser parser = new XmlDataParser();
        parser.setRecordName("PRODUCT");
        List<DBObject> recallRecords = parse(parser, "RecallsDataSet_small.xml");
        assertEquals(6, recallRecords.size());
    }
    
    @Test
    public void testParseWithRecordNameOption() throws IOException, XMLStreamException {
        XmlDataParser parser = new XmlDataParser();
        parser.setRecordName("PRODUCT");
        List<DBObject> recallRecords = parse(parser, "RecallsDataSet_small.xml");
        assertEquals(6, recallRecords.size());
    }
    
    @Test
    public void testParseWithNestedRecord() throws IOException, XMLStreamException {
        XmlDataParser parser = new XmlDataParser();
        parser.setRecordName("PRODUCT");
        List<DBObject> recallRecords = parse(parser, "RecallsDataSet_nested.xml");
        assertEquals(4, recallRecords.size());
        logger.debug(recallRecords.get(2).toString());
    }
    
    private List<DBObject> parse(XmlDataParser parser, String filename) throws IOException, XMLStreamException {
        InputStream input = new ClassPathResource(filename).getInputStream();
        List<DBObject> recallRecords = parser.parse(input);
        
        for (DBObject record : recallRecords) {
            logger.debug(record.toString());

        }
        
        assertEquals("Expected 1st record to have 2 fields", 2, recallRecords.get(0).keySet().size());
        assertEquals("Expected 2nd record to have 7 fields", 7, recallRecords.get(1).keySet().size());
        
        DBObject second = recallRecords.get(1);
        int keyNum = 1;
        for (String key : second.keySet()) {
            Integer value = (Integer)second.get(key);
            assertEquals((Integer)keyNum++, value);
        }
        

        return recallRecords;
    }

}
