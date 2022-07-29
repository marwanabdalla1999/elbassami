package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.CustomText.CustomRegularTextView;
import com.albassami.logistics.R;
import com.albassami.logistics.dto.response.VehiclesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 1/20/2017.
 */

public class MyVehiclesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<VehiclesData> itemsList;
    private OnDeleteClicked onDeleteClicked;
    private OnEditClicked onEditClicked;

    public MyVehiclesAdapter(Context context, ArrayList<VehiclesData> itemsList, OnDeleteClicked onDeleteClicked, OnEditClicked onEditClicked) {
        mContext = context;
        this.onDeleteClicked = onDeleteClicked;
        this.onEditClicked = onEditClicked;
        this.itemsList = itemsList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vehicles, null);
        BranchesViewHolder holder = new BranchesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof BranchesViewHolder) {
            if (itemsList.get(position).getMakerName() != null && !(itemsList.get(position).getMakerName().isEmpty())){
                    ((BranchesViewHolder) viewHolder).tvCarBrand.setText(itemsList.get(position).getMakerName());
            }
            if (itemsList.get(position).getTypeName() != null && !(itemsList.get(position).getTypeName().isEmpty())){
                ((BranchesViewHolder) viewHolder).tvCarType.setText(itemsList.get(position).getModelName());
            }
            if (itemsList.get(position).getOwnerName() != null && !(itemsList.get(position).getOwnerName().isEmpty())){
                ((BranchesViewHolder) viewHolder).tvOwnerName.setText(itemsList.get(position).getOwnerName());
            }
            if (itemsList.get(position).getIdNumber() != null && !(itemsList.get(position).getIdNumber().isEmpty())){
                ((BranchesViewHolder) viewHolder).tvIDNumber.setText(itemsList.get(position).getIdNumber());
            }
            if (itemsList.get(position).getPlateNumber() != null && !(itemsList.get(position).getPlateNumber().isEmpty())){
                ((BranchesViewHolder) viewHolder).tvPlateNumber.setText(itemsList.get(position).getPlateNumber());
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences=mContext.getSharedPreferences("lang", Context.MODE_PRIVATE);
                    if(sharedPreferences.getString("language","").equals("ar")){
                        onEditClicked.onEdit(itemsList.get(position).getCar_id(),itemsList.get(position).getModelName(), itemsList.get(position).getMakerName(),itemsList.get(position).getPlateNumber(),itemsList.get(position).getPlateType(),itemsList.get(position).getPhoneNumber(),itemsList.get(position).getOwnerName(),itemsList.get(position).getIdNumber(),itemsList.get(position).getOwnerIdType(),itemsList.get(position).getOwnerNationality(),itemsList.get(position).getCarColor(),itemsList.get(position).getCarYear(),Integer.toString(itemsList.get(position).getModelId()),Integer.toString(itemsList.get(position).getVehicleTypeId()),Integer.toString(itemsList.get(position).getVehicleMakerId()),itemsList.get(position).getTypeName());

                    }
                    else{
                        onEditClicked.onEdit(itemsList.get(position).getCar_id(),itemsList.get(position).getModelName(), itemsList.get(position).getMakerName(),itemsList.get(position).getPlateNumber(),itemsList.get(position).getPlateType(),itemsList.get(position).getPhoneNumber(),itemsList.get(position).getOwnerName(),itemsList.get(position).getIdNumber(),itemsList.get(position).getOwnerIdType(),itemsList.get(position).getOwnerNationality(),itemsList.get(position).getCarColor(),itemsList.get(position).getCarYear(),Integer.toString(itemsList.get(position).getModelId()),Integer.toString(itemsList.get(position).getVehicleTypeId()),Integer.toString(itemsList.get(position).getVehicleMakerId()),itemsList.get(position).getTypeName());
                    }}
            });
            ((BranchesViewHolder) viewHolder).ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteClicked.onDelete(itemsList.get(position).getPlateNumber(), "");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class BranchesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivDelete)
        ImageView ivDelete;
        @BindView(R.id.ivEdit)
        ImageView ivEdit;
        @BindView(R.id.tvCarBrand)
        CustomRegularTextView tvCarBrand;
        @BindView(R.id.tvCarType)
        CustomRegularTextView tvCarType;
        @BindView(R.id.tvIDNumber)
        CustomRegularTextView tvIDNumber;
        @BindView(R.id.tvOwnerName)
        CustomRegularTextView tvOwnerName;
        @BindView(R.id.tvPlateNumber)
        CustomRegularTextView tvPlateNumber;

        public BranchesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnDeleteClicked {
        void onDelete(String id, String name);
    }

    public interface OnEditClicked {
        void onEdit(Integer id, String brand, String type, String plate, String plateType, String phone, String owner, String idNumber, String owner_id_type, String owner_nationality,String color,String year,String modelid,String typeid,String makerid,String typename);
    }
}


