package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.R;

import java.util.ArrayList;

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.HolderAdapter>{
    Context context;
    ArrayList<Bitmap> bitmap;

    public recycleradapter(Context context, ArrayList<Bitmap> bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }

    @NonNull
    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_model,parent,false);
        return new HolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, int position) {
        holder.imageView.setImageBitmap(bitmap.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmap.size();
    }
    public class HolderAdapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        public HolderAdapter(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageads);
        }
    }
}
