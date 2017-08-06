package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.databinding.FragmentDominantZooplanktonSpeciesBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantZooplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;
import com.stormphoenix.imagepicker.DirUtils;
import com.stormphoenix.imagepicker.FishImageType;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantZooplanktonFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private static final String TAG = "DomZooplanktonFragment";
    @BindView(R.id.et_dom_zooplankton_mount)
    EditText etDomZooplanktonMount;
    @BindView(R.id.et_dom_zooplankton_biomass)
    EditText etDomZooplanktonBiomass;
    @BindView(R.id.et_dom_zoop_name)
    EditText etDomZoopName;
    @BindView(R.id.rv_pic_dom_zooplankton)
    RecyclerView rvPicDomZooplankton;
    int maxImgCount;
    ImagePickerAdapter adapter;
    ArrayList<ImageItem> selImageList;
    private DominantZooplanktonSpecies model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_zooplankton_species;
    }

    @Override
    protected void initVariables() {
        model = (DominantZooplanktonSpecies) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdZooplankton(model.getZooplankton().getModelId());
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            ((FragmentDominantZooplanktonSpeciesBinding) binding).setDomZoopBean((DominantZooplanktonSpecies) attachedBean);
        } else {
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDomZooplanktonMount.addTextChangedListener(new DefaultIntTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setQuality(text);
            }
        });

        etDomZooplanktonBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    super.afterTextChanged(s);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.input_type_error), Toast.LENGTH_SHORT).show();
                    text = 0;
                }
                model.setBiomass(text);
            }
        });

        initPicturesListView();
    }


    private void updatePicturesData() {
        selImageList = new ArrayList<>();
        File rootDir = DirUtils.getAppRootDir(this.getActivity(), FishImageType.DOM_ZOOPLANKTON);
        for (File file : rootDir.listFiles()) {
            ImageItem item = new ImageItem();
            item.path = file.getAbsolutePath();
            selImageList.add(item);
        }
        adapter.setImages(selImageList);
    }

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        maxImgCount = 10;
        adapter = new ImagePickerAdapter(this.getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        rvPicDomZooplankton.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        rvPicDomZooplankton.setHasFixedSize(true);
        rvPicDomZooplankton.setAdapter(adapter);

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
}
