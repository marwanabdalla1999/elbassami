package com.albassami.logistics.NewUtilsAndPref;

import android.text.TextUtils;
import android.util.Patterns;


import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppUtils {
    private AppUtils(){

    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = new Date().getTime();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static RequestBody getPartFor(String stuff) {
        return RequestBody.create(MediaType.parse("text/plain"), stuff);
    }

//    public static void permissionCheck(MainActivity context) {
//        PermissionBitte.permissions(context).observe(context, permissions -> {
//            for (Permission permission : permissions.filter(PermissionResult.DENIED)){
//            }
//
//            if(permissions.needAskingForPermission())
//            {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle(context.getString(R.string.permissions))
//                        .setMessage(context.getString(R.string.youNeedToGrantPermission))
//                        .setPositiveButton(R.string.grant, (dialog, which) -> {
//                    PermissionBitte.ask(context);
//                }).setNegativeButton(R.string.dont, (dialog, which) -> {
//                    dialog.dismiss();
//                }).show();
//            }
//            else if(permissions.deniedPermanently())
//            {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle(context.getString(R.string.permissions))
//                        .setMessage(context.getString(R.string.youNeedToGrantThePermissionsMannualy))
//                        .setPositiveButton(R.string.grant, (dialog, which) -> {
//                    PermissionBitte.goToSettings(context);
//                }).setNegativeButton(R.string.dont, (dialog, which) -> {
//                    dialog.dismiss();
//                }).show();
//            }
//            else if(permissions.showRationale())
//            {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//                alertDialogBuilder.setTitle(context.getString(R.string.permissions))
//                        .setMessage(context.getString(R.string.youHaveNotGrantedAllThePermissions))
//                        .setPositiveButton(R.string.grant, (dialog, which) -> {
//                    PermissionBitte.ask(context);
//                }).setNegativeButton(R.string.dont, (dialog, which) -> {
//                    dialog.dismiss();
//                }).show();
//            }
//            else if(permissions.allGranted())
//            {
//
//            }
//        });
//    }
}
