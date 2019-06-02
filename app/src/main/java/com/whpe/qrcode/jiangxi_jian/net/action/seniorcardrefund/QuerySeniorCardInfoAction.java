package com.whpe.qrcode.jiangxi_jian.net.action.seniorcardrefund;

import android.app.Activity;


import com.tomyang.whpe.seniorscardrefund.Request;
import com.tomyang.whpe.seniorscardrefund.bean.QueryBean;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by yang on 2018/10/24.
 */

public class QuerySeniorCardInfoAction {
    public Inter_QuerySeniorcardinfo inter;
    public Activity activity;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();


    public QuerySeniorCardInfoAction(Activity context,Inter_QuerySeniorcardinfo inter_QuerySeniorcardinfo) {
        this.inter = inter_QuerySeniorcardinfo;
        activity=context;
    }

    public void sendAction(String seniorcardno,String idcard){
        final String st_seniorcardno=seniorcardno;
        final String st_idcard=idcard;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request.Companion.getInstance(GlobalConfig.SENIORCARD_REFUND_URL).queryRefundByCard(st_seniorcardno,st_idcard).subscribe(new Observer<QueryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QueryBean queryBean) {
                        final QueryBean bean=queryBean;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQuerySeniorcardinfoSucces(bean);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        final String e_error=e.getMessage();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onQuerySeniorcardinfoFaild(e_error);
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

    public interface Inter_QuerySeniorcardinfo{
        public void onQuerySeniorcardinfoSucces(QueryBean queryBean);
        public void onQuerySeniorcardinfoFaild(String resmag);
    }
}
