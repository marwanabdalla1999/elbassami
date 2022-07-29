package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.R;
import com.albassami.logistics.ui.activity.select_from_to;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class towing_serviceadpt extends RecyclerView.Adapter<towing_serviceadpt.holder> {
    ArrayList<towing_servicemodel> arrayList;
    Context context;

    public towing_serviceadpt(ArrayList<towing_servicemodel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.towing_service,parent,false);

        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
holder.textView.setText(arrayList.get(position).name);
        Glide.with(context).asBitmap().load(arrayList.get(position).Img).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            select_from_to select_from_to= new select_from_to();
            select_from_to.getpriceofservice(arrayList.get(position).price,context);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public holder(@NonNull View itemView) {
            super(itemView);
textView=itemView.findViewById(R.id.textView54);
imageView=itemView.findViewById(R.id.imageView10);

        }
    }
}
