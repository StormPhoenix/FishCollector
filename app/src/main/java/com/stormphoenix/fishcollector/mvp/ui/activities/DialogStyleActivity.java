package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.checkboxs.CheckBoxWrapper;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DialogStyleActivity extends BaseActivity {
    public static final String TAG = "DialogStyleActivity";

    public static final String MODEL_NAME = "model_name";

    @BindView(R.id.toolbar_dialog)
    Toolbar toolbar;
    @BindView(R.id.linear_chose_btn_wrapper)
    CheckBoxWrapper choseBtnWrapper;

    private String modelName;
    private List<String> subModelNames;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        modelName = intent.getStringExtra(MODEL_NAME);

        subModelNames = new ArrayList<>();
        subModelNames.addAll(ModelConstantMap.getHolder(modelName).subModels);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (String eachModelName : subModelNames) {
            choseBtnWrapper.addCheckBox(eachModelName);
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
                if (choseBtnWrapper.getChosenValue() != null) {
                    Intent view = new Intent();
                    view.putExtra(MODEL_NAME, choseBtnWrapper.getChosenValue());
                    setResult(RESULT_OK, view);
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
