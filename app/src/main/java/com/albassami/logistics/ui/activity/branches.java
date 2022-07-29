package com.albassami.logistics.ui.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.R;
import com.albassami.logistics.dto.response.places;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.androidquery.util.AQUtility.getContext;

public class branches extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
ImageView btnBack;
    PlacesClient placesClient;
    TextView nameofplace,addressofplace,phoneofplace,website,openingtime;
    ArrayList<places> places;
    ImageView photo;
    LinearLayout detailsofplace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);
        Places.initialize(getApplicationContext(),getString(R.string.map_key));
        places=new ArrayList<>();
        placesClient = Places.createClient(this);
        btnBack=findViewById(R.id.imageView8);
        nameofplace=findViewById(R.id.nameofplace);
        addressofplace=findViewById(R.id.addressofplace);
        phoneofplace=findViewById(R.id.phoneofplace);
        website=findViewById(R.id.website);
        openingtime=findViewById(R.id.openingtime);
        photo=findViewById(R.id.closedialoge);
        detailsofplace=findViewById(R.id.detailsofplace);
        detailsofplace.setVisibility(View.GONE);
        phoneofplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel: "+phoneofplace.getText()));


                try {
                    startActivity(callIntent);
                }
                catch (SecurityException s){
                    Toast.makeText(branches.this,s.getMessage(), Toast.LENGTH_SHORT)
                            .show();

                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsofplace.setVisibility(View.GONE);
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180);}
        btnBack.setOnClickListener(i->onBackPressed());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

