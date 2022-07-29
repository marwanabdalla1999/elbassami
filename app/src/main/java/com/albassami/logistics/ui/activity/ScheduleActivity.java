package com.albassami.logistics.ui.activity;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.ui.Fragment.BaseMapFragment;
import com.albassami.logistics.ui.Fragment.HomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity implements LocationHelper.OnLocationReceived, OnMapReadyCallback {

    private LocationHelper locHelper;
    private boolean s_click = false, d_click = false, stop_click = false;
    SupportMapFragment search_place_map;
    private Marker PickUpMarker, DropMarker, StopMarker;
    private PrefUtils prefUtils;
    TextView tvTime, tvTimeLight, tvPrice,tvSource,tvDestination,tvTypeText;
    private GoogleMap googleMap;
    ImageView ivIcon;
    APIInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(ScheduleActivity.this);
        locHelper = new LocationHelper(ScheduleActivity.this);
        locHelper.setLocationReceivedLister(ScheduleActivity.this);
        tvTime = findViewById(R.id.tvTime);
        tvTypeText = findViewById(R.id.tvTitle);
        ivIcon = findViewById(R.id.ivIcon);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvTimeLight = findViewById(R.id.tvTimeLight);
        tvPrice = findViewById(R.id.tvPrice);
        try {
            MapsInitializer.initialize(ScheduleActivity.this);
        } catch (Exception e) {

        }
        search_place_map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_place_map);
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }
        tvPrice.setText(getIntent().getExtras().getString(Const.PassParam.PRICE, ""));
        tvTime.setText(getIntent().getExtras().getString(Const.PassParam.SCHEDULE_DATE) + " " + getIntent().getExtras().getString(Const.PassParam.SCHEDULE_TIME));
        tvTimeLight.setText(getIntent().getExtras().getString(Const.PassParam.SCHEDULE_DATE) + " " + getIntent().getExtras().getString(Const.PassParam.SCHEDULE_TIME));
        tvSource.setText(getIntent().getExtras().getString(Const.PassParam.SOURCE_ADDRESS, ""));
        tvDestination.setText(getIntent().getExtras().getString(Const.PassParam.DEST_ADDRESS, ""));
        tvTypeText.setText(getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE));
        if (getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE).equalsIgnoreCase("Regular Towing")) {
            ivIcon.setBackgroundResource(R.drawable.icon_towing_regular);
        } else if (getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE).equalsIgnoreCase("Special Towing")) {
            ivIcon.setBackgroundResource(R.drawable.icon_towing_closed);
        } else if (getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE).equalsIgnoreCase("Hydrolic Towing")) {
            ivIcon.setBackgroundResource(R.drawable.icon_towing_hydraulic);
        }
    }


    @Override
    public void onLocationReceived(LatLng latlong) {
    }

    @Override
    public void onLocationReceived(Location location) {
    }

    @Override
    public void onConntected(Bundle bundle) {
    }

    @Override
    public void onConntected(Location location) {
        if (location != null && null != googleMap) {
            final LatLng currentlatLang = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatLang,
                    16));
            BaseMapFragment.pic_latlan = currentlatLang;
            MarkerOptions markerOpt = new MarkerOptions();
            markerOpt.position(currentlatLang);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_green_marker));
            Marker locMark = googleMap.addMarker(markerOpt);
        }
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        try {
            googleMap = gMap;
            AndyUtils.removeProgressDialog();
            if (googleMap != null) {
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(BaseMapFragment.pic_latlan)
                        .zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                if (null != googleMap) {
                    MarkerOptions markerOpt = new MarkerOptions();
                    markerOpt.position(BaseMapFragment.pic_latlan);
                    markerOpt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pickup_location));
                    PickUpMarker = googleMap.addMarker(markerOpt);

                }
                googleMap.setOnCameraIdleListener(() -> {
                    if (d_click) {
//                    des_latLng = googleMap.getCameraPosition().target;
//                    if (null != DropMarker) {
//                        SmoothMoveMarker.animateMarker(DropMarker, googleMap.getCameraPosition().target, false, googleMap);
//                    }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void scheduleAAirportRide(String service_type) {
        UiUtils.showLoadingDialog(ScheduleActivity.this);
        Call<String> call = apiInterface.scheduleaAirportRide(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                , service_type
                , Const.source_address
                , Const.source_address
                , Const.pic_latlan.latitude
                , Const.pic_latlan.longitude
                , Const.drop_latlan.latitude
                , Const.drop_latlan.longitude
                , 3
                , ""
                , prefUtils.getStringValue(PrefKeys.PAYMENT_MODE, "")
                , tvTime.getText().toString()
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject createRequestResponse = null;
                try {
                    createRequestResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (createRequestResponse != null) {
                    if (createRequestResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                        builder.setMessage(getResources().getString(R.string.txt_trip_schedule_success))
                                .setCancelable(false)
                                .setPositiveButton(getResources().getString(R.string.txt_ok), (dialog, id) -> {
                                    dialog.dismiss();
                                    //activity.addFragment(new HomeFragment(), false, Const.HOME_MAP_FRAGMENT, true);

                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        UiUtils.showShortToast(ScheduleActivity.this, createRequestResponse.optString(APIConsts.Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(ScheduleActivity.this)) {
                    UiUtils.showShortToast(ScheduleActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
