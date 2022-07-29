package com.albassami.logistics.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.CarDOBData;
import com.albassami.logistics.dto.response.CarsDOBResponse;
import com.albassami.logistics.dto.response.CreateOrderResponse;
import com.albassami.logistics.dto.response.TowingDataResponse;
import com.albassami.logistics.dto.response.TowingResponse;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.network.Location.LocationHelper;
import com.albassami.logistics.ui.activity.MainActivity;
import com.albassami.logistics.ui.activity.OrderSummaryActivity;
import com.albassami.logistics.ui.activity.RideLaterActivity;
import com.albassami.logistics.ui.activity.SelectLocationActivity;
import com.albassami.logistics.ui.activity.ThankYouActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class DoorToDoorFragment extends Fragment implements View.OnClickListener {
    private TextView tv_deliver_home, tv_home_pick;
    private LinearLayout layoutDoor;
    private PrefUtils prefUtils;
    private EditText etReceipt, etDestination;
    private String sourceAddress, destinationAddress;
    private ImageView btnBack;
    ArrayAdapter<String> agreementAdapter, towingAdapter, branchesAdapter;
    private AutoCompleteTextView atvAgreement, atvTowing;
    EditText etBranches;
    private String receiptAddress, isFromHome = "home", agreementName, agreementID, agreementLocToID, agreementLocToName, agreementLocFromID, agreementLocFromName;
    private LatLng recieptlatlong;
    private ArrayList<String> agreementList;
    private ArrayList<CarDOBData> agreeAllList;
    String[] branches = {"Riyadh Khurais", "South Jeddah", "Dammam", "Qasim"};
    String[] agreement = {"89108", "23560925", "334489"};
    ArrayList towingTypeList;
    ArrayList<TowingDataResponse> towingTypeListAll;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private int PLACE_REQUEST_CODE = 12;
    private LocationHelper locHelper;
    private String priceTowing;
    private boolean s_click = false, d_click = false, stop_click = false;
    SupportMapFragment search_place_map;
    private GoogleMap googleMap;
    ApiServices apiServices;
    private Button btnNext;
    APIInterface apiInterface;

    LinearLayout paymethods;
TextView card,cash;
String cashcard="";
String homepickup="true",homedeleviry="";
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
        View view = inflater.inflate(R.layout.fragment_door_step, container, false);

        apiServices = RestClient.getApiService();
        prefUtils = PrefUtils.getInstance(getActivity());
        agreementList = new ArrayList<>();
        towingTypeList = new ArrayList<>();
        towingTypeListAll = new ArrayList<>();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getContext());
        tv_deliver_home = view.findViewById(R.id.tvHomeDeliver);
        etDestination = view.findViewById(R.id.etdest);
        tv_home_pick = view.findViewById(R.id.tvHomePick);
        atvAgreement = view.findViewById(R.id.atvAgreement);
        atvAgreement.setDropDownVerticalOffset(10);
        etBranches = view.findViewById(R.id.atvBranch);
        layoutDoor = view.findViewById(R.id.layout_door);
        etReceipt = view.findViewById(R.id.etReceipt);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNext);
        card=view.findViewById(R.id.tvCard1);
        cash=view.findViewById(R.id.tvCash1);

        paymethods=view.findViewById(R.id.paymethods);

        paymentchoice();
        atvAgreement.setOnItemClickListener(new MyClickListener(atvAgreement));
      //  atvTowing.setOnItemClickListener(new MyClickListener(atvTowing));
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        btnNext.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);
        atvAgreement.setOnClickListener(this::onClick);
        tv_deliver_home.setOnClickListener(this::onClick);
        tv_home_pick.setOnClickListener(this::onClick);
        setOnTouchListener();
        tv_home_pick.setTextColor(getContext().getResources().getColor(R.color.white));
        tv_home_pick.setBackgroundResource(R.drawable.grean_with_corner);
        tv_deliver_home.setTextColor(getContext().getResources().getColor(R.color.green));
        tv_deliver_home.setBackgroundResource(R.drawable.white_with_corner);
        getShipLicense(prefUtils.getStringValue(PrefKeys.NATIONAL_ID, ""));
        //getTowingTypes();
        return view;
    }


    private void paymentchoice() {
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) card.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) cash.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                cashcard = "card";
            }
        });
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable cardDrawable = (GradientDrawable) cash.getBackground().mutate();
                cardDrawable.setColor(getResources().getColor(R.color.dark_grey));
                GradientDrawable cashDrawable = (GradientDrawable) card.getBackground().mutate();
                cashDrawable.setColor(getResources().getColor(R.color.et_color));
                cashcard = "cash";
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListener() {
//        etDestination.setOnTouchListener((v, event) -> {
//            Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
//            this.startActivityForResult(intent, PLACE_REQUEST_CODE);
//            if (MotionEvent.ACTION_UP == event.getAction()) {
//                if (!Places.isInitialized()) {
//                    Places.initialize(getActivity().getApplicationContext(), Const.PLACES_AUTOCOMPLETE_API_KEY);
//                }
//                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
//                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                        .build(getContext());
//                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//            }
//            return true; // return is important...
//        });
        etReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SelectLocationActivity.class).putExtra("type","0");
                startActivityForResult(i, PLACE_REQUEST_CODE);
            }
        });
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SelectLocationActivity.class).putExtra("type","0");;
                startActivityForResult(i, PLACE_REQUEST_CODE);
            }
        });
