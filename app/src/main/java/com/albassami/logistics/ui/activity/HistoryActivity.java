package com.albassami.logistics.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.albassami.logistics.network.ApiManager.ApiServices;
import com.albassami.logistics.network.ApiManager.CustomRestClient;
import com.albassami.logistics.network.ApiManager.RestClient;
import com.albassami.logistics.ui.Adapter.pagerAdapter;
import com.albassami.logistics.ui.Adapter.pagerAdapter1;
import com.albassami.logistics.ui.Fragment.regular_orders;
import com.albassami.logistics.ui.Fragment.towing_orders;
import com.bumptech.glide.Glide;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.Utils.RecyclerLongPressClickListener;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.ApiManager.ParserUtils;
import com.albassami.logistics.network.Models.History;
import com.albassami.logistics.ui.Adapter.HistoryAdapter;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys.NATIONAL_ID;
import static com.androidquery.util.AQUtility.getContext;

/**
 * Created by user on 1/20/2017.
 */

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.heading)
    CustomBoldRegularTextView heading;
    private ArrayList<History> historylst = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    PrefUtils prefUtils;
    boolean isHistory;
    ImageView btnBack;
    ViewPager viewPager;
    TabLayout tabLayout;


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
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        btnBack=findViewById(R.id.history_back);
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        //apiInterface = CustomRestClient.getApiService();
        if (getIntent().getExtras() != null) {
            isHistory = getIntent().getBooleanExtra("isHistory", true);
        }
        heading.setText(isHistory ? getString(R.string.ride_history) : getString(R.string.schedule_rides));
        seetupViewpagerwithtabview();

    }

    private void seetupViewpagerwithtabview() {
        tabLayout=findViewById(R.id.ordertype);
        viewPager=findViewById(R.id.orders);
        pagerAdapter1 pagerAdapter=new pagerAdapter1(getSupportFragmentManager());
        pagerAdapter.addfragment(new towing_orders(),"شحن سطحه");
        pagerAdapter.addfragment(new regular_orders(),"شحن عادي");
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            viewPager.setRotation(180); }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @OnClick(R.id.history_back)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }}

