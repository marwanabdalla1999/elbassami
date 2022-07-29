package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CarMaker;
import com.albassami.logistics.dto.response.CarModel;
import com.albassami.logistics.dto.response.CarYear;
import com.albassami.logistics.dto.response.CountriesData;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.GetVehiclesResponse;
import com.albassami.logistics.dto.response.VehicleColor;
import com.albassami.logistics.dto.response.VehiclesData;
import com.albassami.logistics.dto.response.maker;
import com.albassami.logistics.dto.response.type;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.ui.Adapter.carsadapter;
import com.albassami.logistics.ui.Fragment.DetailFormFragment;
import com.chaos.view.PinView;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class detailsform extends AppCompatActivity implements View.OnClickListener {
    Button nextBtn;
    private ImageView btnBack;
    ArrayAdapter<String> nationalityModel, vehiclesModel;
    ArrayList<String> carsList;
    private ArrayList<VehiclesData> vehiclesDataArrayList;
    ArrayList<CarModel> carSizesList;
    PrefUtils prefUtils;
    LinearLayout layoutCarDetail;
    GetPriceDataResponse dataResponse;
    ArrayList<CountriesData> countriesDataArrayList;
    ArrayList<String> countriesList;
    APIInterface apiInterface,apiInterface1;
    private AutoCompleteTextView atvCars, atvNationality,atvnationallityofowner,idtypes;
    String service_type, country_id, branch_name, branch_id, carModelName, maker_id, receiverName, recieverNumber, user_phone_number, car_size, owner_name, id_number, user_plate_number, car_size_id, car_model_id, branch_name_to, branch_id_to;
    EditText etReceiverName, et_visa_number, et_id_number, etReceiverNumber, etReceiverID, etSenderName;
    TextView etCarName, etCarModel, etCarPlate,from,to;
    Button add_vehicle;
    ArrayList<String> carMakers,carSizes,carColors,carYears,yeararray,montharray,dayarray,timearray;
    ArrayList<CarMaker> carMakersList;
    ArrayList<VehicleColor> carColorsList;
    ArrayList<CarYear> carYearsList;
    LinearLayout othersLayout, saudiLayout;
    PinView pinView;
    EditText et_piate_number, et_phone_number, et_owner_name, et_plate_saudi;
    private AutoCompleteTextView atvCarSize, atvModel, atvPlate, atvCarColor, atvCarYear,YDate,MDate,DDate,Time;
    ArrayAdapter<String> modelAdapter,carSizeAdapter, plateAdapter, carColorAdapter, carYearAdapter,yearAdapter,monthAdapter,dayAdapter,timeAdapter;
    String car_maker_id,plateDigit, completePlateNo,plateType , plateTypeUpdate, modelName, modelID, plateLetter, maker_name, type_name, plateNoUpdated = "", ownerNameUpdated = "", makerNameUpdated = "", phoneUpdated = "", typeUpdated = "", idUpdated = "", vehicleId = "";
    ArrayList<String> plateTypeList;
    ArrayList<com.albassami.logistics.dto.response.maker> maker;
    ArrayList<com.albassami.logistics.dto.response.type> type;
    RecyclerView carlist;
    ArrayList<String> cars;
    carsadapter carsadpt;
    String nationalid="";
    EditText idnumber;
    String plate_type;
    ArrayList<String> idtype,nationallityofowner;
    String nationalityid,idtypenumber;
    String car_id="";
    LatLng lat_long_from,lat_lang_to;
    public void setUpLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
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
        setContentView(R.layout.fragment_between_cities);
        add_vehicle=findViewById(R.id.add_vehicle);
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialogue();
            }
        });
        inIT();
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180);}
        getDate();
    }
    private void getDate() {
        String stringofdate =new SimpleDateFormat("yyyy-MM-dd-HH").format(new Date());

        String[] date = stringofdate.split("-", 4);
        YDate.setText(Integer.toString(Integer.parseInt(date[0])));
        MDate.setText(Integer.toString(Integer.parseInt(date[1])));
        DDate.setText(Integer.toString(Integer.parseInt(date[2])));
        Time.setText(Integer.parseInt(date[3])+0+":00");
        yeararray.add(Integer.toString(Integer.parseInt(date[0])));
        yeararray.add(Integer.toString(Integer.parseInt(date[0])+1));
        montharray.add(Integer.toString(Integer.parseInt(date[1])));
        if(Integer.parseInt(date[1])<12){
            montharray.add(Integer.toString(Integer.parseInt(date[1])+1));
        }
        else{montharray.add("1");}
        for(int i=Integer.parseInt(date[2]);i<32;i++){
            dayarray.add(Integer.toString(i));
        }
        for(int j=Integer.parseInt(date[3]);j<25;j++){
            if(j<10){
                timearray.add("0"+j+":00");
            }
            else {
                timearray.add(j + ":00");
            }
        }
        YDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(YDate.getText().toString().equals(Integer.toString(Integer.parseInt(date[0])))){
                    montharray.clear();
                    dayarray.clear();
                    timearray.clear();
                    montharray.add(Integer.toString(Integer.parseInt(date[1])));
                    if(Integer.parseInt(date[1])<12){
                        montharray.add(Integer.toString(Integer.parseInt(date[1])+1));
                    }
                    else{montharray.add("1");}
                    for(int i=Integer.parseInt(date[2]);i<32;i++){
                        dayarray.add(Integer.toString(i));
                    }
                    for(int j=Integer.parseInt(date[3]);j<25;j++){
                        if(j<10){
                            timearray.add("0"+j+":00");
                        }
                        else {
                            timearray.add(j + ":00");
                        }
                    }

                }
                else{
                    montharray.clear();
                    dayarray.clear();
                    timearray.clear();

                    for (int z = 1; z < 13; z++) {
                        montharray.add(Integer.toString(z));
                    }
                    for (int i = 1; i < 32; i++) {
                        dayarray.add(Integer.toString(i));
                    }
                    for (int j = 1; j < 25; j++) {
                        if (j < 10) {
                            timearray.add("0" + j + ":00");
                        } else {
                            timearray.add(j + ":00");
                        }
                    }


                }
            }
        });
        MDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MDate.getText().toString().equals(Integer.toString(Integer.parseInt(date[1])))){

                    dayarray.clear();
                    timearray.clear();
                    for(int i=Integer.parseInt(date[2]);i<32;i++){
                        dayarray.add(Integer.toString(i));
                    }
                    for(int j=Integer.parseInt(date[3]);j<25;j++){
                        if(j<10){
                            timearray.add("0"+j+":00");
                        }
                        else {
                            timearray.add(j + ":00");
                        }
                    }

                }
                else{

                    dayarray.clear();
                    timearray.clear();
                    for (int i = 1; i < 32; i++) {
                        dayarray.add(Integer.toString(i));
                    }
                    for (int j = 1; j < 25; j++) {
                        if (j < 10) {
                            timearray.add("0" + j + ":00");
                        } else {
                            timearray.add(j + ":00");
                        }
                    }


                }
            }
        });
        DDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(DDate.getText().toString().equals(Integer.toString(Integer.parseInt(date[2])))){

                    timearray.clear();
                    for(int j=Integer.parseInt(date[3]);j<25;j++){
                        if(j<10){
                            timearray.add("0"+j+":00");
                        }
                        else {
                            timearray.add(j + ":00");
                        }
                    }

                }
                else{

                    timearray.clear();

                    for (int j = 1; j < 25; j++) {
                        if (j < 10) {
                            timearray.add("0" + j + ":00");
                        } else {
                            timearray.add(j + ":00");
                        }
                    }


                }
            }
        });
        yearAdapter=new ArrayAdapter<>(this,R.layout.item_drop_down,yeararray);
        monthAdapter=new ArrayAdapter<>(this,R.layout.item_drop_down,montharray);
        dayAdapter=new ArrayAdapter<>(this,R.layout.item_drop_down,dayarray);
        timeAdapter=new ArrayAdapter<>(this,R.layout.item_drop_down,timearray);
        yearAdapter.notifyDataSetChanged();
        monthAdapter.notifyDataSetChanged();
        dayAdapter.notifyDataSetChanged();
        timeAdapter.notifyDataSetChanged();
        yearAdapter.setNotifyOnChange(true);
        monthAdapter.setNotifyOnChange(true);
        dayAdapter.setNotifyOnChange(true);
        timeAdapter.setNotifyOnChange(true);
        YDate.setAdapter(yearAdapter);
        MDate.setAdapter(monthAdapter);
        DDate.setAdapter(dayAdapter);
        Time.setAdapter(timeAdapter);

    }

    private void inIT() {
        carsList = new ArrayList<>();
        vehiclesDataArrayList = new ArrayList<>();
        apiInterface = CustomRestClient.getApiService();
        apiInterface1= APIClient.getClient().create(APIInterface.class);
        service_type = getIntent().getStringExtra(Const.PassParam.SERVICE_TYPE);
        branch_name = getIntent().getStringExtra(Const.PassParam.BRANCH_NAME);
        branch_name_to = getIntent().getStringExtra(Const.PassParam.BRANCH_NAME_TO);
        prefUtils = PrefUtils.getInstance(this);
        layoutCarDetail = findViewById(R.id.layoutCarDetail);
        etReceiverName = findViewById(R.id.etReceiverName);
        etReceiverNumber = findViewById(R.id.etReceiverPhone);
        etReceiverID = findViewById(R.id.IDnumber);
        etCarName = findViewById(R.id.etCarName);
        etCarModel = findViewById(R.id.etCarModel);
        etCarPlate = findViewById(R.id.etPlateNumber);
        atvCars = findViewById(R.id.atvCars);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        from.setText(branch_name);
        to.setText(branch_name_to);
        atvNationality = findViewById(R.id.atvNationality);
        nextBtn = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);
        Time=findViewById(R.id.time);
        YDate=findViewById(R.id.year);
        MDate=findViewById(R.id.month);
        DDate=findViewById(R.id.day);
        Bundle bundle= getIntent().getBundleExtra("location");
        lat_lang_to=bundle.getParcelable("lat_lang_to");
        lat_long_from=bundle.getParcelable("lat_lang_from");
        yeararray=new ArrayList<>();
        montharray=new ArrayList<>();
        dayarray=new ArrayList<>();
        timearray=new ArrayList<>();
        atvNationality.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        atvCars.setOnClickListener(this);
        YDate.setOnClickListener(this);
        MDate.setOnClickListener(this);
        DDate.setOnClickListener(this);
        Time.setOnClickListener(this);
        atvCars.setOnItemClickListener(new detailsform.MyClickListener(atvCars));
        atvNationality.setOnItemClickListener(new detailsform.MyClickListener(atvNationality));
        atvCars.setDropDownVerticalOffset(10);
        getCountries();
        getVehiclesData();
    }
    @Override
    public void onStart() {
        super.onStart();
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (etCarName.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.selectcarsize), Toast.LENGTH_SHORT).show();
                } else if (atvNationality.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.selectnationality), Toast.LENGTH_SHORT).show();
                } else if (etReceiverName.getText().toString().isEmpty()) {
                    etReceiverName.setError(getString(R.string.enterreceivername));
                } else if (etReceiverNumber.getText().toString().isEmpty()) {
                    et_id_number.setError(getString(R.string.enterreceivernumber));}
              /*  else if (!et_id_number.getText().toString().isEmpty()&&et_id_number.getText().toString().length()!=10) {
                    et_id_number.setError(getString(R.string.idnumbermustconsistsof10numbers));}
                else if (!et_id_number.getText().toString().isEmpty()&&!et_id_number.getText().toString().startsWith("1")) {
                    et_id_number.setError(getString(R.string.idnumbermustconsistsof10numbers));}*/

                else {

                    Bundle bundle = new Bundle();
                    bundle.putString(Const.PassParam.BRANCH_NAME, branch_name);
                    bundle.putString(Const.PassParam.BRANCH_ID_TO,"128");
                    bundle.putString(Const.PassParam.SERVICE_TYPE, service_type);
                    bundle.putString(Const.PassParam.CAR_MODEL, etCarModel.getText().toString());
                    bundle.putString(Const.PassParam.CAR_SIZE,etCarName.getText().toString());
                    bundle.putString(Const.PassParam.CAR_MODEL_ID, car_model_id);
                    bundle.putString(Const.PassParam.CAR_SIZE_ID, car_size_id);
                    bundle.putString(Const.PassParam.CAR_MAKER_ID, maker_id);
                    bundle.putString(Const.PassParam.ID_NUMBER, id_number);
                    bundle.putString(Const.PassParam.PHONE_NUMBER, user_phone_number);
                    bundle.putString(Const.PassParam.OWNER_NAME, owner_name);
                    bundle.putString(Const.PassParam.CAR_MODEL_NAME, carModelName);
                    bundle.putString(Const.PassParam.RECEIVER_NAME, etReceiverName.getText().toString());
                    bundle.putString(Const.PassParam.RECEIVER_NATIONAL_NUMBER, etReceiverID.getText().toString());
                    bundle.putString(Const.PassParam.RECEIVER_COUNTRY, country_id);
                    bundle.putString(Const.PassParam.RECEIVER_NUMBER, etReceiverNumber.getText().toString());
                    bundle.putString(Const.PassParam.PIATE_NUMBER, user_plate_number);
                    bundle.putString("from", branch_name);
                    bundle.putString("to", branch_name_to);
                    bundle.putString("carid",car_id);
                    bundle.putString("price", getIntent().getStringExtra("price"));
                   bundle.putParcelable("lat_lang_from",lat_long_from);
                    bundle.putParcelable("lat_lang_to",lat_lang_to);
                    bundle.putString("Date",YDate.getText().toString()+"-"+MDate.getText().toString()+"-"+DDate.getText().toString()+" "+Time.getText());
                    Intent intent = new Intent(this, OrderSummaryActivity1.class);
                    intent.putExtras(bundle);
                    intent.putExtra("carid",car_id);
                    intent.putExtra("towing_id",getIntent().getStringExtra("towing_id"));
                    intent.putExtra("Shipmenttype",getIntent().getStringExtra("Shipmenttype"));
                    intent.putExtra("distance",getIntent().getStringExtra("distance"));
                    intent.putExtra("time",getIntent().getStringExtra("time"));
                    intent.putExtra("forkm",getIntent().getStringExtra("forkm"));
                    intent.putExtra("startwith",getIntent().getStringExtra("startwith"));


                    startActivity(intent);
                }
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.atvCars:
                atvCars.showDropDown();
                atvCars.requestFocus();
                break;
            case R.id.atvNationality:
                atvNationality.showDropDown();
                atvNationality.requestFocus();
                break;

            case R.id.year:
                YDate.showDropDown();
                YDate.requestFocus();
                break;
            case R.id.month:
                MDate.showDropDown();
                MDate.requestFocus();
                break;
            case R.id.day:
                DDate.showDropDown();
                DDate.requestFocus();
                break;


            case R.id.time:
                Time.showDropDown();
                Time.requestFocus();
                break;

        }}
    public class MyClickListener implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener(AutoCompleteTextView myAc) {
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Boolean retval=false;
            switch (ac.getId()) {
                case R.id.atvCars:
                    SharedPreferences sharedPreferences1=getSharedPreferences("lang", Context.MODE_PRIVATE);
                    if(sharedPreferences1.getString("language","").equals("ar")){
                        for (int j = 0; j < vehiclesDataArrayList.size(); j++) {
                            if (atvCars.getAdapter().getItem(i).toString().equalsIgnoreCase(vehiclesDataArrayList.get(j).getModelName()
                                    +" - "+vehiclesDataArrayList.get(j).getPlateNumber())) {
                                car_id=Integer.toString(vehiclesDataArrayList.get(j).getCar_id());
                                car_model_id = Integer.toString(vehiclesDataArrayList.get(j).getModelId());
                                car_size_id = vehiclesDataArrayList.get(j).getVehicleTypeId().toString();
                                maker_id = Integer.toString(vehiclesDataArrayList.get(j).getVehicleMakerId());
                                id_number = vehiclesDataArrayList.get(j).getIdNumber();
                                owner_name = vehiclesDataArrayList.get(j).getOwnerName();
                                user_phone_number = vehiclesDataArrayList.get(j).getPhoneNumber();
                                user_plate_number = vehiclesDataArrayList.get(j).getPlateNumber();
                                plate_type=vehiclesDataArrayList.get(j).getPlateType();
                                car_size = vehiclesDataArrayList.get(j).getTypeName();
                                if(service_type.equals(getString(R.string.full_load))){
                                    Boolean ret=false;
                                    if(cars.size()<10){
                                        for(int k=0;k<cars.size();k++){
                                            if(atvCars.getAdapter().getItem(i).toString().equals(cars.get(k))){
                                                ret=true;
                                            }
                                        }
                                        if(!ret){
                                            cars.add(atvCars.getAdapter().getItem(i).toString());
                                            carsadpt.notifyDataSetChanged();}
                                    }}
                                else{
                                    layoutCarDetail.setVisibility(View.VISIBLE);
                                    carModelName = vehiclesDataArrayList.get(j).getMakerName();
                                    etCarModel.setText(vehiclesDataArrayList.get(j).getModelName());
                                    etCarName.setText(vehiclesDataArrayList.get(j).getTypeName());
                                    etCarPlate.setText(vehiclesDataArrayList.get(j).getPlateNumber());}
                                retval=true;
                                break;
                            }}}
                    else{
                        for (int j = 0; j < vehiclesDataArrayList.size(); j++) {
                            if (atvCars.getAdapter().getItem(i).toString().equalsIgnoreCase(vehiclesDataArrayList.get(j).getMakerName()
                                    +" - "+vehiclesDataArrayList.get(j).getPlateNumber())) {
                                car_id=Integer.toString(vehiclesDataArrayList.get(j).getCar_id());
                                car_model_id = Integer.toString(vehiclesDataArrayList.get(j).getModelId());
                                car_size_id = vehiclesDataArrayList.get(j).getVehicleTypeId().toString();
                                maker_id = Integer.toString(vehiclesDataArrayList.get(j).getVehicleMakerId());
                                id_number = vehiclesDataArrayList.get(j).getIdNumber();
                                owner_name = vehiclesDataArrayList.get(j).getOwnerName();
                                user_phone_number = vehiclesDataArrayList.get(j).getPhoneNumber();
                                user_plate_number = vehiclesDataArrayList.get(j).getPlateNumber();
                                car_size = vehiclesDataArrayList.get(j).getTypeName();
                                if(service_type.equals(getString(R.string.full_load))){
                                    Boolean ret=false;
                                    if(cars.size()<10){
                                        for(int k=0;k<cars.size();k++){
                                            if(atvCars.getAdapter().getItem(i).toString().equals(cars.get(k))){
                                                ret=true;
                                            }
                                        }
                                        if(!ret){
                                            cars.add(atvCars.getAdapter().getItem(i).toString());
                                            carsadpt.notifyDataSetChanged();}
                                    }}
                                else{
                                    layoutCarDetail.setVisibility(View.VISIBLE);
                                    carModelName = vehiclesDataArrayList.get(j).getMakerName();
                                    etCarModel.setText(vehiclesDataArrayList.get(j).getMakerName());
                                    etCarName.setText(vehiclesDataArrayList.get(j).getTypeName());
                                    etCarPlate.setText(vehiclesDataArrayList.get(j).getPlateNumber());}
                                retval=true;
                                break;
                            }}


                    }
                    if(!retval){
                        atvCars.setText("");
                        layoutCarDetail.setVisibility(View.GONE);
                        addDialogue();}

                    break;
                case R.id.atvNationality:
                    SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
                    if(sharedPreferences.getString("language","").equals("ar")){
                        for (int j = 0; j < countriesDataArrayList.size(); j++) {
                            if (atvNationality.getAdapter().getItem(i).toString().equalsIgnoreCase(countriesDataArrayList.get(j).getName_ar())) {
                                country_id = countriesDataArrayList.get(j).getId().toString();
                            }
                        }}
                    else{

                        for (int j = 0; j < countriesDataArrayList.size(); j++) {
                            if (atvNationality.getAdapter().getItem(i).toString().equalsIgnoreCase(countriesDataArrayList.get(j).getName())) {
                                country_id = countriesDataArrayList.get(j).getId().toString();
                            }
                        }
                    }
                    break;
            }

        }
    }

    private void getVehiclesData() {
        carsList.clear();
        carsList.add(getString(R.string.add_vehicles));
        UiUtils.showLoadingDialog(this);
        Call<GetVehiclesResponse> vehiclesResponseCall = apiInterface.getVehicles(Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)), prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        vehiclesResponseCall.enqueue(new Callback<GetVehiclesResponse>() {
            @Override
            public void onResponse(Call<GetVehiclesResponse> call, Response<GetVehiclesResponse> response) {
                carsList.clear();
                carsList.add(getString(R.string.add_vehicles));
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    vehiclesDataArrayList = new ArrayList<>();
                    if (!(response.body().getData().isEmpty())) {
                        vehiclesDataArrayList = response.body().getData();
                        SharedPreferences sharedPreferences1=getSharedPreferences("lang", Context.MODE_PRIVATE);
                        if(sharedPreferences1.getString("language","").equals("ar")){
                        for (int i = 0; i < vehiclesDataArrayList.size(); i++) {
                            carsList.add(vehiclesDataArrayList.get(i).getModelName()
                                    +" - "+vehiclesDataArrayList.get(i).getPlateNumber());
                        }}
                        else{
                            for (int i = 0; i < vehiclesDataArrayList.size(); i++) {
                                carsList.add(vehiclesDataArrayList.get(i).getMakerName()
                                        +" - "+vehiclesDataArrayList.get(i).getPlateNumber());
                            }
                        }
                    }



                } else if (response.errorBody() != null) {
                    // ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                    UiUtils.hideLoadingDialog();
                    Toast.makeText(detailsform.this, getString(R.string.error_in_getting_data), Toast.LENGTH_SHORT).show();
                }
                vehiclesModel = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carsList);
                atvCars.setAdapter(vehiclesModel);
                vehiclesModel.setNotifyOnChange(true);
            }


            @Override
            public void onFailure(Call<GetVehiclesResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();

            }
        });
    }


    private void getcarmaker( ArrayList<VehiclesData> vehiclesDataArrayList) {
        Call<ArrayList<maker>> call=apiInterface.getcarmaker(Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0)), prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<ArrayList<maker>>() {
            @Override
            public void onResponse(Call<ArrayList<maker>> call, Response<ArrayList<maker>> response) {
                UiUtils.hideLoadingDialog();
                maker=response.body();
                for (int i = 0; i < vehiclesDataArrayList.size(); i++) {
                    carsList.add(maker.get(i).getCar_make_ar_name()
                            +" - "+vehiclesDataArrayList.get(i).getPlateNumber());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<maker>> call, Throwable t) {

            }
        });

    }

    private void getCountries() {
        countriesDataArrayList = new ArrayList<>();
        countriesList = new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("ar")){
                countriesList.clear();
                atvNationality.setText("المملكة العربية السعودية");
                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    countriesList.add(dataResponse.getData().get(0).getCountries().get(i).getName_ar());
                }  }
            else{
                countriesList.clear();
                atvNationality.setText("saudia");
                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    countriesList.add(dataResponse.getData().get(0).getCountries().get(i).getName());
                }}
            nationalityModel = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, countriesList);
            atvNationality.setAdapter(nationalityModel);

        }
    }
    private void getCountries1() {
        countriesDataArrayList = new ArrayList<>();
        plateTypeList=new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
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
            ArrayAdapter<String> idtypeadpt=new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, idtype);
            idtypes.setAdapter(idtypeadpt);
            ArrayAdapter<String>  nationalityofowner = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, nationallityofowner);
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
    private void addDialogue() {
        carMakers = new ArrayList<>();
        carSizes = new ArrayList<>();
        carColors = new ArrayList<>();
        carYears = new ArrayList<>();
        carMakersList = new ArrayList<>();
        carSizesList = new ArrayList<>();
        carColorsList = new ArrayList<>();
        carYearsList = new ArrayList<>();
        idtype=new ArrayList<>();
        nationallityofowner=new ArrayList<>();
        Dialog dialog = new Dialog(detailsform.this, R.style.custom_dialogue);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_add_vehicle);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Button btnCancel = dialog.findViewById(R.id.cancel);
        pinView = dialog.findViewById(R.id.pinView);
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
        atvnationallityofowner=dialog.findViewById(R.id.atvnationalityofowner);
        idtypes=dialog.findViewById(R.id.atvidtype);
        Button addBtn = dialog.findViewById(R.id.btnAdd);
        getCountries1();
        if (!(makerNameUpdated.equalsIgnoreCase(""))) {
            atvModel.setText(makerNameUpdated);
            et_id_number.setText(idUpdated);
            et_phone_number.setText(phoneUpdated);
            et_owner_name.setText(ownerNameUpdated);
            atvCarSize.setText(typeUpdated);
            atvPlate.setText(plateTypeUpdate);
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
                pinView.setText(secondType);
            }
            addBtn.setText(R.string.btn_edit);
        } else {
            addBtn.setText(getString(R.string.add));
        }

        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCarMakers() != null && dataResponse.getData().get(0).getCarMakers() != null) {
            carMakersList = dataResponse.getData().get(0).getCarMakers();
//            carColorsList = dataResponse.getData().get(0).getVehicleColors();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("ar")){
                carMakers.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCarMakers().size(); i++) {
                    carMakers.add(dataResponse.getData().get(0).getCarMakers().get(i).getCarMakerId().getName_ar());}

            }
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
        modelAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carMakers);
        plateAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, plateTypeList);
        carColorAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carColors);
        carYearAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carYears);
        atvModel.setAdapter(modelAdapter);
        atvPlate.setAdapter(plateAdapter);
        atvCarColor.setAdapter(carColorAdapter);
        atvCarYear.setAdapter(carYearAdapter);
        atvModel.setOnItemClickListener(new detailsform.MyClickListener1(atvModel));
        atvCarSize.setOnItemClickListener(new detailsform.MyClickListener1(atvCarSize));
        atvPlate.setOnItemClickListener(new detailsform.MyClickListener1(atvPlate));
        atvCarColor.setOnItemClickListener(new detailsform.MyClickListener1(atvCarColor));
        atvCarYear.setOnItemClickListener(new detailsform.MyClickListener1(atvCarYear));
        //atvCarSize.setDropDownWidth(250);
        //  atvModel.setDropDownWidth(250);
        // atvCarColor.setDropDownWidth(250);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (atvCarSize.getText().toString().isEmpty()) {
                    Toast.makeText(detailsform.this, getString(R.string.selectcarsize), Toast.LENGTH_SHORT).show();
                } else if (atvModel.getText().toString().isEmpty()) {
                    Toast.makeText(detailsform.this, getString(R.string.selectcarmodel), Toast.LENGTH_SHORT).show();
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
                        if(atvPlate.getText().toString().equals(getString(R.string.others))) {
                            Toast.makeText(detailsform.this, atvModel.getText().toString(), Toast.LENGTH_SHORT).show();
                            addVehicles1(et_owner_name.getText().toString(),et_piate_number.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), maker_name, type_name,atvCarColor.getText().toString(),atvCarYear.getText().toString());
                        }
                        else{ Toast.makeText(detailsform.this, atvModel.getText().toString(), Toast.LENGTH_SHORT).show();
                            addVehicles1(et_owner_name.getText().toString(), pinView.getText().toString()+et_plate_saudi.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), maker_name, type_name,atvCarColor.getText().toString(),atvCarYear.getText().toString());

                        }
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
                if (value.length() == 3) {
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
                if (value.length() == 0) {
                    pinView.requestFocus();
                } else if (value.length() == 4) {
                    plateDigit = value;
                }
            }
        });

        dialog.show();
    }
    private void getnatinalityandidnumber() {
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName_ar())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                }

            }}
        else{
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(atvnationallityofowner.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode().toString();
                }

            }

        }
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtypes.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("}")))){

                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(0).toString().substring(dataResponse.getData().get(0).getId_card_types().get(0).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(0).toString().indexOf("name_en")-2);
                }

            }
        }
        else{
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtypes.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")-2))){
                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(0).toString().substring(dataResponse.getData().get(0).getId_card_types().get(0).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(0).toString().indexOf("name_en")-2);
                }

            }
        }}
    private void addVehicles1(String ownerName, String plateNumber, String id_number, String phoneNumber, String make_id, String type_id, String model_id, String model_name, String plate_type, String maker_name, String type_name,String color,String year) {
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
                .url("http://93.112.44.39/api/car/add_car")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();

        UiUtils.showLoadingDialog(detailsform.this);

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

    public class MyClickListener1 implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener1(AutoCompleteTextView myAc) {
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (ac.getId()) {
                case R.id.atvModel:
                    carSizesList = new ArrayList<>();
                    carSizes = new ArrayList<>();

                    SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
                    if(sharedPreferences.getString("language","").equals("ar")){
                        for (int j = 0; j < carMakersList.size(); j++) {
                            if (atvModel.getAdapter().getItem(i).equals(carMakersList.get(i).getCarMakerId().getName_ar())) {
                                car_maker_id = carMakersList.get(i).getCarMakerId().getId().toString();
                                maker_name = carMakersList.get(i).getCarMakerId().getName();
                                carSizesList = carMakersList.get(i).getCarModels();
                                for (int k = 0; k < carSizesList.size(); k++) {
                                    carSizes.add(carSizesList.get(k).getCarModelId().getName());
                                }
                                if (!(carSizes.isEmpty())) {
                                    atvCarSize.setEnabled(true);
                                    atvCarSize.setClickable(true);
                                    carSizeAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carSizes);
                                    atvCarSize.setAdapter(carSizeAdapter);
                                }
                                break;
                            }
                        }}
                    else{
                        for (int j = 0; j < carMakersList.size(); j++) {
                            if (atvModel.getAdapter().getItem(i).equals(carMakersList.get(i).getCarMakerId().getName())) {
                                car_maker_id = carMakersList.get(i).getCarMakerId().getId().toString();
                                maker_name = carMakersList.get(i).getCarMakerId().getName_ar();
                                carSizesList = carMakersList.get(i).getCarModels();
                                for (int k = 0; k < carSizesList.size(); k++) {
                                    carSizes.add(carSizesList.get(k).getCarModelId().getName());
                                }
                                if (!(carSizes.isEmpty())) {
                                    atvCarSize.setEnabled(true);
                                    atvCarSize.setClickable(true);
                                    carSizeAdapter = new ArrayAdapter<>(detailsform.this, android.R.layout.simple_dropdown_item_1line, carSizes);
                                    atvCarSize.setAdapter(carSizeAdapter);
                                }
                                break;
                            }}
                    }
                    break;

                case R.id.atvCarType:
                    for (int m = 0; m < carSizes.size(); m++) {
                        if (atvCarSize.getAdapter().getItem(i).toString().equalsIgnoreCase(carSizesList.get(m).getCarModelId().getName())) {
                            car_size_id = String.valueOf(carSizesList.get(m).getCarSize().getId());
                            modelName = atvCarSize.getAdapter().getItem(i).toString();
                            modelID = String.valueOf(carSizesList.get(m).getCarModelId().getId());
                            type_name = carSizesList.get(m).getCarSize().getName();
                            break;
                        }
                    }
                    break;

                case R.id.atvPlateType:
                    SharedPreferences sharedPreferences1=getSharedPreferences("lang", Context.MODE_PRIVATE);
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
                    if (atvPlate.getAdapter().getItem(i).toString().equalsIgnoreCase(getString(R.string.others))) {
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
    private void addVehicles(String ownerName, String plateNumber, String id_number, String phoneNumber, String make_id, String type_id, String model_id, String model_name, String plate_type, String maker_name, String type_name) {
        UiUtils.showLoadingDialog(this);
        Log.d("VAluesssssssssssssss" ,"addVehicles: "+prefUtils.getIntValue(PrefKeys.USER_ID, 0)+"//"+
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                +"//"+ "en"+"//"+plateNumber+"//"+plate_type+"//"+model_id+"//"+make_id+"//"+type_id+"//"+phoneNumber+"//"+id_number+"//"+ownerName);
        Call<String> addVehicleResponseCall = apiInterface.addVehicle(prefUtils.getIntValue(PrefKeys.USER_ID, 0),
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                "en",plateNumber,plate_type,Integer.parseInt(model_id),Integer.parseInt(make_id),Integer.parseInt(type_id),phoneNumber,id_number,ownerName);
        addVehicleResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UiUtils.hideLoadingDialog();
                    getVehiclesData();
                    Log.d("dataaaaaaaaaaa", "onResponse: "+new Gson().toJson(response.body()));

                } else if (response.errorBody() != null) {
                    UiUtils.hideLoadingDialog();

                    Toast.makeText(detailsform.this, getString(R.string.error_in_adding_vehicle), Toast.LENGTH_SHORT).show();


                }
                else{
                    Toast.makeText(detailsform.this, "erorr", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                Toast.makeText(detailsform.this, "Failure", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
