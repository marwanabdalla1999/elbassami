package com.albassami.logistics.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.albassami.logistics.NewUtilsAndPref.splash.SplashAnimationHelper;
import com.albassami.logistics.R;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by Mahesh on 7/19/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int RC_APP_UPDATE = 100;
    @BindView(R.id.splashAnimationLayout)
    RelativeLayout splashAnimationLayout;
    private SplashAnimationHelper.SplashRouteAnimation splashRouteAnimation;
    PrefUtils prefUtils;
    AppUpdateManager appUpdateManager;
    InstallStateUpdatedListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animation);
        try {
            SharedPreferences sharedPreferences =getSharedPreferences("lang", Context.MODE_PRIVATE);
            setLocale(sharedPreferences.getString("language", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        ButterKnife.bind(this);
        try {
            checkForAnAppUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//            animateToHomeScreen();
//            startProgressAnimation();
//        } else {
            if (prefUtils.getBooleanValue(PrefKeys.IS_LOGGED_IN, false)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }else {
                Intent i = new Intent(getApplicationContext(), GetStartedActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }

        //}
    }

    public void checkForAnAppUpdate() {
        appUpdateManager = AppUpdateManagerFactory.create(SplashActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateManager.registerListener(listener);
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE, SplashActivity.this, RC_APP_UPDATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        listener = installState -> {
            try {
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.splashAnimationLayout),
                        getString(R.string.updateAvailable),
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getString(R.string.restartCaps), view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


    private void startProgressAnimation() {
        this.splashRouteAnimation = new SplashAnimationHelper().createSplashAnimation(this);
        this.splashRouteAnimation.startAnimation(this.splashAnimationLayout);
    }


    private void animateToHomeScreen() {
        AnimatorSet localAnimatorSet1 = new AnimatorSet();
        AnimatorSet localAnimatorSet2 = new AnimatorSet();
        AnimatorSet localAnimatorSet3 = new AnimatorSet();
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        localArrayList1.add(ObjectAnimator.ofFloat(this.splashAnimationLayout, "alpha", new float[]{1.0F, 0.0F}));
        localAnimatorSet2.setDuration(1000);
        localAnimatorSet2.playTogether(localArrayList1);
        localAnimatorSet3.playSequentially(localArrayList2);
        localAnimatorSet3.setDuration(500L);
        localAnimatorSet3.setStartDelay(50L);
        localAnimatorSet1.playSequentially(localAnimatorSet2, localAnimatorSet3);
        localAnimatorSet1.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator paramAnonymousAnimator) {
            }

            public void onAnimationEnd(Animator paramAnonymousAnimator) {
                if (SplashActivity.this.splashRouteAnimation != null) {
                    if (prefUtils.getBooleanValue(PrefKeys.IS_LOGGED_IN, false)) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                    }else {
                        startActivity(new Intent(getApplicationContext(), GetStartedActivity.class));
                    }

                }
            }

            public void onAnimationRepeat(Animator paramAnonymousAnimator) {
            }

            public void onAnimationStart(Animator paramAnonymousAnimator) {
            }
        });
        localAnimatorSet1.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
            }
        }
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
        // new PreferenceHelper(this).getLanguage();
        //sharedPreferences=getSharedPreferences("lang",MODE_PRIVATE);
       // sharedPreferences.edit().putString("language",lang).apply();
        // Intent refresh = new Intent(this, GetStartedActivity.class);
        //startActivity(refresh);
        //   this.overridePendingTransition(0, 0);
    }
}
