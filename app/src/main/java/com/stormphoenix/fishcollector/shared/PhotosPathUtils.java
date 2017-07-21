package com.stormphoenix.fishcollector.shared;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public class PhotosPathUtils {
    public static String mergePhotosPath(String[] paths) {
        String result = null;
        for (String path : paths) {
            result = appendPath(result, path);
        }
        return result;
    }

    public static String[] processPhotosPath(String path) {
        if (path == null) {
            return null;
        }
          /*
             * 一定要注意检测一下　ｐａｔｈｓ里面保存的路径是否存在，如果不存在就要去掉
             * 之所以这样做是为了防止一些意外情况，比如照片被删除了，但是数据库保存的路径却没有改变，
             * 这样在写入服务端的时候，数据就会奔溃
              */
        String[] paths = path.split("&");
        List<String> result = new ArrayList<>();
        for (String tempPath : paths) {
            if (new File(tempPath).exists()) {
                result.add(tempPath);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    public static String appendPath(String rawStr, String prefix) {
        if (rawStr == null) {
            return prefix;
        } else {
            return rawStr + "&" + prefix;
        }
    }
}
