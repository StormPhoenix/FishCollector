package com.stormphoenix.fishcollector.mvp.presenter.interfaces.base;

import android.support.annotation.NonNull;

import com.stormphoenix.fishcollector.mvp.view.base.BaseView;

/**
 * Created by Developer on 16-12-25.
 * Wang Cheng is a intelligent Android developer.
 */

public interface BasePresenter {
    // when presenter created.
    void onCreate();

    // when BaseView is attached to presenter.
    void attachView(@NonNull BaseView view);

    // when BaseView is destoryed.
    void onDestory();
}
