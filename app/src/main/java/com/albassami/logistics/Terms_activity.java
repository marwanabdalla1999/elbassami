package com.albassami.logistics.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.albassami.logistics.R;
public class Terms_activity extends AppCompatActivity {
    ImageView btnBack;
    WebView webview;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_activity);
        btnBack=findViewById(R.id.imageView11);
       SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
       if(sharedPreferences.getString("language","").equals("ar")){
           btnBack.setRotation(180);}
       btnBack.setOnClickListener(i->onBackPressed());
        init();
        listener();
    }

    private void init() {

        webview = (WebView) findViewById(R.id.terms_view);
        webview.getSettings().setJavaScriptEnabled(true);

        pDialog = new ProgressDialog(Terms_activity.this);
        pDialog.setTitle("");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=93.112.44.39/docs/agreement_ar.pdf");

    }

    private void listener() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
    }
}