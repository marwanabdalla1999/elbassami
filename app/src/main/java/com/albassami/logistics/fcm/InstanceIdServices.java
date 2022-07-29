package com.albassami.logistics.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.albassami.logistics.R;
import com.albassami.logistics.Utils.PreferenceHelper;
import com.albassami.logistics.ui.activity.MainActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefKeys;
import com.albassami.logistics.NewUtilsAndPref.sharedpref.PrefUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class InstanceIdServices extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private PreferenceHelper preferenceHelper;
    private String date = "";
    private Map data;
    String from;

    @Override
    public void onMessageReceived(RemoteMessage message){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        if (manager != null) {
            manager.notify(0, builder.build());
        }
    }
}


//public class InstanceIdServices extends FirebaseInstanceIdService {
//
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        saveDeviceToken(refreshedToken);
//    }
//
//    private void saveDeviceToken(String token) {
//        PrefUtils.getInstance(this).setValue(PrefKeys.FCM_TOKEN, token);
//    }
//
//}