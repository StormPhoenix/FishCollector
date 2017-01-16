package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.databinding.FragmentMonitorSiteBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.AddressUtils;
import com.stormphoenix.fishcollector.shared.constants.Constants;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultTextWatcher;
import com.stormphoenix.imagepicker.DirUtils;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * 维护 监测点 界面
 */
@SuppressLint("ValidFragment")
public class MonitoringSiteFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, ImagePickerAdapter.OnRecyclerViewItemClickListener {

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
    @BindView(R.id.sp_province)
    Spinner spinProvince;
    @BindView(R.id.sp_city)
    Spinner spinCity;
    @BindView(R.id.et_details_address)
    EditText etDetailsAddress;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    //城市在省级常量表中的下标值
    private int cityPosition = 0;
    private int cityIndex = 0;
    private String detailAddress = null;
    private String site = null;
    int maxImgCount;
    ImagePickerAdapter adapter;
    ArrayList<ImageItem> selImageList;

    //    private View addSurfaceView = null;
    private View addPictureView = null;

    //用于计算GridLayout一个View的大小
    private int size;
    private RelativeLayout.LayoutParams params = null;

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
        AddressUtils.processAddress(((MonitoringSite) attachedBean).getSite());
        cityPosition = AddressUtils.getCityPosition();
        cityIndex = AddressUtils.getCityIndex();
        detailAddress = AddressUtils.getAddressDetails();
        site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
    }

    private void updatePicturesData() {
        selImageList = new ArrayList<>();
        File rootDir = DirUtils.getAppRootDir(this.getActivity());
        for (File file : rootDir.listFiles()) {
            ImageItem item = new ImageItem();
            item.path = file.getAbsolutePath();
            selImageList.add(item);
        }
        adapter.setImages(selImageList);
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

        etDetailsAddress.setText(detailAddress);
        etDetailsAddress.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                detailAddress = text;
                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
                ((MonitoringSite) attachedBean).setSite(site);
            }
        });

        spinProvince.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Constants.PROVINCE));
        spinProvince.setSelection(cityPosition);
        spinProvince.setOnItemSelectedListener(this);

        spinCity.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, Constants.CITY[cityPosition]));
        spinCity.setSelection(cityIndex);
        spinCity.setOnItemSelectedListener(this);

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
            case R.id.sp_province:
                cityPosition = position;
                cityIndex = 0;
                spinCity.setAdapter(new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_spinner_item, Constants.CITY[position]));
                spinCity.setSelection(cityIndex);
                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
                ((MonitoringSite) attachedBean).setSite(site);
                break;
            case R.id.sp_city:
                cityIndex = position;
                site = String.valueOf(cityPosition) + "|" + String.valueOf(cityIndex) + "$" + detailAddress;
                ((MonitoringSite) attachedBean).setSite(site);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this.getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_CODE_SELECT);
                break;
            default:
//                打开预览
                Intent intentPreview = new Intent(this.getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
}
