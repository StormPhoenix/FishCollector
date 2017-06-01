package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeBuilder {
    private static final String TAG = TreeBuilder.class.getName();

    private AndroidTreeView androidTreeView;
    private TreeNode root = null;
    private Context mContext;
    private DbManager dbManager;
    private List<MonitoringSite> data;
    private int treeHolderType = -1;

    private TreeViewListener listener = null;
    private TreeNode.TreeNodeClickListener nodeClickListener = null;

    public static final int TREE_HOLDER_ADD_AND_DELETE = 0;

    /**
     * @param context
     * @param data
     */
    public TreeBuilder(Context context, List<MonitoringSite> data, int treeHolderType, TreeViewListener listener) {
        this.mContext = context;
        this.data = data;
        this.treeHolderType = treeHolderType;
        this.listener = listener;
        dbManager = new DbManager(context);
    }

    public void buildTree() {
        handleRecursion();
        androidTreeView = new AndroidTreeView(mContext, root);
        androidTreeView.setDefaultAnimation(true);
        androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        androidTreeView.setDefaultViewHolder(createTreeNodeHolder().getClass());
        if (nodeClickListener != null) {
            androidTreeView.setDefaultNodeClickListener(nodeClickListener);
        }
    }

    public BaseFragment getRootFirstChildFragment() {
        if (root == null || root.getChildren() == null || root.getChildren().size() == 0) {
            return null;
        }

        return ((ITreeView.TreeItem) (root.getChildren().get(0).getValue())).getAttachedFragment();
    }

    private void handleRecursion() {
        root = TreeNode.root().getRoot();
        List<TreeNode> treeNodesList = new ArrayList<>();
        for (BaseModel obj : data) {
            List<TreeNode> tempNodes = new ArrayList<>();
            TreeNode tempNode;
            try {
                tempNode = createTreeNode(mContext, obj, createTreeNodeHolder());
                List<TreeNode> tempChildrenNodes = treeRecursion(obj);
                if (tempChildrenNodes != null && tempChildrenNodes.size() != 0) {
                    tempNode.addChildren(tempChildrenNodes);
                }
                tempNodes.add(tempNode);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (tempNodes != null && tempNodes.size() != 0) {
                treeNodesList.addAll(tempNodes);
            }
        }

        if (!treeNodesList.isEmpty()) {
            root.addChildren(treeNodesList);
        }
    }

    public void setNodeClickListener(TreeNode.TreeNodeClickListener listener) {
        this.nodeClickListener = listener;
    }
    /**
     * @param obj
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private List<TreeNode> treeRecursion(BaseModel obj) throws InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }

        List<TreeNode> childrenList = new ArrayList<>();
        // obj中的方法进行遍历，查找是否有下级节点
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getReturnType().equals(List.class)) {
                // 找到的下级节点组装成链表
                List<BaseModel> objList = (List<BaseModel>) method.invoke(obj, null);
                for (BaseModel mObj : objList) {
                    TreeNode tempNode = createTreeNode(mContext, mObj, createTreeNodeHolder());
                    List<TreeNode> tempChildNodes = treeRecursion(mObj);
                    tempNode.addChildren(tempChildNodes);
                    childrenList.add(tempNode);
                }
            }
        }
        return childrenList;
    }

    private TreeNode.BaseNodeViewHolder createTreeNodeHolder() {
        switch (treeHolderType) {
            case TREE_HOLDER_ADD_AND_DELETE:
                TreeAddDeleteHolder holder = new TreeAddDeleteHolder(mContext);
                holder.setItemOperationListener((TreeAddDeleteHolder.ItemAddDeleteListener) listener);
                return holder;
            default:
                return null;
        }
    }

    public TreeNode createTreeNode(Context context, BaseModel model, TreeNode.BaseNodeViewHolder holder) {
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
        treeNode.setViewHolder(holder);
        return treeNode;
    }

    public void addNode(TreeNode node, ITreeView.TreeItem item) {
        TreeNode parentTreeNode = null;
        TreeNode tempNode = null;

        if (node == null) {
            parentTreeNode = root;
        } else {
            parentTreeNode = node;
        }

        tempNode = new TreeNode(item);
        tempNode.setViewHolder(createTreeNodeHolder());

        parentTreeNode.getViewHolder().getTreeView().addNode(parentTreeNode, tempNode);
    }

    public View getView() {
        return androidTreeView.getView();
    }
}
