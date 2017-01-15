package com.stormphoenix.fishcollector.shared.photoutils;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Developer on 17-1-15.
 * Wang Cheng is a intelligent Android developer.
 */

public class PhotoUtils {
    //启动actviity的请求码
    private static final int REQUEST_IMAGE = 2;

    public static void selectPhotos(Activity context, ArrayList<String> mSelectPath) {
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }
        context.startActivityForResult(intent, REQUEST_IMAGE);
    }
}
