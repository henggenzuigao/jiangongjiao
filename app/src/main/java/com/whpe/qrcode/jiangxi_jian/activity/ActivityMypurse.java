package com.whpe.qrcode.jiangxi_jian.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.BigUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryQrUserInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.QrcodeStatusBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ActivityMypurse extends BackgroundTitleActivity implements QueryQrUserInfoAction.Inter_queryqruserinfo {

    private Button btn_topaypurse;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private TextView tv_balance;
    private QrcodeStatusBean qrcodeStatusBean=new QrcodeStatusBean();


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
        setContentView(R.layout.activity_mypurse);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setMyTitleColor(R.color.transparency);
        setTitle(getString(R.string.mypurse_title));

        btn_topaypurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Bundle bundle=new Bundle();
                bundle.putString(GlobalConfig.INTENT_MONEY_KEY,tv_balance.getText().toString());*/
                transAty(ActivityPayPurse.class);
            }
        });
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_topaypurse = (Button)findViewById(R.id.btn_topaypurse);
        tv_balance = (TextView)findViewById(R.id.tv_balance);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(progressIsShow()){
            return;
        }
        showProgress();
        QueryQrUserInfoAction queryQrUserInfoAction=new QueryQrUserInfoAction(this,this);
        queryQrUserInfoAction.sendAction(sharePreferenceLogin.getLoginPhone(),loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
    }

    @Override
    public void onQueryqruserinfoSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                qrcodeStatusBean= (QrcodeStatusBean) JsonComomUtils.parseAllInfo(getinfo.get(2),qrcodeStatusBean);
                String balance=new BigDecimal(qrcodeStatusBean.getBalance()).divide(new BigDecimal(100))
                        .toString();
                //tv_balance.setText(balance);
                tv_balance.setText(String.format("%.2f",Double.parseDouble(balance)));
            }else {
                if(rescode.equals(GlobalConfig.RESCODE_NOTOPENQRCARD)){
                    toQrcodeActivity(getinfo);
                    return;
                }
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }

    }

    @Override
    public void onQueryqruserinfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
