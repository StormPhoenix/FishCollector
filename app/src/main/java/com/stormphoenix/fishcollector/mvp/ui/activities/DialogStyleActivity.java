package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.mixiaoxiao.smoothcompoundbutton.SmoothCheckBox;
import com.mixiaoxiao.smoothcompoundbutton.SmoothCompoundButton;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DialogStyleActivity extends BaseActivity {
    public static final String TAG = "DialogStyleActivity";

    @BindView(R.id.toolbar_dialog)
    Toolbar toolbar;
    @BindView(R.id.linear_chose_btn_wrapper)
    LinearLayout choseBtnWrapper;

    private String modelName;
    private List<String> subModelNames;

    private String chosenItem = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        modelName = intent.getStringExtra("model_name");

        subModelNames = new ArrayList<>();
        Log.e(TAG, "initVariables: modelName : " + modelName);
        subModelNames.addAll(ModelConstantMap.getHolder(modelName).subModels);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (String eachModelName : subModelNames) {
            final SmoothCheckBox checkBox = new SmoothCheckBox(this);
            checkBox.setChecked(false);
            checkBox.setClickable(true);
            checkBox.setText(ModelConstantMap.getHolder(eachModelName).MODEL_NAME);
            checkBox.setTag(eachModelName);

            checkBox.setOnCheckedChangeListener(new SmoothCompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SmoothCompoundButton smoothCompoundButton, boolean b) {
                    for (int index = 0; index < choseBtnWrapper.getChildCount(); index++) {
                        ((SmoothCheckBox) choseBtnWrapper.getChildAt(index)).setChecked(false);
                    }
                    if (b) {
                        smoothCompoundButton.setChecked(true);
                        chosenItem = (String) smoothCompoundButton.getTag();
                    } else {
                        chosenItem = null;
                    }
                }
            });
            choseBtnWrapper.addView(checkBox);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dialog_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Log.e(TAG, "onOptionsItemSelected: R.id.action_save");
                if (chosenItem != null) {
                    Intent view = new Intent();
                    view.putExtra("model_name", chosenItem);
                    setResult(RESULT_OK, view);
                    Log.e(TAG, "onOptionsItemSelected: " + chosenItem);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
