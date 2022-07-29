package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.albassami.logistics.ui.activity.sign_up1;
import com.google.android.material.textfield.TextInputLayout;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.ui.activity.SignInActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 1/5/2017.
 */

public class ForgotpassFragment extends Fragment {

    APIInterface apiInterface;
    EditText phone;
    AutoCompleteTextView countrycode;
    Button next;
    String[] codes={"966- SA"};
    ArrayAdapter<String> codesAdapter;
    TextView txtphone;
    String unique_id,codetest,token,timezone,picture;
    private static ForgotpassFragment instance1=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sign_in_phone, container, false);
        codesAdapter=new ArrayAdapter<>(getContext(),R.layout.item_drop_down,codes);
        txtphone=view.findViewById(R.id.user_phone);
        countrycode=view.findViewById(R.id.countrycode);
        countrycode.setAdapter(codesAdapter);
        instance1 = this;
        countrycode.setOnClickListener(i->{countrycode.showDropDown();});
        countrycode.setText(codes[0]);
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
        Call<String> call=apiInterface.resend_otp(phone,code);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if(response.isSuccessful()&&response.body()!=null) {
                    if(response.body().equals("Invalid User Mobile No")){
                        txtphone.setError(getString(R.string.phonenumberis_invalide));
                        txtphone.requestFocus();
                    }
                    else{
                    Bundle bundle=new Bundle();
                    bundle.putString("codetest",response.body());
                        bundle.putString("code", code);
                        bundle.putString("phonenumber", phone);
                        bundle.putString("from","forgetpassword");
                    sign_in_code sign_in_code = new sign_in_code();
                     sign_in_code.setArguments(bundle);
                   sign_up1.getInstance().addFragment(sign_in_code, false, "sign_in_code", true);
                }}
                else{
                        txtphone.setError(getString(R.string.phonenumberis_invalide));
                        txtphone.requestFocus();
                    }

                    // errors errors= (com.albassami.logistics.network.Models.errors) response.body().geterrors().get(0);
                    //  if(errors!=null){
                    //      txtphone.setError(errors.getmobile());
                    //    txtphone.requestFocus();
                    //}
                    //else{


                    //    }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "errorfail", Toast.LENGTH_SHORT).show();
                UiUtils.hideLoadingDialog();
            }
        });
    }



}
