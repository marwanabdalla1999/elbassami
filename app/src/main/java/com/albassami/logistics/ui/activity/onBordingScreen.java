package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.widget.TextView;

import com.albassami.logistics.R;
import com.albassami.logistics.ui.Adapter.pagerAdapter;
import com.albassami.logistics.ui.Fragment.BorderScreen1;
import com.albassami.logistics.ui.Fragment.BorderScreen2;
import com.albassami.logistics.ui.Fragment.BorderScreen3;
import com.albassami.logistics.ui.Fragment.BorderScreen4;
import com.albassami.logistics.ui.Fragment.BorderScreen5;
import com.google.android.material.tabs.TabLayout;

public class onBordingScreen extends AppCompatActivity {
TextView skip;
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_bording_screen);
        skip=findViewById(R.id.skip);
         layout();



    }

    private void layout() {
         viewpager=findViewById(R.id.viewpager);
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        pagerAdapter view_pager_adapter=new pagerAdapter(getSupportFragmentManager());
        view_pager_adapter.addfragment(new BorderScreen1());
        view_pager_adapter.addfragment(new BorderScreen2());
        view_pager_adapter.addfragment(new BorderScreen3());
        view_pager_adapter.addfragment(new BorderScreen4());
        view_pager_adapter.addfragment(new BorderScreen5());
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")) {

          viewpager.setRotationY(180);
        }

        viewpager.setAdapter(view_pager_adapter);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position<view_pager_adapter.getCount()-1){
                    skip.setText(getString(R.string.skip));
                }
                else{
                    skip.setText(getString(R.string.finish));

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        skip.setOnClickListener(i->{startActivity(new Intent(onBordingScreen.this,SignInActivity.class));
        onBordingScreen.this.finish();});

    }


}
