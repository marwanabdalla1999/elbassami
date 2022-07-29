package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.R;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.Models.checkphonenumber;
import com.albassami.logistics.network.Models.errors;
import com.albassami.logistics.ui.activity.sign_up1;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Params.DATA;


public class sign_in_phone extends Fragment {
    APIInterface apiInterface;
 EditText phone;
 AutoCompleteTextView countrycode;
 Button next;
 String[] codes={"966- SA"};
ArrayAdapter<String> codesAdapter;
TextView txtphone;
String unique_id,codetest,token,timezone,picture,idnumber;
int id;
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
View view=inflater.inflate(R.layout.fragment_sign_in_phone, container, false);
codesAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,codes);
txtphone=view.findViewById(R.id.user_phone);
countrycode=view.findViewById(R.id.countrycode);
countrycode.setAdapter(codesAdapter);

countrycode.setOnClickListener(i->{countrycode.showDropDown();});
countrycode.setText(codes[0]);
countrycode.setDropDownHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
phone=view.findViewById(R.id.user_phone);
next=view.findViewById(R.id.nextbtn);
apiInterface = APIClient.getClient().create(APIInterface.class);
next.setOnClickListener(i->{
    String[] code = countrycode.getText().toString().split("-", 2);
   if(phone.getText().toString().length()<8&&countrycode.getText().toString().isEmpty()){
       Toast.makeText(getContext(), "please enter valid phone number and country code", Toast.LENGTH_SHORT).show();
   }
   else {
       UiUtils.showLoadingDialog(getContext());
       checkphonenumber(phone.getText().toString(),code[0]);
   }});

return view;
    }

    void checkphonenumber(String phone,String code){
       // SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        Call<String> call=apiInterface.checkphonenumber("ar"
                ,phone,code
                ,"android");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
              //  Log.d("dataaaaaaaaaaa", "onResponse: "+new Gson().toJson(response.body()));

                JSONObject phonerespone = null;
                try {
                    phonerespone = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (phonerespone != null) {
                    if (phonerespone.optString("unique_id")!=null &&!phonerespone.optString("unique_id").isEmpty()) {

                            recivecode(phonerespone,code,phone);
                        }
                        else {
                            txtphone.setError(getString(R.string.thisphoneisalreadyused));
                            txtphone.requestFocus();
                        }

                } else {
                    txtphone.setError(getString(R.string.thisphoneisalreadyused));
                    txtphone.requestFocus();
                }

                // errors errors= (com.albassami.logistics.network.Models.errors) response.body().geterrors().get(0);
                //  if(errors!=null){
                //      txtphone.setError(errors.getmobile());
                //    txtphone.requestFocus();
                //}
                //else{


                //    }

                 if(response.errorBody()!=null)

            {
                txtphone.setError(getString(R.string.thisphoneisalreadyused));
                txtphone.requestFocus();
            }
        }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });
    }


private void recivecode(JSONObject phonerespone,String code,String phone){
    unique_id = phonerespone.optString("unique_id");
    id = phonerespone.optInt("id");
    codetest = phonerespone.optString("mobile_verification_code");
    token = phonerespone.optString("token");
    timezone = phonerespone.optString("timezone");
    picture = phonerespone.optString("picture");
    idnumber= phonerespone.optString("token_expiry");

    Bundle bundle = new Bundle();
    bundle.putString("from", "signup");
    bundle.putString("code", code);
    bundle.putString("phonenumber", phone);
    bundle.putString("codetest", codetest);
    bundle.putString("unique_id", unique_id);
    bundle.putString("token", token);
    bundle.putString("timezone", timezone);
    bundle.putString("picture", picture);
    bundle.putString("idnumber", idnumber);
    bundle.putInt("id", id);
    sign_in_code sign_in_code = new sign_in_code();
    sign_in_code.setArguments(bundle);
    sign_up1.getInstance().addFragment(sign_in_code, false, "sign_in_code", true);
}

}
