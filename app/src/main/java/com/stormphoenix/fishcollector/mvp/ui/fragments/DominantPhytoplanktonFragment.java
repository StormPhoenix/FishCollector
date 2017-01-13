package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.view.View;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantPhytoplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantPhytoplanktonFragment extends BaseFragment {
    private static final String TAG = "DominantPhytoplanktonFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_phytoplankton_species;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
//            ((FragmentDominantPhytoplanktonSpeciesBinding) binding).setDominantPhytoplanktonBean((DominantPhytoplanktonSpecies) attachedBean);
        } else {
        }
    }

    @Override
    protected void initViews(View view) {

    }
}
