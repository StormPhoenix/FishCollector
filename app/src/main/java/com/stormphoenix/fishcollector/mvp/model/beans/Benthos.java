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
 * 底栖生物
 * Created by Phoenix on 2016/5/31.
 */

@Entity(nameInDb = "benthos")
public class Benthos extends BaseObservable implements BaseModel {
    //地栖生物主键
    @Unique
    private String modelId = null;

    //照片路径
    @Nullable
    private String photo = null;

    //数量
    @Nullable
    private int quality = 0;

    //生物量
    @Nullable
    private float biomass = 0;
    //断面外键
    private String idFractureSurface = null;

    /** *************************************** **/

    @ToOne(joinProperty = "foreignKey")
    private FractureSurface fractureSurface;

    @Id
    private Long id;

    private Long foreignKey;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<DominantBenthosSpecies> dominantBenthosSpecies;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 792678032)
    private transient BenthosDao myDao;

    @Generated(hash = 791386)
    public Benthos(String modelId, String photo, int quality, float biomass,
            String idFractureSurface, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.photo = photo;
        this.quality = quality;
        this.biomass = biomass;
        this.idFractureSurface = idFractureSurface;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 989772010)
    public Benthos() {
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
    public int getQuality() {
        return this.quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
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

    /** To-one relationship, resolved on first access. */
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

    /** called by internal mechanisms, do not call yourself. */
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
    @Generated(hash = 1964884043)
    public List<DominantBenthosSpecies> getDominantBenthosSpecies() {
        if (dominantBenthosSpecies == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DominantBenthosSpeciesDao targetDao = daoSession
                    .getDominantBenthosSpeciesDao();
            List<DominantBenthosSpecies> dominantBenthosSpeciesNew = targetDao
                    ._queryBenthos_DominantBenthosSpecies(id);
            synchronized (this) {
                if (dominantBenthosSpecies == null) {
                    dominantBenthosSpecies = dominantBenthosSpeciesNew;
                }
            }
        }
        return dominantBenthosSpecies;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 220625143)
    public synchronized void resetDominantBenthosSpecies() {
        dominantBenthosSpecies = null;
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
    @Generated(hash = 1689106206)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBenthosDao() : null;
    }
}
