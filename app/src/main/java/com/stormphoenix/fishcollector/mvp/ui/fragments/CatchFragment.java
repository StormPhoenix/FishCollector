package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentCatchBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.Catches;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class CatchFragment extends BaseFragment {
    private static final String TAG = "CatchFragment";
    @BindView(R.id.et_fish_name)
    EditText etFishName;
    @BindView(R.id.et_fry_total_num)
    EditText etFryTotalNum;
    @BindView(R.id.et_egg_mount)
    EditText etEggMount;
    @BindView(R.id.et_little_fish_num)
    EditText etLittleFishNum;

    private Catches model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catch;
    }

    @Override
    protected void initVariables() {
        model = (Catches) attachedBean;
        Log.i(TAG, "initVariables: " + model.getTotalQuality());
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentCatchBinding) binding).setCatchBean(model);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etFryTotalNum.addTextChangedListener(new DefaultIntTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setTotalQuality(text);
                Log.i(TAG, "afterTextChanged: " + model.getTotalQuality());
            }
        });

        etEggMount.addTextChangedListener(new DefaultIntTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setEggQuality(text);
            }
        });

        etLittleFishNum.addTextChangedListener(new DefaultIntTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setFryQuality(text);
            }
        });
    }
}
