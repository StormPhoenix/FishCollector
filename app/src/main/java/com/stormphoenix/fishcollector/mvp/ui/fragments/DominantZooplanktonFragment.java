package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentDominantZooplanktonSpeciesBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantZooplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantZooplanktonFragment extends BaseFragment {
    private static final String TAG = "DominantZooplanktonFragment";
    @BindView(R.id.et_dom_zooplankton_mount)
    EditText etDomZooplanktonMount;
    @BindView(R.id.et_dom_zooplankton_biomass)
    EditText etDomZooplanktonBiomass;
    @BindView(R.id.et_dom_zoop_name)
    EditText etDomZoopName;

    private DominantZooplanktonSpecies model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_zooplankton_species;
    }

    @Override
    protected void initVariables() {
model  = (DominantZooplanktonSpecies) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            ((FragmentDominantZooplanktonSpeciesBinding) binding).setDomZoopBean((DominantZooplanktonSpecies) attachedBean);
        } else {
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDomZooplanktonMount.addTextChangedListener(new DefaultIntTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setQuality(text);
            }
        });

        etDomZooplanktonBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setBiomass(text);
            }
        });
    }

}
