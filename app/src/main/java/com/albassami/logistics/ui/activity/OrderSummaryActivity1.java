package com.albassami.logistics.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CreateOrderResponse;
import com.albassami.logistics.dto.response.CreateOrderResponse2;
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
import com.google.android.gms.maps.model.LatLng;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import kong.unirest.HttpResponse;
//import kong.unirest.Unirest;

public class OrderSummaryActivity1 extends AppCompatActivity implements IPaymentRequestCallBack {
    String carSize,shipment_date, phoneNumber, receivrName, maker_id, carModelName, receiverNumber, cashCard = "", idNumber, piateNumber, carModel, serviceType, branchName, ownerName, branchId, carSizeID, carModelID, branchNameTo, branchIdTo;
    ArrayList<Integer> iconList;
    PaymentIconAdapter paymentIconAdapter;
    public FortCallBackManager fortCallback = null;
    ApiServices apiServices,apiServices1;
    TextView tvPhoneNumber, tvReceiverName, tvSenderName, tvSource, tvCarSize, tvDestination, tvService, tvPrice, tvCash, tvCard,shapmentdate;
    PrefUtils prefUtils;
    String payPrice = "",date="";
    Button btnNext;
    ImageView btnBack;
    APIInterface apiInterface;
    ArrayAdapter<String> numbersarr;
    TextView paymenttxt;
  String app_payment_method="",app_paid_amount="",app_fortid="";
String shipmenttype="";
String price="";
    String getReceiverNumber;
String countryid="";
LatLng from_lat_lang,to_lat_lang;
String payment_type="";
Double kmprice;
String with_return="";
CheckBox withreturnbox;

    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
            setContentView(R.layout.activity_order_summary1);

        inIT();




        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }

    }

    @Override
    protected void onStart() {
        super.onStart();
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


    private void inIT() {
        initilizePayFortSDK();
        serviceType = getIntent().getExtras().getString(Const.PassParam.SERVICE_TYPE);
        branchName = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME);
        ownerName = getIntent().getExtras().getString(Const.PassParam.OWNER_NAME);
        idNumber = getIntent().getExtras().getString(Const.PassParam.ID_NUMBER);
        phoneNumber = getIntent().getExtras().getString(Const.PassParam.PHONE_NUMBER);
        piateNumber = getIntent().getExtras().getString(Const.PassParam.PIATE_NUMBER);
        carSize = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE);
        carModel = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        branchId = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID);
        carSizeID = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE_ID);
        carModelID = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_ID);
        shipment_date = getIntent().getExtras().getString("Date");
        maker_id = getIntent().getExtras().getString(Const.PassParam.CAR_MAKER_ID);
        branchIdTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID_TO);
        branchNameTo = getIntent().getExtras().getString("to");
        receivrName = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NAME);
        receiverNumber = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NUMBER);
        carModelName = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        price=getIntent().getStringExtra("price");
        getReceiverNumber=getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER);
        date=getIntent().getExtras().getString("Date");
        prefUtils = PrefUtils.getInstance(this);
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
        withreturnbox=findViewById(R.id.withreturnbox);
        btnNext = findViewById(R.id.btnNext);
        shapmentdate=findViewById(R.id.tvshapment_date);
        from_lat_lang=getIntent().getExtras().getParcelable("lat_lang_from");
        to_lat_lang=getIntent().getExtras().getParcelable("lat_lang_to");
        iconList = new ArrayList<>();
        iconList.add(R.drawable.viza_card);
        iconList.add(R.drawable.master_card);
        iconList.add(R.drawable.apple_pay);
        with_return="false";
shapmentdate.setText(date);
withreturnbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            with_return="true";

        }
        else{
            with_return="false";
        }
    }
});
if(getIntent().getStringExtra("Shipmenttype").equals("20")){
withreturnbox.setVisibility(View.VISIBLE);

}
else{
    withreturnbox.setVisibility(View.GONE);
}
        paymenttxt=findViewById(R.id.paymenttxt);
        try{
        kmprice=Double.parseDouble(getIntent().getStringExtra("distance"))*Double.parseDouble(getIntent().getStringExtra("forkm"));
        }catch (Exception e){
            Toast.makeText(this, "cant calculate the price", Toast.LENGTH_SHORT).show();
        }
