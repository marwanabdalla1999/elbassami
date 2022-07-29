package com.albassami.logistics.network.ApiManager;

import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.AuthTokenResponse;
import com.albassami.logistics.dto.response.CarsDOBResponse;
import com.albassami.logistics.dto.response.CreateOrderResponse;
import com.albassami.logistics.dto.response.CreateOrderResponse2;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.dto.response.PriceResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.AUTH_TOKEN;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CREATE_CUSTOMER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.CREATE_ORDER;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_CARS_DOB;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_CARS_SHIP;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.GET_PRICE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Apis.PRICE_DATA;

public interface ApiServices {
    @GET(AUTH_TOKEN)
    Call<AuthTokenResponse> getAuthToken(@Query(Const.Params.LOG_IN) String login, @Query(Const.Params.PASSWORD_LOG) String password, @Query(Const.Params.DB) String db);

    @GET("paymentGetway/payfort/credentials")
    Call<String> getfortdata();

    @FormUrlEncoded
    @POST(CREATE_CUSTOMER)
    Call<CreateOrderResponse> createCustomer(@Header(Const.Params.ACCESS_TOKEN) String token, @Field(Const.Params.CUSTOMER_NATIONAL_ID) String customer_national_id,
                                             @Field(Const.Params.CUSTOMER_NAME) String customer_name,
                                             @Field(Const.Params.CUSTOMER_PHONE) String customer_phone,
                                             @Field("customer_nationality") String customer_nationality);
    @FormUrlEncoded
    @POST("add_other_services")
    Call<String> updateorder( @Field("order_ref") String order_ref,
                                             @Field("app_payment_method") String app_payment_method,
                                              @Field("app_paid_amount") String app_paid_amount,
                                              @Field("app_fortid") String app_fortid,
     @Field("is_home_pickup") String is_home_pickup,
                              @Field("is_home_delivery") String is_home_delivery,
                              @Field("pickup_location") String pickup_location,
    @Field("home_location") String home_location);

    @FormUrlEncoded
    @POST(CREATE_ORDER)
    Call<CreateOrderResponse> createOrder(
            @Field(Const.Params.CUSTOMER) String customer,
                                          @Field(Const.Params.SHIPMENT_TYPE) String shipmentType,
                                          @Field(Const.Params.LOC_FROM) String locFrom, @Field(Const.Params.LOC_TO) String locTo, @Field(Const.Params.CAR_MAKE) String carMake,
                                          @Field(Const.Params.CAR_MODEL) String carModel,@Field(Const.Params.PLATE_NUMBER) String none_saudi_plate_n
            , @Field(Const.Params.CAR_SIZE_ORDER) String carSize, @Field(Const.Params.PLATE_TYPE) String plateType,
                                          @Field(Const.Params.CHASSES) String chasses, @Field(Const.Params.PAYMENT_METHODE) String paymentMethod,
                                          @Field(Const.Params.RECIEVER_NAME) String receiver_name, @Field(Const.Params.RECIEVER_NATIONALITY) String receiver_nationality,
                                          @Field(Const.Params.RECIEVER_NUMBER) String receiver_number, @Field(Const.Params.RECIEVER_ID_NUMBER) String receiver_id_card,
                                          @Field(Const.Params.YEAR) String year,
                                          @Field(Const.Params.CAR_COLOR) String car_color,
                                          @Field("is_home_pickup") String is_home_pickup,
                                          @Field("is_home_delivery") String is_home_delivery,
                                          @Field("home_location") String home_location,
                                          @Field("pickup_location") String pickup_location,
                                          @Field("large_boxes") String large_boxes,
                                          @Field("medium_boxes") String medium_boxes,
                                          @Field("small_boxes") String small_boxes,
            @Field("app_payment_method") String app_payment_method,
                    @Field("app_paid_amount") String app_paid_amount,
                    @Field("app_fortid") String app_fortid,
            @Field("owner_name") String owner_name,
            @Field("owner_id_type") String owner_id_type,
            @Field("owner_type") String owner_type,
            @Field("owner_id_card_no") String owner_id_card_no,
            @Field("owner_nationality") String owner_nationality,
            @Field("transaction_reference") String transaction_reference

    );
    @FormUrlEncoded
    @POST("api/create_order")
    Call<String> create_init_order(
            @Field("token") String token,
            @Field("id") String id,
            @Field(Const.Params.LOC_FROM) String locFrom,
            @Field(Const.Params.LOC_TO) String locTo,
            @Field("shippment_type") String shippment_type,
            @Field("car_id") String car_id,
            @Field("chassis") String chassis,
            @Field(Const.Params.RECIEVER_NAME) String receiver_name,
            @Field(Const.Params.RECIEVER_NATIONALITY) String receiver_nationality,
            @Field(Const.Params.RECIEVER_NUMBER) String receiver_number,
            @Field(Const.Params.RECIEVER_ID_NUMBER) String receiver_id_card,
            @Field("large_boxes") String large_boxes
,            @Field("medium_boxes") String medium_boxes
,            @Field("small_boxes") String small_boxes
,            @Field("pickup_longitude") String pickup_longitude
            ,            @Field("pickup_latitude") String pickup_latitude

            ,            @Field("destination_longitude") String destination_longitude





    );

