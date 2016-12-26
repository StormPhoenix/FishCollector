package com.stormphoenix.fishcollector.mvp.presenter.interfaces;

import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.BasePresenter;

/**
 * Created by Developer on 16-12-25.
 * Wang Cheng is a intelligent Android developer.
 */

public interface LoginPresenter extends BasePresenter {
    void login(String username, String password);
}
