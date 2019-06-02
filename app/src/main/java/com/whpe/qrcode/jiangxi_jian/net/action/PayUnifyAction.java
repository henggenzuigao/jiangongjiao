package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;


import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.UnifiedOrderRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2018/10/9.
 */

public class PayUnifyAction {
    public Inter_queryqruserinfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public PayUnifyAction(Activity context,Inter_queryqruserinfo inter_querypaytype) {
        this.inter = inter_querypaytype;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(int rechargeAmount,String payPurpose,String cardNo,String payWay,String phoneNum,String cardArea,String readWay,String balance,String goodsOrdernum){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final UnifiedOrderRequestBody unifiedOrderRequestBody=new UnifiedOrderRequestBody();
        unifiedOrderRequestBody.setRechargeAmount(rechargeAmount);
        unifiedOrderRequestBody.setPayPurpose(payPurpose);
        unifiedOrderRequestBody.setCardNo(cardNo);
        unifiedOrderRequestBody.setPayWay(payWay);
        unifiedOrderRequestBody.setPhoneNum(phoneNum);
        unifiedOrderRequestBody.setCardArea(cardArea);
        unifiedOrderRequestBody.setReadWay(readWay);
        unifiedOrderRequestBody.setBalance(balance);
        unifiedOrderRequestBody.setGoodsOrderNum(goodsOrdernum);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).unifiedOrder(head, unifiedOrderRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","统一下单="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onPayUnifySucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onPayUnifyFaild(e.getMessage());
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

    public interface Inter_queryqruserinfo{
        public void onPayUnifySucces(ArrayList<String> getinfo);
        public void onPayUnifyFaild(String resmsg);
    }
}