//        etReceipt.setOnTouchListener((v, event) -> {

//            if (MotionEvent.ACTION_UP == event.getAction()) {
//                if (!Places.isInitialized()) {
//                    Places.initialize(getActivity().getApplicationContext(), Const.PLACES_AUTOCOMPLETE_API_KEY);
//                }
//                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
//                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//                        .build(getContext());
//                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//            }
//            return true; // return is important...
//        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    final Place place = Autocomplete.getPlaceFromIntent(data);
                    getActivity().runOnUiThread(() -> {
                        receiptAddress = String.valueOf(place.getAddress());
                        recieptlatlong = place.getLatLng();
                        Const.dest_address = receiptAddress;
                        Const.drop_latlan = recieptlatlong;
                        etReceipt.setText(receiptAddress);
                        etDestination.setText(receiptAddress);
                    });
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i("place error", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                }
            } else if (requestCode == PLACE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                   etReceipt.setText(data.getData().toString());
                   etDestination.setText(data.getData().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (atvAgreement.getText().toString().isEmpty()) {
                    atvAgreement.setError(getString(R.string.pleaseselectagreement));
                } else if (etBranches.getText().toString().isEmpty()) {
                    etBranches.setError(getString(R.string.pleaseselectbranch));
                }
             //   else if(cashcard.isEmpty()||cashcard.equals("")){
               //     Toast.makeText(getContext(), getString(R.string.Pleaseselectpaymentmethod), Toast.LENGTH_SHORT).show();
                //}
                else {
                    updateorder();

                }

                // startActivity(new Intent(getContext(), OrderSummaryActivity.class));
                break;
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.tvHomeDeliver:
                homedeleviry="true";
                homepickup="";
                tv_deliver_home.setTextColor(getContext().getResources().getColor(R.color.white));
                tv_deliver_home.setBackgroundResource(R.drawable.grean_with_corner);
                tv_home_pick.setTextColor(getContext().getResources().getColor(R.color.green));
                tv_home_pick.setBackgroundResource(R.drawable.white_with_corner);
                isFromHome = "deliver";
                etBranches.setText("");
                atvAgreement.setText("");
                etDestination.setHint(getString(R.string.set_destination));
                etReceipt.setHint(getString(R.string.set_pickup));
                etReceipt.setText("");
                etDestination.setText("");
                etDestination.setVisibility(View.VISIBLE);
                etReceipt.setVisibility(View.GONE);
                getDOBLicense(prefUtils.getStringValue(PrefKeys.NATIONAL_ID, ""));

                break;
            case R.id.tvHomePick:
                homedeleviry="";
                homepickup="true";
                tv_home_pick.setTextColor(getContext().getResources().getColor(R.color.white));
                tv_home_pick.setBackgroundResource(R.drawable.grean_with_corner);
                tv_deliver_home.setTextColor(getContext().getResources().getColor(R.color.green));
                tv_deliver_home.setBackgroundResource(R.drawable.white_with_corner);
                isFromHome = "home";
                etReceipt.setText("");
                etDestination.setText("");
                etBranches.setText("");
                atvAgreement.setText("");
                etReceipt.setHint(R.string.set_pickup);
                etDestination.setHint(R.string.destination);
                etDestination.setVisibility(View.GONE);
                etReceipt.setVisibility(View.VISIBLE);
                getShipLicense(prefUtils.getStringValue(PrefKeys.NATIONAL_ID, ""));
                break;
            case R.id.atvAgreement:
                atvAgreement.requestFocus();
                atvAgreement.showDropDown();
                break;

        }
    }

    private void updateorder() {
        UiUtils.showLoadingDialog(getContext());
        OkHttpClient client = new OkHttpClient()
                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("access-token", "access_token_770b27a4c5deac5f11c8cff387a49c533b3071d8")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", "session_id=2564e3f9362ade021f6e677bbf01bd41e00abd40")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();
        apiServices = RestClient.getApiService();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://albassami-pre-production-1489044.dev.odoo.com/api/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiServices  apiServices1=retrofit.create(ApiServices.class);
        Call<String> orderResponseCall = apiServices1.updateorder(agreementName,
                cashcard, "150","", homepickup,homedeleviry,etReceipt.getText().toString(),etDestination.getText().toString());
        orderResponseCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                             Bundle bundle = new Bundle();
                    bundle.putString(Const.PassParam.BRANCH_NAME, agreementLocFromName);
                    bundle.putString(Const.PassParam.BRANCH_ID, agreementLocFromID);
                    bundle.putString(Const.PassParam.BRANCH_NAME_TO, agreementLocToName);
                    bundle.putString(Const.PassParam.BRANCH_ID_TO, agreementLocToID);
                    bundle.putString(Const.PassParam.SERVICE_TYPE, Const.DOOR_TO_DOOR);
                    bundle.putString(Const.PassParam.AGGREMENT_ID, agreementID);
                     bundle.putString("order_id", agreementName);
                    bundle.putString(Const.PassParam.SOURCE_ADDRESS, sourceAddress);
                    bundle.putString(Const.PassParam.DEST_ADDRESS, destinationAddress);
                    bundle.putString(Const.PassParam.TOWING_PRICE, priceTowing);
                    Intent intent = new Intent(getContext(), ThankYouActivity.class);
                    intent.putExtra("order_id",agreementName);
                    startActivity(intent);
                    }

                else if(response.errorBody()!=null) {
                    Toast.makeText(getContext(),getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });


    }

    private void getDOBLicense(String nationalID) {
        agreementList = new ArrayList<>();
        agreeAllList = new ArrayList<>();
        UiUtils.showLoadingDialog(getContext());
        Call<CarsDOBResponse> carsDOBResponseCall = apiServices.getDOBLicense("100000428");
        carsDOBResponseCall.enqueue(new Callback<CarsDOBResponse>() {
            @Override
            public void onResponse(Call<CarsDOBResponse> call, Response<CarsDOBResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        agreeAllList = response.body().getData();
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
                        if(sharedPreferences.getString("language","").equals("ar")){
                        for (int i = 0; i < agreeAllList.size(); i++) {
                            if(response.body().getData().get(i).getLocTo().getHas_satha_service()){
                            agreementList.add("("+response.body().getData().get(i).getSaleLineRecName()+")"+" - ("+response.body().getData().get(i).getLocFrom().getName()+")"
                                    +" - ("+response.body().getData().get(i).getLocTo().getName()+")");
                        }}}
                        else{
                        for (int i = 0; i < agreeAllList.size(); i++) {
                            if(response.body().getData().get(i).getLocTo().getHas_satha_service()){
                            agreementList.add("("+response.body().getData().get(i).getSaleLineRecName()+")"+" - ("+response.body().getData().get(i).getLocFrom().getName_en()+")"
                                    +" - ("+response.body().getData().get(i).getLocTo().getName_en()+")");
                        }
                        }}
                        agreementAdapter = new ArrayAdapter<>(getContext(), R.layout.item_drop_down, agreementList);
                        atvAgreement.setDropDownVerticalOffset(10);
                        atvAgreement.setAdapter(agreementAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CarsDOBResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
            }
        });
    }

    private void getShipLicense(String nationalID) {
        UiUtils.showLoadingDialog(getContext());
        agreementList = new ArrayList<>();
        agreeAllList = new ArrayList<>();
        Call<CarsDOBResponse> carsDOBResponseCall = apiServices.getCsrShipLicense("100000428");
        carsDOBResponseCall.enqueue(new Callback<CarsDOBResponse>() {
            @Override
            public void onResponse(Call<CarsDOBResponse> call, Response<CarsDOBResponse> response) {
                UiUtils.hideLoadingDialog();
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getData() != null) {
                        agreeAllList = response.body().getData();
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
                        if(sharedPreferences.getString("language","").equals("ar")){
                            for (int i = 0; i < agreeAllList.size(); i++) {
                                if(response.body().getData().get(i).getLocFrom().getHas_satha_service()){
                                agreementList.add("("+response.body().getData().get(i).getSaleLineRecName()+")"+" - ("+response.body().getData().get(i).getLocFrom().getName()+")"
                                        +" - ("+response.body().getData().get(i).getLocTo().getName()+")");
                            }}}
                        else{
                            for (int i = 0; i < agreeAllList.size(); i++) {
                                if(response.body().getData().get(i).getLocFrom().getHas_satha_service()){
                                agreementList.add("("+response.body().getData().get(i).getSaleLineRecName()+")"+" - ("+response.body().getData().get(i).getLocFrom().getName_en()+")"
                                        +" - ("+response.body().getData().get(i).getLocTo().getName_en()+")");
                            }}}

                        agreementAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_drop_down, agreementList);
                        atvAgreement.setDropDownVerticalOffset(10);
                        atvAgreement.setAdapter(agreementAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<CarsDOBResponse> call, Throwable t) {
                UiUtils.hideLoadingDialog();
            }
        });
    }

    public class MyClickListener implements AdapterView.OnItemClickListener {

        AutoCompleteTextView ac;

        public MyClickListener(AutoCompleteTextView myAc) {
            ac = myAc;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (ac.getId()) {
                case R.id.atvAgreement:
                    for (int j = 0; j < agreeAllList.size(); j++) {
                        if (atvAgreement.getAdapter().getItem(i).toString().equalsIgnoreCase("("+agreeAllList.get(j).getSaleLineRecName()+")"
                                +" - ("+agreeAllList.get(j).getLocFrom().getName()+")"
                                +" - ("+agreeAllList.get(j).getLocTo().getName()+")")) {
                            agreementName = agreeAllList.get(j).getSaleLineRecName();
                            agreementID = agreeAllList.get(j).getId().toString();
                            etBranches.setText(agreementLocFromName);
                            if (isFromHome.equalsIgnoreCase("home")) {
                                agreementLocToID = agreeAllList.get(j).getLocTo().getId().toString();
                                agreementLocToName = agreeAllList.get(j).getLocTo().getName();
                                agreementLocFromID = agreeAllList.get(j).getLocFrom().getId().toString();
                                agreementLocFromName = agreeAllList.get(j).getLocFrom().getName();
                                etBranches.setText(agreementLocFromName);
                                etReceipt.setHint(getString(R.string.set_pickup));
                                try {
                                    String log = agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLongitude();
                                    String lat = agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLatitude();
                                    double longi = Double.parseDouble(log);
                                    double lati = Double.parseDouble(lat);
                                    sourceAddress = getAddress(longi, lati);
                                    destinationAddress = getAddress(Double.parseDouble(agreeAllList.get(j).getLocTo().getGpsCoordinates().getLongitude()), Double.parseDouble(agreeAllList.get(j).getLocTo().getGpsCoordinates().getLatitude()));
                                    //   setDestination(longi, lati);
                                } catch (NumberFormatException ex) {
                                    System.out.println("not a number" + ex);
                                }

                            } else if (isFromHome.equalsIgnoreCase("deliver")) {

                                agreementLocToID = agreeAllList.get(j).getLocFrom().getId().toString();
                                agreementLocToName = agreeAllList.get(j).getLocFrom().getName();
                                agreementLocFromID = agreeAllList.get(j).getLocTo().getId().toString();
                                agreementLocFromName = agreeAllList.get(j).getLocTo().getName();
                                etBranches.setText(agreementLocFromName);
                                etReceipt.setHint(getString(R.string.set_destination));
                                try {
                                    double longi = Double.parseDouble(agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLongitude());
                                    double lati = Double.parseDouble(agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLatitude());
                                    // setDestination(longi, lati);
                                    sourceAddress = getAddress(longi, lati);
                                    destinationAddress = getAddress(Double.parseDouble(agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLongitude()), Double.parseDouble(agreeAllList.get(j).getLocFrom().getGpsCoordinates().getLatitude()));

                                } catch (NumberFormatException ex) {
                                    System.out.println("not a number" + ex);
                                }

                            }
                            break;
                        }
                    }
                    break;

            }
        }
    }

    private String getAddress(double longitude, double latitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        Locale loc;
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            loc = new Locale("ar");}
        else{
            loc = new Locale("en");
        }
        geocoder = new Geocoder(getContext(),loc);
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();

        return address + " " + city + " " + state + " " + country;

    }

    private void setDestination(double longitude, double latitude) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }


        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        if (isFromHome.equalsIgnoreCase("home")) {
            etReceipt.setText(address + " " + city + " " + state + " " + country);
            etDestination.setVisibility(View.GONE);
            etReceipt.setVisibility(View.VISIBLE);
            etReceipt.setHint(R.string.set_pickup);
            etDestination.setHint(R.string.set_destination);
            Const.source_address = address + " " + city + " " + state + " " + country;
            Const.pic_latlan = new LatLng(latitude, longitude);
        } else if (isFromHome.equalsIgnoreCase("deliver")) {
            etDestination.setText(address + " " + city + " " + state + " " + country);
            etReceipt.setHint(R.string.set_pickup);
            etDestination.setHint(getString(R.string.set_destination));
            etDestination.setVisibility(View.VISIBLE);
            etReceipt.setVisibility(View.GONE);
        }

    }

  /*  private void getTowingTypes() {
        apiInterface = CustomRestClient.getApiService();
        Call<TowingResponse> towingResponseCall = apiInterface.getTowing();
        towingResponseCall.enqueue(new Callback<TowingResponse>() {
            @Override
            public void onResponse(Call<TowingResponse> call, Response<TowingResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    towingTypeListAll = response.body().getData();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        towingTypeList.add(response.body().getData().get(i).getTowingType());
                    }
                    towingAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_drop_down, towingTypeList);
                    atvTowing.setAdapter(towingAdapter);
                }
            }

            @Override
            public void onFailure(Call<TowingResponse> call, Throwable t) {
            }
        });
    }*/
}