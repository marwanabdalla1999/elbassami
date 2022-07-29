package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.dto.response.CountriesData;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.ui.activity.MainActivity;
import com.albassami.logistics.ui.activity.SignUpNextActivity;
import com.albassami.logistics.ui.activity.sign_up1;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Signup_information extends Fragment {

CustomRegularEditView fristname,lastname,ID,password,confirmpassowrd;
CheckBox acceptterms;
Button signup;
APIInterface apiInterface;
    PrefUtils prefUtils;
    AutoCompleteTextView nationality,idtype;
    ArrayAdapter nationalityadpt,idtypeadpt;
    ArrayList<String> idtypelist;
    ArrayList<CountriesData> countriesDataArrayList;
    ArrayList<String> countriesList;
    GetPriceDataResponse dataResponse;
    String nationalityid,idtypenumber;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpLocale();
        View view=inflater.inflate(R.layout.fragment_signup_information, container, false);
        prefUtils = PrefUtils.getInstance(getContext());
init(view);
apiInterface= APIClient.getClient().create(APIInterface.class);
signup.setOnClickListener(i->{
    check_Is_data_corect();
});
    return view;

    }
private  void check_Is_data_corect(){
        if(fristname.getText().toString().isEmpty()||
                lastname.getText().toString().isEmpty()||
                ID.getText().toString().isEmpty()||
    password.getText().toString().isEmpty()||
    confirmpassowrd.getText().toString().isEmpty()){
            Toast.makeText(getContext(), getString(R.string.pleasecompletetheform), Toast.LENGTH_SHORT).show();
        }
        else if(!acceptterms.isChecked()){
            Toast.makeText(getContext(), getString(R.string.Pleaseacceptthetermsandconditionandprivacypolicy), Toast.LENGTH_SHORT).show();

        }
        else if(!password.getText().toString().equals(confirmpassowrd.getText().toString())){
            confirmpassowrd.setError(getString(R.string.thepassworddoesntmatch));
            confirmpassowrd.requestFocus();

        }
        else if(ID.getText().toString().length()!=10){
            ID.setError(getString(R.string.idnumbermustconsistsof10numbers));
            ID.requestFocus();
        }
        else if(ID.getText().toString().startsWith("1") && ID.getText().toString().startsWith("2")){
            ID.setError(getString(R.string.idnumbermustconsistsof10numbers));
            ID.requestFocus();
        }
        else{
            signupuser();
        }


}
    private void getnatinalityandidnumber() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(nationality.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName_ar())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode();
                }

            }}
        else{
            for(int k=0; k<dataResponse.getData().get(0).getCountries().size();k++){
                if(nationality.getText().toString().equals(dataResponse.getData().get(0).getCountries().get(k).getName())){
                    nationalityid=dataResponse.getData().get(0).getCountries().get(k).getCode();
                }

            }

        }
        if(sharedPreferences.getString("language","").equals("ar")){
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtype.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("}")))){

                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")-2);
                }

            }
        }
        else{
            for(int b=0; b<dataResponse.getData().get(0).getId_card_types().size();b++){
                if(idtype.getText().toString().equals(dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")+8,
                        dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_ar")-2))){
                    idtypenumber=dataResponse.getData().get(0).getId_card_types().get(b).toString().substring(dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("code")+5,
                            dataResponse.getData().get(0).getId_card_types().get(b).toString().indexOf("name_en")-2);
                }

            }
        }}
    private void signupuser(){
        getnatinalityandidnumber();
        String unique_id=getArguments().getString("unique_id");
    UiUtils.showLoadingDialog(getContext());
    Call<String> call=apiInterface.doSignUpUser("ar",unique_id,ID.getText().toString(),fristname.getText().toString(),lastname.getText().toString()
    ,"Saudi Arabia",password.getText().toString(),confirmpassowrd.getText().toString(),idtypenumber,nationalityid);
    call.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            UiUtils.hideLoadingDialog();
            if(response.isSuccessful()&&response.body()!=null){
                JSONObject signUpResponse=null;
                try{
                    signUpResponse = new JSONObject(response.body());
                }catch (Exception e){}
if(signUpResponse != null){
    if (signUpResponse.optString("message").equals("succcess")){
        JSONObject data = signUpResponse.optJSONObject("data");
        if(data!=null){
        loginUserInDevice(data, APIConsts.Constants.MANUAL_LOGIN);}
    }

}
else{
        Toast.makeText(getContext(), getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();
    }

            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            UiUtils.hideLoadingDialog();
            Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();

        }
    });

}


    public void init(View view) {
    fristname=view.findViewById(R.id.fristname);
        lastname=view.findViewById(R.id.lastname);
        ID=view.findViewById(R.id.ID);
        password=view.findViewById(R.id.user_password);
        confirmpassowrd=view.findViewById(R.id.confpassword);
        acceptterms=view.findViewById(R.id.acceptterms);
        signup=view.findViewById(R.id.signup);
        nationality =view.findViewById(R.id.atvNationality4);
        idtype=view.findViewById(R.id.atvNationality5);

        getCountries();

    }
    public void loginUserInDevice(JSONObject data, String loginBy) {
      //  String id=Integer.toString(data.optInt(APIConsts.Params.USER_ID));
       // Toast.makeText(getContext(),id, Toast.LENGTH_SHORT).show();
        PrefHelper.setUserLoggedIn(getContext(), getArguments().getInt("id")
                , getArguments().getString("token")
                , loginBy
                , getArguments().getString("code")
                , getArguments().getString("phone")
                , fristname.getText().toString()+" "+lastname.getText().toString()
                , ID.getText().toString()
                , fristname.getText().toString()
                , lastname.getText().toString()
                , getArguments().getString("picture")
                , "cod"
                , getArguments().getString("timezone"),
                "male"
                ,nationality.getText().toString()
                ,idtype.getText().toString()
        );
        Intent toHome = new Intent(getContext(), MainActivity.class);
        startActivity(toHome);
        getActivity().finish();
    }
    private void getCountries() {
        countriesDataArrayList = new ArrayList<>();
        idtypelist=new ArrayList<>();
        countriesList = new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
            // idtypes=dataResponse.getData().get(0).getId_card_types();
            SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
            if(sharedPreferences.getString("language","").equals("ar")){
                countriesList.clear();
                idtypelist.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    countriesList.add(dataResponse.getData().get(0).getCountries().get(i).getName_ar());
                }
                for (int j = 0; j < dataResponse.getData().get(0).getId_card_types().size(); j++) {
                    idtypelist.add(dataResponse.getData().get(0).getId_card_types().get(j).toString().substring(dataResponse.getData().get(0).getId_card_types().get(j).toString().indexOf("name_ar")+8,
                            dataResponse.getData().get(0).getId_card_types().get(j).toString().indexOf("}")));
                }
            }
            else{
                countriesList.clear();
                idtypelist.clear();
                for (int i = 0; i < dataResponse.getData().get(0).getCountries().size(); i++) {
                    countriesList.add(dataResponse.getData().get(0).getCountries().get(i).getName());
                    //  idtypelist.add(dataResponse.getData().get(0).getId_card_types().get(i).getName_en());
                }
                for (int j = 0; j < dataResponse.getData().get(0).getId_card_types().size(); j++) {
                    idtypelist.add(dataResponse.getData().get(0).getId_card_types().get(j).toString().substring(dataResponse.getData().get(0).getId_card_types().get(j).toString().indexOf("name_en")+8,
                            dataResponse.getData().get(0).getId_card_types().get(j).toString().indexOf("name_ar")-2));

                }
            }
            idtypeadpt=new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, idtypelist);
            idtype.setAdapter(idtypeadpt);
            nationalityadpt = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, countriesList);
            nationality.setAdapter(nationalityadpt);
            nationality.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nationality.showDropDown();
                }
            });
            idtype.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idtype.showDropDown();
                }
            });
        }
    }
}
