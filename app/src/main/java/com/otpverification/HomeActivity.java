package com.otpverification;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.otpverification.R;

public class HomeActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        String url ="https://www.murudjanjiratourism.in/" +
                "";
        WebView web = findViewById(R.id.webView);

        web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(url);

        web.setWebViewClient(new WebViewClient () {
            @ Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("whatsapp://")) {

                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                    return true;

                }
                else {
                    return false;
                }
            }
        });
    }



}