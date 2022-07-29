package com.albassami.logistics.Utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by getit on 8/5/2016.
 */
public class Const {


    public static String PREF_NAME = "SMARCAR_PRERENCE";
    public static final int GET = 0;
    public static final int POST = 1;
    public static final int TIMEOUT = 30000;
    public static final int MAX_RETRY = 4;
    public static final float DEFAULT_BACKOFF_MULT = 1f;
    public static LatLng pic_latlan;
    public static LatLng drop_latlan;
    public static String source_address,dest_address="",stop_address="";
    public static LatLng stop_latlan;

    public static final int CHOOSE_PHOTO = 100;
    public static final int TAKE_PHOTO = 101;

    public static final String REQUEST_ACCEPT = "REQUEST_ACCEPT";
    public static final String REQUEST_CANCEL = "REQUEST_CANCEL";
    public static final int NO_REQUEST = -1;
    public static final String DRIVER_STATUS = "driverstatus";
    public static String SERVICE_TYPE = "";

    public static final long DELAY = 0;
    public static final long TIME_SCHEDULE = 5 * 1000;
    public static final long DELAY_OFFLINE = 15 * 60 * 1000;
    public static final long TIME_SCHEDULE_OFFLINE = 15 * 60 * 1000;

//    public static final String PLACES_AUTOCOMPLETE_API_KEY = "AIzaSyDtj2EwLdJqyTWknqWXSDuHAXcR12zhGV4";
////    public static final String GOOGLE_API_KEY = "AIzaSyDoujGbr86VY2F6vhh-bzZjsebCFoRn0ik";
//
//    public static final String GOOGLE_API_KEY = "AIzaSyDtj2EwLdJqyTWknqWXSDuHAXcR12zhGV4";


    public static final String PLACES_AUTOCOMPLETE_API_KEY = "AIzaSyBuPueeJXrdCz0lehxCwWhui07eZBU9qLU";
//    public static final String GOOGLE_API_KEY = "AIzaSyDoujGbr86VY2F6vhh-bzZjsebCFoRn0ik";

    public static final String GOOGLE_API_KEY = "AIzaSyBuPueeJXrdCz0lehxCwWhui07eZBU9qLU";
    //Fragments
    public static final String HOME_MAP_FRAGMENT = "home_map_fragment";
    public static final String HOME_START_FRAGMENT = "home_start_fragment";
    public static final String ODER_SUMMARY_FRAGMENT = "order_summary_fragment";
    public static final String CAR_FRAGMENT = "car_fragment";
    public static final String DOOR_FRAGMENT = "door_fragment";
    public static final String TRAVEL_MAP_FRAGMENT = "travel_map_fragment";
    public static final String RATING_FRAGMENT = "rating_fragment";
    public static final String CAR_TAB_FRAGMENT = "car_tab_fragment";
    public static final String BRANCHES_FRAGMENT = "branches_fragment";
    public static final String CAR_FORM_FRAGMENT = "car_form_fragment";
    public static final String REGISTER_FRAGMENT = "register_fragment";
    public static final String FORGOT_PASSWORD_FRAGMENT = "forgot_fragment";
    public static final String SEARCH_FRAGMENT = "search_fragment";
    public static final String REQUEST_FRAGMENT = "request_fragment";
    public static final String HOURLY_FRAGMENT = "hourly_fragment";
    public static final String AIRPORT_FRAGMENT = "airport_fragment";
    public static final String BOLT_FRAGMENT = "bolt_fragment";

    //  Trip request status
    public static final int IS_CREATED = 0;
    public static final int IS_ACCEPTED = 1;
    public static final int IS_DRIVER_DEPARTED = 2;
    public static final int IS_DRIVER_ARRIVED = 3;
    public static final int IS_DRIVER_TRIP_STARTED = 4;
    public static final int IS_DRIVER_TRIP_ENDED = 5;
    public static final int IS_DRIVER_RATED = 6;
    public static final String DOOR_TO_DOOR = "Home Pickup";
    public static final String HomeDelivery= "HomeDelivery";
    public static final String CarShip= "carShip";
    public static final String DEVICE_TYPE = "android";
    public static final String DEVICE_TYPE_ANDROID = "android";
    public static final String SOCIAL_FACEBOOK = "facebook";
    public static final String SOCIAL_GOOGLE = "google";
    public static final String MANUAL = "manual";
    public static final String SOCIAL = "social";
    public static final String REQUEST_DETAIL = "requestDetails";
    public static double currentLong = 0;
    public static double currentLat = 0;