    @FormUrlEncoded
    @POST("api/update_order")
    Call<String> update_order(
            @Field("token") String token,
            @Field("id") String id,
            @Field("agreement_number") String agreement_number,
            @Field("chassis") String chassis,
            @Field("is_home_pickup") String is_home_pickup,
            @Field("is_home_delivery") String is_home_delivery,
            @Field("home_location") String home_location,
            @Field("pickup_location") String pickup_location,
            @Field("large_boxes") String large_boxes
            ,            @Field("medium_boxes") String medium_boxes
            ,            @Field("small_boxes") String small_boxes
            ,            @Field("app_payment_method") String app_payment_method
            ,            @Field("app_paid_amount") String app_paid_amount
            ,            @Field("app_fortid") String app_fortid
            ,            @Field("odoo_agreement") String odoo_agreement
            ,            @Field("pickup_latitude") String pickup_latitude
             ,            @Field("pickup_longitude") String pickup_longitude
            ,            @Field("destination_latitude") String destination_latitude
            ,            @Field("destination_longitude") String destination_longitude
            ,            @Field("base_price") String base_price);


    @FormUrlEncoded
    @POST("paymentGetway/payfort/postTransactionData")
    Call<String> fortdata(
            @Field("order_id") String order_id,
            @Field("amount") String amount,
            @Field("email") String email,
            @Field("languagetype") String languagetype,
            @Field("currency") String currency,
            @Field("command") String command,
            @Field("marchantReferance") String marchantReferance
    );
    @FormUrlEncoded
    @POST("create_order")
    Call<String> createOrder1(
            @Field("token") String token,
            @Field("id") String id,
            @Field(Const.Params.CUSTOMER) String customer,
            @Field(Const.Params.SHIPMENT_TYPE) String shipmentType,
            @Field("car_make_id") String car_make_id,
            @Field(Const.Params.CAR_MAKE) String carMake,
            @Field("car_model_id") String car_model_id,
            @Field(Const.Params.CAR_MODEL) String carModel,
            @Field("car_size") String car_size,
            @Field(Const.Params.PLATE_TYPE) String plateType,
            @Field(Const.Params.PLATE_NUMBER) String none_saudi_plate_n
            ,
            @Field(Const.Params.CHASSES) String chasses,
            @Field(Const.Params.RECIEVER_NAME) String receiver_name,
            @Field(Const.Params.RECIEVER_NATIONALITY) String receiver_nationality,
            @Field(Const.Params.RECIEVER_NUMBER) String receiver_number,
            @Field(Const.Params.RECIEVER_ID_NUMBER) String receiver_id_card,
            @Field("large_boxes") String large_boxes,
            @Field("medium_boxes") String medium_boxes,
            @Field("small_boxes") String small_boxes,
            @Field("app_payment_method") String app_payment_method,
            @Field("app_paid_amount") String app_paid_amount,
            @Field("app_fortid") String app_fortid,
            @Field("pickup_longitude") String pickup_longitude,
            @Field("pickup_latitude") String pickup_latitude,
            @Field("destination_longitude") String destination_longitude,
            @Field("destination_latitude") String destination_latitude



    );

    @GET(PRICE_DATA)
    Call<GetPriceDataResponse> getPriceData(@Header(Const.Params.ACCESS_TOKEN) String token);

    @GET(GET_PRICE)
    Call<PriceResponse> getPrice(@Header(Const.Params.ACCESS_TOKEN) String token,
                                @Query("customer_type") String customer_type,
                                 @Query("waypoint_from") String waypoint_from,
                                 @Query("waypoint_to")    String waypoint_to,
                                 @Query("shipment_date")  String shipment_date,
                                 @Query("service_type")  String service_type,
                                         @Query("car_model")  String car_model,
                                         @Query("car_make")  String car_make,
                                 @Query("shipment_type")  String shipment_type
                                 );

    @GET(GET_CARS_DOB)
    Call<CarsDOBResponse> getDOBLicense(@Query("national_id") String national_id);

    @GET(GET_CARS_SHIP)
    Call<CarsDOBResponse> getCsrShipLicense(@Query("national_id") String national_id);

    @GET("cancel_order")
    Call<String> cancelorder(@Header("access-token") String access_token, @Query("order_ref") String order_ref);
    @GET("register_payment")
    Call<String> register_payment(@Header("access-token") String access_token, @Query("order_ref") String order_ref
            , @Query("app_paid_amount") String app_paid_amount
            , @Query("app_fortid") String app_fortid
    );

}
