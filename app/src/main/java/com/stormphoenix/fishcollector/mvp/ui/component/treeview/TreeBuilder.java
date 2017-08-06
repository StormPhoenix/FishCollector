package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeMemberHolder;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.network.model.TaskEntry;
import com.stormphoenix.fishcollector.network.model.TaskTable;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class TreeBuilder {
    public static final int TREE_HOLDER_ADD_AND_DELETE = 0;
    public static final int TREE_HOLDER_ADD = 1;
    private static final String TAG = TreeBuilder.class.getName();
    private AndroidTreeView androidTreeView;
    private TreeNode root = null;
    private Context mContext;
    private List<MonitoringSite> data;
    private int treeHolderType = -1;
    private boolean isTaskDispatch = false;
    private TreeViewListener listener = null;
    private TreeNode.TreeNodeClickListener nodeClickListener = null;

    public TreeBuilder(Context context, List<MonitoringSite> data, int treeHolderType, TreeViewListener listener, boolean isTaskDispatch) {
        this.mContext = context;
        this.data = data;
        this.treeHolderType = treeHolderType;
        this.listener = listener;
        this.isTaskDispatch = isTaskDispatch;
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

        return ((ITreeView.DataTreeItem) (root.getChildren().get(0).getValue())).getAttachedFragment();
    }

    private void handleRecursion() {
        // 先创建根节点
        root = TreeNode.root().getRoot();
        // treeNodesList 用于存放 root 的下级节点
        List<TreeNode> treeNodesList = new ArrayList<>();
        for (BaseModel obj : data) {
            // tempNodes 用于存放每个 obj 生成的 TreeNode 的下级节点
            List<TreeNode> tempNodes = new ArrayList<>();
            // tempNode 是根据 obj 生成的 BaseModel
            TreeNode tempNode;
            try {
                // 创建该节点
                tempNode = createDataTreeNode(mContext, obj, createTreeNodeHolder());
                /*
                 * 如果 isTaskDispatch 为 true，那么 tempNode 下面需要添加成员节点
                 */
                List<TreeNode> childrenNodes = new ArrayList<>();
                if (isTaskDispatch) {
                    List<TreeNode> memberNodes = createMembersOfNode(obj);
                    if (memberNodes != null) {
                        childrenNodes.addAll(memberNodes);
                    }
                }
                List<TreeNode> temp = treeRecursion(obj);
                if (temp != null && temp.size() != 0) {
                    childrenNodes.addAll(temp);
                }
                if (childrenNodes != null && childrenNodes.size() != 0) {
                    tempNode.addChildren(childrenNodes);
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

    private List<TreeNode> createMembersOfNode(BaseModel model) {
        // **************************
        if (model.getModelId().startsWith("MEA")) {
            System.out.print("stop");
        }
        // **************************
        // 用户结果集合
        List<String> userList = new ArrayList<>();
        TaskTable taskTable = FSManager.getInstance().getRecordContent().taskTable;
        if (taskTable != null && taskTable.taskEntries != null) {
            Iterator<Map.Entry<String, Set<TaskEntry>>> memberIter = taskTable.taskEntries.entrySet().iterator();
            // 遍历每一个用户的分配任务的集合
            while (memberIter.hasNext()) {
                Map.Entry<String, Set<TaskEntry>> entry = memberIter.next();
                // 用户名
                String username = entry.getKey();
                // 用户任务
                Set<TaskEntry> userTask = entry.getValue();
                if (isUserDispatchTask(model, userTask)) {
                    userList.add(username);
                }
            }
            if (userList.size() != 0) {
                List<TreeNode> treeNodes = new ArrayList<>();
                for (String username : userList) {
                    treeNodes.add(createMemberTreeNode(mContext, username));
                }
                return treeNodes;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private boolean isUserDispatchTask(BaseModel model, Set<TaskEntry> userTask) {
        Iterator<TaskEntry> taskIter = userTask.iterator();
        // 遍历该用户所有的任务
        while (taskIter.hasNext()) {
            TaskEntry taskEntry = taskIter.next();
            if (taskEntry.modelId.equals(model.getModelId())) {
                return true;
            }
        }
        return false;
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

        if (isTaskDispatch) {
            List<TreeNode> memberNodes = createMembersOfNode(obj);
            if (memberNodes != null) {
                childrenList.addAll(memberNodes);
            }
        }
        // obj中的方法进行遍历，查找是否有下级节点
        Method[] methods = obj.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && method.getReturnType().equals(List.class)) {
                // 找到的下级节点组装成链表
                List<BaseModel> objList = (List<BaseModel>) method.invoke(obj, null);
                for (BaseModel mObj : objList) {
                    TreeNode tempNode = createDataTreeNode(mContext, mObj, createTreeNodeHolder());
                    /*
                    * 如果 isTaskDispatch 为 true，那么 tempNode 下面需要添加成员节点
                    */
                    List<TreeNode> childrenNodes = new ArrayList<>();
                    List<TreeNode> temp = treeRecursion(mObj);
                    if (temp != null && temp.size() != 0) {
                        childrenNodes.addAll(temp);
                    }
                    tempNode.addChildren(childrenNodes);
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
            case TREE_HOLDER_ADD:
                TreeAddHolder holder1 = new TreeAddHolder(mContext);
                holder1.setItemOperationListener((TreeAddHolder.ItemAddListener) listener);
                return holder1;
            default:
                return null;
        }
    }

    public TreeNode createDataTreeNode(Context context, BaseModel model, TreeNode.BaseNodeViewHolder holder) {
        String modelClassName = model.getClass().getName();
        // 创建一个 DataTreeItem 保存 BaseModel 的信息
        ITreeView.DataTreeItem dataTreeItem = new ITreeView.DataTreeItem(modelClassName);
        Log.e(TAG, "createTreeNode: " + model.getClass().getName());
        dataTreeItem.setAttachedModel(model);

        /** ********* 设置对应的 fragment， 将其添加到 DataTreeItem 里面 ********** **/
        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(context, ModelConstantMap.getHolder(modelClassName).fragmentClassName);
        dataTreeItem.setAttachedFragment(attachedFragment);
        attachedFragment.setModel(model);

        // DataTreeItem 传入 TreeNode
        TreeNode treeNode = new TreeNode(dataTreeItem);
        treeNode.setViewHolder(holder);
        return treeNode;
    }

    public TreeNode createMemberTreeNode(Context context, String username) {
        ITreeView.MemberTreeItem memberTreeItem = new ITreeView.MemberTreeItem(username);

        // DataTreeItem 传入 TreeNode
        TreeNode treeNode = new TreeNode(memberTreeItem);
        TreeMemberHolder holder = new TreeMemberHolder(context);
        treeNode.setViewHolder(holder);
        return treeNode;
    }

    public void addNode(TreeNode node, ITreeView.DataTreeItem item) {
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
