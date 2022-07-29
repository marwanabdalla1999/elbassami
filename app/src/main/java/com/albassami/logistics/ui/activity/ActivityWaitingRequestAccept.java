package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.ParserUtils;
import com.albassami.logistics.network.Models.RequestDetail;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityWaitingRequestAccept extends AppCompatActivity {
    ProgressBar progressBar;
    String carSize,date, phoneNumber,idNumber,receivrName,receiverNumber,carModelName, piateNumber, carModel, serviceType, branchName, ownerName, branchId, casSizeID, carModelID, branchNameTo, branchIdTo;
    PrefUtils prefUtils;
    APIInterface apiInterface;
    TextView confirmBtn, tvPhoneNumber,tvReceiverName,tvTotalCost,tvSenderName,tvSource, tvCarSize, tvDestination, tvService, tvPrice, tvCash, tvCard,Date;
    //LinearLayout layoutReceiver, layoutReceiverNumber, layout_vehicle;
    private Handler reqHandler;
    Runnable runnable = new Runnable() {
        public void run() {
            requestStatusCheck();
            reqHandler.postDelayed(this, 5000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_request_accept);
        prefUtils = PrefUtils.getInstance(this);
      //  layoutReceiver = findViewById(R.id.layout_receiver);
      //  layoutReceiverNumber = findViewById(R.id.layout_receiver_mobile);
      //  layout_vehicle = findViewById(R.id.layout_vehicle);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
       // tvCash = findViewById(R.id.tvCash);
      //  tvCard = findViewById(R.id.tvCard);
      //  layoutReceiver = findViewById(R.id.layout_receiver);
      //  layoutReceiverNumber = findViewById(R.id.layout_receiver_mobile);
      //  layout_vehicle = findViewById(R.id.layout_vehicle);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvSource = findViewById(R.id.tvSource);
        tvDestination = findViewById(R.id.tvDestination);
        tvPrice = findViewById(R.id.tvPrice);
        tvCarSize = findViewById(R.id.tvCarSize);
        tvSenderName = findViewById(R.id.tvSenderName);
        tvService = findViewById(R.id.tvServiceType);
        Date=findViewById(R.id.tvshapment_date);
        serviceType = getIntent().getExtras().getString(Const.PassParam.SERVICE_TYPE);
        branchName = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME);
        ownerName = getIntent().getExtras().getString(Const.PassParam.OWNER_NAME);
        idNumber = getIntent().getExtras().getString(Const.PassParam.ID_NUMBER);
        phoneNumber = getIntent().getExtras().getString(Const.PassParam.PHONE_NUMBER);
        piateNumber = getIntent().getExtras().getString(Const.PassParam.PIATE_NUMBER);
        carSize = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE);
        carModel = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL);
        branchId = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID);
        casSizeID = getIntent().getExtras().getString(Const.PassParam.CAR_SIZE_ID);
        carModelID = getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_ID);
        branchIdTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_ID_TO);
        branchNameTo = getIntent().getExtras().getString(Const.PassParam.BRANCH_NAME_TO);
        receivrName = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NAME);
        receiverNumber = getIntent().getExtras().getString(Const.PassParam.RECEIVER_NUMBER);
        carModelName =  getIntent().getExtras().getString(Const.PassParam.CAR_MODEL_NAME);
        date =  getIntent().getStringExtra("date");
       /* if (serviceType.equalsIgnoreCase(Const.DOOR_TO_DOOR)) {
            layout_vehicle.setVisibility(View.GONE);
            layoutReceiverNumber.setVisibility(View.GONE);
            layoutReceiver.setVisibility(View.GONE);
        } else {
            layout_vehicle.setVisibility(View.VISIBLE);
            layoutReceiverNumber.setVisibility(View.VISIBLE);
            layoutReceiver.setVisibility(View.VISIBLE);
            tvCarSize.setText(carSize + "-" + carModelName + "-" + piateNumber);
            tvPhoneNumber.setText(receiverNumber);
            tvReceiverName.setText(receivrName);
        }*/
        tvDestination.setText(branchNameTo);
        tvSource.setText(branchName);
        tvSenderName.setText(prefUtils.getStringValue(PrefKeys.USER_NAME,""));
        tvReceiverName.setText(receivrName);
        tvPhoneNumber.setText(receiverNumber);
        tvCarSize.setText(carSize+"-"+carModelName);
        tvPrice.setText(getIntent().getExtras().getString(Const.PassParam.PRICE));
        tvService.setText(serviceType);
        Date.setText(date);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
        String currentTime = df.format(Calendar.getInstance().getTime());
        reqHandler = new Handler();
        prefUtils = PrefUtils.getInstance(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        startCheckRegTimer();
    }

    protected void requestStatusCheck() {
        Call<String> call = apiInterface.pingRequestStatusCheck(prefUtils.getIntValue(PrefKeys.USER_ID, 0),
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject requestStatusResponse = null;
                try {
                    requestStatusResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (requestStatusResponse != null) {
                    if (requestStatusResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        RequestDetail requestDetail = ParserUtils.parseRequestStatus(response.body());
                        if (requestDetail != null) {
                            switch (requestDetail.getTripStatus()) {
                                case Const.NO_REQUEST:
                                    stopCheckingUpcomingRequests();
                                    finish();
                                    Toast.makeText(ActivityWaitingRequestAccept.this, "no driver available please try again", Toast.LENGTH_LONG).show();
                                    break;
                                case Const.IS_ACCEPTED:
                                    stopCheckingUpcomingRequests();
                                    Intent i = new Intent(ActivityWaitingRequestAccept.this, MainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    break;
                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void stopCheckingUpcomingRequests() {
        if (reqHandler != null) {
            reqHandler.removeCallbacks(runnable);
        }
    }

    public void startCheckRegTimer() {
        reqHandler.postDelayed(runnable, 5000);
    }
}
