package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.databinding.FragmentMeasurePointBinding;
import com.stormphoenix.fishcollector.location.Locator;
import com.stormphoenix.fishcollector.mvp.model.beans.MeasurePoint;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.mvp.view.LocationView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class MeasurePointFragment extends BaseFragment {
    private static final String TAG = "MeasurePointFragment";
    @BindView(R.id.imgBtn_location)
    ImageButton imgBtnLocation;
    @BindView(R.id.pb_location)
    ProgressBar pbLocation;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.et_latitude)
    EditText etLatitude;

    Locator locator = null;
    LocationView locationView = null;
    private MeasurePoint model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_measure_point;
    }

    @Override
    protected void initVariables() {
        model = (MeasurePoint) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdMeasuringLine(model.getMeasuringLine().getModelId());

        locationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
                model.setLongitude((float) longitude);
                model.setLatitude((float) latitude);
                etLongitude.setText(String.valueOf(longitude));
                etLatitude.setText(String.valueOf(latitude));
            }

            @Override
            public void onLocationFailed(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), getResources().getString(R.string.locate_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                imgBtnLocation.setVisibility(View.GONE);
                pbLocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                imgBtnLocation.setVisibility(View.VISIBLE);
                pbLocation.setVisibility(View.GONE);
            }
        };
        locator = new Locator(locationView);
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentMeasurePointBinding) binding).setMeasuringPointBean((MeasurePoint) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
//        etLongitude.addTextChangedListener(new DefaultFloatTextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    super.afterTextChanged(s);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
//                    text = 0;
//                }
//                model.setLongitude(text);
//            }
//        });

//        etLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    super.afterTextChanged(s);
//                } catch (NumberFormatException e) {
//                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
//                    text = 0;
//                }
//                model.setLatitude(text);
//            }
//        });
    }

    @OnClick(R.id.imgBtn_location)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_location:
                locator.startLocate(getActivity());
                break;
        }
    }
}
