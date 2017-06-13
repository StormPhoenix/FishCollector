package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.dialog.MultiProgressDialogGenerator;
import com.stormphoenix.fishcollector.shared.PicturePathUtils;
import com.stormphoenix.fishcollector.shared.ReflectUtils;
import com.stormphoenix.imagepicker.LocalVariables;
import com.stormphoenix.imagepicker.bean.ImageItem;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseImageListFragment extends BaseFragment {

    protected int maxImgCount;
    protected ImagePickerAdapter adapter;
    protected ArrayList<ImageItem> selImageList;
    public static final String TAG = BaseImageListFragment.class.getSimpleName();

    private MultiProgressDialogGenerator multiProgressDialogGenerator = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        multiProgressDialogGenerator = new MultiProgressDialogGenerator(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void updatePicturesData() {
        selImageList = new ArrayList<>();

        Method getPhotoMethod = null;
        try {
            getPhotoMethod = attachedBean.getClass().getDeclaredMethod("getPhoto", (Class<?>[]) null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (getPhotoMethod == null) {
            return;
        }

        String path = null;
        try {
            path = (String) getPhotoMethod.invoke(attachedBean, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String[] paths = PicturePathUtils.processPicturePath(path);
        if (paths != null) {
            for (String tempPath : paths) {
                if (!new File(tempPath).exists()) {
                    continue;
                }
                ImageItem item = new ImageItem();
                item.path = tempPath;
                selImageList.add(item);
            }
        }
        adapter.setImages(selImageList);
    }

    @Override
    public void uploadModel() {
        uploadModel(attachedBean);
    }

    @Override
    protected void uploadModel(BaseModel model) {
        if (model != null && model.checkValue()) {
            String[] paths = ReflectUtils.getModelPhotoPaths(model);
            multiProgressDialogGenerator.setProgressCount(paths.length);
            multiProgressDialogGenerator.build();
            multiProgressDialogGenerator.setCancelable(false);
            submitPresenter.submitModelAndPhoto(model.getClass().getSimpleName(), model, paths, multiProgressDialogGenerator);
        } else {
            Snackbar.make(mFragmentView, "数据不完善，无法提交", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateData() {
        if (LocalVariables.newImageFilePaths != null) {
            Method setPhotoMethod = null;
            Method getPhotoMethod = null;
            try {
                setPhotoMethod = attachedBean.getClass().getDeclaredMethod("setPhoto", String.class);
                getPhotoMethod = attachedBean.getClass().getDeclaredMethod("getPhoto", (Class<?>[]) null);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (setPhotoMethod == null || getPhotoMethod == null) {
                LocalVariables.newImageFilePaths = null;
                return;
            }

            String photoPaths = null;
            try {
                photoPaths = (String) getPhotoMethod.invoke(attachedBean, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            for (String tempPath : LocalVariables.newImageFilePaths) {
                File tempFile = new File(tempPath);
                if (tempFile.exists()) {
                    photoPaths = PicturePathUtils.appendPath(photoPaths, tempPath);
                }
            }

            try {
                setPhotoMethod.invoke(attachedBean, photoPaths);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            LocalVariables.newImageFilePaths = null;
            save();
        }
    }
}
