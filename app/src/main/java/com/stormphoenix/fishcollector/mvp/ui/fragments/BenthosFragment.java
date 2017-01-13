package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.util.Log;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.Benthos;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */


public class BenthosFragment extends BaseFragment {
    private static final String TAG = "BenthosFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_benthos;
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
//            ((FragmentBenthosBinding) binding).setBenthosBean((Benthos) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
    }

    @Override
    protected void initViews(View view) {

    }
}
