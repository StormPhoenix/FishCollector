package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stormphoenix.fishcollector.Locals;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeBuilder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.treeholder.TreeAddDeleteHolder;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ActionDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.network.model.Group;
import com.stormphoenix.fishcollector.shared.KeyGenerator;
import com.stormphoenix.fishcollector.shared.ModelUtils;
import com.stormphoenix.fishcollector.shared.ViewUtils;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_ADD_NODE = 5;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.tree_view_wrapper)
    FrameLayout treeViewWrapper;
    @BindView(R.id.btn_add_site_main)
    FloatingActionButton btnAddSite;
    @BindView(R.id.layout_fragment_wrapper)
    FrameLayout layoutFragmentWrapper;
    @BindView(R.id.empty_display_wrapper)
    RelativeLayout mEmptyDisplayWrapper;
    @BindView(R.id.content_main)
    CoordinatorLayout contentMain;

    private ProgressDialogGenerator generator = null;
    private DbManager dbManager = null;

    private TreeAddDeleteHolder.ItemAddDeleteListener listener = null;
    private TreeNode.TreeNodeClickListener nodeClickListener = null;

    // 当前被点击的树形节点
    private TreeNode currentNode;
    private BaseFragment currentFragment = null;
    private TreeBuilder treeBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * initVariables() 用于初始化 Locales 中所有的数据
     */
    @Override
    protected void initVariables() {
        // 初始化当前登录用户所有的组
//        FSManager.getInstance().queryAllGroupsAsync(new FSManager.FsCallback<List<Group>>() {
//            @Override
//            public void call(List<Group> groups) {
//                Log.e(TAG, "call: " + groups.toString());
//                Locals.userGroups = groups;
//                if (Locals.userGroups == null || Locals.userGroups.size() == 0) {
//                    Locals.currentGroup = -1;
//                } else {
//                    Locals.currentGroup = 0;
//                }
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//                Snackbar.make(drawerLayout, errorMsg, Snackbar.LENGTH_LONG).show();
//            }
//        });
//        FSManager.getInstance().queryDispatchTablesAsync(new FSManager.FsCallback<List<DispatchTable>>() {
//            @Override
//            public void call(List<DispatchTable> dispatchTables) {
//                Log.e(TAG, "call: " + dispatchTables.toString());
//                Locals.dispatchTables = dispatchTables;
//                if (Locals.dispatchTables == null || Locals.dispatchTables.size() == 0) {
//                    Locals.currentDispatchTable = -1;
//                } else {
//                    Locals.currentDispatchTable = 0;
//                }
//            }
//
//            @Override
//            public void onError(String errorMsg) {
//                Snackbar.make(drawerLayout, errorMsg, Snackbar.LENGTH_LONG).show();
//            }
//        });
        dbManager = new DbManager(this);
        // 点击树木项进行操作
//        listener = new TreeAddDeleteHolder.ItemAddDeleteListener() {
//            @Override
//            public void onItemAddBtnClicked(TreeNode node, String key, String value) {
//                if (ModelConstantMap.getHolder(value).subModels.isEmpty()) {
//                    return;
//                }
//                currentNode = node;
//                Intent view = new Intent(MainActivity.this, DialogStyleActivity.class);
//                view.putExtra(key, value);
//                startActivityForResult(view, REQUEST_CODE_ADD_NODE);
//            }
//
//            @Override
//            public void onItemDeleteBtnClicked(TreeNode node) {
////                treeView.deleteNode(node);
//                BaseModel attachedModel = ((ITreeView.TreeItem) (node.getValue())).getAttachedModel();
//                dbManager.delete(attachedModel);
//                if (((ITreeView.TreeItem) (node.getValue())).getAttachedFragment() == currentFragment) {
//                    getFragmentManager().beginTransaction()
//                            .remove(currentFragment)
//                            .commit();
//                    setMainContent();
//                }
//            }
//        };
        // 点击数目切换 Fragment
//        nodeClickListener = new TreeNode.TreeNodeClickListener() {
//            @Override
//            public void onClick(TreeNode node, Object value) {
//                ITreeView.TreeItem item = (ITreeView.TreeItem) value;
//                String modelClassName = item.modelConstant;
//                mEmptyDisplayWrapper.setVisibility(View.GONE);
//                BaseFragment attachedFragment = item.getAttachedFragment();
//                getFragmentManager().beginTransaction().replace(R.id.layout_fragment_wrapper, attachedFragment, attachedFragment.getClass().getName()).commit();
//                currentFragment = attachedFragment;
//                toolbar.setTitle(ModelConstantMap.getHolder(currentFragment.getAttachedBean().getClass().getName()).MODEL_NAME);
//            }
//        };
    }

    @Override
    protected void initViews() {
        initToolbar();
        refreshTreeView();
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.add_monitor));
                builder.setMessage(getString(R.string.sure_to_add_monitor));
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        addMonitorSite();
                        setMainContent();
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult:");
        switch (requestCode) {
            case REQUEST_CODE_ADD_NODE:
                if (resultCode == RESULT_OK) {
                    String modelName = data.getStringExtra(DialogStyleActivity.MODEL_NAME);
                    addNewNode(modelName);
                }
                break;
            case REQUEST_CODE_SELECT:
                // 图片选择完毕，更新数据
                currentFragment.updateData();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 从这里开始创建树
     */
    private void refreshTreeView() {
        // 先判断当前组是哪一个组
        if (Locals.userGroups == null || Locals.currentGroup == -1) {
            Snackbar.make(contentMain, getString(R.string.havent_chosen_group), Snackbar.LENGTH_LONG).show();
            return;
        }

        if (Locals.dispatchTables == null || Locals.currentDispatchTable == -1) {
            Snackbar.make(contentMain, getString(R.string.dispatch_table_error), Snackbar.LENGTH_LONG).show();
            return;
        }

        TreeNode.BaseNodeViewHolder holder = new TreeAddDeleteHolder(MainActivity.this);
        if (treeBuilder == null) {
            if (Locals.isHeader) {
                this.treeBuilder = new TreeBuilder(MainActivity.this, holder, Locals.userGroups.get(Locals.currentGroup), Locals.dispatchTables.get(Locals.currentGroup), TreeBuilder.HEADER_TREE_TYPE);
            } else {
                this.treeBuilder = new TreeBuilder(MainActivity.this, holder, Locals.userGroups.get(Locals.currentGroup), Locals.dispatchTables.get(Locals.currentGroup), TreeBuilder.MEMBER_TREE_TYPE);
            }
        }
        treeBuilder.buildTree();
        treeViewWrapper.addView(treeBuilder.getAndroidTreeView().getView());
    }

    private void addMonitorSite() {
        MonitoringSite model = new MonitoringSite();
        model.setModelId(KeyGenerator.generateModelKey(MonitoringSite.class.getSimpleName()));
        Long modelMainKey = saveLocal(model);
        model.setId(modelMainKey);

        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(MonitoringSite.class.getName());
        treeItem.setAttachedModel(model);

        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(this, ModelConstantMap.getHolder(ModelConstant.MONITORING_SITE).fragmentClassName);
        attachedFragment.setModel(model);
        treeItem.setAttachedFragment(attachedFragment);

//        treeView.addNode(null, treeItem);
    }

    private void addNewNode(String modelClassName) {
        BaseModel attachedModel = ((ITreeView.TreeItem) currentNode.getValue()).getAttachedModel();
        if (attachedModel == null) {
            Log.e(TAG, "addNewNode: model == null");
        }
        Long currentId = attachedModel.getId();

        BaseModel resultObj = ModelUtils.createModelObject(currentId, modelClassName);
        Long saveId = saveLocal(resultObj);
        resultObj.setId(saveId);

        ITreeView.TreeItem treeItem = new ITreeView.TreeItem(modelClassName);
        treeItem.setAttachedModel(resultObj);

        Log.e(TAG, "addNewNode: " + ModelConstantMap.getHolder(resultObj.getClass().getName()).fragmentClassName);
        BaseFragment attachedFragment = (BaseFragment) Fragment.instantiate(this, ModelConstantMap.getHolder(resultObj.getClass().getName()).fragmentClassName);
        attachedFragment.setModel(resultObj);
        treeItem.setAttachedFragment(attachedFragment);

//        treeView.addNode(currentNode, treeItem);
    }

    private Long saveLocal(BaseModel modelObj) {
        DbManager dbManager = new DbManager(this);
        return dbManager.save(modelObj);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generator = new ProgressDialogGenerator(this);
        setMainContent();
    }

    private void setMainContent() {
        if (treeBuilder == null) {
            return;
        }
        if (treeBuilder.getRootFirstChildFragment() != null) {
            mEmptyDisplayWrapper.setVisibility(View.GONE);
            currentFragment = treeBuilder.getRootFirstChildFragment();
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment_wrapper, currentFragment, currentFragment.getClass().getName())
                    .commit();
            toolbar.setTitle(ModelConstantMap.getHolder(currentFragment.getAttachedBean().getClass().getName()).MODEL_NAME);
        } else {
            mEmptyDisplayWrapper.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu_normal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_download:
                HttpMethod.getInstance().downloadData(new RequestCallback<HttpResult<List<MonitoringSite>>>() {
                    @Override
                    public void beforeRequest() {
                        generator.cancelable(false);
                        generator.circularProgress();
                        generator.title("下载");
                        generator.content("下载中...");
                        generator.show();
                    }

                    @Override
                    public void success(HttpResult<List<MonitoringSite>> data) {
                        generator.cancel();
                        removeAllTreeView();
                        dbManager.saveModels(data.getData());
                        refreshTreeView();
                        setMainContent();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        generator.cancel();
                        Snackbar.make(contentMain, getResources().getString(R.string.download_data_failed), Snackbar.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.action_save:
                if (currentFragment != null) {
                    currentFragment.save();
                }
                break;
            case R.id.action_upload:
                if (currentFragment != null) {
                    currentFragment.uploadModel();
                }
                break;
            case R.id.action_manager_group:
                Intent intent = new Intent(MainActivity.this, GroupTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.action_switch_group:
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.ui_choose_button, drawerLayout, false);
                // 在用户点击切换组之前，必须加载完成组别
                assert Locals.userGroups != null;
                List<Group> groups = Locals.userGroups;
                if (groups == null || groups.size() == 0) {
                    Snackbar.make(contentMain, getString(R.string.group_empty), Snackbar.LENGTH_LONG).show();
                    break;
                }

                final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
                for (int index = 0; index < groups.size(); index++) {
                    RadioButton rb = new RadioButton(MainActivity.this);
                    rb.setTag(new Integer(index));
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rb.getLayoutParams();
                    layoutParams.bottomMargin = ViewUtils.dp2Pixel(MainActivity.this, 5);
                    rb.setLayoutParams(layoutParams);
                    radioGroup.addView(rb);
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        Log.e(TAG, "onCheckedChanged: " + checkedId);
                    }
                });

                final ActionDialogGenerator adg = new ActionDialogGenerator(MainActivity.this);
                adg.title(getString(R.string.switch_group));
                adg.cancelable(true);
                adg.customView(view);
                adg.setActionButton(DialogAction.POSITIVE, getString(R.string.ok));
                adg.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        int radioButtonId = radioGroup.getCheckedRadioButtonId();
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonId);
                        if (radioButton != null) {
                            Integer index = (Integer) radioButton.getTag();
                            Locals.currentGroup = index;
//                            refreshTreeView();
                        }
                        adg.cancel();
                    }
                });
                adg.show();
                break;
