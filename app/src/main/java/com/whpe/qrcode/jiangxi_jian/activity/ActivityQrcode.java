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

    //????????????????????????????????????????????????
    public void requstForQrcodeUserInfo() {
        showProgress();
        queryQrUserInfoAction = new QueryQrUserInfoAction(this,this);
        queryQrUserInfoAction.sendAction(sharePreferenceLogin.getLoginPhone(),loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
    }

    //???????????????????????????
    public void requestForQrcode() {
        if(!progressIsShow()){
            showProgress();
        }
        InitQrcodeAction initQrcodeAction=new InitQrcodeAction(this,this);
        initQrcodeAction.sendAction(sharePreferenceLogin.getLoginPhone(),qrcodeStatusBean.getPlatformUserId(),qrcodeStatusBean.getQrCardNo(),paytypeLaterPayCode);
    }


    //??????????????????
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

    //??????????????????????????????(??????????????????app????????????????????????????????????????????????????????????????????????)
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

    //????????????fragment?????????????????????????????????
    public String getQrcodedata(){
        String qrdata=initQrcodeBean.getQrData();
        String decode_qrdata=new String(Base64.decode(qrdata));
        return decode_qrdata;
    }

    //???????????????
    private void judgePrePay(){
        //????????????????????????????????????????????????????????????
        if(qrcodeStatusBean.getBindWay().size()==0){
            dissmissProgress();
            showFrg(GlobalConfig.QRCODE_TYPE_NOTBANDPAYTYPE,qrcodeStatusBean.getQrCardNo());
            return;
        }
        initPaytypeLaterpay();
        //???????????????????????????????????????????????????????????????????????????(??????????????????????????????????????????????????????????????????)
        if(qrcodeStatusBean.getDeposit()<loadQrcodeParamBean.getCityQrParamConfig().getNeedDeposit()){
            dissmissProgress();
            showExceptionAlertDialog();
            //showFrg(GlobalConfig.QRCODE_TYPE_DEPOSIT,qrcodeStatusBean.getQrCardNo());
            return;
        }
        //????????????????????????????????????????????????????????????
        if(qrcodeStatusBean.getBalance()<loadQrcodeParamBean.getCityQrParamConfig().getAllowLowestAmt()){
            dissmissProgress();
            showFrg(GlobalConfig.QRCODE_TYPE_BALANCECANTUSE,qrcodeStatusBean.getQrCardNo());
            return;
        }
        //???????????????????????????????????????????????????????????????????????????(??????????????????????????????????????????????????????????????????)
        if(qrcodeStatusBean.getOweAmt()>loadQrcodeParamBean.getCityQrParamConfig().getAllowOweAmt()){
            dissmissProgress();
            showExceptionAlertDialog();
            //showFrg(GlobalConfig.QRCODE_TYPE_ARREAR,qrcodeStatusBean.getQrCardNo());
            return;
        }
        requestForQrcode();
    }

    //???????????????
    private void judgeLaterPay(){
        showExceptionAlertDialog(getString(R.string.activity_qrcode_function_notopen));
    }

    //???????????????
    private void judgeAllPay(){
        showExceptionAlertDialog(getString(R.string.activity_qrcode_function_notopen));
    }


    //?????????????????????????????????
    @Override
    public void onQueryqruserinfoSucces(ArrayList<String> getinfo) {
        try{
            String rescode=getinfo.get(0);
            //rescode??????01???02??????????????????????????????????????????
            if(rescode.equals(GlobalConfig.RESCODE_NOTOPENQRCARD)){
                dissmissProgress();
                showFrg(GlobalConfig.QRCODE_TYPE_NOTOPEN,null);
                return;
            }
            //rescode???01,?????????????????????
            if(rescode.equals(GlobalConfig.QRCODESTATUS_SUCCESS)){
                qrcodeStatusBean = (QrcodeStatusBean) JsonComomUtils.parseAllInfo(getinfo.get(2),qrcodeStatusBean);

                //QrCardStatus??????01??????????????????????????????
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

    //?????????????????????????????????
    @Override
    public void onQueryqruserinfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }


    //?????????????????????????????????
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

    //?????????????????????????????????
    @Override
    public void onInitqrcodeFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }


    /**
     * ???fragment???????????????
     */
    public void showQrPrpgress(){
        showProgress();
    }

    public void dissmissQrProgress(){
        dissmissProgress();
    }


}
