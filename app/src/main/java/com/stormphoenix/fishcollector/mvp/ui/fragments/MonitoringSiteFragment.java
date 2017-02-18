package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
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
import com.stormphoenix.fishcollector.databinding.FragmentMonitorSiteBinding;
import com.stormphoenix.fishcollector.location.Locator;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.dialog.TimeSelectorDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.mvp.view.LocationView;
import com.stormphoenix.fishcollector.shared.AddressUtils;
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
import butterknife.OnClick;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * 维护 监测点 界面
 */
@SuppressLint("ValidFragment")
public class MonitoringSiteFragment extends BaseImageListFragment implements AdapterView.OnItemSelectedListener, ImagePickerAdapter.OnRecyclerViewItemClickListener {

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
    Locator locator = null;
    LocationView startLocationView;
    LocationView endLocationView;

    OnDateSetListener onDateSetListener = null;
    OnDateSetListener onStartDateSetListener = null;
    OnDateSetListener onEndDateSetListener = null;

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        if (binding != null && attachedBean != null) {
            Log.e(TAG, "onStart: binding != null && attachedBean != null");
            ((FragmentMonitorSiteBinding) binding).setMonitoringSiteBean((MonitoringSite) attachedBean);
        } else {
            Log.e(TAG, "onStart: binding == null || attachedBean == null");
        }
        super.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_monitor_site;
    }

    @Override
    protected void initVariables() {
        model = (MonitoringSite) attachedBean;

//        AddressUtils.processAddress(model.getSite());
//        cityPosition = AddressUtils.getCityPosition();
//        cityIndex = AddressUtils.getCityIndex();
//        detailAddress = AddressUtils.getAddressDetails();
//        site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;

        locator = new Locator();

        startLocationView = new LocationView() {
            @Override
            public void onLocationSuccess(double longitude, double latitude) {
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

        etStartLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                ((MonitoringSite) attachedBean).setStartLatitude(text);
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
                ((MonitoringSite) attachedBean).setStartLongitude(text);
            }
        });
        etEndLatitude.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                ((MonitoringSite) attachedBean).setEndLatitude(text);
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
                ((MonitoringSite) attachedBean).setEndLongitude(text);
            }
        });

        etDetailsAddress.setText(model.getSite());
        etDetailsAddress.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
//                detailAddress = text;
//                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
                ((MonitoringSite) attachedBean).setSite(text);
            }
        });

//        spinProvince.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Constants.PROVINCE));
//        spinProvince.setSelection(cityPosition);
//        spinProvince.setOnItemSelectedListener(this);
//
//        spinCity.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Constants.CITY[cityPosition]));
//        spinCity.setSelection(cityIndex);
//        spinCity.setOnItemSelectedListener(this);

        initPicturesListView();
    }

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        maxImgCount = 10;
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
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this.getActivity(), ImageGridActivity.class);
                intent.putExtra(FishImageType.IMAGE_TYPE, FishImageType.MONITORING_SITE);
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
                locator.startLocate(startLocationView, getActivity());
                break;
            case R.id.img_end_location:
                locator.startLocate(endLocationView, getActivity());
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
}
