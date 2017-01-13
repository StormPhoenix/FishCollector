package com.stormphoenix.fishcollector.mvp.model.beans;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class MeasurePoint extends BaseObservable implements BaseModel {

    @Unique
    private String modelId;
    @Nullable
    private float longitude;
    @Nullable
    private float latitude;
    @Nullable
    private String idMeasuringLine;

    // ********************************
    @Id
    private Long id;

    private Long foreignKey;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<WaterLayer> waterLayers;

    @ToOne(joinProperty = "foreignKey")
    private MeasuringLine measuringLine;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1617290021)
    private transient MeasurePointDao myDao;

    @Generated(hash = 1859168494)
    public MeasurePoint(String modelId, float longitude, float latitude, String idMeasuringLine,
            Long id, Long foreignKey) {
        this.modelId = modelId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idMeasuringLine = idMeasuringLine;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 878361328)
    public MeasurePoint() {
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Bindable
    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @Bindable
    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getIdMeasuringLine() {
        return this.idMeasuringLine;
    }

    public void setIdMeasuringLine(String idMeasuringLine) {
        this.idMeasuringLine = idMeasuringLine;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getForeignKey() {
        return this.foreignKey;
    }

    public void setForeignKey(Long foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 270898113)
    private transient Long measuringLine__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 524236604)
    public MeasuringLine getMeasuringLine() {
        Long __key = this.foreignKey;
        if (measuringLine__resolvedKey == null
                || !measuringLine__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeasuringLineDao targetDao = daoSession.getMeasuringLineDao();
            MeasuringLine measuringLineNew = targetDao.load(__key);
            synchronized (this) {
                measuringLine = measuringLineNew;
                measuringLine__resolvedKey = __key;
            }
        }
        return measuringLine;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1557940170)
    public void setMeasuringLine(MeasuringLine measuringLine) {
        synchronized (this) {
            this.measuringLine = measuringLine;
            foreignKey = measuringLine == null ? null : measuringLine.getId();
            measuringLine__resolvedKey = foreignKey;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1084146498)
    public List<WaterLayer> getWaterLayers() {
        if (waterLayers == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WaterLayerDao targetDao = daoSession.getWaterLayerDao();
            List<WaterLayer> waterLayersNew = targetDao
                    ._queryMeasurePoint_WaterLayers(id);
            synchronized (this) {
                if (waterLayers == null) {
                    waterLayers = waterLayersNew;
                }
            }
        }
        return waterLayers;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 426312877)
    public synchronized void resetWaterLayers() {
        waterLayers = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1938891994)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMeasurePointDao() : null;
    }
}
