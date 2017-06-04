package com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces;

import android.view.View;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public interface ITreeView {
    View getView();

    BaseFragment getRootFirstChildFragment();

    void buildTree();

    void setNodeClickListener(TreeNode.TreeNodeClickListener listener);

    void setItemOprationListener(TreeAddDeleteHolder.ItemAddDeleteListener listener);

    void addNode(TreeNode node, DataTreeItem item);

    void deleteNode(TreeNode node);

    public static class MemberTreeItem {
        private String username;

        public MemberTreeItem(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    public static class DataTreeItem {
        public DataTreeItem(String modelConstant) {
            this.modelConstant = modelConstant;
        }

        public String modelConstant;

        private BaseFragment attachedFragment = null;

        private BaseModel attachedModel = null;

        public BaseModel getAttachedModel() {
            return attachedModel;
        }

        public void setAttachedModel(BaseModel obj) {
            this.attachedModel = obj;
        }

        public BaseFragment getAttachedFragment() {
            return attachedFragment;
        }

        public void setAttachedFragment(BaseFragment attachedFragment) {
            this.attachedFragment = attachedFragment;
        }
    }
}
