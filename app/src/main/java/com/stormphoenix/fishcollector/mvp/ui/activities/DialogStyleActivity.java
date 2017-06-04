package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.checkboxs.CheckBoxWrapper;
import com.stormphoenix.fishcollector.network.model.Acc;
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DialogStyleActivity extends BaseActivity {
    public static final String TAG = "DialogStyleActivity";

    public static final String SHOW_TYPE = "show_type";
    public static final int SHOW_TYPE_SUB_MODELS = 0;
    public static final int SHOW_TYPE_MEMBERS = 1;
    private int showType = -1;

    public static final String MODEL_NAME = "model_name";

    @BindView(R.id.toolbar_dialog)
    Toolbar toolbar;
    @BindView(R.id.linear_chose_btn_wrapper)
    CheckBoxWrapper choseBtnWrapper;

    private String modelName;
    private List<String> subModelNames;
    private String modelId;
    private List<Acc> members;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        showType = intent.getIntExtra(SHOW_TYPE, -1);

        switch (showType) {
            case SHOW_TYPE_SUB_MODELS:
                modelName = intent.getStringExtra(MODEL_NAME);
                // 获取所有子model数据
                subModelNames = new ArrayList<>();
                subModelNames.addAll(ModelConstantMap.getHolder(modelName).subModels);
                break;
            case SHOW_TYPE_MEMBERS:
                modelId = intent.getStringExtra("model_id");
                initMembers();
                break;
        }
    }

    private void initMembers() {
        members = new ArrayList<>();
        GroupRecord recordContent = FSManager.getInstance().getRecordContent();
        if (recordContent == null || recordContent.group == null) {
            return;
        }

        members.add(recordContent.group.header);
        if (recordContent.group.members != null && recordContent.group.members.size() != 0) {
            // 组内没有任何成员
            for (Acc acc : recordContent.group.members) {
                members.add(acc);
            }
        } else {
            Log.e(TAG, "refreshData: no members !!!");
        }
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switch (showType) {
            case SHOW_TYPE_SUB_MODELS:
                for (String eachModelName : subModelNames) {
                    choseBtnWrapper.addCheckBox(eachModelName, ModelConstantMap.getHolder(eachModelName).MODEL_NAME);
                }
                break;
            case SHOW_TYPE_MEMBERS:
                for (Acc account : members) {
                    choseBtnWrapper.addCheckBox(account.name, account.name);
                }
                break;
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
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_chose:
                switch (showType) {
                    case SHOW_TYPE_SUB_MODELS:
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
                    case SHOW_TYPE_MEMBERS:
                        if (choseBtnWrapper.getChosenValue() != null) {
                            Intent intent = new Intent();
                            intent.putExtra("chosen_name", choseBtnWrapper.getChosenValue());
                            intent.putExtra("model_id", modelId);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        break;
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