    public static final String paypal = "PAYPAL";
    public static final String paygate = "PAYGATE";
    public static final String stripe = "STRIPE";


    public static final String GOOGLE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?";

    public class Params {
        public static final String ID = "id";
        public static final String TOKEN = "token";
        public static final String SOCIAL_ID = "social_unique_id";
        public static final String URL = "url";
        public static final String PICTURE = "picture";
        public static final String EMAIL = "email";
        public static final String SHIPMENT_DATE = "shipment_date";
        public static final String PASSWORD = "password";
        public static final String REPASSWORD = "confirm_password";
        public static final String FIRSTNAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String PHONE = "mobile";
        public static final String OTP = "otp";
        public static final String SSN = "ssn";
        public static final String DEVICE_TOKEN = "device_token";
        public static final String ICON = "icon";
        public static final String DEVICE_TYPE = "device_type";
        public static final String LOGIN_BY = "login_by";
        public static final String CURRENCEY = "currency_code";
        public static final String LANGUAGE = "language";
        public static final String REQUEST_ID = "request_id";
        public static final String GENDER = "gender";
        public static final String COUNTRY = "country";
        public static final String TIMEZONE = "timezone";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String TAXI_TYPE = "service_type_id";
        public static final String SERVICE_TYPE = "service_type";
        public static final String ORIGINS = "origins";
        public static final String DESTINATION = "destinations";
        public static final String SENSOR = "sensor";
        public static final String MODE = "mode";
        public static final String DISTANCE = "distance";
        public static final String TIME = "time";
        public static final String S_LATITUDE = "s_latitude";
        public static final String S_LONGITUDE = "s_longitude";
        public static final String D_LATITUDE = "d_latitude";
        public static final String D_LONGITUDE = "d_longitude";
        public static final String S_ADDRESS = "s_address";
        public static final String D_ADDRESS = "d_address";
        public static final String PAYMENT_MODE = "payment_mode";
        public static final String IS_PAID = "is_paid";
        public static final String COMMENT = "comment";
        public static final String RATING = "rating";
        public static final String PAYMENT_METHOD_NONCE = "payment_method_nonce";
        public static final String LAST_FOUR = "last_four";
        public static final String CARD_ID = "card_id";
        public static final String NO_HOUR = "number_hours";
        public static final String REQ_STATUS_TYPE = "request_status_type";
        public static final String HOURLY_PACKAGE_ID = "hourly_package_id";
        public static final String AIRPORT_PACKAGE_ID = "airport_price_id";
        public static final String PROMOCODE = "promo_code";
        public static final String REFERRAL_CODE = "referral_code";
        public static final String FAV_ID ="fav_id" ;
        public static final String ADDRESS ="address" ;
        public static final String FAVOURITE_NAME ="favourite_name" ;
        public static final String IS_ADSTOP ="is_adstop" ;
        public static final String ADSTOP_LONGITUDE ="adstop_longitude" ;
        public static final String ADSTOP_LATITUDE = "adstop_latitude";
        public static final String ADSTOP_ADDRESS ="adstop_address" ;
        public static final String CHANGE_TYPE ="change_type" ;
        public static final String NAME = "name";
        public static final String SUCCESS = "success";
        public static final String TRUE = "true";
        public static final String DATA = "data";
        public static final String RESULT = "result";
        public static final String GEOMETRY = "geometry";
        public static final String LOCATION = "location";
        public static final String LAT = "lat";
        public static final String LNG = "lng";
        public static final String GATEWAY = "gateway";
        public static final String PAYMENT_ID = "payment_id";
        public static final String AMOUNT = "amount";
        public static final String AUTHORIZATION = "Authorization";
        public static final String GATEWAYS = "gateways";
        public static final String STATUS = "status";
        public static final String PAGE_TYPE = "page_type";
        public static final String LOG_IN = "login";
        public static final String ACCESS_TOKEN = "access-token";
        public static final String CUSTOMER_NATIONAL_ID = "customer_national_id";
        public static final String CUSTOMER_NAME = "customer_name";
        public static final String CUSTOMER_PHONE = "customer_phone";
        public static final String PASSWORD_LOG = "password";
        public static final String CUSTOMER = "customer";
        public static final String TRIP_TYPE = "trip_type";
        public static final String LOC_FROM = "loc_from";
        public static final String LOC_TO = "loc_to";
        public static final String SHIPMENT_TYPE = "shipment_type";
        public static final String CAR_MAKE = "car_make";
        public static final String CAR_MODEL = "car_model";
        public static final String CAR_SIZE_ORDER = "car_size";
        public static final String PLATE_NUMBER = "non_saudi_plate_no";
        public static final String PLATE_TYPE = "plate_type";
        public static final String CHASSES = "chassis";
        public static final String RECIEVER_ID_NUMBER = "receiver_id_card_no";
        public static final String RECIEVER_NAME = "receiver_name";
        public static final String RECIEVER_VISA = "receiver_visa_no";
        public static final String RECIEVER_NUMBER = "receiver_mob_no";
        public static final String RECIEVER_NATIONALITY = "receiver_nationality";
        public static final String YEAR = "year";
        public static final String CAR_COLOR = "car_color";
        public static final String PAYMENT_METHODE = "payment_method";
        public static final String DB = "db";
        public static final String REASON_ID = "reason_id";
        public static final String CANCEL_RESON ="cancel_reason" ;
        public static final String AIRPORT_ID ="airport_id" ;
        public static final String AIRPORT_PRICE_ID ="airport_price_id" ;
        public static final String PRICE = "price";
        public static final String CUSTOMER_TYPE = "customer_type";
        public static final String CAR_SIZE = "car_size";
        public static final String WAYPOINT_FROM = "waypoint_from";
        public static final String WAYPOINT_TO = "waypoint_to";
        public static final String NO_OF_TOLLS ="number_tolls" ;
        public static final String LOCATION_ID = "location_id";
        public static final String MOBILE_FORMATTED = "mobile_formatted";
        public static final String TOTAL_DUE_AMOUNT_FORMATTED = "total_due_amount_formatted";
        public static final String MOBILE = "mobile";
        public static final String DOOR_TO_DOOR = "door_to_door";
        public static final String ERROR_MSG = "error" ;
        public static final String ERROR_CODE = "error_code";
        public static final String PRICE_FORMATTED = "price_formatted";
    }

