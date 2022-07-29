package com.albassami.logistics.ui.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.ui.activity.AddVehiclesActivity;
import com.albassami.logistics.ui.activity.HistoryActivity;
import com.albassami.logistics.ui.activity.MainActivity;
import com.albassami.logistics.ui.activity.ProfileActivity;
import com.albassami.logistics.ui.activity.branches;
import com.albassami.logistics.ui.activity.contactus;
import com.albassami.logistics.ui.activity.tracking;

import java.util.Locale;

import butterknife.BindView;

import static android.content.Context.MODE_PRIVATE;

public class HomeStartFragment extends Fragment implements View.OnClickListener {
    LinearLayout layoutCarShip,layoutTowing,layoutOther,layoutCars,layoutProfile,layoutRides,tracking,contactus,branches;
    private static HomeStartFragment instance = null;
    FrameLayout fragmentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bnt_menu)
    ImageButton bntMenu;
    MainActivity mainActivity;
    private ActionBarDrawerToggle drawerToggle;

    DrawerLayout drawerLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpLocale();
        View view = inflater.inflate(R.layout.activity_home_start, container, false);
        mainActivity=new MainActivity();
        inIt(view);
        drawerLayout= view.findViewById(R.id.drawer);
        bntMenu=view.findViewById(R.id.bnt_menu);
        initDrawer();
        setUpDrawerAndToolBar();


        return view;
    }
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
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void inIt(View view){
        instance = this;
        layoutCars = view.findViewById(R.id.layout_my_cars);
        layoutProfile = view.findViewById(R.id.layout_profile);
        layoutRides = view.findViewById(R.id.layout_my_rides);
        layoutCarShip = view.findViewById(R.id.layoutCarShipping);
        layoutOther = view.findViewById(R.id.layoutOthers);
        layoutTowing = view.findViewById(R.id.layoutTowing);
        fragmentLayout = view.findViewById(R.id.content_fragment);
        tracking=view.findViewById(R.id.tracking);
        branches=view.findViewById(R.id.branches);
        contactus=view.findViewById(R.id.contactus);
        tracking.setOnClickListener(this);
        branches.setOnClickListener(this);
        contactus.setOnClickListener(this);
        layoutTowing.setOnClickListener(this);
        layoutOther.setOnClickListener(this);
        layoutCarShip.setOnClickListener(this);
        layoutCars.setOnClickListener(this);
        layoutProfile.setOnClickListener(this);
        layoutRides.setOnClickListener(this);


    }
    public void setUpDrawerAndToolBar() {
        bntMenu.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));


    }
    private void initDrawer() {
           drawerToggle = new ActionBarDrawerToggle((Activity) getContext(), drawerLayout,
              toolbar, R.string.drawer_open, R.string.drawer_close);


    }






   public void closeDrawer() {
        drawerLayout.closeDrawers();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layoutCarShipping:
                Const.SERVICE_TYPE = Const.CarShip;
               // fragmentLayout.setVisibility(View.VISIBLE);
                Fragment fragment = new CarTabFragment();
                addFragment(fragment, true, Const.CAR_FRAGMENT, true);
                break;
            case R.id.layoutTowing:
                Const.SERVICE_TYPE = Const.HomeDelivery;
                layoutTowing.setVisibility(View.VISIBLE);
                addFragment(new towingservice(), true, Const.DOOR_FRAGMENT, true);
                break;
            case R.id.layoutOthers:
                break;
            case R.id.layout_my_cars:
                startActivity(new Intent(getContext(), AddVehiclesActivity.class));
                break;
            case R.id.layout_profile:
                Intent i = new Intent(getContext(), ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.layout_my_rides:
                startActivity(new Intent(getContext(), HistoryActivity.class).putExtra("isHistory", true));
                break;
            case R.id.tracking:
                startActivity(new Intent(getContext(), tracking.class));
                break;
            case R.id.branches:
                startActivity(new Intent(getContext(), branches.class));
                break;
            case R.id.contactus:
                startActivity(new Intent(getContext(), contactus.class));
                break;
        }
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, boolean isAnimate) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAnimate) {
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("")){
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left,
                    R.anim.slide_out_right);}
            else if(sharedPreferences.getString("language","").equals("ar")){
                ft.setCustomAnimations(R.anim.slide_in_left,
                        R.anim.slide_out_right, R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);

        }
        ft.replace(R.id.content_fragment, fragment, tag);
        ft.commit();
    }
    public static HomeStartFragment getInstance() {
        return instance;
    }

}
