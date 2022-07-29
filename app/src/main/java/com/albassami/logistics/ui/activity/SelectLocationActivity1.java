package com.albassami.logistics.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.dto.response.branchesdata;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.ui.Fragment.BaseMapFragment;
import com.albassami.logistics.ui.Fragment.DetailFormFragment;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SelectLocationActivity1 extends AppCompatActivity implements LocationHelper.OnLocationReceived, OnMapReadyCallback {
    private LocationHelper locHelper;
    private boolean s_click = false, d_click = false, stop_click = false;
    private double locationLong,locationLat;
    SupportMapFragment search_place_map;
    private GoogleMap googleMap;
    private Marker dropMarker;
    public static LatLng des_latLng, sourceLatLng, stop_latLng;
    private Button applynewlocation;
    private Button applycurrentlocation;
    CardView mylocation;
    ImageView searchbtn;
    ImageView Backbutton;
 AutoCompleteTextView search;
ImageView status;
LinearLayout next;
LinearLayout btndone;
Button nextbtn;
ImageView locationpicker;
TextView fromlocation;
RelativeLayout fromlocationtransastor;
TextView status1;
LinearLayout statuslayout;
    ArrayList<String> branchesname;
ArrayList<branchesdata> branches;
ArrayAdapter<branchesdata> branchesadpt;
LatLng location;
    Handler handler;
    final int LOCATION_SETTINGS_REQUEST=102;
   // MarkerOptions markerOpt;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.dialogue_get_location2);
        Places.initialize(getApplicationContext(),getString(R.string.map_key));
locationpicker=findViewById(R.id.imageView3);
        branches=new ArrayList<>();
        branchesname=new ArrayList<>();
search=findViewById(R.id.searchdropdown);
mylocation=findViewById(R.id.mylocation);
nextbtn=findViewById(R.id.next);
        Backbutton=findViewById(R.id.Backbutton);
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            Backbutton.setRotation(180); }
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        next=findViewById(R.id.btnDone1);
        btndone=findViewById(R.id.btnDone);
        fromlocation=findViewById(R.id.fromlocation);
