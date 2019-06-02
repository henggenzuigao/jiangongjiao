package com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus;

import android.app.Activity;
import android.util.Log;

import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.request.GenerateTicketOrderInfoRequestBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.QueryUserTicketInfoRequestBody;
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

public class QueryUserTicketInfoAction {
    public Inter_QueryUserTicketInfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryUserTicketInfoAction(Activity context, Inter_QueryUserTicketInfo  interinter) {
        this.inter = interinter;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction( ){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid(((ParentActivity) activity).sharePreferenceLogin.getUid());
        head.setToken(((ParentActivity) activity).sharePreferenceLogin.getToken());
        head.setCityQrParamVersion(loadQrcodeParamBean.getCityQrParamConfig().getParamVersion());
        final QueryUserTicketInfoRequestBody queryUserTicketInfoRequestBody=new QueryUserTicketInfoRequestBody();
        queryUserTicketInfoRequestBody.setInterfaceVersion(GlobalConfig.CUSTOMBUS_INTERFACEVERSION);
        queryUserTicketInfoRequestBody.setCurrentPage("0");
        queryUserTicketInfoRequestBody.setPageNo(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).queryUserTicketInfo(head, queryUserTicketInfoRequestBody).subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final String info) {
                        final String getinfo=info;
                        Log.e("YC","查询定制公交票数="+info);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<String> infos= JsonComomUtils.parseJson(getinfo);
                                inter.onQueryUserTicketInfoSucces(infos);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryUserTicketInfoFaild(e.getMessage());
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

    public interface Inter_QueryUserTicketInfo {
        public void onQueryUserTicketInfoSucces(ArrayList<String> getinfo);
        public void onQueryUserTicketInfoFaild(String resmsg);
    }
}
