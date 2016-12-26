package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAnimation();
    }

    private void finishAnimation() {
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxJavaCustomTransformer.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Intent view = new Intent(SplashActivity.this, MainActivity.class);
                        finish();
                        startActivity(view);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                    }
                });
    }
}
