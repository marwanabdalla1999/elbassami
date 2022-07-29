package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.albassami.logistics.NewUtilsAndPref.AppUtils;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;

import org.json.JSONObject;

import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.albassami.logistics.network.ApiManager.APIConsts.Params.DATA;

/**
 * Created by Mahesh on 7/5/2017.
 */

public class SignUpNextActivity extends AppCompatActivity {

    int i = 0;
    CustomRegularEditView userFname;
    CustomRegularEditView userEmail;
    CustomRegularEditView userNationalID;
    CustomRegularEditView userPassword;
    CustomRegularEditView userReferralCode;
//    @BindView(R.id.applyRefCode)
//    CustomBoldRegularTextView applyRefCode;
    Button btnNext;
    TextInputLayout regPassword;
    private String mobile = "", countryCode;
    Unbinder unbinder;
    APIInterface apiInterface;
    PrefUtils prefUtils;
    TextView termsDesc;
    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup2);
//        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(SignUpNextActivity.this);
//        userFname.setFilters(new InputFilter[]{EMOJI_FILTER});
//        userLname.setFilters(new InputFilter[]{EMOJI_FILTER});
        if (getIntent().getExtras() != null) {
            mobile = getIntent().getStringExtra(Const.Params.PHONE);
            countryCode = getIntent().getStringExtra(APIConsts.Params.COUNTRY_CODE);
        }
        regPassword = findViewById(R.id.regPassword);
        userFname = findViewById(R.id.user_fname);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userNationalID = findViewById(R.id.user_national_id);
        userReferralCode = findViewById(R.id.user_referral_code);
        btnNext = findViewById(R.id.btn_next);
        termsDesc = findViewById(R.id.termsDesc);
        checkBox = findViewById(R.id.checkBox);
        regPassword.setHintAnimationEnabled(false);
        regPassword.setHint("");
        userPassword.setHint(getString(R.string.password));

        btnNext.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), checkBox.isChecked() ? R.color.colorAccent : R.color.dark_grey));
        btnNext.setEnabled(false);

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            btnNext.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), checkBox.isChecked() ? R.color.colorAccent : R.color.dark_grey));
            btnNext.setEnabled(b);
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields());
                   // doSignUpUser();
            }
        });

        Spannable termsAndConditionsSpan = new SpannableString(getString(R.string.please_accept_our_terms_and_conditions_to_proceed));
        termsAndConditionsSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i = new Intent(getApplicationContext(), HelpwebActivity.class);
                startActivity(i);
            }
        },18, 37, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsAndConditionsSpan.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i = new Intent(getApplicationContext(), HelpwebActivity.class);
                startActivity(i);
            }
        },42, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        termsDesc.setText(termsAndConditionsSpan);
//        termsDesc.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE  || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

//    @OnClick({R.id.btn_next})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.applyRefCode:
//                if (userReferralCode.getText().toString() != null && !userReferralCode.getText().toString().isEmpty())
//                    applyReferralCode();
//                break;
//            case R.id.btn_next:
//                if (validateFields())
//                    doSignUpUser();
//                break;
//        }
//    }

    private boolean validateFields() {
        if (userFname.getText().toString().length() == 0) {
            UiUtils.showShortToast(SignUpNextActivity.this, getString(R.string.enterFirstName));
            return false;
        }  else if (!AppUtils.isValidEmail(userEmail.getText().toString())) {
            UiUtils.showShortToast(SignUpNextActivity.this, getString(R.string.email_cant_be_empty));
            return false;
        } else if (userPassword.getText().toString().length() < 6) {
            UiUtils.showShortToast(SignUpNextActivity.this, getString(R.string.minimum_six_characters));
            return false;
        }
        return true;
    }

   /* protected void doSignUpUser() {
        UiUtils.showLoadingDialog(SignUpNextActivity.this);
        userReferralCode.getText().toString();
        Call<String> call = apiInterface.doSignUpUser(userFname.getText().toString()
                , userFname.getText().toString()
                , userEmail.getText().toString()
                , userPassword.getText().toString()
                , mobile
                , "1"
                , prefUtils.getStringValue(PrefKeys.FCM_TOKEN, "")
                , APIConsts.Constants.ANDROID
                , APIConsts.Constants.MANUAL_LOGIN
                , userNationalID.getText().toString()
                , TimeZone.getDefault().getID()
                , userReferralCode.getText().toString()
                , countryCode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject signUpResponse = null;
                try {
                    signUpResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (signUpResponse != null) {
                    if (signUpResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        UiUtils.showLongToast(SignUpNextActivity.this, signUpResponse.optString(APIConsts.Params.MESSAGE));
                        JSONObject data = signUpResponse.optJSONObject(DATA);
                        loginUserInDevice(data, APIConsts.Constants.MANUAL_LOGIN);
                        prefUtils.setValue(PrefKeys.IS_SOCIAL_LOGIN, false);
                    } else {
                        UiUtils.showShortToast(SignUpNextActivity.this, signUpResponse.optString(APIConsts.Params.ERROR));
                        if (signUpResponse.optInt(APIConsts.Params.ERROR_CODE) == APIConsts.ErrorCodes.NOT_APPROVED ) {
                            startActivity(new Intent(SignUpNextActivity.this, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }*/


    protected void applyReferralCode() {
        UiUtils.showLoadingDialog(SignUpNextActivity.this);
        Call<String> call = apiInterface.applyReferralCode(userReferralCode.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject signUpResponse = null;
                try {
                    signUpResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (signUpResponse != null) {
                    if (signUpResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        UiUtils.showShortToast(SignUpNextActivity.this, signUpResponse.optString(APIConsts.Params.MESSAGE));
                    } else {
                        UiUtils.showShortToast(SignUpNextActivity.this, signUpResponse.optString(APIConsts.Params.ERROR));
                    }
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

   /* public void loginUserInDevice(JSONObject data, String loginBy) {
        PrefHelper.setUserLoggedIn(this, data.optInt(APIConsts.Params.USER_ID)
                , data.optString(APIConsts.Params.TOKEN)
                , loginBy
                , data.optString(APIConsts.Params.EMAIL)
                , data.optString(APIConsts.Params.NAME)
                , data.optString(APIConsts.Params.NATIONAL_ID)
                , data.optString(APIConsts.Params.FIRSTNAME)
                , data.optString(APIConsts.Params.LAST_NAME)
                , data.optString(APIConsts.Params.PICTURE)
                , data.optString(APIConsts.Params.PAYMENT_MODE)
                , data.optString(APIConsts.Params.TIMEZONE)
                , data.optString(APIConsts.Params.MOBILE)
                , data.optString(APIConsts.Params.GENDER)
                , data.optString(APIConsts.Params.REFERRAL_CODE)
                , data.optString(APIConsts.Params.REFERRAL_BONUS));
        Intent toHome = new Intent(SignUpNextActivity.this, MainActivity.class);
        toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toHome);
        finish();
    }*/

}
