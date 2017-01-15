package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.Sediment;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class SedimentFragment extends BaseFragment {
    private static final String TAG = "SedimentFragment";

    private Sediment model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sediment;
    }

    @Override
    protected void initVariables() {
        model = (Sediment) attachedBean;
    }

    @Override
    protected void initViews(View view) {

    }
}
