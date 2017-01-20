package com.stormphoenix.fishcollector.mvp.presenter.impls;

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
        if (data.getResultCode() != 0) {
            mBaseView.onSubmitError(data.getResultMessage());
        } else {
            mBaseView.onSubmitSuccess();
        }
    }
}
