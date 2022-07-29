package com.albassami.logistics.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.DirectionHelper;
import com.albassami.logistics.dto.GeocodingLocation;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.network.Models.towtype;
import com.albassami.logistics.ui.Adapter.price;
import com.albassami.logistics.ui.Adapter.towing_serviceadpt;
import com.albassami.logistics.ui.Adapter.towing_servicemodel;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import retrofit2.http.Url;

public class select_from_to extends FragmentActivity implements OnMapReadyCallback, LocationHelper.OnLocationReceived,
 GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private GoogleMap mMap;
    SupportMapFragment search_place_map;
    private LocationHelper locHelper;

Polyline polyline1;
LinearLayout c1,c2,c3,c4,c5;

ArrayList<LatLng> latlanglist=new ArrayList<>();
LinearLayout details;
Button nextbtn;
String servicetype="";
TextView startwithprice,timeprice,kmprice;
LinearLayout details1;
String type;
TextView from,to;
String price,priceforkm;
LatLng fromloc;
LatLng toloc;
towing_serviceadpt towing_serviceadpt;
ArrayList<towing_servicemodel> towing_servicemodelArrayList;
    LatLng start;
    LatLng end;
    private List<Polyline> polylines=null;
    towtype closed,regular,hydrulic;
    String towing_id;
    TextView distance;
    TextView estmatedtime;
    String strdistance,strtime;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.dialogue_get_location1);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        closed=new towtype();
        regular=new towtype();
        hydrulic=new towtype();
c1=findViewById(R.id.regular);
        c2=findViewById(R.id.hydraulic);
        c3=findViewById(R.id.closed);
        c4=findViewById(R.id.coast_startwith1);
        c5=findViewById(R.id.coast_km1);
        details=findViewById(R.id.details);
        nextbtn=findViewById(R.id.nextbtn);
        distance=findViewById(R.id.distanceofpoints);
        estmatedtime=findViewById(R.id.estmated_time);
from=findViewById(R.id.fromlocation);
to=findViewById(R.id.tolocation);
        gettowtypes();
