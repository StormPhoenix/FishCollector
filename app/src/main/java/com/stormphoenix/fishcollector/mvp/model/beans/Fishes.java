package com.stormphoenix.fishcollector.mvp.model.beans;

import android.databinding.BaseObservable;
import android.support.annotation.Nullable;

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
public class Fishes extends BaseObservable implements BaseModel {

    @Unique
    private String modelId;
    @Nullable
    private String photo;
    @Nullable
    private float bodyLength;
    @Nullable
    private float length;
    @Nullable
    private float bodyWeight;
    @Nullable
    private float age;
    @Nullable
    private String id_Catches;

    // *****************************

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
    @Generated(hash = 963653936)
    private transient FishesDao myDao;

    @Generated(hash = 135400818)
    public Fishes(String modelId, String photo, float bodyLength, float length,
                  float bodyWeight, float age, String id_Catches, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.photo = photo;
        this.bodyLength = bodyLength;
        this.length = length;
        this.bodyWeight = bodyWeight;
        this.age = age;
        this.id_Catches = id_Catches;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 824269915)
    public Fishes() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                photo == null ||
                id_Catches == null ||
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

    public float getBodyLength() {
        return this.bodyLength;
    }

    public void setBodyLength(float bodyLength) {
        this.bodyLength = bodyLength;
    }

    public float getLength() {
        return this.length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getBodyWeight() {
        return this.bodyWeight;
    }

    public void setBodyWeight(float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public float getAge() {
        return this.age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public String getId_Catches() {
        return this.id_Catches;
    }

    public void setId_Catches(String id_Catches) {
        this.id_Catches = id_Catches;
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

    @Generated(hash = 822618378)
    private transient Long catches__resolvedKey;

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
    @Generated(hash = 1934625302)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFishesDao() : null;
    }
}
