package com.albassami.logistics.ui.Fragment;

import android.content.Context;
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

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


public class CarTabFragment extends Fragment {
    private ImageView btnBack;
    RelativeLayout local , international , special_towing  ;
  //  , full_load

            //,homedeliviry,spcialtowing,fromlocationtoanother,spcialtowing2;
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
        View view=inflater.inflate(R.layout.bottom_sheet_car, container, false);
        btnBack=view.findViewById(R.id.btnBack);
        local=view.findViewById(R.id.barMaintransloc2);
        international=view.findViewById(R.id.barMaintransloc3);
        special_towing= view.findViewById(R.id.barMaintransloc4);

        //full_load=  view.findViewById(R.id.barMaintransloc5);

       // spcialtowing=view.findViewById(R.id.fromtolocation);
        //spcialtowing2=view.findViewById(R.id.spcialtowing2);
        //homedeliviry=view.findViewById(R.id.barMainLayout);
     //   fromlocationtoanother=view.findViewById(R.id.barMaintransloc);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        btnBack.setOnClickListener(i->getActivity().onBackPressed());

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(Const.PassParam.SERVICE_TYPE, getString(R.string.intercity));

                CarBranchesFragment carBranchesFragment = new CarBranchesFragment();
                carBranchesFragment.setArguments(bundle);
                HomeStartFragment.getInstance().addFragment(carBranchesFragment,true, Const.BRANCHES_FRAGMENT,true);
            }
        });

        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(Const.PassParam.SERVICE_TYPE, getString(R.string.international));

                CarBranchesFragment carBranchesFragment = new CarBranchesFragment();
                carBranchesFragment.setArguments(bundle);
                HomeStartFragment.getInstance().addFragment(carBranchesFragment,true, Const.BRANCHES_FRAGMENT,true);
            }
        });



        special_towing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(Const.PassParam.SERVICE_TYPE, getString(R.string.special_towing));

                CarBranchesFragment carBranchesFragment = new CarBranchesFragment();
                carBranchesFragment.setArguments(bundle);
                HomeStartFragment.getInstance().addFragment(carBranchesFragment,true, Const.BRANCHES_FRAGMENT,true);
            }
        });


      //  full_load.setOnClickListener(new View.OnClickListener() {
        //    @Override
          //  public void onClick(View v) {
            //    Bundle bundle=new Bundle();
            //    bundle.putString(Const.PassParam.SERVICE_TYPE, getString(R.string.full_load));
             //   CarBranchesFragment carBranchesFragment = new CarBranchesFragment();
             //   carBranchesFragment.setArguments(bundle);
             //   HomeStartFragment.getInstance().addFragment(carBranchesFragment,true, Const.BRANCHES_FRAGMENT,true);
          //  }
       // });


        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
