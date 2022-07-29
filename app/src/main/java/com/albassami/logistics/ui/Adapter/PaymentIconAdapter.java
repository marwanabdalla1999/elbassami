package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentIconAdapter extends RecyclerView.Adapter<PaymentIconAdapter.PayIconHolder> {
    private Context mContext;
    private ArrayList<Integer> mList;

    public PaymentIconAdapter(Context context, ArrayList<Integer> integerArrayList) {
        this.mContext = context;
        this.mList = integerArrayList;
    }

    @NonNull
    @Override
    public PayIconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.payment_icon, null);
        PaymentIconAdapter.PayIconHolder holder = new PaymentIconAdapter.PayIconHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayIconHolder holder, int position) {
        holder.iv_icons.setBackgroundResource(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PayIconHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icons;

        public PayIconHolder(View itemView) {
            super(itemView);
            iv_icons = (ImageView) itemView.findViewById(R.id.card_image);
        }
    }
}
