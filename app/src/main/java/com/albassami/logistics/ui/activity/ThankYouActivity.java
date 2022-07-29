package com.albassami.logistics.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.albassami.logistics.R;

import java.util.Locale;

public class ThankYouActivity extends AppCompatActivity {
    Intent intent;
    TextView tvOrderNumber,txtexcellent,txtfast,txteasy,txtreliable;
    String order_id;
    Button btnNext;
    LinearLayout excellent,fast,easy,reliable;
    boolean doubleBackToExitPressedOnce = false;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.activity_thank_you);
        intent = getIntent();
       order_id = intent.getExtras().getString("order_id");
        tvOrderNumber = findViewById(R.id.tvOrder);
        tvOrderNumber.setText(getString(R.string.yourorder) + order_id );
        txtexcellent=findViewById(R.id.txtexcellent);
        txtfast=findViewById(R.id.txtfast);
        txteasy=findViewById(R.id.txteasy);
        txtreliable=findViewById(R.id.txtreliable);
        btnNext =  findViewById(R.id.btnNext);
        excellent=findViewById(R.id.excellent);
        fast=findViewById(R.id.fast);
        easy=findViewById(R.id.easy);
        reliable=findViewById(R.id.reliable);
        excellent.setOnClickListener(i->{
            excellent.setBackground(getDrawable(R.drawable.rouded_green));
            fast.setBackgroundColor(Color.TRANSPARENT);
            easy.setBackgroundColor(Color.TRANSPARENT);
            reliable.setBackgroundColor(Color.TRANSPARENT);
            txtexcellent.setTextColor(Color.WHITE);
            txtreliable.setTextColor(Color.BLACK);
            txteasy.setTextColor(Color.BLACK);
            txtfast.setTextColor(Color.BLACK);


        });
        fast.setOnClickListener(i->{
            fast.setBackground(getDrawable(R.drawable.rouded_green));
            excellent.setBackgroundColor(Color.TRANSPARENT);
            easy.setBackgroundColor(Color.TRANSPARENT);
            reliable.setBackgroundColor(Color.TRANSPARENT);
            txtexcellent.setTextColor(Color.BLACK);
            txtreliable.setTextColor(Color.BLACK);
            txteasy.setTextColor(Color.BLACK);
            txtfast.setTextColor(Color.WHITE);

        });
        easy.setOnClickListener(i->{
            easy.setBackground(getDrawable(R.drawable.rouded_green));
            excellent.setBackgroundColor(Color.TRANSPARENT);
            fast.setBackgroundColor(Color.TRANSPARENT);
            reliable.setBackgroundColor(Color.TRANSPARENT);
            txtexcellent.setTextColor(Color.BLACK);
            txtreliable.setTextColor(Color.BLACK);
            txteasy.setTextColor(Color.WHITE);
            txtfast.setTextColor(Color.BLACK);
        });
        reliable.setOnClickListener(i->{
            reliable.setBackground(getDrawable(R.drawable.rouded_green));
            excellent.setBackgroundColor(Color.TRANSPARENT);
            easy.setBackgroundColor(Color.TRANSPARENT);
            fast.setBackgroundColor(Color.TRANSPARENT);
            txtexcellent.setTextColor(Color.BLACK);
            txtreliable.setTextColor(Color.WHITE);
            txteasy.setTextColor(Color.BLACK);
            txtfast.setTextColor(Color.BLACK);

        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAffinity(ThankYouActivity.this);
                Intent toHome = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toHome);
            }
        });
        excellent.setBackground(getDrawable(R.drawable.rouded_green));
        txtexcellent.setTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            startActivity(new Intent(ThankYouActivity.this,MainActivity.class));
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
