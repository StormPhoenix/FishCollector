package com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces;

import android.view.View;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.unnamed.b.atv.model.TreeNode;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public interface ITreeView {
    View getView();

    void buildTree();

    void setItemOprationListener(TreeItemHolder.ItemOperationListener listener);

    void addNode(TreeNode node, TreeItem item);

    void deleteNode(TreeNode node);

    public static class TreeItem {
        public TreeItem(String modelConstant) {
            this.modelConstant = modelConstant;
        }

        public String modelConstant;

        private BaseModel attachedModel = null;

        public BaseModel getAttachedModel() {
            return attachedModel;
        }

        public void setAttachedModel(BaseModel obj) {
            this.attachedModel = obj;
        }
    }
}
