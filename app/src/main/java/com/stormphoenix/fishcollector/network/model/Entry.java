package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */
public class Entry {
    @Expose
    public String entryId;
    // 任务接受的人
    @Expose
    public Acc acceptor;
    // 接受地点的id
    @Expose
    public String modelId;
    // 接受任务的类型
    @Expose
    public String modelType;
}
