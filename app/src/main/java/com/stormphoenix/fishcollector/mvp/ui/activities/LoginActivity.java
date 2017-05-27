package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.stormphoenix.fishcollector.Locals;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.DispatchTable;
import com.stormphoenix.fishcollector.shared.ConfigUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.pb_login)
    ProgressBar pbLogin;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    protected ProgressDialogGenerator submitDialogGenerator;
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

        HttpMethod.getInstance().login(username, password, new RequestCallback<HttpResult<List<DispatchTable>>>() {
            @Override
            public void beforeRequest() {
                showProgress();
            }

            @Override
            public void success(final HttpResult<List<DispatchTable>> data) {
                if (data.getResultCode() == 0) {
                    ConfigUtils.getInstance().saveUserInfo(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
                    ConfigUtils.getInstance().setUserLogin();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 保存信息
                            FSManager.getInstance().saveAll(data.getData());
                            hideProgress();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).start();
                } else {
                    hideProgress();
                    Snackbar.make(btnLogin, getResources().getString(R.string.login_failed), Snackbar.LENGTH_SHORT).show();
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

    public void register(View view) {

    }
}
