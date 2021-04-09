package com.whpe.qrcode.jiangxi_jian.activity;


import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund.FrgSeniorCardQueryBalance;
import com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund.FrgSeniorCardRefund;
import com.whpe.qrcode.jiangxi_jian.fragment.seniorcardrefund.FrgSeniorCardRefundSuccess;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;


/**
 * Created by yang on 2018/10/23.
 */

public class ActivitySeniorCardRefund extends NormalTitleActivity {

    private FrgSeniorCardQueryBalance frgSeniorCardQueryBalance;
    private FrgSeniorCardRefund frgSeniorCardRefund;
    private FrgSeniorCardRefundSuccess frgSeniorCardRefundSuccess;
    private String seniorcard,idcard,balance,name;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_seniorcardrefund);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        showQueryBalance();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
    }


    public void showQueryBalance(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgSeniorCardQueryBalance = new FrgSeniorCardQueryBalance();
        ft.replace(R.id.fl_content, frgSeniorCardQueryBalance);
        ft.commitAllowingStateLoss();
    }

    public void showRefund(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgSeniorCardRefund = new FrgSeniorCardRefund();
        ft.replace(R.id.fl_content, frgSeniorCardRefund);
        ft.commitAllowingStateLoss();
    }

    public void showRefundSuccess(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgSeniorCardRefundSuccess = new FrgSeniorCardRefundSuccess();
        ft.replace(R.id.fl_content, frgSeniorCardRefundSuccess);
        ft.commitAllowingStateLoss();
    }

    public void setSeniorrefundTitle(String title){
        setTitle(title);
    }

    public void setIDcard(String idcard1){
        this.idcard=idcard1;
    }

    public void setSeniorcard(String seniorcard1){
        this.seniorcard=seniorcard1;
    }

    public void setBalance(String balance1){
        this.balance=balance1;
    }

    public void setTheName(String name1){
        this.name=name1;
    }

    public String getSeniorcard() {
        return seniorcard;
    }

    public String getIdcard() {
        return idcard;
    }

    public String getBalance() {
        return balance;
    }

    public String getTheName() {
        return name;
    }

    public void showSeniorProgress(){
        showProgress();
    }

    public void dissmissSeniorProgress(){
        dissmissProgress();
    }
}
