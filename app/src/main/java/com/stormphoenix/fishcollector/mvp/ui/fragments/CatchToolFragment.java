package com.stormphoenix.fishcollector.mvp.ui.fragments;

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
import android.widget.EditText;
import android.widget.Toast;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.databinding.FragmentCatchToolsBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.CatchTools;
import com.stormphoenix.fishcollector.mvp.ui.dialog.TimeSelectorDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.imagepicker.FishImageType;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class CatchToolFragment extends BaseImageListFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
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
    @BindView(R.id.rv_pic_catch_tools)
    RecyclerView rvPicCatchTools;
    OnDateSetListener onStartDateSetListener = null;
    OnDateSetListener onEndDateSetListener = null;
    private CatchTools model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_catch_tools;
    }

    @Override
    protected void initVariables() {
        model = (CatchTools) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdWaterLayer(model.getWaterLayer().getModelId());

        onStartDateSetListener = new TimeSelectorDialogGenerator.DefaultTimeSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                super.onDateSet(timePickerView, millseconds);
                etStartTimeTools.setText(fomatedTimeText);
            }
        };

        onEndDateSetListener = new TimeSelectorDialogGenerator.DefaultTimeSetListener() {
            @Override
            public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                super.onDateSet(timePickerView, millseconds);
                etEndTimeTools.setText(fomatedTimeText);
            }
        };
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

        initPicturesListView();
    }

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        maxImgCount = 10;
        adapter = new ImagePickerAdapter(this.getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        rvPicCatchTools.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        rvPicCatchTools.setHasFixedSize(true);
        rvPicCatchTools.setAdapter(adapter);

        updatePicturesData();
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.e(TAG, "onItemClick: ");
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this.getActivity(), ImageGridActivity.class);
                intent.putExtra(FishImageType.IMAGE_TYPE, FishImageType.CATCH_TOOL);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.img_select_start_date_time_catch_tools, R.id.img_select_end_date_time_catch_tools})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_select_start_date_time_catch_tools:
                Log.e(TAG, "onClick: et_date");
                new TimeSelectorDialogGenerator(getActivity(), onStartDateSetListener)
                        .setType(Type.YEAR_MONTH_DAY)
                        .show(((AppCompatActivity) getActivity()).getSupportFragmentManager());
                break;
            case R.id.img_select_end_date_time_catch_tools:
                Log.e(TAG, "onClick: et_date");
                new TimeSelectorDialogGenerator(getActivity(), onEndDateSetListener)
                        .setType(Type.YEAR_MONTH_DAY)
                        .show(((AppCompatActivity) getActivity()).getSupportFragmentManager());
                break;
        }
    }
}
