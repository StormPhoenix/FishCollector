package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.content.Context;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.ModelConstant;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeViewImpl implements ITreeView {
    private ITreeView.Listener listener = null;
    private AndroidTreeView treeView = null;
    private TreeNode root = null;
    private Context context = null;

    public TreeViewImpl(Context context) {
        this.context = context;
        root = TreeNode.root();

        TreeNode computerRoot = new TreeNode(new ITreeView.TreeItem(ModelConstant.BENTHOS));
        TreeNode myDocuments = new TreeNode(new ITreeView.TreeItem(ModelConstant.FISH_EGGS));
        TreeNode downloads = new TreeNode(new ITreeView.TreeItem(ModelConstant.CATCHES));

        root.addChildren(computerRoot, myDocuments, downloads);

        treeView = new AndroidTreeView(context, root);
        treeView.setDefaultAnimation(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        treeView.setDefaultViewHolder(TreeItemHolder.class);
    }

    @Override
    public View getView() {
        return treeView.getView();
    }

    @Override
    public void setAddClickListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addNode(TreeNode node, TreeItem item) {
    }

    @Override
    public void deleteNode(TreeNode node) {

    }
}
