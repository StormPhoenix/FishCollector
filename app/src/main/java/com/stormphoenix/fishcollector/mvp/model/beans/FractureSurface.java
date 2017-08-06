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
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class FractureSurface extends BaseObservable implements BaseModel {
    @Unique
    @Expose
    @SerializedName("id")
    private String modelId;
    @Nullable
    @Expose
    private String position;
    @Nullable
    @Expose
    private float distance2Bank;
    @Nullable
    @Expose
    @SerializedName("idMonitoringSite")
    private String id_MonitoringSite;

    // ****************************************

    @Id
    private Long id;

    @Expose
    @SerializedName("benthosesById")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<Benthos> benthoses;

    @Expose
    @SerializedName("phytoplanktonsById")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<Phytoplankton> phytoplanktons;

    @Expose
    @SerializedName("zooplanktonsById")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<Zooplankton> zooplanktons;

    @ToOne(joinProperty = "foreignKey")
    private MonitoringSite monitoringSite;

    @Expose
    @SerializedName("sedimentsById")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<Sediment> sediments;

    @Expose
    @SerializedName("measuringLinesById")
    @ToMany(referencedJoinProperty = "foreignKey")
    private List<MeasuringLine> measuringLines;

    private Long foreignKey;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 857002845)
    private transient FractureSurfaceDao myDao;
    @Generated(hash = 272232982)
    private transient Long monitoringSite__resolvedKey;

    @Generated(hash = 1731540934)
    public FractureSurface(String modelId, String position, float distance2Bank,
                           String id_MonitoringSite, Long id, Long foreignKey) {
        this.modelId = modelId;
        this.position = position;
        this.distance2Bank = distance2Bank;
        this.id_MonitoringSite = id_MonitoringSite;
        this.id = id;
        this.foreignKey = foreignKey;
    }

    @Generated(hash = 563633098)
    public FractureSurface() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                position == null ||
                id_MonitoringSite == null ||
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
    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Bindable
    public float getDistance2Bank() {
        return this.distance2Bank;
    }

    public void setDistance2Bank(float distance2Bank) {
        this.distance2Bank = distance2Bank;
    }

    public String getId_MonitoringSite() {
        return this.id_MonitoringSite;
    }

    public void setId_MonitoringSite(String id_MonitoringSite) {
        this.id_MonitoringSite = id_MonitoringSite;
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
    @Generated(hash = 1728717077)
    public MonitoringSite getMonitoringSite() {
        Long __key = this.foreignKey;
        if (monitoringSite__resolvedKey == null
                || !monitoringSite__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MonitoringSiteDao targetDao = daoSession.getMonitoringSiteDao();
            MonitoringSite monitoringSiteNew = targetDao.load(__key);
            synchronized (this) {
                monitoringSite = monitoringSiteNew;
                monitoringSite__resolvedKey = __key;
            }
        }
        return monitoringSite;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2127374704)
    public void setMonitoringSite(MonitoringSite monitoringSite) {
        synchronized (this) {
            this.monitoringSite = monitoringSite;
            foreignKey = monitoringSite == null ? null : monitoringSite.getId();
            monitoringSite__resolvedKey = foreignKey;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1704068118)
    public List<Benthos> getBenthoses() {
        if (benthoses == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BenthosDao targetDao = daoSession.getBenthosDao();
            List<Benthos> benthosesNew = targetDao
                    ._queryFractureSurface_Benthoses(id);
            synchronized (this) {
                if (benthoses == null) {
                    benthoses = benthosesNew;
                }
            }
        }
        return benthoses;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1729397372)
    public synchronized void resetBenthoses() {
        benthoses = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1282729591)
    public List<Phytoplankton> getPhytoplanktons() {
        if (phytoplanktons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PhytoplanktonDao targetDao = daoSession.getPhytoplanktonDao();
            List<Phytoplankton> phytoplanktonsNew = targetDao
                    ._queryFractureSurface_Phytoplanktons(id);
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
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 319033360)
    public List<Zooplankton> getZooplanktons() {
        if (zooplanktons == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ZooplanktonDao targetDao = daoSession.getZooplanktonDao();
            List<Zooplankton> zooplanktonsNew = targetDao
                    ._queryFractureSurface_Zooplanktons(id);
            synchronized (this) {
                if (zooplanktons == null) {
                    zooplanktons = zooplanktonsNew;
                }
            }
        }
        return zooplanktons;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 2016089598)
    public synchronized void resetZooplanktons() {
        zooplanktons = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 941791091)
    public List<Sediment> getSediments() {
        if (sediments == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SedimentDao targetDao = daoSession.getSedimentDao();
            List<Sediment> sedimentsNew = targetDao
                    ._queryFractureSurface_Sediments(id);
            synchronized (this) {
                if (sediments == null) {
                    sediments = sedimentsNew;
                }
            }
        }
        return sediments;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1094743749)
    public synchronized void resetSediments() {
        sediments = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1186364535)
    public List<MeasuringLine> getMeasuringLines() {
        if (measuringLines == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeasuringLineDao targetDao = daoSession.getMeasuringLineDao();
            List<MeasuringLine> measuringLinesNew = targetDao
                    ._queryFractureSurface_MeasuringLines(id);
            synchronized (this) {
                if (measuringLines == null) {
                    measuringLines = measuringLinesNew;
                }
            }
        }
        return measuringLines;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1652497574)
    public synchronized void resetMeasuringLines() {
        measuringLines = null;
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
    @Generated(hash = 159696533)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFractureSurfaceDao() : null;
    }
}
