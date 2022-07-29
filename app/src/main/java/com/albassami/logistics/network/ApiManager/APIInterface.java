package com.albassami.logistics.network.ApiManager;

import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.AddVehicleResponse;
import com.albassami.logistics.dto.response.DeleteVehicleResponse;
import com.albassami.logistics.dto.response.GetVehiclesResponse;
import com.albassami.logistics.dto.response.TowingResponse;
import com.albassami.logistics.dto.response.maker;
import com.albassami.logistics.dto.response.type;
import com.albassami.logistics.network.Models.checkphonenumber;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.ADD_CARDS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.ADD_FAV_PROVIDER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.ADD_MONEY_TO_WALLET;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.ADD_VEHICLE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.ADVERTISEMENTS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.AIRPORT_LIST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.AIRPORT_PACKAGE_FARE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.APPLY_REFERRAL;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CANCEL_CREATE_REQUEST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CANCEL_LATER_REQUEST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CANCEL_ONGOING_RIDE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CANCEL_REASON;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CANCEL_REDEEM_REQUEST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CARDS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CHANGE_DEFAULT_PAYMENT_MODE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CHANGE_PASSWORD;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CHECK_PENDING_PAYMENTS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.DELETE_CARD;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.DELETE_VEHICLES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.FARE_CALCULATION;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.FAV_PORVIDER_LIST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.FORGOT_PASSWORD;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_LATER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_OTP;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_PAYMENT_MODES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_PROVIDERS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_VEHICLES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.HISTORY;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.HOURLY_FARE_CALCULATION;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.LOCATION_LST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.LOGIN;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.LOGOUT;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.MAKE_DEFAULT_CARD;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.MAKE_DUE_PAYMENTS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.PROFILE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.PROMO_CODE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.PROVIDER_PROFILE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.RATE_PROVIDER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REDEEMS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REDEEM_REQUESTS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REFERRAL_CODE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REGISTER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REMOVE_FAV_PROVIDER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REQUESTS_VIEW;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REQUEST_LATER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REQUEST_STATUS_CHECK;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REQUEST_STATUS_CHECK_NEW;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.REQUEST_TAXI;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.SEND_MONEY_TO_REDEEM;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.SERVICE_TYPES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.STATIC_PAGES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.TOWING_TYPES;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.UPDATE_ADDRESS;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.UPDATE_LOCATION;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.UPDATE_PROFILE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.UPDATE_VEHICLE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.WALLET;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.WALLET_PAYMENTS;

public interface APIInterface {

    @FormUrlEncoded
    @POST("api/user/reset_password")
    Call<String> resetpassword(
            @Field("mobile") String lang
            ,@Field("country_code") String country_code
            ,@Field("otp") String otp
            ,@Field("password") String password
            ,@Field("password_confirmation") String password_confirmation);


    @FormUrlEncoded
    @POST("api/user/validate_otp")
    Call<String> checkotp(
            @Field("mobile") String mobile,
            @Field("country_code") String country_code,
            @Field("otp") String otp);

    @FormUrlEncoded
    @POST("api/user/resend_otp")
    Call<String> resendotp(
            @Field("mobile") String mobile,
            @Field("country_code") String country_code);

@FormUrlEncoded
@POST("api/user/register_user_phone")
Call<String> checkphonenumber(
        @Field("lang") String lang,
        @Field("mobile") String mobile,
        @Field("country_code") String country_code,
        @Field("device_type") String device_type);
    @FormUrlEncoded
    @POST("api/user/resend_otp")
    Call<String> resend_otp(
            @Field("mobile") String mobile,
            @Field("country_code") String country_code);
    @FormUrlEncoded
    @POST(REGISTER)
    Call<String> doSocialLoginUser(
            @Field(APIConsts.Params.SOCIAL_ID) String socialUniqueId
            , @Field(APIConsts.Params.LOGIN_BY) String loginBy
            , @Field(APIConsts.Params.EMAIL) String email
            , @Field(APIConsts.Params.FIRSTNAME) String firstName
            , @Field(APIConsts.Params.LAST_NAME) String lastName
            , @Field(APIConsts.Params.PICTURE) String picture
            , @Field(APIConsts.Params.DEVICE_TYPE) String deviceType
            , @Field(APIConsts.Params.DEVICE_TOKEN) String deviceToken
            , @Field(APIConsts.Params.TIMEZONE) String timeZone
    );

