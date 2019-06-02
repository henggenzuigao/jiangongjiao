package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;


import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.LoadQrParamConfigRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.util.ArrayList;
import java.util.UUID;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2018/10/9.
 */

public class LoadQrcodeParamAction {
    public Inter_loadqrcodeparam inter;
    public Activity activity;

    public LoadQrcodeParamAction(Activity context,Inter_loadqrcodeparam inter_querypaytype) {
        this.inter = inter_querypaytype;
        activity=context;
    }

    public void sendAction(){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        final LoadQrParamConfigRequestBody loadQrParamConfigRequestBody=new LoadQrParamConfigRequestBody();
        loadQrParamConfigRequestBody.setRequestNo(UUID.randomUUID().toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).loadQrParam(head, loadQrParamConfigRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        final String getinfo=info;
                        Log.e("YC","预加载="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onLoadqrcodeparamSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onLoadqrcodeparamFaild(e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        }).start();


    }

    public interface Inter_loadqrcodeparam{
        public void onLoadqrcodeparamSucces(ArrayList<String> getinfo);
        public void onLoadqrcodeparamFaild(String resmsg);
    }
}
