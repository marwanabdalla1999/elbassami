package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Region;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.dto.response.Branches;
import com.albassami.logistics.dto.response.GetPriceDataResponse;
import com.albassami.logistics.ui.Adapter.CustomExpandableListAdapter;
import com.albassami.logistics.ui.activity.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarBranchToFragment extends Fragment implements CustomExpandableListAdapter.OnChildItemClicked{
    ExpandableListView expandableListView;
    TextView tvHeader;
    String service_type, branch_name, branch_id;
    ArrayList<Branches> itemList;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> regionBrnaches;
    CustomExpandableListAdapter  customExpandableListAdapter;
    HashMap<String, List<Branches>> expandableListDetail = new HashMap<String, List<Branches>>();
    private ImageView btnBack;
    GetPriceDataResponse dataResponse;
    PrefUtils prefUtils;
ImageView toolbarimg;
    TextView textView;
    LinearLayout towingtype;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branches_fragment, container, false);
        expandableListView = view.findViewById(R.id.expandableListView);
        tvHeader = view.findViewById(R.id.tvHeader);
        btnBack = view.findViewById(R.id.btnBack);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180); }
        toolbarimg=view.findViewById(R.id.ivIcon);
        textView=view.findViewById(R.id.textView45);
        textView.setText(getString(R.string.to_Branch));
        service_type = getArguments().getString(Const.PassParam.SERVICE_TYPE);
        towingtype=view.findViewById(R.id.towingtypes);
        towingtype.setVisibility(View.GONE);
        String to=getArguments().getString("from");
        if(service_type.equals(getResources().getString(R.string.intercity))) {

                tvHeader.setText(R.string.intercity_destination);
                toolbarimg.setImageResource(R.drawable.img_intercity);
                branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
                branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
                prefUtils = PrefUtils.getInstance(getContext());
                itemList = new ArrayList<>();
                regionBrnaches = new ArrayList<>();
                expandableListDetail = new HashMap<String, List<Branches>>();
                Gson gson = new Gson();
                dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
                if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {

                    if(sharedPreferences.getString("language","").equals("ar")){
                    for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                        if (dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals("الفروع الدولية")) {
                        } else {
                            regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                            expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                        }
                    }}
                    else{
                        for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                            if (dataResponse.getData().get(0).getRegions().get(i).getName().equals("الفروع الدولية")) {
                            } else {
                                regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                                expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                            }
                        }
                    }

                }


            }
           else if(service_type.equals(getResources().getString(R.string.international))) {
                tvHeader.setText(getString(R.string.international_destination));
                toolbarimg.setImageResource(R.drawable.icon_international);
                branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
                branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
                prefUtils = PrefUtils.getInstance(getContext());
                itemList = new ArrayList<>();
                regionBrnaches = new ArrayList<>();
                expandableListDetail = new HashMap<String, List<Branches>>();
                Gson gson = new Gson();
                dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
                if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                    if(sharedPreferences.getString("language","").equals("ar")) {
                        if (to.equals(getString(R.string.From_Saudi_Arabia))) {
                            for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                                if (dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals(getString(R.string.International_branches))) {
                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                                }
                            }
                        } else {
                            for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                                if (dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals(getString(R.string.International_branches))) {
                                } else {
                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                                }
                            }
                        }
                    }
                    else{
                        if (to.equals(getString(R.string.From_Saudi_Arabia))) {
                            for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                                if (dataResponse.getData().get(0).getRegions().get(i).getName().equals(getString(R.string.International_branches))) {
                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                                }
                            }
                        } else {
                            for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {
                                if (dataResponse.getData().get(0).getRegions().get(i).getName().equals(getString(R.string.International_branches))) {
                                } else {
                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                                }
                            }
                        }

                    }
            }
        }
        else if(service_type.equals(getResources().getString(R.string.special_towing))||service_type.equals(getResources().getString(R.string.closed))) {

            tvHeader.setText(R.string.special_towing);
            toolbarimg.setImageResource(R.drawable.img_intercity);
            branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
            branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
            prefUtils = PrefUtils.getInstance(getContext());
            itemList = new ArrayList<>();
            regionBrnaches = new ArrayList<>();
            expandableListDetail = new HashMap<String, List<Branches>>();
            Gson gson = new Gson();
            dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
            if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                if(sharedPreferences.getString("language","").equals("ar")) {
                    for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {

                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(), dataResponse.getData().get(0).getRegions().get(i).getBranches());

                    }
                }
                else {
                    for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {

                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(), dataResponse.getData().get(0).getRegions().get(i).getBranches());
                    }

                }
            }


        }
        else if(service_type.equals(getResources().getString(R.string.full_load))) {

            tvHeader.setText(R.string.full_load);
            toolbarimg.setImageResource(R.drawable.img_intercity);
            branch_name = getArguments().getString(Const.PassParam.BRANCH_NAME);
            branch_id = getArguments().getString(Const.PassParam.BRANCH_ID);
            prefUtils = PrefUtils.getInstance(getContext());
            itemList = new ArrayList<>();
            regionBrnaches = new ArrayList<>();
            expandableListDetail = new HashMap<String, List<Branches>>();
            Gson gson = new Gson();
            dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);
            if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                if(sharedPreferences.getString("language","").equals("ar")) {
                    for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {

                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(), dataResponse.getData().get(0).getRegions().get(i).getBranches());

                    }
                }
                else
                {
                    for (int i = 0; i < dataResponse.getData().get(0).getRegions().size(); i++) {

                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(), dataResponse.getData().get(0).getRegions().get(i).getBranches());

                    }
                }

            }


        }
        customExpandableListAdapter = new CustomExpandableListAdapter(getContext(),regionBrnaches,expandableListDetail,this::onItemClicked);
        expandableListView.setAdapter(customExpandableListAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onItemClicked(String regionName, String name, Integer id) {
        Bundle bundle=new Bundle();
        //.setBackgroundColor(Color.DKGRAY);

        bundle.putString(Const.PassParam.SERVICE_TYPE, service_type);
        bundle.putString(Const.PassParam.BRANCH_NAME, branch_name);
        bundle.putString(Const.PassParam.BRANCH_ID, branch_id);
        bundle.putString(Const.PassParam.BRANCH_NAME_TO, name);
        bundle.putString(Const.PassParam.BRANCH_ID_TO, String.valueOf(id));
        DetailFormFragment carDetailFormFragment = new DetailFormFragment();
        carDetailFormFragment.setArguments(bundle);
        HomeStartFragment.getInstance().addFragment(carDetailFormFragment, true, Const.CAR_FORM_FRAGMENT, true);
    }

}