    public class ServiceType {

        public static final String HOST_URL = "http://178.20.144.38/";// live server
        public static final String WEBVIEW = "http://178.20.144.38/";// live server
        public static final String SOCKET_URL = "http://178.20.144.38:80?type=user&id=";

       /* public static final String SOCKET_URL = "http://178.128.33.48:3000?type=user&id=";// dev socket server
        public static final String HOST_URL = "http://178.128.33.48/";// PL developing server*/


        public static final String BASE_URL = HOST_URL + "api/user/";
        public static final String LOGIN = BASE_URL + "login";
        public static final String REGISTER = BASE_URL + "register";
        public static final String UPDATE_PROFILE = BASE_URL + "updateProfile";
        public static final String FORGOT_PASSWORD = BASE_URL + "forgotpassword";
        public static final String TAXI_TYPE = BASE_URL + "services_list";
        public static final String GET_PROVIDERS = BASE_URL + "guest_provider_list";
        public static final String FARE_CALCULATION = BASE_URL + "fare_calculator";
        public static final String REQUEST_TAXI = BASE_URL + "requests_create";
        public static final String CANCEL_CREATE_REQUEST = BASE_URL + "waiting_request_cancel";
        public static final String CHECKREQUEST_STATUS = BASE_URL + "request_status_check";
        public static final String PAYNOW = BASE_URL + "payment";
        public static final String RATE_PROVIDER = BASE_URL + "requests_rate_provider";
        public static final String CANCEL_RIDE = BASE_URL + "requests_cancel";
        public static final String GET_HISTORY = BASE_URL + "history";
        public static final String GET_PAYMENT_MODES = BASE_URL + "get_payment_modes";
        public static final String PAYMENT_MODE_UPDATE = BASE_URL + "PaymentModeUpdate";
        public static final String GET_BRAIN_TREE_TOKEN_URL = BASE_URL + "getbraintreetoken";
        public static final String CREATE_ADD_CARD_URL = BASE_URL + "addcard";
        public static final String GET_ADDED_CARDS_URL = BASE_URL + "getcards?";
        public static final String REMOVE_CARD = BASE_URL + "deletecard";
        public static final String CREATE_SELECT_CARD_URL = BASE_URL + "selectcard";
        public static final String REQUEST_LATER = BASE_URL + "laterRequest";
        public static final String GET_LATER = BASE_URL + "upcomingRequest";
        public static final String CANCEL_LATER_RIDE = BASE_URL + "cancel_later_request?";
        public static final String HOURLY_PACKAGE_FARE = BASE_URL + "hourly_package_fare";
        public static final String USER_MESSAGE_NOTIFY = BASE_URL + "message_notification?";
        public static final String AIRPORT_LST = BASE_URL + "airport_details?";
        public static final String LOCATION_LST = BASE_URL + "location_details?";
        public static final String AIRPORT_PACKAGE_FARE = BASE_URL + "airport_package_fare";
        public static final String GET_OTP = BASE_URL + "sendOtp?";
        public static final String CANCEL_REASON = BASE_URL + "cancellation_reasons";
        public static final String VALIDATE_PROMO = BASE_URL + "promo_code_check";
        public static final String ADVERTISEMENTS = BASE_URL + "adsManagement";

