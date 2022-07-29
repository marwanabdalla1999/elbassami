package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 1/20/2017.
 */

public class CarTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> itemsList;
    private OnItemClicked onItemClicked;

    public CarTabAdapter(Context context, ArrayList<String> itemshistroyList, OnItemClicked onItemClick) {
        mContext = context;
        this.onItemClicked = onItemClick;
        this.itemsList = itemshistroyList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cat_tab, null);
        TabViewHolder holder = new TabViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof TabViewHolder) {
            if (itemsList.get(position).equalsIgnoreCase(mContext.getString(R.string.intercity))){
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.iconintercity1);
                ((TabViewHolder) viewHolder).tvDes.setText(R.string.desc_intercity);
            }
            if (itemsList.get(position).equalsIgnoreCase(mContext.getString(R.string.international))){
                ((TabViewHolder) viewHolder).tvDes.setText(R.string.desc_international);
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.iconinternational1);
            }

            if (itemsList.get(position).equalsIgnoreCase(mContext.getString(R.string.special_towing))){
                ((TabViewHolder) viewHolder).tvDes.setText(R.string.desc_intercity);
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.iconspecial1);
            }
            if (itemsList.get(position).equalsIgnoreCase(mContext.getString(R.string.full_load))){
                ((TabViewHolder) viewHolder).tvDes.setText(R.string.otherservice);
                ((TabViewHolder) viewHolder).ivImage.setBackgroundResource(R.drawable.iconfullload1);
            }

            ((TabViewHolder) viewHolder).tvTitle.setText(itemsList.get(position));
            ((TabViewHolder) viewHolder).layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClicked.onClicked(itemsList.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    public class TabViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvdes)
        TextView tvDes;
        @BindView(R.id.ivIcon)
        ImageView ivImage;
        @BindView(R.id.layoutMain)
        RelativeLayout layoutMain;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClicked {
        void onClicked(String id);
    }
}


