/**
 * Copyright(C) 2014/08/27.
 * NES Corporation All rights reserved.
 */
package org.ryu1.utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json Utilities
 * 
 * @since 2014
 * @version 1.0
 * @author Ryu
 */
public class JsonUtils {
    
    /**
     * Json形式の文字列に変換する
     * 
     * @param map 変換対象であるMapオブジェクト
     * @return jsonStr Json String
     * @throws java.io.IOException 変換エラー
     * @since 2014
     */
    public static String toJsonString(final Map<String, Object> map) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(map);
        return jsonStr;
    }
    
    /**
     * Linked Hash Map形式に変換する
     * 
     * @param jsonStr 変換対象であるJson形式の文字列
     * @return map Map
     * @throws java.io.IOException 変換エラー
     * @since 2014
     */
    public static Map<String, Object> toLinkedHashMap(final String jsonStr) throws IOException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        map = mapper.readValue(jsonStr, new TypeReference<LinkedHashMap<String, Object>>() {
        });
        return map;
    }
    
    private JsonUtils() {
    }
    
}
