package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Context;
import android.net.http.SslError;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.view.ProgressWebView;


/**
 * Created by yang on 2018/11/7.
 */

public class ActivityTitleWeb extends NormalTitleActivity {
    private ProgressWebView wv;
    private String weburl;
    private String title;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        weburl = getIntent().getExtras().getString("weburl");
        title = getIntent().getExtras().getString("webtitle");

    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_titleweb);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(title);
        initWebview();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        wv = findViewById(R.id.wv);
    }

    private void initWebview() {
        WebSettings settings = wv.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);          //允许缩放
        settings.setBuiltInZoomControls(true);  //原网页基础上缩放
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);      //任意比例缩放


        settings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        wv.loadUrl(weburl);
        wv.setWebViewClient(new WebViewClient(){
            //2017 10-19webview无法读取页面由于https因此修改
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv.removeAllViews();
        wv.destroy();
    }

}
