package com.stormphoenix.fishcollector.mvp.ui.fragments.group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.Locals;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.BaseRecyclerAdapter;
import com.stormphoenix.fishcollector.adapter.GroupAdapter;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.ListBaseFragment;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.shared.ConfigUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupFragment extends ListBaseFragment<Group> {

    public static final int MANAGE_GROUP_TYPE = 1;
    public static final int JOIN_GROUP_TYPE = 2;

    private final int groupType;

    View view;

    public GroupFragment(int groupType) {
        this.groupType = groupType;
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
        List<Group> groupList = new ArrayList<>();
        if (groupType == MANAGE_GROUP_TYPE) {
            if (Locals.userGroups != null && Locals.userGroups.size() != 0) {
                for (Group group : Locals.userGroups) {
                    if (group.header.name.equals(ConfigUtils.getInstance().getUsername())) {
                        groupList.add(group);
                    }
                }
            }
            if (groupList.size() == 0) {
                emptyWrapper.setVisibility(View.VISIBLE);
            } else {
                setupGroupInfo(groupList);
                emptyWrapper.setVisibility(View.GONE);
            }
        } else if (groupType == JOIN_GROUP_TYPE) {
            if (Locals.userGroups != null && Locals.userGroups.size() != 0) {
                for (Group group : Locals.userGroups) {
                    if (!group.header.name.equals(ConfigUtils.getInstance().getUsername())) {
                        groupList.add(group);
                    }
                }
            }
            if (groupList.size() == 0) {
                emptyWrapper.setVisibility(View.VISIBLE);
            } else {
                emptyWrapper.setVisibility(View.GONE);
                setupGroupInfo(groupList);
            }
        }
        mRefreshLayout.setRefreshing(false);
    }

    private void setupGroupInfo(List<Group> data) {
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    //    @Override
    public BaseRecyclerAdapter<Group> getAdapter() {
        GroupAdapter adapter = new GroupAdapter(getActivity(), new ArrayList<Group>());
        adapter.addOnViewClickListener(R.id.text_group_name, new BaseRecyclerAdapter.OnInternalViewClickListener<Group>() {
            @Override
            public void onClick(View parentV, View v, Integer position, Group values) {

            }

            @Override
            public boolean onLongClick(View parentV, View v, Integer position, Group values) {
                return false;
            }
        });
        return adapter;
    }
}
