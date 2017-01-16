package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.BasePresenter;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Developer on 16-12-21.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T mPresenter;
    private View mFragmentView;
    protected Subscription mSubscription;

    protected ViewDataBinding binding = null;
    protected BaseModel attachedBean = null;

    public void setModel(BaseModel model) {
        this.attachedBean = model;
    }

    public void save() {
        DbManager manager = new DbManager(getActivity());
        if (attachedBean != null) {
            manager.save(attachedBean);
        }
    }

    public void updateData() {
    }

    protected abstract int getLayoutId();

    protected abstract void initVariables();

    protected abstract void initViews(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            if (binding != null) {
                mFragmentView = binding.getRoot();
            } else {
                mFragmentView = inflater.inflate(getLayoutId(), container, false);
            }
            ButterKnife.bind(this, mFragmentView);
            initVariables();
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
