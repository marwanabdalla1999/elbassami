package com.albassami.logistics.ui.Adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class pagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
  //  ArrayList<ImageView> titles;

    public pagerAdapter(FragmentManager fm) {
        super(fm);
     fragments=new ArrayList<>();
       // titles=new ArrayList<>();
    }
    public void addfragment(Fragment fragment){
        fragments.add(fragment);


    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
