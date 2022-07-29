package com.albassami.logistics.ui.activity;

import android.app.AlertDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.R;
import com.albassami.logistics.Utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mahesh on 5/24/2017.
 */

public class AskBotActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.web_loader)
    ProgressBar webLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.helpview);
        ButterKnife.bind(this);
        loadWebViewLoad(webView);
    }

    private void loadWebViewLoad(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new SSLTolerentWebViewClient());
        webView.loadUrl("http://prevue.info/web-view/?token=" + new PreferenceHelper(this).getUserId() + "&" + "session_token" + "=" + new PreferenceHelper(this).getSessionToken());
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webLoader.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("My Webview", url);
                view.loadUrl(url);
                return false;
            }
        });
    }

    @OnClick(R.id.help_back)
    public void onViewClicked() {
        onBackPressed();
    }

    private class SSLTolerentWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage(getResources().getString(R.string.txt_load_web));
            builder.setPositiveButton(getResources().getString(R.string.txt_continue), (dialog, which) -> handler.proceed());
            builder.setNegativeButton(getResources().getString(R.string.txt_cancel), (dialog, which) -> {
                handler.cancel();
                onBackPressed();
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.isFocused() && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}