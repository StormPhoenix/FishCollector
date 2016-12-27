package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class DominantPhytoplanktonSpecies {
    @Unique
    private String modelId;
    @Nullable
    private String name;
    @Nullable
    private String photo;
    @Nullable
    private float quality;
    @Nullable
    private float biomass;
    @Nullable
    private String idPhytoplankton;

    // ********************************
    @Id
    private Long id;

    @ToOne(joinProperty = "phytoplanktonId")
    private Phytoplankton phytoplankton;

    private Long phytoplanktonId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1308065015)
    private transient DominantPhytoplanktonSpeciesDao myDao;

    @Generated(hash = 1463690878)
    public DominantPhytoplanktonSpecies(String modelId, String name, String photo,
            float quality, float biomass, String idPhytoplankton, Long id,
            Long phytoplanktonId) {
        this.modelId = modelId;
        this.name = name;
        this.photo = photo;
        this.quality = quality;
        this.biomass = biomass;
        this.idPhytoplankton = idPhytoplankton;
        this.id = id;
        this.phytoplanktonId = phytoplanktonId;
    }

    @Generated(hash = 1524043572)
    public DominantPhytoplanktonSpecies() {
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getQuality() {
        return this.quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    public float getBiomass() {
        return this.biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
    }

    public String getIdPhytoplankton() {
        return this.idPhytoplankton;
    }

    public void setIdPhytoplankton(String idPhytoplankton) {
        this.idPhytoplankton = idPhytoplankton;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhytoplanktonId() {
        return this.phytoplanktonId;
    }

    public void setPhytoplanktonId(Long phytoplanktonId) {
        this.phytoplanktonId = phytoplanktonId;
    }

    @Generated(hash = 1533042808)
    private transient Long phytoplankton__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 398209780)
    public Phytoplankton getPhytoplankton() {
        Long __key = this.phytoplanktonId;
        if (phytoplankton__resolvedKey == null
                || !phytoplankton__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhytoplanktonDao targetDao = daoSession.getPhytoplanktonDao();
            Phytoplankton phytoplanktonNew = targetDao.load(__key);
            synchronized (this) {
                phytoplankton = phytoplanktonNew;
                phytoplankton__resolvedKey = __key;
            }
        }
        return phytoplankton;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 992180354)
    public void setPhytoplankton(Phytoplankton phytoplankton) {
        synchronized (this) {
            this.phytoplankton = phytoplankton;
            phytoplanktonId = phytoplankton == null ? null : phytoplankton.getId();
            phytoplankton__resolvedKey = phytoplanktonId;
        }
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
    @Generated(hash = 169178027)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDominantPhytoplanktonSpeciesDao() : null;
    }
}
