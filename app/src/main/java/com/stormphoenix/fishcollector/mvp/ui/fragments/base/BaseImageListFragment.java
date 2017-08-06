package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.dialog.MultiProgressDialogGenerator;
import com.stormphoenix.fishcollector.shared.PhotosPathUtils;
import com.stormphoenix.fishcollector.shared.ReflectUtils;
import com.stormphoenix.imagepicker.LocalVariables;
import com.stormphoenix.imagepicker.bean.ImageItem;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseImageListFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener{

    public static final String TAG = BaseImageListFragment.class.getSimpleName();
    protected int maxImgCount;
    protected ImagePickerAdapter adapter;
    protected ArrayList<ImageItem> selImageList;
    private MultiProgressDialogGenerator uploadPDGenerator = null;
    private MultiProgressDialogGenerator downloadPDGenerator = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uploadPDGenerator = new MultiProgressDialogGenerator(getActivity());
        downloadPDGenerator = new MultiProgressDialogGenerator(getActivity());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void updatePicturesData() {
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

        String[] paths = PhotosPathUtils.processPhotosPath(path);
        if (paths != null) {
            List<String> pathsList = new ArrayList<>();
//                    Arrays.asList(path);
            for (String tempPath : paths) {
                if (new File(tempPath).exists()) {
                    // 如果图片不存在说明图片没有下载下来，此处最好把这个字段去掉
                    pathsList.add(tempPath);
                } else {
                    continue;
                }
                ImageItem item = new ImageItem();
                item.path = tempPath;
                selImageList.add(item);
            }
            if (pathsList.size() == 0) {
                ReflectUtils.setModelPhotoPaths(attachedBean, (String) null);
            } else {
                String[] results = new String[pathsList.size()];
                ReflectUtils.setModelPhotoPaths(attachedBean, pathsList.toArray(results));
            }
            new DbManager(FishApplication.getInstance()).save(attachedBean);
        }

        adapter.setImages(selImageList);
    }

    @Override
    public void uploadModel() {
        uploadModel(attachedBean);
    }

    @Override
    public void downloadPhotos(List photoNames) {
        if (attachedBean != null) {
            downloadPDGenerator.setProgressCount(photoNames.size());
            downloadPDGenerator.build();
            downloadPDGenerator.setCancelable(false);
            // 这里要判断服务器下载下来的图片是否与本地重复，如果重复就不用下载了
            // 判断依据是图片的名字是否重复
            String[] modelPaths = ReflectUtils.getModelPhotoPaths(attachedBean);
            List<String> copyList = new ArrayList<>();
            copyList.addAll(photoNames);

            if (modelPaths != null && modelPaths.length != 0) {
                for (String path : modelPaths) {
                    String name = path.substring(path.lastIndexOf(File.separatorChar) + 1);
                    for (String photoName : (List<String>) photoNames) {
                        if (name.equals(photoName)) {
                            copyList.remove(name);
                            break;
                        }
                    }
                }
            }

            if (copyList.size() == 0) {
                return;
            }

            String[] paths = new String[copyList.size()];
            for (int i = 0; i < copyList.size(); i++) {
                paths[i] = (String) copyList.get(i);
            }
            submitPresenter.downloadPhotos(attachedBean, paths, downloadPDGenerator, this);
        }
    }

    @Override
    protected void uploadModel(BaseModel model) {
        if (model != null && model.checkValue()) {
            String[] paths = ReflectUtils.getModelPhotoPaths(model);
            if (paths == null || paths.length == 0) {
                uploadPDGenerator.setProgressCount(0);
            } else {
                uploadPDGenerator.setProgressCount(paths.length);
            }
            uploadPDGenerator.build();
            uploadPDGenerator.setCancelable(false);
            // 为了赶工，这里写的很乱
            submitPresenter.submitModelAndPhoto(model.getClass().getSimpleName(), model, paths, uploadPDGenerator);
        } else {
            Snackbar.make(mFragmentView, "数据不完善，无法提交", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

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
                    photoPaths = PhotosPathUtils.appendPath(photoPaths, tempPath);
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
