package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.Utils.PreferenceHelper;
import com.albassami.logistics.dto.response.AuthTokenResponse;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.ui.Adapter.SpinnerAdapter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 1/3/2017.
 */

public class GetStartedActivity extends AppCompatActivity {
    @BindView(R.id.sp_country_reg)
    Spinner spCountryReg;
    @BindView(R.id.welcome_btn)
    CustomRegularTextView welcomeBtn;
    PrefUtils prefUtils;
    private ApiServices apiServices;
    private SpinnerAdapter adapter_language;
CustomRegularTextView arbic;
    CustomRegularTextView eng;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        ButterKnife.bind(this);
        setUpLocale();

arbic=findViewById(R.id.welcome_btn2);
arbic.setOnClickListener(i->{
    setLocale("ar");
    getAuthToken();});
        eng=findViewById(R.id.welcome_btn);
        eng.setOnClickListener(i->{
            setLocale("");
            getAuthToken();});
    }





    public void setUpLocale() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        sharedPreferences=getSharedPreferences("lang",MODE_PRIVATE);
            Locale myLocale = null;
            switch (sharedPreferences.getString("language","")) {
                case "":
                    myLocale = new Locale("");
                    break;
                case "ar":
                    myLocale = new Locale("ar");
                    break;

            }
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            this.getResources().updateConfiguration(config,this.getResources().getDisplayMetrics());


        String[] lst_currency = getResources().getStringArray(R.array.language);
        Integer[] currency_imageArray = {null, R.drawable.ic_united_states};

        adapter_language = new SpinnerAdapter(this, R.layout.spinner_value_layout, lst_currency, currency_imageArray);
        spCountryReg.setAdapter(adapter_language);
        if (!TextUtils.isEmpty(new PreferenceHelper(this).getLanguage())) {

            switch (sharedPreferences.getString("language","")) {
                case "":
                    spCountryReg.setSelection(0, false);
                    break;
                case "ar":
                    spCountryReg.setSelection(1, false);

                    break;


            }

        }
        spCountryReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        new PreferenceHelper(GetStartedActivity.this).putLanguage("");
                        break;
                    case 1:
                        new PreferenceHelper(GetStartedActivity.this).putLanguage("en");
                        setLocale("en");
                        break;
                    case 2:
                        new PreferenceHelper(GetStartedActivity.this).putLanguage("fr");
                        setLocale("fr");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
       Configuration config = new Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
       // new PreferenceHelper(this).getLanguage();
        sharedPreferences=getSharedPreferences("lang",MODE_PRIVATE);
        sharedPreferences.edit().putString("language",lang).apply();
       // Intent refresh = new Intent(this, GetStartedActivity.class);
        //startActivity(refresh);
     //   this.overridePendingTransition(0, 0);
    }

    @OnClick(R.id.welcome_btn)
    public void onViewClicked() {
                setLocale("");
        getAuthToken();
    }



    private void getAuthToken() {
        UiUtils.showLoadingDialog(GetStartedActivity.this);
        apiServices = RestClient.getApiService();
        Call<AuthTokenResponse> authTokenResponseCall = apiServices.getAuthToken("admin", "odoo", "albassami-pre-production-1489044");
        authTokenResponseCall.enqueue(new Callback<AuthTokenResponse>() {
            @Override
            public void onResponse(Call<AuthTokenResponse> call, Response<AuthTokenResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getAccessToken() != null) {
                        PrefHelper.setAuthToken(GetStartedActivity.this, response.body().getAccessToken());

                    }
                }
                getPriceData();

            }

            @Override
            public void onFailure(Call<AuthTokenResponse> call, Throwable t) {
                Toast.makeText(GetStartedActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
              //  Intent i = new Intent(getApplicationContext(), onBordingScreen.class);
                //startActivity(i);
                //GetStartedActivity.this.finish();
            }
        });

    }
    private void getPriceData(){
        apiServices = RestClient.getApiService();
        Call<GetPriceDataResponse> priceDataResponseCall = apiServices.getPriceData(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN,""));
        priceDataResponseCall.enqueue(new Callback<GetPriceDataResponse>() {
            @Override
            public void onResponse(Call<GetPriceDataResponse> call,
                                   Response<GetPriceDataResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() !=null){
                    PrefHelper.setBranchesListData(GetStartedActivity.this,response.body());
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
                GetStartedActivity.this.finish();
            }}

            @Override
            public void onFailure(Call<GetPriceDataResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                Toast.makeText(GetStartedActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
              //  Intent i = new Intent(getApplicationContext(), onBordingScreen.class);
            //    startActivity(i);
             //   GetStartedActivity.this.finish();
            }
        });
    }
}
