package com.albassami.logistics.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.TowingResponse;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.ui.Adapter.TowingTypeAdapter;
import com.albassami.logistics.ui.Fragment.BaseMapFragment;
import com.albassami.logistics.ui.Fragment.CarTabFragment;
import com.albassami.logistics.ui.Fragment.DoorToDoorFragment;
import com.albassami.logistics.ui.Fragment.FragmentOrderSummary;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideLaterActivity extends AppCompatActivity implements LocationHelper.OnLocationReceived, OnMapReadyCallback,TowingTypeAdapter.OnItemClicked {
    private Button laterBtn, driverBtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Bundle bundle;
    private LocationHelper locHelper;
    private boolean s_click = false, d_click = false, stop_click = false;
    SupportMapFragment search_place_map;
    private GoogleMap googleMap;
    LinearLayoutManager linearLayoutManager;
    TowingTypeAdapter towingTypeAdapter;
    String carSize,towing_type = "",priceTowing="", phoneNumber, idNumber, carSizeID, price, receivrName, receiverNumber, carModelName, piateNumber, carModel, serviceType, branchName, ownerName, branchId, casSizeID, carModelID, branchNameTo, branchIdTo;
    PrefUtils prefUtils;
    private Calendar myCalendar;
    String currentdate;
    APIInterface apiInterface;
    private RecyclerView rvTowings;
    private Marker PickUpMarker, DropMarker, StopMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_later);

        locHelper = new LocationHelper(RideLaterActivity.this);
        locHelper.setLocationReceivedLister(RideLaterActivity.this);

        rvTowings = findViewById(R.id.rv_towing);
        prefUtils = PrefUtils.getInstance(RideLaterActivity.this);
        try {
            MapsInitializer.initialize(RideLaterActivity.this);
        } catch (Exception e) {

        }
        search_place_map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_place_map);
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }


        myCalendar = Calendar.getInstance();
        laterBtn = findViewById(R.id.btnLater);
        driverBtn = findViewById(R.id.btnNoDriver);
        prefUtils = PrefUtils.getInstance(RideLaterActivity.this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        bundle = getIntent().getExtras();
        serviceType = getIntent().getExtras().getString(Const.PassParam.SERVICE_TYPE);
        branchName = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME);
        ownerName = getIntent().getExtras().getString(Const.PassParam.OWNER_NAME);
        idNumber = getIntent().getExtras().getString(Const.PassParam.ID_NUMBER);
        towing_type = getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE);
        phoneNumber = getIntent().getExtras().getString(Const.PassParam.PHONE_NUMBER);
        piateNumber = getIntent().getExtras().getString(Const.PassParam.PIATE_NUMBER);
        carSize = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE);
        carModel = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        branchId = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID);
        casSizeID = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE_ID);
        carModelID = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_ID);
        branchIdTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID_TO);
        price = getIntent().getExtras().getString(Const.PassParam.PRICE);
        branchNameTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME_TO);
        receivrName = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NAME);
        receiverNumber = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NUMBER);
        carModelName = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_NAME);
        priceTowing = getIntent().getExtras().getString(Const.PassParam.TOWING_PRICE);
        getTowingTypes();
        driverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString(Const.PassParam.BRANCH_NAME, branchName);
                    bundle.putString(Const.PassParam.BRANCH_ID, branchId);
                    bundle.putString(Const.PassParam.BRANCH_NAME_TO, branchNameTo);
                    bundle.putString(Const.PassParam.BRANCH_ID_TO, branchIdTo);
                    bundle.putString(Const.PassParam.SERVICE_TYPE, Const.DOOR_TO_DOOR);
                    bundle.putString(Const.PassParam.AGGREMENT_ID, getIntent().getExtras().getString(Const.PassParam.AGGREMENT_ID));
                    bundle.putString(Const.PassParam.AGGREMENT_NAME, getIntent().getExtras().getString(Const.PassParam.AGGREMENT_NAME));
                    bundle.putString(Const.PassParam.SOURCE_ADDRESS, getIntent().getExtras().getString(Const.PassParam.SOURCE_ADDRESS));
                    bundle.putString(Const.PassParam.DEST_ADDRESS, getIntent().getExtras().getString(Const.PassParam.DEST_ADDRESS));
                    Intent myIntent = new Intent(RideLaterActivity.this, OrderSummaryActivity.class);
//                myIntent.putExtra("key", 2); //Optional parameters
                    myIntent.putExtras(bundle);
                    RideLaterActivity.this.startActivity(myIntent);


