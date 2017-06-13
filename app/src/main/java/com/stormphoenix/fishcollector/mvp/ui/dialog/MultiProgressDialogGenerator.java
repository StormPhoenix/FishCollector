package com.stormphoenix.fishcollector.mvp.ui.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.shared.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by StormPhoenix on 17-6-8.
 * StormPhoenix is a intelligent Android developer.
 */

public class MultiProgressDialogGenerator {

    private Activity context;
    private View dialogView;
    private LinearLayout linearLayout;
    private TextView dialogTitle;
    private List<NumberProgressBar> pbs;

    private boolean cancelabe = false;
    private int progressCount = 0;

    public ProgressBarsWrapper getWrapper() {
        return wrapper;
    }

    private ProgressBarsWrapper wrapper;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    public static class ProgressBarsWrapper {
        private List<NumberProgressBar> pbs;
        private boolean[] dones;
        private MultiProgressDialogGenerator generator;

        public ProgressBarsWrapper(MultiProgressDialogGenerator generator, List<NumberProgressBar> bars) {
            pbs = bars;
            dones = new boolean[bars.size()];
            this.generator = generator;
            Arrays.fill(dones, false);
        }

        public void setProgress(int index, int progress, boolean done) {
            pbs.get(index).setProgress(progress);
            dones[index] = done;
            if (checkCanDismiss()) {
                generator.dismiss();
            }
        }

        private boolean checkCanDismiss() {
            for (boolean isDone : dones) {
                if (!isDone) {
                    return false;
                }
            }
            return true;
        }
    }

    public MultiProgressDialogGenerator(Activity context) {
        this.context = context;
    }

    public void setProgressCount(int count) {
        progressCount = count;
    }

    public List<NumberProgressBar> getProgressBars() {
        return pbs;
    }

    public void build() {
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_upload_images, null, false);
        linearLayout = (LinearLayout) dialogView.findViewById(R.id.progress_wrapper);
        dialogTitle = (TextView) dialogView.findViewById(R.id.progress_dialog_title);
        pbs = new ArrayList<>();

        dialogTitle.setText("正在上传" + String.valueOf(progressCount) + "份文件");
        for (int i = 0; i < progressCount; i++) {
            NumberProgressBar progressBar = new NumberProgressBar(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.topMargin = ViewUtils.dp2Pixel(context, 15);
            progressBar.setLayoutParams(layoutParams);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) progressBar.getLayoutParams();
            pbs.add(progressBar);
            linearLayout.addView(progressBar);
        }

        builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        alertDialog = builder.create();

        wrapper = new ProgressBarsWrapper(this, getProgressBars());
    }

    public void setCancelable(boolean cancelable) {
        this.cancelabe = cancelable;
    }

    public void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
            linearLayout.removeAllViews();
            System.gc();
        }
    }

    public void show() {
        if (alertDialog != null) {
            alertDialog.show();
        }
    }
}
