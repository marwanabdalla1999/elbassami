package com.albassami.logistics.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.albassami.logistics.listener.OnCarClicked;
import com.albassami.logistics.listener.OnDoorToDoorClicked;
import com.albassami.logistics.ui.Fragment.HomeStartFragment;
import com.albassami.logistics.ui.Fragment.SearchPlaceFragment;
import com.braintreepayments.cardform.OnCardFormFieldFocusedListener;
import com.bumptech.glide.Glide;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.Utils.PreferenceHelper;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.ParserUtils;
import com.albassami.logistics.network.ApiManager.events.APIEvent;
import com.albassami.logistics.network.Models.RequestDetail;
import com.albassami.logistics.ui.Fragment.HomeFragment;
import com.albassami.logistics.ui.Fragment.IncompletePaymentDialog;
import com.albassami.logistics.ui.Fragment.OngoingTripFragment;
import com.albassami.logistics.ui.Fragment.RatingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.skyfishjy.library.RippleBackground;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.albassami.logistics.ui.Fragment.NavigationDrawableFragment.ivUserIcon;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, BottomNavigationView.OnNavigationItemReselectedListener, BottomNavigationView.OnNavigationItemSelectedListener {




    @BindView(R.id.navigationBottom)
    public BottomNavigationView bottomNavigationView;
    private ActionBarDrawerToggle drawerToggle;
    public String currentFragment = "";
    PrefUtils prefUtils;
    APIInterface apiInterface;
    OnCarClicked onCarClicked;
    OnDoorToDoorClicked onDoorToDoorClicked;
    Dialog requestDialog;
    private int RC_LOCATION_PERM = 124;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setUpLocale();
        setContentView(R.layout.activity_main);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ButterKnife.bind(this);
        bottomNavigationView.setOnNavigationItemReselectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        findIfPhonePresent();
       id= prefUtils.getIntValue(PrefKeys.USER_ID, 0);
       // Toast.makeText(this,id, Toast.LENGTH_SHORT).show();
       checkForPendingPayment();
        checkPermissionAndRequestStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerEventBus();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof OnCarClicked) {
            onCarClicked = (OnCarClicked) fragment;
        }
        if (fragment instanceof OnDoorToDoorClicked) {
            onDoorToDoorClicked = (OnDoorToDoorClicked) fragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenExpiry(APIEvent event) {
        unregisterEventBus();
        logOutUserFromDevice();
    }

    private void logOutUserFromDevice() {
        PrefHelper.setUserLoggedOut(MainActivity.this);
        Intent restartActivity = new Intent(MainActivity.this, SplashActivity.class);
        restartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(restartActivity);
        MainActivity.this.finish();
    }



    public void setUpLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            Locale myLocale = new Locale("ar");
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
            // new PreferenceHelper(this).getLanguage();
            sharedPreferences=getSharedPreferences("lang",MODE_PRIVATE);
            sharedPreferences.edit().putString("language","ar").apply();
        }

    }

    private void checkPermissionAndRequestStatus() {
        EasyPermissions.requestPermissions(this, getString(R.string.youNeedToGrantPermission),
                RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void findIfPhonePresent() {
        if (!new PreferenceHelper(this).getLoginBy().equalsIgnoreCase(Const.MANUAL)) {
            if (prefUtils.getStringValue(PrefKeys.USER_MOBILE, "") == null) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
                builder.setMessage(getResources().getString(R.string.txt_update_number))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.txt_ok), (dialog, id) -> {
                            dialog.dismiss();
                            openProfileActivity();
                        });
                androidx.appcompat.app.AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private void openProfileActivity() {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        finish();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
//        if (currentFragment.equals(Const.REQUEST_FRAGMENT) || currentFragment.equals(Const.SEARCH_FRAGMENT) || currentFragment.equals(Const.HOURLY_FRAGMENT) || currentFragment.equals(Const.AIRPORT_FRAGMENT)) {
//            addFragment(new HomeFragment(), false, Const.HOME_MAP_FRAGMENT, true);
//        } else {
        FragmentManager fm = getSupportFragmentManager();
        if (onBackPressed(fm)) {
            return;
        }else {
            if (!isFinishing()) {
                openExitDialog();
            }
        }

        //}
    }
    private boolean onBackPressed(FragmentManager fm) {
        if (fm != null) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                return true;
            }

            List<Fragment> fragList = fm.getFragments();
            if (fragList != null && fragList.size() > 0) {
                for (Fragment frag : fragList) {
                    if (frag == null) {
                        continue;
                    }
                    if (frag.isVisible()) {
                        if (onBackPressed(frag.getChildFragmentManager())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void openExitDialog() {
        final Dialog exit_dialog = new Dialog(this, R.style.DialogSlideAnim_leftright);
        exit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exit_dialog.setCancelable(true);
        exit_dialog.setContentView(R.layout.exit_layout);
        CustomRegularTextView tvExitOk = exit_dialog.findViewById(R.id.tvExitOk);
        CustomRegularTextView tvExitCancel = exit_dialog.findViewById(R.id.tvExitCancel);
        tvExitOk.setOnClickListener(view -> {
            exit_dialog.dismiss();
            finishAffinity();
        });
        tvExitCancel.setOnClickListener(view -> exit_dialog.dismiss());
        exit_dialog.show();
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, boolean isAnimate) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (isAnimate) {
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void requestStatusCheck() {
        Call<String> call = apiInterface.pingRequestStatusCheck(id,
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject requestStatusResponse = null;
                try {
                    requestStatusResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (requestStatusResponse != null) {
                    if (requestStatusResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        Bundle bundle = new Bundle();
                        RequestDetail requestDetail = ParserUtils.parseRequestStatus(response.body());
                        OngoingTripFragment travalfragment = new OngoingTripFragment();
                        if (requestDetail == null) {
                            return;
                        }

                        if (requestDetail.getTripStatus() == 0 && requestDetail.getDriverStatus() == 1) {
                            showRequestDialog();
                        } else {
                            switch (requestDetail.getTripStatus()) {
                                case Const.NO_REQUEST:
                                    new PreferenceHelper(MainActivity.this).clearRequestData();
//                                    if (!currentFragment.equals(Const.HOME_MAP_FRAGMENT)) {
//                                        addFragment(new SearchPlaceFragment(), false, Const.HOME_MAP_FRAGMENT, true);
//                                    }
                                    break;
                                case Const.IS_CREATED:
//                                    if (!currentFragment.equals(Const.HOME_MAP_FRAGMENT)) {
//                                        addFragment(new SearchPlaceFragment(), false, Const.HOME_MAP_FRAGMENT, true);
//                                    }
                                    break;
                                case Const.IS_ACCEPTED:
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_ACCEPTED);
                                    if (!currentFragment.equals(Const.TRAVEL_MAP_FRAGMENT)) {
                                        travalfragment.setArguments(bundle);
                                        addFragment(travalfragment, false, Const.TRAVEL_MAP_FRAGMENT, true);
                                    }
                                    break;
                                case Const.IS_DRIVER_DEPARTED:
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_DEPARTED);
                                    if (!currentFragment.equals(Const.TRAVEL_MAP_FRAGMENT)) {
                                        travalfragment.setArguments(bundle);
                                        addFragment(travalfragment, false, Const.TRAVEL_MAP_FRAGMENT, true);
                                    }
                                    break;
                                case Const.IS_DRIVER_ARRIVED:
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_ARRIVED);
                                    if (!currentFragment.equals(Const.TRAVEL_MAP_FRAGMENT)) {
                                        travalfragment.setArguments(bundle);
                                        addFragment(travalfragment, false, Const.TRAVEL_MAP_FRAGMENT, true);
                                    }
                                    break;
                                case Const.IS_DRIVER_TRIP_STARTED:
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_TRIP_STARTED);
                                    if (!currentFragment.equals(Const.TRAVEL_MAP_FRAGMENT)) {
                                        travalfragment.setArguments(bundle);
                                        addFragment(travalfragment, false, Const.TRAVEL_MAP_FRAGMENT, true);
                                    }
                                    break;
                                case Const.IS_DRIVER_TRIP_ENDED:
                                case Const.IS_DRIVER_RATED:
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_TRIP_ENDED);
                                    if (!currentFragment.equals(Const.RATING_FRAGMENT)) {
                                        RatingFragment feedbackFragment = new RatingFragment();
                                        feedbackFragment.setArguments(bundle);
                                        addFragment(feedbackFragment, false, Const.RATING_FRAGMENT, true);
                                    }
                            }
                        }
                    } else {
//                        UiUtils.showShortToast(getApplicationContext(), requestStatusResponse.optString(APIConsts.Params.ERROR));
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void showRequestDialog() {
        if (requestDialog != null)
            if (requestDialog.isShowing())
                return;
        requestDialog = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        requestDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent_black)));
        requestDialog.setCancelable(false);
        requestDialog.setContentView(R.layout.request_loading);
        final RippleBackground rippleBackground = requestDialog.findViewById(R.id.content);
        TextView cancel_req_create = requestDialog.findViewById(R.id.cancel_req_create);
        final TextView req_status = requestDialog.findViewById(R.id.req_status);
        final TextView requestType = requestDialog.findViewById(R.id.requestType);
        requestType.setVisibility(View.GONE);
        rippleBackground.startRippleAnimation();
        cancel_req_create.setOnClickListener(view -> {
            req_status.setText(getResources().getString(R.string.txt_canceling_req));
            cancelCreateRequest();
        });
        requestDialog.show();
    }

    protected void cancelCreateRequest() {
        Call<String> call = apiInterface.cancelRequest(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject cancelResponse = null;
                try {
                    cancelResponse = new JSONObject(response.body());
                    requestStatusCheck();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (requestDialog != null && requestDialog.isShowing()) {
                    requestDialog.dismiss();
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(MainActivity.this)) {
                    UiUtils.showShortToast(MainActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

   private void checkForPendingPayment() {
        UiUtils.showLoadingDialog(MainActivity.this);
       Call<String> call = apiInterface.checkingForPendingPayments(id,
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject paymentsResponse = null;
                try {
                    paymentsResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (paymentsResponse != null) {
                    if (paymentsResponse.optString(APIConsts.Constants.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        JSONObject data = paymentsResponse.optJSONObject(APIConsts.Params.DATA);
                        if (data.optInt(APIConsts.Params.IS_USER_NEED_PAY_NOW) == 1) {
                            IncompletePaymentDialog IncompletePaymentDialog = new IncompletePaymentDialog(data.optString(Const.Params.TOTAL_DUE_AMOUNT_FORMATTED));
                            IncompletePaymentDialog.show(getSupportFragmentManager(), IncompletePaymentDialog.getTag());
                        } else {
                            requestStatusCheck();
                        }
                    } else {
                        UiUtils.showShortToast(MainActivity.this, paymentsResponse.optString(APIConsts.Params.ERROR_MESSAGE));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(MainActivity.this)) {
                    UiUtils.showShortToast(MainActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        addFragment(new HomeStartFragment(), false, Const.HOME_START_FRAGMENT, true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.label_one:
                Toast.makeText(this,"label one",Toast.LENGTH_LONG).show();
                return true;
            case R.id.label_two:
                Toast.makeText(this,"label two",Toast.LENGTH_LONG).show();
                return true;
            case R.id.door_to_door:
                if (onDoorToDoorClicked != null){
                    onDoorToDoorClicked.onDoorClick();
                }
                return true;
            case R.id.towing:
                Toast.makeText(this,"towing",Toast.LENGTH_LONG).show();
                return true;
            case R.id.car:
                if (onCarClicked != null){
                    onCarClicked.onClickCar();
                }
                return true;
            default:
                return false;
        }
    }

}
