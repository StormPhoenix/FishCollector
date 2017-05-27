package com.stormphoenix.fishcollector.mvp.ui.component.treeview;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.network.model.DispatchTable;
import com.stormphoenix.fishcollector.network.model.Entry;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
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

    private final Group group;
    private final DispatchTable dispatchTable;
    private final List<Entry> entries;
    // 树形结构每一项的样式类型
    private TreeNode.BaseNodeViewHolder treeItemHolder = null;
//    public static final int ADD_DELETE_TYPE = 1;
//    public static final int CHOOSE_TYPE = 2;
//    public static final int DISPLAY_TYPE = 3;

    // 组长的树还是组员的树
    public static final int HEADER_TREE_TYPE = 1;
    public static final int MEMBER_TREE_TYPE = 2;

    private int treeManagerType = 0;
    private Context mContext;

    private TreeNode root = null;
    private AndroidTreeView androidTreeView;

    private DbManager dbManager;

    /**
     * @param context
     * @param holder     是 ItemAddDel、ItemChoose、ItemDisplay
     * @param group      所在的组别
     * @param table      分配表
     * @param manageType 是组长还是组员的节点树木
     */
    public TreeBuilder(Context context, TreeNode.BaseNodeViewHolder holder, Group group, DispatchTable table, int manageType) {
        this.treeItemHolder = holder;
        this.treeManagerType = manageType;
        this.group = group;
        this.dispatchTable = table;
        this.entries = dispatchTable.entries;
        dbManager = new DbManager(context);
    }

    public void buildTree() {
        root = TreeNode.root().getRoot();
        List<TreeNode> treeNodesList = new ArrayList<>();
        if (treeManagerType == HEADER_TREE_TYPE) {
            handleRecursion(treeItemHolder, treeNodesList, true, null);
        } else if (treeManagerType == MEMBER_TREE_TYPE) {
            // 只显示组员负责的节点
            List<Entry> responseIds = new ArrayList<>();
            // 先找出组员负责的区域
            for (Entry entry : entries) {
                if (entry.acceptor.name.equals(ConfigUtils.getInstance().getUsername())) {
                    // 说明该节点由自己负责
                    responseIds.add(entry);
                }
            }
            handleRecursion(treeItemHolder, treeNodesList, false, responseIds);
        }
        androidTreeView = new AndroidTreeView(mContext, root);
        androidTreeView.setDefaultAnimation(true);
        androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        androidTreeView.setDefaultViewHolder(TreeAddDeleteHolder.class);
    }

    public BaseFragment getRootFirstChildFragment() {
        if (root == null || root.getChildren() == null || root.getChildren().size() == 0) {
            return null;
        }

        return ((ITreeView.TreeItem) (root.getChildren().get(0).getValue())).getAttachedFragment();
    }

    private void handleRecursion(TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> holder, List<TreeNode> treeNodesList, boolean isHeaderTree, List<Entry> entries) {
        List<MonitoringSite> monitoringSites = group.monitoringSites;
        for (MonitoringSite obj : monitoringSites) {
            List<TreeNode> tempNodes = new ArrayList<>();
            try {
                if (isHeaderTree) {
                    TreeNode tempNode = createTreeNode(mContext, obj, holder);
                    List<TreeNode> tempChildrenNodes = treeRecursion(obj, holder, true, null);
                    if (tempChildrenNodes != null && tempChildrenNodes.size() != 0) {
                        tempNode.addChildren(tempChildrenNodes);
                    }
                    tempNodes.add(tempNode);
                } else {
                    boolean isDispatched = false;
                    for (Entry entry : entries) {
                        if (entry.modelId.equals(obj.getModelId())) {
                            // 说明该点已经被分配了
                            isDispatched = true;
                            entries.remove(entry);
                            TreeNode tempNode = createTreeNode(mContext, obj, holder);
                            List<TreeNode> tempChildrenNodes = treeRecursion(obj, holder, true, entries);
                            tempNode.addChildren(tempChildrenNodes);
                            tempNodes.add(tempNode);
                            break;
                        }
                    }

                    if (!isDispatched) {
                        // 说明该点没有被分配，但说不定下一点有分配过
                        List<TreeNode> tempChildNodes = treeRecursion(obj, holder, false, entries);
                        if (tempChildNodes != null && tempChildNodes.size() != 0) {
                            tempNodes.addAll(tempChildNodes);
                        }
                    }
                }
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

    /**
     * @param obj
     * @param holder
     * @param isDispatched 代表 obj 对象是否被分配过
     * @param entries
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private List<TreeNode> treeRecursion(BaseModel obj, TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> holder, boolean isDispatched, List<Entry> entries) throws InvocationTargetException, IllegalAccessException {
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
                    if (isDispatched) {
                        // 如果 obj 节点被分配了，那么其下层所有节点都要被分配
                        TreeNode tempNode = createTreeNode(mContext, mObj, holder);
                        List<TreeNode> tempChildNodes = treeRecursion(mObj, holder, true, null);
                        tempNode.addChildren(tempChildNodes);
                        childrenList.add(tempNode);
                    } else {
                        boolean isDis = false;
                        for (Entry entry : entries) {
                            if (entry.modelId.equals(mObj.getModelId())) {
                                // 如果 mObj 被分配了
                                isDis = true;
                                TreeNode tempNode = createTreeNode(mContext, mObj, holder);
                                List<TreeNode> tempChildNodes = treeRecursion(mObj, holder, true, null);
                                tempNode.addChildren(tempChildNodes);
                                childrenList.add(tempNode);
                                break;
                            }
                        }
                        if (!isDis) {
                            // 如果当前没有被分配，则可能下一级节点会被分配
                            List<TreeNode> tempChildNodes = treeRecursion(mObj, holder, false, entries);
                            if (tempChildNodes != null && tempChildNodes.size() != 0) {
                                childrenList.addAll(tempChildNodes);
                            }
                        }
                    }
                }
            }
        }
        return childrenList;
    }

    public static TreeNode createTreeNode(Context context, BaseModel model, TreeNode.BaseNodeViewHolder<ITreeView.TreeItem> treeItemHolder) {
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
        if (treeItemHolder == null) {
            treeNode.setViewHolder(treeItemHolder);
        }
        return treeNode;
    }

    public AndroidTreeView getAndroidTreeView() {
        return androidTreeView;
    }
}
