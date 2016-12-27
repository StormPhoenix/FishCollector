package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class FractureSurface {
    @Unique
    private String modelId;
    @Nullable
    private String position;
    @Nullable
    private float distance2Bank;
    @Nullable
    private String id_MonitoringSite;

    // ****************************************

    @Id
    private Long id;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Benthos> benthoses;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Phytoplankton> phytoplanktons;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Zooplankton> zooplanktons;

    @ToOne(joinProperty = "monitorSiteId")
    private MonitoringSite monitoringSite;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Sediment> sediments;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<MeasuringLine> measuringLines;

    private Long monitorSiteId;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 857002845)
    private transient FractureSurfaceDao myDao;

    @Generated(hash = 463992281)
    public FractureSurface(String modelId, String position, float distance2Bank,
            String id_MonitoringSite, Long id, Long monitorSiteId) {
        this.modelId = modelId;
        this.position = position;
        this.distance2Bank = distance2Bank;
        this.id_MonitoringSite = id_MonitoringSite;
        this.id = id;
        this.monitorSiteId = monitorSiteId;
    }

    @Generated(hash = 563633098)
    public FractureSurface() {
    }

    public String getModelId() {
        return this.modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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

    public Long getMonitorSiteId() {
        return this.monitorSiteId;
    }

    public void setMonitorSiteId(Long monitorSiteId) {
        this.monitorSiteId = monitorSiteId;
    }

    @Generated(hash = 272232982)
    private transient Long monitoringSite__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1151831135)
    public MonitoringSite getMonitoringSite() {
        Long __key = this.monitorSiteId;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1187647077)
    public void setMonitoringSite(MonitoringSite monitoringSite) {
        synchronized (this) {
            this.monitoringSite = monitoringSite;
            monitorSiteId = monitoringSite == null ? null : monitoringSite.getId();
            monitoringSite__resolvedKey = monitorSiteId;
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
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
