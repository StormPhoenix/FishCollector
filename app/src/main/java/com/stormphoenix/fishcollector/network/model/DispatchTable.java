package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class DispatchTable {
    // "DIS" + 时间
    @Expose
    public String tableId;
    // 用来链接组的外建
    @Expose
    public Group group;
    @Expose
    public List<Entry> entries;
}
