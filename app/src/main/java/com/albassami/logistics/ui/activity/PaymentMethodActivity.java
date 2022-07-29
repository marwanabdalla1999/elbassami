package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvVisa, tvCredit, tvApplePay;
    PrefUtils prefUtils;
    APIInterface apiInterface;
    Bundle bundle;
    Intent intent;
    private Button payNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        inIT();
    }

    private void inIT() {
        prefUtils = PrefUtils.getInstance(this);
        intent = getIntent();
        bundle = intent.getExtras();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        tvVisa = findViewById(R.id.tv_visa_card);
        tvCredit = findViewById(R.id.tv_credit_card);
        tvApplePay = findViewById(R.id.tv_apple_pay);
        payNow = findViewById(R.id.btnPayNow);
        tvVisa.setOnClickListener(this);
        tvCredit.setOnClickListener(this);
        tvApplePay.setOnClickListener(this);
        payNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_visa_card:
                tvVisa.setBackgroundResource(R.drawable.bottom_line_layout);
                tvCredit.setBackgroundColor(getResources().getColor(R.color.white));
                tvApplePay.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_credit_card:
                tvCredit.setBackgroundResource(R.drawable.bottom_line_layout);
                tvVisa.setBackgroundColor(getResources().getColor(R.color.white));
                tvApplePay.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.tv_apple_pay:
                tvApplePay.setBackgroundResource(R.drawable.bottom_line_layout);
                tvVisa.setBackgroundColor(getResources().getColor(R.color.white));
                tvCredit.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case R.id.btnPayNow:
                Intent intent = new Intent(PaymentMethodActivity.this, RideLaterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
               // createANowRequest();
                break;
        }
    }


//    protected void createANowRequest() {
//        UiUtils.showLoadingDialog(PaymentMethodActivity.this);
//        try {
//            Call<String> call = apiInterface.createNowRequest(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
//                    , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
//                    , ""
//                    , prefUtils.getStringValue(PrefKeys.PRICE, "")
//                    , Const.source_address
//                    , Const.dest_address
//                    , Const.stop_address != null ? Const.stop_address : ""
//                    , Const.pic_latlan.latitude
//                    , Const.pic_latlan.longitude
//                    , Const.drop_latlan.latitude
//                    , Const.drop_latlan.longitude
//                    , Const.stop_latlan != null ? Const.stop_latlan.latitude : 0
//                    , Const.stop_latlan != null ? Const.stop_latlan.longitude : 0
//                    , 1
//                    , ""
//                    , prefUtils.getStringValue(PrefKeys.PAYMENT_MODE, "")
//            );
//            call.enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//                    UiUtils.hideLoadingDialog();
//                    JSONObject createRequestResponse = null;
//                    try {
//                        createRequestResponse = new JSONObject(response.body());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    if (createRequestResponse != null) {
//                        if (createRequestResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
//                            try {
//                                Toast.makeText(PaymentMethodActivity.this, createRequestResponse.getString("message"), Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//                        } else {
//                            try {
//                                if (createRequestResponse.getString(Const.Params.ERROR_MSG) != null) {
//                                    Toast.makeText(PaymentMethodActivity.this, createRequestResponse.getString(Const.Params.ERROR_MSG), Toast.LENGTH_LONG).show();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
////                            enableRequestBtn();
////                            UiUtils.showShortToast(getActivity(), createRequestResponse.optString(APIConsts.Params.ERROR));
////                            if (req_load_dialog != null && req_load_dialog.isShowing()) {
////                                req_load_dialog.dismiss();
////                                stopCheckingforstatus();
////                            }
//                            cancelCreateRequest();
//                            if (createRequestResponse.optInt(APIConsts.Params.ERROR_CODE) == APIConsts.ErrorCodes.WALLETEMPTY) {
//                                Intent walletIntent = new Intent(PaymentMethodActivity.this, WalletAcivity.class);
//                                startActivity(walletIntent);
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//                    UiUtils.hideLoadingDialog();
//                    if (NetworkUtils.isNetworkConnected(PaymentMethodActivity.this)) {
//                        UiUtils.showShortToast(PaymentMethodActivity.this, getString(R.string.may_be_your_is_lost));
//                    }
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    protected void cancelCreateRequest() {
        UiUtils.showLoadingDialog(PaymentMethodActivity.this);
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
                if (NetworkUtils.isNetworkConnected(PaymentMethodActivity.this)) {
                    UiUtils.showShortToast(PaymentMethodActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }
}