        // wallet config
        public static final String WALLET_HOST_URL = "http://walletbay.net/apps";
        public static final String WALLET_BASE_URL = WALLET_HOST_URL + "/api/business/";
        public static final String WALLET_TYPES = WALLET_BASE_URL + "payment-gateways";
        public static final String WALLET_BALANCE = WALLET_HOST_URL + "/api/businesses/users/";

        public static final String GET_SAVED_PLACES =BASE_URL+"userFavourites" ;
        public static final String CANCEL_FAV =BASE_URL+"deleteuserFavourite" ;
        public static final String ADD_FAV =BASE_URL+"adduserFavourite" ;
        public static final String LOGOUT = BASE_URL + "logout";
        public static final String GET_VERSION = HOST_URL + "get_version";
        public static final String MESSAGE_GET =BASE_URL+"message/get" ;
        public static final String UPDATE_ADDRESS =BASE_URL+"updateAddress" ;
        public static final String APPLY_REFERRAL = BASE_URL+"applyReferral";
    }

    // service codes
    public class ServiceCode {
        public static final int REGISTER = 1;
        public static final int LOGIN = 2;
        public static final int UPDATE_PROFILE = 3;
        public static final int FORGOT_PASSWORD = 4;
        public static final int GOOGLE_DIRECTION_API = 5;
        public static final int TAXI_TYPE = 6;
        public static final int GET_PROVIDERS = 7;
        public static final int GOOGLE_MATRIX = 8;
        public static final int FARE_CALCULATION = 9;
        public static final int REQUEST_TAXI = 10;
        public static final int CANCEL_CREATE_REQUEST = 11;
        public static final int CHECKREQUEST_STATUS = 12;
        public static final int PAYNOW = 13;
        public static final int RATE_PROVIDER = 14;
        public static final int GOOGLE_MATRIX_ETA = 15;
        public static final int HOMETAXI_TYPE = 16;
        public static final int CANCEL_RIDE = 17;
        public static final int GET_HISTORY = 18;
        public static final int GET_PAYMENT_MODES = 19;
        public static final int PAYMENT_MODE_UPDATE = 20;
        public static final int GET_BRAIN_TREE_TOKEN_URL = 21;
        public static final int CREATE_ADD_CARD_URL = 22;
        public static final int GET_ADDED_CARDS_URL = 23;
        public static final int CREATE_SELECT_CARD_URL = 24;
        public static final int REMOVE_CARD = 25;
        public static final int REQUEST_LATER = 26;
        public static final int GET_LATER = 27;
        public static final int CANCEL_LATER_RIDE = 28;
        public static final int HOURLY_PACKAGE_FARE = 29;
        public static final int USER_MESSAGE_NOTIFY = 30;
        public static final int AIRPORT_LST = 31;
        public static final int LOCATION_LST = 32;
        public static final int AIRPORT_PACKAGE_FARE = 33;
        public static final int GOOGLE_DIRECTION_forcar_API = 34;
        public static final int LOCATION_API_BASE_SOURCE = 35;
        public static final int LOCATION_API_BASE_DESTINATION = 36;
        public static final int ADDRESS_API_BASE = 37;
        public static final int GET_OTP = 38;
        public static final int GOOGLE_ADDRESS_API = 39;
        public static final int WALLET_TYPES = 40;
        public static final int WALLET_BALANCE = 41;
        public static final int WALLET_CREDIT = 42;
        public static final int WALLET_PAYGATE = 43;
        public static final int CANCEL_REASON = 44;
        public static final int VALIDATE_PROMO =45;
        public static final int GET_SAVED_PLACES = 46;
        public static final int CANCEL_FAV = 47;
        public static final int ADD_FAV =48 ;
        public static final int ADVERTISEMENTS = 49;
        public static final int LOGOUT = 50;
        public static final int GET_VERSION = 51;
        public static final int MESSAGE_GET = 52;
        public static final int GEO_DEST =53 ;
        public static final int UPDATE_ADDRESS = 54;
        public static final int APPLY_REFERRAL = 55;
    }

