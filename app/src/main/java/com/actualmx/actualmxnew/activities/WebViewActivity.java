package com.actualmx.actualmxnew.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actualmx.actualmxnew.R;
import com.actualmx.actualmxnew.utill.Pref;

public class WebViewActivity extends AppCompatActivity {

    TextView txtnewsUrl;
    Button btnShareUrll;
    WebView webview;
    ImageButton btnClose;
    Boolean fromDeepLink;
    private String myUrl ="";

    String htmlUrl;
    Pref pref;
    private ProgressBar webProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        pref = new Pref(WebViewActivity.this);

        txtnewsUrl = (TextView) findViewById(R.id.txtNewsUrl);
        btnShareUrll = (Button) findViewById(R.id.btnShareNewsUrl);
        webview = (WebView) findViewById(R.id.webviewNews);
        webProgress = (ProgressBar) findViewById(R.id.web_progress);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
       // webview.setWebViewClient(new WebViewClient());
        //webview.setWebChromeClient(new WebChromeClient());


        btnClose = (ImageButton) findViewById(R.id.btnClose);

        htmlUrl =/* "http://www.bhaskar.com/";//*/getIntent().getStringExtra("html_url");
        myUrl = htmlUrl;
        fromDeepLink  = getIntent().getBooleanExtra("fromDeepLink", false);
        txtnewsUrl.setText(myUrl);
        webview.loadUrl(myUrl);

        btnShareUrll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* ShareData shareData = new ShareData(WebViewActivity.this, "IndiaTV\n\n"
                        + htmlUrl);*/
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fromDeepLink == true){
                    webview.stopLoading();
                    finish();
                    Intent intent12 = new Intent(WebViewActivity.this, HomeMainActivity.class);
                    startActivity(intent12);
                    overridePendingTransition(0, 0);
                    finish();
                }else {

                    webview.stopLoading();
                    finish();

                }


            }
        });


    }



    public static String getBase64Decode(String inputValue) {
        byte[] decodeValue = Base64.decode(inputValue, Base64.DEFAULT);
        String data = new String(decodeValue);
        return data;
    }

    private class myWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // progress.setVisibility(View.GONE);
            // WebViewActivity.this.progress.setProgress(100);
            // ChartUtills.hideProgress();
            super.onPageFinished(view, url);
            webProgress.setVisibility(View.GONE);
            WebViewActivity.this.webProgress.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // progress.setVisibility(View.VISIBLE);
            // WebViewActivity.this.progress.setProgress(0);
            // ChartUtills.showProgress(WebViewActivity.this);
            super.onPageStarted(view, url, favicon);
            webProgress.setVisibility(View.VISIBLE);
            WebViewActivity.this.webProgress.setProgress(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
