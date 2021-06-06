package com.ecommerce.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author ngoclt2
 */
public class JacksonHelper {

    private static ObjectMapper objectMapper = null;

    public static ObjectMapper getUnescapedInstance() {
        return getInstance(false);
    }

    private static ObjectMapper getInstance(boolean isEscaped) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static String getString(Map<String, Object> json, String key) {
        Object object = json.get(key);

        return (object == null)
                ? null
                : object.toString();
    }

    public static int getInt(Map<String, Object> json, String key) throws Exception {
        Object object = json.get(key);
        try {
            return object instanceof Number
                    ? ((Number) object).intValue()
                    : Integer.parseInt((String) object);
        } catch (Exception e) {
            throw new Exception("JSONObject[" + key + "] is not an int.");
        }
    }

    public static ArrayList getJSONArray(Map<String, Object> json, String key) throws Exception {
        Object object = json.get(key);
        if (object instanceof ArrayList) {
            return (ArrayList) object;
        }
        throw new Exception("JSONObject[" + key + "] is not a JSONArray.");
    }

    public static Map<String, Object> getOrderedJSONObject(Map<String, Object> json, String key) throws Exception {
        Object object = json.get(key);
        if (object instanceof Map) {
            return (Map<String, Object>) object;
        }
        throw new Exception("JSONObject[" + key + "] is not a JSONObject.");
    }
}
