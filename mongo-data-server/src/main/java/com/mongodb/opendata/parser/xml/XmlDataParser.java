package com.mongodb.opendata.parser.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import com.mongodb.DBObject;
import com.mongodb.opendata.parser.AbstractDataParser;
import com.mongodb.opendata.parser.json.KeyCompressingJsonParser;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

/**
 * @TODO - keys are always compressed
 * @TODO - this parser doesn't actually stream, it reads the entire XML file into a buffer
 * 
 * @author mh
 */
public class XmlDataParser extends AbstractDataParser {

    protected String recordName;
    protected String recordNamespace;
    
    protected KeyCompressingJsonParser jsonParser;
    
    JsonXMLConfig config;

    public XmlDataParser() {
        init();
    }

    private void init() {
        config = new JsonXMLConfigBuilder()
        .autoArray(true)
        .autoPrimitive(true)
        .prettyPrint(true).build();
        
    }

    public List<DBObject> parse(InputStream input) throws IOException, XMLStreamException, FactoryConfigurationError {
        
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            /*
             * Create reader (XML).
             */
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
    
            /*
             * Create writer (JSON).
             */
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            
            /*
             * Copy events from reader to writer.
             */
            writer.add(reader);
            
            /*
             * Close reader/writer.
             */
            reader.close();
            writer.close();
        } finally {
            /*
             * As per StAX specification, XMLEventReader/Writer.close() doesn't close
             * the underlying stream.
             */
            output.close();
            input.close();
        }
        
        //logger.debug(output.toString());
        
        InputStream is = new ByteArrayInputStream(output.toByteArray());
        jsonParser = new KeyCompressingJsonParser();
        jsonParser.setRecordsArrayName(recordName);
        List<DBObject> records = jsonParser.parse(is);
        return records;

    }

    protected void add(String key, Object value) {
        logger.debug("add(): " + key + " : " + value);

    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public void setRecordNamespace(String recordNamespace) {
        this.recordNamespace = recordNamespace;
    }

    public Map<String, String> getKeyToCompressedKeyMap() {
        return jsonParser.getKeyToCompressedKeyMap();
    }

    public Map<String, String> getCompressedKeyToKeyMap() {
        return jsonParser.getCompressedKeyToKeyMap();
    }

}
