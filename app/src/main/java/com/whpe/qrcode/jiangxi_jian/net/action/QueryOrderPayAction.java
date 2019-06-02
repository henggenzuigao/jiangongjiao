package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;


import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.OrderQueryRequestBody;
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

public class QueryOrderPayAction {
    public Inter_Queryorderpayinfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryOrderPayAction(Activity context,Inter_Queryorderpayinfo inter_querypaytype) {
        this.inter = inter_querypaytype;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String selecttype,String selectParamType,String selectParam){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final OrderQueryRequestBody orderQueryRequestBody=new OrderQueryRequestBody();
        orderQueryRequestBody.setSelectType(selecttype);
        orderQueryRequestBody.setSelectPramType(selectParamType);
        orderQueryRequestBody.setSelectPram(selectParam);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).queryOder(head, orderQueryRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","用户网关订单查询="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onQueryorderpaySucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryorderpayFaild(e.getMessage());
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

    public interface Inter_Queryorderpayinfo{
        public void onQueryorderpaySucces(ArrayList<String> getinfo);
        public void onQueryorderpayFaild(String resmsg);
    }
}
