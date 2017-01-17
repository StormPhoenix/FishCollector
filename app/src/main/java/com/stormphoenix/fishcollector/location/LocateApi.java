package com.stormphoenix.fishcollector.location;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public class LocateApi {

    public static final String TAG = "LocateApi";

    public static void Locate(Context context, final RequestCallback<Location> callback) {
        //声明AMapLocationClient类对象
        final AMapLocationClient mLocationClient = new AMapLocationClient(context);
        //声明定位回调监听器
        AMapLocationListener mLocationListener = null;
        //异步获取定位结果
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    Log.d(TAG, "onLocationChanged: " + amapLocation.getErrorInfo());
                    Log.d(TAG, "onLocationChanged: " + String.valueOf(amapLocation.getErrorCode()));
                    if (amapLocation.getErrorCode() == 0) {
                        //解析定位结果
                        Location location = new Location();
                        location.latitude = amapLocation.getLatitude();
                        location.longitude = amapLocation.getLongitude();
                        callback.success(location);
                    } else {
                        switch (amapLocation.getErrorCode()) {
                            case 2:
                                callback.onError("定位失败，由于仅扫描到单个wifi，且没有基站信息。");
                                break;
                            default:
                                callback.onError("网络错误" + String.valueOf(amapLocation.getErrorCode()));
                                break;
                        }
                    }
                }
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            }
        };
        //初始化定位
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        callback.beforeRequest();
        mLocationClient.startLocation();
    }
}
