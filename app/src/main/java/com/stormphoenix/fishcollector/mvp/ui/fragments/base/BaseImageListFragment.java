package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.FishApplication;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.ImagePickerAdapter;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.activities.MainActivity;
import com.stormphoenix.fishcollector.mvp.ui.dialog.MultiProgressDialogGenerator;
import com.stormphoenix.fishcollector.shared.PhotosPathUtils;
import com.stormphoenix.fishcollector.shared.ReflectUtils;
import com.stormphoenix.imagepicker.LocalVariables;
import com.stormphoenix.imagepicker.bean.ImageItem;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Developer on 17-1-17.
 * Wang Cheng is a intelligent Android developer.
 */

public abstract class BaseImageListFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    private volatile boolean DOWNLOADING_PHOTOS = false;

    public boolean isDownloadingPhotos() {
        return DOWNLOADING_PHOTOS;
    }

    public void startDownload() {
        DOWNLOADING_PHOTOS = true;
    }

    public void stopDownload() {
        DOWNLOADING_PHOTOS = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 当出现在屏幕的时候，检测图片是否为空，若是则下载数据
        if (getActivity() instanceof MainActivity) {
            if (attachedBean != null) {
                String[] photoPaths = ReflectUtils.getModelPhotoPaths(attachedBean);
                if (photoPaths != null && photoPaths.length != 0) {
                } else {
                    ((MainActivity) getActivity()).downloadPhotos();
                }
            }
        }
    }

    public static final String TAG = BaseImageListFragment.class.getSimpleName();
    protected int maxImgCount = 20;
    protected ImagePickerAdapter adapter;
    protected ArrayList<ImageItem> selImageList;
    private MultiProgressDialogGenerator uploadPDGenerator = null;
    private MultiProgressDialogGenerator downDialogGen = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uploadPDGenerator = new MultiProgressDialogGenerator(getActivity());
        downDialogGen = new MultiProgressDialogGenerator(getActivity());
        View view = super.onCreateView(inflater, container, savedInstanceState);
        NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
        if (scrollView != null) {
            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (oldScrollY > scrollY) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).showFloatBtn();
                        }
                    } else if (oldScrollY < scrollY) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).hideFloatBtn();
                        }
                    }
                }
            });
        }
        return view;
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
            path = (String) getPhotoMethod.invoke(attachedBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        String[] paths = PhotosPathUtils.processPhotosPath(path);
        if (paths != null) {
            List<String> pathsList = new ArrayList<>();
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
            downDialogGen.setProgressCount(photoNames.size());
            downDialogGen.build();
            downDialogGen.setCancelable(false);
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
            submitPresenter.downloadPhotos(attachedBean, paths, downDialogGen, this);
        }
    }

    private BaseModel createNewModel(List<String> imagePathList) {
        BaseModel newModel = ReflectUtils.cloneBaseModel(attachedBean);

        String newPath = null;
        if (imagePathList != null && imagePathList.size() != 0) {
            for (String path : imagePathList) {
                newPath = PhotosPathUtils.appendPath(newPath, new File(path).getName());
            }
        }
        ReflectUtils.setModelPhotoPaths(newModel, newPath);
        return newModel;
    }

    @Override
    protected void uploadModel(BaseModel model) {
        if (model != null && model.checkValue()) {
            // 先对model中的图片数据进行处理，防止提交不存在的图片
            String[] absolutePaths = ReflectUtils.getModelPhotoPaths(model);
            List<String> exsistPathoPaths = new ArrayList<>();
            if (absolutePaths != null && absolutePaths.length != 0) {
                for (String absolutePath : absolutePaths) {
                    if (new File(absolutePath).exists()) {
                        exsistPathoPaths.add(absolutePath);
                    }
                }
            }
            BaseModel newModel = createNewModel(Arrays.asList(absolutePaths));

            if (exsistPathoPaths == null || exsistPathoPaths.size() == 0) {
                uploadPDGenerator.setProgressCount(0);
            } else {
                uploadPDGenerator.setProgressCount(exsistPathoPaths.size());
            }
//            uploadPDGenerator.build();
            uploadPDGenerator.setCancelable(false);
            // 为了赶工，这里写的很乱
            submitPresenter.submitModelAndPhoto(model.getClass().getSimpleName(), newModel, exsistPathoPaths.toArray(new String[exsistPathoPaths.size()]), uploadPDGenerator);
        } else {
            Snackbar.make(mFragmentView, "数据不完善，无法提交", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateData() {
        if (LocalVariables.selectedImageFilePaths != null) {
            Method photoMethodSetter = null;
            try {
                photoMethodSetter = attachedBean.getClass().getDeclaredMethod("setPhoto", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            if (photoMethodSetter == null) {
                LocalVariables.selectedImageFilePaths = null;
                return;
            }

            String photoPaths = null;

            for (String tempPath : LocalVariables.selectedImageFilePaths) {
                File tempFile = new File(tempPath);
                if (tempFile.exists()) {
                    photoPaths = PhotosPathUtils.appendPath(photoPaths, tempPath);
                }
            }

            try {
                photoMethodSetter.invoke(attachedBean, photoPaths);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            LocalVariables.selectedImageFilePaths = null;
            save();
        }
    }
}
