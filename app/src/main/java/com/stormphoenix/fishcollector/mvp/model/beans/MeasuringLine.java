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
public class MeasuringLine {

    @Unique
    private String modelId;
    @Nullable
    private float startLongitude;
    @Nullable
    private float startLatitude;
    @Nullable
    private float endLongitude;
    @Nullable
    private float endLatitude;
    @Nullable
    private String idFractureSurface;

    // *******************************

    @Id
    private Long id;

    @ToOne(joinProperty = "fractureSurfaceId")
    private FractureSurface fractureSurface;

    @ToMany(referencedJoinProperty = "measuringLineId")
    private List<MeasurePoint> measurePoints;

    private Long fractureSurfaceId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1481508108)
    private transient MeasuringLineDao myDao;

    @Generated(hash = 219044219)
    public MeasuringLine(String modelId, float startLongitude, float startLatitude,
            float endLongitude, float endLatitude, String idFractureSurface,
            Long id, Long fractureSurfaceId) {
        this.modelId = modelId;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
        this.idFractureSurface = idFractureSurface;
        this.id = id;
        this.fractureSurfaceId = fractureSurfaceId;
    }

    @Generated(hash = 85376486)
    public MeasuringLine() {
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public float getStartLongitude() {
        return this.startLongitude;
    }

    public void setStartLongitude(float startLongitude) {
        this.startLongitude = startLongitude;
    }

    public float getStartLatitude() {
        return this.startLatitude;
    }

    public void setStartLatitude(float startLatitude) {
        this.startLatitude = startLatitude;
    }

    public float getEndLongitude() {
        return this.endLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    public float getEndLatitude() {
        return this.endLatitude;
    }

    public void setEndLatitude(float endLatitude) {
        this.endLatitude = endLatitude;
    }

    public String getIdFractureSurface() {
        return this.idFractureSurface;
    }

    public void setIdFractureSurface(String idFractureSurface) {
        this.idFractureSurface = idFractureSurface;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFractureSurfaceId() {
        return this.fractureSurfaceId;
    }

    public void setFractureSurfaceId(Long fractureSurfaceId) {
        this.fractureSurfaceId = fractureSurfaceId;
    }

    @Generated(hash = 844368361)
    private transient Long fractureSurface__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1111745244)
    public FractureSurface getFractureSurface() {
        Long __key = this.fractureSurfaceId;
        if (fractureSurface__resolvedKey == null
                || !fractureSurface__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FractureSurfaceDao targetDao = daoSession.getFractureSurfaceDao();
            FractureSurface fractureSurfaceNew = targetDao.load(__key);
            synchronized (this) {
                fractureSurface = fractureSurfaceNew;
                fractureSurface__resolvedKey = __key;
            }
        }
        return fractureSurface;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1218494074)
    public void setFractureSurface(FractureSurface fractureSurface) {
        synchronized (this) {
            this.fractureSurface = fractureSurface;
            fractureSurfaceId = fractureSurface == null ? null
                    : fractureSurface.getId();
            fractureSurface__resolvedKey = fractureSurfaceId;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 859840785)
    public List<MeasurePoint> getMeasurePoints() {
        if (measurePoints == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeasurePointDao targetDao = daoSession.getMeasurePointDao();
            List<MeasurePoint> measurePointsNew = targetDao
                    ._queryMeasuringLine_MeasurePoints(id);
            synchronized (this) {
                if (measurePoints == null) {
                    measurePoints = measurePointsNew;
                }
            }
        }
        return measurePoints;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1962954793)
    public synchronized void resetMeasurePoints() {
        measurePoints = null;
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
    @Generated(hash = 1576161310)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMeasuringLineDao() : null;
    }
}