//package com.albassami.logistics.ui.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.albassami.logistics.R;
//import com.albassami.logistics.dto.response.Branches;
//
//import java.util.ArrayList;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by user on 1/20/2017.
// */
//
//public class BranchesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private Context mContext;
//    private ArrayList<Branches> itemsList;
//    private OnItemClicked onItemClicked;
//
//    public BranchesAdapter(Context context, ArrayList<Branches> branchesArrayList, OnItemClicked onItemClick) {
//        mContext = context;
//        this.onItemClicked = onItemClick;
//        this.itemsList = branchesArrayList;
//
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cat_tab, null);
//        BranchesViewHolder holder = new BranchesViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//        if (viewHolder instanceof BranchesViewHolder) {
//            ((BranchesViewHolder) viewHolder).tvTitle.setText(itemsList.get(position).getRouteWaypointName());
//            ((BranchesViewHolder) viewHolder).layoutMain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClicked.onClicked(itemsList.get(position).getId(),itemsList.get(position).getRouteWaypointName());
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemsList.size();
//    }
//
//
//    public class BranchesViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tvTitle)
//        TextView tvTitle;
//        @BindView(R.id.layoutMain)
//        RelativeLayout layoutMain;
//
//        public BranchesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    public interface OnItemClicked {
//        void onClicked(Integer id,String name);
//    }
//}
//
//
