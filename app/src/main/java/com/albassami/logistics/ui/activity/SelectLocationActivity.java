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
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.albassami.logistics.NewUtilsAndPref.MarkerUtils.SmoothMoveMarker;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.ui.Fragment.BaseMapFragment;
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
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.albassami.logistics.NewUtilsAndPref.AppLogger.init;

public class SelectLocationActivity extends AppCompatActivity implements LocationHelper.OnLocationReceived, OnMapReadyCallback {
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
 EditText search;
ImageView status;
LinearLayout next;
LinearLayout btndone;
Button nextbtn;
ImageView locationpicker;
TextView fromlocation;
RelativeLayout fromlocationtransastor;
TextView status1;
LinearLayout statuslayout;
final int LOCATION_SETTINGS_REQUEST=101;

    Handler handler;
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
        setContentView(R.layout.dialogue_get_location);
        Places.initialize(getApplicationContext(),getString(R.string.map_key));
locationpicker=findViewById(R.id.imageView3);
search=findViewById(R.id.search);
searchbtn=findViewById(R.id.search1);
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
if(getIntent().getStringExtra("type").equals("1")){
    locationpicker.setImageResource(R.mipmap.pickup_location);
    status.setVisibility(View.VISIBLE);
    status.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
    fromlocation.setVisibility(View.GONE);
    status1.setVisibility(View.VISIBLE);

    statuslayout.setVisibility(View.VISIBLE);
    fromlocationtransastor.setVisibility(View.GONE);
next.setVisibility(View.VISIBLE);
    status1.setText(getString(R.string.Determinethestartingpoint));
    nextbtn.setText(getString(R.string.Determinethestartingpoint));
    btndone.setVisibility(View.GONE);
}
else if(getIntent().getStringExtra("type").equals("2")){
    locationpicker.setImageResource(R.mipmap.drop_location);
    status.setVisibility(View.VISIBLE);
    status.setImageResource(R.drawable.ic_radio_button_checked_black_24dp1);
    fromlocation.setVisibility(View.VISIBLE);
    status1.setVisibility(View.VISIBLE);
    statuslayout.setVisibility(View.VISIBLE);
    fromlocationtransastor.setVisibility(View.VISIBLE);
    status1.setText(getString(R.string.Selecttheaccesspoint));
    fromlocation.setText(getIntent().getStringExtra("from"));
    next.setVisibility(View.VISIBLE);
    btndone.setVisibility(View.GONE);
    nextbtn.setText(getString(R.string.Selecttheaccesspoint));
}
      else if(getIntent().getStringExtra("type").equals("3")){
            locationpicker.setImageResource(R.mipmap.pickup_location);
            status.setVisibility(View.VISIBLE);
            status.setImageResource(R.drawable.ic_radio_button_checked_black_24dp);
            fromlocation.setVisibility(View.GONE);
            status1.setVisibility(View.VISIBLE);

            statuslayout.setVisibility(View.VISIBLE);
            fromlocationtransastor.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
            status1.setText(getString(R.string.Determinethestartingpoint));
            nextbtn.setText(getString(R.string.Determinethestartingpoint));
            btndone.setVisibility(View.GONE);
        }
else{
    fromlocation.setVisibility(View.GONE);
    fromlocationtransastor.setVisibility(View.GONE);
    status1.setVisibility(View.GONE);
    status.setVisibility(View.GONE);
    next.setVisibility(View.GONE);
    btndone.setVisibility(View.VISIBLE);
    statuslayout.setVisibility(View.GONE);

}
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


