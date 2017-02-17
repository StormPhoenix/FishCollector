package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentWaterLayerBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.WaterLayer;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.constants.Constants;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;

import butterknife.BindView;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class WaterLayerFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "WaterLayerFragment";
    @BindView(R.id.sp_water_layer)
    MaterialSpinner spWaterLayer;
    @BindView(R.id.et_depth_water_layer)
    EditText etDepthWaterLayer;
    @BindView(R.id.et_water_temperature)
    EditText etWaterTemperature;
    @BindView(R.id.et_water_level)
    EditText etWaterLevel;
    @BindView(R.id.et_water_velocity)
    EditText etWaterVelocity;

    private WaterLayer model;
    private int waterLayerPosition = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_water_layer;
    }

    @Override
    protected void initVariables() {
        model = (WaterLayer) attachedBean;

        assert model.getForeignKey() != null;
        model.setIdMeasurePoint(model.getMeasurePoint().getModelId());
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentWaterLayerBinding) binding).setWaterLayerBean((WaterLayer) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDepthWaterLayer.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setDepth(text);
            }
        });

        etWaterTemperature.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setTemperature(text);
            }
        });

        etWaterLevel.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setWaterLevel(text);
            }
        });

        etWaterVelocity.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setVelocity(text);
            }
        });

        spWaterLayer.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, Constants.WATER_LAYER));
        if (model.getLayer() == null) {
            waterLayerPosition = -1;
        } else {
            for (waterLayerPosition = 0; waterLayerPosition < Constants.WATER_LAYER.length; waterLayerPosition++) {
                if (Constants.WATER_LAYER[waterLayerPosition].equals(model.getLayer())) {
                    break;
                }
            }
        }
        spWaterLayer.setSelection(waterLayerPosition);
        spWaterLayer.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemSelected: " + position);
        switch (parent.getId()) {
            case R.id.sp_water_layer:
                waterLayerPosition = position;
                if (position == -1) {
                    Log.e(TAG, "onItemSelected: -1");
                    model.setLayer(null);
                } else if (position < Constants.WATER_LAYER.length) {
                    Log.e(TAG, "onItemSelected: " + Constants.WATER_LAYER[waterLayerPosition]);
                    model.setLayer(Constants.WATER_LAYER[position]);
                } else {
                    model.setLayer(null);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
