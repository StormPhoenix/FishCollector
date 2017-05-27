package com.stormphoenix.fishcollector.shared;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class KeyGenerator {

    public static String generateModelKey(String modelSimpleName) {
        return modelSimpleName.substring(0, 3).toUpperCase() + generateKeyByTime();
    }

    public static String generateGroupKey() {
        return "Group_" + ConfigUtils.getInstance().getUsername() + "_" + generateKeyByTime();
    }

    public static String generateModelKey(BaseModel obj) {
        String classSimpleName = obj.getClass().getSimpleName();
        return generateModelKey(classSimpleName);
    }

    private static String generateKeyByTime() {
        StringBuilder sb = new StringBuilder();
        return sb.append(DateUtils.getYear())
                .append(DateUtils.getMonNum())
                .append(DateUtils.getDay())
                .append(DateUtils.getHour())
                .append(DateUtils.getMinute())
                .append(DateUtils.getSec()).toString();
    }


}
