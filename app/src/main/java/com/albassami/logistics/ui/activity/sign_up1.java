package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.albassami.logistics.R;
import com.albassami.logistics.ui.Fragment.ForgotpassFragment;
import com.albassami.logistics.ui.Fragment.sign_in_phone;

public class sign_up1 extends AppCompatActivity {
    private static sign_up1 instance=null;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        instance=this;
if(getIntent().getStringExtra("type").equals("signup")){
    sign_in_phone sign_in_phone=new sign_in_phone();

    addFragment(sign_in_phone,false,"sign_in_phone",true);}
else{
    ForgotpassFragment forgotpassFragment=new ForgotpassFragment();

    addFragment(forgotpassFragment,false,"forgetpassword",true);

}

    }
    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, boolean isAnimate) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (isAnimate) {
            SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
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
        ft.replace(R.id.content_frame1, fragment, tag);
        ft.commit();
    }
    public static sign_up1 getInstance() {
        return instance;
    }
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