status=findViewById(R.id.status);
        fromlocationtransastor=findViewById(R.id.fromlocationtransastor);
        status1=findViewById(R.id.status1);
        statuslayout=findViewById(R.id.statuslayout);
        fromlocation.setText(getIntent().getStringExtra("from"));

        applynewlocation=findViewById(R.id.applaynewlocation);
        applycurrentlocation=findViewById(R.id.applaycurrentlocation);

        //markerOpt = new MarkerOptions();
        //      markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick_marker));
        init();
        locHelper = new LocationHelper(this);
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {

        }
        locHelper.setLocationReceivedLister(this);
        search_place_map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.search_place_map);
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search.getText().toString().equals("")||search.getText()==null||search.getText().toString().equals("null")){
                        search.setError(getString(R.string.pick_location));
                        search.requestFocus();
                    }
                    else{
                    Intent returnIntent = new Intent(SelectLocationActivity1.this,select_from_to.class);
                    returnIntent.putExtra("to",search.getText().toString());
                    returnIntent.putExtra("from",getIntent().getStringExtra("from"));
                        Bundle args = new Bundle();
                        Bundle bundle = getIntent().getParcelableExtra("loc_from");
                        LatLng fromPosition = bundle.getParcelable("from_position");
                        args.putParcelable("from_position", fromPosition);
                        args.putParcelable("to_position", location);
                        returnIntent.putExtra("from_to",args);
                        returnIntent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
                    startActivity(returnIntent);
                }}
            });

        getlcoationservice();
    }
    private void getlcoationservice() {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);
        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(settingsBuilder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response =
                            task.getResult(ApiException.class);
                } catch (ApiException ex) {
                    switch (ex.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException =
                                        (ResolvableApiException) ex;
                                resolvableApiException
                                        .startResolutionForResult(SelectLocationActivity1.this,
                                                LOCATION_SETTINGS_REQUEST);
                            } catch (IntentSender.SendIntentException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;
                    }
                }
            }
        });
    }
    @Override
    public void onLocationReceived(LatLng latlong) {
    }

    @Override
    public void onLocationReceived(Location location) {
        mylocation.setOnClickListener(i->{
            LatLng mylocation=new LatLng(location.getLatitude(),location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation,
                    16));
            if (null != googleMap) {
            //    markerOpt.position(mylocation);
               // googleMap.clear();
               //  googleMap.addMarker(markerOpt).setTitle(getString(R.string.your_location));;

            }
            locationLong = mylocation.longitude;
            locationLat = mylocation.latitude;

        });
        applycurrentlocation.setOnClickListener(i->{
            LatLng mylocation=new LatLng(location.getLatitude(),location.getLongitude());
            String loc=getAddress(mylocation.latitude,mylocation.longitude);
            Intent returnIntent = new Intent();
            returnIntent.setData(Uri.parse(loc));
            setResult(Activity.RESULT_OK,returnIntent);
            SelectLocationActivity1.this.finish();
        });

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
         //    markerOpt = new MarkerOptions();
        //    markerOpt.position(currentlatLang);
        //    markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick_marker));
          //  googleMap.clear();
          //   googleMap.addMarker(markerOpt).setTitle(getString(R.string.your_location));
            locationLong = currentlatLang.longitude;
            locationLat = currentlatLang.latitude;



        }
    }
    @Override
    public void onMapReady(GoogleMap gMap) {
        try {
            googleMap = gMap;
            AndyUtils.removeProgressDialog();
            if (googleMap != null) {

                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.getUiSettings().setRotateGesturesEnabled(true);
                googleMap.getUiSettings().setScrollGesturesEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                locations();
                search.setOnItemClickListener(new SelectLocationActivity1.MyClickListener1(search));
         //       markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick_marker)).draggable(true);
              // googleMap.addMarker(markerOpt);
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        LatLng latLng = googleMap.getCameraPosition().target;
                        String s = getAddress(latLng.latitude, latLng.longitude);
                        if (s != null && !s.equals("")) {

                        }


                    }
                });

                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        LatLng latLng = marker.getPosition();
                        Geocoder geocoder = new Geocoder(SelectLocationActivity1.this, Locale.getDefault());
                        try {
                            Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(BaseMapFragment.pic_latlan)
                        .zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
              /*  if (null != googleMap) {
                    MarkerOptions markerOpt = new MarkerOptions();
                    markerOpt.position(BaseMapFragment.pic_latlan);
                    markerOpt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pickup_location));
                    pickUpMarker = googleMap.addMarker(markerOpt);

                }*/
                           }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMap = null;
    }
    void init(){
        search.setFocusable(false);


//  Toast.makeText(this, "init has intiaitzied", Toast.LENGTH_SHORT).show();
/*search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if(actionId== EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_SEARCH ||
        event.getAction()== KeyEvent.ACTION_DOWN ||
                event.getAction() == KeyEvent.KEYCODE_SEARCH ||
                event.getAction() == KeyEvent.KEYCODE_ENTER)
        {

           // geolocate();

        }
        return false;
    }
});*/
search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        search.showDropDown();
    }
});


    }

    private void locations() {

        UiUtils.showLoadingDialog(this);
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiServices2=retrofit.create(APIInterface.class);

        Call<String> call = apiServices2.branches();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONArray orderdata = null;
                try {
                    orderdata = new JSONArray(response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(orderdata!=null) {
                    JSONObject data = null;
                    SharedPreferences sharedPreferences = getSharedPreferences("lang", MODE_PRIVATE);
                    if (sharedPreferences.getString("language", "").equals("ar")) {
                        for (int i = 0; i < orderdata.length(); i++) {
                            try {
                                data = orderdata.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (data != null) {
                                branchesdata branchesdata = new branchesdata(data.optString("ar_name"), new LatLng(Double.parseDouble(data.optString("latitude")),Double.parseDouble(data.optString("longitude"))));
                                branches.add(branchesdata);
                                branchesname.add(branchesdata.getName());

                            }
                        }

                    }else{
                        for (int i = 0; i < orderdata.length(); i++) {
                            try {
                                data = orderdata.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (data != null) {
                                branchesdata branchesdata = new branchesdata(data.optString("en_name"), new LatLng(Double.parseDouble(data.optString("latitude")),Double.parseDouble(data.optString("longitude"))));
                                branches.add(branchesdata);
                                branchesname.add(branchesdata.getName());

                            }
                        }
                    }
                    branchesadpt=new ArrayAdapter(SelectLocationActivity1.this, android.R.layout.simple_dropdown_item_1line,branchesname);
                    search.setAdapter(branchesadpt);

                }
            }



            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });

    }

    private void geolocate(LatLng latLng) {
Geocoder geocoder=new Geocoder(this);
        List<Address> addresses=new ArrayList<>();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                    20));
            if (googleMap!=null ) {
             /*   MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.position(latLng);
                markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick_marker));
                googleMap.clear();
                 googleMap.addMarker(markerOpt).setTitle(search.getText().toString());*/


            }
            else{
                Toast.makeText(this, "GoogLe map = null", Toast.LENGTH_SHORT).show();
            }
            locationLong = latLng.longitude;
            locationLat = latLng.latitude;

        }



    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        Locale loc;
        SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            loc = new Locale("ar");}
        else{
            loc = new Locale("en");
        }
        List<Address> addresses = null;
        geocoder = new Geocoder(this, loc);
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

    if(addresses!=null &&addresses.size()>0) {
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        return address;
    }
    else {return "null";}
    }
    public class MyClickListener1 implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener1(AutoCompleteTextView myAc) {
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (ac.getId()) {
             case R.id.searchdropdown:
                for(int j=0; j<branchesname.size();j++){
                    if(search.getAdapter().getItem(i).equals(branchesname.get(j))){
                        Marker marker=null;
                        if(marker==null) {
                  marker=googleMap.addMarker(new MarkerOptions().position(branches.get(j).getLocation()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.drop_location)));
                        }
                        else{
                            marker.setPosition(branches.get(j).getLocation());
                        }
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(branches.get(j).getLocation(),12));
                         location=branches.get(j).getLocation();
                    }

                }
                break;

            }

        }
    }
}
