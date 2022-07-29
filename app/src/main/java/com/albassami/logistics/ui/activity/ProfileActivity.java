package com.albassami.logistics.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.albassami.logistics.dto.response.CountriesData;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.id_card_types;
import com.albassami.logistics.dto.response.plate_types;
import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularEditView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefHelper;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.NATIONAL_ID;

/**
 * Created by user on 1/7/2017.
 */

public class ProfileActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final int PICK_IMAGE = 100;

    @BindView(R.id.profile_back)
    ImageButton profileBack;
    @BindView(R.id.btn_edit_profile)
    CustomBoldRegularTextView btnEditProfile;
    @BindView(R.id.toolbar_profile)
    Toolbar toolbarProfile;
    @BindView(R.id.actionbar_lay)
    RelativeLayout actionbarLay;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_img_lay)
    RelativeLayout profileImgLay;
    @BindView(R.id.lay_name)
    LinearLayout layName;
    @BindView(R.id.et_profile_email)
    CustomRegularEditView etProfileEmail;
    @BindView(R.id.radio_btn_male)
    RadioButton radioBtnMale;

    RadioButton radio_btn_others;
    @BindView(R.id.radio_btn_female)
    RadioButton radioBtnFemale;
    @BindView(R.id.profile_radioGroup)
    RadioGroup profileRadioGroup;
    @BindView(R.id.etFirstName)
    CustomRegularEditView etFirstName;
    @BindView(R.id.etLastName)
    CustomRegularEditView etLastName;


    @BindView(R.id.id_number)
    CustomRegularEditView id_number;
    private String filePath = "";
    private File cameraFile;
    private Uri uri = null;
    private RadioButton rd_btn;
    private String userMobile;
    RequestBody requestFile;
    PrefUtils prefUtils;
    APIInterface apiInterface;
    private Uri fileToUpload = null;
    boolean isEditMode;
    String countryCode;
    private int RC_STORAGE_PERM = 125;
    String gender;
