package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;

import java.util.List;

/**
 * Created by StormPhoenix on 17-5-28.
 * StormPhoenix is a intelligent Android developer.
 */

public class Group {
    @Expose
    public String groupId;
    @Expose
    public String groupName;
    @Expose
    public Acc header;
    @Expose
    public List<Acc> members;
    @Expose
    public List<MonitoringSite> monitoringSites;
}