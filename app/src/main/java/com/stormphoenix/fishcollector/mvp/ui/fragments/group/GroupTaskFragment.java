package com.stormphoenix.fishcollector.mvp.ui.fragments.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.activities.DialogStyleActivity;
import com.stormphoenix.fishcollector.mvp.ui.activities.GroupTaskActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeBuilder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeViewListener;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddHolder;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-6-2.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupTaskFragment extends Fragment {
    public static final String TAG = GroupTaskFragment.class.getSimpleName();

    @BindView(R.id.text_no_data)
    TextView textNoData;
    private TreeBuilder treeBuilder;

    @BindView(R.id.group_tree_view_wrapper)
    FrameLayout treeViewWrapper;

    private View rootView;
    private TreeViewListener listener = null;

    public GroupTaskFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_group_task, container, false);
        ButterKnife.bind(this, rootView);
        initVariables();
        initTree();
        return rootView;
    }

    private void initVariables() {
        if (listener == null) {
            listener = new TreeAddHolder.ItemAddListener() {
                @Override
                public void onItemAddBtnClicked(TreeNode node, String key, String value) {
                    // 点击了之后，需要获取被点击节点的 modelId
                    String modelId = ((ITreeView.DataTreeItem) node.getValue()).getAttachedModel().getModelId();

                    Intent intent = new Intent(getActivity(), DialogStyleActivity.class);
                    intent.putExtra(DialogStyleActivity.SHOW_TYPE, DialogStyleActivity.SHOW_TYPE_MEMBERS);
                    intent.putExtra("model_id", modelId);
                    Log.e(TAG, "onItemAddBtnClicked: " + modelId);
                    getActivity().startActivityForResult(intent, GroupTaskActivity.REQUEST_CODE_CHOSE_MEMBER);
                }
            };
        }
    }

    private void initTree() {
        DbManager dbManager = new DbManager(getActivity());
        // 获取所有 MonitoringSite 数据
        List<MonitoringSite> datas = new ArrayList<>();
        if (FSManager.getInstance().getRecordContent() != null
                && FSManager.getInstance().getRecordContent().group.monitoringSiteIds != null) {
            for (String mId : FSManager.getInstance().getRecordContent().group.monitoringSiteIds) {
                MonitoringSite site = dbManager.queryMSiteByKey(mId);
                if (site != null) {
                    datas.add(site);
                }
            }
        }

        if (datas == null || datas.size() == 0) {
            textNoData.setVisibility(View.VISIBLE);
            return;
        } else {
            textNoData.setVisibility(View.GONE);
        }
        treeBuilder = new TreeBuilder(
                getActivity(),
                datas,
                TreeBuilder.TREE_HOLDER_ADD,
                listener,
                true
        );
        treeBuilder.buildTree();
        treeViewWrapper.removeAllViews();
        treeViewWrapper.addView(treeBuilder.getView());
        System.gc();
    }

    public void updateData() {
        initVariables();
        initTree();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
