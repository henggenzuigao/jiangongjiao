package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.PayUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.PayUnifyAction;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryQrUserInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.QrcodeStatusBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.AlipayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.CcbBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.UnionBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.WeichatBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypePrepayBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.MoneyGridAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.PaypursePaytypeLvAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2018/9/20.
 */

public class ActivityPayPurse extends BackgroundTitleActivity implements PayUnifyAction.Inter_queryqruserinfo, QueryQrUserInfoAction.Inter_queryqruserinfo {

    private GridView gv_money;
    private MoneyGridAdapter moneyGridAdapter;
    private Float float_money;
    private String rechargemoney;
    private Button btn_submit;
    private ListView lv_paytype;
    private PaypursePaytypeLvAdapter paypursePaytypeLvAdapter;
    private TextView tv_balance;
    public LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private ArrayList<PaytypePrepayBean> paytypePrepayBeans=new ArrayList<>();
    private QrcodeStatusBean qrcodeStatusBean=new QrcodeStatusBean();
    private boolean btn_submit_flag=false;

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
        initPrePaytype();
    }

    //??????????????????????????????(???????????????????????????)
    private void initPrePaytype() {
        paytypePrepayBeans.clear();
        List<LoadQrcodeParamBean.CityQrParamConfigBean.PayWayBean> payWayBeans=loadQrcodeParamBean.getCityQrParamConfig().getPayWay();
        for(int i=0;i<payWayBeans.size();i++){
            if(payWayBeans.get(i).getPayWayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAY)){
                PaytypePrepayBean paytypePrepayBean=new PaytypePrepayBean();
                paytypePrepayBean.setPayWayCode(payWayBeans.get(i).getPayWayCode());
                paytypePrepayBean.setPayWayName(payWayBeans.get(i).getPayWayName());
                paytypePrepayBeans.add(paytypePrepayBean);
            }
        }
    }

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_paypurse);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.paypurse_title));
        setMyTitleColor(R.color.transparency);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_submit_flag){
                    return;
                }
                if(float_money==null){
                    ToastUtils.showToast(ActivityPayPurse.this,getString(R.string.paypurse_noselect_money_toast));
                }else {
                    requestForPayUnity();
                }
            }
        });
        initGridMoney();
        initListViewPaytype();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestQrUserInfo();
        btn_submit_flag=false;
        Log.e("YC","??????onstart");
    }

    //??????????????????
    private void requestQrUserInfo() {
        showProgress();
        QueryQrUserInfoAction queryQrUserInfoAction=new QueryQrUserInfoAction(this,this);
        queryQrUserInfoAction.sendAction(sharePreferenceLogin.getLoginPhone(),loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
    }

    //????????????
    private void requestForPayUnity() {
        showProgress();
        PayUnifyAction payUnifyAction=new PayUnifyAction(this,this);
        Integer int_rechargemoney=Integer.parseInt(rechargemoney);
        payUnifyAction.sendAction(int_rechargemoney,GlobalConfig.PAYUNITY_TYPE_BALANCE_TOPAY,qrcodeStatusBean.getQrCardNo(),paypursePaytypeLvAdapter.getPaytypeCode(),sharePreferenceLogin.getLoginPhone(),"","","","");
    }

    //??????????????????????????????
    private void initListViewPaytype() {
        paypursePaytypeLvAdapter = new PaypursePaytypeLvAdapter(this,paytypePrepayBeans);
        lv_paytype.setAdapter(paypursePaytypeLvAdapter);
        lv_paytype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                paypursePaytypeLvAdapter.setPaytypePosition(i);
                paypursePaytypeLvAdapter.notifyDataSetChanged();
            }
        });
    }

    //????????????????????????
    private void initGridMoney() {
        try {
            if(TextUtils.isEmpty(loadQrcodeParamBean.getCityQrParamConfig().getRechargeAmount())){
                Log.e("YC","???????????????????????????-?????????????????????");
                moneyGridAdapter = new MoneyGridAdapter(this);
                gv_money.setAdapter(moneyGridAdapter);
                gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        view.setSelected(true);
                        moneyGridAdapter.notifyDataSetChanged();
                        initRechargeMoney(i);
                        initSelectMoney(i);
                    }
                });
            }else {
                Log.e("YC","????????????????????????");
                final String[] net_money_select=loadQrcodeParamBean.getCityQrParamConfig().getRechargeAmount().split("&");
                moneyGridAdapter = new MoneyGridAdapter(this,net_money_select);
                gv_money.setAdapter(moneyGridAdapter);
                gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        view.setSelected(true);
                        moneyGridAdapter.notifyDataSetChanged();
                        initNetRechargeMoney(net_money_select[i]);
                        initNetSelectMoney(net_money_select[i]);
                    }
                });
            }
        }catch (Exception e){
            Log.e("YC","????????????????????????-?????????????????????");
            moneyGridAdapter = new MoneyGridAdapter(this);
            gv_money.setAdapter(moneyGridAdapter);
            gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    view.setSelected(true);
                    moneyGridAdapter.notifyDataSetChanged();
                    initRechargeMoney(i);
                    initSelectMoney(i);
                }
            });
        }

    }

    //??????????????????????????????(????????????)
    private void initNetSelectMoney(String i) {
        rechargemoney=i;
    }

    //??????????????????????????????(????????????)
    private void initNetRechargeMoney(String i) {
        String money=new BigDecimal(i).divide(new BigDecimal(100))
                .toString();
        float_money=Float.parseFloat(money);
        btn_submit.setText(getString(R.string.paypurse_btntext_default)+String.format("%.2f",float_money));
    }

    //??????????????????????????????
    private void initRechargeMoney(int i){
        String[] money_recharges=getResources().getStringArray(R.array.paypurse_payunity_money);
        rechargemoney=money_recharges[i];
    }
    //??????????????????????????????
    private void initSelectMoney(int i) {
        String[] money_selects=getResources().getStringArray(R.array.paypurse_money_select);
        float_money=Float.parseFloat(money_selects[i]);
        btn_submit.setText(String.format(getString(R.string.paypurse_btntext),float_money));
    }

    private void solveGWPayCallback(ArrayList<String> getinfo) {
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                startToPay(getinfo);
            }else if(rescode.equals(GlobalConfig.RESCODE_PAYUNITY_QRCARDNOTFIND)){
                showExceptionAlertDialog(getinfo.get(1));
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    //???????????????
    private Handler aliHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            requestQrUserInfo();
        };
    };


    private void startToPay(ArrayList<String> getinfo) {
        if(paypursePaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION)){
            UnionBean unionBean=new UnionBean();
            unionBean= (UnionBean) JsonComomUtils.parseAllInfo(getinfo.get(2),unionBean);
            PayUtils.unionPay(this,unionBean.getPayParam().getTn());
            return;
        }
        if(paypursePaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY)){
            AlipayBean alipayBean=new AlipayBean();
            alipayBean= (AlipayBean) JsonComomUtils.parseAllInfo(getinfo.get(2),alipayBean);
            PayUtils.aliPay(this,alipayBean.getPayParam().getOrderStr(),aliHandler);
            return;
        }
        if(paypursePaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT)){
            WeichatBean weichatBean=new WeichatBean();
            weichatBean= (WeichatBean) JsonComomUtils.parseAllInfo(getinfo.get(2),weichatBean);
            PayUtils.weichatPay(this,weichatBean);
            return;
        }
        showExceptionAlertDialog(getString(R.string.app_function_notopen));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("YC","????????????");
        requestQrUserInfo();
        btn_submit_flag=false;
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        gv_money = (GridView)findViewById(R.id.gvmoney);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        lv_paytype = (ListView)findViewById(R.id.lv_paytype);
        tv_balance = (TextView)findViewById(R.id.tv_balance);
    }

    @Override
    public void onPayUnifySucces(ArrayList<String> getinfo) {
        dissmissProgress();
        solveGWPayCallback(getinfo);
    }

    @Override
    public void onPayUnifyFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
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
