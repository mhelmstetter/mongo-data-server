package com.mongodb.opendata.parser.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

public class JsonToXmlTest {
    
    @Test
    public void testConvert() throws IOException, XMLStreamException, FactoryConfigurationError {
        JsonXMLConfig config = new JsonXMLConfigBuilder()
        .autoArray(true)
        .autoPrimitive(true)
        .prettyPrint(true)
        .build();
        
        InputStream input = new ClassPathResource("simple.xml").getInputStream();
        OutputStream output = System.out;
        
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
    }

}
