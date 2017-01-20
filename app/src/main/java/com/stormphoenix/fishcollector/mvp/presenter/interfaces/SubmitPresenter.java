package com.stormphoenix.fishcollector.mvp.presenter.interfaces;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public interface SubmitPresenter {
    public void submit(String modelType, BaseModel model);

    public void submitWithPhoto(String modelType, BaseModel model);
}
