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
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
@Entity
public class MonitoringSite extends BaseObservable implements BaseModel, Cloneable {
    @Override
    public MonitoringSite clone() {
        MonitoringSite obj = null;
        try {
            obj = (MonitoringSite) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 这个是给后台使用的主键，用时间拼凑的
     */
    @Unique
    @Expose
    @SerializedName("inverstigationId")
    private String modelId;
    @Nullable
    @Expose
    private String institution;
    @Nullable
    @Expose
    private String investigator;
    @Nullable
    @Expose
    private String investigationDate;
    @Nullable
    @Expose
    private String site;
    @Nullable
    @Expose
    private String river;
    @Nullable
//    @Expose
    private String photo;
    @Nullable
    @Expose
    private String startTime;
    @Nullable
    @Expose
    private String endTime;
    @Nullable
    @Expose
    private float startLongitude;
    @Nullable
    @Expose
    private float startLatitude;
    @Nullable
    @Expose
    private float endLongitude;
    @Nullable
    @Expose
    private float endLatitude;
    @Nullable
    @Expose
    private String weather;
    @Nullable
    @Expose
    private float temperature;
    @Nullable
    private int userId;

    // ***********************************
    @Id
    private Long id;

    @ToMany(referencedJoinProperty = "foreignKey")
    private List<FractureSurface> fractureSurfaces;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1608688954)
    private transient MonitoringSiteDao myDao;

    @Generated(hash = 1479612113)
    public MonitoringSite(String modelId, String institution, String investigator,
                          String investigationDate, String site, String river, String photo,
                          String startTime, String endTime, float startLongitude,
                          float startLatitude, float endLongitude, float endLatitude,
                          String weather, float temperature, int userId, Long id) {
        this.modelId = modelId;
        this.institution = institution;
        this.investigator = investigator;
        this.investigationDate = investigationDate;
        this.site = site;
        this.river = river;
        this.photo = photo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLongitude = startLongitude;
        this.startLatitude = startLatitude;
        this.endLongitude = endLongitude;
        this.endLatitude = endLatitude;
        this.weather = weather;
        this.temperature = temperature;
        this.userId = userId;
        this.id = id;
    }

    @Generated(hash = 692898491)
    public MonitoringSite() {
    }

    @Override
    public boolean checkValue() {
        if (modelId == null ||
                institution == null ||
                investigator == null ||
                investigationDate == null ||
                site == null ||
                river == null ||
                photo == null ||
                startTime == null ||
                endTime == null ||
                startLatitude == 0 ||
                startLongitude == 0 ||
                endLatitude == 0 ||
                endLongitude == 0 ||
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
    public String getInstitution() {
        return this.institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
        notifyPropertyChanged(BR.institution);
    }

    @Bindable
    public String getInvestigator() {
        return this.investigator;
    }

    public void setInvestigator(String investigator) {
        this.investigator = investigator;
        notifyPropertyChanged(BR.investigator);
    }

    @Bindable
    public String getInvestigationDate() {
        return this.investigationDate;
    }

    public void setInvestigationDate(String investigationDate) {
        this.investigationDate = investigationDate;
        notifyPropertyChanged(BR.investigationDate);
    }

    @Bindable
    public String getSite() {
        return this.site;
    }

    public void setSite(String site) {
        this.site = site;
        notifyPropertyChanged(BR.site);
    }

    @Bindable
    public String getRiver() {
        return this.river;
    }

    public void setRiver(String river) {
        this.river = river;
        notifyPropertyChanged(BR.river);
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Bindable
    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable
    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        notifyPropertyChanged(BR.endTime);
    }

    public float getStartLongitude() {
        return this.startLongitude;
    }

    public void setStartLongitude(float startLongitude) {
        this.startLongitude = startLongitude;
    }

    public float getStartLatitude() {
        return this.startLatitude;
    }

    public void setStartLatitude(float startLatitude) {
        this.startLatitude = startLatitude;
    }

    public float getEndLongitude() {
        return this.endLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    public float getEndLatitude() {
        return this.endLatitude;
    }

    public void setEndLatitude(float endLatitude) {
        this.endLatitude = endLatitude;
    }

    @Bindable
    public String getWeather() {
        return this.weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
        notifyPropertyChanged(BR.weather);
    }

    public float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getForeignKey() {
        return null;
    }

    @Override
    public void setForeignKey(Long foreignKey) {

    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 47587984)
    public List<FractureSurface> getFractureSurfaces() {
        if (fractureSurfaces == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FractureSurfaceDao targetDao = daoSession.getFractureSurfaceDao();
            List<FractureSurface> fractureSurfacesNew = targetDao
                    ._queryMonitoringSite_FractureSurfaces(id);
            synchronized (this) {
                if (fractureSurfaces == null) {
                    fractureSurfaces = fractureSurfacesNew;
                }
            }
        }
        return fractureSurfaces;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 911387358)
    public synchronized void resetFractureSurfaces() {
        fractureSurfaces = null;
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
    @Generated(hash = 1999460273)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMonitoringSiteDao() : null;
    }
}
