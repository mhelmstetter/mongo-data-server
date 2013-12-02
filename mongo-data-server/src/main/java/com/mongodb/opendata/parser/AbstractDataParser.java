package com.mongodb.opendata.parser;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class AbstractDataParser {
    
    protected static final Logger logger = LoggerFactory.getLogger(AbstractDataParser.class);
    
    protected LinkedList<Object> stack = new LinkedList<Object>();
    protected int recordCount = 0;
    protected ParseDataListener listener;

    public AbstractDataParser() {
        super();
    }

    @SuppressWarnings("unchecked")
    protected void add(String key, Object value) {
        logger.debug("add(): " + key + " : " + value);
        if (stack.isEmpty()) {
            stack.push(new BasicDBObject());
        } else {
            Object top = stack.getFirst();
            if (top instanceof DBObject) {
                if (value instanceof List) {
                    ((DBObject) top).put(key, value);
                    stack.push(value);
                } else if (value instanceof DBObject) {
                    ((DBObject) top).put(key, value);
                    stack.push(value);
                } else {
                    ((DBObject) top).put(key, value);
                }
    
            } else if (top instanceof List) {
                if (value instanceof List) {
                    ((List<Object>) top).add(value);
                    stack.push(value);
                } else if (value instanceof DBObject) {
                    ((List<Object>) top).add(value);
                    stack.push(value);
                } else {
                    ((List<Object>) top).add(value);
                }
            } else {
                throw new RuntimeException("Unexpected stack element " + top.getClass().getName());
            }
        }
    }

    protected void addRecord(DBObject top) {
        listener.recordComplete(top);
        recordCount++;
    }

}