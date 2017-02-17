package com.stormphoenix.fishcollector.mvp.model.beans;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class MeasuringLine extends BaseObservable implements BaseModel {
    @Unique
    @Expose
    @SerializedName("id")
    private String modelId;
    @Nullable
    @Expose
    private float startLongitude;
    @Nullable
    @Expose
    private float startLatitude;
    @Nullable
    @Expose
    private float endLongitude;
    @Nullable
    @Expose
    private float endLatitude;
    @Nullable
    @Expose
    @SerializedName("idFractureSurface")
    private String idFractureSurface;

    // *******************************

    @Id
    private Long id;

    @ToOne(joinProperty = "foreignKey")
    private FractureSurface fractureSurface;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<MeasurePoint> measurePoints;

    private Long foreignKey;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1481508108)
    private transient MeasuringLineDao myDao;

    @Generated(hash = 1134349867)
    public MeasuringLine(String modelId, float startLongitude, float startLatitude,
                         float endLongitude, float endLatitude, String idFractureSurface,
                         Long id, Long foreignKey) {
        this.modelId = modelId;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
        this.idFractureSurface = idFractureSurface;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 85376486)
    public MeasuringLine() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                startLongitude == 0 ||
                startLatitude == 0 ||
                endLongitude == 0 ||
                endLatitude == 0 ||
                idFractureSurface == null ||
                id == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    @Bindable
    public float getStartLongitude() {
        return this.startLongitude;
    }

    public void setStartLongitude(float startLongitude) {
        this.startLongitude = startLongitude;
    }

    @Bindable
    public float getStartLatitude() {
        return this.startLatitude;
    }

    public void setStartLatitude(float startLatitude) {
        this.startLatitude = startLatitude;
    }

    @Bindable
    public float getEndLongitude() {
        return this.endLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    @Bindable
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

    public Long getForeignKey() {
        return this.foreignKey;
    }

    public void setForeignKey(Long foreignKey) {
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 844368361)
    private transient Long fractureSurface__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 610717877)
    public FractureSurface getFractureSurface() {
        Long __key = this.foreignKey;
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

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1813620905)
    public void setFractureSurface(FractureSurface fractureSurface) {
        synchronized (this) {
            this.fractureSurface = fractureSurface;
            foreignKey = fractureSurface == null ? null : fractureSurface.getId();
            fractureSurface__resolvedKey = foreignKey;
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

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
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