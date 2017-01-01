package com.stormphoenix.fishcollector.mvp.ui.component.treeview.utils;

import android.content.Context;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeUtils {
    public static TreeNode createTreeNode(Context context, BaseModel obj, TreeItemHolder.ItemOperationListener listener) {
        String modelClassName = obj.getClass().getName();
        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(modelClassName);
        treeItem.setAttachedModel(obj);

        TreeNode treeNode = new TreeNode(treeItem);
        TreeItemHolder holder = new TreeItemHolder(context);
        holder.setItemOperationListener(listener);
        treeNode.setViewHolder(holder);
        return treeNode;
    }
}
