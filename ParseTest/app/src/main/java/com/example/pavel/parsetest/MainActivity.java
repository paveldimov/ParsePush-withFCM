package com.example.pavel.parsetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    private ParseInstallation parseInstallation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Parse.initialize(new Parse.Configuration.Builder(this)
                    .applicationId("appId")
                    .server("http://8883cfa8.ngrok.io/parse/")
                    .clientKey("clientKey")
                    .build());

            parseInstallation = ParseInstallation.getCurrentInstallation();
            String senderId = getResources().getString(R.string.gcm_defaultSenderId);
            parseInstallation.put("GCMSenderId", senderId);
            parseInstallation.saveInBackground();
        }catch (Exception e) {
            e.printStackTrace();

        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseMessaging.getInstance().subscribeToTopic("News");

        ParsePush.subscribeInBackground("Live");

        ParseUser.logInInBackground("username","password", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    System.out.println("log in successfull");
                } else {
                    System.out.println("log in UNsuccessfull");
                }
            }
        });
    }
}

