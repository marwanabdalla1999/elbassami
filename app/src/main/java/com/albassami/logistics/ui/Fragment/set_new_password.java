package com.albassami.logistics.ui.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.ui.activity.SignInActivity;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class set_new_password extends Fragment {

String phone,code,otp;
TextView password,confpassword;
Button confirm;
APIInterface apiInterface;
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
        View view=inflater.inflate(R.layout.fragment_set_new_password, container, false);
        init(view);
        confirm.setOnClickListener(i->checkpassword());

        return view;
    }

    private void checkpassword() {
        if(password.getText().toString().isEmpty()||confpassword.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "please enter the new password", Toast.LENGTH_SHORT).show();
        }
        else if(!password.getText().toString().equals(confpassword.getText().toString())){
            confpassword.setError("this password doesn't match");
        }
        else{
            UiUtils.showLoadingDialog(getContext());
Call<String> call=apiInterface.resetpassword(phone,code,otp,password.getText().toString(),confpassword.getText().toString());
call.enqueue(new Callback<String>() {
    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        UiUtils.hideLoadingDialog();
        if(response.isSuccessful()&&response.body()!=null){

                Toast.makeText(getContext(), getString(R.string.passwordhasbeenchanged), Toast.LENGTH_SHORT).show();
                getContext().startActivity(new Intent(getContext(), SignInActivity.class));


               // Toast.makeText(getContext(), getString(R.string.errorinchangingpassword), Toast.LENGTH_SHORT).show();

            }

        }



    @Override
    public void onFailure(Call<String> call, Throwable t) {
        UiUtils.hideLoadingDialog();
        Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
    }
});

        }
    }

    private void init(View view) {
        phone=getArguments().getString("phone");
        code=getArguments().getString("code");
        otp=getArguments().getString("codetest");
        password=view.findViewById(R.id.user_password);
        confpassword=view.findViewById(R.id.confpassword);
        confirm=view.findViewById(R.id.confirm);
        apiInterface= APIClient.getClient().create(APIInterface.class);
    }


}
