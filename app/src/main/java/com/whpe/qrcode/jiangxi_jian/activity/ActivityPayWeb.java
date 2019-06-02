package com.whpe.qrcode.jiangxi_jian.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.view.ProgressWebView;


/**
 * Created by yang on 2018/10/22.
 */

public class ActivityPayWeb extends NormalTitleActivity {

    private ProgressWebView wv;
    private WebSettings webSettings;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_payweb);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.payhtml_title));
        initWebview();
    }

    private void initWebview() {
        String param=getIntent().getExtras().getString(GlobalConfig.INTENT_TOPAYWEB_PARAM);
        String web_type=getIntent().getExtras().getString(GlobalConfig.INTENT_TOPAYWEB_TYPE);
        webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);    //允许加载javascript
        webSettings.setSupportZoom(true);          //允许缩放
        webSettings.setBuiltInZoomControls(true);  //原网页基础上缩放
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);      //任意比例缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        String encoding = "UTF-8";
        String mimeType = "text/html";
        if(web_type.equals(GlobalConfig.INTENT_TOPAYWEB_TYPE_HTML)){
            wv.loadDataWithBaseURL(null, param,mimeType, encoding, null);
        }else if(web_type.equals(GlobalConfig.INTENT_TOPAYWEB_TYPE_URL)){
            wv.loadUrl(param);
        }
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("YC","start_URL="+url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("YC","stop_URL="+url);
            }

            //2017 10-19webview无法读取页面由于https因此修改
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                handler.proceed();// 接受所有网站的证书
            }

        });
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        wv = (ProgressWebView) findViewById(R.id.wv);
    }
}
