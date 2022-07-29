package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.PriceResponse;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.ui.activity.OrderSummaryActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class otherservice extends Fragment {
String[] numbers={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
Spinner n1,n2,n3;
    String carSize,date,shipment_date, phoneNumber, receivrName, maker_id, carModelName, receiverNumber, cashCard = "", idNumber, piateNumber, carModel, serviceType, branchName, ownerName, branchId, carSizeID, carModelID, branchNameTo, branchIdTo;
AutoCompleteTextView datebox;
    ApiServices apiServices;
    PrefUtils prefUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_otherservice, container, false);
        init();
        numberdropdownlist(view);
        Date(view);
       // getPrice();
        return view ;
    }
    /*private void getPrice() {
        UiUtils.showLoadingDialog(getContext());
        apiServices = RestClient.getApiService();

        Call<PriceResponse> priceResponseCall = apiServices.getPrice(prefUtils.getStringValue(PrefKeys.AUTH_TOKEN, ""),"individual",carSizeID,branchId,branchIdTo,date);
        priceResponseCall.enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null && !(response.body().getData().isEmpty())&&response.body().getData().size()>0){
                        //  Toast.makeText(OrderSummaryActivity.this, "resopne"+String.valueOf(response.body().getData()) , Toast.LENGTH_LONG).show();
                      //datebox.setText();
                        prefUtils.setValue(PrefKeys.PRICE, String.valueOf(response.body().getData().get(0).getPrice()) + "SR");
                    } }else{ //if (response.errorBody() != null) {
//                    ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);

                    //Toast.makeText(OrderSummaryActivity.this, "client Error" , Toast.LENGTH_SHORT).show();

                    //   Toast.makeText(OrderSummaryActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
            }
        });
    }*/

    private void Date(View view) {
        datebox=view.findViewById(R.id.datebox);

    }

    private void numberdropdownlist(View view) {
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,numbers);

        n1=view.findViewById(R.id.spinner1);
        n2=view.findViewById(R.id.spinner2);
        n3=view.findViewById(R.id.spinner3);
        n1.setAdapter(arrayAdapter);
        n2.setAdapter(arrayAdapter);
        n3.setAdapter(arrayAdapter);

    }

    private void init() {
        serviceType = getArguments().getString(Const.PassParam.SERVICE_TYPE);
        branchName = getArguments().getString(Const.PassParam.BRANCH_NAME);
        ownerName = getArguments().getString(Const.PassParam.OWNER_NAME);
        idNumber = getArguments().getString(Const.PassParam.ID_NUMBER);
        phoneNumber = getArguments().getString(Const.PassParam.PHONE_NUMBER);
        piateNumber = getArguments().getString(Const.PassParam.PIATE_NUMBER);
        carSize = getArguments().getString(Const.PassParam.CAR_SIZE);
        carModel = getArguments().getString(Const.PassParam.CAR_MODEL);
        branchId = getArguments().getString(Const.PassParam.BRANCH_ID);
        carSizeID = getArguments().getString(Const.PassParam.CAR_SIZE_ID);
        carModelID = getArguments().getString(Const.PassParam.CAR_MODEL_ID);
        shipment_date = getArguments().getString("Date");
        maker_id = getArguments().getString(Const.PassParam.CAR_MAKER_ID);
        branchIdTo = getArguments().getString(Const.PassParam.BRANCH_ID_TO);
        branchNameTo = getArguments().getString(Const.PassParam.BRANCH_NAME_TO);
        receivrName = getArguments().getString(Const.PassParam.RECEIVER_NAME);
        receiverNumber = getArguments().getString(Const.PassParam.RECEIVER_NUMBER);
        carModelName = getArguments().getString(Const.PassParam.CAR_MODEL_NAME);

        date=getArguments().getString("Date");

    }
}
