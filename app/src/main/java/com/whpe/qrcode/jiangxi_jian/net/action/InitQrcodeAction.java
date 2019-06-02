package com.whpe.qrcode.jiangxi_jian.net.action;

/**
 * Created by yang on 2018/10/9.
 */

import android.app.Activity;
import android.util.Log;


import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.PullQrcodeRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2018/9/27.
 */

public class InitQrcodeAction {

    public Inter_initqrcode inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public InitQrcodeAction(Activity context,Inter_initqrcode inter_querypaytype) {
        this.inter = inter_querypaytype;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction( String phone, String platformUserId,String qrcardNO, String payWay){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final PullQrcodeRequestBody pullQrcodeRequestBody=new PullQrcodeRequestBody();
        pullQrcodeRequestBody.setPayWay(payWay);
        pullQrcodeRequestBody.setPhoneNum(phone);
        pullQrcodeRequestBody.setPlatformUserId(platformUserId);
        pullQrcodeRequestBody.setQrCardNo(qrcardNO);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).pullQrcode(head, pullQrcodeRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String info) {
                        final String getinfo=info;
                        Log.e("YC","填充二维码="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onInitqrcodeSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onInitqrcodeFaild(e.getMessage());
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

    public interface Inter_initqrcode{
        public void onInitqrcodeSucces(ArrayList<String> getinfo);
        public void onInitqrcodeFaild(String resmsg);
    }
}