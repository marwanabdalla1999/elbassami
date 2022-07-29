package com.albassami.logistics.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.albassami.logistics.R;
import com.albassami.logistics.ui.activity.MainActivity;

import java.util.Map;


/**
 * Created by user on 6/29/2015.
 */

public class FCMIntentService extends FirebaseMessagingService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    private String date = "";
    private Map data;
    String from;

    @Override
    public void onMessageReceived(RemoteMessage message) {
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

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
    }
}
