package com.stormphoenix.fishcollector.shared;

import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by StormPhoenix on 17-6-8.
 * StormPhoenix is a intelligent Android developer.
 */

public class ReflectUtils {
    public static final String TAG = ReflectUtils.class.getSimpleName();

    public static void appendModelPhotoPath(BaseModel model, String path) {
        String result = null;
        try {
            // 利用反射查找 getMethod方法
            Method getPicMethod = model.getClass().getMethod("getPhoto", (Class[]) null);
            result = (String) getPicMethod.invoke(model, null);
            result = PhotosPathUtils.appendPath(result, path);
            setModelPhotoPaths(model, result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "uploadPhotos: " + e.toString());
        }
    }

    public static void setModelPhotoPaths(BaseModel model, String paths) {
        try {
            // 利用反射查找 getMethod方法
            Method setPhotoMethod = model.getClass().getMethod("setPhoto", String.class);
            setPhotoMethod.invoke(model, paths);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "uploadPhotos: " + e.toString());
        }
    }

    public static void setModelPhotoPaths(BaseModel model, String[] paths) {
        setModelPhotoPaths(model, PhotosPathUtils.mergePhotosPath(paths));
    }

    public static String[] getModelPhotoPaths(BaseModel model) {
        String[] paths = null;
        try {
            // 利用反射查找 getMethod方法
            Method getPicMethod = model.getClass().getMethod("getPhoto", (Class[]) null);
            String path = (String) getPicMethod.invoke(model, null);
            paths = PhotosPathUtils.processPhotosPath(path);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, "uploadPhotos: " + e.toString());
        }
        return paths;
    }
}