from.setText(getIntent().getStringExtra("from"));
        to.setText(getIntent().getStringExtra("to"));
        details1=findViewById(R.id.details1);
        type=getIntent().getStringExtra("type");
        startwithprice=findViewById(R.id.startwithprice);
        timeprice=findViewById(R.id.timeprice);
        kmprice=findViewById(R.id.kmprice);
        towing_servicemodelArrayList=new ArrayList<>();
        setupservice();
        Bundle bundle = getIntent().getParcelableExtra("from_to");
         start = bundle.getParcelable("from_position");
         end = bundle.getParcelable("to_position");

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextstep();
            }
        });

        //place1=new Marker()
        //place2=new MarkerOptions();

        setchoice();
        c4.setBackgroundResource(R.drawable.rounded_with_green);
        Places.initialize(getApplicationContext(),getString(R.string.map_key));
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

        //String url=geturl(place1.getPosition(),place2.getPosition(),"Driving");

    }

    private void setupservice() {
        price price=new price(120.0,1.0,1.0);
        towing_servicemodel towing_servicemodel=new towing_servicemodel(R.drawable.startwith,R.string.startwith,price);
        price price1=new price(120.0,1.0,1.0);
        towing_servicemodel towing_servicemodel1=new towing_servicemodel(R.drawable.destance,R.string.distance,price);
        price price2=new price(120.0,1.0,1.0);
        towing_servicemodel towing_servicemodel2=new towing_servicemodel(R.drawable.timer,R.string.time_cost,price);
        towing_servicemodelArrayList.add(towing_servicemodel);
        towing_servicemodelArrayList.add(towing_servicemodel1);
        towing_servicemodelArrayList.add(towing_servicemodel2);
        towing_serviceadpt=new towing_serviceadpt(towing_servicemodelArrayList,this);


    }
   public  void getpriceofservice(price price1, Context context){
//       if(type.equals("1")){
      //     details.setVisibility(View.VISIBLE);}
 //      else if(type.equals("2")){
 //          details1.setVisibility(View.VISIBLE);
 //      }
       servicetype=getString(R.string.hydraulic);
       kmprice.setText(price1.getDistanceprice()+"SR/KM");
       timeprice.setText(price1.getTimeprice()+"SR/KM");
       startwithprice.setText(price1.getStartwith()+"SR");



   }

    private void nextstep() {

        if(details.getVisibility()==View.GONE && details1.getVisibility()==View.GONE){
            Toast.makeText(this, "please select type of towing", Toast.LENGTH_SHORT).show();

        }

        else{

Intent intent=new Intent(this,detailsform.class);
intent.putExtra(Const.PassParam.SERVICE_TYPE,servicetype);
            intent.putExtra(Const.PassParam.BRANCH_NAME,getIntent().getStringExtra("from"));
            intent.putExtra(Const.PassParam.BRANCH_NAME_TO,getIntent().getStringExtra("to"));
            Bundle bundle=new Bundle();
            bundle.putParcelable("lat_lang_from",start);
            bundle.putParcelable("lat_lang_to",end);
            intent.putExtra("location",bundle);
            intent.putExtra("towing_id",towing_id);
            intent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
            String[] distance=strdistance.split(" ");
            if(distance[1].equals("m")){
                intent.putExtra("distance",Double.toString(Double.parseDouble(distance[0])/1000));
            }
            else if(distance[1].equals("km")){
                intent.putExtra("distance",distance[0]);
            }
            else{
                intent.putExtra("distance","0");
            }

            intent.putExtra("time",strtime);
            intent.putExtra("forkm",priceforkm);
            intent.putExtra("startwith",price);
            startActivity(intent);
        }

    }



    private void setchoice() {
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackgroundResource(R.drawable.rounded_with_green);
                c2.setBackgroundColor(Color.TRANSPARENT);
               c3.setBackgroundColor(Color.TRANSPARENT);
              //  if(type.equals("1")){
                details.setVisibility(View.VISIBLE);
            //    }
            //    else if(type.equals("2")){
             //       details1.setVisibility(View.VISIBLE);
            //    }
               servicetype=getString(R.string.hydraulic);
               if(regular!=null){
                kmprice.setText(regular.getKm_price()+"SR/KM");
               timeprice.setText(regular.getMinute_price()+"SR/KM");
                startwithprice.setText(regular.getBaseprice()+"SR");
                   towing_id=regular.getTow_trunck_id();
                   price=regular.getBaseprice();
                   priceforkm=regular.getKm_price();
               }

            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackgroundColor(Color.TRANSPARENT);
                c2.setBackgroundResource(R.drawable.rounded_with_green);
                c3.setBackgroundColor(Color.TRANSPARENT);
              //  if(type.equals("1")){
                    details.setVisibility(View.VISIBLE);
                //}
              //  else if(type.equals("2")){
             //       details1.setVisibility(View.VISIBLE);
            //    }
                servicetype=getString(R.string.winch);
                if(hydrulic!=null){
                kmprice.setText(hydrulic.getKm_price()+"SR/KM");
                timeprice.setText(hydrulic.getMinute_price()+"SR/KM");
                startwithprice.setText(hydrulic.getBaseprice()+"SR");
                    towing_id=hydrulic.getTow_trunck_id();
                    price=hydrulic.getBaseprice();
                    priceforkm=hydrulic.getKm_price();
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackgroundColor(Color.TRANSPARENT);
                c2.setBackgroundColor(Color.TRANSPARENT);
                c3.setBackgroundResource(R.drawable.rounded_with_green);
              //  if(type.equals("1")){
                    details.setVisibility(View.VISIBLE);
            //}
              //  else if(type.equals("2")){
              //      details1.setVisibility(View.VISIBLE);
             //   }
                servicetype=getString(R.string.closed);
                if(closed!=null){
                kmprice.setText(closed.getKm_price()+"SR/KM");
                timeprice.setText(closed.getMinute_price()+"SR/KM");
                startwithprice.setText(closed.getBaseprice()+"SR");
                    towing_id=closed.getTow_trunck_id();
                    price=closed.getBaseprice();
                    priceforkm=closed.getKm_price();
                }
            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c5.setBackgroundColor(Color.TRANSPARENT);
                c4.setBackgroundResource(R.drawable.rounded_with_green);

            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c4.setBackgroundColor(Color.TRANSPARENT);
                c5.setBackgroundResource(R.drawable.rounded_with_green);
                servicetype+=getString(R.string.goingandcomingback);

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      if(mMap!=null){  googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);

      //  googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
          Findroutes(start,end);
      }
        // Add a marker in Sydney and move the camera
      //  LatLng sydney = new LatLng(-34, 151);

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



    }
    private String getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 &&resultCode==RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);



        }
        if(requestCode==101 &&resultCode==RESULT_OK){
            Place place1 = Autocomplete.getPlaceFromIntent(data);




        }

        }

    private String requestdirection(String requrl) throws IOException {
        String response="";
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection=null;
        try{
            URL url=new URL(requrl);
              httpURLConnection=(HttpURLConnection) url.openConnection();
              httpURLConnection.connect();
              inputStream =httpURLConnection.getInputStream();
              InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
              BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
              StringBuffer stringBuffer=new StringBuffer();
              String line="";
              while ((line=bufferedReader.readLine())!=null){
                  stringBuffer.append(line);
              }
              response=stringBuffer.toString();
              bufferedReader.close();
              inputStreamReader.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
return response;
    }
    public class TaskRequestdirction extends AsyncTask<String,Void, String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String response="";
            try{
                response=requestdirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();

            }
            return response;
        }
    }
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(select_from_to.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key(getString(R.string.map_key))  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(select_from_to.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(getResources().getColor(R.color.green));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);
                distance.setText(route.get(i).getDistanceText());
                estmatedtime.setText(route.get(i).getDurationText());
                strdistance=route.get(i).getDistanceText();
                strtime=route.get(i).getDurationText();
            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.pickup_location));
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.drop_location));
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(polylineStartLatLng.latitude,polylineStartLatLng.longitude),
                8));
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);

    }

   public void gettowtypes(){
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

       Call<String> call = apiServices2.gettowtypes("1");
       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               UiUtils.hideLoadingDialog();
               JSONArray towdata = null;
               try {
                   towdata = new JSONArray(response.body());
               } catch (Exception e) {
                   e.printStackTrace();
               }
               if (towdata != null) {
                   JSONObject items=null;
for(int i=0;i<towdata.length();i++){
    try {
        items=towdata.getJSONObject(i);}
    catch (JSONException e) {
        e.printStackTrace();
    }
    if(items!=null){
        if(items.optString("shippment_type").equals("شحن السيارات")){
            if(items.optBoolean("is_active")==true){
                if(items.optString("tow_truck").equals("سطحة هيدرولك")){
                    c1.setVisibility(View.VISIBLE);
                        regular.setKm_price(Double.toString(items.optDouble("km_price")));
                    regular.setBaseprice(Double.toString(items.optDouble("base_price")));
                    regular.setMinute_price(Double.toString(items.optDouble("minute_price")));
                    regular.setTow_trunck_id(items.optString("tow_truck_id"));
                }
                else if(items.optString("tow_truck").equals("سطحة مغطاه")){
                    c3.setVisibility(View.VISIBLE);
                    closed.setKm_price(Double.toString(items.optDouble("km_price")));
                    closed.setBaseprice(Double.toString(items.optDouble("base_price")));
                    closed.setMinute_price(Double.toString(items.optDouble("minute_price")));
                    closed.setTow_trunck_id(items.optString("tow_truck_id"));
                }
                else if(items.optString("tow_truck").equals("سطحة ونش")){
                    c2.setVisibility(View.VISIBLE);
                    hydrulic.setKm_price(Double.toString(items.optDouble("km_price")));
                    hydrulic.setBaseprice(Double.toString(items.optDouble("base_price")));
                    hydrulic.setMinute_price(Double.toString(items.optDouble("minute_price")));
                    hydrulic.setTow_trunck_id(items.optString("tow_truck_id"));
                }
            }
            else{
                if(items.optString("tow_truck").equals("سطحة هيدرولك")){
                   c1.setVisibility(View.GONE);
                }
                else if(items.optString("tow_truck").equals("سطحة مغطاه")){
                    c3.setVisibility(View.GONE);
                }
                else if(items.optString("tow_truck").equals("سطحة ونش")){
                    c2.setVisibility(View.GONE);
                }
            }

        }}
    }
} }



           @Override
           public void onFailure(Call<String> call, Throwable t) {
               UiUtils.hideLoadingDialog();
               if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                      UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
               }
           }
       });


    }
}

