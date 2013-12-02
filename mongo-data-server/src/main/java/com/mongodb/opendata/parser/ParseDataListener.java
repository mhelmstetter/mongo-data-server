package com.mongodb.opendata.parser;

import com.mongodb.DBObject;

public interface ParseDataListener {
    
    public void recordComplete(DBObject record);

}
