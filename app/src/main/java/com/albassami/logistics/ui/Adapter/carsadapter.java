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

import java.util.ArrayList;
import java.util.zip.Inflater;

public class carsadapter extends RecyclerView.Adapter<carsadapter.holder> {
    ArrayList<String> arrayList;
    Context context;

    public carsadapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.full_loadmodel,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.tv.setText(arrayList.get(position));
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               arrayList.remove(position);
               notifyItemRemoved(position);
               notifyItemChanged(position);
               notifyItemRangeRemoved(position, 0);
               notifyDataSetChanged();

           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;
        public holder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tvcar);
            img=itemView.findViewById(R.id.delete);
        }
    }
}
