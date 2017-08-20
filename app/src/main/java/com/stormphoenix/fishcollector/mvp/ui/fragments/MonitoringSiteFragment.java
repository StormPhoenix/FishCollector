package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.location.Locator;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.dialog.TimeSelectorDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.mvp.view.LocationView;
import com.stormphoenix.fishcollector.shared.constants.Constants;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultTextWatcher;
import com.stormphoenix.imagepicker.FishImageType;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * 维护 监测点 界面
 */
@SuppressLint("ValidFragment")
public class MonitoringSiteFragment extends BaseImageListFragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MonitoringSiteFragment";
    @BindView(R.id.et_temperature)
    EditText etTemperature;
    @BindView(R.id.et_start_longitude)
    EditText etStartLongitude;
    @BindView(R.id.et_start_latitude)
    EditText etStartLatitude;
    @BindView(R.id.et_end_longitude)
    EditText etEndLongitude;
    @BindView(R.id.et_end_latitude)
    EditText etEndLatitude;
    @BindView(R.id.et_details_address)
    EditText etDetailsAddress;
    @BindView(R.id.rv_pic_monitor_site)
    RecyclerView recyclerView;
    @BindView(R.id.img_start_location)
    ImageView imgStartLocation;
    @BindView(R.id.img_end_location)
    ImageView imgEndLocation;
    @BindView(R.id.pb_start_location)
    ProgressBar pbStartLocation;
    @BindView(R.id.pb_end_location)
    ProgressBar pbEndLocation;
    @BindView(R.id.img_select_date_monitor)
    ImageView imgSelectDateMonitor;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.img_select_start_date_time_monitor)
    ImageView imgSelectStartDateTimeMonitor;
    @BindView(R.id.et_start_time)
    EditText etStartTime;
    @BindView(R.id.img_select_end_date_time_monitor)
    ImageView imgSelectEndDateTimeMonitor;
    @BindView(R.id.et_end_time)
    EditText etEndTime;

    //城市在省级常量表中的下标值
//    private int cityPosition = 0;
//    private int cityIndex = 0;
//    private String detailAddress = null;
//    private String site = null;

    MonitoringSite model = null;
    Locator startPosLocator = null;
    Locator endPosLocator = null;
    LocationView startLocationView;
    LocationView endLocationView;

    OnDateSetListener onDateSetListener = null;
    OnDateSetListener onStartDateSetListener = null;
    OnDateSetListener onEndDateSetListener = null;

    @BindView(R.id.spin_weather)
    MaterialSpinner spinWeather;
    @BindView(R.id.et_detection_unit)
    EditText etDetectionUnit;
    @BindView(R.id.et_monitors)
    EditText etMonitors;
    @BindView(R.id.et_water_area)
    EditText etWaterArea;
    private ArrayAdapter<String> weatherAdapter;

    @Override
    protected void refreshFragment() {
        etTemperature.setText(String.valueOf(model.getTemperature()));
        etDetectionUnit.setText(model.getInstitution());
        etMonitors.setText(model.getInvestigator());
        etDate.setText(model.getInvestigationDate());
        etDetailsAddress.setText(model.getSite());
        etWaterArea.setText(model.getRiver());
        etStartTime.setText(model.getStartTime());
        etEndTime.setText(model.getEndTime());
        setSpinnerSelection();
        etStartLongitude.setText(String.valueOf(model.getStartLongitude()));
        etStartLatitude.setText(String.valueOf(model.getStartLatitude()));
        etEndLatitude.setText(String.valueOf(model.getEndLatitude()));
        etEndLongitude.setText(String.valueOf(model.getEndLongitude()));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitor_site;
    }

    @Override
    protected void initVariables() {
        weatherAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, Constants.SAMPLE_WEATHER);
        model = (MonitoringSite) attachedBean;