void getlocationdetails(String placeid){

    List<Place.Field> fieldList= Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.PHONE_NUMBER);
    final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeid, fieldList);
    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
        Place place = response.getPlace();
        places.add(new places(place.getLatLng(),placeid));
        addmarks(place.getLatLng().latitude,place.getLatLng().longitude,place);
    }).addOnFailureListener((exception) -> {
        if (exception instanceof ApiException) {
            final ApiException apiException = (ApiException) exception;

            final int statusCode = apiException.getStatusCode();
            // TODO: Handle error with given status code.
        }
    });

}
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(26.406784,43.878853);
        getlocationdetails("ChIJD1Z9e5tWfxURLsUV34g1eLk");
        getlocationdetails("ChIJTX6s_B_jBxYRjmsvCle45Ic");
        getlocationdetails("ChIJ13T6Nu9HdhURR9vtXWFitBE");
        getlocationdetails("ChIJrW4DYPowrBURk6NQ9VOmFiE");
        getlocationdetails("ChIJrxRdvCXDST4RuNzEdTyF54E");
      //  getlocationdetails(" ChIJwYRR2Zz_Lj4Rr7fUwzR7dMo");
        getlocationdetails("ChIJjYNUsacZ7xURx6masKjhIO8");
        getlocationdetails("ChIJJ096GMoDNj4RcPuEJU0Ghck");
        getlocationdetails(" ChIJFd8t5TbXwxURgTWTB2tp87c");
        getlocationdetails("ChIJPROUPfGh_BURxkekkqrEjGA");
        getlocationdetails("ChIJ-SbBjnsezD8RktHz9mkfENw");
        getlocationdetails("ChIJxT0qEKFNqBURZpQ6HCJDBcg");
        getlocationdetails(" ChIJfYxjncSp_BURu8gV3spxcpk");
        getlocationdetails("ChIJwYRR2Zz_Lj4Rr7fUwzR7dMo");
        getlocationdetails("ChIJwSsEYfowrBURhlTUoLqRF1s");
        getlocationdetails("ChIJzRhafio3whURGqqGr15Ll2Q");
        getlocationdetails("ChIJQQxgFUiF5BUR4x3jQU-H5uw");
        getlocationdetails("ChIJMTw4MhpVLj4RRaMEbSijcmk");
        getlocationdetails("ChIJzRhafio3whURGqqGr15Ll2Q");
        getlocationdetails("ChIJN0DyjNxY-xURqt84VSJ6aPo");
        getlocationdetails("ChIJNQUM2W1-5RURZlAtNuQJE5g");
        getlocationdetails("ChIJawFAn7KI6RURorNOsjYCGns");
        getlocationdetails("ChIJ32R1jwBPJT4RS0zg30poui8");
        getlocationdetails("ChIJbxtDfOYALz4RTXJbkqybzZY");
        getlocationdetails("ChIJxYL94AFZ-xURr_lwA84R4g8");
        getlocationdetails("ChIJ0UvqIoQa5RURtNJq2rcitOI");
        getlocationdetails("ChIJLeq-Vvi2Bz4RVsaaIWcBe4w");
        getlocationdetails("ChIJS9nttyMOuRURdAr01ajcBjg");
        getlocationdetails("ChIJge09q_oINT4RdlcXydElYXQ");
        getlocationdetails("ChIJx1CeMxg_1z8RLT2nVsASakA");
        getlocationdetails("ChIJKSgEJBbVERURWaNEdVWSvyw");
        getlocationdetails("ChIJ5RVJLti7chURdrLz6mo5tuI");
        getlocationdetails("ChIJh9sme8dybBUROdoZ_JH1OWc");
        getlocationdetails("ChIJDSodIJ4o8BURThfAHGHrlr0");
        //getlocationdetails("ChIJkVAzcQnZ_hURlK2dVRA9x54");
        getlocationdetails("ChIJEwX0ZvowrBUR8c_VUxg41sQ");
        getlocationdetails("ChIJwSsEYfowrBURhlTUoLqRF1s");
        getlocationdetails("ChIJawFAn7KI6RURorNOsjYCGns");
        getlocationdetails("ChIJUVeDW5WtqRURdPJPj2xd1fI");
        getlocationdetails("ChIJGRG3QtmkGhUR0mepVd0apOU");
        getlocationdetails("ChIJX8FUoS_QwxURvlG6UaAf4rI");
        getlocationdetails("ChIJp8YLs7LHvRUR3KVp978wTZQ");
        getlocationdetails("ChIJwSsEYfowrBURhlTUoLqRF1s");






        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                 for(int i=0;i<places.size();i++){
                     if(places.get(i).getLatLng().toString().equals(marker.getPosition().toString())) {
                         List<Place.Field> fieldList = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.PHONE_NUMBER, Place.Field.NAME, Place.Field.ADDRESS,Place.Field.OPENING_HOURS,Place.Field.PHOTO_METADATAS);
                         final FetchPlaceRequest request = FetchPlaceRequest.newInstance(places.get(i).getPlace_id(), fieldList);
                         placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                             Place place = response.getPlace();
                             nameofplace.setText(place.getName());
                             addressofplace.setText(place.getAddress());
                             phoneofplace.setText(place.getPhoneNumber());
                             detailsofplace.setVisibility(View.VISIBLE);
                             String opningtime=place.getOpeningHours().getWeekdayText().get(0);
                             for(int j=1;j<place.getOpeningHours().getWeekdayText().size();j++){
                                 opningtime+="\n"+place.getOpeningHours().getWeekdayText().get(j);
                             }
                             openingtime.setText(opningtime);
                         }).addOnFailureListener((exception) -> {
                             if (exception instanceof ApiException) {
                                 final ApiException apiException = (ApiException) exception;
                                 final int statusCode = apiException.getStatusCode();
                                 // TODO: Handle error with given status code.
                             }
                         });
                     }}
                         return false;
                     }
                 });



      /*  addmarks(24.7499331,46.744216,"الاداره العامة");
        addmarks(24.731137,46.8013117,"الرياض خريص");
        addmarks(24.554295,46.6769,"الرياض الشفا");
        addmarks(18.27549,42.66625,"خميس مشيط");
        addmarks(21.43629,39.25187,"جدة جنوب");
        addmarks(21.635934,39.213029,"جدة شمال");
        addmarks(26.397778,50.007103," الدمام");
        addmarks(21.452236,40.504665,"الطائف");
        addmarks(28.4190766,36.6561604,"تبوك");
        addmarks(31.46748,37.14067,"القريات");
        addmarks(21.455775,39.878244,"مكه المكرمة");
        addmarks(24.42217,39.51661,"المدينه المنورة");
        addmarks(16.883915,42.590581,"جيزان");
        addmarks(17.582173,44.343987,"نجران");
        addmarks(26.406784,43.878853,"القصيم بريدة");
        addmarks(19.933489,42.606096,"بيشة");
        addmarks(30.969728,41.033041,"عرعر");
        addmarks(29.91816,40.23677,"سكاكا/الجوف");
        addmarks(31.57508,38.4962,"طريف");
        addmarks(28.3783189,45.9647864," حفر الباطن");
        addmarks(27.476611,41.749307,"حائل");
        addmarks(26.999813,49.604948,"الجبيل");
        addmarks(24.133953,38.148229,"ينبع");
        addmarks(17.467775,47.095175,"شرورة");
        addmarks(19.054822,42.123041,"النماص");
        addmarks(29.61593,43.53061,"رفحا");
        addmarks(18.24392,42.770112,"خميس الرونة");
        addmarks(26.222743,50.195014,"الخبر");
        addmarks(24.169789,47.328584,"الخرج");
        addmarks(25.4072715,49.4582106,"الهفوف");
        addmarks(27.360115,35.688997,"ضبا");
        addmarks(21.257197,40.461578," الطائف البلد");
        addmarks(28.405065,36.554165,"تبوك 2");
        addmarks(19.62888,41.9872,"سبت العلايا");
        addmarks(24.1317962,39.145262,"البطحاء الامارات");
        addmarks(21.259782,41.444455," البرحة الجنويية");
        addmarks(20.086733,41.444455," الباحة بنى سار");
        addmarks(20.066033,41.437502,"العقيق-الباحة");
        addmarks(27.478831,39.25187,"محايــل عسير");
        addmarks(21.1824404,42.61036,"تربه");
        addmarks(19.98455,46.8115747," مطار بيشه");
        addmarks(24.8306851,42.65317,"الرياض القادسية");
        addmarks(18.234672,42.65317,"أبها المطار");
        addmarks(28.4326069,48.477449,"الخفجى");
        addmarks(18.1203889 ,42.850811,"أحد رفيدة");
        addmarks(17.306016,42.606769,"صبيا 52");
        addmarks(20.288411,41.646074,"مطار الباحه");
        addmarks(16.899257,42.578083,"مطار جيزان");
        addmarks(19.836522,41.607584,"بلجريشي");*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,5));

    }
    private void addmarks(double lat,double loget,Place place){
        LatLng sydney = new LatLng(lat,loget);
        mMap.addMarker(new MarkerOptions().position(sydney).icon(BitmapDescriptorFactory.fromResource(R.drawable.locationicon)));

    }
}
