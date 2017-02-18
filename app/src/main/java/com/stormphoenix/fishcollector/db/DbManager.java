package com.stormphoenix.fishcollector.db;

import android.content.Context;
import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.Benthos;
import com.stormphoenix.fishcollector.mvp.model.beans.CatchTools;
import com.stormphoenix.fishcollector.mvp.model.beans.Catches;
import com.stormphoenix.fishcollector.mvp.model.beans.DaoMaster;
import com.stormphoenix.fishcollector.mvp.model.beans.DaoSession;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantBenthosSpecies;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantPhytoplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantZooplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.model.beans.FishEggs;
import com.stormphoenix.fishcollector.mvp.model.beans.Fishes;
import com.stormphoenix.fishcollector.mvp.model.beans.FractureSurface;
import com.stormphoenix.fishcollector.mvp.model.beans.MeasurePoint;
import com.stormphoenix.fishcollector.mvp.model.beans.MeasuringLine;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSiteDao;
import com.stormphoenix.fishcollector.mvp.model.beans.Phytoplankton;
import com.stormphoenix.fishcollector.mvp.model.beans.Sediment;
import com.stormphoenix.fishcollector.mvp.model.beans.WaterLayer;
import com.stormphoenix.fishcollector.mvp.model.beans.Zooplankton;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public void deleteAll() {
        DaoSession daoSession = new DaoMaster(devOpenHelper.getWritableDb()).newSession();
        daoSession.deleteAll(DominantBenthosSpecies.class);
        daoSession.deleteAll(DominantPhytoplanktonSpecies.class);
        daoSession.deleteAll(DominantZooplanktonSpecies.class);
        daoSession.deleteAll(Benthos.class);
        daoSession.deleteAll(Zooplankton.class);
        daoSession.deleteAll(Phytoplankton.class);
        daoSession.deleteAll(Sediment.class);
        daoSession.deleteAll(FishEggs.class);
        daoSession.deleteAll(Fishes.class);
        daoSession.deleteAll(Catches.class);
        daoSession.deleteAll(CatchTools.class);
        daoSession.deleteAll(WaterLayer.class);
        daoSession.deleteAll(MeasurePoint.class);
        daoSession.deleteAll(MeasuringLine.class);
        daoSession.deleteAll(FractureSurface.class);
        daoSession.deleteAll(MonitoringSite.class);
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

    public void saveModels(List<MonitoringSite> models) {
        for (MonitoringSite monitoringSite : models) {
            saveMonitoringSite(monitoringSite);
            List<List<BaseModel>> childrenModelsList = getChildrenModels(monitoringSite);
            for (List<BaseModel> childrendModels : childrenModelsList) {
                saveByDepthFirst(monitoringSite, childrendModels);
            }
        }
    }

    private void saveByDepthFirst(BaseModel baseModel, List<BaseModel> childrenModel) {
        for (BaseModel childModel : childrenModel) {
            childModel.setForeignKey(baseModel.getId());
            save(childModel);

            List<List<BaseModel>> childrenModelsList = getChildrenModels(childModel);
            for (List<BaseModel> childrenModels : childrenModelsList) {
                saveByDepthFirst(childModel, childrenModels);
            }
        }
    }

    private List<List<BaseModel>> getChildrenModels(BaseModel baseModel) {
        List<List<BaseModel>> modelsList = new ArrayList<>();

        for (Field field : baseModel.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ToMany.class) != null) {
                try {
                    modelsList.add((List<BaseModel>) field.get(baseModel));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return modelsList;
    }

    public List<MonitoringSite> queryAll() {
        DaoSession daoSession = new DaoMaster(devOpenHelper.getReadableDatabase()).newSession();
        MonitoringSiteDao dao = daoSession.getMonitoringSiteDao();
        return dao.queryBuilder().build().list();
    }
}
