package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * Created by Phoenix on 2016/5/31.
 */
public class Zooplankton {
    @Unique
    private String SampleID;
    @Nullable
    private String Photo;
    @Nullable
    private int Quality;
    @Nullable
    private float Biomass;
    @Nullable
    private String ID_FractureSurface;

    // **********************************
    @Id
    private Long id;

    private Long fractureSurfaceId;

    @ToMany(referencedJoinProperty = "zooplanktonId")
    private List<DominantZooplanktonSpecies> dominantZooplanktonSpecies;
}
