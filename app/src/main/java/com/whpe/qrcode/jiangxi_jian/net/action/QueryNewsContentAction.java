package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;

import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsContentAckBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.QueryNewsContentRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2019/1/2.
 */

public class QueryNewsContentAction {
    public Inter_QueryNewsContent inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public QueryNewsContentAction(Activity context,Inter_QueryNewsContent inter_QueryNewsContent) {
        this.inter = inter_QueryNewsContent;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String contentid){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid("");
        head.setToken("");
        head.setCityQrParamVersion("");
        final QueryNewsContentRequestBody queryNewsContentRequestBody=new QueryNewsContentRequestBody();
        queryNewsContentRequestBody.setContentId(contentid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).queryNewsDetail(head, queryNewsContentRequestBody)
                .subscribe(new Observer<QueryNewsContentAckBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final QueryNewsContentAckBody info) {
                        Log.e("YC","新闻独条内容获取="+info.getContentType()+info.getContent());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryQueryNewsContentSucces(info);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQueryQueryNewsContentFaild(e.getMessage());
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

    public interface Inter_QueryNewsContent{
        public void onQueryQueryNewsContentSucces(QueryNewsContentAckBody getinfo);
        public void onQueryQueryNewsContentFaild(String resmsg);
    }
}