AutoCompleteTextView nationality,idtype;
 ArrayAdapter nationalityadpt,idtypeadpt;
 ArrayList<String> idtypelist;
    ArrayList<CountriesData> countriesDataArrayList;
    ArrayList<id_card_types> idtypes;
    ArrayList<String> countriesList;
    GetPriceDataResponse dataResponse;
    String nationalityid,idtypenumber;

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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_profile);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        nationality=findViewById(R.id.atvNationality2);
        idtype=findViewById(R.id.atvNationality3);
        idtypelist=new ArrayList<>();
        getCountries();

        ButterKnife.bind(this);
        etProfileEmail.setEnabled(false);
        enableAndDisableView(false);
        etFirstName.setFilters(new InputFilter[]{EMOJI_FILTER});
        etLastName.setFilters(new InputFilter[]{EMOJI_FILTER});
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            profileBack.setRotation(180);}
            getProfile();



    }

    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

    @SuppressLint("ClickableViewAccessibility")
    private void setUpProfileData(boolean isMobileEdit) {
        etFirstName.setText("");
        etFirstName.append(prefUtils.getStringValue(PrefKeys.FIRST_NAME, ""));
        etLastName.setText("");
        etLastName.append(prefUtils.getStringValue(PrefKeys.LAST_NAME, ""));
        Glide.with(this).load(prefUtils.getStringValue(PrefKeys.USER_PICTURE, "")).into(profileImage);
        if (prefUtils.getStringValue(PrefKeys.GENDER, "").equalsIgnoreCase(getString(R.string.txt_male))) {
            radioBtnMale.setChecked(true);
        }  else {
            radioBtnFemale.setChecked(true);
        }


    }

    private void enableAndDisableView(boolean toggle) {
        profileImage.setEnabled(toggle);
        etFirstName.setEnabled(toggle);
        etLastName.setEnabled(toggle);
        etProfileEmail.setEnabled(toggle);
        radioBtnFemale.setEnabled(toggle);
        radioBtnMale.setEnabled(toggle);
        idtype.setEnabled(toggle);
        nationality.setEnabled(toggle);

        id_number.setEnabled(toggle);
//        etProfileMobile.setEnabled(toggle);
//        updateMobileNumber.setVisibility(toggle ? View.VISIBLE : View.GONE);
    }

    protected void updateUserProfile() {
        getnatinalityandidnumber();
        UiUtils.showLoadingDialog(ProfileActivity.this);
        MultipartBody.Part multipartBody = null;
        if (fileToUpload != null) {
            String path = getRealPathFromURIPath(fileToUpload, this);
            File file = new File(path);
            Timber.d("Filename %s", file.getName());
            // create RequestBody instance tempFrom file
            String mimeType = getContentResolver().getType(fileToUpload);
             requestFile =
                    RequestBody.create(MediaType.parse(mimeType == null ? "multipart/form-data" : mimeType),
                            file);
            // MultipartBody.Part is used to send also the actual file name
            multipartBody =
                    MultipartBody.Part.createFormData(APIConsts.Params.PICTURE, file.getName(), requestFile);
        }
        gender = null;
        if (radioBtnFemale.isChecked()) {
            gender = "female";
        } else if (radioBtnMale.isChecked()) {
            gender = "male";
        } else {
            gender = "others";
        }
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiServices1=retrofit.create(APIInterface.class);
        Call<String> call = apiServices1.doUpdateProfile(
               Integer.toString(prefUtils.getIntValue(PrefKeys.USER_ID, 0))
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                ,prefUtils.getStringValue("code", "")
                ,prefUtils.getStringValue(PrefKeys.Phone, "")
                ,"ar"
                , etFirstName.getText().toString()
                ,etLastName.getText().toString()
                , id_number.getText().toString()
                , etProfileEmail.getText().toString()
                ,""
                ,multipartBody
                ,requestFile
                ,gender
                ,idtypenumber
                ,nationalityid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject updateProfileResponse = null;
                if(response.isSuccessful()&& response.body()!=null){

                try {
                    updateProfileResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (updateProfileResponse != null) {


                        UiUtils.showShortToast(ProfileActivity.this,"profile is updated successfully");
                       updateUserInDevice(updateProfileResponse);
                        enableAndDisableView(false);
                        btnEditProfile.setText(getString(R.string.btn_edit));
                        isEditMode = false;

                }}
                else{
                  //  Toast.makeText(ProfileActivity.this, prefUtils.getStringValue(PrefKeys.Phone, "")+"//"+prefUtils.getStringValue("code", ""), Toast.LENGTH_SHORT).show();
                  Toast.makeText(ProfileActivity.this, getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void getnatinalityandidnumber() {
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
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

    private void callImagePicker() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            EasyPermissions.requestPermissions(this, getString(R.string.youNeedToGrantPermission),
                    RC_STORAGE_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
            openGalleryIntent.setType("image/*");
            startActivityForResult(openGalleryIntent, PICK_IMAGE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            fileToUpload = data.getData();
            Glide.with(this)
                    .load(fileToUpload)
                    .into(profileImage);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void updateUserInDevice(JSONObject data) {
        //Toast.makeText(this, prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""), Toast.LENGTH_SHORT).show();
        PrefHelper.setUserLoggedIn(this,  prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                ,prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                , APIConsts.Constants.MANUAL_LOGIN
                ,prefUtils.getStringValue("code", "")
                ,prefUtils.getStringValue(PrefKeys.Phone, "")
                , data.optString(APIConsts.Params.FIRSTNAME)+" "+data.optString(APIConsts.Params.LAST_NAME)
                , data.optString("national_id")
                , data.optString(APIConsts.Params.FIRSTNAME)
                , data.optString(APIConsts.Params.LAST_NAME)
                , data.optString(APIConsts.Params.PICTURE)
                , data.optString(APIConsts.Params.PAYMENT_MODE)
                , data.optString(APIConsts.Params.TIMEZONE)
        ,gender,
                nationality.getText().toString()
        ,idtype.getText().toString());
    }

    @Override
    public void onBackPressed() {
        UiUtils.hideKeyboard(ProfileActivity.this);
        if (isEditMode) {
            enableAndDisableView(isEditMode);
            btnEditProfile.setText(getString(R.string.btn_edit));
            isEditMode = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.profile_back, R.id.btn_edit_profile, R.id.profile_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_back:
                onBackPressed();
                break;
            case R.id.btn_edit_profile:
                if (btnEditProfile.getText().toString().equals(getString(R.string.btn_edit))) {
                    enableAndDisableView(true);
                    btnEditProfile.setText(getString(R.string.btn_save));
                    isEditMode = true;
                } else {
                    if(id_number.getText().toString().length()<10){
                        id_number.setError(getString(R.string.idnumbermustconsistsof10numbers));
                        id_number.requestFocus();
                    }
                    else{
                    enableAndDisableView(false);
                    updateUserProfile();
                    isEditMode = false;
                }}
                break;
            case R.id.profile_image:
                callImagePicker();
                break;
        }
    }


    //  TODO:handle crop for image
    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), (Calendar.getInstance()
                .getTimeInMillis() + ".jpg")));
        Crop.of(source, outputUri).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            filePath = getRealPathFromURIPath(Crop.getOutput(result), this);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.defult_user);
            requestOptions.error(R.drawable.defult_user);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(filePath).into(profileImage);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void getProfile() {
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiServices2=retrofit.create(APIInterface.class);
        UiUtils.showLoadingDialog(ProfileActivity.this);
        Call<String> call = apiServices2.getProfile(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject data = null;
                try {
                    data = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (data != null) {
                        etProfileEmail.setText("");
                        etProfileEmail.append(data.optString(Const.Params.EMAIL));
                        etFirstName.setText("");
                        etFirstName.append(data.optString(Const.Params.FIRSTNAME));
                        etLastName.setText("");
                        etLastName.append(data.optString(Const.Params.LAST_NAME));

                        if(data.optString("owner_nationality")!=null){
                            nationality.setText(data.optString("nationality"),false);
                            }
                        if(data.optString("owner_id_type")!=null){
                        idtype.setText(data.optString("identity_type"),false);
                            }
                        id_number.setText(data.optString("national_id"));
                        Glide.with(ProfileActivity.this).load(data.optString(Const.Params.PICTURE)).into(profileImage);
                        if (data.optString(Const.Params.GENDER).equalsIgnoreCase("male")) {
                            radioBtnMale.setChecked(true);
                        } else {
                            radioBtnFemale.setChecked(true);
                        }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    private void getCountries() {
        countriesDataArrayList = new ArrayList<>();
        idtypes=new ArrayList<>();
        countriesList = new ArrayList<>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
        if (dataResponse != null && dataResponse.getData().get(0).getCountries() != null && !(dataResponse.getData().get(0).getCountries().isEmpty())) {
            countriesDataArrayList = dataResponse.getData().get(0).getCountries();
           // idtypes=dataResponse.getData().get(0).getId_card_types();
            SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
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
            idtypeadpt=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, idtypelist);
            idtype.setAdapter(idtypeadpt);
            nationalityadpt = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countriesList);
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
