package com.stormphoenix.fishcollector.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.BasePresenter;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by Developer on 16-12-25.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected T mPresenter = null;
    protected Subscription mSubscription = null;

    protected abstract int getLayoutId();

    protected abstract void initVariables();

    protected abstract void initViews();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initVariables();
        initViews();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }
}
