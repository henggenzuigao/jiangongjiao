package com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivitySeniorCardRefund;


/**
 * Created by yang on 2018/10/23.
 */

public class FrgSeniorCardRefundSuccess extends Fragment implements View.OnClickListener{
    private View content;
    private Context context;
    private ActivitySeniorCardRefund activity;
    private Button btn_submit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_seniorcardrefund_refundsuccess,container,false);
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
        activity.setSeniorrefundTitle(getString(R.string.seniorcardrefund_title_refundsuccess));
        btn_submit.setOnClickListener(this);
    }

    private void bindView() {
        btn_submit = (Button)content.findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_submit){
            submit();
        }
    }

    private void submit() {
        activity.finish();
    }
}
