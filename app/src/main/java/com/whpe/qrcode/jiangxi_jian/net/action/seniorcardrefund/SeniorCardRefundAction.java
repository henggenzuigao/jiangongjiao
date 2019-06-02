package com.whpe.qrcode.jiangxi_jian.net.action.seniorcardrefund;

import android.app.Activity;

import com.tomyang.whpe.seniorscardrefund.Request;
import com.tomyang.whpe.seniorscardrefund.bean.StatusAck;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by yang on 2018/10/24.
 */

public class SeniorCardRefundAction {
    public Inter_SeniorCardRefund inter;
    public Activity activity;

    public SeniorCardRefundAction(Activity context,Inter_SeniorCardRefund inter_SeniorCardRefund) {
        this.inter = inter_SeniorCardRefund;
        activity=context;
    }

    public void sendAction(String seniorcard,String bankno){
        final String st_bankno=bankno;
        final String st_seniorcard=seniorcard;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request.Companion.getInstance(GlobalConfig.SENIORCARD_REFUND_URL).refund(st_seniorcard,st_bankno).subscribe(new Observer<StatusAck>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StatusAck statusAck) {
                        final StatusAck bean=statusAck;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onSeniorCardRefundSucces(bean);
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        final String e_error=e.getMessage();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                inter.onSeniorCardRefundFaild(e_error);
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

    public interface Inter_SeniorCardRefund{
        public void onSeniorCardRefundSucces(StatusAck statusAck);
        public void onSeniorCardRefundFaild(String resmag);
    }
}