    @FormUrlEncoded
    @POST(ADD_VEHICLE)
    Call<String> addVehicle(
             @Field(APIConsts.Params.ID_USER) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field("lang") String lang
            , @Field(APIConsts.Params.PLATE_NUMBER) String plateNumber
            , @Field(APIConsts.Params.PLATE_TYPE) String plateType
            , @Field(APIConsts.Params.MODEL_ID) int modelID
            , @Field(APIConsts.Params.MAKER_ID) int makerID
            , @Field("type_id") int typeID
            , @Field("phone_number") String phone_number
            , @Field(APIConsts.Params.ID_NUMBER) String idNumber
            ,@Field(APIConsts.Params.OWNER_NAME) String ownerName
    );


    @GET(TOWING_TYPES)
    Call<TowingResponse> getTowing();

    @FormUrlEncoded
    @POST(UPDATE_VEHICLE)
    Call<String> editVehicle(
            @Field(APIConsts.Params.ID_USER) String id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field("lang") String lang
            , @Field(APIConsts.Params.PLATE_NUMBER) String plateNumber
            , @Field(APIConsts.Params.PLATE_TYPE) String plateType
            , @Field(APIConsts.Params.MODEL_ID) String modelID
            , @Field(APIConsts.Params.MAKER_ID) String makerID
            , @Field("type_id") String typeID
            , @Field("phone_number") String phone_number
            , @Field(APIConsts.Params.ID_NUMBER) String idNumber
            ,@Field(APIConsts.Params.OWNER_NAME) String ownerName
            , @Field("model_name") String model_name
            , @Field("maker_name") String maker_name
            ,@Field("type name") String type_name
            , @Field("owner_id_type") String owner_id_type
            , @Field("owner_nationality") String owner_nationality

    );

    @FormUrlEncoded
    @POST(GET_VEHICLES)
    Call<GetVehiclesResponse> getVehicles(
            @Field(APIConsts.Params.ID_USER) String id
            , @Field(APIConsts.Params.TOKEN) String token
    );
    @FormUrlEncoded
    @POST("api/car/car_types")
    Call<ArrayList<type>> getcartype(
            @Field(APIConsts.Params.ID_USER) String id
            , @Field(APIConsts.Params.TOKEN) String token
    );
    @FormUrlEncoded
    @POST("api/car/car_makers")
    Call<ArrayList<maker>> getcarmaker(
            @Field(APIConsts.Params.ID_USER) String id
            , @Field(APIConsts.Params.TOKEN) String token
    );
    @FormUrlEncoded
    @POST(DELETE_VEHICLES)
    Call<String> deleteVehicles(
             @Field(APIConsts.Params.ID_USER) String id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field("plate_number")       String plate_number
    );

    @FormUrlEncoded
    @POST(LOGIN)
    Call<String> doMannualLogin(
            @Field("lang") String lang,
            @Field("mobile") String mobile,
            @Field("country_code") String country_code
            , @Field(APIConsts.Params.PASSWORD) String password
    );

    @FormUrlEncoded
    @POST(GET_OTP)
    Call<String> getOtp(
            @Field(APIConsts.Params.MOBILE) String phoneNumb,
            @Field(APIConsts.Params.COUNTRY_CODE) String countryCode
    );

