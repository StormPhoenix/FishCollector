package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.mvp.model.beans.FishEggs;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultTextWatcher;
import com.stormphoenix.imagepicker.FishImageType;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class FishEggFragment extends BaseImageListFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private static final String TAG = "FishEggFragment";
    @BindView(R.id.et_egg_diameter)
    EditText etEggDiameter;
    @BindView(R.id.et_emDiameter)
    EditText etEmDiameter;
    @BindView(R.id.rv_pic_fish_egg)
    RecyclerView rvPicFishEgg;
    @BindView(R.id.et_puberty)
    EditText etPuberty;
    @BindView(R.id.et_pigment_prop)
    EditText etPigmentProp;
    @BindView(R.id.et_embryo_traits)
    EditText etEmbryoTraits;

    private FishEggs model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fish_egg;
    }

    @Override
    protected void initVariables() {
        model = (FishEggs) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdCatches(model.getCatches().getModelId());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etEmbryoTraits.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                model.setEmbryoProp(s.toString());
            }
        });
        etPigmentProp.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                model.setPigmentProp(s.toString());
            }
        });
        etPuberty.addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                model.setPeriod(s.toString());
            }
        });
        etEggDiameter.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setDiameter(text);
            }
        });
        etEmDiameter.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setEmDiameter(text);
            }
        });
        refreshFragment();
        initPicturesListView();
    }

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this.getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        rvPicFishEgg.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        rvPicFishEgg.setHasFixedSize(true);
        rvPicFishEgg.setAdapter(adapter);

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
                intent.putExtra(FishImageType.IMAGE_TYPE, FishImageType.FISH_EGG);
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
    protected void refreshFragment() {
        etPuberty.setText(model.getPeriod());
        etEggDiameter.setText(String.valueOf(model.getDiameter()));
        etEmDiameter.setText(String.valueOf(model.getEmDiameter()));
        etPigmentProp.setText(model.getPigmentProp());
        etEmbryoTraits.setText(model.getEmbryoProp());
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
