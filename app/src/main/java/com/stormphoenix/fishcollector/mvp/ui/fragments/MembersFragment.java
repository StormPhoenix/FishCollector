package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.adapter.BaseRecyclerAdapter;
import com.stormphoenix.fishcollector.adapter.MembersAdapter;
import com.stormphoenix.fishcollector.mvp.model.beans.Account;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.ListBaseFragment;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.apis.MembersApi;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.NetManager;
import com.stormphoenix.fishcollector.shared.constants.MembersConstants;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public class MembersFragment extends ListBaseFragment<Account> {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMembersInfo();
            }
        });
        loadMembersInfo();
        return view;
    }

    private void loadMembersInfo() {
        refresh();
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl(NetManager.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MembersApi api = retrofit.create(MembersApi.class);
        api.loadMembersInfo(ConfigUtils.getInstance().getUsername(), ConfigUtils.getInstance().getPassword()).compose(RxJavaCustomTransformer.<HttpResult<List<Account>>>defaultSchedulers())
                .subscribe(new Subscriber<HttpResult<List<Account>>>() {
                    @Override
                    public void onCompleted() {
                        unRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        unRefresh();
                        Snackbar.make(mRecyclerView, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(HttpResult<List<Account>> listHttpResult) {
                        setupMembersInfo(listHttpResult.getData());
                        unRefresh();
                    }
                });
    }

    private void setupMembersInfo(List<Account> data) {
        MembersConstants.accounts = data;
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerAdapter<Account> getAdapter() {
        return new MembersAdapter(getActivity(), new ArrayList<Account>());
    }
}
