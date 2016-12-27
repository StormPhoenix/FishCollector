package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class FractureSurfaceFragment extends BaseFragment {
    @BindView(R.id.spin_collect_pos)
    Spinner spinCollectPos;
    @BindView(R.id.et_shore_distance)
    EditText etShoreDistance;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fracture_surface;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
