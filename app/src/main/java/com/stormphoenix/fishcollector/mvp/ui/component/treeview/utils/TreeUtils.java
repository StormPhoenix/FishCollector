package com.stormphoenix.fishcollector.mvp.ui.component.treeview.utils;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeChooseHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeUtils {
    private static final String TAG = "TreeUtils";

    public static TreeNode createTaskTreeNode(Context context, BaseModel model, TreeChooseHolder.ItemChosenListener listener) {
        String modelClassName = model.getClass().getName();
        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(modelClassName);
        Log.e(TAG, "createTreeNode: " + model.getClass().getName());
        treeItem.setAttachedModel(model);
        /** ********* 设置对应的fragment ********** **/
//        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(context, ModelConstantMap.getHolder(modelClassName).fragmentClassName);
//        treeItem.setAttachedFragment(attachedFragment);
//        attachedFragment.setModel(model);

        TreeNode treeNode = new TreeNode(treeItem);
        TreeChooseHolder holder = new TreeChooseHolder(context);
        holder.setItemChosenListener(listener);
        treeNode.setViewHolder(holder);
        return treeNode;
    }

    /**
     * 根据 BaseModel 创建一个 TreeNode
     * @param context
     * @param model
     * @param listener
     * @return
     */
    public static TreeNode createTreeNode(Context context, BaseModel model, TreeAddDeleteHolder.ItemAddDeleteListener listener) {
        String modelClassName = model.getClass().getName();
        // 创建一个 TreeItem 保存 BaseModel 的信息
        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(modelClassName);
        Log.e(TAG, "createTreeNode: " + model.getClass().getName());
        treeItem.setAttachedModel(model);

        /** ********* 设置对应的 fragment， 将其添加到 TreeItem 里面 ********** **/
        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(context, ModelConstantMap.getHolder(modelClassName).fragmentClassName);
        treeItem.setAttachedFragment(attachedFragment);
        attachedFragment.setModel(model);

        // TreeItem 传入 TreeNode
        TreeNode treeNode = new TreeNode(treeItem);
        TreeAddDeleteHolder holder = new TreeAddDeleteHolder(context);
        holder.setItemOperationListener(listener);
        treeNode.setViewHolder(holder);
        return treeNode;
    }
}
