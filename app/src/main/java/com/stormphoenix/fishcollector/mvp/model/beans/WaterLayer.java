package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

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
public class WaterLayer {
    @Unique
    private String modelId;
    @Nullable
    private String layer;
    @Nullable
    private float depth;
    @Nullable
    private float temperature;
    @Nullable
    private float waterLevel;
    @Nullable
    private float velocity;
    @Nullable
    private String idMeasurePoint;

    // *****************************
    @Id
    private Long id;

    private Long measurePointId;

    @ToOne(joinProperty = "measurePointId")
    private MeasurePoint measurePoint;

    @ToMany(referencedJoinProperty = "waterLayerId")
    private List<Catches> catches;

    @ToMany(referencedJoinProperty = "waterLayerId")
    private List<CatchTools> catchToolses;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1028787526)
    private transient WaterLayerDao myDao;

    @Generated(hash = 1765978184)
    public WaterLayer(String modelId, String layer, float depth, float temperature,
            float waterLevel, float velocity, String idMeasurePoint, Long id,
            Long measurePointId) {
        this.modelId = modelId;
        this.layer = layer;
        this.depth = depth;
        this.temperature = temperature;
        this.waterLevel = waterLevel;
        this.velocity = velocity;
        this.idMeasurePoint = idMeasurePoint;
        this.id = id;
        this.measurePointId = measurePointId;
    }

    @Generated(hash = 1475513300)
    public WaterLayer() {
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getLayer() {
        return this.layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public float getDepth() {
        return this.depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWaterLevel() {
        return this.waterLevel;
    }

    public void setWaterLevel(float waterLevel) {
        this.waterLevel = waterLevel;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public String getIdMeasurePoint() {
        return this.idMeasurePoint;
    }

    public void setIdMeasurePoint(String idMeasurePoint) {
        this.idMeasurePoint = idMeasurePoint;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMeasurePointId() {
        return this.measurePointId;
    }

    public void setMeasurePointId(Long measurePointId) {
        this.measurePointId = measurePointId;
    }

    @Generated(hash = 1875589804)
    private transient Long measurePoint__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2023765787)
    public MeasurePoint getMeasurePoint() {
        Long __key = this.measurePointId;
        if (measurePoint__resolvedKey == null
                || !measurePoint__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeasurePointDao targetDao = daoSession.getMeasurePointDao();
            MeasurePoint measurePointNew = targetDao.load(__key);
            synchronized (this) {
                measurePoint = measurePointNew;
                measurePoint__resolvedKey = __key;
            }
        }
        return measurePoint;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 569446088)
    public void setMeasurePoint(MeasurePoint measurePoint) {
        synchronized (this) {
            this.measurePoint = measurePoint;
            measurePointId = measurePoint == null ? null : measurePoint.getId();
            measurePoint__resolvedKey = measurePointId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 569117650)
    public List<Catches> getCatches() {
        if (catches == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CatchesDao targetDao = daoSession.getCatchesDao();
            List<Catches> catchesNew = targetDao._queryWaterLayer_Catches(id);
            synchronized (this) {
                if (catches == null) {
                    catches = catchesNew;
                }
            }
        }
        return catches;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 85715937)
    public synchronized void resetCatches() {
        catches = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 624272551)
    public List<CatchTools> getCatchToolses() {
        if (catchToolses == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CatchToolsDao targetDao = daoSession.getCatchToolsDao();
            List<CatchTools> catchToolsesNew = targetDao
                    ._queryWaterLayer_CatchToolses(id);
            synchronized (this) {
                if (catchToolses == null) {
                    catchToolses = catchToolsesNew;
                }
            }
        }
        return catchToolses;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1840662511)
    public synchronized void resetCatchToolses() {
        catchToolses = null;
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
    @Generated(hash = 529674059)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWaterLayerDao() : null;
    }
}
