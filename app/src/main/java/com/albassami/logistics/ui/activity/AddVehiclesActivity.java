package com.albassami.logistics.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CarMaker;
import com.albassami.logistics.dto.response.CarModel;
import com.albassami.logistics.dto.response.CarYear;
import com.albassami.logistics.dto.response.CountriesData;
import com.albassami.logistics.dto.response.DeleteVehicleResponse;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.GetVehiclesResponse;
import com.albassami.logistics.dto.response.VehicleColor;
import com.albassami.logistics.dto.response.VehiclesData;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.ui.Adapter.MyVehiclesAdapter;
import com.chaos.view.PinView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddVehiclesActivity extends AppCompatActivity implements View.OnClickListener, MyVehiclesAdapter.OnDeleteClicked, MyVehiclesAdapter.OnEditClicked {
    private RecyclerView rvVehicles;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<String> plateTypeList;
    AutoCompleteTextView fristitem,seconditem,thirditem;
    LinearLayout othersLayout, saudiLayout;
    private MyVehiclesAdapter myVehiclesAdapter;
    private Button btnAdd;
    APIInterface apiInterface;
    EditText et_piate_number, et_id_number, et_phone_number, et_owner_name, et_plate_saudi;
    GetPriceDataResponse dataResponse;
    private AutoCompleteTextView atvCarSize, atvModel, atvPlate, atvCarColor, atvCarYear,atvnationallityofowner,idtypes;
    private ArrayList<VehiclesData> vehiclesDataArrayList;
    ArrayAdapter<String> modelAdapter, carSizeAdapter, plateAdapter, carColorAdapter, carYearAdapter;
    PrefUtils prefUtils;
    ArrayList<String> carSizes;
    ArrayList<String> carMakers;
    ArrayList<String> carColors;
    ArrayList<String> carYears;
    String car_maker_id, car_size_id, car_color_id, car_year_id;
    ArrayList<CarModel> carSizesList;
    ArrayList<CarMaker> carMakersList;
    ArrayList<VehicleColor> carColorsList;
    ArrayList<CarYear> carYearsList;
    String plateDigit, completePlateNo, plateType, plateTypeUpdate, modelName, modelID, plateLetter, maker_name, type_name, plateNoUpdated = "", ownerNameUpdated = "", makerNameUpdated = "", phoneUpdated = "", typeUpdated = "", idUpdated = "", vehicleId = "";
    private ImageView btnBack;
    ArrayList<CountriesData> countriesDataArrayList;
    ArrayList<String> idtype,nationallityofowner;
    private AutoCompleteTextView atvNationality;
    String nationalityid,idtypenumber;
    TextView atvplateplatenumber;
    LinearLayout layouteditplate;
    String stridtype,strnatioanlity,strcolor,stryear;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.activity_add_vehicles);

        inIT();
        SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
    }

    private void inIT() {
        apiInterface = CustomRestClient.getApiService();
        prefUtils = PrefUtils.getInstance(this);
        vehiclesDataArrayList = new ArrayList<>();
        rvVehicles = findViewById(R.id.rvVehicles);
        btnAdd = findViewById(R.id.btnAddVehicle);
        btnBack = findViewById(R.id.ivBack);

        btnBack.setOnClickListener(this::onClick);
        btnAdd.setOnClickListener(this::onClick);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvVehicles.setLayoutManager(linearLayoutManager);
        UiUtils.showLoadingDialog(this);
        getVehiclesData();


    }
    private void getCountries1() {
        nationallityofowner = new ArrayList<>();
        idtype=new ArrayList<>();
        plateTypeList=new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("ar")){
                nationallityofowner.clear();
                idtype.clear();
                plateTypeList.clear();

                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    nationallityofowner.add(dataResponse.getData().get(0).getCountries().get(i).getName_ar());

                }
                for (int i = 0; i < dataResponse.getData().get(0).getId_card_types().size(); i++) {
                    idtype.add(dataResponse.getData().get(0).getId_card_types().get(i).toString().substring(dataResponse.getData().get(0).getId_card_types().get(i).toString().indexOf("name_ar")+8,
                            dataResponse.getData().get(0).getId_card_types().get(i).toString().indexOf("}")));

                }
                for (int i = 0; i < dataResponse.getData().get(0).getPlate_types().size(); i++) {
                    plateTypeList.add(dataResponse.getData().get(0).getPlate_types().get(i).getName_ar());

                }
            }
            else{
                nationallityofowner.clear();
                idtype.clear();
                plateTypeList.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    // idtype.add(dataResponse.getData().get(0).getId_card_types().get(i).getName_en());
                    nationallityofowner.add(dataResponse.getData().get(0).getCountries().get(i).getName());
                }
                for (int i = 0; i < dataResponse.getData().get(0).getId_card_types().size(); i++) {
                    idtype.add(dataResponse.getData().get(0).getId_card_types().get(i).toString().substring(dataResponse.getData().get(0).getId_card_types().get(i).toString().indexOf("name_en")+8,
                            dataResponse.getData().get(0).getId_card_types().get(i).toString().indexOf("name_ar")-2));

                }
                for (int i = 0; i < dataResponse.getData().get(0).getPlate_types().size(); i++) {
                    plateTypeList.add(dataResponse.getData().get(0).getPlate_types().get(i).getName_en());

                }
            }
            ArrayAdapter<String> idtypeadpt=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, idtype);
            idtypes.setAdapter(idtypeadpt);
            ArrayAdapter<String>  nationalityofowner = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nationallityofowner);
            atvnationallityofowner.setAdapter(nationalityofowner);
            atvnationallityofowner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    atvnationallityofowner.showDropDown();
                }
            });
            idtypes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idtypes.showDropDown();
                }
            });
        }
    }
    public void addDialogue() {
        carMakers = new ArrayList<>();
        carSizes = new ArrayList<>();
        carColors = new ArrayList<>();
        carYears = new ArrayList<>();
        carMakersList = new ArrayList<>();
        carSizesList = new ArrayList<>();
        carColorsList = new ArrayList<>();
        carYearsList = new ArrayList<>();
        Dialog dialog = new Dialog(AddVehiclesActivity.this, R.style.custom_dialogue);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_add_vehicle);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Button btnCancel = dialog.findViewById(R.id.cancel);
        fristitem = dialog.findViewById(R.id.fristletter);
        seconditem = dialog.findViewById(R.id.secondletter);
        thirditem = dialog.findViewById(R.id.thirdletter);
        String[] platelettters={"أ","ب","ح","د","ر","س","ص","ط","ع","ق","ك","ل","م","ن","ه","و","ى"};
        ArrayAdapter plateletttersadpt=new ArrayAdapter(AddVehiclesActivity.this, android.R.layout.simple_dropdown_item_1line,platelettters);
        fristitem.setAdapter(plateletttersadpt);
        seconditem.setAdapter(plateletttersadpt);
        thirditem.setAdapter(plateletttersadpt);
        fristitem.setOnClickListener(i-> fristitem.showDropDown());
        seconditem.setOnClickListener(i-> seconditem.showDropDown());
        thirditem.setOnClickListener(i-> thirditem.showDropDown());
        othersLayout = dialog.findViewById(R.id.layoutOthers);
        saudiLayout = dialog.findViewById(R.id.layoutSaudi);
        et_plate_saudi = dialog.findViewById(R.id.etPlateSaudi);
        et_id_number = dialog.findViewById(R.id.etIDNumber);
        et_owner_name = dialog.findViewById(R.id.etOwnerName);
        et_piate_number = dialog.findViewById(R.id.etPiateNumber);
        atvCarSize = dialog.findViewById(R.id.atvCarType);
        atvPlate = dialog.findViewById(R.id.atvPlateType);
        atvModel = dialog.findViewById(R.id.atvModel);
        atvCarColor = dialog.findViewById(R.id.atvColor);
        atvCarYear = dialog.findViewById(R.id.atvCarYear);
        atvplateplatenumber=dialog.findViewById(R.id.etPiateNumber2);
        layouteditplate=dialog.findViewById(R.id.editlayout);
        atvnationallityofowner=dialog.findViewById(R.id.atvnationalityofowner);
        idtypes=dialog.findViewById(R.id.atvidtype);
        Button addBtn = dialog.findViewById(R.id.btnAdd);
        getCountries1();
        if (!(makerNameUpdated.equalsIgnoreCase(""))) {
            atvModel.setText(makerNameUpdated);
            atvCarSize.setText(typeUpdated);
            et_id_number.setText(idUpdated);
            et_owner_name.setText(ownerNameUpdated);
            atvPlate.setText(plateTypeUpdate);
            atvCarYear.setText(stryear);
            atvCarColor.setText(strcolor);
            atvnationallityofowner.setText(strnatioanlity,false);
            idtypes.setText(stridtype,false);
            if (plateTypeUpdate.equalsIgnoreCase(getString(R.string.others))) {
                othersLayout.setVisibility(View.VISIBLE);
                saudiLayout.setVisibility(View.GONE);

            } else {
                String[] splitString = plateNoUpdated.split("");
                String firstType = "";
                String secondType = "";
                for (int i = 0; i < splitString.length; i++) {
                    if (i == 0) {
                        firstType = splitString[i];
                    }
                    if (i == 1) {
                        firstType = firstType + splitString[i];
                    }
                    if (i == 2) {
                        firstType = firstType + splitString[i];
                    }
                    if (i == 3) {
                        firstType = firstType + splitString[i];
                    }
                    if (i == 4) {
                        firstType = firstType + splitString[i];

                    }
                    if (i == 5) {
                        secondType = splitString[i];
                    }
                    if (i == 6) {
                        secondType = secondType + splitString[i];
                    }
                    if (i == 7) {
                        secondType = secondType + splitString[i];
                    }
                }
                othersLayout.setVisibility(View.GONE);
                saudiLayout.setVisibility(View.VISIBLE);
                et_plate_saudi.setText(firstType);

            }
            addBtn.setText(R.string.btn_edit);
            layouteditplate.setVisibility(View.VISIBLE);
            othersLayout.setVisibility(View.GONE);
            saudiLayout.setVisibility(View.GONE);
            atvPlate.setEnabled(false);
            atvplateplatenumber.setText(plateNoUpdated);
        } else {
            addBtn.setText(getString(R.string.add));
        }

        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCarMakers() != null && dataResponse.getData().get(0).getCarMakers() != null) {
            carMakersList = dataResponse.getData().get(0).getCarMakers();
//            carColorsList = dataResponse.getData().get(0).getVehicleColors();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("ar")){
                carMakers.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCarMakers().size(); i++) {
                    carMakers.add(dataResponse.getData().get(0).getCarMakers().get(i).getCarMakerId().getName_ar());}}
            else{
                carMakers.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCarMakers().size(); i++) {
                    carMakers.add(dataResponse.getData().get(0).getCarMakers().get(i).getCarMakerId().getName());
                    //   carColors.add(dataResponse.getData().get(0).getVehicleColors().get(i).getVehicle_color_name());
                    //   carYears.add(dataResponse.getData().get(0).getCarYears().get(i).getName());
                }}
            if(sharedPreferences.getString("language","").equals("ar")){
                for (int i = 0; i < dataResponse.getData().get(0).getVehicleColors().size(); i++) {
                    carColors.add(dataResponse.getData().get(0).getVehicleColors().get(i).getVehicle_color_name());
                }}
            else{
                for (int i = 0; i < dataResponse.getData().get(0).getVehicleColors().size(); i++) {
                    carColors.add(dataResponse.getData().get(0).getVehicleColors().get(i).getVehicle_color_name_en());
                }
            }
            for (int i = 0; i < dataResponse.getData().get(0).getCarYears().size(); i++) {
                carYears.add(dataResponse.getData().get(0).getCarYears().get(i).getName());
            }
        }
