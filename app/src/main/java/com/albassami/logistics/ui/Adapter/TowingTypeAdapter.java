package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.R;
import com.albassami.logistics.dto.response.TowingDataResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 1/20/2017.
 */

public class TowingTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int lastPosition = -1;
    private ArrayList<TowingDataResponse> itemsList;
    private OnItemClicked onItemClicked;
    LinearLayout lastView;
    String towingType;

    public TowingTypeAdapter(Context context, ArrayList<TowingDataResponse> itemshistroyList, OnItemClicked onItemClick, String towingType) {
        mContext = context;
        this.onItemClicked = onItemClick;
        this.towingType = towingType;
        this.itemsList = itemshistroyList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_towing, null);
        TabViewHolder holder = new TabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof TabViewHolder) {
            ((TabViewHolder) viewHolder).tvPrice.setText(itemsList.get(position).getPrice().toString() + "SR");
            ((TabViewHolder) viewHolder).tvTitle.setText(itemsList.get(position).getTowingType());
            if (itemsList.get(position).getTowingType().equalsIgnoreCase("Regular Towing")) {
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.icon_towing_regular);
            } else if (itemsList.get(position).getTowingType().equalsIgnoreCase("Special Towing")) {
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.icon_towing_closed);
            } else if (itemsList.get(position).getTowingType().equalsIgnoreCase("Hydrolic Towing")) {
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.icon_towing_hydraulic);
            }
            if (towingType!=null && (!towingType.equalsIgnoreCase(""))) {
                if (towingType.equalsIgnoreCase(itemsList.get(position).getTowingType())) {
                    ((TabViewHolder) viewHolder).mainLayout.setBackgroundResource(R.drawable.rounded_with_green);
                }
            }
            ((TabViewHolder) viewHolder).mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastView != null)
                        deselect(lastView);
                    select(((TabViewHolder) viewHolder).mainLayout);
                    lastView = ((TabViewHolder) viewHolder).mainLayout;
                    onItemClicked.onClicked(itemsList.get(position).getId().toString(), itemsList.get(position).getTowingType(),itemsList.get(position).getPrice().toString());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class TabViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvHomePick)
        TextView tvTitle;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.iv_icon)
        ImageView ivImage;
        @BindView(R.id.mainLayout)
        LinearLayout mainLayout;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClicked {
        void onClicked(String id, String name,String price);
    }

    private void select(LinearLayout v) {
        v.setBackgroundResource(R.drawable.rounded_with_green);
    }

    private void deselect(LinearLayout v) {
        v.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }
}
