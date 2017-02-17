package com.stormphoenix.fishcollector.mvp.model.beans;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.Nullable;

import com.android.databinding.library.baseAdapters.BR;
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
 * 渔获物
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class Catches extends BaseObservable implements BaseModel {
    //主键
    @Unique
    @Expose
    @SerializedName("sampleId")
    private String modelId = null;
    //鱼类名称
    @Nullable
    @Expose
    private String name = null;
    //图片路径
    @Nullable
    @Expose
    private String photo = null;
    //卵苗总数
    @Nullable
    @Expose
    private int totalQuality;
    //鱼卵总数
    @Nullable
    @Expose
    private int eggQuality;
    //幼鱼数
    @Nullable
    @Expose
    private int fryQuality;
    //水层外键
    @Nullable
    @Expose
    private String idWaterLayer = null;

    // *******************************
    @Id
    private Long id;

    private Long foreignKey;

    @ToOne(joinProperty = "foreignKey")
    private WaterLayer waterLayer;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<Fishes> fishes;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<FishEggs> fishEggses;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1459665042)
    private transient CatchesDao myDao;

    @Generated(hash = 1474779920)
    public Catches(String modelId, String name, String photo, int totalQuality,
                   int eggQuality, int fryQuality, String idWaterLayer, Long id,
                   Long foreignKey) {
        this.modelId = modelId;
        this.name = name;
        this.photo = photo;
        this.totalQuality = totalQuality;
        this.eggQuality = eggQuality;
        this.fryQuality = fryQuality;
        this.idWaterLayer = idWaterLayer;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 1720481633)
    public Catches() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                name == null ||
                photo == null ||
                idWaterLayer == null ||
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

    @Bindable
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getTotalQuality() {
        return this.totalQuality;
    }

    public void setTotalQuality(int totalQuality) {
        this.totalQuality = totalQuality;
    }

    public int getEggQuality() {
        return this.eggQuality;
    }

    public void setEggQuality(int eggQuality) {
        this.eggQuality = eggQuality;
    }

    public int getFryQuality() {
        return this.fryQuality;
    }

    public void setFryQuality(int fryQuality) {
        this.fryQuality = fryQuality;
    }

    public String getIdWaterLayer() {
        return this.idWaterLayer;
    }

    public void setIdWaterLayer(String idWaterLayer) {
        this.idWaterLayer = idWaterLayer;
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

    @Generated(hash = 1203946248)
    private transient Long waterLayer__resolvedKey;

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2125111669)
    public WaterLayer getWaterLayer() {
        Long __key = this.foreignKey;
        if (waterLayer__resolvedKey == null
                || !waterLayer__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WaterLayerDao targetDao = daoSession.getWaterLayerDao();
            WaterLayer waterLayerNew = targetDao.load(__key);
            synchronized (this) {
                waterLayer = waterLayerNew;
                waterLayer__resolvedKey = __key;
            }
        }
        return waterLayer;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 95803670)
    public void setWaterLayer(WaterLayer waterLayer) {
        synchronized (this) {
            this.waterLayer = waterLayer;
            foreignKey = waterLayer == null ? null : waterLayer.getId();
            waterLayer__resolvedKey = foreignKey;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1291500174)
    public List<Fishes> getFishes() {
        if (fishes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FishesDao targetDao = daoSession.getFishesDao();
            List<Fishes> fishesNew = targetDao._queryCatches_Fishes(id);
            synchronized (this) {
                if (fishes == null) {
                    fishes = fishesNew;
                }
            }
        }
        return fishes;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 306342869)
    public synchronized void resetFishes() {
        fishes = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1943347594)
    public List<FishEggs> getFishEggses() {
        if (fishEggses == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FishEggsDao targetDao = daoSession.getFishEggsDao();
            List<FishEggs> fishEggsesNew = targetDao._queryCatches_FishEggses(id);
            synchronized (this) {
                if (fishEggses == null) {
                    fishEggses = fishEggsesNew;
                }
            }
        }
        return fishEggses;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 24613756)
    public synchronized void resetFishEggses() {
        fishEggses = null;
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
    @Generated(hash = 1460040865)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCatchesDao() : null;
    }
}
