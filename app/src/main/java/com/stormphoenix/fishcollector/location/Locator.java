package com.stormphoenix.fishcollector.location;

import android.app.Activity;

import com.stormphoenix.fishcollector.mvp.presenter.impls.LocationPresenterImpl;
import com.stormphoenix.fishcollector.mvp.view.LocationView;
import com.stormphoenix.fishcollector.permissions.PermissionsUtils;

/**
 * Created by Developer on 17-1-20.
 * Wang Cheng is a intelligent Android developer.
 */

public class Locator {
    LocationPresenterImpl locationPresenter;

    public Locator() {
        locationPresenter = new LocationPresenterImpl();
        locationPresenter.onCreate();
    }

    public void startLocate(LocationView view, Activity context) {
        if (PermissionsUtils.checkLocationPermissions(context)) {
            locationPresenter.attachView(view);
            locationPresenter.locate();
        } else {
            PermissionsUtils.requestLocationPermissions(context);
        }
    }
}
