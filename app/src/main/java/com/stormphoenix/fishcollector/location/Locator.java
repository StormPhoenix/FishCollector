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

    public Locator(LocationView locationView) {
        locationPresenter = new LocationPresenterImpl();
        locationPresenter.onCreate();
        locationPresenter.attachView(locationView);
    }

    public void startLocate(Activity context) {
        if (PermissionsUtils.checkLocationPermissions(context)) {
            locationPresenter.locate();
        } else {
            PermissionsUtils.requestLocationPermissions(context);
        }
    }
}
