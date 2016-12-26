package com.stormphoenix.fishcollector.mvp.presenter.impls.base;

import android.support.annotation.NonNull;

import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.BasePresenter;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.view.base.BaseView;

import rx.Subscription;

/**
 * Created by Developer on 16-12-23.
 * Wang Cheng is a intelligent Android developer.
 */

public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallback<E> {

    protected T mBaseView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        this.mBaseView = (T) view;
    }

    @Override
    public void onDestory() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void beforeRequest() {
        if (mBaseView != null) {
            mBaseView.showProgress();
        }
    }

    @Override
    public void success(E data) {
        if (mBaseView != null) {
            mBaseView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        if (mBaseView != null) {
            mBaseView.hideProgress();
        }
    }
}
