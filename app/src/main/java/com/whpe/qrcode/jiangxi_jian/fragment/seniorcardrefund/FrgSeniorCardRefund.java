package com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tomyang.whpe.seniorscardrefund.bean.StatusAck;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivitySeniorCardRefund;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.seniorcardrefund.SeniorCardRefundAction;

import java.math.BigDecimal;

/**
 * Created by yang on 2018/10/23.
 */

public class FrgSeniorCardRefund extends Fragment implements View.OnClickListener, SeniorCardRefundAction.Inter_SeniorCardRefund {
    private View content;
    private Context context;
    private ActivitySeniorCardRefund activity;
    private TextView tv_seniorcardno;
    private TextView tv_balance;
    private Button btn_submit;
    private EditText et_bankcard;
    private EditText et_bankcard_again;
    private TextView tv_name;
    private TextView tv_idcard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_seniorcardrefund_refund,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivitySeniorCardRefund) getActivity();
        bindView();
        initView();
    }

    private void initView() {
        activity.setSeniorrefundTitle(getString(R.string.seniorcardrefund_title_refund));
        String balance=new BigDecimal(activity.getBalance()).divide(new BigDecimal(100))
                .toString();
        tv_name.setText(activity.getTheName());
        tv_idcard.setText(activity.getIdcard());
        tv_balance.setText(String.format(getString(R.string.seniorcardrefund_refund_balance),balance));
        tv_seniorcardno.setText(String.format(getString(R.string.seniorcardrefund_refund_seniorcard),activity.getSeniorcard()));
        btn_submit.setOnClickListener(this);
    }

    private void bindView() {
        tv_seniorcardno = (TextView)content.findViewById(R.id.tv_seniorcardno);
        tv_balance = (TextView)content.findViewById(R.id.tv_balance);
        tv_name = (TextView)content.findViewById(R.id.tv_name);
        tv_idcard = (TextView)content.findViewById(R.id.tv_idcardno);
        btn_submit = (Button)content.findViewById(R.id.btn_submit);
        et_bankcard = (EditText)content.findViewById(R.id.et_bankcard);
        et_bankcard_again = (EditText)content.findViewById(R.id.et_bankcard_again);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_submit){
            submit();
        }
    }

    private void submit() {
        String st_bankcard=et_bankcard.getText().toString();
        String st_bankcard_again=et_bankcard_again.getText().toString();
        if(TextUtils.isEmpty(st_bankcard)||TextUtils.isEmpty(st_bankcard_again)){
            ToastUtils.showToast(context,getString(R.string.seniorcardrefund_refund_inputnull));
            return;
        }
        if(!st_bankcard.equals(st_bankcard_again)){
            ToastUtils.showToast(context,getString(R.string.seniorcardrefund_refund_inputerror));
            return;
        }
        activity.showSeniorProgress();
        SeniorCardRefundAction seniorCardRefundAction=new SeniorCardRefundAction(activity,this);
        seniorCardRefundAction.sendAction(activity.getSeniorcard(),st_bankcard);
    }

    @Override
    public void onSeniorCardRefundSucces(StatusAck statusAck) {
        activity.dissmissSeniorProgress();
        try {
            if(statusAck.getSuccess()){
                activity.showRefundSuccess();
            }else {
                ToastUtils.showToast(activity,statusAck.getMessage());
            }
        }catch (Exception e){
            activity.showExceptionAlertDialog();
        }

    }

    @Override
    public void onSeniorCardRefundFaild(String resmag) {
        activity.dissmissSeniorProgress();
        ToastUtils.showToast(activity,resmag);
    }
}