        if(getIntent().getStringExtra("type").equals("1")){

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search.getText().toString().equals("")||search.getText()==null||search.getText().toString().equals("null")){
                        search.setError(getString(R.string.pick_location));
                        search.requestFocus();
                    }
                    else{
                    Intent returnIntent = new Intent(SelectLocationActivity.this,SelectLocationActivity.class);
                    returnIntent.putExtra("from",search.getText().toString());
                        Bundle args = new Bundle();
                        args.putParcelable("from_position", googleMap.getCameraPosition().target);
                        returnIntent.putExtra("loc_from",args);
                    returnIntent.putExtra("type","2");
                    returnIntent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
                    startActivity(returnIntent);
                }}
            });

        }
       else if(getIntent().getStringExtra("type").equals("2")){

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search.getText().toString().equals("")||search.getText()==null||search.getText().toString().equals("null")){
                        search.setError(getString(R.string.pick_location));
                        search.requestFocus();
                    }
                    else{
                    Intent returnIntent = new Intent(SelectLocationActivity.this,select_from_to.class);
                    returnIntent.putExtra("to",search.getText().toString());
                    returnIntent.putExtra("from",getIntent().getStringExtra("from"));
                        Bundle args = new Bundle();
                        Bundle bundle = getIntent().getParcelableExtra("loc_from");
                        LatLng fromPosition = bundle.getParcelable("from_position");
                        args.putParcelable("from_position", fromPosition);
                        args.putParcelable("to_position", googleMap.getCameraPosition().target);
                        returnIntent.putExtra("from_to",args);
                        returnIntent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
                    startActivity(returnIntent);
                }}
            });

        }
        if(getIntent().getStringExtra("type").equals("3")){

            nextbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search.getText().toString().equals("")||search.getText()==null||search.getText().toString().equals("null")){
                        search.setError(getString(R.string.pick_location));
                        search.requestFocus();
                    }
                    else{
                        Intent returnIntent = new Intent(SelectLocationActivity.this,SelectLocationActivity1.class);
                        returnIntent.putExtra("from",search.getText().toString());
                        Bundle args = new Bundle();
                        args.putParcelable("from_position", googleMap.getCameraPosition().target);
                        returnIntent.putExtra("loc_from",args);
                        returnIntent.putExtra("type","2");
                        returnIntent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
                        startActivity(returnIntent);
                    }}
            });

        }

        else {
            applynewlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(search.getText().toString().equals("")||search.getText()==null||search.getText().toString().equals("null")){
                        search.setError(getString(R.string.pick_location));
                        search.requestFocus();
                    }
                    else{
                    Intent returnIntent = new Intent();
                    returnIntent.setData(Uri.parse(search.getText().toString()));
                        returnIntent.putExtra("latlang",googleMap.getCameraPosition().target.toString());
                        returnIntent.putExtra("lat",googleMap.getCameraPosition().target.latitude);
                        returnIntent.putExtra("lang",googleMap.getCameraPosition().target.longitude);

                        setResult(Activity.RESULT_OK, returnIntent);
                    SelectLocationActivity.this.finish();
                }}
            });
            applycurrentlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

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
                                        .startResolutionForResult(SelectLocationActivity.this,
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
            search.setText(getAddress(location.getLatitude(),location.getLongitude()));
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
            returnIntent.putExtra("latlang",mylocation.toString());
            returnIntent.putExtra("lat",mylocation.latitude);
            returnIntent.putExtra("lang",mylocation.longitude);

            setResult(Activity.RESULT_OK,returnIntent);
            SelectLocationActivity.this.finish();
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
            search.setText(getAddress(locationLat,locationLong));


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
         //       markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pick_marker)).draggable(true);
              // googleMap.addMarker(markerOpt);
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        LatLng latLng = googleMap.getCameraPosition().target;
                        String s = getAddress(latLng.latitude, latLng.longitude);
                        if (s != null && !s.equals("")) {
                            search.setText(s);
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
                        Geocoder geocoder = new Geocoder(SelectLocationActivity.this, Locale.getDefault());
                        try {
                            android.location.Address address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
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
        List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);
        Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN,fieldList).setTypeFilter(TypeFilter.ESTABLISHMENT).build(SelectLocationActivity.this);
        startActivityForResult(intent,100);
    }
});


    }

    private void geolocate(LatLng latLng) {
Geocoder geocoder=new Geocoder(this);
        List<Address> addresses=new ArrayList<>();

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                    16));
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 &&resultCode==RESULT_OK){
Place place=Autocomplete.getPlaceFromIntent(data);
search.setText(place.getAddress());
geolocate(place.getLatLng());

        }
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
}