    @FormUrlEncoded
    @POST(REGISTER)
    Call<String> doSignUpUser(
             @Field("lang") String lang
             ,@Field("unique_id") String unique_id
             ,@Field("national_id") String national_id
            ,@Field(APIConsts.Params.FIRSTNAME) String firstName
            , @Field(APIConsts.Params.LAST_NAME) String lastName
            , @Field("country") String country
            , @Field(APIConsts.Params.PASSWORD) String password
            , @Field("password_confirmation") String password_confirmation
            , @Field("identity_type_code") String identity_type_id
            , @Field("nationality_code") String nationality_id
    );

    @FormUrlEncoded
    @POST(APPLY_REFERRAL)
    Call<String> applyReferralCode(
            @Field(APIConsts.Params.REFERRAL_CODE) String referralCode
    );

    @FormUrlEncoded
    @POST(FORGOT_PASSWORD)
    Call<String> makeCallForgotPassword(
            @Field(APIConsts.Params.EMAIL) String email
    );

    @Multipart
    @POST(UPDATE_PROFILE)
    Call<String> doUpdateProfile(
            @Part(APIConsts.Params.ID) String id
            , @Part(APIConsts.Params.TOKEN) String token
            , @Part("country_code") String country_code
            , @Part("mobile") String mobile
            , @Part("lang") String lang
            , @Part(APIConsts.Params.FIRSTNAME) String firstName
            , @Part(APIConsts.Params.LAST_NAME) String lastName
            , @Part("national_id") String national_id
            , @Part(APIConsts.Params.EMAIL) String email
            , @Part("id_card") String id_card
            , @Part MultipartBody.Part picture
            , @Part ("somedata")RequestBody requestBody
            ,@Part("gender") String gender
            , @Part("identity_type_code") String identity_type_id
            , @Part("nationality_code") String nationality_id
            );

    @FormUrlEncoded
    @POST(LOGOUT)
    Call<String> doLogoutUser(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(CARDS)
    Call<String> getAllCards(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(ADD_CARDS)
    Call<String> addCard(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.CARD_TOKEN) String cardToken
    );

    @FormUrlEncoded
    @POST(DELETE_CARD)
    Call<String> deleteCard(@Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.USER_CARD_ID) String cardId);


