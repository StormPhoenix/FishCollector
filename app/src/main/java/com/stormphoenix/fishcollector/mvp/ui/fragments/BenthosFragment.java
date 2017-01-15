package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentBenthosBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.Benthos;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */


public class BenthosFragment extends BaseFragment {
    private static final String TAG = "BenthosFragment";
    @BindView(R.id.et_mount_benthos)
    EditText etMountBenthos;
    @BindView(R.id.et_benthos_biomass)
    EditText etBenthosBiomass;

    private Benthos model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_benthos;
    }

    @Override
    protected void initVariables() {
        model = (Benthos) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentBenthosBinding) binding).setBenthosBean((Benthos) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etMountBenthos.addTextChangedListener(new DefaultIntTextWatcher() {
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

        etBenthosBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
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
