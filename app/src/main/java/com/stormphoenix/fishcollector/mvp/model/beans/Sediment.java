package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * 沉积物
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class Sediment {
    @Unique
    private String modelId;
    @Nullable
    private String photo;
    @Nullable
    private String id_FractureSurface;

    // **********************************
    @Id
    private Long id;

    private Long fractureSurfaceId;

    @ToOne(joinProperty = "fractureSurfaceId")
    private FractureSurface fractureSurface;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1423276668)
    private transient SedimentDao myDao;

    @Generated(hash = 587038384)
    public Sediment(String modelId, String photo, String id_FractureSurface,
            Long id, Long fractureSurfaceId) {
        this.modelId = modelId;
        this.photo = photo;
        this.id_FractureSurface = id_FractureSurface;
        this.id = id;
        this.fractureSurfaceId = fractureSurfaceId;
    }

    @Generated(hash = 171218527)
    public Sediment() {
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

    public String getId_FractureSurface() {
        return this.id_FractureSurface;
    }

    public void setId_FractureSurface(String id_FractureSurface) {
        this.id_FractureSurface = id_FractureSurface;
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
    @Generated(hash = 1486160235)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSedimentDao() : null;
    }
}
