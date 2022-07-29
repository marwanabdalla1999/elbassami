package com.albassami.logistics.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.albassami.logistics.NewUtilsAndPref.AppUtils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.ui.Fragment.ForgotpassFragment;
import com.albassami.logistics.ui.Fragment.SignupFragment;

import org.json.JSONObject;

import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.albassami.logistics.network.ApiManager.APIConsts.Params;
import static com.albassami.logistics.network.ApiManager.APIConsts.Params.DATA;

/**
 * Created by user on 1/4/2017.
 */

public class SignInActivity extends AppCompatActivity {
    APIInterface apiInterface;
    PrefUtils prefUtils;
    @BindView(R.id.user_phone)
    CustomRegularEditView phone;
    @BindView(R.id.countrycode)
    AutoCompleteTextView code;
    @BindView(R.id.password)
    CustomRegularEditView password;
    @BindView(R.id.forgotPassword)
    CustomRegularTextView forgotPassword;
    @BindView(R.id.login)
    Button login;

    @BindView(R.id.signUplayout)
    LinearLayout signUpactivity;
    @BindView(R.id.inputLayoutPassword)
    TextInputLayout inputLayoutPassword;
    String[] codes={"966-SA"};
    String[] getcode;
    ArrayAdapter<String> codeadapter;
    public void setUpLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
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
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        inputLayoutPassword.setHintAnimationEnabled(false);
        inputLayoutPassword.setHint("");
        password.setHint(getString(R.string.password));
        codeadapter=new ArrayAdapter<>(this,R.layout.item_drop_down,codes);
        code.setAdapter(codeadapter);
        code.setOnClickListener(i-> {code.showDropDown();});
        code.setText(codes[0]);
      //  Spannable wordtoSpan = new SpannableString(getString(R.string.do_you_have_an_account_signup));
//        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       // signUp.setText(wordtoSpan);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean validateFields() {
        if (phone.getText().toString().length()<8) {
            UiUtils.showShortToast(this, getString(R.string.phonenumberis_invalide));
            return false;
        } else if (password.getText().toString().length() < 6) {
            UiUtils.showShortToast(this, getString(R.string.minimum_six_characters));
            return false;
        }
        return true;
    }


   protected void doLoginUser() {
        getcode = code.getText().toString().split("-", 2);
       SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
       UiUtils.showLoadingDialog(SignInActivity.this);
        Call<String> call = apiInterface.doMannualLogin("ar"
                ,phone.getText().toString()
                ,getcode[0]
                , password.getText().toString()
               );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject loginResponse = null;
                try {
                    loginResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (loginResponse != null) {
                   // if (loginResponse.optString("user_id")!=null) {
                        //JSONObject data = loginResponse.optJSONObject(DATA);
                        loginUserInDevice(loginResponse, APIConsts.Constants.MANUAL_LOGIN);
                        prefUtils.setValue(PrefKeys.IS_LOGGED_IN, true);
                    } else {
                        UiUtils.showShortToast(getApplicationContext(), getString(R.string.InvalidUserMobileNoorpassword));
                   // }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    public void loginUserInDevice(JSONObject data, String loginBy) {
        PrefHelper.setUserLoggedIn(this,data.optInt("user_id")
                , data.optString(Params.TOKEN)
                , loginBy
                ,getcode[0]
                , phone.getText().toString()
                , data.optString(Params.FIRSTNAME)+" "+data.optString(Params.LAST_NAME)
                , data.optString("national_id")
                , data.optString(Params.FIRSTNAME)
                , data.optString(Params.LAST_NAME)
                , data.optString(Params.PICTURE)
                , data.optString(Params.PAYMENT_MODE)
                , data.optString(Params.TIMEZONE)
              //  , data.optString(Params.MOBILE)
                , data.optString(Params.GENDER)
                ,data.optString("owner_nationality")
             ,data.optString("owner_id_type")
             //   , data.optString(Params.REFERRAL_CODE)
                //, data.optString(Params.REFERRAL_BONUS)
        );
        Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
      //  toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @OnClick({R.id.signUplayout, R.id.login, R.id.forgotPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUplayout:
                startActivity(new Intent(getApplicationContext(), sign_up1.class).putExtra("type","signup"));
                break;
            case R.id.login:
              //  startActivity(new Intent(getApplicationContext(), SelectLocationActivity.class));
               // finish();
                if (validateFields()) {
                    doLoginUser();
                }
                break;

            case R.id.forgotPassword:

                Intent forgotIntent = new Intent(getApplicationContext(), sign_up1.class).putExtra("type","forgetpassword");
                startActivity(forgotIntent);
                break;
        }
    }
}
