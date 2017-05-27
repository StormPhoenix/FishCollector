package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.FragmentsAdapter;
import com.stormphoenix.fishcollector.db.FSManager;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ActionDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.group.GroupFragment;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupTaskActivity extends AppCompatActivity {
    private static final String TAG = GroupTaskActivity.class.getName();

    @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    FragmentsAdapter mAdapter;
    @BindView(R.id.group_manager_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_create_new_group)
    FloatingActionButton fab;

    private GroupFragment managerFragment;
    private GroupFragment joinFragment;
    private ProgressDialogGenerator generator;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDialogGenerator generator = new ActionDialogGenerator(GroupTaskActivity.this);
                if (viewPager.getCurrentItem() == 0) {
                    View dialgoView = LayoutInflater.from(GroupTaskActivity.this).inflate(R.layout.edit_frame_layout, viewPager, false);
                    final AppCompatEditText editText = (AppCompatEditText) dialgoView.findViewById(R.id.dialog_edit_text);

                    generator.title(getString(R.string.create_group_name));
                    generator.customView(dialgoView);
                    generator.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            String groupName = editText.getText().toString().trim();
                            // 开始请求数据
                            requestCreateNewGroup(groupName);
                        }
                    });
                    generator.setActionButton(DialogAction.POSITIVE, getString(R.string.ok));
                    generator.cancelable(true);
                    generator.show();
                } else if (viewPager.getCurrentItem() == 1) {

                }
            }
        });
    }

    private void requestCreateNewGroup(String groupName) {
        HttpMethod.getInstance().createNewGroup(groupName, new RequestCallback<HttpResult<Group>>() {
            @Override
            public void beforeRequest() {
                if (generator == null) {
                    generator = new ProgressDialogGenerator(GroupTaskActivity.this);
                    generator.title(getString(R.string.uploading_group));
                    generator.content(getString(R.string.please_waiting));
                    generator.circularProgress();
                    generator.cancelable(false);
                }
                generator.show();
            }

            @Override
            public void success(HttpResult<Group> data) {
                if (data.getResultCode() == 0) {
                    // 成功
                    Log.e(TAG, "success: ");
                    FSManager.getInstance().saveGroupAsync(data.getData(), new FSManager.FsCallback<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            // 通知 Fragment 更新数据
                            generator.cancel();
                            managerFragment.refreshData();
                            joinFragment.refreshData();
                        }

                        @Override
                        public void onError(String errorMsg) {
                            Snackbar.make(viewPager, errorMsg, Snackbar.LENGTH_LONG).show();
                            generator.cancel();
                        }
                    });
                } else {
                    Log.e(TAG, "failed: ");
                    Snackbar.make(viewPager, getString(R.string.upload_data_failed), Snackbar.LENGTH_LONG).show();
                    generator.cancel();
                }
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "onError: " + errorMsg);
                generator.cancel();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createPager() {
        mAdapter = createAdapter();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
    }

    protected FragmentsAdapter createAdapter() {
//        String[] titleList = {getString(R.string.wait_to_be_dispatch)};
        String[] titleList = {getString(R.string.my_group), getString(R.string.join_group)};
        List<Fragment> fragmentList = new ArrayList<>();
        managerFragment = new GroupFragment(GroupFragment.MANAGE_GROUP_TYPE);
        fragmentList.add(managerFragment);
        joinFragment = new GroupFragment(GroupFragment.JOIN_GROUP_TYPE);
        fragmentList.add(joinFragment);

        mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
