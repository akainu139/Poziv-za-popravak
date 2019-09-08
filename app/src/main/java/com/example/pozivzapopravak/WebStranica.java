package com.example.pozivzapopravak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebStranica extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_stranica);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            String webStranica = extras.getString("stranica");
            String pomoc = webStranica;
            if(!pomoc.contains("http"))
            {
                webStranica = "http://" + pomoc;
            }

            webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(webStranica);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

        }

    }
}
