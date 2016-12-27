package com.stormphoenix.fishcollector.shared;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class KeyGenerator {

    public String generateModelKey(Object obj) {
        String className = obj.getClass().getName();
        return className.substring(0, 3).toUpperCase() + generateKeyByTime();
    }

    private String generateKeyByTime() {
        StringBuilder sb = new StringBuilder();
        return sb.append(DateUtils.getYear())
                .append(DateUtils.getMonNum())
                .append(DateUtils.getDay())
                .append(DateUtils.getHour())
                .append(DateUtils.getMinute())
                .append(DateUtils.getSec()).toString();
    }


}
