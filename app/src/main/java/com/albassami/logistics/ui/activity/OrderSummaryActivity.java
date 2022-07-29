package com.albassami.logistics.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;
import java.io.*;
import okhttp3.*;
//import kong.unirest.HttpResponse;
//import kong.unirest.Unirest;
import okhttp3.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.TouchImageView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CreateOrderResponse;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.PriceResponse;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.network.ApiManager.clientcustom;
import com.albassami.logistics.payfort.IPaymentRequestCallBack;
import com.albassami.logistics.payfort.PayFortData;
import com.albassami.logistics.payfort.PayFortPayment;
import com.albassami.logistics.ui.Adapter.PaymentIconAdapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.NATIONAL_ID;

public class OrderSummaryActivity extends AppCompatActivity implements IPaymentRequestCallBack {
    String carSize,shipment_date, phoneNumber, receivrName, maker_id, carModelName, receiverNumber, cashCard = "", idNumber, piateNumber, carModel, serviceType, branchName, ownerName, branchId, carSizeID, carModelID, branchNameTo, branchIdTo;
    ArrayList<Integer> iconList;
    PaymentIconAdapter paymentIconAdapter;
    public FortCallBackManager fortCallback = null;
    ApiServices apiServices,apiServices1;
    TextView tvPhoneNumber, tvReceiverName, tvTotalCost, tvSenderName, tvSource, tvCarSize, tvDestination, tvService, tvPrice, tvCash, tvCard,shapmentdate,expacteddate,otherservice;
    PrefUtils prefUtils;
    String payPrice = "",date="";
    Button btnNext;
    LinearLayout from_to_location;
    ImageView btnBack;
    APIInterface apiInterface;
    String[] numbers={"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    Spinner small,medium,large;
    ArrayAdapter<String> numbersarr;
    CheckBox homedeleviry,homepickup;
    TextView price,paymenttxt;
    LinearLayout otherservice1,paymentmathods;
    EditText homelocation ,pickuplocation,homelocation1 ,pickuplocation1;
    String is_home_pickup="false",is_home_delivery="false";
    String home_location="",pickup_location="";
    String is_large_box="0",is_medium_box="0",is_small_box="0";
  String app_payment_method="",app_paid_amount="0",app_fortid="",visapaid="0";
  double inthomedelivery,inthomepickup,intsmall,intmedium,intlarge;
String shipmenttype="";
TextView textView;
   double taxes;
    double smallboxcost=0,mediumboxcost=0,largboxcost=0,servicecost=0,totalcost=0,originalprice=0;
    String getReceiverNumber="";
    Button comfirm;
    String platetype;
    String countryid="";
    String ownernationallity,owneridcard,ownertype,owneridtype,ownername;
    String customer_nationality;
    GetPriceDataResponse dataResponse;
    String  nationality_id,id_typenumber;
    TextView addcode;
    CheckBox condtions;
    TextView opencontions;
    String order_id_backend="";
    String order_ref_backend="";
    private String home_locationlat,home_locationlong,pickup_locationlat,pickup_locationlong;
    LinearLayout homedelivirylayout,homepickuplayout;

    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
            setContentView(R.layout.activity_order_summary);


        inIT();
        getProfile();
textView=findViewById(R.id.tvHeader4);

        if(serviceType.equals(getResources().getString(R.string.special_towing))||serviceType.equals(getResources().getString(R.string.full_load))||serviceType.equals(getResources().getString(R.string.closed))){
            textView.setVisibility(View.VISIBLE);
            from_to_prepare();
        }
        else if(serviceType.equals(getResources().getString(R.string.international))){
            otherservice.setVisibility(View.GONE);
        }
        else{
            textView.setVisibility(View.GONE);
        otherserviceprepare();}
        paymenttxt.setOnClickListener(i->checkStatus());
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }

    }
    protected void getProfile() {
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

        Call<String> call = apiServices2.getProfile(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                JSONObject profileResponse = null;
                try {
                    profileResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (profileResponse != null) {


                        owneridcard= profileResponse.optString("national_id");
                        owneridtype=profileResponse.optString("identity_type");
                        ownernationallity=profileResponse.optString("nationality");
                        if(profileResponse.optString("gender").equals("male")){

                            ownertype="1";
                        }
                        else{
                            ownertype="2";
                        }


                    }
                }


            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                       UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpLocale();
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


    private void from_to_prepare() {
        otherservice.setText(R.string.Set_the_receiving_and_delivery_location);
        otherservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(from_to_location.getVisibility()==View.VISIBLE){
                    from_to_location.setVisibility(View.GONE);
                }
                else{
                    from_to_location.setVisibility(View.VISIBLE);

                }
            }
        });
        pickuplocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this, SelectLocationActivity.class).putExtra("type","0");;
                startActivityForResult(i, 16);
            }
        });
        homelocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this, SelectLocationActivity.class).putExtra("type","0");;
                startActivityForResult(i, 15);
            }
        });
    }

    private void checkStatus() {
        if(paymentmathods.getVisibility()==View.VISIBLE){
            paymentmathods.setVisibility(View.GONE);
        }
        else {paymentmathods.setVisibility(View.VISIBLE);}
    }

    private void otherserviceprepare() {
        otherservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otherservice1.getVisibility()==View.VISIBLE){
                otherservice1.setVisibility(View.GONE);
                }
                else{
                    otherservice1.setVisibility(View.VISIBLE);

                }
            }
        });
        pickuplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this, SelectLocationActivity.class).putExtra("type","0");;
                startActivityForResult(i, 13);
            }
        });
        homelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSummaryActivity.this, SelectLocationActivity.class).putExtra("type","0");;
                startActivityForResult(i, 11);
            }
        });
        homedeleviry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateprice("","","",inthomedelivery);
                    is_home_delivery="true";
                }
                else{updateprice("","","",-1*inthomedelivery);
                is_home_delivery="false";
                }
            }
        });
        homepickup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    updateprice("","","",inthomepickup);
                    is_home_pickup="true";
                }
                else{
                    updateprice("","","",-1*inthomepickup);
                    is_home_pickup="false";
                }
            }
        });
