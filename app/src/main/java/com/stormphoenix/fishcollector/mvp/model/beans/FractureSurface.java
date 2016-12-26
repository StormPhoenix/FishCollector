package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class FractureSurface {
    @Unique
    private String id;
    @Nullable
    private String position;
    @Nullable
    private float distance2Bank;
    @Nullable
    private String id_MonitoringSite;

    // ****************************************

    @Id
    private Long greenId;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Benthos> benthoses;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Phytoplankton> phytoplanktons;

    @ToMany(referencedJoinProperty = "fractureSurfaceId")
    private List<Zooplankton> zooplanktons;

    @ToOne(joinProperty = "id")
    private MonitoringSite monitoringSite;

    private Long monitorSiteId;
}
