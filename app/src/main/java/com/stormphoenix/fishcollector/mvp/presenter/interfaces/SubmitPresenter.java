package com.stormphoenix.fishcollector.mvp.presenter.interfaces;

import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.dialog.MultiProgressDialogGenerator;

/**
 * Created by Developer on 17-1-19.
 * Wang Cheng is a intelligent Android developer.
 */

public interface SubmitPresenter {
    public void submitModel(String modelType, BaseModel model);

    public void submitModelAndPhoto(String modelType, BaseModel model, String paths[], MultiProgressDialogGenerator barsWrapper);
}