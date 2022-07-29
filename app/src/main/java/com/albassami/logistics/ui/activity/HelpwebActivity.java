package com.albassami.logistics.ui.activity;

import android.app.AlertDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mahesh on 3/1/2017.
 */

public class HelpwebActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.web_loader)
    ProgressBar webLoader;
    @BindView(R.id.helpContent)
    TextView helpContent;
    @BindView(R.id.nodata)
    TextView nodata;
    APIInterface apiInterface;
    PrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.helpview);
        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
//        getStaticData();
        loadWebViewLoad(webView);
    }

    private void loadWebViewLoad(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new SSLTolerentWebViewClient());
        webView.loadUrl(Const.ServiceType.WEBVIEW);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                webLoader.setVisibility(View.GONE);
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

    private void getStaticData() {
        UiUtils.showLoadingDialog(HelpwebActivity.this);
        Call<String> call = apiInterface.getStaticPages("help");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject staticPagesResponse = null;
                try {
                    staticPagesResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (staticPagesResponse != null) {
                    if (staticPagesResponse.optString(APIConsts.Constants.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        try {
                            JSONObject data = staticPagesResponse.optJSONObject(APIConsts.Params.DATA);
                            helpContent.setText(Html.fromHtml(data.optString(APIConsts.Params.DESCRIPTION)));
                            nodata.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            nodata.setVisibility(View.VISIBLE);
                        }
                    } else {
                        UiUtils.hideLoadingDialog();
                        UiUtils.showShortToast(HelpwebActivity.this, staticPagesResponse.optString(APIConsts.Params.ERROR));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
                    UiUtils.showShortToast(getApplicationContext(), getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

}
