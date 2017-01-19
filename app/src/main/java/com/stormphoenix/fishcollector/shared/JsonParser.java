package com.stormphoenix.fishcollector.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public class JsonParser {
    private static JsonParser instance;

    private Gson gson;

    public static JsonParser getInstance() {
        if (instance == null) {
            synchronized (JsonParser.class) {
                if (instance == null) {
                    instance = new JsonParser();
                }
            }
        }
        return instance;
    }

    private JsonParser() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public Object fromJson(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

    public String toJson(Object model) {
        return gson.toJson(model);
    }
}
