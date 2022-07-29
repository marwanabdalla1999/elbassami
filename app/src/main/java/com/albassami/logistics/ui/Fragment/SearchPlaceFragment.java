package com.albassami.logistics.ui.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.albassami.logistics.listener.OnCarClicked;
import com.albassami.logistics.listener.OnDoorToDoorClicked;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Location.LocationHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 1/7/2017.
 */

public class SearchPlaceFragment extends BaseMapFragment implements View.OnClickListener, OnCarClicked, OnDoorToDoorClicked, LocationHelper.OnLocationReceived, OnMapReadyCallback {
    private static SearchPlaceFragment instance = null;
    public static AutoCompleteTextView etSourceAddress;
    public static AutoCompleteTextView etStopAddress;
    public static AutoCompleteTextView etDestinationAddress;
    public static Button btnSearch;
    @BindView(R.id.addStop)
    ImageView addStop;
    @BindView(R.id.stopLayout)
    LinearLayout stopLayout;
    //    @BindView(R.id.sourceFavIcon)
//    ImageView sourceFavIcon;
//    @BindView(R.id.stopFavIcon)
//    ImageView stopFavIcon;
//    @BindView(R.id.destFavIcon)
//    ImageView destFavIcon;
    FrameLayout fragmentLayout;
    private GoogleMap gMap;
    private LocationHelper locHelper;
    private LatLng currentLatLan;
    private Bundle mBundle;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private boolean s_click = false, d_click = false, stop_click = false;
    String sourceAddress, destAddress, stopAddress;
    public static LatLng des_latLng, sourceLatLng, stop_latLng;
    SupportMapFragment search_place_map;
    private GoogleMap googleMap;
    private Marker PickUpMarker, DropMarker, StopMarker;
    Unbinder unbinder;
    APIInterface apiInterface;
    PrefUtils prefUtils;

