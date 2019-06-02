package com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus;

import android.app.Activity;
import android.util.Log;

import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.QueryBusLinePageRequestBody;
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

public class QueryTicketsByBusLineIdAction {
    public Inter_QueryTicketsByBusLineId inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryTicketsByBusLineIdAction(Activity context, Inter_QueryTicketsByBusLineId interinter) {
        this.inter = interinter;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String lineId,String chooseDate,String chooseTime,String showSeq){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final QueryTicketByLineIdRequestBody queryTicketByLineIdRequestBody=new QueryTicketByLineIdRequestBody();
        queryTicketByLineIdRequestBody.setInterfaceVersion(GlobalConfig.CUSTOMBUS_INTERFACEVERSION);
        queryTicketByLineIdRequestBody.setLineId(lineId);
        queryTicketByLineIdRequestBody.setChooseDate(chooseDate);
        queryTicketByLineIdRequestBody.setChooseTime(chooseTime);
        queryTicketByLineIdRequestBody.setShowSeq(showSeq);

        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).queryTicketByLineId(head, queryTicketByLineIdRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","指定时间点线路购票="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onQueryTicketsByBusLineIdSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryTicketsByBusLineIdFaild(e.getMessage());
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

    public interface Inter_QueryTicketsByBusLineId{
        public void onQueryTicketsByBusLineIdSucces(ArrayList<String> getinfo);
        public void onQueryTicketsByBusLineIdFaild(String resmsg);
    }
}
