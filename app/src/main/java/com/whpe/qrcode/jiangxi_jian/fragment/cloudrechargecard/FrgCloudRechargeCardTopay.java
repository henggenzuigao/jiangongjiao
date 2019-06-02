package com.whpe.qrcode.jiangxi_jian.fragment.cloudrechargecard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityCloudRechargeCard;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.PayUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.PayUnifyAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.AlipayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.UnionBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.WeichatBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypePrepayBean;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypeRechargeCardBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.MoneyGridRechargeCardAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.RechargeCardPaytypeLvAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2018/12/12.
 */

public class FrgCloudRechargeCardTopay extends Fragment implements PayUnifyAction.Inter_queryqruserinfo {
    private View content;
    private Context context;
    private ActivityCloudRechargeCard activity;
    private GridView gv_money;
    private MoneyGridRechargeCardAdapter moneyGridAdapter;
    private String rechargemoney;
    private Button btn_submit;
    private ListView lv_paytype;
    private RechargeCardPaytypeLvAdapter rechargeCardPaytypeLvAdapter;
    private ArrayList<PaytypeRechargeCardBean> paytypeRechargeCardBeans=new ArrayList<>();
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private EditText et_cardnoagain;
    private EditText et_cardno;
    private String cardno;
    private String cardnoagain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_cloudrechargecard_topay,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivityCloudRechargeCard) getActivity();
        loadQrcodeParamBean=activity.loadQrcodeParamBean;
        bindView();
        initView();
    }

    private void bindView() {
        gv_money = (GridView)content.findViewById(R.id.gvmoney);
        btn_submit = (Button)content.findViewById(R.id.btn_submit);
        lv_paytype = (ListView)content.findViewById(R.id.lv_paytype);
        et_cardnoagain = (EditText)content.findViewById(R.id.et_cardnoagain);
        et_cardno = (EditText)content.findViewById(R.id.et_cardno);
    }

    private void initView() {
        initGridMoney();
        initListViewPaytype();
        initSubmit();
    }

    private void initSubmit() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardno = et_cardno.getText().toString();
                cardnoagain = et_cardnoagain.getText().toString();
                if(TextUtils.isEmpty(cardno)||TextUtils.isEmpty(cardnoagain)){
                    ToastUtils.showToast(context,getString(R.string.cloudrecharge_toast_pleaseinput));
                    return;
                }
                if(!cardno.equals(cardnoagain)){
                    ToastUtils.showToast(context,getString(R.string.cloudrecharge_toast_pleasetwiceequals));
                    return;
                }
                if(cardno.length()<16){
                    ToastUtils.showToast(context,getString(R.string.cloudrecharge_toast_cardnoerror));
                    return;
                }
                if(TextUtils.isEmpty(rechargemoney)){
                    ToastUtils.showToast(context,getString(R.string.cloudrecharge_toast_pleaseselectmoney));
                    return;
                }
                requestForPayUnity();
            }
        });
    }

    //填充支付方式选择目录
    private void initListViewPaytype() {
        initPaytype();
        rechargeCardPaytypeLvAdapter = new RechargeCardPaytypeLvAdapter(context,paytypeRechargeCardBeans);
        lv_paytype.setAdapter(rechargeCardPaytypeLvAdapter);
        lv_paytype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                rechargeCardPaytypeLvAdapter.setPaytypePosition(i);
                rechargeCardPaytypeLvAdapter.notifyDataSetChanged();
            }
        });
    }

    //填充支付方式
    private void initPaytype() {
        List<LoadQrcodeParamBean.CityQrParamConfigBean.PayWayBean> payWayBeans=loadQrcodeParamBean.getCityQrParamConfig().getPayWay();
        for(int i=0;i<payWayBeans.size();i++){
            if(payWayBeans.get(i).getPayWayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAY)){
                PaytypeRechargeCardBean paytypeRechargeCardBean=new PaytypeRechargeCardBean();
                paytypeRechargeCardBean.setPayWayCode(payWayBeans.get(i).getPayWayCode());
                paytypeRechargeCardBean.setPayWayName(payWayBeans.get(i).getPayWayName());
                paytypeRechargeCardBeans.add(paytypeRechargeCardBean);
            }
        }
    }

    //填充金额选择目录
    private void initGridMoney() {
        moneyGridAdapter = new MoneyGridRechargeCardAdapter(context);
        gv_money.setAdapter(moneyGridAdapter);
        gv_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                moneyGridAdapter.notifyDataSetChanged();
                initRechargeMoney(i);
            }
        });
    }

    //填充实际发送金额数据
    private void initRechargeMoney(int i){
        String[] money_recharges=getResources().getStringArray(R.array.rechargecard_payunity_money);
        rechargemoney=money_recharges[i];
    }

    //请求下单
    private void requestForPayUnity() {
        activity.showCloudRechargeCardProgress();
        PayUnifyAction payUnifyAction=new PayUnifyAction(activity,this);
        Integer int_rechargemoney=Integer.parseInt(rechargemoney);
        payUnifyAction.sendAction(int_rechargemoney,GlobalConfig.PAYUNITY_TYPE_BUSCARDAMOUNT_TOPAY,cardno.substring(cardno.length()-16,cardno.length()),rechargeCardPaytypeLvAdapter.getPaytypeCode(),activity.sharePreferenceLogin.getLoginPhone(),GlobalConfig.PAYUNITY_TYPE_BUSCARD_CARDAREA_CPUNORMAL,GlobalConfig.PAYUNITY_TYPE_BUSCARD_READWAY_CLOUD,GlobalConfig.PAYUNITY_TYPE_BUSCARD_BALANCE_NORMAL,"");
    }

    //解析数据
    private void solveGWPayCallback(ArrayList<String> getinfo) {
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                activity.havepay=getString(R.string.cloudrecharge_havepay_yes);
                startToPay(getinfo);
            }else{
                activity.checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            activity.showExceptionAlertDialog();
        }
    }



    private void startToPay(ArrayList<String> getinfo) {
        if(rechargeCardPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION)){
            UnionBean unionBean=new UnionBean();
            unionBean= (UnionBean) JsonComomUtils.parseAllInfo(getinfo.get(2),unionBean);
            activity.merchantOderNo=unionBean.getMerchantOderNo();
            PayUtils.unionPay(activity,unionBean.getPayParam().getTn());
            return;
        }
        if(rechargeCardPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY)){
            AlipayBean alipayBean=new AlipayBean();
            alipayBean= (AlipayBean) JsonComomUtils.parseAllInfo(getinfo.get(2),alipayBean);
            activity.merchantOderNo=alipayBean.getMerchantOderNo();
            PayUtils.aliPay(activity,alipayBean.getPayParam().getOrderStr(),activity.aliHandler);
            return;
        }
        if(rechargeCardPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT)){
            WeichatBean weichatBean=new WeichatBean();
            weichatBean= (WeichatBean) JsonComomUtils.parseAllInfo(getinfo.get(2),weichatBean);
            PayUtils.weichatPay(activity,weichatBean);
            return;
        }
        activity.showExceptionAlertDialog(getString(R.string.app_function_notopen));
    }

    @Override
    public void onPayUnifySucces(ArrayList<String> getinfo) {
        activity.dissmissCloudRechargeCardProgress();
        solveGWPayCallback(getinfo);
    }

    @Override
    public void onPayUnifyFaild(String resmsg) {
        activity.dissmissCloudRechargeCardProgress();
        activity.showExceptionAlertDialog(resmsg);
    }
}
