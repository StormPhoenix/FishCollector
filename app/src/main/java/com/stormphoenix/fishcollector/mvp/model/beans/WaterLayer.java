package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class WaterLayer {
    @Unique
    private String id;
    @Nullable
    private String layer;
    @Nullable
    private float depth;
    @Nullable
    private float temperature;
    @Nullable
    private float waterLevel;
    @Nullable
    private float velocity;
    @Nullable
    private String idMeasuringPoint;

    // *****************************
    @Id
    private Long greenId;

    @ToMany(referencedJoinProperty = "waterLayerId")
    private List<Catches> catches;
}
