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
import com.stormphoenix.fishcollector.mvp.model.beans.MonitoringSite;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeViewImpl;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
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

    private ITreeView treeView;

    // 当前被点击的树形节点
    private TreeNode currentNode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {
        initToolbar();
        initTreeView();
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                treeView.addNode(null, new ITreeView.TreeItem(MonitoringSite.class.getName()));
            }
        });
    }

    private void initTreeView() {
        treeView = new TreeViewImpl(this);
        treeView.setItemOprationListener(new TreeItemHolder.ItemOperationListener() {
            @Override
            public void onItemAddBtnClicked(TreeNode node, String key, String value) {
                currentNode = node;
                Intent view = new Intent(MainActivity.this, DialogStyleActivity.class);
                view.putExtra(key, value);
                startActivityForResult(view, REQUEST_CODE_ADD_NODE);
            }

            @Override
            public void onItemDeleteBtnClicked(TreeNode node) {
                treeView.deleteNode(node);
            }
        });
        treeView.buildTree();
        treeViewWrapper.addView(treeView.getView());
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
                    String modelName = data.getStringExtra("model_name");

                    if (currentNode == null) {
                        Log.e(TAG, "onActivityResult: currentNode is null");
                    } else {
                        Log.e(TAG, "onActivityResult: currentNode is not null");
                    }

                    treeView.addNode(currentNode, new ITreeView.TreeItem(modelName));
                    Log.e(TAG, "onActivityResult: " + modelName);
                } else {
                    Log.e(TAG, "onActivityResult: result cancel");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
