package com.enagorik.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Hand-rolled JSON utility for zero external dependencies.
 * Handles basic serialization for the frontend payloads.
 */
public class JsonUtil
{
    /**
     * Extremely simple JSON parser for flat JSON objects like:
     * {"citizenId": 1, "serviceId": 2}
     * Returns a map of string keys to string values.
     */
    public static Map<String, String> parseJson(String json)
    {
        Map<String, String> result = new HashMap<>();
        if (json == null || json.trim().isEmpty()) return result;
        
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
        
        String[] pairs = json.split(",");
        for (String pair : pairs)
        {
            String[] kv = pair.split(":", 2);
            if (kv.length == 2)
            {
                String key = kv[0].replaceAll("\"", "").trim();
                String value = kv[1].trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * Extremely simple object-to-JSON serializer.
     */
    public static String toJson(Object obj)
    {
        if (obj == null) return "null";
        if (obj instanceof String || obj instanceof LocalDateTime || obj.getClass().isEnum()) {
            return "\"" + escapeString(obj.toString()) + "\"";
        }
        if (obj instanceof Number || obj instanceof Boolean) {
            return obj.toString();
        }
        if (obj instanceof Collection) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (Object item : (Collection<?>) obj) {
                if (!first) sb.append(",");
                sb.append(toJson(item));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        }
        if (obj instanceof Map) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                if (!first) sb.append(",");
                sb.append("\"").append(entry.getKey()).append("\":").append(toJson(entry.getValue()));
                first = false;
            }
            sb.append("}");
            return sb.toString();
        }
        if (obj.getClass().isArray()) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            if (obj instanceof String[]) {
                for (String item : (String[]) obj) {
                    if (!first) sb.append(",");
                    sb.append(toJson(item));
                    first = false;
                }
            }
            sb.append("]");
            return sb.toString();
        }

        // POJO
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        Class<?> clazz = obj.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (!first) sb.append(",");
                    sb.append("\"").append(field.getName()).append("\":").append(toJson(value));
                    first = false;
                } catch (IllegalAccessException e) {
                    // ignore
                }
            }
            clazz = clazz.getSuperclass();
        }
        sb.append("}");
        return sb.toString();
    }
    
    private static String escapeString(String s) {
        return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
