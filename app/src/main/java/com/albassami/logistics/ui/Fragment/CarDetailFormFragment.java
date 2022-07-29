package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CarMaker;
import com.albassami.logistics.dto.response.CarModel;
import com.albassami.logistics.dto.response.CarSize;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.ui.activity.OrderSummaryActivity;
import com.chaos.view.PinView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class CarDetailFormFragment extends Fragment implements View.OnClickListener  {
    Button nextBtn;
    private ImageView btnBack;
    ArrayAdapter<String> modelAdapter, carSizeAdapter,plateAdapter;
    String[] plateTypeList = {"Saudi Plate Number", "Other"};
    ArrayList<String> carSizes;
    ArrayList<String> carMakers;
    ArrayList<CarModel> carSizesList;
    ArrayList<CarMaker> carMakersList;
    PrefUtils prefUtils;
    PinView pinView;
    LinearLayout othersLayout,saudiLayout;
    String service_type, branch_name, branch_id,plateDigit ,completePlateNo, plateLetter, car_size_id, car_model_id, branch_name_to, branch_id_to;
    EditText et_piate_number,et_visa_number, et_id_number, et_phone_number, et_owner_name,et_plate_saudi;
    GetPriceDataResponse dataResponse;
    private AutoCompleteTextView atvCarSize, atvModel,atvPlate;
    public void setUpLocale() {

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            Locale myLocale = new Locale("ar");
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            getContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
            // new PreferenceHelper(this).getLanguage();
            sharedPreferences=getContext().getSharedPreferences("lang",MODE_PRIVATE);
            sharedPreferences.edit().putString("language","ar").apply();
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     setUpLocale();
        View view = inflater.inflate(R.layout.fragment_between_cities, container, false);
        carMakers = new ArrayList<>();
        carMakersList = new ArrayList<>();
        service_type = getArguments().getString(Const.PassParam.SERVICE_TYPE);
        branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
        branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
        branch_name_to = getArguments().getString(Const.PassParam.BRANCH_NAME_TO);
        branch_id_to = getArguments().getString(Const.PassParam.BRANCH_ID_TO);
        prefUtils = PrefUtils.getInstance(getContext());
        pinView = view.findViewById(R.id.pinView);
        et_id_number = view.findViewById(R.id.etIDNumber);
        et_plate_saudi = view.findViewById(R.id.etPlateSaudi);
        et_owner_name = view.findViewById(R.id.etOwnerName);
        et_phone_number = view.findViewById(R.id.etPhoneNumber);
        et_piate_number = view.findViewById(R.id.etPiateNumber);
        othersLayout = view.findViewById(R.id.layoutOthers);
        saudiLayout = view.findViewById(R.id.layoutSaudi);
        nextBtn = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
        atvCarSize = view.findViewById(R.id.atvCarType);
        atvModel = view.findViewById(R.id.atvModel);
        atvPlate = view.findViewById(R.id.atvPlateType);
        atvCarSize.setEnabled(false);
        atvCarSize.setClickable(false);
        atvModel.setOnClickListener(this::onClick);
        atvPlate.setOnClickListener(this::onClick);
        atvCarSize.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);
        nextBtn.setOnClickListener(this::onClick);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCarMakers() != null && dataResponse.getData().get(0).getCarMakers() != null) {
            carMakersList = dataResponse.getData().get(0).getCarMakers();
            for (int i = 0; i < dataResponse.getData().get(0).getCarMakers().size(); i++) {
                carMakers.add(dataResponse.getData().get(0).getCarMakers().get(i).getCarMakerId().getName());
            }
        }
        watcherText();
        modelAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_drop_down, carMakers);
        plateAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_drop_down, plateTypeList);
        atvModel.setAdapter(modelAdapter);
        atvPlate.setAdapter(plateAdapter);
        atvModel.setOnItemClickListener(new MyClickListener(atvModel));
        atvCarSize.setOnItemClickListener(new MyClickListener(atvCarSize));
        atvPlate.setOnItemClickListener(new MyClickListener(atvPlate));
        atvCarSize.setDropDownWidth(250);
        atvModel.setDropDownWidth(250);
        return view;
    }

    private void watcherText(){
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  String value = editable.toString();
                  if (value.length() == 3){
                      et_plate_saudi.requestFocus();
                      plateLetter = value;
                  }
            }
        });
        et_plate_saudi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                if (value.length() == 0){
                    pinView.requestFocus();
                }else if (value.length() == 4){
                    plateDigit = value;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:

                if (atvCarSize.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "select car size", Toast.LENGTH_SHORT).show();
                } else if (atvModel.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "select car model", Toast.LENGTH_SHORT).show();
                }  else if (et_phone_number.getText().toString().isEmpty()) {
                    et_phone_number.setError("enter phone number");
                } else {
                    completePlateNo = plateDigit + plateLetter;
                    if (completePlateNo.length() != 7){
                        completePlateNo = et_id_number.getText().toString();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.PassParam.BRANCH_NAME, branch_name);
                    bundle.putString(Const.PassParam.BRANCH_ID, branch_id);
                    bundle.putString(Const.PassParam.BRANCH_NAME_TO, branch_name_to);
                    bundle.putString(Const.PassParam.BRANCH_ID_TO, branch_id_to);
                    bundle.putString(Const.PassParam.SERVICE_TYPE, service_type);
                    bundle.putString(Const.PassParam.CAR_MODEL, atvModel.getText().toString());
                    bundle.putString(Const.PassParam.CAR_SIZE, atvCarSize.getText().toString());
                    bundle.putString(Const.PassParam.CAR_MODEL_ID, car_model_id);
                    bundle.putString(Const.PassParam.CAR_SIZE_ID, car_size_id);
                    bundle.putString(Const.PassParam.ID_NUMBER, et_id_number.getText().toString());
                    bundle.putString(Const.PassParam.PHONE_NUMBER, et_phone_number.getText().toString());
                    bundle.putString(Const.PassParam.OWNER_NAME, et_owner_name.getText().toString());
                    bundle.putString(Const.PassParam.PIATE_NUMBER, et_piate_number.getText().toString());
                    Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.atvModel:
                atvModel.requestFocus();
                if (atvModel.isPopupShowing()) {
                    atvModel.dismissDropDown();
                } else {
                    atvModel.showDropDown();
                }
                break;
            case R.id.atvPlateType:
                atvPlate.requestFocus();
                if (atvPlate.isPopupShowing()) {
                    atvPlate.dismissDropDown();
                } else {
                    atvPlate.showDropDown();
                }
                break;
            case R.id.atvCarType:
                atvCarSize.requestFocus();
                if (atvCarSize.isPopupShowing()) {
                    atvCarSize.dismissDropDown();
                } else {
                    atvCarSize.showDropDown();
                }
                break;


        }
    }
    public class MyClickListener implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener(AutoCompleteTextView myAc){
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch(ac.getId()){
                case R.id.atvModel:
                    carSizesList = new ArrayList<>();
                    carSizes = new ArrayList<>();
                    for (int j = 0; j < carMakersList.size(); j++) {
                        if (atvModel.getAdapter().getItem(i).equals(carMakersList.get(i).getCarMakerId().getName())) {
                            car_model_id = carMakersList.get(i).getCarMakerId().getId().toString();
                            carSizesList = carMakersList.get(i).getCarModels();
                            for (int k = 0; k <carSizesList.size() ; k++) {
                                carSizes.add(carSizesList.get(k).getCarModelId().getName());
                            }
                            if (!(carSizes.isEmpty())){
                                atvCarSize.setEnabled(true);
                                atvCarSize.setClickable(true);
                                carSizeAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_drop_down, carSizes);
                                atvCarSize.setAdapter(carSizeAdapter);
                            }
                            break;
                        }
                    }
                    break;

                case R.id.atvCarType:
                    for (int m = 0; m < carSizes.size(); m++) {
                        if (atvCarSize.getAdapter().getItem(i).toString().equalsIgnoreCase(carSizesList.get(m).getCarModelId().getName())){
                            car_size_id = String.valueOf(carSizesList.get(m).getCarSize().getId());
                            break;
                        }
                    }
                    break;

                case R.id.atvPlateType:
                    if (atvPlate.getAdapter().getItem(i).toString().equalsIgnoreCase("Other")){
                        othersLayout.setVisibility(View.VISIBLE);
                        saudiLayout.setVisibility(View.GONE);
                    }else {
                        othersLayout.setVisibility(View.GONE);
                        saudiLayout.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    }

}