try {
    int val= (int) ((kmprice+Double.parseDouble(getIntent().getStringExtra("startwith")))*100);
    double price=val/100.00;
    tvPrice.setText(price+" SR");

} catch (Exception e) {
    e.printStackTrace();
}




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


            tvCarSize.setText(carModelName);
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

    }

  /*  private void getqunatatyofboxes() {
        small.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getcost();
            }
        });
    }*/
  public void createorder(){
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

      Call<String> call = apiServices2.createorder(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
              Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)),Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0))
      ,getIntent().getStringExtra("towing_id"),getIntent().getStringExtra("Shipmenttype"),getIntent().getStringExtra("carid"),payment_type,Double.toString(from_lat_lang.latitude),
              Double.toString(from_lat_lang.longitude),Double.toString(to_lat_lang.latitude),Double.toString(to_lat_lang.longitude)
      ,receivrName,getIntent().getExtras().getString(Const.PassParam.RECEIVER_COUNTRY),receiverNumber,getIntent().getExtras().getString(Const.PassParam.RECEIVER_NATIONAL_NUMBER),with_return,
              getIntent().getStringExtra("forkm"),getIntent().getStringExtra("time"),getIntent().getStringExtra("startwith"));
      call.enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
              UiUtils.hideLoadingDialog();
              if (response.isSuccessful()) {
                  JSONObject orderdata = null;
                  try {
                      orderdata = new JSONObject(response.body());
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
                  if (orderdata != null) {
                      JSONObject data = orderdata.optJSONObject("data");
                      if (data != null) {
                          Intent intent = new Intent(OrderSummaryActivity1.this, trackingtowing.class);
                          intent.putExtra("order_id", data.optString("order_ref"));
                          intent.putExtra("price", tvPrice.getText().toString());
                          intent.putExtra("startwith", getIntent().getStringExtra("startwith"));
                          intent.putExtra("forkm", getIntent().getStringExtra("forkm"));
                          startActivity(intent);
                          finish();
                      }
                  }
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


    private void onClickListners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment_type.equals("")){
                    Toast.makeText(OrderSummaryActivity1.this, getString(R.string.Pleaseselectpaymentmethod), Toast.LENGTH_SHORT).show();
                }
                else{
                createorder();}

            }
        });

        tvCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) tvCard.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) tvCash.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                payment_type = "card";
            }
        });
        tvCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) tvCash.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) tvCard.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                payment_type = "cash";
            }
        });
    }


    private void createOrderNow(String customer_id ) {
          OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("access-token", "access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
                            .addHeader("Accept", "application/x-www-form-urlencoded")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", "session_id=23bc3b767f211427f5b484d1e65b587b66149249")
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


    }
   private void createOrderNow1(String customer_id ) {
       Toast.makeText(this,prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "") , Toast.LENGTH_SHORT).show();
        //app_paid_amount=Double.toString(totalcost+originalprice+taxes);

        OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).connectTimeout(5, TimeUnit.MINUTES).build();
        apiServices = RestClient.getApiService();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL+"api/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.createOrder1(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),"277","115456"
                , "fffdgdfgdfg", "1","hkj","1", "maker name","size", "others","swe 123"
                , "123456","receiver_name"
                ,"سعودي"
                ,"0543681718"
                , "2234568795"
                ,"0"
                ,"0"
                ,"0","credit","5350","3432432432434"
        ,"13","13.54","351.54","325.45");
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                  //  if (response.body().getData() != null ) {
                   //     if (response.body().getData().get(0).getOrderId() != null) {
                            Toast.makeText(OrderSummaryActivity1.this, "tmaaaaaaaaaam", Toast.LENGTH_SHORT).show();
                            //createOrderNow1(customer_id);

                        }
                   // }
             //   }
                else if(response.errorBody()!=null) {
                    //Toast.makeText(OrderSummaryActivity1.this,getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();
                    Toast.makeText(OrderSummaryActivity1.this,response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderSummaryActivity1.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });


    }


    private void createCustomerNow() {
        UiUtils.showLoadingDialog(OrderSummaryActivity1.this);
        apiServices = RestClient.getApiService();
        Toast.makeText(this, "Second", Toast.LENGTH_SHORT).show();
        Call<CreateOrderResponse> orderResponseCall = apiServices.createCustomer(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, "")
                ,prefUtils.getStringValue(PrefKeys.NATIONAL_ID, ""),
                prefUtils.getStringValue(APIConsts.Params.NAME, ""), prefUtils.getStringValue(APIConsts.Params.PHONE, "")
        ,"");
        orderResponseCall.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null && !(response.body().getData().isEmpty())) {
                        if (response.body().getData().get(0).getCustomer_id() != null) {
                            createOrderNow1(response.body().getData().get(0).getCustomer_id().toString());
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
                Toast.makeText(OrderSummaryActivity1.this, "interneet", Toast.LENGTH_SHORT).show();

            }
        });


    }

    protected void createANowRequest() {
        UiUtils.showLoadingDialog(OrderSummaryActivity1.this);
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
                                Toast.makeText(OrderSummaryActivity1.this, createRequestResponse.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(OrderSummaryActivity1.this, ActivityWaitingRequestAccept.class);
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
                            bundle.putString("date", date);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                if (createRequestResponse.getString(Const.Params.ERROR_MSG) != null) {
                                    Toast.makeText(OrderSummaryActivity1.this, createRequestResponse.getString(Const.Params.ERROR_MSG), Toast.LENGTH_LONG).show();
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
                                Intent walletIntent = new Intent(OrderSummaryActivity1.this, WalletAcivity.class);
                                startActivity(walletIntent);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    UiUtils.hideLoadingDialog();
                    if (NetworkUtils.isNetworkConnected(OrderSummaryActivity1.this)) {
                        UiUtils.showShortToast(OrderSummaryActivity1.this, getString(R.string.may_be_your_is_lost));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cancelCreateRequest() {
        UiUtils.showLoadingDialog(OrderSummaryActivity1.this);
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
                if (NetworkUtils.isNetworkConnected(OrderSummaryActivity1.this)) {
                    UiUtils.showShortToast(OrderSummaryActivity1.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();
        if (!TextUtils.isEmpty(payPrice)) {
            payFortData.amount = String.valueOf((int) (Float.parseFloat(Double.toString(100.0)) * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = "hamdan@albassami.com";
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            payFortPayment.requestForPayment(payFortData);
        }
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
            if (Const.SERVICE_TYPE.equalsIgnoreCase(Const.CarShip)) {
                createCustomerNow();
            } else if (Const.SERVICE_TYPE.equalsIgnoreCase(Const.HomeDelivery)) {
                //  goTorideLater();
                createANowRequest();
            }

        }
    }



    private void goTorideLater() {
        Intent intent = new Intent(OrderSummaryActivity1.this, RideLaterActivity.class);
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