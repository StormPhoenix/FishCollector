package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;

/**
 * Created by StormPhoenix on 17-5-28.
 * StormPhoenix is a intelligent Android developer.
 */

public class Acc {
    @Expose
    public String name;
    @Expose
    public String password;
    @Expose
    public boolean inGroup;
    @Expose
    public String groupId;
}
