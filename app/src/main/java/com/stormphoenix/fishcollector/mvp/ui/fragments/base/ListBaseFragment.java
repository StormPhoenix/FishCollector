package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.BaseRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public abstract class ListBaseFragment<T> extends Fragment {
    @BindView(R.id.recycler_view)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    protected LinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;

    protected View rootView = null;
    @BindView(R.id.empty_group_wrapper)
    protected RelativeLayout emptyWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        initListItemView();
        return rootView;
    }

    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    public void initListItemView() {
        if (mAdapter == null) {
            mAdapter = getAdapter();
        }

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public int getListItemCounts() {
        return mAdapter.getItemCount();
    }

    public void refresh() {
        mRefreshLayout.setRefreshing(true);
    }

    public void unRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    public abstract BaseRecyclerAdapter<T> getAdapter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