    @FormUrlEncoded
    @POST(MAKE_DEFAULT_CARD)
    Call<String> makeCardDefault(@Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.USER_CARD_ID) String cardId);

    @FormUrlEncoded
    @POST(HISTORY)
    Call<String> getHistory(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SKIP) int skip);


    @GET(GET_LATER)
    Call<String> getLaterRequest(
            @Query("national_id") String id);


    @FormUrlEncoded
    @POST(ADVERTISEMENTS)
    Call<String> getAdsFromBackend(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token);


    @FormUrlEncoded
    @POST(SERVICE_TYPES)
    Call<String> getServiceTypes(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token);

    @FormUrlEncoded
    @POST(SERVICE_TYPES)
    Call<String> getServiceWithDistance(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.DISTANCE) float distance
            , @Field(APIConsts.Params.TIME) float duration
    );

    @FormUrlEncoded
    @POST(GET_PROVIDERS)
    Call<String> getAllAvailableProviders(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.LATITUDE) double lat
            , @Field(APIConsts.Params.LONGITUDE) double lng
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
    );

    @FormUrlEncoded
    @POST(REQUEST_LATER)
    Call<String> scheduleALaterRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.D_ADDRESS) String dAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.D_LATITUDE) double dLat
            , @Field(APIConsts.Params.D_LONGITUDE) double dLng
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.REQUESTED_TIME) String requestedTime
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(REQUEST_STATUS_CHECK)
    Call<String> pingRequestStatusCheck(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token);
    @FormUrlEncoded
    @POST("api/customer/get_orders")
    Call<String> gettow_orders(
             @Field(APIConsts.Params.TOKEN) String token,
            @Field(APIConsts.Params.ID) int id);


    @FormUrlEncoded
    @POST(FARE_CALCULATION)
    Call<String> calculateFare(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.DISTANCE) double distance
            , @Field(APIConsts.Params.TIME) String time
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
    );

    @GET
    Call<String> getLocationBasedResponse(@Url String url);

    @FormUrlEncoded
    @POST(AIRPORT_PACKAGE_FARE)
    Call<String> airportFareCalculation(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.AIRPORT_ID) String airportDetailsId
            , @Field(APIConsts.Params.LOCATION_ID) String locationDetailsId
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
    );

    @FormUrlEncoded
    @POST(HOURLY_FARE_CALCULATION)
    Call<String> hourlyFareCalculation(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceTypeId
            , @Field(APIConsts.Params.NO_HOUR) String noOfHours
    );

    @FormUrlEncoded
    @POST(REQUEST_LATER)
    Call<String> scheduleaAirportRide(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.D_ADDRESS) String dAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.D_LATITUDE) double dLat
            , @Field(APIConsts.Params.D_LONGITUDE) double dLng
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.AIRPORT_PRICE_ID) String airportPriceId
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
            , @Field(APIConsts.Params.REQUESTED_TIME) String time
    );

    @FormUrlEncoded
    @POST(REQUEST_TAXI)
    Call<String> createAirportNowRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.D_ADDRESS) String dAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.D_LATITUDE) double dLat
            , @Field(APIConsts.Params.D_LONGITUDE) double dLng
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.AIRPORT_PRICE_ID) String airportPriceId
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(REQUEST_TAXI)
    Call<String> createAHourlyRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.HOURLY_PACKAGE_ID) String hourlyPackageId
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(REQUEST_LATER)
    Call<String> scheduleAHourlyPackage(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.HOURLY_PACKAGE_ID) String hourlyPackageId
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
            , @Field(APIConsts.Params.REQUESTED_TIME) String time
    );

    @FormUrlEncoded
    @POST(LOCATION_LST)
    Call<String> locationList(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.KEY) String key
    );

    @FormUrlEncoded
    @POST(AIRPORT_LIST)
    Call<String> getAirportList(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(CANCEL_CREATE_REQUEST)
    Call<String> cancelRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(UPDATE_ADDRESS)
    Call<String> updateAddress(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.REQUEST_ID) int requestId
            , @Field(APIConsts.Params.ADDRESS) String address
            , @Field(APIConsts.Params.LATITUDE) double lat
            , @Field(APIConsts.Params.LONGITUDE) double lng
            , @Field(APIConsts.Params.CHANGE_TYPE) String changeType
    );

    @FormUrlEncoded
    @POST(CANCEL_ONGOING_RIDE)
    Call<String> cancelOngoingRide(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.REQUEST_ID) int requestId
            , @Field(APIConsts.Params.REASON_ID) String reasonId
            , @Field(APIConsts.Params.CANCELLATION_REASON) String cancellationReason
    );

    @FormUrlEncoded
    @POST(CANCEL_REASON)
    Call<String> cancelReasonsList(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(CHANGE_PASSWORD)
    Call<String> changePassword(@Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.OLD_PASSWORD) String curPassword
            , @Field(APIConsts.Params.PASSWORD) String newPassword
            , @Field(APIConsts.Params.CONFIRM_PASSWORD) String newPasswordConfirm);

    @FormUrlEncoded
    @POST(FORGOT_PASSWORD)
    Call<String> forgotPassword(@Field(APIConsts.Params.EMAIL) String email);


    @FormUrlEncoded
    @POST(CHANGE_DEFAULT_PAYMENT_MODE)
    Call<String> changeDefaultPaymentMode(
            @Field(APIConsts.Params.ID) int id,
            @Field(APIConsts.Params.TOKEN) String token,
            @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(RATE_PROVIDER)
    Call<String> rateProvider(
            @Field(APIConsts.Params.ID) int id,
            @Field(APIConsts.Params.TOKEN) String token,
            @Field(APIConsts.Params.REQUEST_ID) int requestId,
            @Field(APIConsts.Params.COMMENT) String comment,
            @Field(APIConsts.Params.RATING) int rating
    );

    @FormUrlEncoded
    @POST(GET_PAYMENT_MODES)
    Call<String> getPaymentMethods(
            @Field(APIConsts.Params.ID) int id,
            @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(REQUEST_TAXI)
    Call<String> createNowRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SERVICE_TYPE_ID) String serviceType
            , @Field(APIConsts.Params.PRICE) String price
            , @Field(APIConsts.Params.S_ADDRESS) String sAddress
            , @Field(APIConsts.Params.D_ADDRESS) String dAddress
            , @Field(APIConsts.Params.ADD_STOP_ADDRESS) String stopAddress
            , @Field(APIConsts.Params.S_LATITUDE) double lat
            , @Field(APIConsts.Params.S_LONGITUDE) double lng
            , @Field(APIConsts.Params.D_LATITUDE) double dLat
            , @Field(APIConsts.Params.D_LONGITUDE) double dLng
            , @Field(APIConsts.Params.ADD_STOP_LATITUDE) double stopLat
            , @Field(APIConsts.Params.ADD_STOP_LONGITUDE) double stopLng
            , @Field(APIConsts.Params.TRANSPORT_TYPE) String transportType
            , @Field(APIConsts.Params.AGGREMENT_NUMBER) String agreementNumber
            , @Field(APIConsts.Params.CAR_TRANSPORT_TYPE) String catTransportType
            , @Field(APIConsts.Params.BRANCH_ID_TO) String branchTo
            , @Field(APIConsts.Params.BRANCH_ID_FROM) String branchFrom
            , @Field(APIConsts.Params.REQUEST_SERVICE_TYPE) int requestStatusType
            , @Field(APIConsts.Params.PROMO_CODE) String airportPriceId
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode);

    @FormUrlEncoded
    @POST(CANCEL_LATER_REQUEST)
    Call<String> cancelLaterRequest(
            @Field(APIConsts.Params.ID) int id,
            @Field(APIConsts.Params.TOKEN) String token,
            @Field(APIConsts.Params.REQUEST_ID) String requestId);


    @FormUrlEncoded
    @POST(APIConsts.Apis.CHAT_DETAILS)
    Call<String> getChatDetails(@Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.REQUEST_ID) int bookingId
            , @Field(APIConsts.Params.PROVIDER_ID) String providerId
            , @Field(APIConsts.Params.SKIP) int skip);

    @FormUrlEncoded
    @POST(REFERRAL_CODE)
    Call<String> getReferralCode(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(REQUESTS_VIEW)
    Call<String> getRequestsView(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.REQUEST_ID) String requestId
    );

    @FormUrlEncoded
    @POST(PROFILE)
    Call<String> getProfile(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );
    @FormUrlEncoded
    @POST("api/shippment_types/prices")
    Call<String> gettowtypes(
            @Field("shippment_type_id") String shippment_type_id
    );


    @GET("api/branches")
    Call<String> branches(

    );

    @FormUrlEncoded
    @POST("api/create_order_tow")
    Call<String> createorder(
            @Field("token") String token,
             @Field("id") String id,
                     @Field("customer_id") String customer_id,
                     @Field("towing_id") String towing_id,
                     @Field("shippment_type") String shippment_type,
                     @Field("car_id") String car_id,
                     @Field("app_payment_method") String app_payment_method,
            @Field("pickup_latitude") String pickup_latitude,
            @Field("pickup_longitude") String pickup_longitude,
            @Field("destination_latitude") String destination_latitude,
            @Field("destination_longitude") String destination_longitude,
    @Field("receiver_name") String receiver_name,
    @Field("receiver_nationality") String receiver_nationality,
    @Field("receiver_mob_no") String receiver_mob_no,
    @Field("receiver_id_card_no") String receiver_id_card_no,
    @Field("with_return") String with_return,
            @Field("km_price") String km_price,
            @Field("minute_price") String minute_price,
            @Field("base_price") String base_price);

    @FormUrlEncoded
    @POST("api/find_order_by_agreement_number")
    Call<String> order_status(
            @Field("token") String token,
            @Field("id") String id,
            @Field("agreement_number") String agreement_number);
    @FormUrlEncoded
    @POST("api/customer/cancel_order")
    Call<String> cancel_order_tow(
            @Field("id") String id,
            @Field("token") String token,
            @Field("order_id") String order_id);

    @FormUrlEncoded
    @POST("api/update_order")
    Call<String> paying_agreement(
            @Field("token") String token,
            @Field("id") String id,
            @Field("order_id") String order_id,
            @Field("app_payment_method") String app_payment_method,
            @Field("app_paid_amount") String app_paid_amount,
            @Field("app_fortid") String app_fortid
    );

    @FormUrlEncoded
    @POST(PROVIDER_PROFILE)
    Call<String> getProviderProfile(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.PROVIDER_ID) String providerId
    );

    @GET(STATIC_PAGES)
    Call<String> getStaticPages(@Query(Const.Params.PAGE_TYPE) String pageType);

    @GET("get_order")
    Call<String> trackshipment(@Query("order_ref") String order_ref,
    @Query("receiver_mob_no") String receiver_mob_no);

    @FormUrlEncoded
    @POST(WALLET)
    Call<String> getWalletData(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(WALLET_PAYMENTS)
    Call<String> getWalletTransactions(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SKIP) int skip
    );

    @FormUrlEncoded
    @POST(ADD_MONEY_TO_WALLET)
    Call<String> addMoneyToWallet(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.AMOUNT) String amount
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(CANCEL_REDEEM_REQUEST)
    Call<String> cancelRedeemRequest(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.USER_REDEEM_REQUEST_ID) String userRequestId
    );

    @FormUrlEncoded
    @POST(REDEEMS)
    Call<String> getRedeemsList(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(SEND_MONEY_TO_REDEEM)
    Call<String> sendMoneyForRedeem(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.AMOUNT) String amount
    );

    @FormUrlEncoded
    @POST(REDEEM_REQUESTS)
    Call<String> getAllRedeemRequests(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.SKIP) int skip
    );

    @FormUrlEncoded
    @POST(PROMO_CODE)
    Call<String> applyPromoCode(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.PROMO_CODE) String promoCode
    );

    @FormUrlEncoded
    @POST(REMOVE_FAV_PROVIDER)
    Call<String> removeFavProvider(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.USER_FAVOURITE_ID) String userFavId);

    @FormUrlEncoded
    @POST(ADD_FAV_PROVIDER)
    Call<String> addFavProvider(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.PROVIDER_ID) String providerId
    );

    @FormUrlEncoded
    @POST(FAV_PORVIDER_LIST)
    Call<String> listFavProvider(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(CHECK_PENDING_PAYMENTS)
    Call<String> checkingForPendingPayments(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

    @FormUrlEncoded
    @POST(MAKE_DUE_PAYMENTS)
    Call<String> makeDuePayments(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.PAYMENT_MODE) String paymentMode
    );

    @FormUrlEncoded
    @POST(UPDATE_LOCATION)
    Call<String> updateCurrentLocation(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
            , @Field(APIConsts.Params.LATITUDE) double latitude
            , @Field(APIConsts.Params.LONGITUDE) double longitude
    );

    @Multipart
    @POST(UPDATE_PROFILE)
    Call<String> updateMobileNumber(
            @Part(APIConsts.Params.ID) int id
            , @Part(APIConsts.Params.TOKEN) String token
            , @Part(APIConsts.Params.PHONE) String phone
            , @Part(APIConsts.Params.COUNTRY_CODE) String countryCode);


    @FormUrlEncoded
    @POST(REQUEST_STATUS_CHECK_NEW)
    Call<String> requestStatusCheckNew(
            @Field(APIConsts.Params.ID) int id
            , @Field(APIConsts.Params.TOKEN) String token
    );

}