package com.fancycodeworks.tumbleapp.activities;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fancycodeworks.tumbleapp.R;

public class BrowserActivity extends BaseAuthenticatedActivity {
    public  static final String TAG = "BROWSER ACTIVITY";
    private WebView webView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        webView = (WebView) findViewById(R.id.activity_browser_webView);
        toolbar = (Toolbar) findViewById(R.id.activity_browser_toolbar);

        webView.loadUrl("https://stackoverflow.com/questions/7416096/android-webview-not-loading-an-https-url");
        setSupportActionBar(toolbar);

    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.e(TAG, "SSL error occurred");
            handler.proceed();
        }
    }
}
