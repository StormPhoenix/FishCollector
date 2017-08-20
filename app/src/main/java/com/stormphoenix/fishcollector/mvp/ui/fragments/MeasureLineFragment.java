package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.location.Locator;
import com.stormphoenix.fishcollector.mvp.model.beans.MeasuringLine;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.mvp.view.LocationView;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class MeasureLineFragment extends BaseFragment {
    private static final String TAG = "MeasureLineFragment";
    @BindView(R.id.et_start_longitude_line)
    EditText etStartLongitude;
    @BindView(R.id.et_start_latitude_line)
    EditText etStartLatitude;
    @BindView(R.id.et_end_longitude_line)
    EditText etEndLongitude;
    @BindView(R.id.et_end_latitude_line)
    EditText etEndLatitude;
    @BindView(R.id.imgBtn_start_location_line)
    ImageButton imgBtnStartLocationLine;
    @BindView(R.id.pb_start_location_line)
    ProgressBar pbStartLocationLine;
    @BindView(R.id.imgBtn_end_location_line)
    ImageButton imgBtnEndLocationLine;
    @BindView(R.id.pb_end_location_line)
    ProgressBar pbEndLocationLine;
    Locator startPosLocator = null;
    Locator endPosLocator = null;
    private MeasuringLine model;
    private LocationView startLocationView;
    private LocationView endLocationView;

    @Override
    protected void refreshFragment() {
        etEndLatitude.setText(String.valueOf(model.getEndLatitude()));
        etEndLongitude.setText(String.valueOf(model.getEndLongitude()));
        etStartLatitude.setText(String.valueOf(model.getStartLatitude()));
        etStartLongitude.setText(String.valueOf(model.getStartLongitude()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_measure_line;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initVariables() {
        model = (MeasuringLine) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdFractureSurface(model.getFractureSurface().getModelId());

        startLocationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
                model.setStartLongitude((float) longitude);
                model.setStartLatitude((float) latitude);
                etStartLongitude.setText(String.valueOf(longitude));
                etStartLatitude.setText(String.valueOf(latitude));
            }

            @Override
            public void onLocationFailed(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), getResources().getString(R.string.locate_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                imgBtnStartLocationLine.setVisibility(View.GONE);
                pbStartLocationLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                imgBtnStartLocationLine.setVisibility(View.VISIBLE);
                pbStartLocationLine.setVisibility(View.GONE);
            }
        };

        endLocationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
                model.setEndLongitude((float) longitude);
                model.setEndLatitude((float) latitude);
                etEndLongitude.setText(String.valueOf(longitude));
                etEndLatitude.setText(String.valueOf(latitude));
            }

            @Override
            public void onLocationFailed(String errorMsg) {
//                Toast.makeText(getActivity(), getResources().getString(R.string.locate_error), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                imgBtnEndLocationLine.setVisibility(View.GONE);
                pbEndLocationLine.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                imgBtnEndLocationLine.setVisibility(View.VISIBLE);
                pbEndLocationLine.setVisibility(View.GONE);
            }
        };

        startPosLocator = new Locator(startLocationView);
        endPosLocator = new Locator(endLocationView);
    }

    @Override
    protected void initViews(View view) {
        etEndLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setEndLatitude(text);
            }
        });
        etEndLongitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setEndLongitude(text);
            }
        });
        etStartLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setStartLatitude(text);
            }
        });

        etStartLongitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setStartLongitude(text);
            }
        });

        refreshFragment();
    }

    @OnClick({R.id.imgBtn_start_location_line, R.id.imgBtn_end_location_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_start_location_line:
                startPosLocator.startLocate(getActivity());
                break;
            case R.id.imgBtn_end_location_line:
                endPosLocator.startLocate(getActivity());
                break;
        }
    }
}
