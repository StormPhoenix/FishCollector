package com.stormphoenix.fishcollector.db;

import android.content.Context;
import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.DaoMaster;
import com.stormphoenix.fishcollector.mvp.model.beans.DaoSession;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSiteDao;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder.TAG;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DbManager {
    private DaoMaster.DevOpenHelper devOpenHelper = null;

    public DbManager(Context context) {
        devOpenHelper = new DaoMaster.DevOpenHelper(context, "fish-db");
    }

    public void saveMonitoringSite(MonitoringSite monitoringSite) {
        DaoSession daoSession = new DaoMaster(devOpenHelper.getWritableDatabase()).newSession();
        MonitoringSiteDao dao = daoSession.getMonitoringSiteDao();
        dao.save(monitoringSite);
    }

    public Long queryKeyByModelId(String modelId, String modelClassName) {
        Long result = null;
        String simpleName = modelClassName.substring(modelClassName.lastIndexOf('.') + 1);
        try {
            AbstractDao dao = getModelDao(simpleName);
            if (dao != null) {
                Class<?> modelClassInstance = Class.forName(modelClassName + "$Properties");
                Field modelIdField = modelClassInstance.getDeclaredField("ModelId");
                Property property = (Property) modelIdField.get(null);

                List<BaseModel> list = dao.queryBuilder()
                        .where(property.eq(modelId))
                        .list();
                if (list != null && !list.isEmpty()) {
                    BaseModel objResult = list.get(0);
                    result = objResult.getId();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    private AbstractDao getModelDao(String simpleName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "get" + simpleName + "Dao";
        if (DaoSession.class.getDeclaredMethod(methodName, (Class<?>[]) null) != null) {
            Method method = DaoSession.class.getDeclaredMethod(methodName, (Class<?>[]) null);
            DaoSession daoSession = new DaoMaster(devOpenHelper.getWritableDatabase()).newSession();
            AbstractDao dao = (AbstractDao) method.invoke(daoSession, null);
            return dao;
        } else {
            return null;
        }
    }

    public void delete(BaseModel obj) {
        String simpleName = obj.getClass().getSimpleName();
        String modelClassName = obj.getClass().getName();
        Long result = null;
        try {
            Log.e(TAG, "save: " + modelClassName);
            AbstractDao modelDao = getModelDao(simpleName);
            if (modelDao != null) {
                Log.e(TAG, "save: not null");
                modelDao.delete(obj);
                Log.e(TAG, "delete ");
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "save: " + e.toString());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "save: " + e.toString());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "save: " + e.toString());
        }
    }
    /**
     * 存储一个 Model对象，并返回该对象的id值
     *
     * @param obj
     * @return
     */
    public Long save(BaseModel obj) {
        String simpleName = obj.getClass().getSimpleName();
        String modelClassName = obj.getClass().getName();
        Long result = null;
        try {
            Log.e(TAG, "save: " + modelClassName);
            AbstractDao modelDao = getModelDao(simpleName);
            if (modelDao != null) {
                Log.e(TAG, "save: not null");
                modelDao.save(obj);
                Log.e(TAG, "hello ");
                Log.e(TAG, "hello " + obj.getId());
                Log.e(TAG, "hello ");
                result = obj.getId();
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "save: " + e.toString());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "save: " + e.toString());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "save: " + e.toString());
        } finally {
            return result;
        }
    }

    public List<MonitoringSite> queryAll() {
        DaoSession daoSession = new DaoMaster(devOpenHelper.getReadableDatabase()).newSession();
        MonitoringSiteDao dao = daoSession.getMonitoringSiteDao();
        return dao.queryBuilder().build().list();
    }
}
