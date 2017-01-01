package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.db.DbManager;
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.model.beans.interfaces.BaseModel;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.impls.TreeViewImpl;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.KeyGenerator;
import com.stormphoenix.fishcollector.shared.ModelUtils;
import com.stormphoenix.fishcollector.shared.constants.ModelConstantMap;
import com.unnamed.b.atv.model.TreeNode;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    public static final int REQUEST_CODE_ADD_NODE = 5;

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.tree_view_wrapper)
    FrameLayout treeViewWrapper;
    @BindView(R.id.btn_add_site_main)
    Button btnAddSite;

    private TreeItemHolder.ItemOperationListener listener = null;
    private ITreeView treeView;

    // 当前被点击的树形节点
    private TreeNode currentNode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
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
                addMonitorSite();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
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
        Log.e(TAG, "onActivityResult: resultCode " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_ADD_NODE:
                if (resultCode == RESULT_OK) {
                    String modelName = data.getStringExtra(DialogStyleActivity.MODEL_NAME);
                    addNewNode(modelName);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initTreeView() {
        treeView = new TreeViewImpl(this);
        treeView.setItemOprationListener(listener);
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
        treeView.addNode(currentNode, treeItem);
    }

    private Long saveLocal(BaseModel modelObj) {
        DbManager dbManager = new DbManager(this);
        return dbManager.save(modelObj);
    }
}
