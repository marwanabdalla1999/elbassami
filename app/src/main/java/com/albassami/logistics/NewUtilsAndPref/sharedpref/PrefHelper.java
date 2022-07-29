package com.albassami.logistics.NewUtilsAndPref.sharedpref;

import android.content.Context;

import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.google.gson.Gson;

import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.AUTH_TOKEN;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.EMAIL_NOTIFICATIONS;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.FIRST_NAME;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.GENDER;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.IS_LOGGED_IN;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.JSON_OBJ;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.LAST_NAME;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.LOGIN_TYPE;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.NATIONAL_ID;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.PAYMENT_MODE;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.PUSH_NOTIFICATIONS;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.Phone;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.REFERRAL_BONUS;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.REFERRAL_CODE;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.SESSION_TOKEN;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.TIMEZONE;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.USER_ABOUT;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.USER_ID;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.USER_MOBILE;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.USER_NAME;
import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.USER_PICTURE;

public class PrefHelper {
    public static void setAuthToken(Context context,String token){
        PrefUtils preferences = PrefUtils.getInstance(context);
        preferences.setValue(AUTH_TOKEN, token);
    }
    public static void setBranchesListData(Context context, GetPriceDataResponse obj){
        PrefUtils preferences = PrefUtils.getInstance(context);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(obj);
        preferences.setValue(JSON_OBJ, jsonObject);
    }
    public static void setUserLoggedIn(Context context, int id,String token, String loginType,String code, String phone,String name
            ,String national_id, String firstName, String lastName,String picture,String paymentMode,String timeZone
                                     ,String gender,String nationality,String idtype  ) {
        PrefUtils preferences = PrefUtils.getInstance(context);
        preferences.setValue(IS_LOGGED_IN, true);
        preferences.setValue(USER_ID, id);
        preferences.setValue(SESSION_TOKEN, token);
        preferences.setValue(LOGIN_TYPE, loginType);
        preferences.setValue("code",code);
        preferences.setValue(Phone, phone);
        preferences.setValue(USER_NAME, name);
        preferences.setValue(NATIONAL_ID, national_id);
        preferences.setValue(FIRST_NAME, firstName);
        preferences.setValue(LAST_NAME, lastName);
        preferences.setValue(USER_PICTURE, picture);
        preferences.setValue(PAYMENT_MODE, paymentMode);
        preferences.setValue(TIMEZONE, timeZone);
       // preferences.setValue(USER_MOBILE, mobile);
        preferences.setValue(GENDER, gender);
        preferences.setValue("nationality", nationality);
        preferences.setValue("idtype", idtype);
     //   preferences.setValue(REFERRAL_CODE, referralCode);
       // preferences.setValue(REFERRAL_BONUS, referralBonus);
    }

    public static void setUserLoggedOut(Context context) {
        PrefUtils preferences = PrefUtils.getInstance(context);
        preferences.removeKey(IS_LOGGED_IN);
        preferences.removeKey(USER_ID);
        preferences.removeKey(SESSION_TOKEN);
        preferences.removeKey(LOGIN_TYPE);
        preferences.removeKey(Phone);
        preferences.removeKey(USER_NAME);
        preferences.removeKey(USER_ABOUT);
        preferences.removeKey(USER_PICTURE);
        preferences.removeKey(PUSH_NOTIFICATIONS);
        preferences.removeKey(EMAIL_NOTIFICATIONS);
    }
}
