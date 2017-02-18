package com.stormphoenix.fishcollector.mvp.presenter.impls;

import android.util.Log;

import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.impls.base.BasePresenterImpl;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.SubmitPresenter;
import com.stormphoenix.fishcollector.mvp.view.SubmitSingleModelView;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public class SubmitPresenterImpl extends BasePresenterImpl<SubmitSingleModelView, HttpResult<Void>> implements SubmitPresenter {

    public SubmitPresenterImpl(SubmitSingleModelView baseView) {
        attachView(baseView);
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
            case 0:
                mBaseView.onSubmitSuccess();
                break;
            case 4:
                mBaseView.onSubmitError(FishApplication.getInstance().getResources().getString(R.string.already_inserted));
                break;
            case 6:
                mBaseView.onSubmitError(FishApplication.getInstance().getString(R.string.submit_parent_data));
                break;
            default:
                Log.e("TAG", "error " + data.getResultCode());
                mBaseView.onSubmitError("data error");
                break;
        }
    }
}
