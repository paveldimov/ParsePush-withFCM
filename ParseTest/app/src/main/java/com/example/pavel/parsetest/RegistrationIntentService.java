package com.example.pavel.parsetest;

import android.app.IntentService;
import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceId;
import com.parse.ParseInstallation;

public class RegistrationIntentService extends IntentService {


    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();

        String token = instanceID.getToken();

        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
      //  parseInstallation.put("deviceToken",token);

    }
}