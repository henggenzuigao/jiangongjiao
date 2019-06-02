package com.whpe.qrcode.jiangxi_jian.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryOrderPayAction;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryQrUserInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryQrcodeConsumeAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.GetOrcodeConsumeBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.GetOrderPayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.QrcodeStatusBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.ConsumrecordsBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.ConsumrecordsLvAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yang on 2018/9/30.
 */

public class ActivityConsumrecords extends NormalTitleActivity implements View.OnClickListener, QueryQrUserInfoAction.Inter_queryqruserinfo,QueryOrderPayAction.Inter_Queryorderpayinfo,QueryQrcodeConsumeAction.Inter_QrcodeConsumeinfo {

    private RelativeLayout rl_type_recharge;
    private RelativeLayout rl_type_consum;
    private ListView lv_consumrecords;
    private ArrayList<ConsumrecordsBean> consumrecordsBeans=new ArrayList<ConsumrecordsBean>();
    private ConsumrecordsLvAdapter consumrecordsLvAdapter;
    private View v_type_consume;
    private View v_type_recharge;
    private TextView tv_type_consume;
    private TextView tv_type_recharge;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private RelativeLayout rl_notrecord;

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
        setContentView(R.layout.activity_consumrecords);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.consumrecords_title));
        showConsum_recharge();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        initTypeSelect();
        initLvcomsum();
    }

    private void initLvcomsum() {
        lv_consumrecords = (ListView)findViewById(R.id.lv_records);
        consumrecordsLvAdapter = new ConsumrecordsLvAdapter(this,consumrecordsBeans,0);
        lv_consumrecords.setAdapter(consumrecordsLvAdapter);
    }

    private void initTypeSelect() {
        rl_type_recharge =(RelativeLayout) findViewById(R.id.rl_type_recharge);
        rl_type_consum = (RelativeLayout)findViewById(R.id.rl_type_consume);
        rl_type_consum.setOnClickListener(this);
        rl_type_recharge.setOnClickListener(this);
        v_type_consume = (View)findViewById(R.id.v_type_consume);
        v_type_recharge = (View)findViewById(R.id.v_type_recharge);
        tv_type_consume = (TextView) findViewById(R.id.tv_type_consume);
        tv_type_recharge = (TextView) findViewById(R.id.tv_type_recharge);
        rl_notrecord = (RelativeLayout)findViewById(R.id.rl_notrecord);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rl_type_consume){
            showConsum_Consum();
        }else if (view.getId()==R.id.rl_type_recharge){
            showConsum_recharge();
        }
    }

    private void showConsum_recharge() {
        tv_type_recharge.setTextColor(MyDrawableUtils.getColor(this,R.color.app_theme));
        v_type_recharge.setVisibility(View.VISIBLE);

        tv_type_consume.setTextColor(MyDrawableUtils.getColor(this,R.color.comon_text_black_normal));
        v_type_consume.setVisibility(View.GONE);
        requestOrderRecharge();
    }


    private void showConsum_Consum() {
        tv_type_recharge.setTextColor(MyDrawableUtils.getColor(this,R.color.comon_text_black_normal));
        v_type_recharge.setVisibility(View.GONE);

        tv_type_consume.setTextColor(MyDrawableUtils.getColor(this,R.color.app_theme));
        v_type_consume.setVisibility(View.VISIBLE);
        requestQruserinfo();
    }

    //刷新二维码消费记录
    private void refreshQrcodeConsume(JSONArray jsonArray) {
        consumrecordsBeans.clear();
        ArrayList<GetOrcodeConsumeBean> dataBeanList =new ArrayList<GetOrcodeConsumeBean>() ;
        for(int i=0;i<jsonArray.length();i++){
            GetOrcodeConsumeBean getOrcodeConsumeBean=new GetOrcodeConsumeBean();
            ConsumrecordsBean consumrecordsBean=new ConsumrecordsBean();
            try {
                getOrcodeConsumeBean= (GetOrcodeConsumeBean) JsonComomUtils.parseAllInfo(jsonArray.getString(i),getOrcodeConsumeBean);
            } catch (JSONException e) {
                e.printStackTrace();
                showExceptionAlertDialog();
            }
            if(getOrcodeConsumeBean.getOrderStatus().equals(GlobalConfig.QUERYQRAMTDATA_BUSDATA_SUCCESS)){
                consumrecordsBean.setDate(DateUtils.getNowtext(getOrcodeConsumeBean.getConsumeTime()));
                consumrecordsBean.setMoney("-"+new BigDecimal(getOrcodeConsumeBean.getAmount()).divide(new BigDecimal(100))
                        .toString());
                consumrecordsBean.setPaytype(getString(R.string.consumrecords_consumetype_common));
                consumrecordsBeans.add(consumrecordsBean);
                dataBeanList.add(getOrcodeConsumeBean);
            }
        }
        if(dataBeanList.size()==0){
            lv_consumrecords.setVisibility(View.GONE);
            rl_notrecord.setVisibility(View.VISIBLE);
            return;
        }

        lv_consumrecords.setVisibility(View.VISIBLE);
        rl_notrecord.setVisibility(View.GONE);
        consumrecordsLvAdapter.setconsunrecordtype(0);
        consumrecordsLvAdapter.notifyDataSetChanged();
    }


    //刷新钱包充值记录
    private void refreshPurseRecharge(GetOrderPayBean getOrderPayBean) {
        consumrecordsBeans.clear();
        List<GetOrderPayBean.OrderListBean> orderListBeans =getOrderPayBean.getOrderList();
        lv_consumrecords.setVisibility(View.VISIBLE);
        rl_notrecord.setVisibility(View.GONE);
        for(int i=0;i<orderListBeans.size();i++){
            ConsumrecordsBean consumrecordsBean=new ConsumrecordsBean();
            GetOrderPayBean.OrderListBean orderListBean=orderListBeans.get(i);
            if(orderListBean.getOrderStatus().equals(GlobalConfig.QUERYORDER_GETSTATUS_SUCCESS)){
                consumrecordsBean.setDate(DateUtils.getNowtext(orderListBean.getOrderGenerateTime()));
                consumrecordsBean.setMoney(new BigDecimal(orderListBean.getOrderAmt()).divide(new BigDecimal(100))
                        .toString());
                if(orderListBean.getSelectType().equals(GlobalConfig.QUERYORDER_SELECTTYPE_GETWAY)){
                    consumrecordsBean.setPaytype(getString(R.string.consumrecords_orderrechargetype_common));
                    consumrecordsBean.setPayPurpose(orderListBean.getPayPurpose());
                    consumrecordsBeans.add(consumrecordsBean);
                }
            }
        }
        if(consumrecordsBeans.size()==0){
            lv_consumrecords.setVisibility(View.GONE);
            rl_notrecord.setVisibility(View.VISIBLE);
            return;
        }
        consumrecordsLvAdapter.setconsunrecordtype(1);
        consumrecordsLvAdapter.notifyDataSetChanged();
    }

    private void requestOrderRecharge() {
        if(!progressIsShow()){
            showProgress();
            QueryOrderPayAction queryOrderPayAction=new QueryOrderPayAction(this,this);
            queryOrderPayAction.sendAction(GlobalConfig.QUERYORDER_SELECTTYPE_GETWAY,GlobalConfig.QUERYORDER_SELECTPARAMTYPE_UID,sharePreferenceLogin.getUid());
        }
    }

    private void requestQruserinfo() {
        if(!progressIsShow()){
            showProgress();
            QueryQrUserInfoAction queryQrUserInfoAction=new QueryQrUserInfoAction(this,this);
            queryQrUserInfoAction.sendAction(sharePreferenceLogin.getLoginPhone(),loadQrcodeParamBean.getCityQrParamConfig().getQrPayType());
        }
    }

    private void requestQrcodeConsume(QrcodeStatusBean qrcodeStatusBean){
        QueryQrcodeConsumeAction queryQrcodeConsumeAction=new QueryQrcodeConsumeAction(this,this);
        queryQrcodeConsumeAction.sendAction(sharePreferenceLogin.getLoginPhone(),qrcodeStatusBean.getPlatformUserId(),qrcodeStatusBean.getQrCardNo(),GlobalConfig.QUERYQRAMTDATA_SOURCETYPE_BUSDATA);
    }

    @Override
    public void onQueryqruserinfoSucces(ArrayList<String> getinfo) {
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                QrcodeStatusBean qrcodeStatusBean=new QrcodeStatusBean();
                qrcodeStatusBean= (QrcodeStatusBean) JsonComomUtils.parseAllInfo(getinfo.get(2),qrcodeStatusBean);
                requestQrcodeConsume(qrcodeStatusBean);
            }else {
                dissmissProgress();
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            dissmissProgress();
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryqruserinfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }

    @Override
    public void onQueryorderpaySucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                GetOrderPayBean getOrderPayBean=new GetOrderPayBean();
                getOrderPayBean= (GetOrderPayBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getOrderPayBean);
                refreshPurseRecharge(getOrderPayBean);
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

    @Override
    public void onQueryQrcodeConsumeSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                try {
                    JSONObject jsonObject=new JSONObject(getinfo.get(2));
                    JSONArray jsonArray=new JSONArray(jsonObject.getString("busData"));
                    refreshQrcodeConsume(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryQrcodeConsumeFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
