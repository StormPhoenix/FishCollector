package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.FragmentsAdapter;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.group.GroupMemberFragment;
import com.stormphoenix.fishcollector.mvp.ui.fragments.group.GroupTaskFragment;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.TaskTable;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupTaskActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CHOSE_MEMBER = 48;
    private static final String TAG = GroupTaskActivity.class.getSimpleName();
    @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    FragmentsAdapter mAdapter;
    @BindView(R.id.group_manager_toolbar)
    Toolbar toolbar;
    @BindView(R.id.group_task_wrapper)
    CoordinatorLayout taskWrapper;

    private GroupTaskFragment groupTaskFrag;
    private GroupMemberFragment groupMemFrag;
    private ProgressDialogGenerator generator;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_task_menu_header, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_task);
        ButterKnife.bind(this);
        initViews();
        createPager();
    }


    private void initViews() {
        toolbar.setTitle(getString(R.string.manager_group));
        toolbar.setTitle(ConfigUtils.getInstance().getUserGroupId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_upload_task_header:
                // 提交任务分配表
                uploadTaskTable();
                return true;
            case R.id.action_refresh_task_header:
                // 从网络获取数据刷新本地
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadTaskTable() {
        final ProgressDialogGenerator generator = new ProgressDialogGenerator(this);
        generator.title(getResources().getString(R.string.submiting));
        generator.content(getResources().getString(R.string.please_waiting));
        generator.circularProgress();
        generator.cancelable(false);

        TaskTable taskTable = FSManager.getInstance().getRecordContent().taskTable;
        if (taskTable == null) {
            Snackbar.make(taskWrapper, "尚未有任务分配信息", Snackbar.LENGTH_LONG).show();
            return;
        }

        HttpMethod.getInstance().uploadTaskTable(ConfigUtils.getInstance().getUsername(),
                ConfigUtils.getInstance().getPassword(),
                taskTable,
                new RequestCallback<HttpResult<Void>>() {
                    @Override
                    public void beforeRequest() {
                        generator.show();
                    }

                    @Override
                    public void success(HttpResult<Void> data) {
                        switch (data.getResultCode()) {
                            case Constants.USER_NOT_EXISTS:
                                Snackbar.make(taskWrapper, "用户不存在，无法上传数据", Snackbar.LENGTH_LONG).show();
                                generator.cancel();
                                break;
                            case Constants.USER_NOT_IN_GROUP:
                                Snackbar.make(taskWrapper, "用户不在组中，无法上传数据", Snackbar.LENGTH_LONG).show();
                                generator.cancel();
                                break;
                            case Constants.USER_IS_NOT_HEADER:
                                Snackbar.make(taskWrapper, "用户不是组长，无法上传数据", Snackbar.LENGTH_LONG).show();
                                generator.cancel();
                                break;
                            case Constants.SUCCESS:
                                Snackbar.make(taskWrapper, "上传成功", Snackbar.LENGTH_LONG).show();
                                generator.cancel();
                                break;
                        }
//                        Snackbar.make(taskWrapper, , Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        generator.cancel();
                        Snackbar.make(taskWrapper, errorMsg, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    private void createPager() {
        mAdapter = createAdapter();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
    }

    protected FragmentsAdapter createAdapter() {
        String[] titleList = {getString(R.string.dispatch_task), getString(R.string.group_member)};
        List<Fragment> fragmentList = new ArrayList<>();
        groupTaskFrag = new GroupTaskFragment();
        fragmentList.add(groupTaskFrag);
        groupMemFrag = new GroupMemberFragment();
        fragmentList.add(groupMemFrag);

        mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_CHOSE_MEMBER:
                if (resultCode == RESULT_OK) {
                    String chosenName = data.getStringExtra("chosen_name");
                    String modelId = data.getStringExtra("model_id");
                    Log.e(TAG, "onActivityResult: chose member " + modelId + " " + chosenName);
                    FSManager.getInstance().saveTaskEntry(chosenName, modelId);
                    if (groupTaskFrag != null) {
                        groupTaskFrag.updateData();
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
