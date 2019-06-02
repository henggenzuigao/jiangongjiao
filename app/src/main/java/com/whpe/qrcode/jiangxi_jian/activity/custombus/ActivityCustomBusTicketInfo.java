package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.QueryTicketByTicketIdAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetQueryTicketByTicketIdBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetQueryUserTicketInfoBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yang on 2019/1/13.
 */

public class ActivityCustomBusTicketInfo extends NormalTitleActivity implements QueryTicketByTicketIdAction.Inter_QueryTicketByTicketId {
    private TextView tv_frequency,tv_date,tv_ticketprice,tv_huimincardprice,tv_paymoney,
            tv_layerandseat,tv_ticketinfosite,tv_startsite,tv_pointsite;
    private String ticketid;
    private GetQueryTicketByTicketIdBean getQueryTicketByTicketIdBean=new GetQueryTicketByTicketIdBean();

    @Override
    protected void afterLayout() {
        super.afterLayout();
        requestForTicketByTicketId();
    }

    private void requestForTicketByTicketId() {
        showProgress();
        QueryTicketByTicketIdAction queryTicketByTicketIdAction=new QueryTicketByTicketIdAction(this,this);
        queryTicketByTicketIdAction.sendAction(ticketid);
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_ticketinfo);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombusticketinfo_title));
        getMyInfoIntent();
    }

    private void initOtherUI() {
        tv_frequency.setText(getString(R.string.custombusticketinfo_frequency)+ DateUtils.HHmmToShowHHmm(getQueryTicketByTicketIdBean.getChooseTime()));
        tv_date.setText(getString(R.string.custombusticketinfo_date)+DateUtils.yyyyMMddToShowyyyyMMdd(getQueryTicketByTicketIdBean.getChooseDate()));
        String ticketprice=new BigDecimal(getQueryTicketByTicketIdBean.getBusPrice()).divide(new BigDecimal(100))
                .toString();
        String showticketprice=String.format("%.2f",Double.parseDouble(ticketprice));
        tv_ticketprice.setText(showticketprice);

        if(TextUtils.isEmpty(getQueryTicketByTicketIdBean.getVipPrice()+"")){
            tv_huimincardprice.setVisibility(View.GONE);
        }else {
            String huimincardprice=new BigDecimal(getQueryTicketByTicketIdBean.getVipPrice()).divide(new BigDecimal(100))
                    .toString();
            String showhuimincardprice=String.format("%.2f",Double.parseDouble(huimincardprice));
            tv_huimincardprice.setText(showhuimincardprice);
        }

        tv_layerandseat.setText(getString(R.string.custombusticketinfo_layerandseat)+getQueryTicketByTicketIdBean.getLayerNo()
                +getString(R.string.custombusticketinfo_layer)+getQueryTicketByTicketIdBean.getSeatNo()+getString(R.string.custombusticketinfo_seat));
        //tv_seat.setText(getString(R.string.custombustopay_seat)+getQueryTicketByTicketIdBean.getSeatNo());
        String paymoney=new BigDecimal(getQueryTicketByTicketIdBean.getPayMoeny()).divide(new BigDecimal(100))
                .toString();
        String showpaymoney=String.format("%.2f",Double.parseDouble(paymoney));
        tv_paymoney.setText(showpaymoney);

        if(getQueryTicketByTicketIdBean.getShowSeq().equals(GlobalConfig.INTENT_CUSTOMBUS_ASC)){
            tv_startsite.setText(getQueryTicketByTicketIdBean.getBeginStation());
            tv_pointsite.setText(getQueryTicketByTicketIdBean.getEndStation());
            tv_ticketinfosite.setText(getQueryTicketByTicketIdBean.getBeginStation()+"-"+getQueryTicketByTicketIdBean.getEndStation());
        }else {
            tv_startsite.setText(getQueryTicketByTicketIdBean.getBeginStation());
            tv_pointsite.setText(getQueryTicketByTicketIdBean.getEndStation());
            tv_ticketinfosite.setText(getQueryTicketByTicketIdBean.getEndStation()+"-"+getQueryTicketByTicketIdBean.getBeginStation());
        }

    }

    private void getMyInfoIntent() {
        Bundle bundle=getIntent().getExtras();
        ticketid = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_USERTICKETSTOTICKETINFO_TICKETID_KEY);

    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        tv_frequency=(TextView)findViewById(R.id.tv_frequency);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_ticketprice=(TextView)findViewById(R.id.tv_ticketprice);
        tv_huimincardprice=(TextView)findViewById(R.id.tv_huimincardprice);
        tv_paymoney=(TextView)findViewById(R.id.tv_paymoney);
        tv_startsite=(TextView)findViewById(R.id.tv_startsite);
        tv_pointsite=(TextView)findViewById(R.id.tv_pointsite);
        tv_ticketinfosite=(TextView)findViewById(R.id.tv_ticketinfosite);
        tv_layerandseat=(TextView)findViewById(R.id.tv_layerandseat);
    }

    @Override
    public void onQueryTicketByTicketIdSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                getQueryTicketByTicketIdBean= (GetQueryTicketByTicketIdBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getQueryTicketByTicketIdBean);
                parseTicketByTicketid();
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    private void parseTicketByTicketid() {
        initOtherUI();
    }

    @Override
    public void onQueryTicketByTicketIdFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
