package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentFishBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.Fishes;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class FishFragment extends BaseFragment {

    private static final String TAG = "FishFragment";
    @BindView(R.id.et_body_length_fish)
    EditText etBodyLengthFish;
    @BindView(R.id.et_whole_length)
    EditText etWholeLength;
    @BindView(R.id.et_body_weight_fish)
    EditText etBodyWeightFish;
    @BindView(R.id.et_age_fish)
    EditText etAgeFish;

    private Fishes model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fish;
    }

    @Override
    protected void initVariables() {
        model = (Fishes) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentFishBinding) binding).setFishBean((Fishes) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etBodyLengthFish.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setBodyLength(text);
            }
        });

        etWholeLength.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setLength(text);
            }
        });

        etBodyWeightFish.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setBodyWeight(text);
            }
        });

        etAgeFish.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setAge(text);
            }
        });
    }
}
