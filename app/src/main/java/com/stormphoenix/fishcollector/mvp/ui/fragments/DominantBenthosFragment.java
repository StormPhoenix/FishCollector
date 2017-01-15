package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentDominantBenthosSpeciesBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantBenthosSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantBenthosFragment extends BaseFragment {
    private static final String TAG = "DominantBenthosFragment";
    @BindView(R.id.et_dom_benthos_mount)
    EditText etDomBenthosMount;
    @BindView(R.id.et_dom_benthos_biomass)
    EditText etDomBenthosBiomass;
    @BindView(R.id.et_dom_benthos_name)
    EditText etDomBenthosName;

    private DominantBenthosSpecies model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_benthos_species;
    }

    @Override
    protected void initVariables() {
        model = (DominantBenthosSpecies) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentDominantBenthosSpeciesBinding) binding).setDominantBenthosBean((DominantBenthosSpecies) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDomBenthosMount.addTextChangedListener(new DefaultIntTextWatcher() {
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

        etDomBenthosBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
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
