package com.stormphoenix.fishcollector.shared;

import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Developer on 16-12-29.
 * Wang Cheng is a intelligent Android developer.
 */

public class ModelUtils {
    private static final String TAG = "ModelUtils";

    public static Long getId(Object obj) {
        Method getIdMethod = null;
        Long result = null;
        try {
            getIdMethod = obj.getClass().getDeclaredMethod("getId", (Class<?>[]) null);
            result = (Long) getIdMethod.invoke(obj, (Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static void setId(Object obj, Long id) {
        Method getIdMethod = null;
        try {
            getIdMethod = obj.getClass().getDeclaredMethod("setId", Long.class);
            getIdMethod.invoke(obj, id);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getModelId(BaseModel obj) {
        return obj.getModelId();
    }

    public static BaseModel createModelObject(Long foreignKey, String modelClassName) {
        BaseModel modelObj = null;
        try {
            Class<BaseModel> modelClass = modelClass = (Class<BaseModel>) Class.forName(modelClassName);
            Method foreignKeySetter = modelClass.getDeclaredMethod("setForeignKey", Long.class);
            Method modelIdSetter = modelClass.getDeclaredMethod("setModelId", String.class);

            if (foreignKeySetter != null) {
                modelObj = modelClass.newInstance();
                modelObj.setForeignKey(foreignKey);
                modelObj.setModelId(KeyGenerator.generateModelKey(modelClassName.substring(modelClassName.lastIndexOf('.') + 1)));
            } else {
                Log.e(TAG, "createModelObject: setter is null");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return (BaseModel) modelObj;
        }
    }

    public static Long getForeignKey(Object obj) {
        return null;
    }
}
