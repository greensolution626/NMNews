package com.android.nmnewsagency.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;

import com.android.nmnewsagency.R;

/**
 * Created by Vishnu Saini on 29-May-18.
 */

public class NotificationUtils {

    public static void creatMessageNotification(Context context, String title,
                                                String textMessage, String notificationType) {
       /* Intent intent = new Intent();

        intent = new Intent(context, Splash.class);


        // intent.putExtra(Constant.FLD_NOTY_TYPE, notificationType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int notifyID = 1;
            String CHANNEL_ID = context.getResources().getString(R.string.app_name);// The id of the channel.
            CharSequence name = context.getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(textMessage);
            mChannel.enableLights(true);
            mChannel.enableVibration(true);
            mChannel.setSound(sound, attributes);
            mNotificationManager.createNotificationChannel(mChannel);
// Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(textMessage)
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.nintyicon))
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setColor(context.getResources().getColor(R.color.color_gray))
                    .setSound(sound)
                    .setShowWhen(true)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText(textMessage))
                    .build();
            mNotificationManager.notify(0, notification);
        } else {
            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.nintyicon))
                    .setContentTitle(title)
                    .setColor(context.getResources().getColor(R.color.color_gray))
                    .setContentText(textMessage)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent)
                    .setShowWhen(true)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(textMessage))
                    .build();
            mNotificationManager.notify(0, notification);*/

//            Notification notification =
//                    new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.logo)
//                            .setContentTitle("My notification")
//                            .setContentText("Hello World!")
//                            .build();
       // }
    }

    public static int getNotificationIcon() {
     //   boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
       // return useWhiteIcon ? R.mipmap.ic_launcher_foreground : R.mipmap.ic_launcher_foreground;
    return 0;
    }


    public static void sendNotification(Context context, String title, String textMessage, String notificationType,
                                        String redirectto, String slug) {
        /*Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.nintyicon);
        Intent intent;
        if (redirectto!=null && redirectto.equalsIgnoreCase(Constants.NOTIFICATION_REDIRECT_TO_PAID_COURCE_LIST)&& Config.getIsRegister())
        {
            intent = new Intent(context, LiveVideoListActivity.class);
            intent.putExtra(Constants.NOTIFICATION_REDIRECT_TO,Constants.NOTIFICATION_REDIRECT_TO_PAID_COURCE_LIST);
        }else if (redirectto!=null && redirectto.equalsIgnoreCase(Constants.NOTIFICATION_REDIRECT_TO_PAID_COURCE)&& Config.getIsRegister())
        {
            intent = new Intent(context, BuyPaidClassActivity.class);
            intent.putExtra(Constants.CLASS_PAIED_DATA,slug);
            intent.putExtra(Constants.NOTIFICATION_REDIRECT_TO,Constants.NOTIFICATION_REDIRECT_TO_PAID_COURCE);
        }else if (redirectto!=null && redirectto.equalsIgnoreCase(Constants.NOTIFICATION_REDIRECT_TO_E_BOOK)&& Config.getIsRegister())
        {
            intent = new Intent(context, EbookSubContent.class);
            intent.putExtra(Constants.NOTIFICATION_REDIRECT_TO,Constants.NOTIFICATION_REDIRECT_TO_E_BOOK);
            intent.putExtra("maintitle",slug);
            intent.putExtra("recvintent","ebook");
        }else if (Config.getIsRegister())
        {

            intent = new Intent(context, HomeActivity.class);
        }else
        {
            intent = new Intent(context, Splash.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "channel_id")

                //.setContentText(textMessage)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                //.setContentInfo(title)
                .setLargeIcon(icon)
                .setColor(Color.RED)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(textMessage))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher_foreground);

        if (redirectto.equalsIgnoreCase(Constants.NOTIFICATION_REDIRECT_TO_PAID_COURCE))
        {
            notificationBuilder.addAction(R.mipmap.ic_launcher_foreground, "Buy Now", pendingIntent);
        }else if (redirectto.equalsIgnoreCase(Constants.NOTIFICATION_REDIRECT_TO_E_BOOK))
        {
            notificationBuilder.addAction(R.mipmap.ic_launcher_foreground, "Buy Now", pendingIntent);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationBuilder.setContentText(Html.fromHtml(textMessage, Html.FROM_HTML_MODE_COMPACT));
            notificationBuilder.setContentTitle(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
            notificationBuilder.setContentInfo(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT));
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(textMessage, Html.FROM_HTML_MODE_COMPACT)));
        } else {
            notificationBuilder.setContentText(Html.fromHtml(textMessage));
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(Html.fromHtml(textMessage)));
        }

     //   notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon).setSummaryText(textMessage));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("channel description");
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());*/
    }

}
