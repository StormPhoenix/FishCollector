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
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class FishEggs extends BaseObservable implements BaseModel {
    @Unique
    @Expose
    @SerializedName("sampleId")
    private String modelId;
    @Expose
    @Nullable
    private String photo;
    @Nullable
    @Expose
    private String period;
    @Nullable
    @Expose
    private float diameter;
    @Nullable
    @Expose
    private float emDiameter;
    @Nullable
    @Expose
    private String pigmentProp;
    @Nullable
    @Expose
    private String embryoProp;
    @Nullable
    @Expose
    private String idCatches;

    // ******************************

    @Id
    private Long id;

    @ToOne(joinProperty = "foreignKey")
    private Catches catches;

    private Long foreignKey;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1913398067)
    private transient FishEggsDao myDao;
    @Generated(hash = 822618378)
    private transient Long catches__resolvedKey;

    @Generated(hash = 1515414799)
    public FishEggs(String modelId, String photo, String period, float diameter,
                    float emDiameter, String pigmentProp, String embryoProp,
                    String idCatches, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.photo = photo;
        this.period = period;
        this.diameter = diameter;
        this.emDiameter = emDiameter;
        this.pigmentProp = pigmentProp;
        this.embryoProp = embryoProp;
        this.idCatches = idCatches;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 734232265)
    public FishEggs() {
    }

    @Override
    public boolean checkValue() {
        if (photo == null ||
                period == null ||
                pigmentProp == null ||
                embryoProp == null ||
                idCatches == null ||
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

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public float getDiameter() {
        return this.diameter;
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public float getEmDiameter() {
        return this.emDiameter;
    }

    public void setEmDiameter(float emDiameter) {
        this.emDiameter = emDiameter;
    }

    public String getPigmentProp() {
        return this.pigmentProp;
    }

    public void setPigmentProp(String pigmentProp) {
        this.pigmentProp = pigmentProp;
    }

    public String getEmbryoProp() {
        return this.embryoProp;
    }

    public void setEmbryoProp(String embryoProp) {
        this.embryoProp = embryoProp;
    }

    public String getIdCatches() {
        return this.idCatches;
    }

    public void setIdCatches(String idCatches) {
        this.idCatches = idCatches;
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
    @Generated(hash = 343607068)
    public Catches getCatches() {
        Long __key = this.foreignKey;
        if (catches__resolvedKey == null || !catches__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CatchesDao targetDao = daoSession.getCatchesDao();
            Catches catchesNew = targetDao.load(__key);
            synchronized (this) {
                catches = catchesNew;
                catches__resolvedKey = __key;
            }
        }
        return catches;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 720081995)
    public void setCatches(Catches catches) {
        synchronized (this) {
            this.catches = catches;
            foreignKey = catches == null ? null : catches.getId();
            catches__resolvedKey = foreignKey;
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
    @Generated(hash = 911340968)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFishEggsDao() : null;
    }
}
