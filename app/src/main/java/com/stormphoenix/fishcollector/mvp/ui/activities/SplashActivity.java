package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageView4)
    ImageView imageView4;

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
        startAnimation();
    }

    private void startAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(1000);
        imageView4.startAnimation(alphaAnimation);
        finishAnimation();
    }

    private void finishAnimation() {
        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxJavaCustomTransformer.<Long>defaultSchedulers())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
//                        Intent view = new Intent(SplashActivity.this, MainActivity.class);
//                        startActivity(view);
                        if (!ConfigUtils.getInstance().isUserLogin()) {
                            Log.e("TAG", "onCompleted: sdfjlsdkjfls;");
                            Intent view = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(view);
                        } else {
                            Intent view = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(view);
                        }
                        finish();
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
