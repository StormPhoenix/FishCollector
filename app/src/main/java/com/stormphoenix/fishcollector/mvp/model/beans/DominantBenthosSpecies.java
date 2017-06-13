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
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 底栖生物优势种
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class DominantBenthosSpecies extends BaseObservable implements BaseModel {
    @Unique
    @Expose
    @SerializedName("sampleId")
    private String modelId;
    @Nullable
    @Expose
    private String name;
    @Nullable
    private String photo;
    @Nullable
    @Expose
    private float quality;
    @Nullable
    @Expose
    private float biomass;
    @Nullable
    @Expose
    private String idBenthos;

    // ********************************
    @Id
    private Long id;

    @ToOne(joinProperty = "foreignKey")
    private Benthos benthos;

    private Long foreignKey;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 655655680)
    private transient DominantBenthosSpeciesDao myDao;

    @Generated(hash = 1178577145)
    public DominantBenthosSpecies(String modelId, String name, String photo,
                                  float quality, float biomass, String idBenthos, Long id,
                                  Long foreignKey) {
        this.modelId = modelId;
        this.name = name;
        this.photo = photo;
        this.quality = quality;
        this.biomass = biomass;
        this.idBenthos = idBenthos;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 936238251)
    public DominantBenthosSpecies() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                name == null ||
                photo == null ||
                idBenthos == null ||
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

    @Bindable
    public float getQuality() {
        return this.quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }

    @Bindable
    public float getBiomass() {
        return this.biomass;
    }

    public void setBiomass(float biomass) {
        this.biomass = biomass;
    }

    public String getIdBenthos() {
        return this.idBenthos;
    }

    public void setIdBenthos(String idBenthos) {
        this.idBenthos = idBenthos;
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

    @Generated(hash = 1006378000)
    private transient Long benthos__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 997316169)
    public Benthos getBenthos() {
        Long __key = this.foreignKey;
        if (benthos__resolvedKey == null || !benthos__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BenthosDao targetDao = daoSession.getBenthosDao();
            Benthos benthosNew = targetDao.load(__key);
            synchronized (this) {
                benthos = benthosNew;
                benthos__resolvedKey = __key;
            }
        }
        return benthos;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 806677006)
    public void setBenthos(Benthos benthos) {
        synchronized (this) {
            this.benthos = benthos;
            foreignKey = benthos == null ? null : benthos.getId();
            benthos__resolvedKey = foreignKey;
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
    @Generated(hash = 1229743972)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDominantBenthosSpeciesDao() : null;
    }
}
