package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.stormphoenix.fishcollector.Locals;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.network.model.DispatchTable;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.rxutils.RxJavaCustomTransformer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.imageView4)
    ImageView imageView4;
    private static final String TAG = SplashActivity.class.getName();

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
                            finish();
                        } else {
                            // 预先加载一些数据
                            FSManager.getInstance().queryAllGroupsAsync(new FSManager.FsCallback<List<Group>>() {
                                @Override
                                public void call(List<Group> groups) {
                                    Locals.userGroups = groups;
                                    if (Locals.userGroups == null || Locals.userGroups.size() == 0) {
                                        Locals.currentGroup = -1;
                                    } else {
                                        Locals.currentGroup = 0;
                                    }
                                    FSManager.getInstance().queryDispatchTablesAsync(new FSManager.FsCallback<List<DispatchTable>>() {
                                        @Override
                                        public void call(List<DispatchTable> dispatchTables) {
                                            Locals.dispatchTables = dispatchTables;
                                            if (Locals.dispatchTables == null || Locals.dispatchTables.size() == 0) {
                                                Locals.currentDispatchTable = -1;
                                            } else {
                                                Locals.currentDispatchTable = 0;
                                            }
                                            Intent view = new Intent(SplashActivity.this, MainActivity.class);
                                            startActivity(view);
                                            finish();
                                        }

                                        @Override
                                        public void onError(String errorMsg) {
                                            Log.e(TAG, "onError: " + errorMsg);
                                        }
                                    });
                                }

                                @Override
                                public void onError(String errorMsg) {
                                    Log.e(TAG, "onError: " + errorMsg);
                                }
                            });
                        }
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
