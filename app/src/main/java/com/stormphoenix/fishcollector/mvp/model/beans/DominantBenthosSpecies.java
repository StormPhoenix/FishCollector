package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 底栖生物优势种
 * Created by Phoenix on 2016/5/31.
 */
public class DominantBenthosSpecies {
    @Unique
    private String sampleID;
    @Nullable
    private String name;
    @Nullable
    private String photo;
    @Nullable
    private float quality;
    @Nullable
    private float biomass;
    @Nullable
    private String idBenthos;

    // ********************************
    @Id
    private Long id;

    @ToOne(joinProperty = "id")
    private Benthos benthos;

    private Long benthosId;
}
