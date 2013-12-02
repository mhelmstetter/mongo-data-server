package com.mongodb.opendata.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class KeyCompressionUtils {
    
    public static Map<String, String> getKeyToCompressedKeyMap(Collection<String> keys) {
        BiMap<String, String> tempMap = HashBiMap.create();
        for (String key : keys) {
           addCompressedKeyToCompressedKeyToKeyMap(key, tempMap, 1);
        }
        tempMap = tempMap.inverse();
        
        LinkedHashMap<String, String> compressedKeyToKeyMap = new LinkedHashMap<String, String>();
        for (String key : keys) {
            compressedKeyToKeyMap.put(key, tempMap.get(key));
        }
        return compressedKeyToKeyMap;
    }
    
    public static Map<String, String> getCompressedKeyToKeyMap(Collection<String> keys) {
        LinkedHashMap<String, String> compressedKeyToKeyMap = new LinkedHashMap<String, String>();
        for (String key : keys) {
            addKeyToCompressedKeyToKeyMap(key, compressedKeyToKeyMap);
        }
        return compressedKeyToKeyMap;
    }
    
    public static DBObject decompressKeys(DBObject compressedKeyDbo, Map<String, String> compressedKeyToKeyMap) {
        BasicDBObject decompressedKeyDbo = new BasicDBObject();
        
        for (String key : compressedKeyToKeyMap.keySet()) {
            String decompressedKey = compressedKeyToKeyMap.get(key);
            decompressedKeyDbo.put(decompressedKey, compressedKeyDbo.get(key));
        }
        
        return decompressedKeyDbo;
    }
    
    public static String addKeyToCompressedKeyToKeyMap(String key, Map<String, String> compressedKeyToKeyMap) {
        return addCompressedKeyToCompressedKeyToKeyMap(key, compressedKeyToKeyMap, 1);
    }
    
    private static String addCompressedKeyToCompressedKeyToKeyMap(String key, Map<String, String> compressedKeyToKeyMap, int keyLength) {
        String fullKey = DigestUtils.md5Hex(key);
        String compressedKey = null;
        String existingValueWithSameCompressedKey = null;
        
        while (keyLength < fullKey.length()) {
        
            compressedKey = fullKey.substring(0, keyLength++);
            existingValueWithSameCompressedKey = compressedKeyToKeyMap.get(compressedKey);
            if (existingValueWithSameCompressedKey != null) {
                compressedKeyToKeyMap.remove(compressedKey);
                addCompressedKeyToCompressedKeyToKeyMap(existingValueWithSameCompressedKey, compressedKeyToKeyMap, keyLength);
            } else {
                compressedKeyToKeyMap.put(compressedKey, key);
                break;
            }
        }
        return compressedKey;
    }

}
