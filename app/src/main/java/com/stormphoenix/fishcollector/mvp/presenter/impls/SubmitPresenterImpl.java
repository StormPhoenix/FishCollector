package com.stormphoenix.fishcollector.mvp.presenter.impls;

import android.os.Handler;
import android.os.Message;

import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.impls.base.BasePresenterImpl;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.SubmitPresenter;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.mvp.view.SubmitSingleModelView;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.constants.Constants;

import java.security.MessageDigest;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public class SubmitPresenterImpl extends BasePresenterImpl<SubmitSingleModelView, HttpResult<Void>> implements SubmitPresenter {

    private Handler mHandler;

    public SubmitPresenterImpl(Handler handler, SubmitSingleModelView baseView) {
        attachView(baseView);
        this.mHandler = handler;
    }

    public SubmitPresenterImpl() {

    }

    @Override
    public void submit(String modelType, BaseModel model) {
        HttpMethod.getInstance().submitModel(modelType, model, this);
    }

    @Override
    public void submitWithPhoto(String modelType, BaseModel model) {
        HttpMethod.getInstance().submitModelWithPhoto(modelType, model, this);
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        mBaseView.onSubmitError(errorMsg);
    }

    @Override
    public void success(HttpResult<Void> data) {
        super.success(data);
        switch (data.getResultCode()) {
            case Constants.SUBMIT_NO_RIGHT:
                mBaseView.hideProgress();
                mBaseView.onSubmitError("您没有权限提交该数据");
                break;
            case Constants.SUBMIT_SUCCESS:
                // 每次提交成功都要更新数据
                refreshLocalFile();
                break;
            default:
                mBaseView.hideProgress();
                break;
//            case 0:
//                mBaseView.onSubmitSuccess();
//                break;
//            case 4:
//                mBaseView.onSubmitError(FishApplication.getInstance().getResources().getString(R.string.already_inserted));
//                break;
//            case 6:
//                mBaseView.onSubmitError(FishApplication.getInstance().getString(R.string.submit_parent_data));
//                break;
//            default:
//                Log.e("TAG", "error " + data.getResultCode());
//                mBaseView.onSubmitError("data error");
//                break;
        }
    }

    private void refreshLocalFile() {
        mBaseView.hideProgress();
        mBaseView.onSubmitSuccess();
        HttpMethod.getInstance().login(ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                new RequestCallback<HttpResult<GroupRecord>>() {
                    @Override
                    public void beforeRequest() {
                        // do nothing
                    }

                    @Override
                    public void success(final HttpResult<GroupRecord> data) {
                        if (data.getResultCode() == Constants.LOGIN_FAILED) {
                            // 登录失败
                            mBaseView.hideProgress();
                            mBaseView.onSubmitError("获取服务器最新数据失败");
                            return;
                        } else if (data.getResultCode() == Constants.LOGIN_SUCCESS_IN_GROUP) {
                            // 登录成功，并且在组内，保存用户数据
                            ConfigUtils.getInstance().setUserInfo(ConfigUtils.getInstance().getUsername(),
                                    ConfigUtils.getInstance().getPassword());
                            ConfigUtils.getInstance().setUserLogin();
                            ConfigUtils.getInstance().setUserGroupId(data.getData().group.groupId);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 保存信息
                                    FSManager.getInstance().saveRecordContent(data.getData());
                                    mHandler.sendEmptyMessage(BaseFragment.HIDE_PROGRESS_AND_SUCCESS);
                                }
                            }).start();
                        } else if (data.getResultCode() == Constants.LOGIN_SUCCESS_NOT_IN_GROUP) {
                            ConfigUtils.getInstance().setUserInfo(ConfigUtils.getInstance().getUsername(),
                                    ConfigUtils.getInstance().getPassword());
                            ConfigUtils.getInstance().setUserLogin();
                            ConfigUtils.getInstance().setUserGroupId(null);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 保存信息
                                    FSManager.getInstance().saveRecordContent(data.getData());
                                    Message msg = Message.obtain();
                                    msg.what = BaseFragment.HIDE_PROGRESS_AND_SUCCESS;
                                    mHandler.sendMessage(msg);
                                }
                            }).start();
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        mBaseView.hideProgress();
                        mBaseView.onSubmitError(errorMsg);
                    }
                });
    }
}
