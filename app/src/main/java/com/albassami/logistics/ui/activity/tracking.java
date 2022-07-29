package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class tracking extends AppCompatActivity {
ImageView btnBack;
Button tracking;
EditText agreeement,receivernumber;
APIInterface apiInterface;
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
        setContentView(R.layout.activity_tracking);
        btnBack=findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(i->onBackPressed());
        tracking=findViewById(R.id.tracking);
        agreeement=findViewById(R.id.agrement);
        receivernumber=findViewById(R.id.receivernumber);
        tracking.setOnClickListener(i->checkdate());
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180);}

    }

    private void checkdate() {
        UiUtils.showLoadingDialog(tracking.this);
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("access-token", "access_token_770b27a4c5deac5f11c8cff387a49c533b3071d8")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", "session_id=2113bd21b6756fe4275ad5f367b3ec13470f9856")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://albassami-pre-production-1489044.dev.odoo.com/api/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiServices1=retrofit.create(APIInterface.class);
Call<String> call=apiServices1.trackshipment(agreeement.getText().toString(),receivernumber.getText().toString());
call.enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        UiUtils.hideLoadingDialog();
        if(response.isSuccessful()&& response!=null){
            JSONObject alldata = null;
            try {
                alldata = new JSONObject(response.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(alldata!=null){
                JSONArray data=alldata.optJSONArray("data");
               if(data!=null){
                       JSONObject trackingdata= data.optJSONObject(0);
                       if(trackingdata!=null){
                           JSONObject car_maker =null;
                           JSONArray otherservice =null;
                           JSONObject homepickup =null;
                           JSONObject homedelivery =null;
                           JSONObject small =null;
                           JSONObject medium =null;
                           JSONObject large =null;
                           String carmaker="غير معرف";
                           String strhomepickup="غير مفعله";
                           String strhomedelivery="غير مفعله";
                           String strsmall="0";
                           String strmedium="0";
                           String strlarge="0";

                          try{
                            car_maker=trackingdata.optJSONObject("car_maker");
                            otherservice=trackingdata.optJSONArray("other_services");
                            homepickup=otherservice.optJSONObject(0);
                            homedelivery=otherservice.optJSONObject(1);
                            small=otherservice.optJSONObject(2);
                            medium=otherservice.optJSONObject(3);
                            large=otherservice.optJSONObject(4);} catch (Exception e) {
                              e.printStackTrace();
                          }
                          if(car_maker!=null){

                              carmaker=trackingdata.optString("car_make");
                          }
                           if(homepickup!=null){
                               strhomepickup=Double.toString(homepickup.optDouble("pickup_location"));
                           }
                           if(homedelivery!=null){
                               strhomedelivery=Double.toString(homedelivery.optDouble("home_location"));
                           }
                         /*  if(small!=null){
                               if(small.optString("service").equals("Large Box")){
                                   strlarge=Double.toString(large.optDouble("qty"));
                               }
                               else if(small.optString("service").equals("Medium Box")){
                                   strmedium=Double.toString(medium.optDouble("qty"));
                               }
                               else if(small.optString("service").equals("Small Box")){
                                   strsmall=Double.toString(small.optDouble("qty"));
                               }

                           }
                           if(medium!=null){
                               if(medium.optString("service").equals("Large Box")){
                                   strlarge=Double.toString(large.optDouble("qty"));}
                               else if(medium.optString("service").equals("Medium Box")){
                                   strmedium=Double.toString(medium.optDouble("qty"));}
                               else if(medium.optString("service").equals("Small Box")){
                                   strsmall=Double.toString(small.optDouble("qty"));
                               }

                           }
                           if(large!=null){
                               if(large.optString("service").equals("Large Box")){
                                   strlarge=Double.toString(large.optDouble("qty"));}
                               else if(large.optString("service").equals("Medium Box")){
                                   strmedium=Double.toString(medium.optDouble("qty"));}
                               else if(large.optString("service").equals("Small Box")){
                                   strsmall=Double.toString(small.optDouble("qty"));
                               }
}*/
                           for(int y=2 ;y<5;y++){
                               try{ if(otherservice.optJSONObject(y).optString("service").equals("Large Box")){
                                   strlarge="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")"+otherservice.optJSONObject(y).optDouble("cost");
                               }
                               else if(otherservice.optJSONObject(y).optString("service").equals("Medium Box")){
                                   strmedium="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")"+otherservice.optJSONObject(y).optDouble("cost");
                               }
                               else if(otherservice.optJSONObject(y).optString("service").equals("Small Box")){
                                   strsmall="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")"+otherservice.optJSONObject(y).optDouble("cost");
                               }


                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }

                           Intent intent=new Intent(tracking.this,agreementactivity.class);
                           intent.putExtra("order_ref",trackingdata.optString("order_ref"));
                           intent.putExtra("receiver_name",trackingdata.optString("receiver_name"));
                           intent.putExtra("receiver_mob_no",trackingdata.optString("receiver_phone"));
                           intent.putExtra("customer_name",trackingdata.optString("sender_name"));
                           intent.putExtra("order_date",trackingdata.optString("order_date"));
                           intent.putExtra("service_type",trackingdata.optString("service_type"));

                           intent.putExtra("loc_from",trackingdata.optJSONObject("loc_from").optString("name"));
                           intent.putExtra("loc_to",trackingdata.optJSONObject("loc_to").optString("name"));

                           intent.putExtra("expected_delivery",trackingdata.optString("expected_delivery_date"));
                           intent.putExtra("car_model",car_maker.optString("name_ar"));
                           intent.putExtra("year",trackingdata.optString("year"));
                           intent.putExtra("car_make",carmaker);
                           intent.putExtra("state",trackingdata.optString("state"));
                           intent.putExtra("homepickup",strhomepickup);
                           intent.putExtra("homedelivery",strhomedelivery);
                           intent.putExtra("small",strsmall);
                           intent.putExtra("medium",strmedium);
                           intent.putExtra("large",strlarge);
                           intent.putExtra("total_amount",Double.toString(trackingdata.optDouble("total_amount")));
                           intent.putExtra("due_amount",Double.toString(trackingdata.optDouble("due_amount")));
                           intent.putExtra("paid_amount",Double.toString(trackingdata.optDouble("paid_amount")));
                           intent.putExtra("service_type",trackingdata.optString("service_type"));
                            
                           startActivity(intent);



                       }
                       else{
                           Toast.makeText(tracking.this, getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


                       }
               }
               else{
                   Toast.makeText(tracking.this, getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


               }
            } else{
                Toast.makeText(tracking.this, getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


            }

        }
        else{
            Toast.makeText(tracking.this, getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        UiUtils.hideLoadingDialog();
        Toast.makeText(tracking.this, getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
    }
});

    }
}
