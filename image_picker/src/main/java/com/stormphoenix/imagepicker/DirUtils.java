package com.stormphoenix.imagepicker;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Phoenix on 2016/8/11.
 */
public class DirUtils {

    private static final String TAG = "DirUtils";

    public static boolean isSDCardOk() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getAppRootDir(Context context) {
        File f = new File(getAppRootDirPath(context));
        if (!f.exists()) {
            Log.e(TAG, "getAppRootDir: exists false");
            f.mkdirs();
        } else {
            Log.e(TAG, "getAppRootDir: exists true");
        }
        return f;
    }

    private static String getAppRootDirPath(Context context) {
        String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "fishDir";
        Log.e(TAG, "getAppRootDirPath: " + path);
        return path;
    }
}