//        AddressUtils.processAddress(model.getSite());
//        cityPosition = AddressUtils.getCityPosition();
//        cityIndex = AddressUtils.getCityIndex();
//        detailAddress = AddressUtils.getAddressDetails();
//        site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;

        startLocationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
                etStartLongitude.setText(String.valueOf(longitude));
                etStartLatitude.setText(String.valueOf(latitude));
                ((MonitoringSite) attachedBean).setStartLongitude((float) longitude);
                ((MonitoringSite) attachedBean).setStartLatitude((float) latitude);
            }

            @Override
            public void onLocationFailed(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), getResources().getString(R.string.locate_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                imgStartLocation.setVisibility(View.GONE);
                pbStartLocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                imgStartLocation.setVisibility(View.VISIBLE);
                pbStartLocation.setVisibility(View.GONE);
            }
        };

        endLocationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
                etEndLongitude.setText(String.valueOf(longitude));
                etEndLatitude.setText(String.valueOf(latitude));
                ((MonitoringSite) attachedBean).setEndLongitude((float) longitude);
                ((MonitoringSite) attachedBean).setEndLatitude((float) latitude);
            }

            @Override
            public void onLocationFailed(String errorMsg) {
//                Toast.makeText(getActivity(), getResources().getString(R.string.locate_error), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void showProgress() {
                imgEndLocation.setVisibility(View.GONE);
                pbEndLocation.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideProgress() {
                imgEndLocation.setVisibility(View.VISIBLE);
                pbEndLocation.setVisibility(View.GONE);
            }
        };

        startPosLocator = new Locator(startLocationView);
        endPosLocator = new Locator(endLocationView);

        onDateSetListener = new TimeSelectorDialogGenerator.DefaultTimeSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                etDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millseconds)));
            }
        };

        onStartDateSetListener = new TimeSelectorDialogGenerator.DefaultTimeSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                super.onDateSet(timePickerView, millseconds);
                etStartTime.setText(fomatedTimeText);
            }
        };

        onEndDateSetListener = new TimeSelectorDialogGenerator.DefaultTimeSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                super.onDateSet(timePickerView, millseconds);
                etEndTime.setText(fomatedTimeText);
            }
        };
    }

    @Override
    protected void initViews(View view) {
        spinWeather.setAdapter(weatherAdapter);
        spinWeather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: " + position);
                if (position == -1) {
                    model.setWeather(null);
                    return;
                } else {
                    model.setWeather(Constants.SAMPLE_WEATHER[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setSpinnerSelection();
        etTemperature.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                ((MonitoringSite) attachedBean).setTemperature(text);
            }
        });

        etDetectionUnit.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setInstitution(text);
            }
        });

        etMonitors.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setInvestigator(text);
            }
        });

        etDate.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setInvestigationDate(text);
            }
        });

        etDetailsAddress.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setSite(text);
            }
        });

        etWaterArea.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setRiver(text);
            }
        });

        etStartTime.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setStartTime(text);
            }
        });

        etEndTime.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                model.setEndTime(text);
            }
        });

        etStartLongitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    text = 0;
                }
                model.setStartLongitude(text);
            }
        });

        etStartLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    text = 0;
                }
                model.setStartLatitude(text);
            }
        });

        etEndLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
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
                    text = 0;
                }
                model.setEndLongitude(text);
            }
        });

        refreshFragment();
        initPicturesListView();
    }

    private void setSpinnerSelection() {
        if (model.getWeather() != null) {
            for (int index = 0; index < Constants.SAMPLE_WEATHER.length; index++) {
                if (model.getWeather().equals(Constants.SAMPLE_WEATHER[index])) {
                    spinWeather.setSelection(index + 1);
                    break;
                }
            }
        } else {
            spinWeather.setSelection(-1);
            model.setWeather(null);
        }
    }

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this.getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        updatePicturesData();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
//            case R.id.sp_province:
//                cityPosition = position;
//                cityIndex = 0;
//                spinCity.setAdapter(new ArrayAdapter<>(getActivity(),
//                        android.R.layout.simple_spinner_item, Constants.CITY[position]));
//                spinCity.setSelection(cityIndex);
//                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
//                ((MonitoringSite) attachedBean).setSite(site);
//                break;
//            case R.id.sp_city:
//                cityIndex = position;
//                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
//                ((MonitoringSite) attachedBean).setSite(site);
//                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.e(TAG, "onItemClick: ");
        switch (position) {
            case IMAGE_ITEM_ADD:
                //添加图片
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this.getActivity(), ImageGridActivity.class);
                intent.putExtra(FishImageType.IMAGE_TYPE, FishImageType.MONITORING_SITE);
                // 启动图片选择 Activity
                getActivity().startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
//                打开预览
                Intent intentPreview = new Intent(this.getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                getActivity().startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void updateData() {
        super.updateData();
        updatePicturesData();
    }

    @Override
    public void uploadModel() {
        MonitoringSite cloneObj = model.clone();
//        cloneObj.setSite(AddressUtils.mergeAddress(cloneObj.getSite()));
        super.uploadModel(cloneObj);
    }

    @OnClick({R.id.img_start_location, R.id.img_end_location, R.id.img_select_date_monitor, R.id.img_select_start_date_time_monitor, R.id.img_select_end_date_time_monitor})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_start_location:
                startPosLocator.startLocate(getActivity());
                break;
            case R.id.img_end_location:
                endPosLocator.startLocate(getActivity());
                break;
            case R.id.img_select_date_monitor:
                Log.e(TAG, "onClick: et_date");
                new TimeSelectorDialogGenerator(getActivity(), onDateSetListener)
                        .setType(Type.YEAR_MONTH_DAY)
                        .show(((AppCompatActivity) getActivity()).getSupportFragmentManager());
                break;
            case R.id.img_select_start_date_time_monitor:
                new TimeSelectorDialogGenerator(getActivity(), onStartDateSetListener)
                        .show(((AppCompatActivity) getActivity()).getSupportFragmentManager());
                break;
            case R.id.img_select_end_date_time_monitor:
                new TimeSelectorDialogGenerator(getActivity(), onEndDateSetListener).
                        show(((AppCompatActivity) getActivity()).getSupportFragmentManager());
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
