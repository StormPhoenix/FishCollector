package com.stormphoenix.fishcollector.shared;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by StormPhoenix on 17-3-19.
 * StormPhoenix is a intelligent Android developer.
 */

public class ViewUtils {
    public static void clear(View v) {
        ViewCompat.setAlpha(v, 1);
        ViewCompat.setScaleY(v, 1);
        ViewCompat.setScaleX(v, 1);
        ViewCompat.setTranslationY(v, 0);
        ViewCompat.setTranslationX(v, 0);
        ViewCompat.setRotation(v, 0);
        ViewCompat.setRotationY(v, 0);
        ViewCompat.setRotationX(v, 0);
        v.setPivotY(v.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(v, v.getMeasuredWidth() / 2);
        ViewCompat.animate(v).setInterpolator(null);
    }

    public static int dp2Pixel(Activity activity, int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int) (dp * metrics.density);
    }
}
