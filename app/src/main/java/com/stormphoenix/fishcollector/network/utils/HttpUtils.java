package com.stormphoenix.fishcollector.network.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Developer on 16-12-21.
 * Wang Cheng is a intelligent Android developer.
 */

public class HttpUtils {
    public static RequestBody createStringBody(String content) {
        return RequestBody.create(MediaType.parse("text/*"), content);
    }

    public static Map<String, RequestBody> createMultFileMap(List<File> imageFiles) {
        Map<String, RequestBody> map = new HashMap<>();
        for (File file : imageFiles) {
            map.put("file\";filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        return map;
    }
}
