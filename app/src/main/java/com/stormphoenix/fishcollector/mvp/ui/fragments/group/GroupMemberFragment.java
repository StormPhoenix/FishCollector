package com.stormphoenix.fishcollector.mvp.ui.fragments.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.BaseRecyclerAdapter;
import com.stormphoenix.fishcollector.adapter.GroupAdapter;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.ListBaseFragment;
import com.stormphoenix.fishcollector.network.model.Acc;
import com.stormphoenix.fishcollector.network.model.GroupRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupMemberFragment extends ListBaseFragment<Acc> {
    public static final String TAG = GroupMemberFragment.class.getName();
    View view;

    public GroupMemberFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        refreshData();
        return view;
    }

    public void refreshData() {
        GroupRecord recordContent = FSManager.getInstance().getRecordContent();
        if (recordContent == null || recordContent.group == null) {
            return;
        }

        List<Acc> members = new ArrayList<>();
        members.add(recordContent.group.header);
        if (recordContent.group.members != null && recordContent.group.members.size() != 0) {
            // 组内没有任何成员
            for (Acc acc : recordContent.group.members) {
                members.add(acc);
            }
        } else {
            Log.e(TAG, "refreshData: no members !!!");
        }
        emptyWrapper.setVisibility(View.GONE);
        mRefreshLayout.setRefreshing(false);
        setupGroupInfo(members);
    }

    private void setupGroupInfo(List<Acc> data) {
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerAdapter<Acc> getAdapter() {
        GroupAdapter adapter = new GroupAdapter(getActivity(), new ArrayList<Acc>());
        adapter.addOnViewClickListener(R.id.text_username, new BaseRecyclerAdapter.OnInternalViewClickListener<Acc>() {
            @Override
            public void onClick(View parentV, View v, Integer position, Acc values) {

            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, Acc values) {
                return false;
            }
        });
        return adapter;
    }
}