//            case R.id.action_create_group:
//                 利用弹出对话框的形式穿件新组
//                final ActionDialogGenerator groupNameDialog = new ActionDialogGenerator(this);
//                groupNameDialog.title(getString(R.string.group_name));
//                View groupNameView = (View) getLayoutInflater().inflate(R.layout.edit_frame_layout, drawerLayout, false);
//                final AppCompatEditText editText = (AppCompatEditText) groupNameView.findViewById(R.id.dialog_edit_text);
//
//                groupNameDialog.customView(groupNameView);
//                groupNameDialog.onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        String trim = editText.getText().toString().trim();
//                        if (!TextUtils.isEmpty(trim)) {
//                            Group group = new Group();
//                            group.setGroupName(trim);
//                            group.setGroupId(KeyGenerator.generateGroupKey());
//                            group.setGroupHeadId(ConfigUtils.getInstance().getUsername());
//                            DbManager dbManager = new DbManager(MainActivity.this);
//                            dbManager.saveGroup(group);
//                            groupNameDialog.cancel();
//                        } else {
//                            groupNameDialog.cancel();
//                            Snackbar.make(drawerLayout, getString(R.string.group_name_can_not_be_empty), Snackbar.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                groupNameDialog.cancelable(false);
//                groupNameDialog.setActionButton(DialogAction.POSITIVE, getString(R.string.ok));
//                groupNameDialog.show();
//                break;
//            case R.id.action_dispatch:
//                Intent intent = new Intent(MainActivity.this, GroupTaskActivity.class);
//                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeAllTreeView() {
        dbManager.deleteAll();
        treeViewWrapper.removeAllViews();
        if (currentFragment != null) {
            getFragmentManager().beginTransaction().remove(currentFragment).commit();
            currentFragment = null;
        }
        System.gc();
    }
}
