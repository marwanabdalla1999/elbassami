package com.albassami.logistics.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.network.ApiManager.APIClient;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.network.ApiManager.NetworkUtils;
import com.albassami.logistics.ui.Adapter.PaymentIconAdapter;
import com.albassami.logistics.ui.activity.ActivityLoginSigupOption;
import com.albassami.logistics.ui.activity.PaymentMethodActivity;
import com.albassami.logistics.ui.activity.WalletAcivity;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentOrderSummary extends Fragment {
    ArrayList<Integer> iconList;
    RecyclerView rvIcons;
    PaymentIconAdapter paymentIconAdapter;
    TextView cancelBtn;
    TextView confirmBtn;
    PrefUtils prefUtils;
    APIInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);
        inIT(view);
        return view;
    }

    private void inIT(View view) {
        prefUtils = PrefUtils.getInstance(getContext());
        rvIcons = view.findViewById(R.id.rvIcons);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        iconList = new ArrayList<>();
        iconList.add(R.drawable.viza_card);
        iconList.add(R.drawable.master_card);
        iconList.add(R.drawable.apple_pay);
        rvIcons.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        paymentIconAdapter = new PaymentIconAdapter(getContext(), iconList);
        rvIcons.setAdapter(paymentIconAdapter);
        confirmBtn = view.findViewById(R.id.btnConfirm);
        cancelBtn = view.findViewById(R.id.btnCancel);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefUtils.getBooleanValue(PrefKeys.IS_LOGGED_IN, false)) {
                    startActivity(new Intent(getActivity(), PaymentMethodActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), ActivityLoginSigupOption.class));
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

}
