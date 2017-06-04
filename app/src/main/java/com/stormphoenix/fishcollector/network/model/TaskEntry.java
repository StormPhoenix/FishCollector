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

    public TaskEntry() {
    }

    public TaskEntry(String modelId) {
        this.modelId = modelId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskEntry) {
            if (((TaskEntry) obj).modelId.equals(modelId)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return modelId.hashCode();
    }
}
