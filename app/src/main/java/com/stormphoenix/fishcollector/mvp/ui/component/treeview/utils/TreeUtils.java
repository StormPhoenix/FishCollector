package com.stormphoenix.fishcollector.mvp.ui.component.treeview.utils;

import android.app.Fragment;
import android.content.Context;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeUtils {
    public static TreeNode createTreeNode(Context context, BaseModel model, TreeItemHolder.ItemOperationListener listener) {
        String modelClassName = model.getClass().getName();
        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(modelClassName);
        treeItem.setAttachedModel(model);

        /** ********* 设置对应的fragment ********** **/
        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(context, ModelConstantMap.getHolder(modelClassName).fragmentClassName);
        treeItem.setAttachedFragment(attachedFragment);
        attachedFragment.setModel(model);

        TreeNode treeNode = new TreeNode(treeItem);
        TreeItemHolder holder = new TreeItemHolder(context);
        holder.setItemOperationListener(listener);
        treeNode.setViewHolder(holder);
        return treeNode;
    }
}
