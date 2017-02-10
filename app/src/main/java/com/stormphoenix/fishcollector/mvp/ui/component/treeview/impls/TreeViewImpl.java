package com.stormphoenix.fishcollector.mvp.ui.component.treeview.impls;

import android.app.Fragment;
import android.content.Context;
import android.view.View;

import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.utils.TreeUtils;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 16-12-26.
 * Wang Cheng is a intelligent Android developer.
 */

public class TreeViewImpl implements ITreeView {
    public static final String TAG = "ITreeView";

    private AndroidTreeView androidTreeView = null;
    private TreeNode root = null;
    private Context context = null;
    private DbManager dbManager = null;

    private TreeItemHolder.ItemOperationListener listener = null;
    private TreeNode.TreeNodeClickListener nodeClickListener = null;

    public TreeViewImpl(Context context) {
        this.context = context;
        dbManager = new DbManager(context);
    }

    @Override
    public void buildTree() {
        root = TreeNode.root();
        List<TreeNode> treeNodesList = new ArrayList<>();
        List<MonitoringSite> monitoringSites = dbManager.queryAll();
        for (BaseModel obj : monitoringSites) {
            TreeNode tempNode = null;
            try {
                tempNode = treeRecursion(obj);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (tempNode != null) {
                treeNodesList.add(tempNode);
            }
        }

        if (!treeNodesList.isEmpty()) {
            root.addChildren(treeNodesList);
        }

        androidTreeView = new AndroidTreeView(context, root);
        androidTreeView.setDefaultAnimation(true);
        if (nodeClickListener != null) {
            androidTreeView.setDefaultNodeClickListener(nodeClickListener);
        }
//        tView.setDefaultNodeLongClickListener(nodeLongClickListener);
    }

    @Override
    public void setNodeClickListener(TreeNode.TreeNodeClickListener listener) {
        this.nodeClickListener = listener;
    }

    private TreeNode treeRecursion(BaseModel obj) throws InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        // 判断 obj 的类型，并将其存储
        TreeNode treeNode = TreeUtils.createTreeNode(context, obj, listener);
        List<TreeNode> childrenList = new ArrayList<>();

        // obj中的方法进行遍历，查找是否有下级节点
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getReturnType().equals(List.class)) {
                List<BaseModel> objList = (List<BaseModel>) method.invoke(obj, null);
                for (BaseModel mObj : objList) {
                    TreeNode tempNode = treeRecursion(mObj);
                    if (tempNode != null) {
                        childrenList.add(tempNode);
                    }
                }
            }
        }
        if (!childrenList.isEmpty()) {
            treeNode.addChildren(childrenList);
        }
        return treeNode;
    }

    @Override
    public View getView() {
        return androidTreeView.getView();
    }

    @Override
    public BaseFragment getRootFirstChildFragment() {
        if (root == null || root.getChildren() == null || root.getChildren().size() == 0) {
            return null;
        }

        return ((TreeItem) (root.getChildren().get(0).getValue())).getAttachedFragment();
    }

    @Override
    public void setItemOprationListener(TreeItemHolder.ItemOperationListener listener) {
        this.listener = listener;
    }

    @Override
    public void addNode(TreeNode node, TreeItem item) {
        TreeNode parentTreeNode = null;
        TreeNode tempNode = null;

        if (node == null) {
            parentTreeNode = root;
        } else {
            parentTreeNode = node;
        }

        tempNode = new TreeNode(item);
        TreeItemHolder treeItemHolder = new TreeItemHolder(context);
        treeItemHolder.setItemOperationListener(listener);
        tempNode.setViewHolder(treeItemHolder);

        parentTreeNode.getViewHolder().getTreeView().addNode(parentTreeNode, tempNode);
    }

    @Override
    public void deleteNode(TreeNode node) {
        androidTreeView.removeNode(node);
    }
}
