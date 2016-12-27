package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.content.Context;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
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
    private ITreeView.Listener listener = null;
    private AndroidTreeView treeView = null;
    private TreeNode root = null;
    private Context context = null;
    private DbManager dbManager = null;

    public TreeViewImpl(Context context) {
        this.context = context;
        dbManager = new DbManager(context);
        root = TreeNode.root();
        List<TreeNode> treeNodesList = new ArrayList<>();

        List<MonitoringSite> monitoringSites = dbManager.queryAll();
        for (Object obj : monitoringSites) {
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

        treeView = new AndroidTreeView(context, root);
        treeView.setDefaultAnimation(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        treeView.setDefaultViewHolder(TreeItemHolder.class);
    }

    private TreeNode treeRecursion(Object obj) throws InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        // 判断 obj 的类型，并将其存储
        TreeNode treeNode = new TreeNode(new ITreeView.TreeItem(obj.getClass().getName()));
        List<TreeNode> childrenList = new ArrayList<>();

        // obj中的方法进行遍历，查找是否有下级节点
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getReturnType().equals(List.class)) {
                List<Object> objList = (List<Object>) method.invoke(obj, null);
                for (Object mObj : objList) {
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
        return treeView.getView();
    }

    @Override
    public void setAddClickListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addNode(TreeNode node, TreeItem item) {
        if (node == null) {
            TreeNode treeNode = new TreeNode(new TreeItem(MonitoringSite.class.getName()));
            treeView.addNode(root, treeNode);
        }
    }

    @Override
    public void deleteNode(TreeNode node) {

    }
}