small.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
{
    public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
    {
        updateprice(Double.toString(Integer.parseInt(numbers[position])*intsmall),"","",0);
        is_small_box=numbers[position];
    }

    public void onNothingSelected(AdapterView<?> arg0)
    {

    }
});
        medium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
                updateprice("",Double.toString(Integer.parseInt(numbers[position])*intmedium),"",0);
                is_medium_box=numbers[position];
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
        large.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
                updateprice("","",Double.toString(Integer.parseInt(numbers[position])*intlarge),0);
                is_large_box=numbers[position];
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });

    }

    private void updateprice(String smallboxcost,String mediumboxcost,String largeboxcost,double servicecost) {
      if(!smallboxcost.equals("")){
       this.smallboxcost=Double.parseDouble(smallboxcost);}
      if(!mediumboxcost.equals("")){
       this.mediumboxcost=Double.parseDouble(mediumboxcost);}
      if(!largeboxcost.equals("")){
       this.largboxcost=Double.parseDouble(largeboxcost);}
       this.servicecost+=servicecost;
       totalcost=(this.smallboxcost+this.mediumboxcost+this.largboxcost+this.servicecost)+(this.smallboxcost+this.mediumboxcost+this.largboxcost+this.servicecost)*(taxes/100.0);
       price.setText(totalcost+"SR");
        btnNext.setText(getString(R.string.total_cost)+" : "+(totalcost+originalprice+(originalprice*taxes/100.0))+"SR");
        app_paid_amount=Double.toString(totalcost+originalprice+taxes);
    }

    private void inIT() {
        initilizePayFortSDK();
        serviceType = getIntent().getExtras().getString(Const.PassParam.SERVICE_TYPE);
        branchName = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME);
        ownerName = getIntent().getExtras().getString(Const.PassParam.OWNER_NAME);
        idNumber = getIntent().getExtras().getString(Const.PassParam.ID_NUMBER);
        phoneNumber = getIntent().getExtras().getString(Const.PassParam.PHONE_NUMBER);
        piateNumber = getIntent().getExtras().getString(Const.PassParam.PIATE_NUMBER);
        platetype = getIntent().getExtras().getString("platetype");
        carSize = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE);
        carModel = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        branchId = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID);
        carSizeID = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE_ID);
        carModelID = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_ID);
        shipment_date = getIntent().getExtras().getString("Date");
        maker_id = getIntent().getExtras().getString(Const.PassParam.CAR_MAKER_ID);
        branchIdTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID_TO);
        branchNameTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME_TO);
        receivrName = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NAME);
        receiverNumber = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NUMBER);
        carModelName = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        getReceiverNumber=getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER);
        countryid=getIntent().getExtras().getString(Const.PassParam.RECEIVER_COUNTRY);
        id_typenumber=getIntent().getExtras().getString("id_typenumber");
        nationality_id=getIntent().getExtras().getString("nationality_id");
        tvTotalCost=findViewById(R.id.tvTotalPrice);
        otherservice=findViewById(R.id.other_service);
        otherservice1=findViewById(R.id.other_service1);
        pickuplocation=findViewById(R.id.pivkuplocation);
        homelocation=findViewById(R.id.homelocation);
        homedeleviry=findViewById(R.id.homedlevery);
        homepickup=findViewById(R.id.homepickup);
        condtions=findViewById(R.id.acceptcondtionandterms);
        opencontions=findViewById(R.id.opencontionsandterms);
        price=findViewById(R.id.price);
        from_to_location=findViewById(R.id.from_to_location);
        homelocation1=findViewById(R.id.homelocation1);
        pickuplocation1=findViewById(R.id.pivkuplocation1);
        date=getIntent().getExtras().getString("Date");
        prefUtils = PrefUtils.getInstance(this);
        //  rvIcons = findViewById(R.id.rvIcons);
        btnBack = findViewById(R.id.btnBack);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvCash = findViewById(R.id.tvCash);
        tvCard = findViewById(R.id.tvCard);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvPrice = findViewById(R.id.tvPrice);
        tvSenderName = findViewById(R.id.tvSenderName);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvCarSize = findViewById(R.id.tvCarSize);
        tvService = findViewById(R.id.tvServiceType);
        btnNext = findViewById(R.id.btnNext);
        shapmentdate=findViewById(R.id.tvshapment_date);
        expacteddate=findViewById(R.id.tvexpacted_date);
        iconList = new ArrayList<>();
        iconList.add(R.drawable.viza_card);
        iconList.add(R.drawable.master_card);
        iconList.add(R.drawable.apple_pay);
        small=findViewById(R.id.small);
        medium=findViewById(R.id.medium);
        large=findViewById(R.id.large);
        addcode=findViewById(R.id.addcode);
        paymenttxt=findViewById(R.id.paymenttxt);
        paymentmathods=findViewById(R.id.paymethods);
        homedelivirylayout=findViewById(R.id.homedelivierlayout);
        homepickuplayout=findViewById(R.id.homepickuplayout);
        numbersarr=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,numbers);
        small.setAdapter(numbersarr);
        medium.setAdapter(numbersarr);
        large.setAdapter(numbersarr);

        opencontions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condtions();
            }
        });
