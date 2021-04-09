package com.whpe.qrcode.jiangxi_jian.activity;

import android.os.Bundle;
import android.view.WindowManager;


import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.BigUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.fragment.qrcode.FrgQrcodeExceptionPrePay;
import com.whpe.qrcode.jiangxi_jian.fragment.qrcode.FrgQrcodeshowPrePay;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.InitQrcodeAction;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryQrUserInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.InitQrcodeBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.QrcodeStatusBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypeLaterPayBean;
import com.whpe.qrcode.jiangxi_jian.utils.Base64;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2018/10/3.
 */

public class ActivityQrcode extends BackgroundTitleActivity implements QueryQrUserInfoAction.Inter_queryqruserinfo, InitQrcodeAction.Inter_initqrcode {
    private FrgQrcodeshowPrePay frgQrcodeshowPrePay;
    private FrgQrcodeExceptionPrePay frgQrcodeExceptionPrePay;
    private QueryQrUserInfoAction queryQrUserInfoAction;
    public LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    public String paytypeLaterPayCode;
    public QrcodeStatusBean qrcodeStatusBean=new QrcodeStatusBean();
    public ArrayList<PaytypeLaterPayBean> paytypeLaterPayBeans=new ArrayList<PaytypeLaterPayBean>();
    private InitQrcodeBean initQrcodeBean;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activtiy_qrcode);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.activtiy_qrcode_title));
        setMyTitleColor(R.color.transparency);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BigUtils.setLight(this,255);
        requstForQrcodeUserInfo();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
    }

    //请求二维码用户信息以查看各种信息
    public void requstForQrcodeUserInfo() {
        showProgress();
        queryQrUserInfoAction = new QueryQrUserInfoAction(this,this);
        queryQrUserInfoAction.sendAction(sharePreferenceLogin.getLoginPhone(),loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
    }

    //请求获取二维码数据
    public void requestForQrcode() {
        if(!progressIsShow()){
            showProgress();
        }
        InitQrcodeAction initQrcodeAction=new InitQrcodeAction(this,this);
        initQrcodeAction.sendAction(sharePreferenceLogin.getLoginPhone(),qrcodeStatusBean.getPlatformUserId(),qrcodeStatusBean.getQrCardNo(),paytypeLaterPayCode);
    }


    //显示各种页面
    public void showFrg(int frg_type,String cardno){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(frg_type== GlobalConfig.QRCODE_TYPE_QRCODESHOW){
            frgQrcodeshowPrePay = new FrgQrcodeshowPrePay();
            Bundle bundle=new Bundle();
            bundle.putInt(GlobalConfig.QRCODE_TYPE_KEY,frg_type);
            bundle.putString(GlobalConfig.QRCODE_CARD_NO_KEY,cardno);
            frgQrcodeshowPrePay.setArguments(bundle);
            ft.replace(R.id.fl_qrcode_content, frgQrcodeshowPrePay);
        }else {
            frgQrcodeExceptionPrePay = new FrgQrcodeExceptionPrePay();
            Bundle bundle=new Bundle();
            bundle.putInt(GlobalConfig.QRCODE_TYPE_KEY,frg_type);
            bundle.putString(GlobalConfig.QRCODE_CARD_NO_KEY,cardno);
            frgQrcodeExceptionPrePay.setArguments(bundle);
            ft.replace(R.id.fl_qrcode_content, frgQrcodeExceptionPrePay);
        }
        ft.commitAllowingStateLoss();
    }

    //填充后付费的支付方式(不管是预付费app还是后付费，都会有，预付费钱包为唯一的后付费模式)
    private void initPaytypeLaterpay() {
        paytypeLaterPayBeans.clear();
        List<LoadQrcodeParamBean.CityQrParamConfigBean.PayWayBean> payWayBeans=loadQrcodeParamBean.getCityQrParamConfig().getPayWay();
        for(int i=0;i<payWayBeans.size();i++){
            if(payWayBeans.get(i).getPayWayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_LATERPAY)){
                PaytypeLaterPayBean paytypeLaterPayBean=new PaytypeLaterPayBean();
                paytypeLaterPayBean.setPayWayCode(payWayBeans.get(i).getPayWayCode());
                paytypeLaterPayBean.setPayWayName(payWayBeans.get(i).getPayWayName());
                paytypeLaterPayBeans.add(paytypeLaterPayBean);
            }
        }
        paytypeLaterPayCode=paytypeLaterPayBeans.get(0).getPayWayCode();
    }

    //为二维码fragment提供获取二维码数据入口
    public String getQrcodedata(){
        String qrdata=initQrcodeBean.getQrData();
        String decode_qrdata=new String(Base64.decode(qrdata));
        return decode_qrdata;
    }

    //预付费情况
    private void judgePrePay(){
        //绑定支付方式为空，显示请绑定支付方式页面
        if(qrcodeStatusBean.getBindWay().size()==0){
            dissmissProgress();
            showFrg(GlobalConfig.QRCODE_TYPE_NOTBANDPAYTYPE,qrcodeStatusBean.getQrCardNo());
            return;
        }
        initPaytypeLaterpay();
        //用户的押金数额小于系统需求押金数额，显示交押金页面(当前预付费不存在这种情况，因此直接报异常退出)
        if(qrcodeStatusBean.getDeposit()<loadQrcodeParamBean.getCityQrParamConfig().getNeedDeposit()){
            dissmissProgress();
            showExceptionAlertDialog();
            //showFrg(GlobalConfig.QRCODE_TYPE_DEPOSIT,qrcodeStatusBean.getQrCardNo());
            return;
        }
        //用户余额不足最低拉码额度，显示请充值页面
        if(qrcodeStatusBean.getBalance()<loadQrcodeParamBean.getCityQrParamConfig().getAllowLowestAmt()){
            dissmissProgress();
            showFrg(GlobalConfig.QRCODE_TYPE_BALANCECANTUSE,qrcodeStatusBean.getQrCardNo());
            return;
        }
        //用户欠费金额大于了允许欠费额度，显示请欠费补缴页面(当前预付费不存在这种情况，因此直接报异常退出)
        if(qrcodeStatusBean.getOweAmt()>loadQrcodeParamBean.getCityQrParamConfig().getAllowOweAmt()){
            dissmissProgress();
            showExceptionAlertDialog();
            //showFrg(GlobalConfig.QRCODE_TYPE_ARREAR,qrcodeStatusBean.getQrCardNo());
            return;
        }
        requestForQrcode();
    }

    //后付费情况
    private void judgeLaterPay(){
        showExceptionAlertDialog(getString(R.string.activity_qrcode_function_notopen));
    }

    //全付费情况
    private void judgeAllPay(){
        showExceptionAlertDialog(getString(R.string.activity_qrcode_function_notopen));
    }


    //请求二维码用户信息成功
    @Override
    public void onQueryqruserinfoSucces(ArrayList<String> getinfo) {
        try{
            String rescode=getinfo.get(0);
            //rescode不为01为02，显示请开通二维码电子卡页面
            if(rescode.equals(GlobalConfig.RESCODE_NOTOPENQRCARD)){
                dissmissProgress();
                showFrg(GlobalConfig.QRCODE_TYPE_NOTOPEN,null);
                return;
            }
            //rescode为01,进行下一步处理
            if(rescode.equals(GlobalConfig.QRCODESTATUS_SUCCESS)){
                qrcodeStatusBean = (QrcodeStatusBean) JsonComomUtils.parseAllInfo(getinfo.get(2),qrcodeStatusBean);

                //QrCardStatus不为01，显示电子卡状态异常
                if(!qrcodeStatusBean.getQrCardStatus().equals(GlobalConfig.QRCODE_CARD_ISOPEN)){
                    dissmissProgress();
                    showExceptionAlertDialog(getString(R.string.activity_qrcode_qrcard_exception));
                    return;
                }
                if(loadQrcodeParamBean.getCityQrParamConfig().getQrPayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAY)){
                    judgePrePay();
                    return;
                }
                if(loadQrcodeParamBean.getCityQrParamConfig().getQrPayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_LATERPAY)){
                    dissmissQrProgress();
                    judgeLaterPay();
                    return;
                }
                if(loadQrcodeParamBean.getCityQrParamConfig().getQrPayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_ALL)){
                    dissmissQrProgress();
                    judgeAllPay();
                    return;
                }
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    //请求二维码用户信息异常
    @Override
    public void onQueryqruserinfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }


    //请求二维码数据返回正常
    @Override
    public void onInitqrcodeSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            initQrcodeBean = new InitQrcodeBean();
            initQrcodeBean = (InitQrcodeBean) JsonComomUtils.parseAllInfo(getinfo.get(2), initQrcodeBean);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                showFrg(GlobalConfig.QRCODE_TYPE_QRCODESHOW,qrcodeStatusBean.getQrCardNo());
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    //请求二维码数据返回异常
    @Override
    public void onInitqrcodeFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }


    /**
     * 给fragment做显示弹框
     */
    public void showQrPrpgress(){
        showProgress();
    }

    public void dissmissQrProgress(){
        dissmissProgress();
    }


}
