package com.stormphoenix.fishcollector.mvp.presenter.impls;

import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.location.LocateApi;
import com.stormphoenix.fishcollector.location.Location;
import com.stormphoenix.fishcollector.mvp.presenter.impls.base.BasePresenterImpl;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.LocationPresenter;
import com.stormphoenix.fishcollector.mvp.view.LocationView;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public class LocationPresenterImpl extends BasePresenterImpl<LocationView, Location> implements LocationPresenter {
    @Override
    public void locate() {
        LocateApi.Locate(FishApplication.getInstance(),this);
    }

    @Override
    public void success(Location data) {
        super.success(data);
        mBaseView.onLocationSuccess(data.longitude, data.latitude);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mBaseView.onLocationFailed(errorMsg);
    }
}
