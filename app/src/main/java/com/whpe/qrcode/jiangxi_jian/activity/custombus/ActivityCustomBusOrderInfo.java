package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.GenerateTicketOrderInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetBuslinePageBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetGenerateTicketOrderInfoBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusSelectDateBean;
import com.whpe.qrcode.jiangxi_jian.view.AlertDialog;
import com.whpe.qrcode.jiangxi_jian.view.CustombusOrderinfoDateTextview;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustomBusLayerSelectAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.MoneyGridAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yang on 2019/1/12.
 */

public class ActivityCustomBusOrderInfo extends NormalTitleActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, GenerateTicketOrderInfoAction.Inter_generateTicketOrderInfo {

    private CustombusOrderinfoDateTextview tv_listdate;
    private Button btn_submit;
    private TextView tv_busno,tv_startsite,tv_pointsite
            ,tv_ticketprice,tv_distance,tv_orderinfosite,tv_frequency,tv_date,tv_paymoney;
    private ImageView iv_ticketsminus,iv_ticketsadd;
    private TextView tv_ticketsnumpromt,tv_ticketsnum;
    private GridView gv_layernum;
    private int select_layernum,select_ticketsnum=1;
    private BusSelectDateBean selectDateBean;
    private RelativeLayout rl_huimincard;
    private View v_line;
    private TextView tv_preferential;
    private CheckBox cb_huimincard;
    private AlertDialog alertDialog;
    private GetGenerateTicketOrderInfoBean getGenerateTicketOrderInfoBean=new GetGenerateTicketOrderInfoBean();
    private CustomBusLayerSelectAdapter customBusLayerSelectAdapter;

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
        setContentView(R.layout.activity_custombus_orderinfo);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombusorderinfo_title));
        getMyInfoIntent();
        alertDialog=new AlertDialog(this).builder().setCancelable(false);
        btn_submit.setOnClickListener(this);
        initOhterUI();
    }

    private void getMyInfoIntent() {
        Bundle bundle=getIntent().getExtras();
        String getinfo = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTDATETOORDERINFO_KEY);
        Log.e("YC","getinfo="+getinfo);
        selectDateBean = new BusSelectDateBean();
        selectDateBean = (BusSelectDateBean) JsonComomUtils.parseAllInfo(getinfo, selectDateBean);
    }

    private void initOhterUI() {
        String price=new BigDecimal(selectDateBean.getBusPrice()).divide(new BigDecimal(100))
                .toString();
        String showprice=String.format("%.2f",Double.parseDouble(price));
        tv_ticketprice.setText(getString(R.string.custombusorderinfo_ticketprice)+getString(R.string.app_rmb)+showprice);
        tv_distance.setText(selectDateBean.getTotalLen());
        tv_date.setText(getString(R.string.custombusorderinfo_date)+DateUtils.yyyyMMddToShowyyyyMMdd(selectDateBean.getDayNum()));
        tv_frequency.setText(getString(R.string.custombusorderinfo_frequency)+ DateUtils.HHmmToShowHHmm(selectDateBean.getChoosetime()));
        tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);
        tv_ticketsnumpromt.setText(String.format(getString(R.string.custombusorderinfo_selectticketsnumpromt),selectDateBean.getTicketsnum()));
        tv_busno.setText(selectDateBean.getLinenum());
        initTicketsNumSelect();
        initLayerNumSelect();
        refreshOtherUI();
    }

    private void initLayerNumSelect() {
        customBusLayerSelectAdapter = new CustomBusLayerSelectAdapter(this,Integer.parseInt(selectDateBean.getLayerNum()));
        gv_layernum.setAdapter(customBusLayerSelectAdapter);
        gv_layernum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                customBusLayerSelectAdapter.notifyDataSetChanged();
                select_layernum=(i+1);
            }
        });
    }

    private void initTicketsNumSelect() {
        iv_ticketsadd.setOnClickListener(this);
        iv_ticketsminus.setOnClickListener(this);
        refreshTicketsNum();
    }

    private void refreshOtherUI() {
        if(selectDateBean.getSupportVip().equals("yes")){
            rl_huimincard.setVisibility(View.VISIBLE);
            v_line.setVisibility(View.VISIBLE);
            tv_preferential.setVisibility(View.VISIBLE);
        }else {
            rl_huimincard.setVisibility(View.GONE);
            v_line.setVisibility(View.GONE);
            tv_preferential.setVisibility(View.GONE);
        }
        if(selectDateBean.getIsAsc().equals(GlobalConfig.INTENT_CUSTOMBUS_ORDERINFO_SHOWSEQ_VALUE_ASC)){
            tv_orderinfosite.setText(selectDateBean.getStartSite()+"-"+selectDateBean.getPointSite());
            tv_startsite.setText(selectDateBean.getStartSite());
            tv_pointsite.setText(selectDateBean.getPointSite());
        }else {
            tv_orderinfosite.setText(selectDateBean.getPointSite()+"-"+selectDateBean.getStartSite());
            tv_startsite.setText(selectDateBean.getPointSite());
            tv_pointsite.setText(selectDateBean.getStartSite());
        }
        cb_huimincard.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_submit = (Button)findViewById(R.id.btn_submit);
        tv_distance=(TextView)findViewById(R.id.tv_distance);
        tv_pointsite=(TextView)findViewById(R.id.tv_pointsite);
        tv_orderinfosite=(TextView)findViewById(R.id.tv_orderinfosite);
        tv_startsite=(TextView)findViewById(R.id.tv_startsite);
        tv_ticketprice=(TextView)findViewById(R.id.tv_ticketprice);
        tv_frequency=(TextView)findViewById(R.id.tv_frequency);
        tv_date=(TextView)findViewById(R.id.tv_date);
        tv_busno=(TextView)findViewById(R.id.tv_busno);
        rl_huimincard = (RelativeLayout)findViewById(R.id.rl_huimincard);
        v_line = findViewById(R.id.v_line);
        tv_preferential = (TextView)findViewById(R.id.tv_preferential);
        cb_huimincard=(CheckBox)findViewById(R.id.cb_huimincard);
        tv_paymoney=(TextView)findViewById(R.id.tv_paymoney);
        tv_ticketsnumpromt=(TextView)findViewById(R.id.tv_ticketsnumpromt);
        tv_ticketsnum=(TextView)findViewById(R.id.tv_ticketsnum);
        iv_ticketsminus=(ImageView)findViewById(R.id.iv_ticketsminus);
        iv_ticketsadd=(ImageView)findViewById(R.id.iv_ticketsadd);
        gv_layernum=(GridView)findViewById(R.id.gv_layernum);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.btn_submit){
            if(select_layernum==0){
                ToastUtils.showToast(this,getString(R.string.custombusorderinfo_notselectlayernum));
                return;
            }
            if(cb_huimincard.isChecked()){
                showThisAlertDialog(getString(R.string.custombusorderindo_huimincard_dialogpromt), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestForGenerateTicketOrderInfo();
                    }
                });
            }else {
                requestForGenerateTicketOrderInfo();
            }
        }else if(id==R.id.iv_ticketsminus){
            if(select_ticketsnum>1){
                select_ticketsnum--;
            }else {
                select_ticketsnum=1;
            }
            refreshTicketsNum();
        }else if(id==R.id.iv_ticketsadd){
            if(select_ticketsnum<Integer.parseInt(selectDateBean.getTicketsnum())){
                select_ticketsnum++;
            }else {
                select_ticketsnum=Integer.parseInt(selectDateBean.getTicketsnum());
            }
            refreshTicketsNum();
        }
    }

    private void refreshTicketsNum() {
        tv_ticketsnum.setText(select_ticketsnum+"");
        if(select_ticketsnum==1){
            iv_ticketsminus.setImageDrawable(MyDrawableUtils.getDrawble(this,R.drawable.aty_custombusorderinfo_ticketscannotminus));
        }else {
            iv_ticketsminus.setImageDrawable(MyDrawableUtils.getDrawble(this,R.drawable.aty_custombusorderinfo_ticketsminus));
        }
        if(select_ticketsnum==Integer.parseInt(selectDateBean.getTicketsnum())){
            iv_ticketsadd.setImageDrawable(MyDrawableUtils.getDrawble(this,R.drawable.aty_custombusorderinfo_ticketscannotadd));
        }else {
            iv_ticketsadd.setImageDrawable(MyDrawableUtils.getDrawble(this,R.drawable.aty_custombusorderinfo_ticketsadd));
        }
        refreshPayMoney();
    }

    private void refreshPayMoney() {
        if(selectDateBean.getSupportVip().equals("yes")){
            if(cb_huimincard.isChecked()){
                String price=new BigDecimal(selectDateBean.getVipPrice()).divide(new BigDecimal(100))
                        .toString();
                String showprice=String.format("%.2f",Double.parseDouble(price)*select_ticketsnum);
                tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);

            }else {
                String price=new BigDecimal(selectDateBean.getBusPrice()).divide(new BigDecimal(100))
                        .toString();
                String showprice=String.format("%.2f",Double.parseDouble(price)*select_ticketsnum);
                tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);

            }
        }else {
            String price=new BigDecimal(selectDateBean.getBusPrice()).divide(new BigDecimal(100))
                    .toString();
            String showprice=String.format("%.2f",Double.parseDouble(price)*select_ticketsnum);
            tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);
        }
    }

    private void requestForGenerateTicketOrderInfo() {
        showProgress();
        GenerateTicketOrderInfoAction generateTicketOrderInfoAction=new GenerateTicketOrderInfoAction(this,this);
        String isHuimincard="";
        if(cb_huimincard.isChecked()){
            isHuimincard="yes";
        }else {
            isHuimincard="no";
        }
        generateTicketOrderInfoAction.sendAction(selectDateBean.getLineRunId(),1,"",isHuimincard,select_ticketsnum,select_layernum);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        /*if(cb_huimincard.isChecked()){
            String price=new BigDecimal(selectDateBean.getVipPrice()).divide(new BigDecimal(100))
                    .toString();
            String showprice=String.format("%.2f",Double.parseDouble(price)*select_ticketsnum);
            tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);

        }else {
            String price=new BigDecimal(selectDateBean.getBusPrice()).divide(new BigDecimal(100))
                    .toString();
            String showprice=String.format("%.2f",Double.parseDouble(price)*select_ticketsnum);
            tv_paymoney.setText(getString(R.string.custombusorderinfo_paymoney)+getString(R.string.app_rmb)+showprice);

        }*/
        refreshPayMoney();
    }


    public boolean showThisAlertDialog(String msg, View.OnClickListener onClickListener){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setPositiveButton("确定", onClickListener)
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onGenerateTicketOrderInfoSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                getGenerateTicketOrderInfoBean= (GetGenerateTicketOrderInfoBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getGenerateTicketOrderInfoBean);
                if(getGenerateTicketOrderInfoBean!=null&&getGenerateTicketOrderInfoBean.getTotalTicketNum()>0&&getGenerateTicketOrderInfoBean.getTicketList()!=null&&getGenerateTicketOrderInfoBean.getTicketList().size()>0){
                    Bundle bundle=new Bundle();
                    bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_ORDERINFOTOPAY_KEY,JsonComomUtils.parseObject(getGenerateTicketOrderInfoBean));
                    transAty(ActivityCustomBusToPay.class,bundle);
                    finish();
                }else {
                    showExceptionAlertDialog();
                }
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onGenerateTicketOrderInfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
