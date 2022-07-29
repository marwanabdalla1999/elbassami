package com.albassami.logistics.ui.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class pagerAdapter1 extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    ArrayList<String> titles;

    public pagerAdapter1(FragmentManager fm) {
        super(fm);
     fragments=new ArrayList<>();
        titles=new ArrayList<>();
    }
    public void addfragment(Fragment fragment,String title){
        fragments.add(fragment);
        titles.add(title);

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);

    }
}
