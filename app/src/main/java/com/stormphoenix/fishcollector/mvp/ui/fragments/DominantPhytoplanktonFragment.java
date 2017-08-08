package com.stormphoenix.fishcollector.mvp.ui.fragments;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.databinding.FragmentDominantPhytoplanktonSpeciesBinding;
import com.stormphoenix.fishcollector.mvp.model.beans.DominantPhytoplanktonSpecies;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseImageListFragment;
import com.stormphoenix.fishcollector.shared.textutils.DefaultFloatTextWatcher;
import com.stormphoenix.fishcollector.shared.textutils.DefaultIntTextWatcher;
import com.stormphoenix.imagepicker.FishImageType;
import com.stormphoenix.imagepicker.ImagePicker;
import com.stormphoenix.imagepicker.bean.ImageItem;
import com.stormphoenix.imagepicker.ui.ImageGridActivity;
import com.stormphoenix.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.IMAGE_ITEM_ADD;
import static com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity.REQUEST_CODE_SELECT;
import static com.stormphoenix.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;

/**
 * Created by Developer on 16-12-27.
 * Wang Cheng is a intelligent Android developer.
 */

public class DominantPhytoplanktonFragment extends BaseImageListFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private static final String TAG = "DomPhytoplanktonFragment";
    @BindView(R.id.et_dom_phytoplankton_mount)
    EditText etDomPhytoplanktonMount;
    @BindView(R.id.et_dom_phytoplankton_biomass)
    EditText etDomPhytoplanktonBiomass;
    @BindView(R.id.et_dom_phy_name)
    EditText etDomPhyName;
    @BindView(R.id.rv_pic_dom_phytoplankton)
    RecyclerView rvPicDomPhytoplankton;

    private DominantPhytoplanktonSpecies model;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dominant_phytoplankton_species;
    }

    @Override
    protected void refreshFragment() {
        etDomPhytoplanktonBiomass.setText(String.valueOf(model.getBiomass()));
        etDomPhytoplanktonMount.setText(String.valueOf(model.getQuality()));
        etDomPhyName.setText(model.getName());
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this.getActivity(), ImageGridActivity.class);
                intent.putExtra(FishImageType.IMAGE_TYPE, FishImageType.DOM_PHYTOPLANKS);
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

    private void initPicturesListView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this.getActivity(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        rvPicDomPhytoplankton.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        rvPicDomPhytoplankton.setHasFixedSize(true);
        rvPicDomPhytoplankton.setAdapter(adapter);

        updatePicturesData();
    }

    @Override
    protected void initVariables() {
        model = (DominantPhytoplanktonSpecies) attachedBean;
        assert model.getForeignKey() != null;
        model.setIdPhytoplankton(model.getPhytoplankton().getModelId());
    }

    @Override
    public void onStart() {
        if (binding != null && attachedBean != null) {
            ((FragmentDominantPhytoplanktonSpeciesBinding) binding).setDomPhytopBean((DominantPhytoplanktonSpecies) attachedBean);
        } else {
        }
        super.onStart();
    }

    @Override
    protected void initViews(View view) {
        etDomPhytoplanktonMount.addTextChangedListener(new DefaultIntTextWatcher() {
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

        etDomPhytoplanktonBiomass.addTextChangedListener(new DefaultFloatTextWatcher() {
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

    @Override
    public void updateData() {
        super.updateData();
        updatePicturesData();
    }
}
