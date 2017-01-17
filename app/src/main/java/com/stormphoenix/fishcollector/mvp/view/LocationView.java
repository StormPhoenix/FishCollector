package com.stormphoenix.fishcollector.mvp.view;

import com.stormphoenix.fishcollector.mvp.view.base.BaseView;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public interface LocationView extends BaseView {
    void onLocationSuccess(double longitude, double latitude);

    void onLocationFailed(String errorMsg);
}
