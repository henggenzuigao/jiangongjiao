package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsContentAckBody;
import com.tomyang.whpe.qrcode.utils.RichText2Html;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryNewsContentAction;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.view.ProgressWebView;

/**
 * Created by yang on 2019/1/3.
 */

public class ActivityNewsAndAdvertiseWeb extends NormalTitleActivity implements QueryNewsContentAction.Inter_QueryNewsContent {
    private ProgressWebView wv;
    private String contenttype;
    private String param;
    private TextView tv_title;
    private String contentid;
    private String title;
    private ImageView iv_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        title = getIntent().getExtras().getString(GlobalConfig.NEWSANDADVER_INTENT_TITLE);
        contentid=getIntent().getExtras().getString(GlobalConfig.NEWSANDADVER_INTENT_CONTENTID);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_truenewsweb);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        if(!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }
        initWebview();
        requestForNewsContent();
    }

    private void requestForNewsContent() {
        QueryNewsContentAction queryNewsContentAction=new QueryNewsContentAction(this,this);
        queryNewsContentAction.sendAction(contentid);
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
        wv.getSettings().setBlockNetworkImage(false); // 解决图片不显示


        settings.setAppCacheEnabled(true);
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        /*wv.loadUrl(weburl);
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

        });*/
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        wv = findViewById(R.id.wv);
        tv_title = (TextView)findViewById(R.id.tv_title);
        iv_info = (ImageView)findViewById(R.id.iv_info);
    }

    private void loadUrl(String url){
        wv.loadUrl(url);
        Log.e("YC","url="+url);
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

    private void loadimg(String url){
        wv.setVisibility(View.GONE);
        iv_info.setVisibility(View.VISIBLE);
        Picasso.with(this)
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE) //新版Picasso必须都设置缓存
                .config(Bitmap.Config.RGB_565)
                .into(iv_info);
    }

    private void loadHtml(String url){
        Log.e("YC","url="+url);
        wv.loadDataWithBaseURL("", url, "text/html", "utf-8", null);
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
        /*super.onDestroy();
        wv.removeAllViews();
        wv.destroy();*/
        if( wv!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = wv.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(wv);
            }

            wv.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            wv.getSettings().setJavaScriptEnabled(false);
            wv.clearHistory();
            wv.clearView();
            wv.removeAllViews();
            wv.destroy();

        }
        super.onDestroy();
    }

    @Override
    public void onQueryQueryNewsContentSucces(QueryNewsContentAckBody getinfo) {
        if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_IMAGE)){
            Log.e("YC","img新闻广告");
            loadimg(getinfo.getContent());
            return;
        }
        if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_URL)){
            Log.e("YC","url新闻广告");
            loadUrl(getinfo.getContent());
            return;
        }
        if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_HTML)){
            Log.e("YC","html新闻广告");
            String content=RichText2Html.INSTANCE.transContent(getinfo.getContent());
            RichText2Html.INSTANCE.creatHtml(Environment.getExternalStorageDirectory()+"/Download/"+getPackageName(),contentid,content);
            loadUrl("file://"+Environment.getExternalStorageDirectory()+"/Download/"+getPackageName()+"/html/"+contentid+".html");
            return;
        }
    }

    @Override
    public void onQueryQueryNewsContentFaild(String resmsg) {
        ToastUtils.showToast(this,getString(R.string.app_request_exception_msg));
    }
}
