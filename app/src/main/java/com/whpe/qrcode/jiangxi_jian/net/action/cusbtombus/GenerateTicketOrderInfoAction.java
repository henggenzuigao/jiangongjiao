package com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus;

import android.app.Activity;
import android.util.Log;

import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.GenerateTicketOrderInfoRequestBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.QueryTicketByLineIdRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2019/1/13.
 */

public class GenerateTicketOrderInfoAction {
    public Inter_generateTicketOrderInfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public GenerateTicketOrderInfoAction(Activity context, Inter_generateTicketOrderInfo  interinter) {
        this.inter = interinter;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String lineRunId,int ticketNum,String palce,String isVip,int ticketnum,int layernum){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final GenerateTicketOrderInfoRequestBody generateTicketOrderInfoRequestBody=new GenerateTicketOrderInfoRequestBody();
        generateTicketOrderInfoRequestBody.setInterfaceVersion(GlobalConfig.CUSTOMBUS_INTERFACEVERSION);
        generateTicketOrderInfoRequestBody.setLineRunId(lineRunId);
        generateTicketOrderInfoRequestBody.setPalce(palce);
        generateTicketOrderInfoRequestBody.setTicketNum(ticketNum);
        generateTicketOrderInfoRequestBody.setVip(isVip);
        generateTicketOrderInfoRequestBody.setTicketNum(ticketnum);
        generateTicketOrderInfoRequestBody.setChooseLayerNum(layernum);

        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).generateTicketOrderInfo(head, generateTicketOrderInfoRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","生成座位号与票单="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onGenerateTicketOrderInfoSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onGenerateTicketOrderInfoFaild(e.getMessage());
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

    public interface Inter_generateTicketOrderInfo {
        public void onGenerateTicketOrderInfoSucces(ArrayList<String> getinfo);
        public void onGenerateTicketOrderInfoFaild(String resmsg);
    }
}
