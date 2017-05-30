package com.stormphoenix.fishcollector.network.model;

import com.google.gson.annotations.Expose;

import java.util.Map;
import java.util.Set;

/**
 * Created by StormPhoenix on 17-5-28.
 * StormPhoenix is a intelligent Android developer.
 */

public class TaskTable {
    // 组的唯一标识
    @Expose
    public String groupId;
    // 保存组成员的任务分配表
    @Expose
    public Map<String, Set<TaskEntry>> taskEntries;
}
