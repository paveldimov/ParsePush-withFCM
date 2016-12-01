package com.example.pavel.parsetest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final int MESSAGE_NOTIFICATION_ID = 123456;
    private Bitmap image;
    private Uri imageUri;
    private JSONObject jsonObject;
    private String notificationTitle,notificationBody;
    private String url;


    public MyFirebaseMessagingService() {}

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(null);

        Map<String, String> data = remoteMessage.getData();
        RemoteMessage.Notification notification = remoteMessage.getNotification();

        String  jsonDataString = data.get("data");

        try {
            if(jsonDataString != null) {
                jsonObject = new JSONObject(jsonDataString);
                notificationTitle = (String) jsonObject.get("title");
                notificationBody = (String) jsonObject.get("alert");
                url = (String) jsonObject.get("url");
            }

            imageUri =Uri.parse("android.resource://com.example.pavel.parsetest/drawable/firebase_large_icon"); //takes the image from the drawable res.
            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri); //gets the bitmap from the image

        } catch (Exception e) {
            e.printStackTrace();
        }






                createNotification(notification, image);



    }




    private void createNotification( RemoteMessage.Notification notification, Bitmap image) {
        Context context = getBaseContext();


        Intent intent = new Intent(this, WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("url",url);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(image));
        mBuilder.setSmallIcon(R.drawable.icon_christmas_tree);
        mBuilder.setContentIntent(pendingIntent);

        if(notification ==null) {
            mBuilder.setContentTitle(notificationTitle).setContentText(notificationBody);
        }else {
            mBuilder.setContentTitle(notification.getTitle()).setContentText(notification.getBody());
        }

       // mBuilder.setContentTitle(notification.getTitle()).setContentText(notification.getBody());
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }

}
