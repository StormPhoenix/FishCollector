package com.stormphoenix.fishcollector.mvp.ui.fragments.group;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.BaseRecyclerAdapter;
import com.stormphoenix.fishcollector.adapter.GroupAdapter;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.ListBaseFragment;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.Acc;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.NetManager;
import com.stormphoenix.fishcollector.shared.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class GroupMemberFragment extends ListBaseFragment<Acc> {
    public static final String TAG = GroupMemberFragment.class.getName();
    private final static int REFRESH_FROM_LOCAL = 0;
    View view;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_FROM_LOCAL:
                    refreshDataFromLocal();
                    break;
            }
        }
    };

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

    /**
     * 刷新组成员的数据，如果网络许可，则从网络上拉取数据；
     * 否则从本地获取数据；
     */
    public void refreshData() {
        if (NetManager.isNetworkWorkWell(getActivity())) {
            // 网络可用，调用网络接口重新下载数据
            HttpMethod.getInstance().login(ConfigUtils.getInstance().getUsername(),
                    ConfigUtils.getInstance().getPassword(),
                    new RequestCallback<HttpResult<GroupRecord>>() {
                        @Override
                        public void beforeRequest() {
                            // do nothing
                        }

                        @Override
                        public void success(final HttpResult<GroupRecord> data) {
                            // 一般来说登录失败是不可能出现的，除非数据错乱；为了保险起见，还是加上登录失败的判断
                            if (data.getResultCode() == Constants.LOGIN_FAILED) {
                                // 登录失败，说明有某个部位出现了数据错乱，请重新登录
                                ConfigUtils.getInstance().setUserLogout();
                                ConfigUtils.getInstance().setUserGroupId(null);
                                ConfigUtils.getInstance().setUserInfo(null, null);
                                new DbManager(getActivity()).deleteAll();
                                Snackbar.make(view, "请重新登录", Snackbar.LENGTH_SHORT).show();
                                return;
                            } else if (data.getResultCode() == Constants.LOGIN_SUCCESS_IN_GROUP) {
                                // 登录成功，并且在组内，保存用户数据
                                ConfigUtils.getInstance().setUserGroupId(data.getData().group.groupId);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 保存信息
                                        FSManager.getInstance().saveRecordContent(data.getData());
                                        mHandler.sendEmptyMessage(REFRESH_FROM_LOCAL);
                                    }
                                }).start();
                            } else {
                                // 其他情况不考虑
                            }
                        }

                        @Override
                        public void onError(String errorMsg) {
                        }
                    });
        } else {
            refreshDataFromLocal();
        }
    }

    private void refreshDataFromLocal() {
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
