package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentCatchToolsBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.CatchTools;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;

import butterknife.BindView;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class CatchToolFragment extends BaseFragment {
    private static final String TAG = "CatchToolFragment";
    @BindView(R.id.et_net_name)
    EditText etNetName;
    @BindView(R.id.et_net_type)
    EditText etNetType;
    @BindView(R.id.et_net_area)
    EditText etNetArea;
    @BindView(R.id.et_net_angle)
    EditText etNetAngle;
    @BindView(R.id.et_net_velocity)
    EditText etNetVelocity;
    @BindView(R.id.et_start_time_tools)
    EditText etStartTimeTools;
    @BindView(R.id.et_end_time_tools)
    EditText etEndTimeTools;
    @BindView(R.id.gl_pictures_wrapper_tools)
    GridLayout glPicturesWrapperTools;

    private CatchTools model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catch_tools;
    }

    @Override
    protected void initVariables() {
        model = (CatchTools) attachedBean;
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentCatchToolsBinding) binding).setCatchToolsBean((CatchTools) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etNetAngle.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setNetMouthDip(text);
            }
        });
        etNetArea.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setNetMouthArea(text);
            }
        });

        etNetVelocity.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setNetMouthVelocity(text);
            }
        });
    }
}
