package com.stormphoenix.fishcollector.mvp.model.beans;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

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
public class Phytoplankton extends BaseObservable implements BaseModel {
    @Unique
    private String modelId;
    @Nullable
    private String photo;
    @Nullable
    private int puality;
    @Nullable
    private float biomass;
    @Nullable
    private String idFractureSurface;

    // *******************************
    @Id
    private Long id;

    private Long foreignKey;

    @ToOne(joinProperty = "foreignKey")
    private FractureSurface fractureSurface;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<DominantPhytoplanktonSpecies> phytoplanktons;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1895206844)
    private transient PhytoplanktonDao myDao;

    @Generated(hash = 1789049601)
    public Phytoplankton(String modelId, String photo, int puality, float biomass,
                         String idFractureSurface, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.photo = photo;
        this.puality = puality;
        this.biomass = biomass;
        this.idFractureSurface = idFractureSurface;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 688717247)
    public Phytoplankton() {
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

    @Bindable
    public int getPuality() {
        return this.puality;
    }

    public void setPuality(int puality) {
        this.puality = puality;
    }

    @Bindable
    public float getBiomass() {
        return this.biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
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
    @Generated(hash = 1837964736)
    public List<DominantPhytoplanktonSpecies> getPhytoplanktons() {
        if (phytoplanktons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DominantPhytoplanktonSpeciesDao targetDao = daoSession
                    .getDominantPhytoplanktonSpeciesDao();
            List<DominantPhytoplanktonSpecies> phytoplanktonsNew = targetDao
                    ._queryPhytoplankton_Phytoplanktons(id);
            synchronized (this) {
                if (phytoplanktons == null) {
                    phytoplanktons = phytoplanktonsNew;
                }
            }
        }
        return phytoplanktons;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1915235998)
    public synchronized void resetPhytoplanktons() {
        phytoplanktons = null;
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
    @Generated(hash = 1295358102)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPhytoplanktonDao() : null;
    }
}
