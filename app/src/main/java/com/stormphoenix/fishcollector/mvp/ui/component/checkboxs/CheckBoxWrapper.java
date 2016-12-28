package com.stormphoenix.fishcollector.mvp.ui.component.checkboxs;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;

import java.util.List;

/**
 * Created by Developer on 16-12-28.
 * Wang Cheng is a intelligent Android developer.
 */

public class CheckBoxWrapper extends LinearLayout {

    private List<String> subModelNames;
    private String chosenItem = null;

    public CheckBoxWrapper(Context context) {
        super(context);
    }

    public CheckBoxWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CheckBoxWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void addCheckBox(String modelClassName) {
        addView(createCheckBox(modelClassName));
    }

    public String getChosenValue() {
        return chosenItem;
    }

    private SmoothCheckBox createCheckBox(String modelClassName) {
        final SmoothCheckBox checkBox = new SmoothCheckBox(getContext());
        checkBox.setChecked(false);
        checkBox.setClickable(true);
        checkBox.setText(ModelConstantMap.getHolder(modelClassName).MODEL_NAME);
        checkBox.setTag(modelClassName);

        checkBox.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                for (int index = 0; index < CheckBoxWrapper.this.getChildCount(); index++) {
                    ((SmoothCheckBox) CheckBoxWrapper.this.getChildAt(index)).setChecked(false);
                }
                if (b) {
                    smoothCompoundButton.setChecked(true);
                    chosenItem = (String) smoothCompoundButton.getTag();
                } else {
                    chosenItem = null;
                }
            }
        });
        return checkBox;
    }
}
