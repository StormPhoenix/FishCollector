package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.presenter.interfaces.base.RequestCallback;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.impls.TreeViewImpl;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.mvp.ui.dialog.ProgressDialogGenerator;
import com.stormphoenix.fishcollector.mvp.ui.fragments.base.BaseFragment;
import com.stormphoenix.fishcollector.network.HttpMethod;
import com.stormphoenix.fishcollector.network.HttpResult;
import com.stormphoenix.fishcollector.shared.KeyGenerator;
import com.stormphoenix.fishcollector.shared.ModelUtils;
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

    private ProgressDialogGenerator generator = null;
    private DbManager dbManager = null;

    private TreeItemHolder.ItemOperationListener listener = null;
    private TreeNode.TreeNodeClickListener nodeClickListener = null;
    private ITreeView treeView;

    // 当前被点击的树形节点
    private TreeNode currentNode;
    private BaseFragment currentFragment = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
        dbManager = new DbManager(this);
        listener = new TreeItemHolder.ItemOperationListener() {
            @Override
            public void onItemAddBtnClicked(TreeNode node, String key, String value) {
                if (ModelConstantMap.getHolder(value).subModels.isEmpty()) {
                    return;
                }
                currentNode = node;
                Intent view = new Intent(MainActivity.this, DialogStyleActivity.class);
                view.putExtra(key, value);
                startActivityForResult(view, REQUEST_CODE_ADD_NODE);
            }

            @Override
            public void onItemDeleteBtnClicked(TreeNode node) {
                treeView.deleteNode(node);
                BaseModel attachedModel = ((ITreeView.TreeItem) (node.getValue())).getAttachedModel();
                dbManager.delete(attachedModel);
                if (((ITreeView.TreeItem) (node.getValue())).getAttachedFragment() == currentFragment) {
                    getFragmentManager().beginTransaction()
                            .remove(currentFragment)
                            .commit();
                    setMainContent();
                }
            }
        };

        nodeClickListener = new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                ITreeView.TreeItem item = (ITreeView.TreeItem) value;
                String modelClassName = item.modelConstant;
                mEmptyDisplayWrapper.setVisibility(View.GONE);
                BaseFragment attachedFragment = item.getAttachedFragment();
                getFragmentManager().beginTransaction().replace(R.id.layout_fragment_wrapper, attachedFragment, attachedFragment.getClass().getName()).commit();
                currentFragment = attachedFragment;
                toolbar.setTitle(ModelConstantMap.getHolder(currentFragment.getAttachedBean().getClass().getName()).MODEL_NAME);
            }
        };
    }

    @Override
    protected void initViews() {
        initToolbar();
        initTreeView();
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
    private void initTreeView() {
        treeView = new TreeViewImpl(this);
        treeView.setItemOprationListener(listener);
        treeView.setNodeClickListener(nodeClickListener);
        treeView.buildTree();
        treeViewWrapper.addView(treeView.getView());
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

        treeView.addNode(null, treeItem);
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

        treeView.addNode(currentNode, treeItem);
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
        if (treeView == null) {
            return;
        }
        if (treeView.getRootFirstChildFragment() != null) {
            mEmptyDisplayWrapper.setVisibility(View.GONE);
            currentFragment = treeView.getRootFirstChildFragment();
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
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu, menu);
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
                        initTreeView();
                        setMainContent();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        generator.cancel();
                        Snackbar.make(mEmptyDisplayWrapper, getResources().getString(R.string.download_data_failed), Snackbar.LENGTH_SHORT).show();
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
            case R.id.action_dispatch:
                Intent intent = new Intent(MainActivity.this, MemberTaskActivity.class);
                startActivity(intent);
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
