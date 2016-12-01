package com.example.pavel.parsetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private String url;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());

        Intent callingIntent  = getIntent();


        if(getIntent().getExtras() !=null) {
           url = getIntent().getStringExtra("url");

        }
        webView.loadUrl(url);

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
