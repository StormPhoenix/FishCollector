package com.stormphoenix.fishcollector.mvp.view;

import com.stormphoenix.fishcollector.mvp.view.base.BaseView;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public interface SubmitSingleModelView extends BaseView {
    void onSubmitSuccess();

    void onSubmitError(String msg);
}
