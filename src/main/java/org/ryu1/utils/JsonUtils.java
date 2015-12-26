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

    /**
     * Object (Entity) を JSON形式の文字列に変換する.
     *
     * @param object
     *        変換前Object
     * @return JSON文字列
     * @throws IOException
     *         入出力例外
     * @throws JsonMappingException
     *         JSONマッピング例外
     * @throws JsonGenerationException
     *         JSON変換例外
     */
    public static String object2Json(final Object object)
            throws JsonGenerationException, JsonMappingException, IOException {
        if (object == null) {
            return null;
        }

        // デバッグログ 変換前
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("変換前オブジェクト[%s]",
                    ReflectionToStringBuilder.toString(object)));
        }

        // JacksonでObjectからJsonに変換
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /**
     * JSON文字列をObject (Entity) に変換する.
     *
     * @param <T>
     *        type
     * @param json
     *        JSON文字列
     * @param valueType
     *        変換オブジェクト(型)
     * @return 変換後のオブジェクト
     * @throws IOException
     *         入出力例外
     * @throws JsonMappingException
     *         JSONマッピング例外
     * @throws JsonParseException
     *         JSON変換例外
     */
    public static <T> T
    json2Object(final String json, final Class<T> valueType)
            throws JsonParseException, JsonMappingException,
            IOException {
        if (StringUtils.isEmpty(json) || valueType == null) {
            return null;
        }

        // デバッグログ 変換前JSON
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("変換前JSON[%s]", json));
        }

        // JacksonでJsonからObjectに変換
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(json, valueType);
    }

    /**
     * MapオブジェクトをJSON形式の文字列に変換する.
     *
     * @param map
     *        Mapオブジェクト
     * @return JSON文字列
     * @throws IOException
     *         入出力例外
     * @throws JsonMappingException
     *         JSONマッピング例外
     * @throws JsonGenerationException
     *         JSON変換例外
     */
    public static String map2Json(final Map<String, Object> map)
            throws JsonGenerationException, JsonMappingException, IOException {
        if (map == null) {
            return null;
        }

        // デバッグログ 変換前Map
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("変換前Map[%s]", map));
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(map);
    }

    /**
     * JSON形式の文字列をMapオブジェクトに変換する.
     *
     * @param json
     *        JSON文字列
     * @return Mapオブジェクト
     * @throws IOException
     *         入出力例外
     * @throws JsonMappingException
     *         JSONマッピング例外
     * @throws JsonParseException
     *         JSON変換例外
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(final String json)
            throws JsonParseException, JsonMappingException, IOException {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        // デバッグログ
        if (logger.isDebugEnabled()) {
            logger.debug("変換前JSON[%s]", json);
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new HashMap<String, Object>().getClass());
    }
    
    private JsonUtils() {
    }
    
}
