package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.RecyclerLongPressClickListener;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.ParserUtils;
import com.albassami.logistics.network.Models.History;
import com.albassami.logistics.ui.Adapter.HistoryAdapter;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class towing_orders extends Fragment implements HistoryAdapter.TripsInterface{

    PrefUtils prefUtils;
    RecyclerView rideLv;
    ImageView emptyIcon;
    LinearLayout emptyLayout;
    private ArrayList<History> historylst = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    FrameLayout content;


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
        View view=inflater.inflate(R.layout.fragment_regular_orders, container, false);
        rideLv=view.findViewById(R.id.ride_lv);
        emptyLayout=view.findViewById(R.id.emptyLayout);
        emptyIcon=view.findViewById(R.id.emptyIcon);
        content=view.findViewById(R.id.content);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            content.setRotation(180); }
        prefUtils = PrefUtils.getInstance(getContext());
        Glide.with(getContext()).load(R.drawable.box).into(emptyIcon);
        getHistoryList(0);
        return view;
    }
    private RecyclerView.OnScrollListener tripScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager llmanager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (llmanager.findLastCompletelyVisibleItemPosition() == (historyAdapter.getItemCount() - 1)) {
                historyAdapter.showLoading();
            }
        }
    };
    private void setUpAdapter() {
        historyAdapter = new HistoryAdapter(getContext(), historylst, this, true,"2");
        rideLv.setLayoutManager(new LinearLayoutManager(getContext()));
        rideLv.setAdapter(historyAdapter);
        rideLv.addOnScrollListener(tripScrollListener);

        historyAdapter.notifyDataSetChanged();
        rideLv.addOnItemTouchListener(new RecyclerLongPressClickListener(getContext(), rideLv, new RecyclerLongPressClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }
    protected void getHistoryList(int skip) {
      /*  if (skip == 0) {
            UiUtils.showLoadingDialog(this);
            historylst.clear();
        }*/
        UiUtils.showLoadingDialog(getContext());

        Call<String> call;
     /*   if (isHistory)
            call = apiInterface.getHistory(prefUtils.getIntValue(PrefKeys.USER_ID, 0)
                    , prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, "")
                    , skip);*/
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                           .header("Accept","application/json")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiInterface=retrofit.create(APIInterface.class);

        call = apiInterface.gettow_orders(prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                prefUtils.getIntValue(PrefKeys.USER_ID, 0));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()&& response.body()!=null){
                    UiUtils.hideLoadingDialog();

                }
                else if(response.errorBody()!=null){
                    Toast.makeText(getContext(), getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
                    UiUtils.hideLoadingDialog();

                }
                UiUtils.hideLoadingDialog();
                if (skip == 0) {
                    historylst.clear();
                }
                JSONArray historyResponse = null;
                try {
                    historyResponse = new JSONArray(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (historyResponse != null) {
                        historylst = ParserUtils.ParseHistoryArrayList(historyResponse, true,getContext(),"2");
                        setUpAdapter();
                        if (!historylst.isEmpty()) {
                            rideLv.removeOnScrollListener(tripScrollListener);

                    }
                }
                emptyLayout.setVisibility(historylst.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.pleasecheckyourinternetconnection));

                }
            }
        });
    }

    @Override
    public void onLoadMoreTrips(int skip) {
        getHistoryList(skip);
    }
}