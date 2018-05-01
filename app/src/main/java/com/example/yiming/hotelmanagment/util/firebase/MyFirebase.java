package com.example.yiming.hotelmanagment.util.firebase;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.yiming.hotelmanagment.common.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebase extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.fireBase,MODE_PRIVATE);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sharedPreferences.edit().putString(Constants.fireBase,refreshedToken).apply();
        Log.d("Refreshed_token: ",   refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
    }
}
