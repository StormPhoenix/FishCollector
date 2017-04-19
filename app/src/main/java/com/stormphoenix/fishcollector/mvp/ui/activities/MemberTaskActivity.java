package com.stormphoenix.fishcollector.mvp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.adapter.FragmentsAdapter;
import com.stormphoenix.fishcollector.mvp.ui.fragments.MembersFragment;
import com.stormphoenix.fishcollector.mvp.ui.fragments.TreeNodeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberTaskActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    SmartTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    FragmentsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_task);
        ButterKnife.bind(this);
        createPager();
    }

    private void createPager() {
        mAdapter = createAdapter();
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mAdapter);
        tabLayout.setViewPager(viewPager);
    }

    protected FragmentsAdapter createAdapter() {
//        String[] titleList = {getString(R.string.wait_to_be_dispatch)};
        String[] titleList = {getString(R.string.monitorsite), getString(R.string.wait_to_be_dispatch)};
        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new TreeNodeFragment());
        fragmentList.add(new MembersFragment());
//        fragmentList.add(staredFragment);

        mAdapter = new FragmentsAdapter(this.getSupportFragmentManager());
        mAdapter.setFragmentList(fragmentList, titleList);
        return mAdapter;
    }
}
