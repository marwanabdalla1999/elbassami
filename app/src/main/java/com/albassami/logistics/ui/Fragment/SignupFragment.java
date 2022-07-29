package com.albassami.logistics.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hbb20.CountryCodePicker;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.ui.activity.OtpActivity;
import com.albassami.logistics.ui.activity.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Mahesh on 7/5/2017.
 */

public class SignupFragment extends AppCompatActivity {
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.user_mobile_nuber)
    CustomRegularEditView userMobileNuber;
    @BindView(R.id.redirectLogin)
    CustomRegularTextView redirectLogin;
    Unbinder unbinder;
    SignInActivity activity;
    APIInterface apiInterface;
    PrefUtils prefUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getApplicationContext());

        Spannable wordtoSpan = new SpannableString(getString(R.string.not_here_to_login_signup));
        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), 19, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        redirectLogin.setText(wordtoSpan);
    }

    @OnClick({R.id.btn_confirm_phone, R.id.redirectLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_phone:
                if (validation()) {
                    Intent i = new Intent(getApplicationContext(), OtpActivity.class);
                    i.putExtra(Const.Params.PHONE, userMobileNuber.getText().toString());
                    i.putExtra(APIConsts.Params.COUNTRY_CODE, ccp.getSelectedCountryCodeWithPlus());
                    i.putExtra("redirectToProfile", false);
                    i.putExtra("newOtp",false);
                    startActivity(i);
                }
                break;
            case R.id.redirectLogin:
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
        }
    }

    private boolean validation() {
        if (userMobileNuber.getText().toString().equalsIgnoreCase("")){
            UiUtils.showShortToast(SignupFragment.this,getString(R.string.empty_mobile_number));
            return false;
        } else if (userMobileNuber.getText().toString().length()>13 ){
            UiUtils.showShortToast(SignupFragment.this,getString(R.string.mobile_number_more_than));
            return false;
        }else if (userMobileNuber.getText().toString().length()<6){
            UiUtils.showShortToast(SignupFragment.this,getString(R.string.mobile_number_less_than));
            return false;
        } else {
            return true;
        }
    }
}
