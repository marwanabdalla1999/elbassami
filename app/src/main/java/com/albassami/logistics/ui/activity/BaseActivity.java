package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.albassami.logistics.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }
}
