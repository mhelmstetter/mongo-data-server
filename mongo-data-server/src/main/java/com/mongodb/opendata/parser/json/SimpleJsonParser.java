package com.mongodb.opendata.parser.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.mongodb.DBObject;
import com.mongodb.opendata.parser.ParseDataListener;

/**
 * 
 * 
 *
 */
public class SimpleJsonParser extends JsonDataParser {
    
    
    public List<DBObject> parse(InputStream input) throws JsonParseException, IOException {
        final List<DBObject> records = new ArrayList<DBObject>();
        this.parse(input, new ParseDataListener() {

            @Override
            public void recordComplete(DBObject record) {
                records.add(record);
            } });
        return records;
    }

}
