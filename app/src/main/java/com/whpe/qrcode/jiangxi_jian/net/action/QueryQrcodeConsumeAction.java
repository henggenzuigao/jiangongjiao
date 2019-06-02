package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;


import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.ConsumeDetailRequestBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2018/10/15.
 */

public class QueryQrcodeConsumeAction {
    public Inter_QrcodeConsumeinfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryQrcodeConsumeAction(Activity context,Inter_QrcodeConsumeinfo inter_querypaytype) {
        this.inter = inter_querypaytype;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String phonenum,String platformuserid,String qrcardNO,String sourcetype){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final ConsumeDetailRequestBody consumeDetailRequestBody=new ConsumeDetailRequestBody();
        consumeDetailRequestBody.setPhoneNum(phonenum);
        consumeDetailRequestBody.setPlatformUserId(platformuserid);
        consumeDetailRequestBody.setSourceType(sourcetype);
        consumeDetailRequestBody.setQrCardNo(qrcardNO);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).consumeDetailQuery(head, consumeDetailRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","二维码记录查询="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onQueryQrcodeConsumeSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryQrcodeConsumeFaild(e.getMessage());
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

    public interface Inter_QrcodeConsumeinfo{
        public void onQueryQrcodeConsumeSucces(ArrayList<String> getinfo);
        public void onQueryQrcodeConsumeFaild(String resmsg);
    }
}
