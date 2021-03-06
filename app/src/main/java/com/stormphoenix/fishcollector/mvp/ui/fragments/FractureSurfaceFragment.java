package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.model.beans.FractureSurface;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.constants.Constants;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;

import butterknife.BindView;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class FractureSurfaceFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "FractureSurfaceFragment";
    @BindView(R.id.spin_collect_pos)
    MaterialSpinner spinCollectPos;
    @BindView(R.id.et_shore_distance)
    EditText etShoreDistance;

    private int samplePosition = 0;
    private FractureSurface model;

    @Override
    protected void refreshFragment() {
        etShoreDistance.setText(String.valueOf(model.getDistance2Bank()));
        setSpinnerSelection();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fracture_surface;
    }

    @Override
    protected void initVariables() {
        model = (FractureSurface) attachedBean;

        assert model.getMonitoringSite() != null;
        model.setId_MonitoringSite(model.getMonitoringSite().getModelId());
    }

    @Override
    protected void initViews(View view) {
        spinCollectPos.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, Constants.SAMPLE_POSITION));
        setSpinnerSelection();
        spinCollectPos.setOnItemSelectedListener(this);

        etShoreDistance.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setDistance2Bank(text);
            }
        });
        refreshFragment();
    }

    private void setSpinnerSelection() {
        if (model.getPosition() == null) {
            samplePosition = -1;
        } else {
            for (samplePosition = 0; samplePosition < Constants.SAMPLE_POSITION.length; samplePosition++) {
                if (Constants.SAMPLE_POSITION[samplePosition].equals(model.getPosition())) {
                    break;
                }
            }
        }

        Log.i(TAG, "initViews: samplePosition : " + samplePosition);
        spinCollectPos.setSelection(samplePosition + 1);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemSelected: " + position);
        switch (parent.getId()) {
            case R.id.spin_collect_pos:
                samplePosition = position;
                if (position == -1) {
                    model.setPosition(null);
                } else if (position < Constants.SAMPLE_POSITION.length) {
                    model.setPosition(Constants.SAMPLE_POSITION[position]);
                } else {
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
