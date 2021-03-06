package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.constants.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getName();
    protected ProgressDialogGenerator submitDialogGenerator;
    @BindView(R.id.pb_login)
    ProgressBar pbLogin;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_bar)
    Toolbar loginBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        setSupportActionBar(loginBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Snackbar.make(btnLogin, getResources().getString(R.string.username_password_is_not_empty), Snackbar.LENGTH_SHORT).show();
            return;
        }

        HttpMethod.getInstance().login(username, password, new RequestCallback<HttpResult<GroupRecord>>() {
            @Override
            public void beforeRequest() {
                showProgress();
            }

            @Override
            public void success(final HttpResult<GroupRecord> data) {
                if (data.getResultCode() == Constants.LOGIN_FAILED) {
                    // 登录失败
                    hideProgress();
                    Snackbar.make(btnLogin, data.getResultMessage(), Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (data.getResultCode() == Constants.LOGIN_SUCCESS_IN_GROUP) {
                    // 登录成功，并且在组内，保存用户数据
                    ConfigUtils.getInstance().setUserInfo(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                    ConfigUtils.getInstance().setUserLogin();
                    ConfigUtils.getInstance().setUserGroupId(data.getData().group.groupId);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 保存信息
                            FSManager.getInstance().saveRecordContent(data.getData());
                            // 保存完毕后开始下载节点树木数据
                            HttpMethod.getInstance().downloadAllModels(ConfigUtils.getInstance().getUsername(),
                                    ConfigUtils.getInstance().getPassword(), new RequestCallback<HttpResult<List<MonitoringSite>>>() {
                                        @Override
                                        public void beforeRequest() {
                                        }

                                        @Override
                                        public void success(HttpResult<List<MonitoringSite>> data) {
                                            switch (data.getResultCode()) {
                                                case Constants.USER_NOT_EXISTS:
                                                    Log.e(TAG, "USER_NOT_EXISTS");
                                                    finish();
                                                    break;
                                                case Constants.USER_NOT_IN_GROUP:
                                                    Log.e(TAG, "USER_NOT_IN_GROUP");
                                                    finish();
                                                    break;
                                                case Constants.NO_MONITOR_DATA:
                                                    hideProgress();
                                                    enterMainPage();
                                                    break;
                                                case Constants.PULL_ALL_DATA_SUCCESS:
                                                    hideProgress();
                                                    new DbManager(LoginActivity.this).saveModels(data.getData());
                                                    enterMainPage();
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onError(String errorMsg) {
                                            enterMainPage();
                                        }
                                    });
                        }
                    }).start();
                } else if (data.getResultCode() == Constants.LOGIN_SUCCESS_NOT_IN_GROUP) {
                    ConfigUtils.getInstance().setUserInfo(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                    ConfigUtils.getInstance().setUserLogin();
                    ConfigUtils.getInstance().setUserGroupId(null);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 保存信息
                            FSManager.getInstance().saveRecordContent(null);
                            hideProgress();
                            enterMainPage();
                        }
                    }).start();
                }
            }

            @Override
            public void onError(String errorMsg) {
                hideProgress();
                Snackbar.make(btnLogin, errorMsg, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        submitDialogGenerator = new ProgressDialogGenerator(this);
        super.onStart();
    }

    private void hideProgress() {
        submitDialogGenerator.cancel();
    }

    private void showProgress() {
        submitDialogGenerator.title(getResources().getString(R.string.logining));
        submitDialogGenerator.content(getResources().getString(R.string.please_waiting));
        submitDialogGenerator.circularProgress();
        submitDialogGenerator.cancelable(false);
        submitDialogGenerator.show();
    }

    private void enterMainPage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void register(View view) {

    }
}
