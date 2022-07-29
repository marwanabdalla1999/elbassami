package com.albassami.logistics.ui.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.simpleratingbar.SimpleRatingBar;
import com.bumptech.glide.Glide;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Service.LocationService;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.Utils.PreferenceHelper;
import com.albassami.logistics.Utils.RecyclerLongPressClickListener;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.AsyncTaskCompleteListener;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.ParserUtils;
import com.albassami.logistics.network.ApiManager.VollyRequester;
import com.albassami.logistics.network.Models.CancelReason;
import com.albassami.logistics.network.Models.LocationUpdate;
import com.albassami.logistics.network.Models.RequestDetail;
import com.albassami.logistics.ui.Adapter.CancelReasonAdapter;
import com.albassami.logistics.ui.Adapter.PlacesAutoCompleteAdapter;
import com.albassami.logistics.ui.activity.ChatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.albassami.logistics.Service.LocationService.ACTION_LOCATION_BROADCAST;
import static com.albassami.logistics.network.ApiManager.APIConsts.Constants;
import static com.albassami.logistics.network.ApiManager.APIConsts.Params;
import static com.albassami.logistics.network.ApiManager.APIConsts.Params.SENDER_UPDATE;
import static com.albassami.logistics.network.ApiManager.APIConsts.Params.USER_LOCATION_UPDATE;

/**
 * Created by user on 1/12/2017.
 */
public class OngoingTripFragment extends BaseMapFragment implements AsyncTaskCompleteListener, OnMapReadyCallback, EasyPermissions.PermissionCallbacks {
    Polyline poly_line;
    @BindView(R.id.tv_driver_status)
    CustomBoldRegularTextView tvDriverStatus;
    @BindView(R.id.address_title)
    CustomRegularTextView addressTitle;
    @BindView(R.id.tv_current_location)
    CustomRegularTextView tvCurrentLocation;
    @BindView(R.id.stopAddress)
    CustomRegularTextView stopAddress;
    @BindView(R.id.stopLay)
    RelativeLayout stopLay;
    @BindView(R.id.imageView2)
    CircleImageView imageView2;
    @BindView(R.id.driver_img)
    CircleImageView driverImg;
    @BindView(R.id.driver_name)
    CustomBoldRegularTextView driverName;
    @BindView(R.id.driver_car_number)
    CustomRegularTextView driverCarNumber;
    @BindView(R.id.driver_car_model)
    CustomRegularTextView driverCarModel;
    @BindView(R.id.driver_mobile_number)
    CustomRegularTextView driverMobileNumber;
    @BindView(R.id.v_line)
    View vLine;
    @BindView(R.id.cancel_trip)
    LinearLayout cancelTrip;
    @BindView(R.id.moreLay)
    LinearLayout moreLay;
    @BindView(R.id.optionsLine)
    CustomRegularTextView optionsLine;
    @BindView(R.id.addStop)
    CustomRegularTextView addStop;
    @BindView(R.id.editDrop)
    CustomRegularTextView editDrop;
    @BindView(R.id.addEditLay)
    LinearLayout addEditLay;
    private GoogleMap googleMap;
    private Bundle mBundle;
    SupportMapFragment user_travel_map;
    private View view;
    private RequestDetail requestDetail;
    private int jobStatus = 0;
    private Marker driver_car, source_marker, destination_marker, stop_marker;
    private LatLng d_latlon, s_latlon, driver_latlan, changeLatLng, stop_latlng;
    Handler checkreqstatus;
    Handler locationStatus;
    private String eta_time = "--";
    private String mobileNo = "";
    private Boolean isConnected = true;
    private boolean iscancelpopup = false;
    MarkerOptions pickup_opt;
    private LatLng delayLatlan;
    int mIndexCurrentPoint = 0;
    Bitmap mMarkerIcon;
    private ArrayList<CancelReason> cancelReasonLst;
    private GoogleMap gMap;
    ImageView pin_marker;
    AutoCompleteTextView et_source_dia_address;
    APIInterface apiInterface;
    PrefUtils prefUtils;
    Unbinder unbinder;
    LatLng latLng;
    Socket socket;
    private int RC_LOCATION_PERM = 124;
    private int RC_CALL_PERM = 127;
    LatLng myLatLng;

    BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onCurrentLocationObtained(new LatLng(intent.getDoubleExtra(Params.LATITUDE, 0.0f),
                    intent.getDoubleExtra(Params.LONGITUDE, 0.0f)));
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(() -> {
                try {
                    JSONObject object = new JSONObject();
                    object.put(Params.COMMONID, MessageFormat.format("user_id_{0}_provider_id_{1}_request_id_{2}",
                            prefUtils.getIntValue(PrefKeys.USER_ID, 0), requestDetail.getDriver_id(), requestDetail.getRequestId()));
                    object.put(Params.MYID, prefUtils.getIntValue(PrefKeys.USER_ID, 0));
                    socket.emit(SENDER_UPDATE, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = args -> getActivity().runOnUiThread(() -> {
    });

    private Emitter.Listener onConnectError = args -> {

    };

    private Emitter.Listener onNewMessage = args -> getActivity().runOnUiThread(() -> {
        JSONObject locationObject = (JSONObject) args[0];
        LocationUpdate userLocation = new LocationUpdate();
        userLocation.setLatitude(locationObject.optDouble(Params.LATITUDE));
        userLocation.setLongitude(locationObject.optDouble(Params.LONGITUDE));
        updateMarkerOnMap(userLocation.getLatitude(), userLocation.getLongitude());
    });

    public void updateMarkerOnMap(double latitude, double longitude) {
        if (driver_car != null)
            driver_car.remove();
        MarkerOptions opty = new MarkerOptions();
        opty.position(new LatLng(latitude, longitude));
        opty.anchor(0.5f, 0.5f);
        opty.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_booking_lux_map_topview));
        driver_car = googleMap.addMarker(opty);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.travel_fragment, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        initSocket();
        unbinder = ButterKnife.bind(this, view);
        prefUtils = PrefUtils.getInstance(activity);
        tvCurrentLocation.setSelected(true);
        startLocationService();
        user_travel_map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.user_travel_map);
        if (null != user_travel_map)
            user_travel_map.getMapAsync(this);
        clickListerners(savedInstanceState);
        return view;
    }

    public void onCurrentLocationObtained(LatLng latLng) {
        if (latLng != null) {
            myLatLng = new LatLng(latLng.latitude,
                    latLng.longitude);
        }
    }

    public void startLocationService() {
        try {
            startLocationBroadcastReceiver();
            Intent intent = new Intent(getActivity(), LocationService.class);
            getActivity().startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startLocationBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                locationReceiver, new IntentFilter(ACTION_LOCATION_BROADCAST)
        );
    }


    private void initSocket() {
        try {
            socket = IO.socket(APIConsts.Urls.SOCKET_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on(Params.PROVIDER_LOCATION, onNewMessage);
        socket.connect();
    }

    private void attemptToSendLocation(double latitude, double longitude) {
        if (!socket.connected()) return;
        JSONObject locationObject = new JSONObject();
        try {
            locationObject.put(Params.USER_ID, prefUtils.getIntValue(PrefKeys.USER_ID, 0));
            locationObject.put(Params.PROVIDER_ID, requestDetail.getDriver_id());
            locationObject.put(Params.REQUEST_ID, requestDetail.getRequestId());
            locationObject.put(Params.USER_TOKEN, prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
            locationObject.put(Params.LATITUDE, latitude);
            locationObject.put(Params.LONGITUDE, longitude);
            socket.emit(USER_LOCATION_UPDATE, locationObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clickListerners(Bundle savedInstanceState) {
        addStop.setOnClickListener(view -> {
            if (Integer.valueOf(requestDetail.getIsAdStop()) == 0) {
                //openMap("stop", savedInstanceState);
                openMap("stop", savedInstanceState, true);
            } else {
                Toast.makeText(activity, "Stop already added", Toast.LENGTH_SHORT).show();
            }
        });
        editDrop.setOnClickListener(view -> openMap("dest", savedInstanceState, false));
    }


    private void moreLayoutVisibility() {
        if (addEditLay.getVisibility() == View.VISIBLE) {
            addEditLay.setVisibility(View.GONE);
            vLine.setVisibility(View.GONE);
        } else {
            addEditLay.setVisibility(View.VISIBLE);
            vLine.setVisibility(View.VISIBLE);
            Log.e("asher", "visibile ");
        }
        if (optionsLine.getVisibility() == View.VISIBLE) {
            optionsLine.setVisibility(View.GONE);
        } else {
            optionsLine.setVisibility(View.VISIBLE);
        }
    }

    //  Change to dialog fragment...
    private void openMap(final String type, Bundle savedInstanceState, boolean addstop) {
        final Dialog searchMap = new Dialog(activity, R.style.DialogSlideAnim_leftright_Fullscreen);
        searchMap.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchMap.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        searchMap.setCancelable(true);
        searchMap.setContentView(R.layout.search_map_dialog);
        MapView mMapView = searchMap.findViewById(R.id.search_map);
        pin_marker = searchMap.findViewById(R.id.pin_location);
        final Button btn_done = searchMap.findViewById(R.id.btn_done);
        ImageButton search_dai_back = searchMap.findViewById(R.id.search_dai_back);
        et_source_dia_address = searchMap.findViewById(R.id.et_source_dia_address);
        if (type.equalsIgnoreCase("dest")) {
            et_source_dia_address.setText(requestDetail.getD_address());
        }
        if (addstop) {
            et_source_dia_address.setHint("Add Stop");
        } else {
            et_source_dia_address.setHint("Add Drop Location");
        }
        btn_done.requestFocus();
        final PlacesAutoCompleteAdapter S_placesadapter = new PlacesAutoCompleteAdapter(activity,
                R.layout.autocomplete_list_text);
        et_source_dia_address.setAdapter(S_placesadapter);
        et_source_dia_address.setDropDownBackgroundDrawable(new ColorDrawable(Color.WHITE));
        search_dai_back.setOnClickListener(v -> searchMap.dismiss());
        btn_done.setOnClickListener(v -> {
            if (et_source_dia_address.getText().toString() != null && !et_source_dia_address.getText().toString().isEmpty()) {
                destinationChangesApi(changeLatLng, et_source_dia_address.getText().toString(), type);
                searchMap.cancel();
            } else {
                Toast.makeText(activity, "Please enter address", Toast.LENGTH_SHORT).show();
            }
            moreLayoutVisibility();
            searchMap.cancel();
        });
        et_source_dia_address.setOnItemClickListener((adapterView, view, i, l) -> {
            et_source_dia_address.setSelection(0);
            final String selectedSourcePlace = S_placesadapter.getItem(i);
            changeLatLng = getLatLngFrmAddress(selectedSourcePlace);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(changeLatLng, 15));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(changeLatLng, 16));
        });
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(googleMap -> {
            gMap = googleMap;
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.getUiSettings().setMapToolbarEnabled(true);
            gMap.getUiSettings().setScrollGesturesEnabled(true);
            EasyPermissions.requestPermissions(this, getString(R.string.youNeedToGrantPermission),
                    RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
            gMap.setMyLocationEnabled(true);
            changeLatLng = new LatLng(d_latlon.latitude, d_latlon.longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(changeLatLng,
                    15));
            gMap.setOnCameraChangeListener(cameraPosition -> {
//                et_source_dia_address.setText(getAddressFromLatLng(cameraPosition.target.latitude, cameraPosition.target.longitude));
//                changeLatLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                btn_done.requestFocus();
            });
        });
        searchMap.setOnDismissListener(dialogInterface -> {
            mMapView.setVisibility(View.GONE);
            mMapView.removeAllViews();
        });
        pin_marker.setImageResource(R.mipmap.drop_location);
        searchMap.show();
    }


    private String getAddressFromLatLng(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private LatLng getLatLngFrmAddress(String address) {
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latLng;
    }


    private void tearDownSocket() {
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.off(Params.PROVIDER_LOCATION, onNewMessage);
    }

    protected void destinationChangesApi(LatLng changeLatLng, String destinationAddress, String type) {
        UiUtils.showLoadingDialog(activity);
        Call<String> call = apiInterface.updateAddress(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                , requestDetail.getRequestId()
                , destinationAddress
                , changeLatLng.latitude
                , changeLatLng.longitude
                , type.equalsIgnoreCase("stop") ? "0" : "1"
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject createRequestResponse = null;
                try {
                    createRequestResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (createRequestResponse != null) {
                    if (createRequestResponse.optString(Const.Params.SUCCESS).equals(Constants.TRUE)) {
                        if (createRequestResponse.optString("change_type").equalsIgnoreCase("0")) {
                            requestDetail.setAdStopLatitude(createRequestResponse.optString("adstop_latitude"));
                            requestDetail.setAdStopLongitude(createRequestResponse.optString("adstop_longitude"));
                            requestDetail.setAdStopAddress(createRequestResponse.optString("adstop_address"));
                            requestDetail.setIsAdStop(createRequestResponse.optString("is_adstop"));
                            if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                if (stopLay.getVisibility() == View.GONE) {
                                    stopLay.setVisibility(View.VISIBLE);
                                    stopAddress.setText(requestDetail.getAdStopAddress());
                                }
                            }
                            getDirectionsWay(Double.valueOf(requestDetail.getS_lat()), Double.valueOf(requestDetail.getS_lon()), Double.valueOf(requestDetail.getD_lat()), Double.valueOf(requestDetail.getD_lon()),
                                    Double.valueOf(createRequestResponse.optString("adstop_latitude")), Double.valueOf(createRequestResponse.optString("adstop_longitude")));
                        } else if (createRequestResponse.optString("change_type").equalsIgnoreCase("1")) {
                            requestDetail.setD_lat(createRequestResponse.optString("d_latitude"));
                            requestDetail.setD_lon(createRequestResponse.optString("d_longitude"));
                            requestDetail.setD_address(createRequestResponse.optString("d_address"));
                            requestDetail.setIsAddressChanged(createRequestResponse.optString("is_address_changed"));
                            if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                getDirectionsWay(Double.valueOf(requestDetail.getS_lat()), Double.valueOf(requestDetail.getS_lon()), Double.valueOf(createRequestResponse.optString("d_latitude")), Double.valueOf(createRequestResponse.optString("d_longitude")),
                                        Double.valueOf(requestDetail.getAdStopLatitude()), Double.valueOf(requestDetail.getAdStopLongitude()));
                            } else {
                                getDirections(Double.valueOf(requestDetail.getS_lat()), Double.valueOf(requestDetail.getS_lon()), Double.valueOf(createRequestResponse.optString("d_latitude")), Double.valueOf(createRequestResponse.optString("d_longitude")));
                            }
                        }
                    } else {
                        UiUtils.showShortToast(activity, createRequestResponse.optString(Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getActivity())) {
                    UiUtils.showShortToast(getActivity(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void call() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobileNo));
        startActivity(callIntent);
    }

    protected void newRequestStatusCheck() {
        Call<String> call = apiInterface.requestStatusCheckNew(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject requestResponse = null;
                try {
                    requestResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (requestResponse != null) {
                    if (requestResponse.optString(Const.Params.SUCCESS).equals(Constants.TRUE)) {
                        UiUtils.hideLoadingDialog();

                    } else {
                        UiUtils.showShortToast(activity, requestResponse.optString(Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getActivity())) {
                    UiUtils.showShortToast(getActivity(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    protected void cancelOngoingRide(String reason_id, String reasontext) {
        UiUtils.showLoadingDialog(activity);
        Call<String> call = apiInterface.cancelOngoingRide(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                , requestDetail.getRequestId()
                , reason_id
                , reasontext);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject cancelResponse = null;
                try {
                    cancelResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cancelResponse != null) {
                    if (cancelResponse.optString(Const.Params.SUCCESS).equals(Constants.TRUE)) {
                        UiUtils.hideLoadingDialog();
//                        TODO:Clear data
//                        new PreferenceHelper(activity_add_vehicles).clearRequestData();
                        PreferenceHelper preferenceHelper = new PreferenceHelper(activity);
                        preferenceHelper.putIs_Ongoing(true);
                        activity.addFragment(new HomeStartFragment(), false, Const.HOME_START_FRAGMENT, true);
                    } else {
                        UiUtils.showShortToast(activity, cancelResponse.optString(Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getActivity())) {
                    UiUtils.showShortToast(getActivity(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    protected void getCancelReasons() {
        UiUtils.showLoadingDialog(activity);
        Call<String> call = apiInterface.cancelReasonsList(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                cancelReasonLst.clear();
                JSONObject cancelReasonsResponse = null;
                try {
                    cancelReasonsResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cancelReasonsResponse != null) {
                    if (cancelReasonsResponse.optString(Const.Params.SUCCESS).equals(Constants.TRUE)) {
                        JSONArray cancelReasonArray = cancelReasonsResponse.optJSONArray(Params.DATA);
                        if (null != cancelReasonArray && cancelReasonArray.length() > 0)
                            for (int i = 0; i < cancelReasonArray.length(); i++) {
                                JSONObject dataObj = cancelReasonArray.optJSONObject(i);
                                CancelReason cancel = new CancelReason();
                                cancel.setReasonId(dataObj.optString("reason_id"));
                                cancel.setReasontext(dataObj.optString("cancel_reason"));
                                cancelReasonLst.add(cancel);
                            }
                        if (null != cancelReasonLst && cancelReasonLst.size() > 0) {
                            CancelReasonDialog(cancelReasonLst);
                        } else {
                            AndyUtils.showShortToast(getResources().getString(R.string.txt_no_cancel_reason), activity);
                        }
                    } else {
                        UiUtils.showShortToast(activity, cancelReasonsResponse.optString(Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getActivity())) {
                    UiUtils.showShortToast(getActivity(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = savedInstanceState;
        checkreqstatus = new Handler();
        locationStatus = new Handler();
        cancelReasonLst = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
        }
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            requestDetail = (RequestDetail) mBundle.getSerializable(
                    Const.REQUEST_DETAIL);
            jobStatus = mBundle.getInt(Const.DRIVER_STATUS,
                    Const.IS_DRIVER_DEPARTED);
            Glide.with(activity).load(requestDetail.getDriver_picture()).into(driverImg);
            Glide.with(activity).load(requestDetail.getDriver_car_picture()).into(imageView2);
            if (jobStatus == 1 || jobStatus == 2) {
                addressTitle.setText(activity.getString(R.string.txt_pickup_address));
                tvCurrentLocation.setTextColor(ContextCompat.getColor(activity, R.color.green));
                tvCurrentLocation.setText(requestDetail.getS_address());
            } else {
                addressTitle.setText(activity.getString(R.string.txt_drop_address));
                addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.red));
                if (!requestDetail.getD_address().equals("")) {
                    tvCurrentLocation.setText(requestDetail.getD_address());
                } else {
                    tvCurrentLocation.setText(getResources().getString(R.string.not_available));
                }
                if (source_marker != null) {
                    source_marker.hideInfoWindow();
                }
            }
            driverName.setText(requestDetail.getDriver_name());
            driverMobileNumber.setText(getResources().getString(R.string.txt_mobile) + " " + requestDetail.getDriver_mobile());
            driverCarNumber.setText(getResources().getString(R.string.txt_car_no) + " " + requestDetail.getDriver_car_number());
            driverCarModel.setText(requestDetail.getDriver_car_color() + " " + requestDetail.getDriver_car_model());
            mobileNo = requestDetail.getDriver_mobile();
            if (requestDetail.getRequest_type().equals("2") || requestDetail.getRequest_type().equals("3")) {
                moreLay.setVisibility(View.GONE);
            }
        }
    }


    private Bitmap getMarkerBitmapFromViewforsource(String value) {
        View customMarkerView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.source_infowindow, null);
        ImageView info_iv = customMarkerView.findViewById(R.id.info_iv);
        if (value.equals("1")) {
            info_iv.setImageResource(R.mipmap.pickup_location);
        } else {
            info_iv.setImageResource(R.mipmap.drop_location);
        }
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private Bitmap getMarkerBitmapFromView(String eta, String value) {
        String time = eta.replaceAll("\\s+", "\n");
        View customMarkerView = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.eta_info_window, null);
        TextView markertext = customMarkerView.findViewById(R.id.txt_eta);
        ImageView iv = customMarkerView.findViewById(R.id.eta_iv);
        if (value.equals("1")) {
            iv.setImageResource(R.drawable.s_eta_circle);
        } else {
            iv.setImageResource(R.drawable.d_eta_circle);
        }
        markertext.setText(time);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    private void getDirections(double latitude, double longitude, double latitude1, double longitude1) {
        if (!AndyUtils.isNetworkAvailable(activity)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.URL, Const.DIRECTION_API_BASE + Const.ORIGIN + "="
                + latitude + "," + longitude + "&" + Const.DESTINATION + "="
                + latitude1 + "," + longitude1 + "&" + Const.EXTANCTION);
        new VollyRequester(activity, Const.GET, map, Const.ServiceCode.GOOGLE_DIRECTION_API, this);
    }


    @Override
    public void onResume() {
        super.onResume();
        activity.currentFragment = Const.TRAVEL_MAP_FRAGMENT;
        requestStatusCheck();
        startgetreqstatus();
    }

    public void drawPath(String result) {
        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            PolylineOptions options = new PolylineOptions().width(8).color(Color.BLACK).geodesic(true);
            for (int z = 0; z < list.size(); z++) {
                LatLng point = list.get(z);
                options.add(point);
            }
            if (googleMap != null) {
                if (poly_line != null) {
                    poly_line.remove();
                }
                poly_line = googleMap.addPolyline(options);
            }
        } catch (Exception e) {
        }
    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    private void startgetreqstatus() {
        startCheckstatusTimer();
    }

    private void startCheckstatusTimer() {
        checkreqstatus.postDelayed(reqrunnable, 10000);
    }

    private void startGettingLocation() {
        locationStatus.postDelayed(locationRunnable, 20000);
    }


    private void stopCheckingforstatus() {
        if (checkreqstatus != null) {
            checkreqstatus.removeCallbacks(reqrunnable);
            Log.d("mahi", "stop status handler");
        }
    }

    private void stopGettingRequest() {
        if (locationStatus != null) {
            locationStatus.removeCallbacks(locationRunnable);
        }
    }


    Runnable reqrunnable = new Runnable() {
        public void run() {
            requestStatusCheck();
            if (myLatLng != null)
                attemptToSendLocation(myLatLng.latitude, myLatLng.longitude);
            checkreqstatus.postDelayed(this, 5000);
        }
    };

    Runnable locationRunnable = new Runnable() {
        public void run() {
//            requestStatusCheck();
            locationStatus.postDelayed(this, 20000);
        }
    };

    protected void requestStatusCheck() {
        Call<String> call = apiInterface.pingRequestStatusCheck(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject requestStatusResponse = null;
                    try {
                        requestStatusResponse = new JSONObject(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (requestStatusResponse != null) {
                        if (requestStatusResponse.optString(Const.Params.SUCCESS).equals(Constants.TRUE)) {
                            requestDetail = new RequestDetail();
                            requestDetail = ParserUtils.parseRequestStatus(response.body());
                            Bundle bundle = new Bundle();
                            prefUtils.setValue(PrefKeys.REQUEST_ID, requestDetail.getRequestId());
                            int reqId = prefUtils.getIntValue(PrefKeys.REQUEST_ID, requestDetail.getRequestId());
                            prefUtils.setValue(PrefKeys.PROVIDER_ID, requestDetail.getProviderId());
                            if (stop_latlng != null)
                                stop_latlng = new LatLng(Double.valueOf(requestDetail.getAdStopLatitude()), Double.valueOf(requestDetail.getAdStopLongitude()));
                            String providerId = requestDetail.getProviderId();
                            switch (requestDetail.getTripStatus()) {
                                case Const.NO_REQUEST:
                                    if (isAdded() && iscancelpopup == false && googleMap != null && activity.currentFragment.equals(Const.TRAVEL_MAP_FRAGMENT)) {
                                        iscancelpopup = true;
                                        stopCheckingforstatus();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setMessage(getResources().getString(R.string.txt_cancel_driver))
                                                .setCancelable(false)
                                                .setPositiveButton(getResources().getString(R.string.txt_ok), (dialog, id) -> {
                                                    new PreferenceHelper(activity).clearRequestData();
                                                    dialog.dismiss();
                                                    activity.addFragment(new HomeStartFragment(), false, Const.HOME_START_FRAGMENT, true);

                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                    break;
                                case Const.IS_ACCEPTED:
                                    jobStatus = Const.IS_ACCEPTED;
                                    addressTitle.setText(activity.getString(R.string.txt_pickup_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                    tvCurrentLocation.setText(requestDetail.getS_address());
                                    tvDriverStatus.setText(activity.getString(R.string.text_job_accepted));
                                    findDistanceAndTimeforTypes(s_latlon, driver_latlan);
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1 && stop_marker == null) {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude, stop_latlng.latitude, stop_latlng.longitude);
                                    } else {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude);
                                    }
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                        if (stopLay.getVisibility() == View.GONE) {
                                            stopLay.setVisibility(View.VISIBLE);
                                            stopAddress.setText(requestDetail.getAdStopAddress());
                                        }
                                    }
                                    break;
                                case Const.IS_DRIVER_DEPARTED:
                                    jobStatus = Const.IS_DRIVER_DEPARTED;
                                    addressTitle.setText(activity.getString(R.string.txt_pickup_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.green));
                                    tvCurrentLocation.setText(requestDetail.getS_address());
                                    tvDriverStatus.setText(activity.getString(R.string.text_driver_started));
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1 && stop_marker == null) {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude, stop_latlng.latitude, stop_latlng.longitude);
                                    } else {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude);
                                    }
                                    findDistanceAndTimeforTypes(s_latlon, driver_latlan);
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                        if (stopLay.getVisibility() == View.GONE) {
                                            stopLay.setVisibility(View.VISIBLE);
                                            stopAddress.setText(requestDetail.getAdStopAddress());
                                        }
                                    }
                                    break;
                                case Const.IS_DRIVER_ARRIVED:
                                    jobStatus = Const.IS_DRIVER_ARRIVED;
                                    addressTitle.setText(activity.getString(R.string.txt_drop_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                    if (!requestDetail.getD_address().equals("")) {
                                        tvCurrentLocation.setText(requestDetail.getD_address());
                                    } else {
                                        tvCurrentLocation.setText(getResources().getString(R.string.not_available));
                                    }
                                    tvDriverStatus.setText(activity.getString(R.string.text_driver_arrvied));
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1 && stop_marker == null) {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude, stop_latlng.latitude, stop_latlng.longitude);
                                    } else {
                                        getDirectionsWay(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude(), s_latlon.latitude, s_latlon.longitude);
                                    }
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                        if (stopLay.getVisibility() == View.GONE) {
                                            stopLay.setVisibility(View.VISIBLE);
                                            stopAddress.setText(requestDetail.getAdStopAddress());
                                        }
                                    }
                                    driver_latlan = new LatLng(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude());
                                    findDistanceAndTimeforTypes(s_latlon, driver_latlan);
                                    break;
                                case Const.IS_DRIVER_TRIP_STARTED:
                                    cancelTrip.setVisibility(View.GONE);
                                    jobStatus = Const.IS_DRIVER_TRIP_STARTED;
                                    addressTitle.setText(activity.getString(R.string.txt_drop_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                    if (!requestDetail.getD_address().equals("")) {
                                        tvCurrentLocation.setText(requestDetail.getD_address());
                                    } else {
                                        tvCurrentLocation.setText(getResources().getString(R.string.not_available));
                                    }
                                    tvDriverStatus.setText(activity.getString(R.string.text_trip_started));
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1 && stop_marker == null) {
                                        getDirectionsWay(s_latlon.latitude, s_latlon.longitude, d_latlon.latitude, d_latlon.longitude, stop_latlng.latitude, stop_latlng.longitude);
                                    } else {
                                        getDirectionsWay(s_latlon.latitude, s_latlon.longitude, d_latlon.latitude, d_latlon.longitude);
                                    }
                                    if (Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                                        if (stopLay.getVisibility() == View.GONE) {
                                            stopLay.setVisibility(View.VISIBLE);
                                            stopAddress.setText(requestDetail.getAdStopAddress());
                                        }
                                    }
                                    cancelTrip.setVisibility(View.GONE);
                                    findDistanceAndTimeforTypes(d_latlon, driver_latlan);
                                    break;
                                case Const.IS_DRIVER_TRIP_ENDED:
                                    jobStatus = Const.IS_DRIVER_TRIP_ENDED;
                                    addressTitle.setText(activity.getString(R.string.txt_drop_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                    if (!requestDetail.getD_address().equals("")) {
                                        tvCurrentLocation.setText(requestDetail.getD_address());
                                    } else {
                                        tvCurrentLocation.setText(getResources().getString(R.string.not_available));
                                    }
                                    tvDriverStatus.setText(activity.getString(R.string.text_trip_completed));
                                    cancelTrip.setVisibility(View.GONE);
                                    if (d_latlon == null) {
                                        d_latlon = new LatLng(latLng.latitude, latLng.longitude);
                                    }
                                    findDistanceAndTimeforTypes(d_latlon, driver_latlan);
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_TRIP_ENDED);
                                    if (!activity.currentFragment.equals(Const.RATING_FRAGMENT) && !activity.isFinishing()) {
                                        stopCheckingforstatus();
                                        RatingFragment feedbackFragment = new RatingFragment();
                                        feedbackFragment.setArguments(bundle);
                                        if (activity != null)
                                            activity.addFragment(feedbackFragment, false, Const.RATING_FRAGMENT, true);
                                    }
                                    break;
                                case Const.IS_DRIVER_RATED:
                                    jobStatus = Const.IS_DRIVER_TRIP_ENDED;
                                    addressTitle.setText(activity.getString(R.string.txt_drop_address));
                                    addressTitle.setTextColor(ContextCompat.getColor(activity, R.color.red));
                                    if (d_latlon != null && driver_latlan != null) {
                                        findDistanceAndTimeforTypes(d_latlon, driver_latlan);
                                    }
                                    if (!requestDetail.getD_address().equals("")) {
                                        tvCurrentLocation.setText(requestDetail.getD_address());
                                    } else {
                                        tvCurrentLocation.setText(getResources().getString(R.string.not_available));
                                    }
                                    tvDriverStatus.setText(activity.getString(R.string.text_trip_completed));
                                    bundle.putSerializable(Const.REQUEST_DETAIL, requestDetail);
                                    bundle.putInt(Const.DRIVER_STATUS, Const.IS_DRIVER_TRIP_ENDED);
                                    cancelTrip.setVisibility(View.GONE);
                                    if (!activity.currentFragment.equals(Const.RATING_FRAGMENT)) {
                                        stopCheckingforstatus();
                                        RatingFragment feedbackFragment = new RatingFragment();
                                        feedbackFragment.setArguments(bundle);
                                        activity.addFragment(feedbackFragment, false, Const.RATING_FRAGMENT, true);
                                    }
                                    break;
                            }
                        } else {
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getActivity())) {
                    UiUtils.showShortToast(getActivity(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    private void fitmarkers_toMap() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(source_marker.getPosition());
        builder.include(destination_marker.getPosition());
        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10);
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        googleMap.moveCamera(cu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tearDownSocket();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.user_travel_map);
        if (f != null) {
            try {
                getFragmentManager().beginTransaction().remove(f).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stopLocationService();
        stopGettingRequest();
        ViewGroup mContainer = getActivity().findViewById(R.id.content_frame);
        mContainer.removeAllViews();
        googleMap = null;
        stopCheckingforstatus();
    }


    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case Const.ServiceCode.GOOGLE_DIRECTION_API:
                try {
                    if (response != null) {
                        if (Integer.valueOf(requestDetail.getIsAdStop()) == 1 && stop_marker == null) {
                            stop_latlng = new LatLng(Double.valueOf(requestDetail.getAdStopLatitude()), Double.valueOf(requestDetail.getAdStopLongitude()));
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(stop_latlng);
                            opt.anchor(0.5f, 0.5f);
                            opt.icon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.pin_stop));
                            stop_marker = googleMap.addMarker(opt);
                        }
                        if (destination_marker != null) {
                            destination_marker.remove();
                            d_latlon = new LatLng(Double.valueOf(requestDetail.getD_lat()), Double.valueOf(requestDetail.getD_lon()));
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(d_latlon);
                            opt.anchor(0.5f, 0.5f);
                            opt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drop_location));
                            destination_marker = googleMap.addMarker(opt);
                        } else {
                            d_latlon = new LatLng(Double.valueOf(requestDetail.getD_lat()), Double.valueOf(requestDetail.getD_lon()));
                            MarkerOptions opt = new MarkerOptions();
                            opt.position(d_latlon);
                            opt.anchor(0.5f, 0.5f);
                            opt.icon(BitmapDescriptorFactory
                                    .fromResource(R.mipmap.drop_location));
                            destination_marker = googleMap.addMarker(opt);
                        }
                        drawPath(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Const.ServiceCode.GOOGLE_MATRIX:
                try {
                    JSONObject fareResponse = new JSONObject(response);
                    fareResponse.optString(Constants.STATUS).equals(Constants.OK);
                    JSONArray sourceArray = fareResponse.optJSONArray("origin_addresses");
                    String sourceObject = (String) sourceArray.opt(0);
                    JSONArray destinationArray = fareResponse.optJSONArray("destination_addresses");
                    String destinationObject = (String) destinationArray.opt(0);
                    JSONArray jsonArray = fareResponse.optJSONArray("rows");
                    JSONObject elementsObject = jsonArray.optJSONObject(0);
                    JSONArray elementsArray = elementsObject.optJSONArray("elements");
                    JSONObject distanceObject = elementsArray.optJSONObject(0);
                    try {
                        JSONObject dObject = distanceObject.optJSONObject("distance");
                        String distance = dObject.optString("text");
                        JSONObject durationObject = distanceObject.optJSONObject("duration");
                        String duration = durationObject.optString("text");
                        String dis = dObject.optString("value");
                        String dur = durationObject.optString("value");
                        double trip_dis = Integer.valueOf(dis) * 0.001;
                        eta_time = duration;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (pickup_opt != null && source_marker != null) {
                        if (jobStatus == 1 || jobStatus == 2 || jobStatus == 3) {
                            activity.runOnUiThread(() -> {
                                if (jobStatus == 3) {
                                    source_marker.setIcon((BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView("0 MIN", "1"))));
                                } else {
                                    source_marker.setIcon((BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(eta_time, "1"))));
                                }
                                if (null != destination_marker) {
                                    destination_marker.setIcon((BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromViewforsource("2"))));
                                }
                            });
                        } else {
                            activity.runOnUiThread(() -> {
                                source_marker.setIcon((BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromViewforsource("1"))));
                                if (null != destination_marker) {
                                    destination_marker.setIcon((BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(eta_time, "2"))));
                                }

                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
//    protected void getDistanceAndDuration(LatLng picLatLng, LatLng dropLatLng) {
//        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+picLatLng.latitude+","+picLatLng.longitude+"&destination="+dropLatLng.latitude+","+dropLatLng.longitude+
//        "&mode=drving&language=en-EN&Key"+ APIConsts.Constants.GOOGLE_API_KEY +"&sensor=false";
//        Call<String> call = apiInterface.getDistanceAndDuration(url);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                JSONObject fareResponse = null;
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (fareResponse != null) {
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                NetworkUtils.onApiError(activity_add_vehicles);
//            }
//        });
//    }

    private void findDistanceAndTimeforTypes(LatLng pic_latlan, LatLng drop_latlan) {
        if (!AndyUtils.isNetworkAvailable(activity)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.URL, Const.GOOGLE_MATRIX_URL + Const.Params.ORIGINS + "="
                + pic_latlan.latitude + "," + pic_latlan.longitude + "&" + Const.Params.DESTINATION + "="
                + drop_latlan.latitude + "," + drop_latlan.longitude + "&" + Const.Params.MODE + "="
                + "driving" + "&" + Const.Params.LANGUAGE + "="
                + "en-EN" + "&" + "key=" + Const.GOOGLE_API_KEY + "&" + Const.Params.SENSOR + "="
                + false);
        new VollyRequester(activity, Const.GET, map, Const.ServiceCode.GOOGLE_MATRIX, this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopCheckingforstatus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCheckingforstatus();
        stopGettingRequest();
    }


    @Override
    public void onMapReady(GoogleMap mgoogleMap) {
        // map.
        googleMap = mgoogleMap;
        AndyUtils.removeProgressDialog();
        if (googleMap != null) {
            googleMap.setTrafficEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            EasyPermissions.requestPermissions(this, getString(R.string.youNeedToGrantPermission),
                    RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
            googleMap.setMyLocationEnabled(false);
            LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            List<String> providers = lm.getProviders(true);
            Location l = null;
            if (requestDetail != null) {
                if (requestDetail.getS_lat() != null) {
                    s_latlon = new LatLng(Double.valueOf(requestDetail.getS_lat()), Double.valueOf(requestDetail.getS_lon()));
                    pickup_opt = new MarkerOptions();
                    pickup_opt.position(s_latlon);
                    pickup_opt.title(getResources().getString(R.string.txt_pickup));
                    pickup_opt.anchor(0.5f, 0.5f);
                    pickup_opt.icon(BitmapDescriptorFactory
                            .fromBitmap(getMarkerBitmapFromView(eta_time, "1")));
                    source_marker = googleMap.addMarker(pickup_opt);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(s_latlon, 15));
                }
                if (requestDetail.getD_lat() != null && !requestDetail.getD_lat().equals("") && !requestDetail.getD_address().equals("")) {
                    d_latlon = new LatLng(Double.valueOf(requestDetail.getD_lat()), Double.valueOf(requestDetail.getD_lon()));
                    MarkerOptions opt = new MarkerOptions();
                    opt.position(d_latlon);
                    opt.title(activity.getResources().getString(R.string.txt_drop_loc));
                    opt.anchor(0.5f, 0.5f);
                    opt.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.map_drop_marker));
                    destination_marker = googleMap.addMarker(opt);
                }
                driver_latlan = new LatLng(requestDetail.getDriver_latitude(), requestDetail.getDriver_longitude());
                try {
                    if (d_latlon != null && s_latlon != null) {
                        if (Integer.valueOf(requestDetail.getIsAdStop()) != null && Integer.valueOf(requestDetail.getIsAdStop()) == 1) {
                            stop_latlng = new LatLng(Double.valueOf(requestDetail.getAdStopLatitude()), Double.valueOf(requestDetail.getAdStopLongitude()));
                            getDirectionsWay(s_latlon.latitude, s_latlon.longitude, d_latlon.latitude, d_latlon.longitude, stop_latlng.latitude, stop_latlng.longitude);

                        } else {
                            getDirections(s_latlon.latitude, s_latlon.longitude, d_latlon.latitude, d_latlon.longitude);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                                   @Override
                                                   public View getInfoWindow(Marker marker) {
                                                       View vew = null;
                                                       if (destination_marker != null) {
                                                           if (marker.getId().equals(destination_marker.getId())) {
                                                               vew = activity.getLayoutInflater().inflate(R.layout.info_window_dest, null);
                                                           } else if (!marker.getId().equals(source_marker.getId())) {
                                                               vew = activity.getLayoutInflater().inflate(R.layout.driver_info_window, null);
                                                               TextView txt_driver_name = vew.findViewById(R.id.driver_name);
                                                               SimpleRatingBar driver_rate = vew.findViewById(R.id.driver_rate);
                                                               driver_rate.setVisibility(View.GONE);
                                                           }
                                                       }
                                                       return vew;
                                                   }

                                                   @Override
                                                   public View getInfoContents(Marker marker) {
                                                       return null;
                                                   }
                                               }
                );
                if (isAdded() && destination_marker != null) {
                    fitmarkers_toMap();
                }
            }
        }
    }

    private void getDirectionsWay(double latitude, double longitude, double latitude1, double longitude1, double latitideStop, double longitudeStop) {
        if (!AndyUtils.isNetworkAvailable(activity)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.URL, Const.DIRECTION_API_BASE + Const.ORIGIN + "="
                + latitude + "," + longitude + "&" + Const.DESTINATION + "="
                + latitude1 + "," + longitude1 + "&" + Const.WAYPOINTS + "="
                + latitideStop + "," + longitudeStop + "&" + Const.EXTANCTION);
        Log.e("asher", "directions stop map " + map);
        new VollyRequester(activity, Const.GET, map, Const.ServiceCode.GOOGLE_DIRECTION_API, this);
    }

    private void getDirectionsWay(double latitude, double longitude, double latitude1, double longitude1) {
        if (!AndyUtils.isNetworkAvailable(activity)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.URL, Const.DIRECTION_API_BASE + Const.ORIGIN + "="
                + latitude + "," + longitude + "&" + Const.DESTINATION + "="
                + latitude1 + "," + longitude1 + "&" + Const.EXTANCTION);
        Log.e("asher", "directions stop map " + map);
        new VollyRequester(activity, Const.GET, map, Const.ServiceCode.GOOGLE_DIRECTION_API, this);
    }


    private void CancelReasonDialog(final ArrayList<CancelReason> cancelReasonLst) {
        final Dialog CancelReasondialog = new Dialog(activity, R.style.DialogThemeforview);
        CancelReasondialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CancelReasondialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.fade_drawable));
        CancelReasondialog.setCancelable(false);
        CancelReasondialog.setContentView(R.layout.cancel_request_layout);
        RecyclerView cancel_reason_lst = CancelReasondialog.findViewById(R.id.cancel_reason_lst);
        CancelReasonAdapter CancelAdapter = new CancelReasonAdapter(activity, cancelReasonLst);
        cancel_reason_lst.setLayoutManager(new LinearLayoutManager(getActivity()));
        cancel_reason_lst.setAdapter(CancelAdapter);
        cancel_reason_lst.addOnItemTouchListener(new RecyclerLongPressClickListener(activity, cancel_reason_lst, new RecyclerLongPressClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                cancelOngoingRide(cancelReasonLst.get(position).getReasonId(), cancelReasonLst.get(position).getReasontext());
                CancelReasondialog.dismiss();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        CancelReasondialog.show();
    }


    @OnClick({R.id.driver_contact, R.id.cancel_trip, R.id.moreLay, R.id.driver_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.driver_contact:
                final iOSDialog ContactDialog = new iOSDialog(activity);
                ContactDialog.setTitle(getResources().getString(R.string.txt_contact_driver));
                ContactDialog.setSubtitle(mobileNo);
                ContactDialog.setNegativeLabel(getResources().getString(R.string.txt_call));
                ContactDialog.setPositiveLabel(getResources().getString(R.string.txt_msg));
                ContactDialog.setBoldPositiveLabel(false);
                ContactDialog.setNegativeListener(view13 -> {
                    mobileNo = requestDetail.getDriver_mobile();
                    if (!mobileNo.equals("")) {
                        EasyPermissions.requestPermissions(this, getString(R.string.youNeedToGrantPermission),
                                RC_CALL_PERM, Manifest.permission.CALL_PHONE);
                    }
                    ContactDialog.dismiss();
                });
                ContactDialog.setPositiveListener(view14 -> {
                    if (requestDetail != null) {
                        Intent i = new Intent(activity, ChatActivity.class);
                        i.putExtra(Params.PROVIDER_ID, requestDetail.getDriver_id());
                        startActivity(i);
                    }
                    ContactDialog.dismiss();
                });
                ContactDialog.show();
                break;
            case R.id.cancel_trip:
                final iOSDialog cancelDialog = new iOSDialog(activity);
                cancelDialog.setTitle(getResources().getString(R.string.cancel_ride));
                cancelDialog.setSubtitle(getResources().getString(R.string.cancel_txt));
                cancelDialog.setNegativeLabel(getResources().getString(R.string.txt_no));
                cancelDialog.setPositiveLabel(getResources().getString(R.string.txt_yes));
                cancelDialog.setBoldPositiveLabel(false);
                cancelDialog.setNegativeListener(view1 -> cancelDialog.dismiss());
                cancelDialog.setPositiveListener(view12 -> {
                    getCancelReasons();
                    stopCheckingforstatus();
                    cancelDialog.dismiss();
                });
                cancelDialog.show();
                break;
            case R.id.moreLay:
                moreLayoutVisibility();
                break;
            case R.id.driver_img:
                //TODO: UI for provider profile...
//                ProviderProfileSheet providerProfileSheet = new ProviderProfileSheet();
//                providerProfileSheet.setProviderId(requestDetail.getProviderId());
//                providerProfileSheet.show(activity_add_vehicles.getSupportFragmentManager(), providerProfileSheet.getTag());
                break;
        }
    }

    public void stopLocationService() {
        stopLocationBroadcastReceiver();
        getActivity().stopService(new Intent(getActivity(), LocationService.class));
    }

    public void stopLocationBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                locationReceiver);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
    }
}