addcode.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        addcode();
    }
});
        //getqunatatyofboxes();
//        rvIcons.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        paymentIconAdapter = new PaymentIconAdapter(this, iconList);
//        rvIcons.setAdapter(paymentIconAdapter);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
        String currentTime = df.format(Calendar.getInstance().getTime());
        tvService.setText(serviceType);
        if (serviceType.equalsIgnoreCase(Const.DOOR_TO_DOOR)) {

        }
        else {


            tvCarSize.setText(carSize + "-" + carModelName);
            tvPhoneNumber.setText(receiverNumber);
            tvReceiverName.setText(receivrName);
        }


        tvDestination.setText(branchNameTo);
        tvSource.setText(branchName);

        tvSenderName.setText(prefUtils.getStringValue(PrefKeys.USER_NAME, ""));
        if (serviceType.equalsIgnoreCase(Const.DOOR_TO_DOOR)) {

        } else {

            tvCarSize.setText(carModelName);
            tvPhoneNumber.setText(receiverNumber);
            tvReceiverName.setText(receivrName);

        }
        onClickListners();
       if(serviceType.equalsIgnoreCase(getString(R.string.intercity))||serviceType.equalsIgnoreCase(getString(R.string.international))||serviceType.equalsIgnoreCase(getString(R.string.special_towing))||serviceType.equalsIgnoreCase(getString(R.string.full_load))
               ||serviceType.equalsIgnoreCase(getString(R.string.closed))){
        getPrice();
           }
       else{
           shapmentdate.setText(date);
       }
    }

    private void create_init_order() {
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
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.create_init_order(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)),branchId, branchIdTo, shipmenttype,getIntent().getStringExtra("carid"),"123456",
                receivrName
                ,countryid
                ,receiverNumber
                , getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER)
                , "1"
                , "1"
                ,"1"
                ,"1"
                ,"1",
                "1"
                );
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JSONObject jsonObject=null;
                    try {
                         jsonObject=new JSONObject(response.body());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(jsonObject!=null){
                     JSONObject   data=jsonObject.optJSONObject("data");
                        if(data!=null){
                            order_ref_backend=data.optString("order_ref");
                                    order_id_backend=Integer.toString(data.optInt("order_id"));

                        }
                    }



                        }

                else if(response.errorBody()!=null) {
                    Toast.makeText(OrderSummaryActivity.this,getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });

    }

    private void addcode() {
        setUpLocale();
        Dialog dialog=new Dialog(OrderSummaryActivity.this);
        dialog.setContentView(R.layout.addcode);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        EditText addcode=dialog.findViewById(R.id.discountcode);
        TextView textView=dialog.findViewById(R.id.textView);
        SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            addcode.setText("إضـافة");
            textView.setText("ادخل كوبون الخصم");
        }
        else{
            addcode.setText("add");
            textView.setText("Enter discount code");
        }
        ImageView close=dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button submit=dialog.findViewById(R.id.submitcode);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

  /*  private void getqunatatyofboxes() {
        small.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getcost();
            }
        });
    }*/



    private void onClickListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefUtils.getBooleanValue(PrefKeys.IS_LOGGED_IN, false)) {
                    if (TextUtils.isEmpty(cashCard)) {
                        Toast.makeText(OrderSummaryActivity.this, getString(R.string.Pleaseselectpaymentmethod), Toast.LENGTH_SHORT).show();
                    }
                    else if(!condtions.isChecked()){
                        Toast.makeText(OrderSummaryActivity.this, getString(R.string.please_accept_our_terms_and_conditions), Toast.LENGTH_SHORT).show();
                    }
                    else if (cashCard.equalsIgnoreCase("cash")) {
                        app_payment_method="cash";
//                        Intent intent = new Intent(OrderSummaryActivity.this, RideLaterActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString(Const.PassParam.BRANCH_NAME, branchName);
//                        bundle.putString(Const.PassParam.BRANCH_ID, branchId);
//                        bundle.putString(Const.PassParam.BRANCH_NAME_TO, branchNameTo);
//                        bundle.putString(Const.PassParam.BRANCH_ID_TO, branchIdTo);
//                        bundle.putString(Const.PassParam.SERVICE_TYPE, serviceType);
//                        bundle.putString(Const.PassParam.CAR_MODEL, carModel);
//                        bundle.putString(Const.PassParam.CAR_SIZE, carSize);
//                        bundle.putString(Const.PassParam.CAR_MODEL_ID, carModelID);
//                        bundle.putString(Const.PassParam.CAR_SIZE_ID, carSizeID);
//                        bundle.putString(Const.PassParam.ID_NUMBER, idNumber);
//                        bundle.putString(Const.PassParam.PHONE_NUMBER, phoneNumber);
//                        bundle.putString(Const.PassParam.OWNER_NAME, ownerName);
//                        bundle.putString(Const.PassParam.PIATE_NUMBER, piateNumber);
//                        bundle.putString(Const.PassParam.AGGREMENT_ID, getIntent().getExtras().getString(Const.PassParam.AGGREMENT_ID));
//                        bundle.putString(Const.PassParam.SOURCE_ADDRESS, getIntent().getExtras().getString(Const.PassParam.SOURCE_ADDRESS));
//                        bundle.putString(Const.PassParam.DEST_ADDRESS, getIntent().getExtras().getString(Const.PassParam.DEST_ADDRESS));
//                        bundle.putString(Const.PassParam.PRICE, tvPrice.getText().toString());
//                        bundle.putString(Const.PassParam.CAR_MODEL_NAME, carModelName);
//                        bundle.putString(Const.PassParam.RECEIVER_NAME, receivrName);
//                        bundle.putString(Const.PassParam.RECEIVER_NUMBER, receiverNumber);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
                        // createANowRequest();
                        if(homepickup.isChecked()&&pickuplocation.getText().toString().isEmpty()){
                            Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasepickuplocation), Toast.LENGTH_SHORT).show();
                        }
                        else if(homedeleviry.isChecked()&&homelocation.getText().toString().isEmpty()){
                            Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasepickuplocation), Toast.LENGTH_SHORT).show();
                        }
                       else if (Const.SERVICE_TYPE.equalsIgnoreCase(Const.CarShip)) {


                            createCustomerNow();
                        } else if (Const.SERVICE_TYPE.equalsIgnoreCase(Const.HomeDelivery)) {
                            //goTorideLater();
                            createANowRequest();
                        }
                    } else if (cashCard.equalsIgnoreCase("card")) {
                        app_payment_method="card";

                            requestForPayfortPayment();}


                } else {
                    startActivity(new Intent(OrderSummaryActivity.this, ActivityLoginSigupOption.class));
                }
            }
        });
        tvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) tvCard.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) tvCash.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                cashCard = "card";
            }
        });
        tvCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) tvCash.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) tvCard.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                cashCard = "cash";
            }
        });
    }

    private void condtions() {
        Dialog dialog=new Dialog(OrderSummaryActivity.this,R.style.custom_dialogue);
        dialog.setContentView(R.layout.condtions);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Button comfirm;
        TextView title=dialog.findViewById(R.id.condtiontitle);
        title.setText(getString(R.string.pleaseconfirmthecondtionsandterms));
        comfirm=dialog.findViewById(R.id.confirm_conditions);
        WebView webView=dialog.findViewById(R.id.condtions);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        String pdf="";
        if(sharedPreferences.getString("language","").equals("ar")){
            pdf = "http://93.112.44.39/docs/agreement_ar.pdf";
            comfirm.setText("تاكيد");
            title.setText("برجاء الاطلاع علي الشروط الاحكام والموافقه عليها");
        }
        else{
            pdf = "http://93.112.44.39/docs/agreement_en.pdf";
            comfirm.setText("Confirm");
            title.setText("please confirm the condtions and prepare Documents");

        }

        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        setUpLocale();
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condtions.setChecked(true);
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }

    private void getPrice() {
        UiUtils.showLoadingDialog(OrderSummaryActivity.this);
        apiServices = RestClient.getApiService();
        apiServices1= clientcustom.getApiService();
if(serviceType.equals(getResources().getString(R.string.full_load))){
         shipmenttype="12";
        }
       else if(serviceType.equals(getResources().getString(R.string.special_towing))){
            shipmenttype="2";
        }
       else if(serviceType.equals(getResources().getString(R.string.closed))){
    shipmenttype="39";

}

       else if(serviceType.equals(getResources().getString(R.string.intercity))||serviceType.equals(getResources().getString(R.string.international))){
            shipmenttype="1";
        }
        create_init_order();
        Call<PriceResponse> priceResponseCall = apiServices.getPrice(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, ""),"individual",branchId,branchIdTo,date,"1",carModelID,maker_id,shipmenttype);
        priceResponseCall.enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {

                    if (response.body().getData() != null && !(response.body().getData().isEmpty())&&response.body().getData().size()>0){
                        if(response.body().getData().get(0).getWaypoint_from().optBoolean("has_satha_service")){
                            homepickuplayout.setVisibility(View.VISIBLE);
                        }
                        else{

                            homepickuplayout.setVisibility(View.GONE);
                        }
                        if(response.body().getData().get(0).getWaypoint_to().optBoolean("has_satha_service")){
                            homedelivirylayout.setVisibility(View.VISIBLE);
                        }
                        else{
                            homedelivirylayout.setVisibility(View.GONE);
                        }
                        inthomedelivery=response.body().getData().get(0).getOtherServices().get(0).getPrice();
                        inthomepickup=response.body().getData().get(0).getOtherServices().get(1).getPrice();
                        intsmall=response.body().getData().get(0).getOtherServices().get(4).getPrice();
                        intmedium=response.body().getData().get(0).getOtherServices().get(3).getPrice();
                        intlarge=response.body().getData().get(0).getOtherServices().get(2).getPrice();

                        //  Toast.makeText(OrderSummaryActivity.this, "resopne"+String.valueOf(response.body().getData()) , Toast.LENGTH_LONG).show();
                        originalprice=response.body().getData().get(0).getPrice();
                        taxes=(Double.parseDouble(response.body().getData().get(0).gettaxes().toString()));
                        tvPrice.setText(response.body().getData().get(0).getPrice()+"SR");
                        tvTotalCost.setText(Double.toString(taxes)+"%");
                        shapmentdate.setText(date+":00");
                        expacteddate.setText(response.body().getData().get(0).getExpected_delivery_date());
                        payPrice = String.valueOf(response.body().getData().get(0).getPrice());
                        prefUtils.setValue(PrefKeys.PRICE, String.valueOf(response.body().getData().get(0).getPrice()) + "SR");
                        btnNext.setText(getString(R.string.total_cost)+" : "+(totalcost+originalprice+(originalprice*taxes/100.0))+"SR");
                        app_paid_amount=Double.toString(totalcost+originalprice+(originalprice*taxes/100.0));
                }
                   else{ Dialog dialog = new Dialog(OrderSummaryActivity.this,R.style.custom_dialogue);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.nullprice);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    Button button=dialog.findViewById(R.id.returntomenu);
                    button.setOnClickListener(i->{
                        startActivity(new Intent(OrderSummaryActivity.this,MainActivity.class));
                        finishAffinity();});
                    dialog.show();

                   }


                }
