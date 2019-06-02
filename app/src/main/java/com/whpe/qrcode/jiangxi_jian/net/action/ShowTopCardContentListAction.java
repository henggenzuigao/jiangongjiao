package com.whpe.qrcode.jiangxi_jian.net.action;

import android.app.Activity;
import android.util.Log;

import com.tomyang.whpe.qrcode.QrcodeRequest;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListAckBody;
import com.tomyang.whpe.qrcode.bean.request.Head;
import com.tomyang.whpe.qrcode.bean.request.QueryNewsListRequestBody;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yang on 2019/1/2.
 */

public class ShowTopCardContentListAction {
    public Inter_ShowCardContentList inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    public ShowTopCardContentListAction(Activity context, Inter_ShowCardContentList inter_QueryNewsContent) {
        this.inter = inter_QueryNewsContent;
        activity=context;
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(((ParentActivity)activity).sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    public void sendAction(String pageChoose,String phoneNum,String spaceId){
        final Head head=new Head();
        head.setAppId(GlobalConfig.APPID);
        head.setAppVersion(((ParentActivity)activity).getLocalVersionName());
        head.setCityCode(GlobalConfig.CITYCODE);
        head.setUid("");
        head.setToken("");
        head.setCityQrParamVersion("");
        final QueryNewsListRequestBody queryNewsListRequestBody=new QueryNewsListRequestBody();
        queryNewsListRequestBody.setPageChoose(pageChoose);
        queryNewsListRequestBody.setPhoneNum(phoneNum);
        queryNewsListRequestBody.setSpaceId(spaceId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QrcodeRequest.Companion.getInstance(GlobalConfig.APP_GW_FUNCTION_URL).queryNewsList(head, queryNewsListRequestBody).subscribe(new Observer<QueryNewsListAckBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final QueryNewsListAckBody info) {
                        Log.e("YC","新闻列表获取="+info.getSpaceId()+info.getPageChoose()+info.getContentList());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onShowCardContentListSucces(info);
                            }
                        });
                    }

                    @Override
                    public void onError(final Throwable e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onShowCardContentListFaild(e.getMessage());
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

    public interface Inter_ShowCardContentList{
        public void onShowCardContentListSucces(QueryNewsListAckBody getinfo);
        public void onShowCardContentListFaild(String resmsg);
    }
}
