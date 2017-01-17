package com.stormphoenix.fishcollector.shared;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public class PicturePathUtils {
    public static String[] processPicturePath(String path) {
        if (path == null) {
            return null;
        }
        return path.split("&");
    }

    public static String appendPath(String rawStr, String prefix) {
        if (rawStr == null) {
            return prefix;
        } else {
            return rawStr + "&" + prefix;
        }
    }
}
