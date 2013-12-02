package com.mongodb.opendata.parser.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.opendata.parser.ParseDataListener;
import com.mongodb.opendata.util.KeyCompressionUtils;

/**
 * 
 * 
 *
 */
public class KeyCompressingJsonParser extends JsonDataParser {
    
    
    Map<String, String> keyToCompressedKeyMap;
    Map<String, String> compressedKeyToKeyMap;
    
    public List<DBObject> parse(InputStream input) throws JsonParseException, IOException {
        final List<DBObject> records = new ArrayList<DBObject>();
        this.parse(input, new ParseDataListener() {

            @Override
            public void recordComplete(DBObject record) {
                BasicDBObject compressedKeyDbo = new BasicDBObject();
                
                if (keyToCompressedKeyMap == null) {
                    Set<String> keys = record.keySet();
                    keyToCompressedKeyMap = KeyCompressionUtils.getKeyToCompressedKeyMap(keys);
                    compressedKeyToKeyMap = KeyCompressionUtils.getCompressedKeyToKeyMap(keys);
                }
                
                
                for (String key : record.keySet()) {
                    String compressedKey = keyToCompressedKeyMap.get(key);
                    if (compressedKey == null) {
                        compressedKey = KeyCompressionUtils.addKeyToCompressedKeyToKeyMap(key, compressedKeyToKeyMap);
                        compressedKeyToKeyMap.put(compressedKey, key);
                    }
                    compressedKeyDbo.put(compressedKey, record.get(key));
                }
                

                records.add(compressedKeyDbo);
            } });
        return records;
    }

    public Map<String, String> getKeyToCompressedKeyMap() {
        return keyToCompressedKeyMap;
    }

    public Map<String, String> getCompressedKeyToKeyMap() {
        return compressedKeyToKeyMap;
    }

}
