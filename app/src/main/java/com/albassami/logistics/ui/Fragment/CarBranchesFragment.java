package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Region;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.facebook.FacebookSdk.getApplicationContext;

public class CarBranchesFragment extends Fragment implements CustomExpandableListAdapter.OnChildItemClicked{
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
    ImageView toolbaricon;
     RadioGroup region;
     RadioButton from_saudi,to_saudi;
     LinearLayout c1,c2;
     LinearLayout towingtype;
     TextView textView;
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
        toolbaricon=view.findViewById(R.id.ivIcon);
        region=view.findViewById(R.id.region);
        textView=view.findViewById(R.id.textView45);
        textView.setText(getString(R.string.From_branch));
        from_saudi=view.findViewById(R.id.from_saudi_arabia);
        to_saudi=view.findViewById(R.id.to_saudi_arabia);
        c1=view.findViewById(R.id.regular);
        c2=view.findViewById(R.id.closedtowing);
        towingtype=view.findViewById(R.id.towingtypes);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackgroundResource(R.drawable.rounded_with_green);
                c2.setBackgroundColor(Color.TRANSPARENT);
                service_type=getString(R.string.special_towing);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackgroundColor(Color.TRANSPARENT);
                c2.setBackgroundResource(R.drawable.rounded_with_green);
                service_type=getString(R.string.closed);
            }
        });
        from_saudi.setChecked(true);

        service_type = getArguments().getString(Const.PassParam.SERVICE_TYPE);
        prefUtils = PrefUtils.getInstance(getContext());
        itemList = new ArrayList<>();
        regionBrnaches = new ArrayList<>();
        expandableListDetail = new HashMap<String, List<Branches>>();
        Gson gson = new Gson();
        dataResponse = gson.fromJson(prefUtils.getStringValue(PrefKeys.JSON_OBJ, ""), GetPriceDataResponse.class);

        if(service_type.equals(getString(R.string.special_towing))||service_type.equals(getString(R.string.full_load))){
            if(sharedPreferences.getString("language","").equals("ar")){
        if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
            for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                }

        }}
        else{

                if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                    for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                    }

                }
            }
        }

        else{
        if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
            if(sharedPreferences.getString("language","").equals("ar")){
            for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                if(dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals(getString(R.string.International_branches))){}
                else{
                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                }}}
            else{
                for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                    if(dataResponse.getData().get(0).getRegions().get(i).getName().equals(getString(R.string.International_branches))){}
                    else{
                        regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                        expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                    }}
            }

        }}
        if(service_type.equals(getString(R.string.intercity))){
                tvHeader.setText(getString(R.string.intercity_source));
                toolbaricon.setImageResource(R.drawable.local);
                region.setVisibility(View.GONE);


            }
            else if(service_type.equals(getString(R.string.international))){
                tvHeader.setText(getString(R.string.international_source));
                toolbaricon.setImageResource(R.drawable.international);
                prefUtils = PrefUtils.getInstance(getContext());
                region.setVisibility(View.GONE);

        }
       else if(service_type.equals(getString(R.string.special_towing))){
            tvHeader.setText(getString(R.string.special_towing));
            toolbaricon.setImageResource(R.drawable.spcialtowing);
            region.setVisibility(View.GONE); }
        else if(service_type.equals(getString(R.string.full_load))){
            tvHeader.setText(getString(R.string.full_load));
            toolbaricon.setImageResource(R.drawable.fullshipping);
            region.setVisibility(View.GONE);}
            from_saudi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                        regionBrnaches.clear();
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
                        if(sharedPreferences.getString("language","").equals("ar")){
                        for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                            if(dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals(getString(R.string.International_branches))){}
                            else{
                                regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                                expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                            }}}
                        else{
                            for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                                if(dataResponse.getData().get(0).getRegions().get(i).getName().equals(getString(R.string.International_branches))){}
                                else{
                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                                }}
                        }
                        customExpandableListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        to_saudi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
                        regionBrnaches.clear();
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
                        if(sharedPreferences.getString("language","").equals("ar")){
                        for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                            if(dataResponse.getData().get(0).getRegions().get(i).getName_ar().equals(getString(R.string.International_branches))){

                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName_ar());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName_ar(),dataResponse.getData().get(0).getRegions().get(i).getBranches());

                            }
                        }}
                        else{
                            for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                                if(dataResponse.getData().get(0).getRegions().get(i).getName().equals(getString(R.string.International_branches))){

                                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(),dataResponse.getData().get(0).getRegions().get(i).getBranches());

                                }
                            }
                        }
                        customExpandableListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        customExpandableListAdapter = new CustomExpandableListAdapter(getContext(),regionBrnaches,expandableListDetail,this::onItemClicked);
        expandableListView.setAdapter(customExpandableListAdapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        if(service_type.equals(getString(R.string.special_towing))){towingtype.setVisibility(View.VISIBLE);
            c1.setBackgroundResource(R.drawable.rounded_with_green);
        }
        else{towingtype.setVisibility(View.GONE);}
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onItemClicked(String regionName, String name, Integer id) {


          Bundle bundle = new Bundle();

          bundle.putString(Const.PassParam.SERVICE_TYPE, service_type);
          bundle.putString(Const.PassParam.BRANCH_NAME, name);
          bundle.putString(Const.PassParam.BRANCH_ID, String.valueOf(id));

          if (to_saudi.isChecked()) {
              bundle.putString("from", to_saudi.getText().toString());
          } else {
              bundle.putString("from", from_saudi.getText().toString());
          }
          CarBranchToFragment carBranchToFragment = new CarBranchToFragment();
          carBranchToFragment.setArguments(bundle);
          HomeStartFragment.getInstance().addFragment(carBranchToFragment, true, Const.CAR_FORM_FRAGMENT, true);
      }

   /* public void branches(GetPriceDataResponse dataResponse){
        if (dataResponse != null && dataResponse.getData().get(0).getRegions() != null) {
            if()
            for (int i = 0; i <dataResponse.getData().get(0).getRegions().size() ; i++) {
                if(dataResponse.getData().get(0).getRegions().get(i).getName().equals("الفروع الدولية")){
                    regionBrnaches.add(dataResponse.getData().get(0).getRegions().get(i).getName());
                    expandableListDetail.put(dataResponse.getData().get(0).getRegions().get(i).getName(),dataResponse.getData().get(0).getRegions().get(i).getBranches());
                }
            }}*/


    }