//                createANowRequest();
            }
        });

        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!towing_type.equalsIgnoreCase("")){
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(RideLaterActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    String myFormat = "dd MMM yyyy"; //In which you need put here
                                    SimpleDateFormat sdformat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                                    myCalendar.set(Calendar.YEAR, year);
                                    myCalendar.set(Calendar.MONTH, monthOfYear + 1);
                                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    currentdate = sdformat.format(myCalendar.getTime());
                                    openTimePicker();
//                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }else {
                    Toast.makeText(RideLaterActivity.this, "Please select towing type", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void openTimePicker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(RideLaterActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mHour = selectedHour;
                mMinute = selectedMinute;
                Intent intent = new Intent(RideLaterActivity.this, ScheduleActivity.class);
                intent.putExtra(Const.PassParam.SCHEDULE_DATE, currentdate);
                intent.putExtra(Const.PassParam.SCHEDULE_TIME, getTime(mHour, mMinute));
                intent.putExtra(Const.PassParam.TOWING_TYPE, towing_type);
                intent.putExtra(Const.PassParam.SOURCE_ADDRESS, getIntent().getExtras().getString(Const.PassParam.SOURCE_ADDRESS));
                intent.putExtra(Const.PassParam.DEST_ADDRESS, getIntent().getExtras().getString(Const.PassParam.DEST_ADDRESS));
                intent.putExtra(Const.PassParam.PRICE, price);
                startActivity(intent);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    protected void createANowRequest() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        UiUtils.showLoadingDialog(RideLaterActivity.this);
        try {
            Call<String> call = apiInterface.createNowRequest(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                    , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                    , ""
                    , prefUtils.getStringValue(PrefKeys.PRICE, "")
                    , Const.source_address
                    , Const.dest_address
                    , Const.stop_address != null ? Const.stop_address : ""
                    , Const.pic_latlan != null ? Const.pic_latlan.latitude : 0
                    , Const.pic_latlan != null ? Const.pic_latlan.longitude : 0
                    , Const.drop_latlan != null ? Const.drop_latlan.latitude : 0
                    , Const.drop_latlan != null ? Const.drop_latlan.longitude : 0
                    , Const.stop_latlan != null ? Const.stop_latlan.latitude : 0
                    , Const.stop_latlan != null ? Const.stop_latlan.longitude : 0
                    , Const.SERVICE_TYPE
                    , getIntent().getExtras().getString(Const.PassParam.AGGREMENT_ID)
                    , "towing"
                    , branchIdTo
                    , branchId
                    , 1
                    , ""
                    , prefUtils.getStringValue(PrefKeys.PAYMENT_MODE, "")
            );
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    JSONObject createRequestResponse = null;
                    UiUtils.hideLoadingDialog();
                    try {
                        createRequestResponse = new JSONObject(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (createRequestResponse != null) {
                        if (createRequestResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                            try {
                                Toast.makeText(RideLaterActivity.this, createRequestResponse.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(RideLaterActivity.this, ActivityWaitingRequestAccept.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(Const.PassParam.BRANCH_NAME, branchName);
                            bundle.putString(Const.PassParam.BRANCH_ID, branchId);
                            bundle.putString(Const.PassParam.BRANCH_NAME_TO, branchNameTo);
                            bundle.putString(Const.PassParam.BRANCH_ID_TO, branchIdTo);
                            bundle.putString(Const.PassParam.SERVICE_TYPE, serviceType);
                            bundle.putString(Const.PassParam.CAR_MODEL, carModel);
                            bundle.putString(Const.PassParam.CAR_SIZE, carSize);
                            bundle.putString(Const.PassParam.CAR_MODEL_ID, carModelID);
                            bundle.putString(Const.PassParam.CAR_SIZE_ID, carSizeID);
                            bundle.putString(Const.PassParam.ID_NUMBER, idNumber);
                            bundle.putString(Const.PassParam.PHONE_NUMBER, phoneNumber);
                            bundle.putString(Const.PassParam.OWNER_NAME, ownerName);
                            bundle.putString(Const.PassParam.PIATE_NUMBER, piateNumber);
                            bundle.putString(Const.PassParam.PRICE, price);
                            bundle.putString(Const.PassParam.CAR_MODEL_NAME, carModelName);
                            bundle.putString(Const.PassParam.RECEIVER_NAME, receivrName);
                            bundle.putString(Const.PassParam.RECEIVER_NUMBER, receiverNumber);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            try {
                                if (createRequestResponse.getString(Const.Params.ERROR_MSG) != null) {
                                    Toast.makeText(RideLaterActivity.this, createRequestResponse.getString(Const.Params.ERROR_MSG), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            enableRequestBtn();
//                            UiUtils.showShortToast(getActivity(), createRequestResponse.optString(APIConsts.Params.ERROR));
//                            if (req_load_dialog != null && req_load_dialog.isShowing()) {
//                                req_load_dialog.dismiss();
//                                stopCheckingforstatus();
//                            }
                            if (createRequestResponse.optInt(APIConsts.Params.ERROR_CODE) == APIConsts.ErrorCodes.WALLETEMPTY) {
                                Intent walletIntent = new Intent(RideLaterActivity.this, WalletAcivity.class);
                                startActivity(walletIntent);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    UiUtils.hideLoadingDialog();
                    if (NetworkUtils.isNetworkConnected(RideLaterActivity.this)) {
                        UiUtils.showShortToast(RideLaterActivity.this, getString(R.string.may_be_your_is_lost));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }
    private void getTowingTypes(){
        apiInterface = CustomRestClient.getApiService();
        UiUtils.showLoadingDialog(RideLaterActivity.this);
        Call<TowingResponse> towingResponseCall = apiInterface.getTowing();
        towingResponseCall.enqueue(new Callback<TowingResponse>() {
            @Override
            public void onResponse(Call<TowingResponse> call, Response<TowingResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() &&  response.body() !=null && response.body().getData()!= null){
                    linearLayoutManager = new LinearLayoutManager(RideLaterActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    rvTowings.setLayoutManager(linearLayoutManager);
                    towingTypeAdapter = new TowingTypeAdapter(RideLaterActivity.this,response.body().getData(),RideLaterActivity.this::onClicked,towing_type);
                    rvTowings.setAdapter(towingTypeAdapter);
                }
            }

            @Override
            public void onFailure(Call<TowingResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
            }
        });
    }
//    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, boolean isAnimate) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        if (isAnimate) {
//            ft.setCustomAnimations(R.anim.slide_in_right,
//                    R.anim.slide_out_left, R.anim.slide_in_left,
//                    R.anim.slide_out_right);
//        }
//        if (addToBackStack) {
//            ft.addToBackStack(tag);
//        }
//        ft.replace(R.id.content_fragment, fragment, tag);
//        ft.commit();
//    }

    @Override
    public void onClicked(String id, String name,String price) {
        towing_type = name;
        priceTowing = price;
    }
}