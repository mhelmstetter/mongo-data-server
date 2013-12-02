package com.mongodb.opendata.util;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

public class KeyCompressionUtilsTest {

    @Test
    @Ignore("Java key compression is not identical to the Clojure algorithm, in cases where there are several keys with similar prefixes the compressed keys differ")
    public void testKeyCompressionWithSimilarHashedKeys() {

        List<String> dimensions = Arrays.asList("yours", "warns", "wore", "wrong", "wire", "wide", "worthwhile");
        Map<String, String> map = KeyCompressionUtils.getKeyToCompressedKeyMap(dimensions);
        assertArrayEquals(map.keySet().toArray(), dimensions.toArray());
        List<String> expectedValues = Arrays.asList("2e", "2bfc", "2bf4", "2bd", "b8", "b71e", "b71f");
        assertArrayEquals(map.values().toArray(), expectedValues.toArray());
        System.out.println(map);

    }

    @Test
    public void testKeyCompression() {

        List<String> dimensions = Arrays.asList("state", "race", "origin", "sex", "population_2010",
                "population_estimate_2010", "population_estimate_2011");
        Map<String, String> map = KeyCompressionUtils.getKeyToCompressedKeyMap(dimensions);
        assertArrayEquals(map.keySet().toArray(), dimensions.toArray());
        List<String> expectedValues = Arrays.asList("9", "2", "7", "3c", "0", "f", "34");
        assertArrayEquals(map.values().toArray(), expectedValues.toArray());
        System.out.println(map);

        dimensions = Arrays.asList("age", "division", "state", "population_2010", "sex", "race", "region",
                "population_estimate_2011", "population_estimate_2010", "origin");
        map = KeyCompressionUtils.getKeyToCompressedKeyMap(dimensions);
        assertArrayEquals(map.keySet().toArray(), dimensions.toArray());
        expectedValues = Arrays.asList("7d", "5", "9e", "0", "3c", "2", "96", "34", "f", "7c");
        assertArrayEquals(map.values().toArray(), expectedValues.toArray());
        System.out.println(map);

    }

}
