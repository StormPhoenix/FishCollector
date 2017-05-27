package com.stormphoenix.fishcollector;

import com.stormphoenix.fishcollector.network.model.DispatchTable;
import com.stormphoenix.fishcollector.network.model.Group;

import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class Locals {
    public static List<Group> userGroups = null;
    public static List<DispatchTable> dispatchTables = null;

    // 指示当前组在 userGroups 中的位置
    public static int currentGroup = 0;

    // 指示当前分配表在 dispatchTables 中的位置
    public static int currentDispatchTable = 0;

    // 是否是当前组的组长
    public static boolean isHeader = false;
}
