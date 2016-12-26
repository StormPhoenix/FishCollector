package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.activities.base.BaseActivity;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeItemHolder;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.TreeViewImpl;
import com.stormphoenix.fishcollector.mvp.ui.component.treeview.interfaces.ITreeView;
import com.stormphoenix.fishcollector.shared.ModelConstant;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.activity_main)
    DrawerLayout drawerLayout;
    @BindView(R.id.tree_view_wrapper)
    FrameLayout treeViewWrapper;

    private ITreeView treeView;

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
    }

    private void initTreeView() {
        ITreeView treeView = new TreeViewImpl(this);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
