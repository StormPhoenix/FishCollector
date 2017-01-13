package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.util.Log;
import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class CatchToolFragment extends BaseFragment {
    private static final String TAG = "CatchToolFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catch_tools;
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
//            ((FragmentCatchToolsBinding) binding).setCatchToolsBean((CatchTools) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
    }

    @Override
    protected void initViews(View view) {
    }
}
