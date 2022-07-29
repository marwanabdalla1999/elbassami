package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.ui.activity.SelectLocationActivity;
import com.albassami.logistics.ui.activity.select_from_to;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class towingservice extends Fragment {
    private ImageView btnBack;
   RelativeLayout local,homedeliviry,spcialtowing,fromlocationtoanother,spcialtowing2;
    public void setUpLocale() {

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            Locale myLocale = new Locale("ar");
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            getContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
            // new PreferenceHelper(this).getLanguage();
            sharedPreferences=getContext().getSharedPreferences("lang",MODE_PRIVATE);
            sharedPreferences.edit().putString("language","ar").apply();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpLocale();
View view=inflater.inflate(R.layout.fragment_towingservice, container, false);
btnBack=view.findViewById(R.id.btnBack);
local=view.findViewById(R.id.barMaintransloc1);
spcialtowing=view.findViewById(R.id.fromtolocation);
        spcialtowing2=view.findViewById(R.id.spcialtowing2);
homedeliviry=view.findViewById(R.id.barMainLayout);
        fromlocationtoanother=view.findViewById(R.id.barMaintransloc1);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        btnBack.setOnClickListener(i->getActivity().onBackPressed());
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        homedeliviry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoorToDoorFragment DoorToDoorFragment = new DoorToDoorFragment();
                HomeStartFragment.getInstance().addFragment(DoorToDoorFragment,true, Const.BRANCHES_FRAGMENT,true);
            }
        });
spcialtowing.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putString(Const.PassParam.SERVICE_TYPE, getString(R.string.special_towing));

        CarBranchesFragment carBranchesFragment = new CarBranchesFragment();
        carBranchesFragment.setArguments(bundle);
        HomeStartFragment.getInstance().addFragment(carBranchesFragment,true, Const.BRANCHES_FRAGMENT,true);

    }
});
        fromlocationtoanother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SelectLocationActivity.class);
                intent.putExtra("Shipmenttype","10");
                intent.putExtra("type","1");
                startActivity(intent);            }
        });
        spcialtowing2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(getContext(),SelectLocationActivity.class);
            intent.putExtra("Shipmenttype","20");
                intent.putExtra("type","3");
                startActivity(intent);
            }
        });
    return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

}
