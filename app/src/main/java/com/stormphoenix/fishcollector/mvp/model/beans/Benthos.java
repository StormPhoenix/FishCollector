package com.stormphoenix.fishcollector.mvp.model.beans;

import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;

/**
 * 底栖生物
 * Created by Phoenix on 2016/5/31.
 */

@Entity(nameInDb = "benthos")
public class Benthos {
    //地栖生物主键
    @Unique
    private String sampleID = null;

    //照片路径
    @Nullable
    private String photo = null;

    //数量
    @NotNull
    private int quality = 0;

    //生物量
    @NotNull
    private float biomass = 0;
    //断面外键
    private String idFractureSurface = null;

    // ***************************************

    @ToOne(joinProperty = "greenId")
    private FractureSurface fractureSurface;

    @Id
    private Long id;

    private Long fractureSurfaceId;

    @ToMany(referencedJoinProperty = "benthosId")
    private List<DominantBenthosSpecies> dominantBenthosSpecies;
}
