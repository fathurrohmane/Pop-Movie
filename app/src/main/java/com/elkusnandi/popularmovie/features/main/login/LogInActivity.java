package com.elkusnandi.popularmovie.features.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webview = new WebView(this);
        setContentView(webview);

        if (getIntent() != null) {
            String requestTokenUrl = getIntent().getStringExtra("RequestTokenUrl");
            webview.loadUrl(requestTokenUrl);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }
}
