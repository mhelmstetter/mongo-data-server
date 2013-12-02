package com.mongodb.opendata.parser.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.opendata.parser.AbstractDataParser;
import com.mongodb.opendata.parser.ParseDataListener;

public abstract class JsonDataParser extends AbstractDataParser {

    JsonFactory f = new JsonFactory();
    JsonParser jp;

    String recordsArrayName;
    // List<DBObject> records = new ArrayList<DBObject>();
    JsonToken currentToken;

    int objDepth = 0;
    int skipDepth = 0;

    protected void parse(InputStream input, ParseDataListener listener) throws JsonParseException, IOException {
        this.listener = listener;
        jp = f.createParser(input);

        currentToken = jp.nextToken();
        if (currentToken != JsonToken.START_OBJECT) {
            throw new JsonParseException("JSON root should be object", jp.getCurrentLocation());
        }

        parseCurrent(jp);

        if (recordsArrayName != null) {
            while (skipDepth-- < objDepth) {
                logger.debug(skipDepth + " " + objDepth);
                Object foo = stack.pop();
                logger.debug(foo + "");
            }
        }
        logger.debug("************ " + recordCount + " records");

    }

    private void parseCurrent(JsonParser jp) throws JsonParseException, IOException {
        
        while (currentToken != null) {
            String key = jp.getCurrentName();
            
            switch (currentToken) {
            case START_OBJECT:
                logger.debug("START_OBJECT: " + key);
                objDepth++;
                add(key, new BasicDBObject());
                break;
            case START_ARRAY:
                if (recordsArrayName != null && jp.getCurrentName().equals(recordsArrayName)) {
                    skipDepth = objDepth;
                }
                // logger.debug("[ Start array " + key);
                List<Object> list = new ArrayList<Object>();
                add(key, list);

                break;
            case END_OBJECT:
                logger.debug("END_OBJECT " + key + " skipDepth: " + skipDepth + " " + objDepth + " recordCount: "
                        + recordCount);
                objDepth--;
                Object top = stack.pop();
                if (objDepth == skipDepth) {
                    addRecord((DBObject) top);
                }
                break;
            case END_ARRAY:
                // logger.debug("End array " + key + " ]");
                if (!key.equals(recordsArrayName)) {
                    stack.pop();
                } else {
                    // return;
                }
                break;
            case FIELD_NAME:
                break;
            case VALUE_STRING:
                add(key, jp.getText());
                break;
            case VALUE_NUMBER_INT:
                add(key, jp.getIntValue());
                break;
            case VALUE_NUMBER_FLOAT:
                add(key, jp.getFloatValue());
                break;
            case VALUE_NULL:
                add(key, null);
                break;
            case VALUE_TRUE:
            case VALUE_FALSE:
                add(key, jp.getBooleanValue());
                break;
            default:
                logger.debug("Unhandled token: " + currentToken.name());
            }
            currentToken = jp.nextToken();
        }
    }

    public String getRecordsArrayName() {
        return recordsArrayName;
    }

    public void setRecordsArrayName(String recordsArrayName) {
        this.recordsArrayName = recordsArrayName;
    }

}
