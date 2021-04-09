package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.fragment.cloudrechargecard.FrgCloudRechargeCardSuccess;
import com.whpe.qrcode.jiangxi_jian.fragment.cloudrechargecard.FrgCloudRechargeCardTopay;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryOrderPayAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.GetOrderPayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;

import java.util.ArrayList;

/**
 * Created by yang on 2018/12/11.
 */

public class ActivityCloudRechargeCard extends NormalTitleActivity implements QueryOrderPayAction.Inter_Queryorderpayinfo {
    private FrgCloudRechargeCardTopay frgCloudRechargeCardTopay;
    private FrgCloudRechargeCardSuccess frgCloudRechargeCardSuccess;
    public String frg_mark="";
    public String havepay="0";
    public LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    public String merchantOderNo="";


    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_cloudrechargecard);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.cloudrecharge_title));
        showTopay();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
    }

    public void showTopay(){
        frg_mark=getString(R.string.cloudrecharge_mark_frgCloudRechargeCardTopay);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgCloudRechargeCardTopay = new FrgCloudRechargeCardTopay();
        ft.replace(R.id.fl_content, frgCloudRechargeCardTopay);
        ft.commitAllowingStateLoss();
    }

    public void showSuccess(){
        frg_mark=getString(R.string.cloudrecharge_mark_frgCloudRechargeCardSuccess);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgCloudRechargeCardSuccess = new FrgCloudRechargeCardSuccess();
        ft.replace(R.id.fl_content, frgCloudRechargeCardSuccess);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(frg_mark.equals(getString(R.string.cloudrecharge_mark_frgCloudRechargeCardTopay))&&havepay.equals(getString(R.string.cloudrecharge_havepay_yes))){
            //requestOrderRecharge();
            finish();
        }
    }

    //支付宝回调
    public Handler aliHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if(frg_mark.equals(getString(R.string.cloudrecharge_mark_frgCloudRechargeCardTopay))){
                //requestOrderRecharge();
                finish();
            }
        };
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(frg_mark.equals(getString(R.string.cloudrecharge_mark_frgCloudRechargeCardTopay))){
            //requestOrderRecharge();
            finish();
        }
    }

    private void requestOrderRecharge() {
        if(!progressIsShow()){
            showProgress();
            QueryOrderPayAction queryOrderPayAction=new QueryOrderPayAction(this,this);
            queryOrderPayAction.sendAction(GlobalConfig.QUERYORDER_SELECTTYPE_GETWAY,GlobalConfig.QUERYORDER_SELECTPARAMTYPE_merchantOderNo,merchantOderNo);
        }
    }

    public void showCloudRechargeCardProgress(){
        showProgress();
    }

    public void dissmissCloudRechargeCardProgress(){
        dissmissProgress();
    }

    @Override
    public void onQueryorderpaySucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                GetOrderPayBean getOrderPayBean=new GetOrderPayBean();
                getOrderPayBean= (GetOrderPayBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getOrderPayBean);
                if(getOrderPayBean.getOrderList().size()==0){
                    ToastUtils.showToast(this,getString(R.string.cloudrecharge_toast_cloud_failed));
                    return;
                }
                if(getOrderPayBean.getOrderList().size()>1){
                    showExceptionAlertDialog();
                    return;
                }
                if(getOrderPayBean.getOrderList().get(0).getOrderStatus().equals(GlobalConfig.QUERYORDER_GETSTATUS_SUCCESS)){
                    showSuccess();
                }else {
                    ToastUtils.showToast(this,getString(R.string.cloudrecharge_toast_cloud_failed));
                }
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryorderpayFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
