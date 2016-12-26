package com.stormphoenix.fishcollector.mvp.model.beans;


import android.support.annotation.Nullable;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 渔获物
 * Created by Phoenix on 2016/5/31.
 */
public class Catches {

    //主键
    @Unique
    private String sampleID = null;
    //鱼类名称
    @Nullable
    private String name = null;
    //图片路径
    @Nullable
    private String photo = null;
    //卵苗总数
    @Nullable
    private int totalQuality;
    //鱼卵总数
    @Nullable
    private int eggQuality;
    //幼鱼数
    @Nullable
    private int fryQuality;
    //水层外键
    @Nullable
    private String idWaterLayer = null;

    // *******************************
    @Id
    private Long id;

    private Long waterLayerId;

    @ToOne(joinProperty = "greenId")
    private WaterLayer waterLayer;
}
