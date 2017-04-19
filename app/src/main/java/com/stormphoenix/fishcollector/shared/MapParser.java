package com.stormphoenix.fishcollector.shared;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 * <p>
 * 用于将 SharedPreferences 中的Key-value对转化成HashMap的形式
 * 用于将 HashMap 中的Key-value对转化成Json的形式
 */

public class MapParser {
    public static Map parseSp2Map(SharedPreferences sp) {
        Map<String, String> all = (Map<String, String>) sp.getAll();
        return all;
    }

    public static String parseMap2Json(Map<String, String> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public static Map parseJson2Map(String json) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(json, Map.class);
        return map;
    }

    public static void parseMap2Sp(Map<String, String> map, SharedPreferences sp) {
        SharedPreferences.Editor edit = sp.edit();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.apply();
        return;
    }
}
