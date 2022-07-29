package com.albassami.logistics.ui.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.GetVehiclesResponse;
import com.albassami.logistics.dto.response.VehicleColor;
import com.albassami.logistics.dto.response.VehiclesData;
import com.albassami.logistics.dto.response.VehiclesData1;
import com.albassami.logistics.dto.response.maker;
import com.albassami.logistics.dto.response.type;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.ui.Adapter.carsadapter;
import com.albassami.logistics.ui.activity.AddVehiclesActivity;
import com.albassami.logistics.ui.activity.GetStartedActivity;
import com.albassami.logistics.ui.activity.MainActivity;
import com.albassami.logistics.ui.activity.OrderSummaryActivity;
import com.chaos.view.PinView;
import com.google.gson.Gson;

import org.json.JSONObject;

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

import static android.content.Context.MODE_PRIVATE;

public class DetailFormFragment extends Fragment implements View.OnClickListener {
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
    private AutoCompleteTextView atvCars, atvNationality;
    String service_type, country_id="192", branch_name, branch_id, carModelName, maker_id, receiverName, recieverNumber, user_phone_number, car_size, owner_name, id_number, user_plate_number, car_size_id, car_model_id, branch_name_to, branch_id_to;
    EditText etReceiverName, et_visa_number, et_id_number, etReceiverNumber, etReceiverID, etSenderName;
    TextView etCarName, etCarModel, etCarPlate,from,to;
    Button add_vehicle;
    ArrayList<String> carMakers,carSizes,carColors,carYears,yeararray,montharray,dayarray,timearray;
    ArrayList<CarMaker> carMakersList;
    ArrayList<VehicleColor> carColorsList;
    ArrayList<CarYear> carYearsList;
    LinearLayout othersLayout, saudiLayout;
    AutoCompleteTextView fristitem,seconditem,thirditem;
    EditText et_piate_number, et_phone_number, et_owner_name, et_plate_saudi;
    private AutoCompleteTextView atvCarSize, atvModel, atvPlate, atvCarColor, atvCarYear,YDate,MDate,DDate,Time,atvnationallityofowner,idtypes;
    ArrayAdapter<String> modelAdapter,carSizeAdapter, plateAdapter, carColorAdapter, carYearAdapter,yearAdapter,monthAdapter,dayAdapter,timeAdapter;
    String car_maker_id,plateDigit, completePlateNo,plateType , plateTypeUpdate, modelName, modelID, plateLetter, maker_name, type_name, plateNoUpdated = "", ownerNameUpdated = "", makerNameUpdated = "", phoneUpdated = "", typeUpdated = "", idUpdated = "", vehicleId = "";
    ArrayList<String> plateTypeList;
    ArrayList<maker> maker;
    ArrayList<type> type;
   RecyclerView carlist;
   ArrayList<String> cars;
   carsadapter carsadpt;
   String nationalid="";
   EditText idnumber;
   String plate_type,nationality_id,id_typenumber;
   ArrayList<String> idtype,nationallityofowner;
   String nationalityid,idtypenumber;
    Intent intent;
    String carid="";
    TextView atvplateplatenumber;
    LinearLayout layouteditplate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setUpLocale();
        View view = inflater.inflate(R.layout.fragment_between_cities, container, false);
        add_vehicle=view.findViewById(R.id.add_vehicle);
        carlist=view.findViewById(R.id.carlist);
        cars=new ArrayList<>();
        carsadpt=new carsadapter(cars,getContext());
        carlist.setAdapter(carsadpt);
        carlist.setLayoutManager(new LinearLayoutManager(getContext()));
        carsadpt.notifyDataSetChanged();
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addDialogue();
            }
        });
        inIT(view);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180);}
        getDate();
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        setUpLocale();
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpLocale();

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
        yearAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,yeararray);
        monthAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,montharray);
        dayAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,dayarray);
        timeAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,timearray);
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

    private void inIT(View view) {
        carsList = new ArrayList<>();
        vehiclesDataArrayList = new ArrayList<>();
        apiInterface = CustomRestClient.getApiService();
        apiInterface1= APIClient.getClient().create(APIInterface.class);
        service_type = getArguments().getString(Const.PassParam.SERVICE_TYPE);
        branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
        branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
        branch_name_to = getArguments().getString(Const.PassParam.BRANCH_NAME_TO);
        branch_id_to = getArguments().getString(Const.PassParam.BRANCH_ID_TO);
        prefUtils = PrefUtils.getInstance(getContext());
        layoutCarDetail = view.findViewById(R.id.layoutCarDetail);
        etReceiverName = view.findViewById(R.id.etReceiverName);
        etReceiverNumber = view.findViewById(R.id.etReceiverPhone);
        etReceiverID = view.findViewById(R.id.etIDNumber);
        etCarName = view.findViewById(R.id.etCarName);
        etCarModel = view.findViewById(R.id.etCarModel);
        etCarPlate = view.findViewById(R.id.etPlateNumber);
        atvCars = view.findViewById(R.id.atvCars);
        from=view.findViewById(R.id.from);
        to=view.findViewById(R.id.to);
        idnumber=view.findViewById(R.id.IDnumber);
        from.setText(branch_name);
        to.setText(branch_name_to);
        atvNationality = view.findViewById(R.id.atvNationality);
        nextBtn = view.findViewById(R.id.btnNext);
        btnBack = view.findViewById(R.id.btnBack);
        Time=view.findViewById(R.id.time);
        YDate=view.findViewById(R.id.year);
        MDate=view.findViewById(R.id.month);
        DDate=view.findViewById(R.id.day);
        atvnationallityofowner=view.findViewById(R.id.atvnationalityofowner);
        idtypes=view.findViewById(R.id.atvidtype);
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
        atvCars.setOnItemClickListener(new MyClickListener(atvCars));
        atvNationality.setOnItemClickListener(new MyClickListener(atvNationality));
        atvCars.setDropDownVerticalOffset(10);
        getCountries();
        getVehiclesData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (etCarName.getText().toString().isEmpty() && cars.size()<=0) {
                    Toast.makeText(getContext(), getString(R.string.selectcarsize), Toast.LENGTH_SHORT).show();
                } else if (atvNationality.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.selectnationality), Toast.LENGTH_SHORT).show();
                } else if (etReceiverName.getText().toString().isEmpty()) {
                    etReceiverName.setError(getString(R.string.enterreceivername));
                }else if(idnumber.getText().toString().isEmpty()){
                    idnumber.setError(getString(R.string.enteridnumber));
                }
                else if (etReceiverNumber.getText().toString().isEmpty()) {
                    etReceiverNumber.setError(getString(R.string.enterreceivernumber));}
              /*  else if (!et_id_number.getText().toString().isEmpty()&&et_id_number.getText().toString().length()!=10) {
                    et_id_number.setError(getString(R.string.idnumbermustconsistsof10numbers));}
                else if (!et_id_number.getText().toString().isEmpty()&&!et_id_number.getText().toString().startsWith("1")) {
                    et_id_number.setError(getString(R.string.idnumbermustconsistsof10numbers));}*/
                    else if(atvNationality.getText().toString().equals(getString(R.string.saudia)) && !idnumber.getText().toString().startsWith("1")){
                    Toast.makeText(getContext(), getString(R.string.idnumberstartwith1), Toast.LENGTH_SHORT).show();;
                  //   idnumber.requestFocus();
                } else if (atvNationality.getText().toString().isEmpty()) {
                    atvNationality.setError(getString(R.string.selectidnumber));
                    }


                    else if(idnumber.getText().toString().isEmpty()){
                    idnumber.setError(getString(R.string.enteridnumber));
                 //   idnumber.requestFocus();

                }
                else if(etReceiverNumber.getText().length() < 9 ){
               // etReceiverNumber.setError(getString(R.string.enterreceivernumber));
                    Toast.makeText(getContext(), getString(R.string.enterphonenumber), Toast.LENGTH_SHORT).show();
                    //   idnumber.requestFocus();

                }
                else if(idnumber.getText().length() < 9 ){
                    // etReceiverNumber.setError(getString(R.string.enterreceivernumber));
                    Toast.makeText(getContext(), getString(R.string.enteridnumber), Toast.LENGTH_SHORT).show();
                    //   idnumber.requestFocus();
                }


                 else {

                    Bundle bundle = new Bundle();
                    bundle.putString(Const.PassParam.BRANCH_NAME, branch_name);
                    bundle.putString(Const.PassParam.BRANCH_ID, branch_id);
                    bundle.putString(Const.PassParam.BRANCH_NAME_TO, branch_name_to);
                    bundle.putString(Const.PassParam.BRANCH_ID_TO, branch_id_to);
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
                    bundle.putString(Const.PassParam.RECEIVER_NATIONAL_NUMBER, idnumber.getText().toString());
                    bundle.putString(Const.PassParam.RECEIVER_COUNTRY, country_id);
                    bundle.putString(Const.PassParam.RECEIVER_NUMBER, etReceiverNumber.getText().toString());
                    bundle.putString("platetype", plate_type);
                    bundle.putString("nationality_id", nationality_id);
                    bundle.putString("id_typenumber", id_typenumber);
                    bundle.putString(Const.PassParam.PIATE_NUMBER, user_plate_number);
                    bundle.putString("Date",YDate.getText().toString()+"-"+MDate.getText().toString()+"-"+DDate.getText().toString()+" "+Time.getText());
                     intent = new Intent(getContext(), OrderSummaryActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("carid",carid);
                    if (Const.SERVICE_TYPE.equalsIgnoreCase(Const.CarShip)) {
                        if(service_type.equals(getResources().getString(R.string.international))){
                            condtions();
                        }
                        else{
                           startActivity(intent);}
                    }
                    else{
                        startActivity(intent);}
                }
                break;
            case R.id.btnBack:
                getActivity().onBackPressed();
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
    private void condtions() {
        Dialog dialog=new Dialog(getContext(),R.style.custom_dialogue);

        dialog.setContentView(R.layout.condtions);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        TextView title=dialog.findViewById(R.id.condtiontitle);
        title.setText(getString(R.string.shippingrequirment));
        Button comfirm;
        comfirm=dialog.findViewById(R.id.confirm_conditions);
        WebView webView=dialog.findViewById(R.id.condtions);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        String pdf="";
        if(sharedPreferences.getString("language","").equals("ar")){
             pdf = "http://93.112.44.39/docs/terms_of_international.pdf";
             comfirm.setText("تاكيد");
        }
        else{
             pdf = "http://93.112.44.39/docs/agreement_en.pdf";
            comfirm.setText("Confirm");

        }

        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        setUpLocale();
       comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(intent);
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }
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
                    SharedPreferences sharedPreferences1=getContext().getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences1.getString("language","").equals("ar")){
                    for (int j = 0; j < vehiclesDataArrayList.size(); j++) {
                        if (atvCars.getAdapter().getItem(i).toString().equalsIgnoreCase(vehiclesDataArrayList.get(j).getMakerName()
                        +" - "+vehiclesDataArrayList.get(j).getPlateNumber())) {
                            car_model_id = Integer.toString(vehiclesDataArrayList.get(j).getModelId());
                            car_size_id = vehiclesDataArrayList.get(j).getVehicleTypeId().toString();
                            maker_id = Integer.toString(vehiclesDataArrayList.get(j).getVehicleMakerId());
                            id_number = vehiclesDataArrayList.get(j).getIdNumber();
                            owner_name = vehiclesDataArrayList.get(j).getOwnerName();
                            user_phone_number = vehiclesDataArrayList.get(j).getPhoneNumber();
                            user_plate_number = vehiclesDataArrayList.get(j).getPlateNumber();
                            plate_type=vehiclesDataArrayList.get(j).getPlateType();
                            car_size = vehiclesDataArrayList.get(j).getTypeName();
                            carid=Integer.toString(vehiclesDataArrayList.get(j).getCar_id());
                            nationality_id=vehiclesDataArrayList.get(j).getOwnerNationality();
                            id_typenumber=vehiclesDataArrayList.get(j).getOwnerIdType();
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
                            etCarName.setText(vehiclesDataArrayList.get(j).getMakerName());
                            etCarPlate.setText(vehiclesDataArrayList.get(j).getPlateNumber());}
                            retval=true;
                            break;
                        }}}
                    else{
                        for (int j = 0; j < vehiclesDataArrayList.size(); j++) {
                        if (atvCars.getAdapter().getItem(i).toString().equalsIgnoreCase(vehiclesDataArrayList.get(j).getMakerName()
                                +" - "+vehiclesDataArrayList.get(j).getPlateNumber())) {
                            car_model_id = Integer.toString(vehiclesDataArrayList.get(j).getModelId());
                            car_size_id = vehiclesDataArrayList.get(j).getVehicleTypeId().toString();
                            maker_id = Integer.toString(vehiclesDataArrayList.get(j).getVehicleMakerId());
                            id_number = vehiclesDataArrayList.get(j).getIdNumber();
                            owner_name = vehiclesDataArrayList.get(j).getOwnerName();
                            user_phone_number = vehiclesDataArrayList.get(j).getPhoneNumber();
                            user_plate_number = vehiclesDataArrayList.get(j).getPlateNumber();
                            nationality_id=vehiclesDataArrayList.get(j).getOwnerNationality();
                            id_typenumber=vehiclesDataArrayList.get(j).getOwnerIdType();
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
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
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
        UiUtils.showLoadingDialog(getContext());
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
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences.getString("language","").equals("ar")){
                    for (int i = 0; i < vehiclesDataArrayList.size(); i++) {
                        carsList.add(vehiclesDataArrayList.get(i).getMakerName()
                                +" - "+vehiclesDataArrayList.get(i).getPlateNumber());
                    }
                }
                else {
                        for (int i = 0; i < vehiclesDataArrayList.size(); i++) {
                            carsList.add(vehiclesDataArrayList.get(i).getMakerName()
                                    + " - " + vehiclesDataArrayList.get(i).getPlateNumber());

                        }
                    }}


            } else if (response.errorBody() != null) {
                // ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                UiUtils.hideLoadingDialog();
                Toast.makeText(getContext(), getString(R.string.error_in_getting_data), Toast.LENGTH_SHORT).show();
            }
            vehiclesModel = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, carsList);
            atvCars.setAdapter(vehiclesModel);
            vehiclesModel.setNotifyOnChange(true);
        }


        @Override
        public void onFailure(Call<GetVehiclesResponse> call, Throwable t) {
            UiUtils.hideLoadingDialog();
            Toast.makeText(getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
          //  startActivity(new Intent(getContext(), GetStartedActivity.class));
          //  getActivity().finishAffinity();
                doLogoutUser();
        }
    });
    }
    protected void doLogoutUser() {
        UiUtils.showLoadingDialog(getContext());
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
                        PrefHelper.setUserLoggedOut(getContext());
                        Intent i = new Intent(getContext(), GetStartedActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        UiUtils.showShortToast(getContext(), loginResponse.optString(APIConsts.Params.ERROR));
                        prefUtils.setValue(PrefKeys.IS_LOGGED_IN, false);
                        startActivity(new Intent(getContext(), GetStartedActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getContext())) {
                    UiUtils.showShortToast(getContext(), getString(R.string.may_be_your_is_lost));
                }
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
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
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
            nationalityModel = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, countriesList);
            atvNationality.setAdapter(nationalityModel);

        }
    }
    private void getCountries1() {
        nationallityofowner = new ArrayList<>();
        idtype=new ArrayList<>();
        plateTypeList=new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
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
            ArrayAdapter<String> idtypeadpt=new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, idtype);
            idtypes.setAdapter(idtypeadpt);
            ArrayAdapter<String>  nationalityofowner = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nationallityofowner);
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
        Dialog dialog = new Dialog(getContext(), R.style.custom_dialogue);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_add_vehicle);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        Button btnCancel = dialog.findViewById(R.id.cancel);
        fristitem = dialog.findViewById(R.id.fristletter);
        seconditem = dialog.findViewById(R.id.secondletter);
        thirditem = dialog.findViewById(R.id.thirdletter);
        String[] platelettters={"أ","ب","ح","د","ر","س","ص","ط","ع","ق","ك","ل","م","ن","ه","و","ى"};
        ArrayAdapter plateletttersadpt=new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line,platelettters);
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

            if (plateTypeUpdate.equalsIgnoreCase(getString(R.string.others))||plateTypeUpdate.equalsIgnoreCase(getString(R.string.customscard))) {
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
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
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
        modelAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, carMakers);
        plateAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, plateTypeList);
        carColorAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, carColors);
        carYearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, carYears);
        atvModel.setAdapter(modelAdapter);
        atvPlate.setAdapter(plateAdapter);
        atvCarColor.setAdapter(carColorAdapter);
        atvCarYear.setAdapter(carYearAdapter);
        atvModel.setOnItemClickListener(new DetailFormFragment.MyClickListener1(atvModel));
        atvCarSize.setOnItemClickListener(new DetailFormFragment.MyClickListener1(atvCarSize));
        atvPlate.setOnItemClickListener(new DetailFormFragment.MyClickListener1(atvPlate));
        atvCarColor.setOnItemClickListener(new DetailFormFragment.MyClickListener1(atvCarColor));
        atvCarYear.setOnItemClickListener(new DetailFormFragment.MyClickListener1(atvCarYear));
        //atvCarSize.setDropDownWidth(250);
        //  atvModel.setDropDownWidth(250);
        // atvCarColor.setDropDownWidth(250);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (atvCarSize.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.selectcarsize), Toast.LENGTH_SHORT).show();
                } else if (atvModel.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.selectcarmodel), Toast.LENGTH_SHORT).show();
                } else if (atvPlate.getText().toString().isEmpty()) {
                    atvPlate.setError(getString(R.string.selectplatetype));
                } else if (et_id_number.getText().toString().isEmpty()) {
                    et_id_number.setError(getString(R.string.selectidnumber));
                } else if (et_owner_name.getText().toString().isEmpty()) {
                    et_owner_name.setError(getString(R.string.selectownername));
                } else  {
                    completePlateNo = plateDigit + plateLetter;
                    if (completePlateNo.length() != 7) {
                        completePlateNo = et_piate_number.getText().toString();
                    }
                    if (addBtn.getText().toString().equalsIgnoreCase(getString(R.string.add))) {
                        dialog.cancel();
                        if (atvPlate.getText().toString().equals(getString(R.string.others)) || atvPlate.getText().toString().equalsIgnoreCase(getString(R.string.customscard))) {

                            addVehicles(et_owner_name.getText().toString(), et_piate_number.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), atvCarSize.getText().toString(), type_name, atvCarColor.getText().toString(), atvCarYear.getText().toString());
                        } else {
                            addVehicles(et_owner_name.getText().toString(), fristitem.getText().toString() + seconditem.getText().toString() + thirditem.getText().toString() + et_plate_saudi.getText().toString(), et_id_number.getText().toString(), "1355316535", car_maker_id, car_size_id, modelID, atvModel.getText().toString(), atvPlate.getText().toString(), atvCarSize.getText().toString(), type_name, atvCarColor.getText().toString(), atvCarYear.getText().toString());

                        }
                    } else {
                        dialog.cancel();


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

    private void getnatinalityandidnumber() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
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
                .url(APIConsts.Urls.BASE_URL+"api/car/add_car")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();

        UiUtils.showLoadingDialog(getContext());

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

                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
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
                                    carSizeAdapter = new ArrayAdapter<>(getContext(), R.layout.item_drop_down, carSizes);
                                    atvCarSize.setAdapter(carSizeAdapter);
                                }
                                break;
                            }
                        }

                    }
                    else{
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
                                    carSizeAdapter = new ArrayAdapter<>(getContext(), R.layout.item_drop_down, carSizes);
                                    atvCarSize.setAdapter(carSizeAdapter);
                                }
                                break;
                            }
                        }}
                    break;

                case R.id.atvCarType:
                    SharedPreferences sharedPreferences2=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
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
                    SharedPreferences sharedPreferences1=getContext().getSharedPreferences("lang", MODE_PRIVATE);
                    if(sharedPreferences1.getString("language","").equals("ar")){
                        for (int m = 0; m < carSizes.size(); m++) {
                        if(atvPlate.getAdapter().getItem(i).toString().equalsIgnoreCase(plateTypeList.get(m))){
                            plateType ="others";
                        }}}
                        else if(atvPlate.getAdapter().getItem(i).toString().equals("لوحه سعوديه")){
                            plateType ="saudia";

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
                .url("http://93.112.44.39/api/car/add_car")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .build();

        UiUtils.showLoadingDialog(getContext());

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
   /* private void getVehiclesData1() {
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
                    myVehiclesAdapter = new MyVehiclesAdapter(getContext(), vehiclesDataArrayList, AddVehiclesActivity.this::onDelete, AddVehiclesActivity.this::onEdit);
                    rvVehicles.setAdapter(myVehiclesAdapter);
                    myVehiclesAdapter.notifyDataSetChanged();
                } else if (response.errorBody() != null) {
                    // ApiError message = new Gson().fromJson(response.errorBody().charStream(), ApiError.class);
                    Toast.makeText(getContext(), "error in getting data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetVehiclesResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();

            }
        });*/
    }