else{
                    Dialog dialog = new Dialog(OrderSummaryActivity.this,R.style.custom_dialogue);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.nullprice);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    Button button=dialog.findViewById(R.id.returntomenu);
                    button.setOnClickListener(i->{
                        startActivity(new Intent(OrderSummaryActivity.this,MainActivity.class));
                        finishAffinity();});
                    dialog.show();

                }
                if(expacteddate.getText()==null || expacteddate.getText().toString().isEmpty()
                        ||expacteddate.getText().toString().equals("")){
                    Dialog dialog = new Dialog(OrderSummaryActivity.this,R.style.custom_dialogue);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.nullprice);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    Button button=dialog.findViewById(R.id.returntomenu);
                    button.setOnClickListener(i->{
                        startActivity(new Intent(OrderSummaryActivity.this,MainActivity.class));
                        finishAffinity();
                        dialog.show();

                    });

                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                Dialog dialog = new Dialog(OrderSummaryActivity.this,R.style.custom_dialogue);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.nullprice);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                Button button=dialog.findViewById(R.id.returntomenu);
                button.setOnClickListener(i->{
                    startActivity(new Intent(OrderSummaryActivity.this,MainActivity.class));
                    finishAffinity();
                    dialog.show();

                });

            }
        });
    }

    private void getrecivernationality() {
        Gson gson=new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(ownernationallity.equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    customer_nationality=dataResponse.getData().get(0).getCountries().get(k).getId().toString();
                }

            }}
        else{
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(ownernationallity.equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    customer_nationality=dataResponse.getData().get(0).getCountries().get(k).getId().toString();
                }

            }

        }
    }

    private void createOrderNow(String customer_id ) {
        getrecivernationality();
        getownernationality();
        app_paid_amount=Double.toString(totalcost+originalprice+(originalprice*taxes/100.0));
          OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("access-token", "access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
                            .addHeader("Accept", "application/x-www-form-urlencoded")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", "session_id=f0c720f5a3111378ba552f52b42ef323454fef4e")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();
        apiServices = RestClient.getApiService();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.Bassami_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<CreateOrderResponse> orderResponseCall = apiServices1.createOrder(customer_id,
                shipmenttype, branchId, branchIdTo, maker_id, carModelID,piateNumber,carSizeID, platetype,
                "12345", "6",receivrName
                ,countryid
                ,receiverNumber
                , getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER)
                , "1"
                , "2"
                ,is_home_pickup
                ,is_home_delivery
                ,home_location
                ,pickup_location
                ,is_large_box
                ,is_medium_box
                ,is_small_box,app_payment_method,app_paid_amount,app_fortid,ownerName,id_typenumber,"1"
                ,owneridcard,nationality_id,
                order_ref_backend);
        orderResponseCall.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                   if (response.body().getData() != null ) {
                        if (response.body().getData().get(0).getOrderId() != null) {
                            updateorder(response.body().getData().get(0).getOrderRef());
                            Intent intent = new Intent(OrderSummaryActivity.this, ThankYouActivity.class);
                            intent.putExtra("order_id", response.body().getData().get(0).getOrderRef());
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                 else if(response.errorBody()!=null) {
                    Toast.makeText(OrderSummaryActivity.this,getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();

            }
        });


    }

    private void updateorder(String orderRef) {
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
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.update_order(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)),order_ref_backend, "123456", is_home_pickup,is_home_delivery,home_location,
                pickup_location
                ,is_large_box
                , is_medium_box
                , is_small_box
                , app_payment_method
                ,visapaid
                ,app_fortid
                ,orderRef
                ,home_locationlat
                ,home_locationlong
                ,pickup_locationlat,pickup_locationlong,
                app_paid_amount

        );
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful()) {

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });
    }

    private void getownernationality() {
        Gson gson=new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        for(int k=0; k<dataResponse.getData().get(0).getPlate_types().size();k++){
            if(platetype.equals(dataResponse.getData().get(0).getPlate_types().get(k).getName_en())){
                platetype=dataResponse.getData().get(0).getPlate_types().get(k).getId().toString();
            }
        }

        for(int k=0; k<dataResponse.getData().get(0).getPlate_types().size();k++){
            if(platetype.equals(dataResponse.getData().get(0).getPlate_types().get(k).getName_ar())){
                platetype=dataResponse.getData().get(0).getPlate_types().get(k).getId().toString();
            }
        }
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(nationality_id.equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    nationality_id=dataResponse.getData().get(0).getCountries().get(k).getId().toString();
                }
            }

            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(nationality_id.equals(dataResponse.getData().get(0).getCountries().get(k).getName_ar())){
                    nationality_id=dataResponse.getData().get(0).getCountries().get(k).getId().toString();
                }
        }
            for(int k=0; k<dataResponse.getData().get(0).getId_card_types().size();k++){
                if(id_typenumber.equals(dataResponse.getData().get(0).getId_card_types().get(k).toString().substring(dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("name_ar")+8,
                        dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("}")))){
                        id_typenumber=dataResponse.getData().get(0).getId_card_types().get(k).toString().substring(dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("code")+5,
                                dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("name_en")-2);
                }

            }
            for(int k=0; k<dataResponse.getData().get(0).getId_card_types().size();k++){
                if(id_typenumber.equals(dataResponse.getData().get(0).getId_card_types().get(k).toString().substring(dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("name_en")+8,
                        dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("name_ar")-2))){
                    id_typenumber=dataResponse.getData().get(0).getId_card_types().get(k).toString().substring(dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(k).toString().indexOf("name_en")-2);
                }



        }
    }


    private void createOrderNow1(String customer_id ) {
        /* Call<CreateOrderResponse> orderResponseCall = apiServices1.createOrder1(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)),customer_id
                , branchId, branchIdTo,"1",shipmenttype ,maker_id,"hyundai", carModelID,carModelName,carSize, "1",piateNumber
                , "6",receivrName
                ,"سعودي"
                ,"6"
                ,receiverNumber
                , getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER)
                ,is_home_pickup
                ,is_home_delivery
                ,home_location
                ,pickup_location
                ,is_large_box
                ,is_medium_box
                ,is_small_box,app_payment_method,app_paid_amount,app_fortid);*/
       /*  app_paid_amount=Double.toString(totalcost+originalprice+taxes);
        OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(3, TimeUnit.MINUTES).writeTimeout(3, TimeUnit.MINUTES).readTimeout(3, TimeUnit.MINUTES).connectTimeout(3, TimeUnit.MINUTES).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://93.112.44.39/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServices  apiServices1=APIClient.getClient().create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.createOrder1(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),"277","115456"
                , "138", "128","1","fffdgdfgdfg" ,"1","hyundai", "1","maker name","size", "2","swe 123"
                , "6","receiver_name"
                ,"سعودي"
                ,"6"
                ,"0543681718"
                , "2234568795"
                ,"true"
                ,"true"
                ,"home location value"
                ,"pickup location value"
                ,"10"
                ,"5"
                ,"2","credit","5135","3432432432434");
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {


                            createOrderNow(customer_id);

                        }
                else if(response.errorBody()!=null) {
                    Toast.makeText(OrderSummaryActivity.this,getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        */
      /* OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("token", prefUtils.getStringValue(PrefKeys.SESSION_TOKEN,""))
                .addFormDataPart("id", Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)))
                .addFormDataPart("customer", customer_id)
                .addFormDataPart("loc_from", branchId)
                .addFormDataPart("loc_to", branchIdTo)
                .addFormDataPart("towing_id", "1")
                .addFormDataPart("shippment_type", shipmenttype)
                .addFormDataPart("car_make_id", maker_id)
                .addFormDataPart("car_make", "hjk")
                .addFormDataPart("car_model_id", carModelID)
                .addFormDataPart("car_model", carModelName)
                .addFormDataPart("car_size", carSize)
                .addFormDataPart("plate_type", "others")
                .addFormDataPart("none_saudi_plate_no", piateNumber)
                .addFormDataPart("chassis", "123456")
                .addFormDataPart("receiver_name", receivrName)
                .addFormDataPart("receiver_nationality", "سعودي")
                .addFormDataPart("payment_method", "6")
                .addFormDataPart("receiver_mob_no", "0543681718")
                .addFormDataPart("receiver_id_card_no", "2234568795")
                .addFormDataPart("is_home_pickup", "true")
                .addFormDataPart("is_home_delivery", "true")
                .addFormDataPart("home_location", "home location value")
                .addFormDataPart("pickup_location", "pickup location value")
                .addFormDataPart("large_boxes", "10")
                .addFormDataPart("medium_boxes", "5")
                .addFormDataPart("small_boxes", "2")
                .addFormDataPart("app_payment_method", "credit")
                .addFormDataPart("app_paid_amount", "5135")
                .addFormDataPart("app_fortid", "3432432432434")
                .build();
        Request request = new Request.Builder()
                .url("93.112.44.39/api/create_order?")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();
        AsyncTask asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                okhttp3.@NotNull Response response=null;
                try {
                     response = client.newCall(request).execute();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return response;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(o!=null){
                    createOrderNow(customer_id);
                }
            }
        };
       asyncTask.execute();*/


    }

    private void createCustomerNow() {
        getrecivernationality();
        UiUtils.showLoadingDialog(OrderSummaryActivity.this);
        apiServices = RestClient.getApiService();

        Call<CreateOrderResponse> orderResponseCall = apiServices.createCustomer(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, "")
                ,prefUtils.getStringValue(PrefKeys.NATIONAL_ID, ""),
                prefUtils.getStringValue(APIConsts.Params.NAME, ""), prefUtils.getStringValue(APIConsts.Params.PHONE, "")
        ,customer_nationality);
        orderResponseCall.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null && !(response.body().getData().isEmpty())) {
                        if (response.body().getData().get(0).getCustomer_id() != null) {
                            createOrderNow(response.body().getData().get(0).getCustomer_id().toString());
                        } else {
                            UiUtils.hideLoadingDialog();

                        }
                    } else {
                        UiUtils.hideLoadingDialog();

                    }
                } else {
                    UiUtils.hideLoadingDialog();

                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                Toast.makeText(OrderSummaryActivity.this, "interneet", Toast.LENGTH_SHORT).show();

            }
        });


    }



    protected void createANowRequest() {
        UiUtils.showLoadingDialog(OrderSummaryActivity.this);
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
                                Toast.makeText(OrderSummaryActivity.this, createRequestResponse.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(OrderSummaryActivity.this, ActivityWaitingRequestAccept.class);
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
                            bundle.putString(Const.PassParam.PRICE, tvPrice.getText().toString());
                            bundle.putString(Const.PassParam.CAR_MODEL_NAME, carModelName);
                            bundle.putString(Const.PassParam.RECEIVER_NAME, receivrName);
                            bundle.putString(Const.PassParam.RECEIVER_NUMBER, receiverNumber);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } else {
                            try {
                                if (createRequestResponse.getString(Const.Params.ERROR_MSG) != null) {
                                    Toast.makeText(OrderSummaryActivity.this, createRequestResponse.getString(Const.Params.ERROR_MSG), Toast.LENGTH_LONG).show();
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
                            cancelCreateRequest();
                            if (createRequestResponse.optInt(APIConsts.Params.ERROR_CODE) == APIConsts.ErrorCodes.WALLETEMPTY) {
                                Intent walletIntent = new Intent(OrderSummaryActivity.this, WalletAcivity.class);
                                startActivity(walletIntent);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    UiUtils.hideLoadingDialog();
                    if (NetworkUtils.isNetworkConnected(OrderSummaryActivity.this)) {
                        UiUtils.showShortToast(OrderSummaryActivity.this, getString(R.string.may_be_your_is_lost));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cancelCreateRequest() {
        UiUtils.showLoadingDialog(OrderSummaryActivity.this);
        Call<String> call = apiInterface.cancelRequest(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject cancelResponse = null;
                try {
                    cancelResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                if (NetworkUtils.isNetworkConnected(OrderSummaryActivity.this)) {
                    UiUtils.showShortToast(OrderSummaryActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();
        if (!TextUtils.isEmpty(payPrice)) {
            payFortData.amount = String.valueOf((int) (Float.parseFloat(Double.toString(totalcost+originalprice+(originalprice*taxes/100.0))) * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = prefUtils.getStringValue(PrefKeys.Phone, "")+"@albassamitransport.com";
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());
            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            sendtoodoo(payFortData,payFortPayment);

        }
    }

    private void sendtoodoo(PayFortData payFortData, PayFortPayment payFortPayment) {
        UiUtils.showLoadingDialog(OrderSummaryActivity.this);
        OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("access-token","access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
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
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.fortdata(order_id_backend,
               Double.toString(totalcost+originalprice+(originalprice*taxes/100.0)),
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
                Toast.makeText(OrderSummaryActivity.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }

            if (requestCode == 11) {
                if (resultCode == RESULT_OK) {
                    homelocation.setText(data.getData().toString());
                    home_location=data.getExtras().getString("latlang");
                    home_locationlat=data.getExtras().getString("lat");
                    home_locationlong=data.getExtras().getString("lang");

                }}
        else if (requestCode == 13) {
            if (resultCode == RESULT_OK) {
                pickuplocation.setText(data.getData().toString());
                pickup_location=data.getExtras().getString("latlang");
                pickup_locationlat=data.getExtras().getString("lat");
                pickup_locationlong=data.getExtras().getString("lang");


            }}
            else if (requestCode == 15) {
                if (resultCode == RESULT_OK) {
                   homelocation1.setText(data.getData().toString());
                    home_location=data.getData().toString();

                }}
            else if (requestCode == 16) {
                if (resultCode == RESULT_OK) {
                    pickuplocation1.setText(data.getData().toString());
                    pickup_location=data.getData().toString();

                }}

    }
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
            app_fortid=responseData.fortId;
            app_payment_method="credit";
            visapaid=app_paid_amount;
            createCustomerNow();


        }
    }



    private void goTorideLater() {
        Intent intent = new Intent(OrderSummaryActivity.this, RideLaterActivity.class);
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
        bundle.putString(Const.PassParam.AGGREMENT_ID, getIntent().getExtras().getString(Const.PassParam.AGGREMENT_ID));
        bundle.putString(Const.PassParam.SOURCE_ADDRESS, getIntent().getExtras().getString(Const.PassParam.SOURCE_ADDRESS));
        bundle.putString(Const.PassParam.DEST_ADDRESS, getIntent().getExtras().getString(Const.PassParam.DEST_ADDRESS));
        bundle.putString(Const.PassParam.TOWING_TYPE, getIntent().getExtras().getString(Const.PassParam.TOWING_TYPE));
        bundle.putString(Const.PassParam.PRICE, tvPrice.getText().toString());
        bundle.putString(Const.PassParam.CAR_MODEL_NAME, carModelName);
        bundle.putString(Const.PassParam.RECEIVER_NAME, receivrName);
        bundle.putString(Const.PassParam.RECEIVER_NUMBER, receiverNumber);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}