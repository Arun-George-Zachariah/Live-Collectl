package edu.missouri.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommonUtil {
    private static CommonUtil instance = null;

    private CommonUtil() {

    }

    public static CommonUtil getInstance() {
        if(instance == null) {
            instance = new CommonUtil();
        }
        return instance;
    }

    public static Map<String, String> createMap(String[] header , String[] data) {
        Map<String, String> map = new LinkedHashMap();
        for(int i=0; i<header.length; i++) {
            map.put(header[i], data[i]);
        }
        return map;
    }
}
