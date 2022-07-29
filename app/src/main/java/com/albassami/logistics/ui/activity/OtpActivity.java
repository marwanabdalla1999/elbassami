package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hbb20.CountryCodePicker;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mahesh on 7/5/2017.
 */

public class OtpActivity extends AppCompatActivity {
    @BindView(R.id.et_otp_mobile)
    CustomRegularEditView etOtpMobile;
    @BindView(R.id.btn_edit_number)
    ImageView btnEditNumber;
    @BindView(R.id.user_otp)
    CustomRegularEditView userOtp;
    @BindView(R.id.notRecevied)
    CustomRegularTextView notRecevied;
    @BindView(R.id.btn_confirm_otp)
    Button btnConfirmOtp;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.getOtp)
    TextView getOtp;
    @BindView(R.id.otpDemoNote)
    CustomRegularTextView otpDemoNote;
    private String code = "";
    APIInterface apiInterface;
    PrefUtils prefUtils;
    Bundle bundle;
    String phoneNumb, countryCode, countryName;
    boolean isRedirectProfile;
    boolean newOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verifier);
        ButterKnife.bind(this);
        prefUtils = PrefUtils.getInstance(OtpActivity.this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (getIntent().getExtras() != null) {
            phoneNumb = getIntent().getStringExtra(Const.Params.PHONE);
            countryCode = getIntent().getStringExtra(APIConsts.Params.COUNTRY_CODE);
            isRedirectProfile = getIntent().getBooleanExtra("redirectToProfile", false);
            newOtp = getIntent().getBooleanExtra("newOtp", false);
            etOtpMobile.setText("");
            etOtpMobile.append(phoneNumb);
        }
        btnEditNumber.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_edit));
        etOtpMobile.setEnabled(false);
        if (!isRedirectProfile) {
            getOtp();
            otpDemoNote.setVisibility(View.VISIBLE);
            getOtp.setVisibility(View.GONE);
        } else {
            otpDemoNote.setVisibility(View.GONE);
            btnConfirmOtp.setText(getString(R.string.updatePhoneNumber));

        }
        if (countryCode != null && !countryCode.equalsIgnoreCase("")) {
            ccp.setCountryForPhoneCode(Integer.parseInt(countryCode));
        }
    }

    private boolean isvalid() {
        if (TextUtils.isEmpty(userOtp.getText().toString())) {
            UiUtils.showShortToast(OtpActivity.this, getString(R.string.txt_otp_error));
            userOtp.requestFocus();
            return false;
        } else if (!userOtp.getText().toString().equals(code)) {
            UiUtils.showShortToast(OtpActivity.this, getString(R.string.txt_otp_wrong));
            userOtp.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    protected void getOtp() {
        UiUtils.showLoadingDialog(OtpActivity.this);
        Call<String> call = apiInterface.getOtp(etOtpMobile.getText().toString()
                , countryCode);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject otpResponse = null;
                try {
                    otpResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (otpResponse.optString(Const.Params.SUCCESS).equals(Const.Params.TRUE)) {
                    JSONObject data = otpResponse.optJSONObject(APIConsts.Params.DATA);
                    code = data.optString(APIConsts.Params.CODE);
                    if (data.optString(APIConsts.Params.IS_PREFILL).equalsIgnoreCase("1")) {
                        userOtp.setText(code);
                        UiUtils.showLongToast(getApplicationContext(), otpResponse.optString(APIConsts.Params.MESSAGE));
                    }
                } else {
                    String error = otpResponse.optString(APIConsts.Params.ERROR);
                    AndyUtils.showShortToast(error, OtpActivity.this);
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

    @OnClick({R.id.btn_edit_number, R.id.notRecevied, R.id.btn_confirm_otp, R.id.getOtp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_number:
//                if (newOtp){
//                    newOtp =false;
//
//                    getOtp();
//                } else {
                newOtp = true;
//                if (!isRedirectProfile) {
                    etOtpMobile.setEnabled(true);
                    etOtpMobile.requestFocus();
//                    } else {
//                        getOtp();
//                    }
//                }
                break;
            case R.id.btn_confirm_otp:
                if (isvalid()) {
                    if (!isRedirectProfile) {
                        Intent i = new Intent(OtpActivity.this, SignUpNextActivity.class);
                        i.putExtra(Const.Params.PHONE, etOtpMobile.getText().toString());
                        i.putExtra(APIConsts.Params.COUNTRY_CODE, countryCode);
                        startActivity(i);
                        finish();
                    } else {
//                        Intent i = new Intent(OtpActivity.this, ProfileActivity.class);
//                        i.putExtra(APIConsts.Params.PHONE, etOtpMobile.getText().toString());
//                        i.putExtra(APIConsts.Params.COUNTRY_CODE, countryCode);
//                        startActivity(i);
//                        finish();
                        updateUserProfile();
                    }
                }
                break;
            case R.id.notRecevied:
                getOtp();
                break;

            case R.id.getOtp:
                getOtp();
                break;
        }
    }

    protected void updateUserProfile() {
        UiUtils.showLoadingDialog(OtpActivity.this);
        Call<String> call = apiInterface.updateMobileNumber(
                prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                , etOtpMobile.getText().toString()
                , ccp.getSelectedCountryCodeWithPlus());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject updateProfileResponse = null;
                try {
                    updateProfileResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (updateProfileResponse != null) {
                    if (updateProfileResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        JSONObject data = updateProfileResponse.optJSONObject(APIConsts.Params.DATA);
                        UiUtils.showShortToast(OtpActivity.this, updateProfileResponse.optString(APIConsts.Params.MESSAGE));
                        finish();
                    } else {
                        UiUtils.showShortToast(getApplicationContext(), updateProfileResponse.optString(APIConsts.Params.ERROR));
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


}
