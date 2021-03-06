package com.stormphoenix.fishcollector.mvp.model.beans;


import android.databinding.BaseObservable;
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
public class Zooplankton extends BaseObservable implements BaseModel {
    @Unique
    @Expose
    @SerializedName("sampleId")
    private String modelId;
    @Expose
    @Nullable
    private String photo;
    @Nullable
    @Expose
    private int quality;
    @Nullable
    @Expose
    private float biomass;
    @Nullable
    @Expose
    @SerializedName("idFractureSurface")
    private String ID_FractureSurface;

    // **********************************
    @Id
    private Long id;

    private Long foreignKey;

    @ToOne(joinProperty = "foreignKey")
    private FractureSurface fractureSurface;

    @Expose
    @SerializedName("dominantZooplanktonSpeciesBySampleId")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<DominantZooplanktonSpecies> dominantZooplanktonSpecies;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1027756039)
    private transient ZooplanktonDao myDao;
    @Generated(hash = 844368361)
    private transient Long fractureSurface__resolvedKey;

    @Generated(hash = 335956638)
    public Zooplankton(String modelId, String photo, int quality, float biomass,
                       String ID_FractureSurface, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.photo = photo;
        this.quality = quality;
        this.biomass = biomass;
        this.ID_FractureSurface = ID_FractureSurface;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 318554425)
    public Zooplankton() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                ID_FractureSurface == null ||
                id == null ||
                foreignKey == null) {
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

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public float getBiomass() {
        return this.biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
    }

    public String getID_FractureSurface() {
        return this.ID_FractureSurface;
    }

    public void setID_FractureSurface(String ID_FractureSurface) {
        this.ID_FractureSurface = ID_FractureSurface;
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
    @Generated(hash = 925980069)
    public List<DominantZooplanktonSpecies> getDominantZooplanktonSpecies() {
        if (dominantZooplanktonSpecies == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DominantZooplanktonSpeciesDao targetDao = daoSession
                    .getDominantZooplanktonSpeciesDao();
            List<DominantZooplanktonSpecies> dominantZooplanktonSpeciesNew = targetDao
                    ._queryZooplankton_DominantZooplanktonSpecies(id);
            synchronized (this) {
                if (dominantZooplanktonSpecies == null) {
                    dominantZooplanktonSpecies = dominantZooplanktonSpeciesNew;
                }
            }
        }
        return dominantZooplanktonSpecies;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1040578637)
    public synchronized void resetDominantZooplanktonSpecies() {
        dominantZooplanktonSpecies = null;
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
    @Generated(hash = 1324745223)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getZooplanktonDao() : null;
    }
}
