package com.albassami.logistics.ui.Fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.R;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.ui.activity.sign_up1;
import com.alimuzaffar.lib.pin.PinEntryEditText;

import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class sign_in_code extends Fragment {
    APIInterface apiInterface;
    String phonenumber;
    String code,codetest,unique_id,token,timezone,picture,idnumber;
    TextView textView;
PinEntryEditText pinEntryEditText;
int id;
TextView resend,resend1;
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
        View view=inflater.inflate(R.layout.fragment_sign_in_code, container, false);
    textView=view.findViewById(R.id.textofphonenumber2);
    pinEntryEditText=view.findViewById(R.id.txt_pin_entry);
    resend=view.findViewById(R.id.resend);
        resend1=view.findViewById(R.id.resend2);
    resend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(resend1.getVisibility()==View.VISIBLE){

            }else{
            resend();
            countdown();
            }
        }
    });
    init();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    textView.setText(getString(R.string.pleaseentertheotpthatsentin)+phonenumber);
    pinEntryEditText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
    @Override
    public void onPinEntered(CharSequence str) {
          if(getArguments().getString("from").equals("forgetpassword")){
              if(pinEntryEditText.getText().toString().equals(codetest)){
                  Bundle bundle=new Bundle();
                  bundle.putString("code",code);
                  bundle.putString("codetest",codetest);
                  bundle.putString("phone",phonenumber);
                  set_new_password set_new_password=new set_new_password();
                  set_new_password.setArguments(bundle);
                  sign_up1.getInstance().addFragment(set_new_password,false,"sign up code",true);

              }
          }
          else{
        Call<String> call= apiInterface.checkotp(phonenumber,code,str.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    JSONObject responsedata=null;
                    try{
                    responsedata=new JSONObject(response.body());}
                    catch (Exception e){}
                    if(responsedata!=null){
                        if(responsedata.optString("message").equals("Mobile Verified")){
                        Bundle bundle=new Bundle();
                        bundle.putString("unique_id",unique_id);
                        bundle.putString("phone",phonenumber);
                        bundle.putString("token",token);
                        bundle.putString("timezone",timezone);
                        bundle.putString("picture",picture);
                        bundle.putString("idnumber",idnumber);
                        bundle.putString("code",code);
                        bundle.putInt("id",id);
                        Signup_information signup_information=new Signup_information();
                        signup_information.setArguments(bundle);
                        sign_up1.getInstance().addFragment(signup_information,false,"signup_information",true);
                    }
                    else{
                            pinEntryEditText.setError(getString(R.string.codeisInvalid));
                            pinEntryEditText.requestFocus();
                            Toast.makeText(getContext(), getString(R.string.codeisInvalid), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else{
                        pinEntryEditText.setError(getString(R.string.codeisInvalid));
                        pinEntryEditText.requestFocus();
                        Toast.makeText(getContext(), getString(R.string.codeisInvalid), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    pinEntryEditText.setError(getString(R.string.codeisInvalid));
                    pinEntryEditText.requestFocus();
                    Toast.makeText(getContext(), getString(R.string.codeisInvalid), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
            }
        });}

    }
});

        return view;

    }

    private void countdown() {
        resend1.setVisibility(View.VISIBLE);
        resend.setTextColor(Color.BLACK);
        CountDownTimer countDownTimer=new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resend1.setText(Long.toString(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
              resend1.setVisibility(View.GONE);
                resend.setTextColor(Color.rgb(76,175,80));
            }
        };
        countDownTimer.start();
    }

    private void resend() {
        Call<String> call= apiInterface.resendotp(phonenumber,code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&&response.body()!=null){
                    JSONObject responsedata=null;
                    try{
                        responsedata=new JSONObject(response.body());}
                    catch (Exception e){}
                    if(responsedata!=null){
                        Toast.makeText(getContext(), getString(R.string.resended), Toast.LENGTH_SHORT).show();





                        }}}



            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {

        phonenumber=getArguments().getString("phonenumber");
        code=getArguments().getString("code");
        unique_id=getArguments().getString("unique_id");
        codetest=getArguments().getString("codetest");
        token=getArguments().getString("token");
        timezone=getArguments().getString("timezone");
        picture=getArguments().getString("picture");
        id=getArguments().getInt("id");
        idnumber=getArguments().getString("idnumber");
    }


}
