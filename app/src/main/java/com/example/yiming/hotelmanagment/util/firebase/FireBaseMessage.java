package com.example.yiming.hotelmanagment.util.firebase;

import android.content.Context;
import android.util.Log;

import com.example.yiming.hotelmanagment.common.Constants;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessage extends FirebaseMessagingService {
    String TAG="FireBaseMessage";
    public static final String SENDER_ID ="508355388568";
    int messageId=0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.i("Refreshed_token ",s);
    }

    public void sentMessage(Context context){
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        Log.d("Refreshed_token ",context.getSharedPreferences(Constants.fireBase,MODE_PRIVATE).getString(Constants.fireBase,"default"));
        Log.d("Refreshed_token ",   SENDER_ID + "@gcm.googleapis.com");
        fm.send(new RemoteMessage.Builder(SENDER_ID + "@gcm.googleapis.com")
                .setMessageId(String.valueOf(messageId++))
                .addData("my_message", "Hello World")
                .addData("my_action","SAY_HELLO")
                .build());
    }
}


// Sender ID 508355388568