    public class PassParam {
        public static final String SERVICE_TYPE = "service_type";
        public static final String AGGREMENT_NAME = "aggree_name";
        public static final String AGGREMENT_ID = "aggree_id";
        public static final String SCHEDULE_DATE = "schedule_date";
        public static final String SCHEDULE_TIME = "schedule_time";
        public static final String SOURCE_ADDRESS = "source_address";
        public static final String TOWING_TYPE = "towing_type";
        public static final String TOWING_PRICE = "towing_price";
        public static final String DEST_ADDRESS = "dest_address";
        public static final String BRANCH_NAME = "branch_name";
        public static final String BRANCH_ID = "branch_id";
        public static final String BRANCH_NAME_TO = "branch_name_to";
        public static final String BRANCH_ID_TO = "branch_id_to";
        public static final String CAR_SIZE = "car_size";
        public static final String PRICE = "price";
        public static final String CAR_MODEL = "car_model";
        public static final String CAR_MAKER_ID = "car_maker_id";
        public static final String CAR_SIZE_ID = "car_size_id";
        public static final String CAR_MODEL_ID = "car_model_id";
        public static final String PIATE_NUMBER = "piate_number";
        public static final String ID_NUMBER = "id_number";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String RECEIVER_NUMBER = "receiver_number";
        public static final String RECEIVER_NAME = "receiver_name";
        public static final String RECEIVER_NATIONAL_NUMBER = "receiver_national_number";
        public static final String RECEIVER_VISA_NUMBER = "receiver_visa_number";
        public static final String RECEIVER_COUNTRY = "receiver_country";
        public static final String OWNER_NAME = "owner_name";
        public static final String CAR_MODEL_NAME = "car_model_name";
    }
    // Placesurls
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String TYPE_NEAR_BY = "/nearbysearch";
    public static final String OUT_JSON = "/json";

    // Location API
    public static final String LOCATION_API_BASE = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    // Address API
    public static final String ADDRESS_API_BASE = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    public static final String GEO_DEST = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    // direction API
    public static final String DIRECTION_API_BASE = "https://maps.googleapis.com/maps/api/directions/json?";
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final String WAYPOINTS ="waypoints" ;
    public static final String EXTANCTION = "sensor=false&mode=driving&alternatives=true&key=" + Const.GOOGLE_API_KEY;
}