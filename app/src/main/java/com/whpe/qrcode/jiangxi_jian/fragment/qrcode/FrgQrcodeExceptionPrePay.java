package com.whpe.qrcode.jiangxi_jian.fragment.qrcode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMypurse;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityQrcode;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.ApplyForQrCardAction;
import com.whpe.qrcode.jiangxi_jian.net.action.PayUnifyAction;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yang on 2018/10/4.
 * 专门用于预付费的异常fragment
 */

public class FrgQrcodeExceptionPrePay extends Fragment implements View.OnClickListener, PayUnifyAction.Inter_queryqruserinfo, ApplyForQrCardAction.Inter_ApplyforQrcard {
    private View content;
    private Context context;
    private ActivityQrcode activity;
    private TextView btn_submit;
    private ImageView iv_qrcode_exception;
    private TextView tv_exception_info;
    private int qrcode_exception_type;
    private String cardno;
    private TextView tv_qrcode_cardnum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_qrcode_exception_prepay,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivityQrcode) getActivity();
        bindView();
        initView();
    }

    //
    private void initView() {
        Bundle bundle=getArguments();
        qrcode_exception_type = bundle.getInt(GlobalConfig.QRCODE_TYPE_KEY);
        cardno = bundle.getString(GlobalConfig.QRCODE_CARD_NO_KEY);
        if(!TextUtils.isEmpty(cardno)){
            tv_qrcode_cardnum.setText(getString(R.string.frg_qrcode_cardnum)+cardno);
        }
        if(qrcode_exception_type ==GlobalConfig.QRCODE_TYPE_NOTOPEN){
            btn_submit.setText(getString(R.string.frg_qrcodeexception_buttoninfo_notopen));
            tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_notopen));
            iv_qrcode_exception.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.frg_notopen_qrcode_prompt));
        }else if(qrcode_exception_type ==GlobalConfig.QRCODE_TYPE_NOTBANDPAYTYPE){
            btn_submit.setText(getString(R.string.frg_qrcodeexception_buttoninfo_notbindpaytype));
            tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_notbindpaytype));
            iv_qrcode_exception.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.frg_qrcode_exception));
        }else if(qrcode_exception_type ==GlobalConfig.QRCODE_TYPE_DEPOSIT){
            btn_submit.setText(getString(R.string.frg_qrcodeexception_buttoninfo_deposit));
            tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_deposit));
            iv_qrcode_exception.setImageDrawable(MyDrawableUtils.getDrawble(context,R.drawable.frg_qrcode_exception));
        }else if(qrcode_exception_type ==GlobalConfig.QRCODE_TYPE_BALANCECANTUSE) {
            btn_submit.setText(getString(R.string.frg_qrcodeexception_buttoninfo_balancecantuse));
            String allowlowestamt=new BigDecimal(activity.loadQrcodeParamBean.getCityQrParamConfig().getAllowLowestAmt()).divide(new BigDecimal(100))
                    .toString();
            tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_balancecantuse)+allowlowestamt+"元");
            //tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_balancecantuse));
            iv_qrcode_exception.setImageDrawable(MyDrawableUtils.getDrawble(context, R.drawable.frg_qrcode_exception));
        }else if(qrcode_exception_type ==GlobalConfig.QRCODE_TYPE_ARREAR){
            btn_submit.setText(getString(R.string.frg_qrcodeexception_buttoninfo_arrear));
            tv_exception_info.setText(getString(R.string.frg_qrcodeexception_textinfo_arrear));
            iv_qrcode_exception.setImageDrawable(MyDrawableUtils.getDrawble(context, R.drawable.frg_qrcode_exception));
        }
    }

    private void bindView() {
        btn_submit = (TextView)content.findViewById(R.id.btn_submit);
        iv_qrcode_exception = (ImageView)content.findViewById(R.id.iv_qrcode_exception);
        tv_exception_info = (TextView)content.findViewById(R.id.tv_exception_info);
        tv_qrcode_cardnum = (TextView)content.findViewById(R.id.tv_qrcode_cardnum);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_submit){
            if(qrcode_exception_type==GlobalConfig.QRCODE_TYPE_BALANCECANTUSE){
                activity.transAty(ActivityMypurse.class);
                return;
            }
            if(qrcode_exception_type==GlobalConfig.QRCODE_TYPE_ARREAR){
                activity.showExceptionAlertDialog(getString(R.string.app_function_notopen));
            }
            if (qrcode_exception_type == GlobalConfig.QRCODE_TYPE_DEPOSIT) {
                activity.showExceptionAlertDialog(getString(R.string.app_function_notopen));
                /*activity.showQrPrpgress();
                PayUnifyAction payUnifyAction=new PayUnifyAction(activity,this);*/
                return;
            }
            if(qrcode_exception_type==GlobalConfig.QRCODE_TYPE_NOTOPEN){
                activity.showQrPrpgress();
                ApplyForQrCardAction applyForQrCardAction=new ApplyForQrCardAction(activity,this);
                applyForQrCardAction.sendAction(activity.sharePreferenceLogin.getLoginPhone(),activity.loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
                return;
            }
            if(qrcode_exception_type==GlobalConfig.QRCODE_TYPE_NOTBANDPAYTYPE){
                activity.showExceptionAlertDialog(getString(R.string.app_function_notopen));
                return;
            }
        }
    }


    @Override
    public void onApplyforQrcardSucces(ArrayList<String> getinfo) {
        activity.dissmissQrProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                activity.showAlertDialog(getString(R.string.frg_qrcodeexception_opensuccess), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.requstForQrcodeUserInfo();
                    }
                });
                return;
            }
            if(rescode.equals(GlobalConfig.RESCODE_APPLYQRCARD_FAILD)){
                activity.showAlertDialog(getString(R.string.frg_qrcodeexception_openfaild),null);
                return;
            }
            if(rescode.equals(GlobalConfig.RESCODE_SYSTEMERROR)){
                activity.showExceptionAlertDialog(getinfo.get(1));
            }else {
                activity.checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            activity.showExceptionAlertDialog();
        }

    }

    @Override
    public void onApplyforQrcardFaild( String resmag) {
        activity.dissmissQrProgress();
        activity.showExceptionAlertDialog(getString(R.string.frg_qrcodeexception_openfaild));
    }

    @Override
    public void onPayUnifySucces(ArrayList<String> getinfo) {

    }

    @Override
    public void onPayUnifyFaild( String resmsg) {

    }
}
