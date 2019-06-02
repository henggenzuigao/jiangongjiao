package com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.tomyang.whpe.seniorscardrefund.bean.QueryBean;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivitySeniorCardRefund;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.seniorcardrefund.QuerySeniorCardInfoAction;


/**
 * Created by yang on 2018/10/23.
 */

public class FrgSeniorCardQueryBalance extends Fragment implements View.OnClickListener, QuerySeniorCardInfoAction.Inter_QuerySeniorcardinfo {
    private View content;
    private Context context;
    private ActivitySeniorCardRefund activity;
    private EditText et_idcard;
    private EditText et_seniorcard;
    private Button btn_submit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_seniorcardrefund_querybalance,container,false);
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
        activity.setSeniorrefundTitle(getString(R.string.seniorcardrefund_title_querybalance));
        btn_submit.setOnClickListener(this);
    }

    private void bindView() {
        et_idcard = (EditText) content.findViewById(R.id.et_idcard);
        et_seniorcard = (EditText) content.findViewById(R.id.et_seniorcard);
        btn_submit=(Button)content.findViewById(R.id.btn_submit);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_submit){
            submit();
        }
    }

    private void submit() {
        String st_idcard=et_idcard.getText().toString();
        String st_seniorcard=et_seniorcard.getText().toString();
        if(TextUtils.isEmpty(st_idcard)||TextUtils.isEmpty(st_seniorcard)){
            ToastUtils.showToast(context,getString(R.string.seniorcardrefund_querybalance_inputerror));
            return;
        }
        activity.showSeniorProgress();
        QuerySeniorCardInfoAction querySeniorCardInfoAction=new QuerySeniorCardInfoAction(getActivity(),this);
        querySeniorCardInfoAction.sendAction(st_seniorcard,st_idcard);

    }

    @Override
    public void onQuerySeniorcardinfoSucces(QueryBean queryBean) {
        activity.dissmissSeniorProgress();
        try {
            if(!queryBean.isSuccess()){
                ToastUtils.showToast(activity,queryBean.getDesc());
                return;
            }
            if(queryBean.isRefunded()){
                ToastUtils.showToast(activity,queryBean.getDesc());
                return;
            }
            activity.setIDcard(queryBean.getId());
            activity.setSeniorcard(queryBean.getPublishCardNo());
            activity.setBalance(queryBean.getRefundMoney()+"");
            activity.setTheName(queryBean.getName());
            activity.showRefund();
        }catch (Exception e){
            activity.showExceptionAlertDialog();
        }
    }

    @Override
    public void onQuerySeniorcardinfoFaild(String resmag) {
        activity.dissmissSeniorProgress();
        ToastUtils.showToast(activity,resmag);
    }
}
