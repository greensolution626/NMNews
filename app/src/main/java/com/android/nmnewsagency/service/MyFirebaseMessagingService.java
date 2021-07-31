package com.android.nmnewsagency.service;

import android.content.Context;
import android.preference.Preference;
import android.util.Log;

import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            if (remoteMessage == null)
                return;
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //handleNotification(remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Notification Body1: " + remoteMessage.getData().size() + "");

           /* String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");
            String type = remoteMessage.getData().get("type");
            String body = remoteMessage.getData().get("body");
            String redirectto = remoteMessage.getData().get("redirectto");
            String slug = remoteMessage.getData().get("slug");
            NotificationUtils.sendNotification(this, title, body, type,redirectto,slug);*/
        }


    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("Devicetoken", "onNewTokenDevice: " + token);
        Prefrence.setDeviceToken(token);
        // getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
    }
}