    public static SearchPlaceFragment getInstance() {
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locHelper = new LocationHelper(activity);
        locHelper.setLocationReceivedLister(this);
        mBundle = savedInstanceState;
        instance = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(activity);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
        }
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.source_destination_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity.bottomNavigationView.setVisibility(View.VISIBLE);
        etSourceAddress = view.findViewById(R.id.et_source_address);
        etDestinationAddress = view.findViewById(R.id.et_destination_address);
        etStopAddress = view.findViewById(R.id.et_stop_address);
        btnSearch = view.findViewById(R.id.btn_search);
        fragmentLayout = view.findViewById(R.id.content_fragment);
        search_place_map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.search_place_map);
        if (null != search_place_map) {
            search_place_map.getMapAsync(this);
        }
        //   addStop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.add));
        etDestinationAddress.requestFocus();
        btnSearch.setEnabled(false);
        btnSearch.setBackgroundColor(getResources().getColor(R.color.light_grey));
        etSourceAddress.setText(sourceAddress);
        setOnTouchListener();
        setSourecAndDestination("");
        setOnLongClickLis();
        return view;
    }

    private void setOnLongClickLis() {
        etSourceAddress.setOnLongClickListener(view -> {
            etSourceAddress.setText("");
            etSourceAddress.requestFocus();
            return false;
        });
        etDestinationAddress.setOnLongClickListener(view1 -> {
            etDestinationAddress.setText("");
            etDestinationAddress.requestFocus();
            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListener() {
        etSourceAddress.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                d_click = false;
                s_click = true;
                stop_click = false;
                if (!Places.isInitialized()) {
                    Places.initialize(activity.getApplicationContext(), Const.PLACES_AUTOCOMPLETE_API_KEY);
                }
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(activity);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                SearchLocationDialog searchLocationDialog = new SearchLocationDialog(s_click, d_click, stop_click);
//                searchLocationDialog.show(getActivity().getSupportFragmentManager(), searchLocationDialog.getTag());
            }
            return true; // return is important...
        });
        etStopAddress.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                d_click = false;
                s_click = false;
                stop_click = true;
                if (!Places.isInitialized()) {
                    Places.initialize(activity.getApplicationContext(), Const.PLACES_AUTOCOMPLETE_API_KEY);
                }
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(activity);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                SearchLocationDialog searchLocationDialog = new SearchLocationDialog(s_click, d_click, stop_click);
//                searchLocationDialog.show(getActivity().getSupportFragmentManager(), searchLocationDialog.getTag());
            }
            return true;
        });
        etDestinationAddress.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                d_click = true;
                s_click = false;
                stop_click = false;
                if (!Places.isInitialized())
                    Places.initialize(activity.getApplicationContext(), Const.PLACES_AUTOCOMPLETE_API_KEY);
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(activity);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
//                SearchLocationDialog searchLocationDialog = new SearchLocationDialog(s_click, d_click, stop_click);
//                searchLocationDialog.show(getActivity().getSupportFragmentManager(), searchLocationDialog.getTag());
            }
            return true;
        });
    }

    public void setSourecAndDestination(String pickUpAddress) {
        sourceAddress = pickUpAddress;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {
                    final Place place = Autocomplete.getPlaceFromIntent(data);
                    activity.runOnUiThread(() -> {
                        if (s_click == true) {
                            getAddressSuggestions(place.getLatLng());
                            sourceAddress = String.valueOf(place.getAddress());
                            sourceLatLng = place.getLatLng();
                            Const.source_address = sourceAddress;
                            Const.pic_latlan = sourceLatLng;
                        } else if (d_click == true) {
                            getAddressSuggestions(place.getLatLng());
                            destAddress = String.valueOf(place.getAddress());
                            des_latLng = place.getLatLng();
                            Const.dest_address = destAddress;
                            Const.drop_latlan = des_latLng;
                        } else if (stop_click == true) {
                            getAddressSuggestions(place.getLatLng());
                            stopAddress = String.valueOf(place.getAddress());
                            stop_latLng = place.getLatLng();
                            Const.stop_address = stopAddress;
                            Const.stop_latlan = stop_latLng;
                        }
                    });
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.i("asher", status.getStatusMessage());
                } else if (resultCode == RESULT_CANCELED) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void getAddressSuggestions(LatLng latLng) {
        String url = Const.GEO_DEST + latLng.latitude + "," + latLng.longitude + "&key=" + APIConsts.Constants.GOOGLE_API_KEY;
        Call<String> call = apiInterface.getLocationBasedResponse(url);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                JSONObject fareResponse = null;
                try {
                    fareResponse = new JSONObject(response.body());
                    JSONArray jarray = fareResponse.optJSONArray("results");
                    final JSONObject locObj = jarray.optJSONObject(0);
                    activity.runOnUiThread(() -> {
                        if (s_click == true) {
                            etSourceAddress.setText(sourceAddress);
                            BaseMapFragment.pic_latlan = sourceLatLng;
                            Const.pic_latlan = sourceLatLng;
                            if (null != PickUpMarker) {
                                PickUpMarker.setPosition(sourceLatLng);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sourceLatLng, 16));
                            }
                        } else if (d_click == true) {
                            BaseMapFragment.d_address = destAddress;
                            Const.dest_address = destAddress;
                            etDestinationAddress.setText("");
                            etDestinationAddress.append(destAddress);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(des_latLng,
                                    16));
                            if (null != getActivity() && isAdded()) {
                                btnSearch.setEnabled(true);
                                btnSearch.setBackgroundColor(getResources().getColor(R.color.black));
                            }
                            if (DropMarker == null) {
                                MarkerOptions markerOpt = new MarkerOptions();
                                markerOpt.position(des_latLng);
                                markerOpt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drop_location));
                                DropMarker = googleMap.addMarker(markerOpt);
                            } else {
                                DropMarker.setPosition(des_latLng);
                            }
                        } else if (stop_click == true) {
                            BaseMapFragment.stop_address = stopAddress;
                            Const.stop_address = stopAddress;
                            etStopAddress.setText("");
                            etStopAddress.append(stopAddress);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stop_latLng,
                                    16));
                            if (StopMarker == null) {
                                MarkerOptions markerOpt = new MarkerOptions();
                                markerOpt.position(stop_latLng);
                                markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_stop));
                                StopMarker = googleMap.addMarker(markerOpt);
                            } else {
                                StopMarker.setPosition(stop_latLng);
                            }
                        }
                    });
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


    @Override
    public void onResume() {
        super.onResume();
        activity.currentFragment = Const.SEARCH_FRAGMENT;
    }

    @Override
    public void onLocationReceived(LatLng latlong) {
    }

    @Override
    public void onLocationReceived(Location location) {
    }

    @Override
    public void onConntected(Bundle bundle) {
    }

    @Override
    public void onConntected(Location location) {
        if (location != null && null != googleMap) {
            final LatLng currentlatLang = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatLang,
                    16));
            BaseMapFragment.pic_latlan = currentlatLang;
            Const.pic_latlan = currentlatLang;
            etSourceAddress.setText(getAddressFromLatLng(Const.pic_latlan.latitude,Const.pic_latlan.longitude));
            Const.source_address = getAddressFromLatLng(Const.pic_latlan.latitude,Const.pic_latlan.longitude);
            MarkerOptions markerOpt = new MarkerOptions();
            markerOpt.position(currentlatLang);
            markerOpt.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_green_marker));
            Marker locMark = googleMap.addMarker(markerOpt);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gMap = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup mContainer = getActivity().findViewById(R.id.content_frame);
        mContainer.removeAllViews();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        try {
            googleMap = gMap;
            AndyUtils.removeProgressDialog();
            if (googleMap != null) {
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(BaseMapFragment.pic_latlan)
                        .zoom(16).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                if (null != googleMap) {
                    MarkerOptions markerOpt = new MarkerOptions();
                    markerOpt.position(BaseMapFragment.pic_latlan);
                    markerOpt.icon(BitmapDescriptorFactory.fromResource(R.mipmap.pickup_location));
                    PickUpMarker = googleMap.addMarker(markerOpt);

                }
                googleMap.setOnCameraIdleListener(() -> {
                    if (d_click) {
//                    des_latLng = googleMap.getCameraPosition().target;
//                    if (null != DropMarker) {
//                        SmoothMoveMarker.animateMarker(DropMarker, googleMap.getCameraPosition().target, false, googleMap);
//                    }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.search_back, R.id.addStop, R.id.btn_pickLoc, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                activity.addFragment(new HomeFragment(), false, Const.HOME_MAP_FRAGMENT, true);
                break;
            case R.id.addStop:
                if (stopLayout.getVisibility() == View.GONE) {
                    stopLayout.setVisibility(View.VISIBLE);
                    addStop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.remove));
                } else {
                    stopLayout.setVisibility(View.GONE);
                    stop_latLng = null;
                    addStop.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.add));
                    if (etStopAddress != null && !etStopAddress.getText().toString().isEmpty()) {
                        etStopAddress.setText("");
                        etStopAddress.append("Add Stop");
                    }
                }
                break;
            case R.id.btn_pickLoc:
                if (null != googleMap && currentLatLan != null)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLan, 15));
                break;
            case R.id.btn_search:
                if (des_latLng != null) {
                    BaseMapFragment.drop_latlan = des_latLng;
                    Const.drop_latlan = des_latLng;
                    if (stop_latLng != null) {
                        Log.e("asher", "stop search map " + stop_latLng);
                        BaseMapFragment.stop_latlan = stop_latLng;
                        Const.stop_latlan = stop_latLng;
                    }
                    BaseMapFragment.searching = true;
                    AndyUtils.hideKeyBoard(activity);
                    BaseMapFragment.s_address = etSourceAddress.getText().toString();
                    Const.source_address = etSourceAddress.getText().toString();
                    activity.addFragment(new RequestMapFragment(), false, Const.REQUEST_FRAGMENT, true);
                }
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                break;
//            case R.id.sourceFavIcon:
//                CreateFavoritePlaceDialog createFavoritePlaceDialog = new CreateFavoritePlaceDialog();
//                createFavoritePlaceDialog.show(getActivity().getSupportFragmentManager(), createFavoritePlaceDialog.getTag());
//                break;
//            case R.id.stopFavIcon:
//                CreateFavoritePlaceDialog createFavoriteStop = new CreateFavoritePlaceDialog();
//                createFavoriteStop.show(getActivity().getSupportFragmentManager(), createFavoriteStop.getTag());
//                break;
//            case R.id.destFavIcon:
//                CreateFavoritePlaceDialog createFavoriteDest = new CreateFavoritePlaceDialog();
//                createFavoriteDest.show(getActivity().getSupportFragmentManager(), createFavoriteDest.getTag());
//                break;
        }
    }

    @Override
    public void onClickCar() {
        fragmentLayout.setVisibility(View.VISIBLE);
        Fragment fragment = new CarTabFragment();
        addFragment(fragment, true, Const.CAR_FRAGMENT, true);
    }

    @Override
    public void onDoorClick() {
        fragmentLayout.setVisibility(View.VISIBLE);
        addFragment(new DoorToDoorFragment(), true, Const.DOOR_FRAGMENT, true);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag, boolean isAnimate) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (isAnimate) {
            ft.setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left,
                    R.anim.slide_out_right);
        }
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.content_fragment, fragment, tag);
        ft.commit();
    }
     private String getAddressFromLatLng(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append("\n");
                result.append(address.getCountryName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}