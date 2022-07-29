package com.albassami.logistics.ui.activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.payfort.IPaymentRequestCallBack;
import com.albassami.logistics.payfort.PayFortData;
import com.albassami.logistics.payfort.PayFortPayment;
import com.bumptech.glide.Glide;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

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

public class trackingtowing extends FragmentActivity implements OnMapReadyCallback, LocationHelper.OnLocationReceived,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener, IPaymentRequestCallBack {
    public FortCallBackManager fortCallback = null;
    private GoogleMap mMap;
    TextView order_id,status,service_type;
    String order_number;
    PrefUtils prefUtils;
    LinearLayout providerdata;
    TextView name,platenumber,platename,phone,from,to;
    LatLng start,end;
    Button cancel;
    ImageView carphoto;
    private List<Polyline> polylines=null;
    TextView estmatedprice,startprice,forkm;
    ImageView up_down;
    CardView dataofagreement;
    double kms;
    double kmscost,base_price;
    LinearLayout up_downbtn;
    Marker providermarker=null;
    Boolean done=false,done1=false,done2=false,done3=false;
   String cashCard="",fortid="";
   double totalprice=0.0;
  ProgressBar progressBar;
  TextView prcentage;
    ApiServices apiServices;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(trackingtowing.this, MainActivity.class));
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
    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.activity_trackingtowing);
        init();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void updatestatus() {
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

            Call<String> call = apiServices2.order_status(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                    Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)),order_number);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    JSONObject orderdata = null;
                    try {
                        orderdata = new JSONObject(response.body());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(orderdata!=null) {
                        JSONObject data = orderdata.optJSONObject("data");
                        if (data != null) {
                            if (data.optString("status").equals("Wait Provider")) {
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cancelorder(Integer.toString(data.optInt("order_id")));
                                    }
                                });
                                if (!done) {
                                    done = true;
                                    progressBar.setVisibility(View.VISIBLE);
                                    status.setText("في انتظار اختيار السائق....");
                                    service_type.setText(data.optString("tow_truck"));
                                    from.setText(getaddress(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude"))));
                                    to.setText(getaddress(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude"))));
                                    start = new LatLng(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude")));
                                    end = new LatLng(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude")));

                                    providerdata.setVisibility(View.GONE);
                                    cancel.setVisibility(View.VISIBLE);
                                    kmscost = Double.parseDouble(data.optString("km_price"));
                                    base_price = Double.parseDouble(data.optString("base_price"));
                                    Findroutes(start, end);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(start.latitude, start.longitude),
                                            8));

                                }
                            } else if (data.optString("status").equals("Wait Pickup")) {
                                if(providermarker==null) {
                                    providermarker = mMap.addMarker(new MarkerOptions().position(new LatLng(24.767970887287277, 46.57118850582779)).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                                }
                                else {
                                    providermarker.setPosition(new LatLng(Double.parseDouble(data.optString("provider_lat")), Double.parseDouble(data.optString("provider_lng"))));
                                }
                                status.setText("السائق في طريقه اليك....");
                                if (!done) {
                                    done = true;
                                    service_type.setText(data.optString("tow_truck"));
                                    from.setText(getaddress(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude"))));
                                    to.setText(getaddress(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude"))));
                                    start = new LatLng(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude")));
                                    end = new LatLng(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude")));
                                    kmscost = Double.parseDouble(data.optString("km_price"));
                                    base_price = Double.parseDouble(data.optString("base_price"));
                                    Findroutes(start, end);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(start.latitude, start.longitude),
                                            8));
                                }
                                if (!done1) {
                                    done1 = true;
                                    progressBar.setVisibility(View.GONE);
                                    JSONObject provider = data.optJSONObject("provider_data");
                                    providerdata.setVisibility(View.VISIBLE);
                                    cancel.setVisibility(View.GONE);
                                    name.setText(provider.optString("first_name") + "" + provider.optString("last_name"));
                                    String[] phonenumber = provider.optString("mobile_formatted").split(" ");
                                    phone.setText(phonenumber[0] + "-" + phonenumber[1]);
                                   String[] platedata = provider.optString("car_plate").split(" ");
                                   platename.setText(platedata[0]);
                                   platenumber.setText(platedata[1]);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(data.optString("provider_lat")), Double.parseDouble(data.optString("provider_lng"))),
                                            8));
                                    Glide.with(trackingtowing.this).load(provider.optString("car_photo")).into(carphoto);
                                    phone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                            callIntent.setData(Uri.parse("tel: " + phonenumber[0] + phonenumber[1]));


                                            try {
                                                startActivity(callIntent);
                                            } catch (SecurityException s) {
                                                Toast.makeText(trackingtowing.this, s.getMessage(), Toast.LENGTH_SHORT)
                                                        .show();

                                            }
                                        }
                                    });
                                }
                            } else if (data.optString("status").equals("Started")) {
                                prcentage.setWidth(300);
                                if(providermarker==null) {
                                    providermarker = mMap.addMarker(new MarkerOptions().position(new LatLng(24.767970887287277, 46.57118850582779)).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                                }
                                else {
                                    providermarker.setPosition(new LatLng(Double.parseDouble(data.optString("provider_lat")), Double.parseDouble(data.optString("provider_lng"))));
                                }
                                status.setText("تم استلام السياره والسائق في طريقه الي موقع الوصول....");
                                if (!done) {
                                    done = true;
                                    service_type.setText(data.optString("tow_truck"));
                                    from.setText(getaddress(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude"))));
                                    to.setText(getaddress(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude"))));
                                    start = new LatLng(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude")));
                                    end = new LatLng(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude")));
                                    kmscost = Double.parseDouble(data.optString("km_price"));
                                    base_price = Double.parseDouble(data.optString("base_price"));
                                    Findroutes(start, end);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(start.latitude, start.longitude),
                                            8));
                                }
                                if (!done1) {
                                    done1 = true;
                                    progressBar.setVisibility(View.GONE);
                                    JSONObject provider = data.optJSONObject("provider_data");
                                    providerdata.setVisibility(View.VISIBLE);
                                    cancel.setVisibility(View.GONE);
                                    name.setText(provider.optString("first_name") + "" + provider.optString("last_name"));
                                    String[] phonenumber = provider.optString("mobile_formatted").split(" ");
                                    phone.setText(phonenumber[0] + "-" + phonenumber[1]);
                                    String[] platedata = provider.optString("car_plate").split(" ");
                                    platename.setText(platedata[0]);
                                    platenumber.setText(platedata[1]);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(data.optString("provider_lat")), Double.parseDouble(data.optString("provider_lng"))),
                                            8));
                                    Glide.with(trackingtowing.this).load(provider.optString("car_photo")).into(carphoto);
                                    phone.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                            callIntent.setData(Uri.parse("tel: " + phonenumber[0] + phonenumber[1]));


                                            try {
                                                startActivity(callIntent);
                                            } catch (SecurityException s) {
                                                Toast.makeText(trackingtowing.this, s.getMessage(), Toast.LENGTH_SHORT)
                                                        .show();

                                            }
                                        }
                                    });
                                }
                            } else if (data.optString("status").equals("Customer Delivered")) {
                                prcentage.setWidth(400);
                                  if (!done) {
                                    done = true;
                                    service_type.setText(data.optString("tow_truck"));
                                    from.setText(getaddress(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude"))));
                                    to.setText(getaddress(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude"))));
                                    start = new LatLng(Double.parseDouble(data.optString("pickup_latitude")), Double.parseDouble(data.optString("pickup_longitude")));
                                    end = new LatLng(Double.parseDouble(data.optString("destination_latitude")), Double.parseDouble(data.optString("destination_longitude")));
                                    kmscost = Double.parseDouble(data.optString("km_price"));
                                    base_price = Double.parseDouble(data.optString("base_price"));
                                    Findroutes(start, end);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(start.latitude, start.longitude),
                                            8));
                                }
                                if(!done2) {
                                    done2=true;
                                    progressBar.setVisibility(View.GONE);
                                    Dialog dialog = new Dialog(trackingtowing.this, R.style.custom_dialogue);
                                    dialog.setContentView(R.layout.activity_towing_done);
                                    dialog.setCancelable(false);
                                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                  ImageView close=dialog.findViewById(R.id.cancelpayment);
                                  close.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          onBackPressed();
                                      }
                                  });
                                   TextView Card=dialog.findViewById(R.id.creditcardpaid);
                                    totalprice= data.optDouble("cost");
                                    TextView Cash=dialog.findViewById(R.id.cashpaid);
                                    Button next=dialog.findViewById(R.id.totalpricepaid);
                                    next.setText((totalprice)+" SR");
                                    Card.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            GradientDrawable cardDrawable = (GradientDrawable) Card.getBackground().mutate();
                                            cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                                            GradientDrawable cashDrawable = (GradientDrawable) Cash.getBackground().mutate();
                                            cashDrawable.setColor(getResources().getColor(R.color.et_color));
                                            cashCard = "card";
                                        }
                                    });
                                    Cash.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            GradientDrawable cardDrawable = (GradientDrawable) Cash.getBackground().mutate();
                                            cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                                            GradientDrawable cashDrawable = (GradientDrawable) Card.getBackground().mutate();
                                            cashDrawable.setColor(getResources().getColor(R.color.et_color));
                                            cashCard = "cash";
                                        }
                                    });
                                    next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(cashCard.equals("cash")){
                                            payingprocesses(cashCard,"0");
                                        }
                                        else{
                                                requestForPayfortPayment();
                                            }
                                        }
                                    });

                                    dialog.show();
                                }
                            }

                        }


                    }}
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                        UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                    }
                }
            });


        }
    private void requestForPayfortPayment() {
        PayFortData payFortData1 = new PayFortData();
        if (!TextUtils.isEmpty(Double.toString(totalprice))) {
            payFortData1.amount = String.valueOf((int) (Float.parseFloat(Double.toString(totalprice))* 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData1.command = PayFortPayment.PURCHASE;
            payFortData1.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData1.customerEmail = "";
            payFortData1.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData1.merchantReference = String.valueOf(System.currentTimeMillis());
            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
           sendtoodoo(payFortData1,payFortPayment);
        }
    }


    private void sendtoodoo(PayFortData payFortData, PayFortPayment payFortPayment) {
        apiServices = RestClient.getApiService();
        UiUtils.showLoadingDialog(trackingtowing.this);
        OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();
        apiServices = RestClient.getApiService();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.Bassami_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiServices apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.fortdata(order_number,
                Double.toString(totalprice),
                prefUtils.getStringValue(PrefKeys.Phone, "")+"@albassamitransport.com", PayFortPayment.LANGUAGE_TYPE,PayFortPayment.CURRENCY_TYPE
                ,PayFortPayment.PURCHASE,String.valueOf(System.currentTimeMillis())
        );
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();

                payFortPayment.requestForPayment(payFortData);



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(trackingtowing.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });

    }
          @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }}
    @Override
    public void onPaymentRequestResponse(int responseType, final PayFortData responseData) {
        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
        } else {
            Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            fortid=responseData.fortId;
            payingprocesses(cashCard,Double.toString(totalprice));


        }
    }
    private void payingprocesses(String cashCard, String price) {

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
        Call<String> call = apiServices2.paying_agreement(Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0))
                ,prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),order_number,cashCard,price,fortid);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                Intent intent = new Intent(trackingtowing.this, ThankYouActivity.class);
                intent.putExtra("order_id", order_number);
                startActivity(intent);
                finish();

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(trackingtowing.this, "please try again", Toast.LENGTH_SHORT).show();
            }


        });

    }

    private String getaddress(Double latitude, Double longitude) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(addresses!=null &&addresses.size()>0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String[] city = addresses.get(0).getAddressLine(0).split(",");
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getSubThoroughfare();

            return address;
        }
        else {return "null";}

    }


    void init(){
        initilizePayFortSDK();
        order_number=getIntent().getStringExtra("order_id");
        prefUtils = PrefUtils.getInstance(this);
     order_id=findViewById(R.id.order_id);
     order_id.setText("#"+order_number);
     up_downbtn=findViewById(R.id.up_down);
     up_down=findViewById(R.id.down_up);
        status=findViewById(R.id.status);
        providerdata=findViewById(R.id.providerdata);
        service_type=findViewById(R.id.service_type);
        platename=findViewById(R.id.platetext);
        dataofagreement=findViewById(R.id.dataofagreement);
        platenumber=findViewById(R.id.platenumber);
        name=findViewById(R.id.name);
        cancel=findViewById(R.id.cancel);
        phone=findViewById(R.id.phonenumber);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        estmatedprice=findViewById(R.id.estmatedprice);
        forkm=findViewById(R.id.forkm);
        startprice=findViewById(R.id.startprice);
       progressBar=findViewById(R.id.waitprovider);
        forkm.setText(getIntent().getStringExtra("forkm")+" SR");
        startprice.setText(getIntent().getStringExtra("startwith")+" SR");
        prcentage=findViewById(R.id.precentage);
        carphoto=findViewById(R.id.carphoto);

        up_downbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataofagreement.getVisibility()==View.GONE){
                dataofagreement.setVisibility(View.VISIBLE);
                up_down.animate().rotation(360).setDuration(1000);
                    }
                else {
                    dataofagreement.setVisibility(View.GONE);
                    up_down.animate().rotation(180).setDuration(1000);
                    }
            }
        });

}

    private void cancelorder(String orderid) {
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
        Call<String> call = apiServices2.cancel_order_tow(Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0))
                ,prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),orderid);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    UiUtils.hideLoadingDialog();
                    Toast.makeText(trackingtowing.this, "order has been cancelled", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(trackingtowing.this,MainActivity.class));
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(trackingtowing.this, "cant cancel this order", Toast.LENGTH_SHORT).show();
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
            updatestatus();
            final Handler handler = new Handler();
            final int delay = 15000; // 1000 milliseconds == 1 second

            handler.postDelayed(new Runnable() {
                public void run() {
                    updatestatus();
                    handler.postDelayed(this, delay);
                }
            }, delay);

        }
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));
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

    public class TaskRequestdirction extends AsyncTask<String,Void, String> {
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
            Toast.makeText(trackingtowing.this,"Unable to get location", Toast.LENGTH_LONG).show();
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


    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {

    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
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
                polyOptions.color(getResources().getColor(R.color.black));
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);
                String[] km=route.get(i).getDistanceText().split(" ");
                if(km[1].equals("m")){
                    kms=Double.parseDouble(km[0])/1000;
                }
                else{
                    kms=Double.parseDouble(km[0]);
                }
                int val= (int) (((kmscost*kms)+base_price)*100);
                double price=val/100.00;
                estmatedprice.setText(price+" SR");



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

    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);

    }
}