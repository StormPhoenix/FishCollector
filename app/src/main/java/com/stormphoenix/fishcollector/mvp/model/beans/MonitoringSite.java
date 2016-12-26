package com.stormphoenix.fishcollector.mvp.model.beans;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class MonitoringSite {

    @Unique
    private String InverstigationID;
    @Nullable
    private String Institution;
    @Nullable
    private String Investigator;
    @Nullable
    private String InvestigationDate;
    @Nullable
    private String Site;
    @Nullable
    private String River;
    @Nullable
    private String Photo;
    @Nullable
    private String StartTime;
    @Nullable
    private String EndTime;
    @Nullable
    private float StartLongitude;
    @Nullable
    private float StartLatitude;
    @Nullable
    private float EndLongitude;
    @Nullable
    private float EndLatitude;
    @Nullable
    private String Weather;
    @Nullable
    private float Temperature;
    @Nullable
    private int userId;

    // ***********************************
    @Id
    private Long id;

    @ToMany(referencedJoinProperty = "monitorSiteId")
    private List<FractureSurface> fractureSurfaces;

}
