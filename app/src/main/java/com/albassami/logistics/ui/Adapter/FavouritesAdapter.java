package com.albassami.logistics.ui.Adapter;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.albassami.logistics.network.ApiManager.AsyncTaskCompleteListener;
import com.albassami.logistics.network.ApiManager.VollyRequester;
import com.albassami.logistics.network.Models.Favourites;
import com.albassami.logistics.R;
import com.albassami.logistics.Utils.AndyUtils;
import com.albassami.logistics.Utils.Commonutils;
import com.albassami.logistics.Utils.Const;
import com.albassami.logistics.Utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by asher on 2/7/2018.
 */

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.TypesViewHolder> implements AsyncTaskCompleteListener {

    private Activity mContext;
    private List<Favourites> itemsFavList;

    public FavouritesAdapter(Activity context, List<Favourites> itemsFavList) {
        mContext = context;
        this.itemsFavList = itemsFavList;
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        switch (serviceCode) {
            case Const.ServiceCode.CANCEL_FAV:
                Log.e("mahi", "cancel later" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("true")) {
                        Commonutils.progressdialog_hide();
                        getSavedPlaces();
                        Commonutils.showtoast(mContext.getResources().getString(R.string.txt_cancel_fav), mContext);
                    } else {
                        Commonutils.progressdialog_hide();
                        String error = jsonObject.getString("error");
                        Commonutils.showtoast(error, mContext);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case Const.ServiceCode.GET_SAVED_PLACES:
                AndyUtils.removeProgressDialog();
                Log.d("mahi", "res saved places" + response);
                if (response != null) {
                    try {
                        JSONObject favobj = new JSONObject(response);
                        if (favobj.getString("success").equals("true")) {
                            //  fav_progress_bar.setVisibility(View.GONE);
                            itemsFavList.clear();
                            JSONArray hisArray = favobj.getJSONArray("data");
                            if (hisArray.length() > 0) {
                                for (int i = 0; i < hisArray.length(); i++) {
                                    JSONObject obj = hisArray.getJSONObject(i);
                                    Favourites fav = new Favourites();
                                    fav.setFav_Id(obj.getString("id"));
                                    fav.setFav_Address(obj.getString("address"));
                                    fav.setFav_Latitude(obj.getString("latitude"));
                                    fav.setFav_Longitude(obj.getString("longitude"));
                                    fav.setFav_Name(obj.getString("favourite_name"));
                                    itemsFavList.add(fav);
                                }

                                if (itemsFavList != null) {
                                    notifyDataSetChanged();
                                }
                            }
                        } else {
                            itemsFavList.clear();
                            notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        itemsFavList.clear();
                        notifyDataSetChanged();
                    }
                }
                break;


        }

    }

    private void getSavedPlaces() {
        if (!AndyUtils.isNetworkAvailable(mContext)) {
            return;
        }
        //  AndyUtils.showSimpleProgressDialog(mContext, "", false);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Const.Params.URL, Const.ServiceType.GET_SAVED_PLACES);
        map.put(Const.Params.ID, new PreferenceHelper(mContext).getUserId());
        map.put(Const.Params.TOKEN, new PreferenceHelper(mContext).getSessionToken());

        Log.d("mahi", map.toString());
        new VollyRequester(mContext, Const.POST, map, Const.ServiceCode.GET_SAVED_PLACES, this);
    }


    public class TypesViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fav_name, tv_fav_address;
        private ImageView cancel_fav;

        public TypesViewHolder(View itemView) {
            super(itemView);
            tv_fav_address = (TextView) itemView.findViewById(R.id.tv_fav_address);
            tv_fav_name = (TextView) itemView.findViewById(R.id.tv_fav_name);
            cancel_fav = (ImageView) itemView.findViewById(R.id.cancel_fav);
        }
    }


    @Override
    public TypesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fav_item, null);
        TypesViewHolder holder = new TypesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TypesViewHolder typesViewHolder, int i) {
        final Favourites fav_itme = itemsFavList.get(i);
        if (fav_itme != null) {
            typesViewHolder.tv_fav_address.setText(fav_itme.getFav_Address());
            typesViewHolder.tv_fav_name.setText(fav_itme.getFav_Name());
            typesViewHolder.cancel_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelFav(fav_itme.getFav_Id());
                }
            });
        }
    }

    private void cancelFav(String fav_id) {
        if (!AndyUtils.isNetworkAvailable(mContext)) {

            return;
        }
        Commonutils.progressdialog_show(mContext, "Removing...");
        HashMap<String, String> map = new HashMap<>();
        map.put(Const.Params.URL, Const.ServiceType.CANCEL_FAV);
        map.put(Const.Params.ID, new PreferenceHelper(mContext).getUserId());
        map.put(Const.Params.TOKEN, new PreferenceHelper(mContext).getSessionToken());
        map.put(Const.Params.FAV_ID, fav_id);
        Log.d("mahi", "cancel_fav " + map.toString());
        new VollyRequester(mContext, Const.POST, map, Const.ServiceCode.CANCEL_FAV, this);
    }

    @Override
    public int getItemCount() {
        return itemsFavList.size();
    }
}
