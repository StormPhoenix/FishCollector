package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.db.FSManager;
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
import com.stormphoenix.fishcollector.network.model.GroupRecord;
import com.stormphoenix.fishcollector.shared.ConfigUtils;
import com.stormphoenix.fishcollector.shared.KeyGenerator;
import com.stormphoenix.fishcollector.shared.ModelUtils;
import com.stormphoenix.fishcollector.shared.constants.Constants;
import com.stormphoenix.fishcollector.shared.constants.ModelConstant;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

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

    private static final int MSG_FINISH_CREATE_GROUP = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FINISH_CREATE_GROUP:
                    generator.cancel();
                    Log.e(TAG, "handleMessage: invalidateOptionsMenu ");
                    supportInvalidateOptionsMenu();
                    break;
                default:
                    break;
            }
        }
    };

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
//        if (Locals.userGroups == null || Locals.currentGroup == -1) {
//            Snackbar.make(contentMain, getString(R.string.havent_chosen_group), Snackbar.LENGTH_LONG).show();
//            return;
//        }
//
//        if (Locals.dispatchTables == null || Locals.currentDispatchTable == -1) {
//            Snackbar.make(contentMain, getString(R.string.dispatch_table_error), Snackbar.LENGTH_LONG).show();
//            return;
//        }

//        TreeNode.BaseNodeViewHolder holder = new TreeAddDeleteHolder(MainActivity.this);
//        if (treeBuilder == null) {
//            if (Locals.isHeader) {
//                this.treeBuilder = new TreeBuilder(MainActivity.this, holder, Locals.userGroups.get(Locals.currentGroup), Locals.dispatchTables.get(Locals.currentGroup), TreeBuilder.HEADER_TREE_TYPE);
//            } else {
//                this.treeBuilder = new TreeBuilder(MainActivity.this, holder, Locals.userGroups.get(Locals.currentGroup), Locals.dispatchTables.get(Locals.currentGroup), TreeBuilder.MEMBER_TREE_TYPE);
//            }
//        }
//        treeBuilder.buildTree();
//        treeViewWrapper.addView(treeBuilder.getAndroidTreeView().getView());
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        createMenuDynamic(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        createMenuDynamic(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void createMenuDynamic(Menu menu) {
        // 判断是否在组里面，如果不在组里面，那么 record 可能为 null
        GroupRecord record = FSManager.getInstance().getRecordContent();

        if (ConfigUtils.getInstance().getUserGroupId() != null) {
            // 在组里面，判断是否是组长
            if (record.group.header.name.equals(ConfigUtils.getInstance().getUsername())) {
                getMenuInflater().inflate(R.menu.main_activity_toolbar_menu_header, menu);
            } else {
                // 不是组长
                getMenuInflater().inflate(R.menu.main_activity_toolbar_menu_member, menu);
            }
        } else {
            // 不在组里面
            getMenuInflater().inflate(R.menu.main_activity_toolbar_menu_normal, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_group:
                showCreateGroupDialog();
                break;
//            case R.id.action_download:
//                HttpMethod.getInstance().downloadData(new RequestCallback<HttpResult<List<MonitoringSite>>>() {
//                    @Override
//                    public void beforeRequest() {
//                        generator.cancelable(false);
//                        generator.circularProgress();
//                        generator.title("下载");
//                        generator.content("下载中...");
//                        generator.show();
//                    }
//
//                    @Override
//                    public void success(HttpResult<List<MonitoringSite>> data) {
//                        generator.cancel();
//                        removeAllTreeView();
//                        dbManager.saveModels(data.getData());
//                        refreshTreeView();
//                        setMainContent();
//                    }
//
//                    @Override
//                    public void onError(String errorMsg) {
//                        generator.cancel();
//                        Snackbar.make(contentMain, getResources().getString(R.string.download_data_failed), Snackbar.LENGTH_SHORT).show();
//                    }
//                });
//                break;
//            case R.id.action_save:
//                if (currentFragment != null) {
//                    currentFragment.save();
//                }
//                break;
//            case R.id.action_upload:
//                if (currentFragment != null) {
//                    currentFragment.uploadModel();
//                }
//                break;
//            case R.id.action_manager_group:
//                Intent intent = new Intent(MainActivity.this, GroupTaskActivity.class);
//                startActivity(intent);
//                break;

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

    private void showCreateGroupDialog() {
        final ActionDialogGenerator generator = new ActionDialogGenerator(MainActivity.this);
        View dialgoView = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_frame_layout, contentMain, false);
        final AppCompatEditText editText = (AppCompatEditText) dialgoView.findViewById(R.id.dialog_edit_text);
        editText.setHint(getString(R.string.input_group_name));
        generator.title(getString(R.string.create_group));
        generator.customView(dialgoView);
        generator.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                String groupName = editText.getText().toString().trim();
                // 开始请求数据
                requestCreateGroup(groupName);
                generator.cancel();
            }
        });
        generator.setActionButton(DialogAction.POSITIVE, getString(R.string.ok));
        generator.cancelable(true);
        generator.show();
    }

    // 发起创建分组的请求
    private void requestCreateGroup(String groupName) {
        HttpMethod.getInstance().createNewGroup(groupName, new RequestCallback<HttpResult<GroupRecord>>() {
            @Override
            public void beforeRequest() {
                if (generator == null) {
                    generator = new ProgressDialogGenerator(MainActivity.this);
                }
                generator.title(getString(R.string.uploading_group));
                generator.content(getString(R.string.please_waiting));
                generator.circularProgress();
                generator.cancelable(false);
                generator.show();
            }

            @Override
            public void success(final HttpResult<GroupRecord> data) {
                switch (data.getResultCode()) {
                    case Constants.USER_NOT_EXISTS:
                        generator.cancel();
                        Snackbar.make(contentMain, "用户不存在", Snackbar.LENGTH_LONG).show();
                        break;
                    case Constants.CREATE_GROUP_SUCCESS:
                        Snackbar.make(contentMain, "创建成功，id是 " + data.getData().group.groupId, Snackbar.LENGTH_LONG).show();
                        // 异步保存数据
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FSManager.getInstance().saveRecordContent(data.getData());
                                ConfigUtils.getInstance().setUserGroupId(data.getData().group.groupId);
                                generator.cancel();
                                // 因为是创建了分组，所以修改标题栏的菜单，同时弹出对话框显示返回的组id
                                mHandler.sendEmptyMessage(MSG_FINISH_CREATE_GROUP);
                            }
                        }).start();
                        break;
                    case Constants.USER_ALREADY_IN_GROUP:
                        generator.cancel();
                        Snackbar.make(contentMain, "您已经在组里面，无法创建", Snackbar.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(String errorMsg) {
                Log.e(TAG, "onError: " + errorMsg);
                generator.cancel();
            }
        });
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
