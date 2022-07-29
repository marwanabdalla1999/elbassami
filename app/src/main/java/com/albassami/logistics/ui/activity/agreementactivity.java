package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;

import com.albassami.logistics.dto.response.CreateOrderResponse;
import com.albassami.logistics.dto.response.PriceResponse;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.payfort.IPaymentRequestCallBack;
import com.albassami.logistics.payfort.PayFortData;
import com.albassami.logistics.payfort.PayFortPayment;
import com.payfort.fort.android.sdk.base.callbacks.FortCallBackManager;
import com.payfort.fort.android.sdk.base.callbacks.FortCallback;
import com.squareup.okhttp.Request;
import com.albassami.logistics.R;
import java.util.Locale;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class agreementactivity extends AppCompatActivity  implements IPaymentRequestCallBack {
TextView agreementnumber,reciever,recievernumber,carmodel,source,to,shipmentdate,expacteddate,customername,homedelivery,homepickup,small,medium,large
        ,piad,due,total,servicename;
    PrefUtils prefUtils;
String stragreementnumber,strreciever,strrecievernumber,strcarmodel,strcarmaker,strcarcolor,strstatus,strsource,strto,strshipmentdate,strexpacteddate,strcustomername
   , strhomedelivery,strhomepickup,strsmall,strmedium,strlarge
        ,strpiad,strdue,strtotal;
ImageView btnback,statusimg,checked,checked1;
Button cancel,pay;
    ApiServices apiServices;
    Dialog dialog;
    private agreementactivity activity;


    public FortCallBackManager fortCallback = null;

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
        setContentView(R.layout.activity_agreementactivity);
        init();
        getstatus();
        btnback.setOnClickListener(i->onBackPressed());
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnback.setRotation(180); }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!due.getText().toString().equals("0.0") ){


                    requestForPayfortPayment();

                }
                else{
                    Toast.makeText(agreementactivity.this, getString(R.string.agreementhasbeenpayedalready), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(piad.getText().toString().equals("0.0") & strstatus.equals("draft") |strstatus.equals("registered")  ){

                    AlertDialog.Builder builder = new AlertDialog.Builder(agreementactivity.this);
                    builder.setTitle(getString(R.string.confirm));
                    builder.setMessage(getString(R.string.dialog_exit_text));

                    builder.setNegativeButton(getString(R.string.txt_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton(getString(R.string.txt_yes), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            cancel_order();
                            // dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

              //    cancel_order();
               }
              else{
                  Toast.makeText(agreementactivity.this, getString(R.string.youcantcancelorder), Toast.LENGTH_SHORT).show();
              }

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setuplocale();
    }

    private void showcanceldailog() {

      /*  dialog = new Dialog(activity, R.style.DialogThemeforview);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.logout_dialog);
        TextView btn_logout_yes = dialog.findViewById(R.id.btn_logout_yes);
        btn_logout_yes.setOnClickListener(view -> cancel_order());
        TextView btn_logout_no = dialog.findViewById(R.id.btn_logout_no);
        btn_logout_no.setOnClickListener(view -> dialog.dismiss());
        dialog.show();*/

    }

    private void setuplocale() {
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

    private void requestForPayfortPayment() {
        PayFortData payFortData = new PayFortData();
        if (!TextUtils.isEmpty(due.getText().toString())) {
            payFortData.amount = String.valueOf((int) (Float.parseFloat(due.getText().toString()) * 100));// Multiplying with 100, bcz amount should not be in decimal format
            payFortData.command = PayFortPayment.PURCHASE;
            payFortData.currency = PayFortPayment.CURRENCY_TYPE;
            payFortData.customerEmail = prefUtils.getStringValue(PrefKeys.Phone, "")+"@albassami.com";
            payFortData.language = PayFortPayment.LANGUAGE_TYPE;
            payFortData.merchantReference = String.valueOf(System.currentTimeMillis());

            PayFortPayment payFortPayment = new PayFortPayment(this, this.fortCallback, this);
            payFortPayment.requestForPayment(payFortData);
        }
    }
    private void cancel_order() {
        UiUtils.showLoadingDialog(agreementactivity.this);
        apiServices = RestClient.getApiService();

        Call<String> orderResponseCall = apiServices.cancelorder(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, ""),agreementnumber.getText().toString());
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                           startActivity(new Intent(agreementactivity.this,HistoryActivity.class));
                    Toast.makeText(agreementactivity.this, getString(R.string.agreementhasbeencanceled), Toast.LENGTH_SHORT).show();


                } else {
                    UiUtils.hideLoadingDialog();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                startActivity(new Intent(agreementactivity.this,HistoryActivity.class));

            }
        });

    }

    private void getstatus() {
        if(strstatus.equals("draft")){
            statusimg.setImageResource(R.drawable.sec);
        }
        if(strstatus.equals("registered")){
            statusimg.setImageResource(R.drawable.sec);
        }
        else if(strstatus.equals("done")){

            statusimg.setImageResource(R.drawable.fif);
        }
        else if(strstatus.equals("confirmed")){

            statusimg.setImageResource(R.drawable.sec);
        }
        else if(strstatus.equals("shipped")){

            statusimg.setImageResource(R.drawable.thir);
        }
        else if(strstatus.equals("Delivered")){

            statusimg.setImageResource(R.drawable.delivered);
        }
        else if(strstatus.equals("released")){

            statusimg.setImageResource(R.drawable.fif);
        }
        else if(strstatus.equals("awaiting")){

            statusimg.setImageResource(R.drawable.fri);
        }
        else if(strstatus.equals("on_transit")){

            statusimg.setImageResource(R.drawable.thir);
        }
        else if(strstatus.equals("cancel")){

            statusimg.setImageResource(R.drawable.fri);
        }
        else{
            statusimg.setImageResource(R.drawable.sec);
        }

    }

    private void init() {
        initilizePayFortSDK();
        prefUtils = PrefUtils.getInstance(this);
        apiServices = RestClient.getApiService();
        agreementnumber=findViewById(R.id.agreementnumber);
        pay=findViewById(R.id.pay);
        reciever=findViewById(R.id.tvReceiverName);
        recievernumber=findViewById(R.id.tvPhoneNumber);
        carmodel=findViewById(R.id.tvCarSize);
        source=findViewById(R.id.tvSource);
        to=findViewById(R.id.tvDestination);
        shipmentdate=findViewById(R.id.tvshapment_date);
        expacteddate=findViewById(R.id.tvexpacted_date);
        customername=findViewById(R.id.customername);
        btnback=findViewById(R.id.btnBack5);
        homedelivery=findViewById(R.id.homeddeleviry2);
        homepickup=findViewById(R.id.homepickup2);
        small=findViewById(R.id.small1);
        medium=findViewById(R.id.medium1);
        large=findViewById(R.id.large1);
        piad=findViewById(R.id.paidamount);
        due=findViewById(R.id.dueamount);
        total=findViewById(R.id.totalcoast);
        statusimg=findViewById(R.id.statusimgs);
        checked=findViewById(R.id.ischecked);
        checked1=findViewById(R.id.ischecked1);
        servicename=findViewById(R.id.service_name);
        cancel=findViewById(R.id.cancel);
        ///////////////////////////////////////////////////////
        stragreementnumber=getIntent().getStringExtra("order_ref");
        strreciever=getIntent().getStringExtra("receiver_name");
        strrecievernumber=getIntent().getStringExtra("receiver_mob_no");
        strcarmodel=getIntent().getStringExtra("car_model");
        strcarmaker=getIntent().getStringExtra("car_make");
        strcarcolor=getIntent().getStringExtra("year");
        strstatus=getIntent().getStringExtra("state");

        strsource=getIntent().getStringExtra("loc_from");
        strto=getIntent().getStringExtra("loc_to");

        strshipmentdate=getIntent().getStringExtra("order_date");
        strexpacteddate=getIntent().getStringExtra("expected_delivery");
        strcustomername=getIntent().getStringExtra("customer_name");
        strhomedelivery=getIntent().getStringExtra("homedelivery");
        strhomepickup=getIntent().getStringExtra("homepickup");
        strsmall=getIntent().getStringExtra("small");
        strmedium=getIntent().getStringExtra("medium");
        strlarge=getIntent().getStringExtra("large");
        strdue=getIntent().getStringExtra("due_amount");
        strtotal=getIntent().getStringExtra("total_amount");
        strpiad=getIntent().getStringExtra("paid_amount");
servicename.setText(getIntent().getStringExtra("service_type"));
        agreementnumber.setText(stragreementnumber);
        reciever.setText(strreciever);
        recievernumber.setText(strrecievernumber);
        carmodel.setText(strcarmodel);
        source.setText(strsource);
        to.setText(strto);
        shipmentdate.setText(strshipmentdate);
        expacteddate.setText(strexpacteddate);
        customername.setText(strcustomername);
        large.setText(strlarge);
        medium.setText(strmedium);
        small.setText(strsmall);
        due.setText(strdue);
        total.setText(strtotal);
        piad.setText(strpiad);
        if(strhomedelivery.equals("")||strhomedelivery.isEmpty()||strhomedelivery==null||strhomedelivery.equals("NaN")){
            checked.setImageResource(R.drawable.ic_clear_black_24dp);
            homedelivery.setText("0");
        }
        else{
            checked.setImageResource(R.drawable.ic_check_black_24dp);
            homedelivery.setText(getIntent().getStringExtra("homedeliveryprice"));
        }
        if(strhomepickup.equals("")||strhomepickup.isEmpty()||strhomepickup==null||strhomepickup.equals("NaN")){
            checked1.setImageResource(R.drawable.ic_clear_black_24dp);
            homepickup.setText("0");
        }
else{
            checked1.setImageResource(R.drawable.ic_check_black_24dp);

            homepickup.setText(getIntent().getStringExtra("homepickupprice"));
        }
            }

    @Override
    public void onPaymentRequestResponse(int responseType, PayFortData responseData) {
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

                paydue(responseData.fortId);

        }
    }

    private void paydue(String fortId) {
        UiUtils.showLoadingDialog(agreementactivity.this);
        apiServices = RestClient.getApiService();

        Call<String> orderResponseCall = apiServices.register_payment(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, ""),agreementnumber.getText().toString()
        ,due.getText().toString(),fortId);
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    startActivity(new Intent(agreementactivity.this,ThankYouActivity.class).putExtra("order_id",agreementnumber.getText().toString()));
                    Toast.makeText(agreementactivity.this,getString(R.string.paymenthasbeenSuccessfully), Toast.LENGTH_SHORT).show();


                } else {

                    startActivity(new Intent(agreementactivity.this,ThankYouActivity.class).putExtra("order_id",agreementnumber.getText().toString()));

                    Toast.makeText(agreementactivity.this,getString(R.string.paymenthasbeenSuccessfully), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                startActivity(new Intent(agreementactivity.this,ThankYouActivity.class).putExtra("order_id",agreementnumber.getText().toString()));
                Toast.makeText(agreementactivity.this,getString(R.string.paymenthasbeenSuccessfully), Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayFortPayment.RESPONSE_PURCHASE) {
            fortCallback.onActivityResult(requestCode, resultCode, data);
        }}
    private void initilizePayFortSDK() {
        fortCallback = FortCallback.Factory.create();
    }
}
