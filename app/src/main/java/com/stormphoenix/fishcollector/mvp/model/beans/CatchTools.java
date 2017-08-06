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
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class CatchTools extends BaseObservable implements BaseModel {
    //CatchTools主键
    @Unique
    @Expose
    @SerializedName("sampleId")
    private String modelId;
    //网具名字
    @Nullable
    @Expose
    private String name;
    //照片路径，多个路径用分号隔开
    @Nullable
    private String photo;
    //网型
    @Nullable
    @Expose
    private String netsModel;
    //网口面积
    @Nullable
    @Expose
    private float netMouthArea;
    //网口倾角
    @Nullable
    @Expose
    private float netMouthDip;
    //开始时间
    @Nullable
    @Expose
    private String startTime;
    //结束时间
    @Nullable
    @Expose
    private String endTime;
    //网口流速
    @Nullable
    @Expose
    private float netMouthVelocity;
    // 水层外键
    @Nullable
    @Expose
    private String idWaterLayer;

    // ******************************

    @Id
    private Long id;

    @ToOne(joinProperty = "foreignKey")
    private WaterLayer waterLayer;

    private Long foreignKey;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 135108567)
    private transient CatchToolsDao myDao;
    @Generated(hash = 1203946248)
    private transient Long waterLayer__resolvedKey;

    @Generated(hash = 1672380762)
    public CatchTools(String modelId, String name, String photo, String netsModel,
                      float netMouthArea, float netMouthDip, String startTime, String endTime,
                      float netMouthVelocity, String idWaterLayer, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.name = name;
        this.photo = photo;
        this.netsModel = netsModel;
        this.netMouthArea = netMouthArea;
        this.netMouthDip = netMouthDip;
        this.startTime = startTime;
        this.endTime = endTime;
        this.netMouthVelocity = netMouthVelocity;
        this.idWaterLayer = idWaterLayer;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 1486274156)
    public CatchTools() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                name == null ||
                netsModel == null ||
                netMouthArea == 0 ||
                startTime == null ||
                endTime == null ||
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
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Bindable
    public String getNetsModel() {
        return this.netsModel;
    }

    public void setNetsModel(String netsModel) {
        this.netsModel = netsModel;
    }

    public float getNetMouthArea() {
        return this.netMouthArea;
    }

    public void setNetMouthArea(float netMouthArea) {
        this.netMouthArea = netMouthArea;
    }

    public float getNetMouthDip() {
        return this.netMouthDip;
    }

    public void setNetMouthDip(float netMouthDip) {
        this.netMouthDip = netMouthDip;
    }

    @Bindable
    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Bindable
    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public float getNetMouthVelocity() {
        return this.netMouthVelocity;
    }

    public void setNetMouthVelocity(float netMouthVelocity) {
        this.netMouthVelocity = netMouthVelocity;
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
    @Generated(hash = 465198582)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCatchToolsDao() : null;
    }
}
