package com.stormphoenix.fishcollector.mvp.view;

import com.stormphoenix.fishcollector.mvp.view.base.BaseView;

/**
 * Created by Developer on 16-12-25.
 * Wang Cheng is a intelligent Android developer.
 */

public interface LoginView extends BaseView {
    void onLoginSuccess();

    void onLoginFailed();
}
