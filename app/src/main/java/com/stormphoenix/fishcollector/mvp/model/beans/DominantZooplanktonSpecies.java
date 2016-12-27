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
public class DominantZooplanktonSpecies {
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
    private String idZooplankton;

    // ******************************
    @Id
    private Long id;

    @ToOne(joinProperty = "zooplanktonId")
    private Zooplankton zooplankton;

    private Long zooplanktonId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 781743425)
    private transient DominantZooplanktonSpeciesDao myDao;

    @Generated(hash = 742495160)
    public DominantZooplanktonSpecies(String modelId, String name, String photo,
            float quality, float biomass, String idZooplankton, Long id,
            Long zooplanktonId) {
        this.modelId = modelId;
        this.name = name;
        this.photo = photo;
        this.quality = quality;
        this.biomass = biomass;
        this.idZooplankton = idZooplankton;
        this.id = id;
        this.zooplanktonId = zooplanktonId;
    }

    @Generated(hash = 990087073)
    public DominantZooplanktonSpecies() {
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

    public String getIdZooplankton() {
        return this.idZooplankton;
    }

    public void setIdZooplankton(String idZooplankton) {
        this.idZooplankton = idZooplankton;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getZooplanktonId() {
        return this.zooplanktonId;
    }

    public void setZooplanktonId(Long zooplanktonId) {
        this.zooplanktonId = zooplanktonId;
    }

    @Generated(hash = 253600324)
    private transient Long zooplankton__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1227914635)
    public Zooplankton getZooplankton() {
        Long __key = this.zooplanktonId;
        if (zooplankton__resolvedKey == null
                || !zooplankton__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ZooplanktonDao targetDao = daoSession.getZooplanktonDao();
            Zooplankton zooplanktonNew = targetDao.load(__key);
            synchronized (this) {
                zooplankton = zooplanktonNew;
                zooplankton__resolvedKey = __key;
            }
        }
        return zooplankton;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1121485628)
    public void setZooplankton(Zooplankton zooplankton) {
        synchronized (this) {
            this.zooplankton = zooplankton;
            zooplanktonId = zooplankton == null ? null : zooplankton.getId();
            zooplankton__resolvedKey = zooplanktonId;
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
    @Generated(hash = 621900367)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDominantZooplanktonSpeciesDao() : null;
    }
}
