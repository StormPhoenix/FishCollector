package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;

/**
 * Created by StormPhoenix on 17-5-28.
 * StormPhoenix is a intelligent Android developer.
 */

public class TaskEntry {
    @Expose
    public String modelId;
    @Expose
    public long modifyTime;
    @Expose
    public long accessTime;
}
