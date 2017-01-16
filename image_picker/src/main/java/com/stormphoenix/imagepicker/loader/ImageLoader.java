package com.stormphoenix.imagepicker.loader;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Created by Developer on 17-1-16.
 * Wang Cheng is a intelligent Android developer.
 */
public interface ImageLoader {
    void displayImage(Activity activity, String path, ImageView imageView, int width, int height);

    void clearMemoryCache();
}