//        if (dataResponse != null && dataResponse.getData().get(0).getVehicleColors() != null && dataResponse.getData().get(0).getVehicleColors() != null) {
//            carColorsList = dataResponse.getData().get(0).getVehicleColors();
//            for (int i = 0; i < dataResponse.getData().get(0).getVehicleColors().size(); i++) {
//                carColors.add(dataResponse.getData().get(0).getVehicleColors().get(i).getVehicle_color_name());
//                carColors.add(dataResponse.getData().get(0).getVehicleColors().get(i).getVehicle_color_name());
//                carColors.add(dataResponse.getData().get(0).getCarYears().get(i).getName());
//            }
//        }
        modelAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, android.R.layout.simple_dropdown_item_1line, carMakers);
        plateAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, android.R.layout.simple_dropdown_item_1line, plateTypeList);
        carColorAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, android.R.layout.simple_dropdown_item_1line, carColors);
        carYearAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, android.R.layout.simple_dropdown_item_1line, carYears);
        atvModel.setAdapter(modelAdapter);
        atvPlate.setAdapter(plateAdapter);
        atvCarColor.setAdapter(carColorAdapter);
        atvCarYear.setAdapter(carYearAdapter);
        atvModel.setOnItemClickListener(new AddVehiclesActivity.MyClickListener(atvModel));
        atvCarSize.setOnItemClickListener(new AddVehiclesActivity.MyClickListener(atvCarSize));
        atvPlate.setOnItemClickListener(new AddVehiclesActivity.MyClickListener(atvPlate));
        atvCarColor.setOnItemClickListener(new AddVehiclesActivity.MyClickListener(atvCarColor));
        atvCarYear.setOnItemClickListener(new AddVehiclesActivity.MyClickListener(atvCarYear));
        //atvCarSize.setDropDownWidth(250);
        //  atvModel.setDropDownWidth(250);
        // atvCarColor.setDropDownWidth(250);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (atvCarSize.getText().toString().isEmpty()) {
                    Toast.makeText(AddVehiclesActivity.this, getString(R.string.selectcarsize), Toast.LENGTH_SHORT).show();
                } else if (atvModel.getText().toString().isEmpty()) {
                    Toast.makeText(AddVehiclesActivity.this, getString(R.string.selectcarmodel), Toast.LENGTH_SHORT).show();
                }  else if (atvPlate.getText().toString().isEmpty()) {
                    atvPlate.setError(getString(R.string.selectplatetype));
                } else if (et_id_number.getText().toString().isEmpty()) {
                    et_id_number.setError(getString(R.string.selectidnumber));
                } else if (et_owner_name.getText().toString().isEmpty()) {
                    et_owner_name.setError(getString(R.string.selectownername));
                } else {
                    completePlateNo = plateDigit + plateLetter;
                    if (completePlateNo.length() != 7) {
                        completePlateNo = et_piate_number.getText().toString();
                    }
                    if (addBtn.getText().toString().equalsIgnoreCase(getString(R.string.add))) {
                        dialog.cancel();
                        if(atvPlate.getText().toString().equals(getString(R.string.others))||atvPlate.getText().toString().equals(getString(R.string.customscard))) {

                            addVehicles(et_owner_name.getText().toString(),et_piate_number.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), atvCarSize.getText().toString(), type_name,atvCarColor.getText().toString(),atvCarYear.getText().toString());
                        }
                        else{
                            addVehicles(et_owner_name.getText().toString(), fristitem.getText().toString()+seconditem.getText().toString()+thirditem.getText().toString()+et_plate_saudi.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), atvCarSize.getText().toString(), type_name,atvCarColor.getText().toString(),atvCarYear.getText().toString());

                        }
                    }
                    else{
                        dialog.cancel();
                        editcar(et_owner_name.getText().toString(),atvplateplatenumber.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), atvCarSize.getText().toString(), type_name,atvCarColor.getText().toString(),atvCarYear.getText().toString());


                    }
                }
            }
        });
        atvCarSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atvCarSize.requestFocus();
                atvCarSize.showDropDown();
            }
        });
        atvPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atvPlate.requestFocus();
                atvPlate.showDropDown();
            }
        });
        atvModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atvModel.requestFocus();
                atvModel.showDropDown();
            }
        });
        atvCarColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atvCarColor.requestFocus();
                atvCarColor.showDropDown();
            }
        });
        atvCarYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atvCarYear.requestFocus();
                atvCarYear.showDropDown();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vehicleId = "";
                makerNameUpdated = "";
                plateTypeUpdate = "";
                typeUpdated = "";
                idUpdated = "";
                plateNoUpdated = "";
                phoneUpdated = "";
                ownerNameUpdated = "";
                dialog.cancel();
            }
        });



        dialog.show();
    }

    private void editcar(String ownerName, String plateNumber, String id_number, String phoneNumber, String make_id, String type_id, String model_id, String model_name, String plate_type, String maker_name, String type_name,String color,String year) {
        getnatinalityandidnumber();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("id", Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)))
                .addFormDataPart("token", prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""))
                .addFormDataPart("lang", "en")
                .addFormDataPart("plate_number", plateNumber)
                .addFormDataPart("plate_type", plate_type)
                .addFormDataPart("model_id", model_id)
                .addFormDataPart("maker_id", make_id)
                .addFormDataPart("type_id", type_id)
                .addFormDataPart("phone_number", "1355316535")
                .addFormDataPart("id_number", id_number)
                .addFormDataPart("owner_name", ownerName)
                .addFormDataPart("model_name", model_name)
                .addFormDataPart("maker_name", maker_name)
                .addFormDataPart("type_name", type_name)
                .addFormDataPart("owner_nationality_code",nationalityid )
                .addFormDataPart("car_color", color)
                .addFormDataPart("car_year",year )
                .addFormDataPart("owner_id_type", idtypenumber)
                .build();
        Request request = new Request.Builder()
                .url(APIConsts.Urls.BASE_URL +"/api/car/update_car")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();

        UiUtils.showLoadingDialog(this);

        AsyncTask asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                UiUtils.hideLoadingDialog();
                getVehiclesData();
            }
        };

        asyncTask.execute();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btnAddVehicle:
                addDialogue();
                break;
        }
    }

    private void getnatinalityandidnumber() {
        SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName_ar())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                }

            }
        if(nationalityid==null||nationalityid.equals("")){
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                }

            }

        }
        }
        else{
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                }

            }
            if(nationalityid==null||nationalityid.equals("")){
                for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                    if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName_ar())){
                        nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                    }

                }

            }

        }
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtypes.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("}")))){

                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")-2);
                }

            }
        }
        else{
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtypes.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")-2))){
                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")-2);
                }

            }
        }}
    private void addVehicles(String ownerName, String plateNumber, String id_number, String phoneNumber, String make_id, String type_id, String model_id, String model_name, String plate_type, String maker_name, String type_name,String color,String year) {
        getnatinalityandidnumber();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("id", Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)))
                .addFormDataPart("token", prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""))
                .addFormDataPart("lang", "en")
                .addFormDataPart("plate_number", plateNumber)
                .addFormDataPart("plate_type", plate_type)
                .addFormDataPart("model_id", model_id)
                .addFormDataPart("maker_id", make_id)
                .addFormDataPart("type_id", type_id)
                .addFormDataPart("phone_number", "1355316535")
                .addFormDataPart("id_number", id_number)
                .addFormDataPart("owner_name", ownerName)
                .addFormDataPart("model_name", model_name)
                .addFormDataPart("maker_name", maker_name)
                .addFormDataPart("type_name", type_name)
                .addFormDataPart("owner_id_type", idtypenumber)
                .addFormDataPart("owner_nationality_code",nationalityid )
                .addFormDataPart("car_color", color)
                .addFormDataPart("car_year",year )
                .build();
        Request request = new Request.Builder()
                .url(APIConsts.Urls.BASE_URL+"/api/car/add_car")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();

        UiUtils.showLoadingDialog(this);

        AsyncTask asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                UiUtils.hideLoadingDialog();
                getVehiclesData();
            }
        };

        asyncTask.execute();




    }
    private void editVehicles(String ownerName, String plateNumber, String id_number, String phoneNumber, String make_id, String type_id, String model_id, String model_name, String plate_type, String maker_name, String type_name) {
        UiUtils.showLoadingDialog(AddVehiclesActivity.this);
        getnatinalityandidnumber();
        Call<String> addVehicleResponseCall = apiInterface.editVehicle("277",  prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""), "en", "1235 س ص ع", "gfdgdf",
                "6592", "1", "53", "1355316535", "1234567890","hassan20","model name","maker name1","type name","2","5");
        addVehicleResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                        vehicleId = "";
                        makerNameUpdated = "";
                        plateTypeUpdate = "";
                        typeUpdated = "";
                        idUpdated = "";
                        plateNoUpdated = "";
                        phoneUpdated = "";
                        ownerNameUpdated = "";
                        Toast.makeText(AddVehiclesActivity.this, "tmammmm", Toast.LENGTH_SHORT).show();
                        getVehiclesData();

                } else if (response.errorBody() != null) {
                    UiUtils.hideLoadingDialog();
                    //   ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                    Toast.makeText(AddVehiclesActivity.this, "error in edit vehicle", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
            }
        });
    }

    private void getVehiclesData() {

        Call<GetVehiclesResponse> vehiclesResponseCall = apiInterface.getVehicles(String.valueOf(prefUtils.getIntValue(PrefKeys.USER_ID, 0)), prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        vehiclesResponseCall.enqueue(new Callback<GetVehiclesResponse>() {
            @Override
            public void onResponse(Call<GetVehiclesResponse> call, Response<GetVehiclesResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    vehiclesDataArrayList = new ArrayList<>();
                    if (!(response.body().getData().isEmpty())) {
                        vehiclesDataArrayList = response.body().getData();
                    }
                    myVehiclesAdapter = new MyVehiclesAdapter(AddVehiclesActivity.this, vehiclesDataArrayList, AddVehiclesActivity.this::onDelete, (id, brand, type, plate, plateType1, phone, owner, idNumber,id_type,nationality,color,year,modelid,typeid,makerid,typename) -> AddVehiclesActivity.this.onEdit(id, brand, type, plate, plateType1, phone, owner, idNumber, id_type,nationality,color,year,modelid,typeid,makerid,typename));
                    rvVehicles.setAdapter(myVehiclesAdapter);
                    myVehiclesAdapter.notifyDataSetChanged();
                } else if (response.errorBody() != null) {
                    // ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                    Toast.makeText(AddVehiclesActivity.this, getString(R.string.error_in_getting_data), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetVehiclesResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
             //   startActivity(new Intent(AddVehiclesActivity.this,GetStartedActivity.class));
              //  finishAffinity();
                Toast.makeText(AddVehiclesActivity.this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                doLogoutUser();
            }
        });
    }
    protected void doLogoutUser() {
        UiUtils.showLoadingDialog(AddVehiclesActivity.this);
        Call<String> call = apiInterface.doLogoutUser(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
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
                    if (loginResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        PrefHelper.setUserLoggedOut(AddVehiclesActivity.this);
                        Intent i = new Intent(AddVehiclesActivity.this, GetStartedActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        UiUtils.showShortToast(AddVehiclesActivity.this, loginResponse.optString(APIConsts.Params.ERROR));
                        prefUtils.setValue(PrefKeys.IS_LOGGED_IN, false);
                        startActivity(new Intent(AddVehiclesActivity.this, GetStartedActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(AddVehiclesActivity.this)) {
                    UiUtils.showShortToast(AddVehiclesActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void deleteVehicle(String id) {
        UiUtils.showLoadingDialog(AddVehiclesActivity.this);
        Call<String> deleteVehicleResponseCall = apiInterface.deleteVehicles( String.valueOf(prefUtils.getIntValue(PrefKeys.USER_ID, 0)), prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),id);
        deleteVehicleResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                getVehiclesData();
                if (response.isSuccessful() && response.body() != null ) {


                } else if (response.errorBody() != null) {
                    UiUtils.hideLoadingDialog();
                    //  ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                    Toast.makeText(AddVehiclesActivity.this, getString(R.string.errorindeletingcar), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getVehiclesData();
                UiUtils.hideLoadingDialog();
            }
        });
    }

    @Override
    public void onDelete(String id, String name) {
        deleteVehicle(id);
    }

    @Override
    public void onEdit(Integer id, String brand, String type, String plate, String plateType, String phone, String owner, String idNumber,String idtype,String nationality,String color,String year,String modelid,String type_ID,String makerid,String typename) {
        vehicleId = id.toString();
        makerNameUpdated = brand;
        typeUpdated = type;
        idUpdated = idNumber;
        plateTypeUpdate = plateType;
        plateNoUpdated = plate;
        phoneUpdated = phone;
        ownerNameUpdated = owner;
        stridtype=idtype;
        strnatioanlity=nationality;
        strcolor=color;
        stryear=year;
        car_maker_id=makerid;
        car_size_id=type_ID;
        modelID=modelid;
        type_name=typename;
        addDialogue();
    }


    public class MyClickListener implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener(AutoCompleteTextView myAc) {
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (ac.getId()) {
                case R.id.atvModel:
                    carSizesList = new ArrayList<>();
                    carSizes = new ArrayList<>();
                    atvCarSize.setText("",false);
                    SharedPreferences sharedPreferences=getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences.getString("language","").equals("ar")){
                        for (int j = 0; j < carMakersList.size(); j++) {
                            if (atvModel.getAdapter().getItem(i).equals(carMakersList.get(i).getCarMakerId().getName_ar())) {
                                car_maker_id = carMakersList.get(i).getCarMakerId().getId().toString();
                                maker_name = carMakersList.get(i).getCarMakerId().getName();
                                carSizesList = carMakersList.get(i).getCarModels();
                                for (int k = 0; k < carSizesList.size(); k++) {
                                    carSizes.add(carSizesList.get(k).getCarModelId().getName_ar());
                                }
                                if (!(carSizes.isEmpty())) {
                                    atvCarSize.setEnabled(true);
                                    atvCarSize.setClickable(true);
                                    carSizeAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, R.layout.item_drop_down, carSizes);
                                    atvCarSize.setAdapter(carSizeAdapter);
                                }
                                break;
                            }
                        }

                    }
                    else{
                        atvCarSize.setText("",false);
                    for (int j = 0; j < carMakersList.size(); j++) {
                        if (atvModel.getAdapter().getItem(i).equals(carMakersList.get(i).getCarMakerId().getName())) {
                            car_maker_id = carMakersList.get(i).getCarMakerId().getId().toString();
                            maker_name = carMakersList.get(i).getCarMakerId().getName();
                            carSizesList = carMakersList.get(i).getCarModels();
                            for (int k = 0; k < carSizesList.size(); k++) {
                                carSizes.add(carSizesList.get(k).getCarModelId().getName());
                            }
                            if (!(carSizes.isEmpty())) {
                                atvCarSize.setEnabled(true);
                                atvCarSize.setClickable(true);
                                carSizeAdapter = new ArrayAdapter<>(AddVehiclesActivity.this, R.layout.item_drop_down, carSizes);
                                atvCarSize.setAdapter(carSizeAdapter);
                            }
                            break;
                        }
                    }}
                    break;

                case R.id.atvCarType:
                    SharedPreferences sharedPreferences2=getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences2.getString("language","").equals("ar")){
                        for (int m = 0; m < carSizes.size(); m++) {
                            if (atvCarSize.getAdapter().getItem(i).toString().equalsIgnoreCase(carSizesList.get(m).getCarModelId().getName_ar())) {
                                car_size_id = String.valueOf(carSizesList.get(m).getCarSize().getId());
                                modelName = atvCarSize.getAdapter().getItem(i).toString();
                                modelID = String.valueOf(carSizesList.get(m).getCarModelId().getId());
                                type_name = carSizesList.get(m).getCarSize().getName();
                                break;
                            }
                        }}
                    else{
                        for (int m = 0; m < carSizes.size(); m++) {
                            if (atvCarSize.getAdapter().getItem(i).toString().equalsIgnoreCase(carSizesList.get(m).getCarModelId().getName())) {
                                car_size_id = String.valueOf(carSizesList.get(m).getCarSize().getId());
                                modelName = atvCarSize.getAdapter().getItem(i).toString();
                                modelID = String.valueOf(carSizesList.get(m).getCarModelId().getId());
                                type_name = carSizesList.get(m).getCarSize().getName();
                                break;
                            }
                        }
                    }
                    break;

                case R.id.atvPlateType:
                    SharedPreferences sharedPreferences1=getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences1.getString("language","").equals("ar")){
                        if(atvPlate.getAdapter().getItem(i).toString().equals("اخرى")){
                            plateType ="others";
                        }
                        else if(atvPlate.getAdapter().getItem(i).toString().equals("لوحه سعوديه")){
                            plateType ="saudia";
                        }
                    }
                    else{
                        plateType = atvPlate.getAdapter().getItem(i).toString();}
                    if (atvPlate.getAdapter().getItem(i).toString().equalsIgnoreCase(getString(R.string.others))||atvPlate.getAdapter().getItem(i).toString().equalsIgnoreCase(getString(R.string.customscard))) {
                        othersLayout.setVisibility(View.VISIBLE);
                        saudiLayout.setVisibility(View.GONE);

                    } else {
                        othersLayout.setVisibility(View.GONE);
                        saudiLayout.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    }
}
