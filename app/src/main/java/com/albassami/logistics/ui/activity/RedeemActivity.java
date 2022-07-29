package com.albassami.logistics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomBoldRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.network.Models.WalletPayments;
import com.albassami.logistics.ui.Adapter.NewWalletAdapter;
import com.albassami.logistics.ui.Fragment.AddMoneyBottomSheet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RedeemActivity extends AppCompatActivity implements NewWalletAdapter.TransactionInterface {

    NewWalletAdapter walletAdapter;
    ArrayList<WalletPayments> paymentsList = new ArrayList<>();
    @BindView(R.id.recentTransactionRecycler)
    RecyclerView recentTransactionRecycler;
    APIInterface apiInterface;
    PrefUtils prefUtils;
    @BindView(R.id.remainingAmount)
    CustomBoldRegularTextView remainingAmount;
    @BindView(R.id.totalAmount)
    CustomBoldRegularTextView totalAmount;
    @BindView(R.id.redeemedAmount)
    CustomBoldRegularTextView redeemedAmount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sendBtn)
    CardView sendBtn;
    @BindView(R.id.viewMore)
    CustomRegularTextView viewMore;
    @BindView(R.id.noData)
    CustomRegularTextView noData;
    @BindView(R.id.emptyData)
    ImageView emptyData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        prefUtils = PrefUtils.getInstance(getApplicationContext());
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        setUpAdapter();
        getRedeemsFromBackend();
        Glide.with(getApplicationContext()).load(R.drawable.box).into(emptyData);
    }

    private void setUpAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        walletAdapter = new NewWalletAdapter(this, paymentsList, true, this);
        recentTransactionRecycler.setLayoutManager(linearLayoutManager);
        recentTransactionRecycler.setAdapter(walletAdapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, getResources().getIdentifier("layout_animation_from_left", "anim", getPackageName()));
        recentTransactionRecycler.setLayoutAnimation(animation);
        recentTransactionRecycler.scheduleLayoutAnimation();
        recentTransactionRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                viewMore.setVisibility(linearLayoutManager.findLastCompletelyVisibleItemPosition() == (walletAdapter.getItemCount() - 1) ? View.VISIBLE : View.GONE);
            }
        });
    }

    protected void getRedeemsFromBackend() {
        UiUtils.showLoadingDialog(RedeemActivity.this);
        Call<String> call = apiInterface.getRedeemsList(prefUtils.getIntValue(PrefKeys.USER_ID, 0),
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                paymentsList.clear();
                JSONObject redeemsResponse = null;
                try {
                    redeemsResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (redeemsResponse != null) {
                    if (redeemsResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        JSONObject data = redeemsResponse.optJSONObject(APIConsts.Params.DATA);
                        JSONObject wallet = data.optJSONObject(APIConsts.Params.WALLET);
                        remainingAmount.setText(wallet.optString(APIConsts.Params.REMINING_FORMATTED));

                        sendBtn.setVisibility(!wallet.optString(APIConsts.Params.REMINING_FORMATTED).equals("$0.00") ? View.VISIBLE :View.INVISIBLE);

                        totalAmount.setText(wallet.optString(APIConsts.Params.TOTAL_FORMATTED));
                        redeemedAmount.setText(wallet.optString(APIConsts.Params.USED_FORMATTED));
                        JSONArray paymentsArray = data.optJSONArray(APIConsts.Params.PAYMENTS);
                        for (int i = 0; i < paymentsArray.length(); i++) {
                            JSONObject paymentsObject = paymentsArray.optJSONObject(i);
                            WalletPayments payments = new WalletPayments();
                            payments.setTitle(paymentsObject.optString(APIConsts.Params.TITLE));
                            payments.setRedeemId(paymentsObject.optString(APIConsts.Params.USER_REDEEM_REQUEST_ID));
                            payments.setDescription(paymentsObject.optString(APIConsts.Params.DESCRIPTION));
                            payments.setUniqueId(paymentsObject.optString(APIConsts.Params.UNIQUE_ID));
                            payments.setWallet_amount_symbol(paymentsObject.optString(APIConsts.Params.AMOUNT_SYMBOL));
                            payments.setWallet_image(paymentsObject.optString(APIConsts.Params.IMAGE));
                            payments.setAmount(paymentsObject.optString(APIConsts.Params.TOTAL_FORMATTED));
                            payments.setCancelButtonStatus(paymentsObject.optInt(APIConsts.Params.CANCEL_BUTTON_STATUS) == 1);
                            paymentsList.add(payments);
                        }

                        walletAdapter.notifyDataSetChanged();
                        emptyData.setVisibility(paymentsList.isEmpty() ? View.VISIBLE : View.GONE);
                        noData.setVisibility(paymentsList.isEmpty() ? View.VISIBLE : View.GONE);
                        recentTransactionRecycler.setVisibility(paymentsList.isEmpty() ? View.GONE : View.VISIBLE);
                        viewMore.setVisibility(paymentsList.isEmpty() ? View.GONE : View.VISIBLE);
                    } else {
                        UiUtils.showShortToast(getApplicationContext(), redeemsResponse.optString(APIConsts.Params.ERROR));
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

    @Override
    public void cancelRequest(String redeemId) {
        cancelRedeemRequest(redeemId);
    }

    @Override
    public void onLoadMoreTransactions(int skip) {
    }

    protected void cancelRedeemRequest(String redeemRequestId) {
        UiUtils.showLoadingDialog(RedeemActivity.this);
        Call<String> call = apiInterface.cancelRedeemRequest(prefUtils.getIntValue(PrefKeys.USER_ID, 0),
                prefUtils.getStringValue(PrefKeys.SESSION_TOKEN, ""),
                redeemRequestId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                JSONObject cancelRequestResponse = null;
                try {
                    cancelRequestResponse = new JSONObject(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cancelRequestResponse != null) {
                    if (cancelRequestResponse.optString(Const.Params.SUCCESS).equals(APIConsts.Constants.TRUE)) {
                        UiUtils.showShortToast(RedeemActivity.this, cancelRequestResponse.optString(APIConsts.Params.MESSAGE));
                        getRedeemsFromBackend();
                    }
                } else {
                    UiUtils.showShortToast(RedeemActivity.this, cancelRequestResponse.optString(APIConsts.Params.ERROR));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (NetworkUtils.isNetworkConnected(RedeemActivity.this)) {
                    UiUtils.showShortToast(RedeemActivity.this, getString(R.string.may_be_your_is_lost));
                }
            }
        });
    }

    @OnClick(R.id.sendBtn)
    public void onViewClicked() {
        AddMoneyBottomSheet addMoneyBottomSheet = new AddMoneyBottomSheet();
        addMoneyBottomSheet.setIsRedeem(true, remainingAmount.getText().toString());
        addMoneyBottomSheet.show(getSupportFragmentManager(), addMoneyBottomSheet.getTag());
    }

    @OnClick(R.id.viewMore)
    public void setViewMore() {
        Intent transactionIntent = new Intent(getApplicationContext(), TransactionListActivity.class);
        transactionIntent.putExtra(APIConsts.Params.IS_REDEEM, true);
        startActivity(transactionIntent);
    }
}
