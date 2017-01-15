package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentDominantPhytoplanktonSpeciesBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantPhytoplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantPhytoplanktonFragment extends BaseFragment {
    private static final String TAG = "DominantPhytoplanktonFragment";
    @BindView(R.id.et_dom_phytoplankton_mount)
    EditText etDomPhytoplanktonMount;
    @BindView(R.id.et_dom_phytoplankton_biomass)
    EditText etDomPhytoplanktonBiomass;
    @BindView(R.id.et_dom_phy_name)
    EditText etDomPhyName;

    private DominantPhytoplanktonSpecies model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_phytoplankton_species;
    }

    @Override
    protected void initVariables() {
        model = (DominantPhytoplanktonSpecies) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            ((FragmentDominantPhytoplanktonSpeciesBinding) binding).setDomPhytopBean((DominantPhytoplanktonSpecies) attachedBean);
        } else {
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDomPhytoplanktonMount.addTextChangedListener(new DefaultIntTextWatcher() {
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

        etDomPhytoplanktonBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
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
