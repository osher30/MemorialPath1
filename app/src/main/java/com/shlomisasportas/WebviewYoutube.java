package com.shlomisasportas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;





public class WebviewYoutube extends AppCompatActivity {

    private WebView b;


    boolean isRunning = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_youtube);

        b=(WebView)findViewById(R.id.we);

        b.setWebViewClient(new MyBrowser());

        Toast.makeText(WebviewYoutube.this, "Loading...", Toast.LENGTH_LONG).show();
        final String url ="https://www.youtube.com/watch?v="+getIntent().getStringExtra("url");
        b.getSettings().setLoadsImagesAutomatically(true);
        b.getSettings().setJavaScriptEnabled(true);
        b.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        b.setWebChromeClient(new WebChromeClient());

        WebSettings settings = b.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            b.getSettings().setDatabasePath("/data/data/" + b.getContext().getPackageName() + "/databases/");
        }
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);
        b.setWebChromeClient(new WebChromeClient() {

        });

        b.loadUrl(url);




    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if(url.startsWith("mailto:")){
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                startActivity(i);
                return true;
            }

            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                view.reload();
                return true;
            }




            Toast.makeText(WebviewYoutube.this, "Loading...", Toast.LENGTH_LONG).show();
            view.loadUrl(url);


            return true;
        }





    }

    @Override
    public void onResume()
    {
        super.onResume();
        b.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        b.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if(b.canGoBack()) {
                b.goBack();
            }
            else
            {
                finish();
                isRunning = false;
            }
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }



    /* <activity android:name="se.Huyomimi.android.MainActivity"
    android:configChanges="orientation|screenSize">

        </activity>
*/







}