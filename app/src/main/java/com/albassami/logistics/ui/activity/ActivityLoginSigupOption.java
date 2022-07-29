package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.R;

import butterknife.BindView;

public class ActivityLoginSigupOption extends AppCompatActivity {
    TextView registerBtn;
    TextView loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_option);
        loginBtn = findViewById(R.id.btnLogin);
        registerBtn = findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLoginSigupOption.this, SignUpNextActivity.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLoginSigupOption.this, SignInActivity.class));
                finish();
            }
        });

    }
}
