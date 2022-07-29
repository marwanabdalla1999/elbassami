package com.albassami.logistics.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.albassami.logistics.NewUtilsAndPref.UiUtils;
import com.albassami.logistics.network.ApiManager.APIConsts;
import com.albassami.logistics.network.ApiManager.APIInterface;
import com.albassami.logistics.ui.activity.agreementactivity;
import com.albassami.logistics.ui.activity.tracking;
import com.albassami.logistics.ui.activity.trackingtowing;
import com.bumptech.glide.Glide;
import com.albassami.logistics.R;
import com.albassami.logistics.network.Models.History;
import com.albassami.logistics.ui.Fragment.ScheduleDetailBottomSheet;
import com.albassami.logistics.ui.activity.HistoryActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by user on 1/20/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<History> itemshistroyList;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat inputformat;
    private TripsInterface tripsInterface;
    boolean isHistory;
    String shipment_type;

    public HistoryAdapter(Context context, List<History> itemshistroyList, TripsInterface tripsInterface, boolean isHistory,String shipment_type) {
        mContext = context;
        simpleDateFormat = new SimpleDateFormat("E, MMM, dd, yyyy hh:mm a");
        inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.tripsInterface = tripsInterface;
        this.itemshistroyList = itemshistroyList;
        this.isHistory = isHistory;
        this.shipment_type=shipment_type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.history_item, null);
        HistoryViewHolder holder = new HistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


     /*   if (viewHolder instanceof HistoryViewHolder) {
            HistoryViewHolder historyViewHolder = (HistoryViewHolder) viewHolder;
            History history_itme = itemshistroyList.get(position);
            if (history_itme != null) {
                historyViewHolder.type.setText(String.format("%s %s", history_itme.getHistory_type(), history_itme.getRequestUniqueId()));
                historyViewHolder.sourceAddress.setText(history_itme.getHistory_Sadd());
                if (!history_itme.getHistory_Dadd().equals("")) {
                    historyViewHolder.destAddress.setText(history_itme.getHistory_Dadd());
                } else {
                    historyViewHolder.destAddress.setText(mContext.getResources().getString(R.string.not_available));
                }
                historyViewHolder.amount.setText(String.format("%s %s", history_itme.getCurrnecy_unit(), history_itme.getHistory_total()));
                historyViewHolder.trip_date.setText(history_itme.getHistory_date());
                Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
                historyViewHolder.itemView.startAnimation(animation);
                Glide.with(mContext).load(history_itme.getProviderPicture()).into(historyViewHolder.providerImage);
                switch (history_itme.getRequest_ico_status()) {
                    case 1:
                        Glide.with(mContext).load(R.drawable.bicycle).into(historyViewHolder.status);
                        break;
                    case 2:
                        Glide.with(mContext).load(R.drawable.schedule).into(historyViewHolder.status);
                        break;
                    case 3:
                        Glide.with(mContext).load(R.drawable.flag).into(historyViewHolder.status);
                        break;
                }

                historyViewHolder.statusText.setText(history_itme.getRequest_icon_status_text());
                if(history_itme.getRequest_ico_status() == 1) {
                    historyViewHolder.amount.setText(history_itme.getRequest_icon_status_text());
                }
                else if(history_itme.getRequest_ico_status() == 4) {
                    historyViewHolder.statusText.setText("");
                    historyViewHolder.amount.setText(history_itme.getRequest_icon_status_text());
                }

                historyViewHolder.itemView.setOnClickListener(view -> {
                    ScheduleDetailBottomSheet scheduleDetailBottomSheet = new ScheduleDetailBottomSheet();
                    scheduleDetailBottomSheet.setRequestId(history_itme.getRequestId());
                    scheduleDetailBottomSheet.show(mContext.getSupportFragmentManager(), scheduleDetailBottomSheet.getTag());
                });
            }
        }*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        HistoryViewHolder historyViewHolder = (HistoryViewHolder) holder;
        historyViewHolder.sourceAddress.setText(itemshistroyList.get(position).getLoc_from());
        historyViewHolder.destAddress.setText(itemshistroyList.get(position).getLoc_to());

        try {
            Bitmap bitmap= barcode(itemshistroyList.get(position).getSale_line_rec_name());
            historyViewHolder.type.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        historyViewHolder.statusText.setText(itemshistroyList.get(position).getState());
        historyViewHolder.sales.setText(itemshistroyList.get(position).getSale_line_rec_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shipment_type.equals("1")){
                checkdate(itemshistroyList.get(position).getSale_line_rec_name());}
                else{
                    mContext.startActivity(new Intent(mContext, trackingtowing.class).putExtra("order_id",itemshistroyList.get(position).getSale_line_rec_name()));
                }
            }
        });

    }

    private Bitmap barcode(String sale_line_rec_name) throws WriterException {
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        BitMatrix bitMatrix=multiFormatWriter.encode(sale_line_rec_name, BarcodeFormat.CODE_128,400,170,null);
        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return itemshistroyList.size();
    }


    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sourceAddress)
        TextView sourceAddress;
        @BindView(R.id.destAddress)
        TextView destAddress;
        @BindView(R.id.type)
        ImageView type;
        @BindView(R.id.sales)
        TextView sales;


        @BindView(R.id.statusText)
        TextView statusText;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void showLoading() {
        if (tripsInterface != null)
            tripsInterface.onLoadMoreTrips(itemshistroyList.size());
    }

    public interface TripsInterface {
        void onLoadMoreTrips(int skip);
    }
    private void checkdate(String sale_line_rec_name) {
        UiUtils.showLoadingDialog(mContext);
        OkHttpClient client = new OkHttpClient()

                .newBuilder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .addHeader("access-token", "access_token_312e6ea498ec5c3e2257b8082de7ab71dee265a5")
                            .addHeader("Content-Type", "application/x-www-form-urlencoded")
                            .addHeader("Cookie", "session_id=2113bd21b6756fe4275ad5f367b3ec13470f9856")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }).callTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(APIConsts.Urls.Bassami_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIInterface apiServices1=retrofit.create(APIInterface.class);
        Call<String> call=apiServices1.trackshipment(sale_line_rec_name,"");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                UiUtils.hideLoadingDialog();
                if(response.isSuccessful()&& response!=null){
                    JSONObject alldata = null;
                    try {
                        alldata = new JSONObject(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(alldata!=null){
                        JSONArray data=alldata.optJSONArray("data");
                        if(data!=null){
                            JSONObject trackingdata= data.optJSONObject(0);
                            if(trackingdata!=null){
                                JSONObject car_maker =null;
                                JSONArray otherservice =null;
                                JSONObject small =null;
                                JSONObject medium =null;
                                JSONObject large =null;
                                String carmaker="غير معرف";
                                String strhomepickup="";
                                String strhomedelivery="";
                                String strhomepickupprice="";
                                String strhomedeliveryprice="";
                                String strsmall="0";
                                String strmedium="0";
                                String strlarge="0";

                                try{
                                    car_maker=trackingdata.optJSONObject("car_maker");
                                    otherservice=trackingdata.optJSONArray("other_services");
                                    } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(car_maker!=null){

                                    carmaker=trackingdata.optString("car_make");
                                }

                             /*  try{ if(small!=null){
                                    if(small.optString("service").equals("Large Box")){
                                        strlarge=Double.toString(large.optDouble("qty"));
                                    }
                                    else if(small.optString("service").equals("Medium Box")){
                                        strmedium=Double.toString(medium.optDouble("qty"));
                                    }
                                        else if(small.optString("service").equals("Small Box")){
                                        strsmall=Double.toString(small.optDouble("qty"));
                                    }

                                }} catch (Exception e) {
                                   e.printStackTrace();
                               }
                               try {
                                if(medium!=null){
                                    if(medium.optString("service").equals("Large Box")){
                                        strlarge=Double.toString(large.optDouble("qty"));}
                                    else if(medium.optString("service").equals("Medium Box")){
                                        strmedium=Double.toString(medium.optDouble("qty"));}
                                    else if(medium.optString("service").equals("Small Box")){
                                        strsmall=Double.toString(small.optDouble("qty"));
                                    }

                                }} catch (Exception e) {
                                   e.printStackTrace();
                               }
                               try{
                                if(large!=null){
                                    if(large.optString("service").equals("Large Box")){
                                        strlarge=Double.toString(large.optDouble("qty"));}
                                    else if(large.optString("service").equals("Medium Box")){
                                        strmedium=Double.toString(medium.optDouble("qty"));}
                                    else if(large.optString("service").equals("Small Box")){
                                        strsmall=Double.toString(small.optDouble("qty"));
                                    }

                                }} catch (Exception e) {
                                   e.printStackTrace();
                               }*/

                                 for(int y=0 ;y<5;y++){
                                     try{  if(otherservice.optJSONObject(y).optString("service").equals("Home Pickup")){
                                         strhomepickup=otherservice.optJSONObject(y).optString("pickup_location");
                                         strhomepickupprice=Double.toString(otherservice.optJSONObject(y).optDouble("cost"));

                                     }
                                           else if(otherservice.optJSONObject(y).optString("service").equals("Home Delivery")){
                                         strhomedelivery=otherservice.optJSONObject(y).optString("home_location");
                                         strhomedeliveryprice=Double.toString(otherservice.optJSONObject(y).optDouble("cost"));

                                           }
                                         else if(otherservice.optJSONObject(y).optString("service").equals("Large Box")){
                                         strlarge="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")" +otherservice.optJSONObject(y).optDouble("cost");
                                     }
                                        else if(otherservice.optJSONObject(y).optString("service").equals("Medium Box")){
                                         strmedium="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")"+otherservice.optJSONObject(y).optDouble("cost");
                                         }
                                        else if(otherservice.optJSONObject(y).optString("service").equals("Small Box")){
                                    strsmall="("+(int)otherservice.optJSONObject(y).optDouble("qty")+")"+otherservice.optJSONObject(y).optDouble("cost");
                                         }


                                     } catch (Exception e) {
                                         e.printStackTrace();
                                     }
                                 }

                                Intent intent=new Intent(mContext,agreementactivity.class);
                                intent.putExtra("order_ref",trackingdata.optString("order_ref"));
                                intent.putExtra("receiver_name",trackingdata.optString("receiver_name"));
                                intent.putExtra("receiver_mob_no",trackingdata.optString("receiver_phone"));
                                intent.putExtra("customer_name",trackingdata.optString("sender_name"));
                                intent.putExtra("order_date",trackingdata.optString("order_date"));
                                intent.putExtra("service_type",trackingdata.optString("service_type"));
                                intent.putExtra("loc_from",trackingdata.optJSONObject("loc_from").optString("name"));
                                intent.putExtra("loc_to",trackingdata.optJSONObject("loc_to").optString("name"));
                                intent.putExtra("expected_delivery",trackingdata.optString("expected_delivery_date"));
                                intent.putExtra("car_model",car_maker.optString("name_ar"));
                                intent.putExtra("year",trackingdata.optString("year"));
                                intent.putExtra("car_make",carmaker);
                                intent.putExtra("state",trackingdata.optString("state"));
                                intent.putExtra("homepickup",strhomepickup);
                                intent.putExtra("homedelivery",strhomedelivery);
                                intent.putExtra("homepickupprice",strhomepickupprice);
                                intent.putExtra("homedeliveryprice",strhomedeliveryprice);
                                intent.putExtra("small",strsmall);
                                intent.putExtra("medium",strmedium);
                                intent.putExtra("large",strlarge);
                                intent.putExtra("total_amount",Double.toString(trackingdata.optDouble("total_amount")));
                                intent.putExtra("due_amount",Double.toString(trackingdata.optDouble("due_amount")));
                                intent.putExtra("paid_amount",Double.toString(trackingdata.optDouble("paid_amount")));
                                intent.putExtra("service_type",trackingdata.optString("service_type"));
                                mContext.startActivity(intent);




                            }
                            else{
                                Toast.makeText(mContext, mContext.getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


                            }
                        }
                        else{
                            Toast.makeText(mContext, mContext.getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


                        }
                    } else{
                        Toast.makeText(mContext, mContext.getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


                    }

                }
                else{
                    Toast.makeText(mContext, mContext.getString(R.string.dataenteredisincorrectpleasecheckit), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                UiUtils.hideLoadingDialog();
                Toast.makeText(mContext, mContext.getString(R.string.pleasecheckyourinternetconnection), Toast.LENGTH_SHORT).show();
            }
        });

    }
}


