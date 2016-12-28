package com.stormphoenix.fishcollector.mvp.ui.component.treeview.utils;

import android.content.Context;

import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeUtils {
    public static TreeNode createTreeNode(Context context, String modelClassName, TreeItemHolder.ItemOperationListener listener) {
        TreeNode treeNode = new TreeNode(new ITreeView.TreeItem(modelClassName));
        TreeItemHolder holder = new TreeItemHolder(context);
        holder.setItemOperationListener(listener);
        treeNode.setViewHolder(holder);
        return treeNode;
    }
}
