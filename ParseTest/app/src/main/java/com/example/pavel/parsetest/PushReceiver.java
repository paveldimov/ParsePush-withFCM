package com.example.pavel.parsetest;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;


public class PushReceiver extends ParsePushBroadcastReceiver {


    @Override
    public void onPushOpen(Context context, Intent intent) {
        Intent i = new Intent(context, WebViewActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {

    }
}
