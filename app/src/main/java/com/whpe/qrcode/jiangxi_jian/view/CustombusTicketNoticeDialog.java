package com.whpe.qrcode.jiangxi_jian.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSearchBusline;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceOther;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;


/**
 * Created by yang on 2018/12/18.
 */

public class CustombusTicketNoticeDialog {
    private ParentActivity context;
    private Dialog dialog;
    private Button btn_submit;
    private CheckBox cb_agree_agreement;
    private WebView wv_agreement;
    private ImageView iv_info;
    private final SharePreferenceOther sharePreferenceOther;

    public CustombusTicketNoticeDialog(ParentActivity activity) {
        this.context = activity;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        sharePreferenceOther = new SharePreferenceOther(context);
    }

    public CustombusTicketNoticeDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_dialog_custombusticketnotice, null);
        btn_submit=(Button) view.findViewById(R.id.btn_submit);
        cb_agree_agreement=(CheckBox)view.findViewById(R.id.cb_agree_agreement);
        wv_agreement=(WebView)view.findViewById(R.id.wv_agreement);
        iv_info=(ImageView)view.findViewById(R.id.iv_info);
        dialog = new Dialog(context, R.style.agreement_dialog);
        dialog.setContentView(view);
        return this;
    }

    public CustombusTicketNoticeDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CustombusTicketNoticeDialog setAgreeClickListner(final View.OnClickListener listner){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_agree_agreement.isChecked()){
                    sharePreferenceOther.saveTicketNoticeStatus(true);
                    dissmiss();
                    context.transAty(ActivityCustomBusSearchBusline.class);
                }else {
                    ToastUtils.showToast(context,context.getString(R.string.custombustotal_ticketnotice_pleaseagree));
                }
            }
        });
        return this;
    }

    public CustombusTicketNoticeDialog setWebAgreement(String url){
        wv_agreement.setVisibility(View.VISIBLE);
        iv_info.setVisibility(View.GONE);
        WebSettings settings = wv_agreement.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");//设置网页默认编码
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        //settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);          //允许缩放
        settings.setBuiltInZoomControls(true);  //原网页基础上缩放
        settings.setDisplayZoomControls(false);
        //settings.setUseWideViewPort(true);      //任意比例缩放


        settings.setAppCacheEnabled(true);
        String appCaceDir = context.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        wv_agreement.loadUrl(url);
        wv_agreement.setWebViewClient(new WebViewClient(){
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
        return this;
    }

    public CustombusTicketNoticeDialog setWebIMGAgreement(String url){
        wv_agreement.setVisibility(View.GONE);
        iv_info.setVisibility(View.VISIBLE);
        Picasso.with(context)
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE) //新版Picasso必须都设置缓存
                .config(Bitmap.Config.RGB_565)
                .into(iv_info);
        return this;
    }


    public void show() {
        dialog.show();
    }

    public void dissmiss(){
        dialog.dismiss();
    }

}
