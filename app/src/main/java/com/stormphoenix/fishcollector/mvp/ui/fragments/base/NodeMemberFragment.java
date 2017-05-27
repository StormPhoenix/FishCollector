package com.stormphoenix.fishcollector.mvp.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stormphoenix.fishcollector.R;
import com.stormphoenix.fishcollector.mvp.ui.fragments.MembersFragment;
import com.stormphoenix.fishcollector.mvp.ui.fragments.TreeNodeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StormPhoenix on 17-5-4.
 * StormPhoenix is a intelligent Android developer.
 */

public class NodeMemberFragment extends Fragment {

    private ViewPager viewPager;
    private FragmentPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_node_member, container, false);
        initViewPagers(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initViewPagers(View view) {
        final List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new TreeNodeFragment());
        fragmentList.add(new MembersFragment());

        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        viewPager = (ViewPager) view.findViewById(R.id.node_member_view_pager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(mAdapter);
    }
}
