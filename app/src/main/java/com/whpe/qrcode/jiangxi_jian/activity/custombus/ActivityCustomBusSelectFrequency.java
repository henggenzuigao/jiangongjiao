package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetBuslinePageBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustombusFrequencyGridAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yang on 2019/1/11.
 */

public class ActivityCustomBusSelectFrequency extends NormalTitleActivity implements View.OnClickListener {

    private GridView gv_selectnormalfrequency;
    private CustombusFrequencyGridAdapter custombusFrequencyGridAdapter;
    private Button btn_submit;
    private TextView tv_totalsite,tv_startsite,tv_pointsite
            ,tv_ticketprice,tv_distance,tv_busno;
    private ImageView iv_changedirectionsite;
    private boolean isAscTime=true;
    private GetBuslinePageBean.LineListBean lineListBean;
    private String selectfrequency="";
    private TextView tv_busdescriptionpromt;

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
        setContentView(R.layout.activity_custombus_selectfrequency);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombusselectfrequency_title));
        btn_submit.setOnClickListener(this);
        getMyInfoIntent();
        initGVFrequency();
        initRefreshIcChange();
        initOtherUI();
    }

    private void initOtherUI() {
        String price=new BigDecimal(lineListBean.getBusPrice()).divide(new BigDecimal(100))
                .toString();
        String showprice=String.format("%.2f",Double.parseDouble(price));
        String distance=new BigDecimal(lineListBean.getTotalLen()).divide(new BigDecimal(1000))
                .toString();
        String showdistance=String.format("%.2f",Double.parseDouble(distance));
        tv_ticketprice.setText(getString(R.string.custombusselectfrequency_ticketprice)+getString(R.string.app_rmb)+showprice);
        tv_distance.setText(getString(R.string.custombusselectfrequency_distance)+showdistance+getString(R.string.custombusselectfrequency_km));
        tv_busdescriptionpromt.setOnClickListener(this);
        tv_busno.setText(lineListBean.getLineNum());
        refreshOtherUI();
    }

    private void initRefreshIcChange() {
        if(lineListBean.getSupportDesc().equals("no")){
            iv_changedirectionsite.setVisibility(View.GONE);
        }
        iv_changedirectionsite.setOnClickListener(this);
    }

    private void getMyInfoIntent() {
        String getInfo =getIntent().getExtras().getString(GlobalConfig.INTENT_CUSTOMBUS_SEARCHBUSLINETOSELECTFREQUENCY_KEY);
        Log.e("YC","获取上个页面的数据="+getInfo);
        //getInfo="{\"appId\":\"03634350JAGJ\",\"ascTime\":[{\"runTime\":\"0830\"},{\"runTime\":\"0930\"}],\"beginStation\":\"井大\",\"busPrice\":68,\"consumeTime\":\"1555\",\"currentPage\":0,\"descTime\":[{\"runTime\":\"1800\"},{\"runTime\":\"1030\"},{\"runTime\":\"0730\"}],\"endStation\":\"渼陂古村\",\"isTemp\":\"no\",\"lineId\":\"2\",\"lineNum\":\"游一路\",\"pageNo\":0,\"showSeq\":\"ASC\",\"supportDesc\":\"yes\",\"totalLen\":26666}";
        lineListBean = new GetBuslinePageBean.LineListBean();
        lineListBean = (GetBuslinePageBean.LineListBean) JsonComomUtils.parseAllInfo(getInfo, lineListBean);
    }

    private void refreshOtherUI() {
        if(isAscTime){
            tv_totalsite.setText(lineListBean.getBeginStation()+"-"+lineListBean.getEndStation());
            tv_startsite.setText(lineListBean.getBeginStation());
            tv_pointsite.setText(lineListBean.getEndStation());
        }else {
            tv_totalsite.setText(lineListBean.getEndStation()+"-"+lineListBean.getBeginStation());
            tv_startsite.setText(lineListBean.getEndStation());
            tv_pointsite.setText(lineListBean.getBeginStation());
        }
    }

    private void initGVFrequency() {
        final ArrayList<GetBuslinePageBean.LineListBean.AscTimeBean> ascTimeBeans= (ArrayList<GetBuslinePageBean.LineListBean.AscTimeBean>) lineListBean.getAscTime();
        final ArrayList<GetBuslinePageBean.LineListBean.DescTimeBean> descTimeBeans= (ArrayList<GetBuslinePageBean.LineListBean.DescTimeBean>) lineListBean.getDescTime();

        custombusFrequencyGridAdapter = new CustombusFrequencyGridAdapter(this,ascTimeBeans,descTimeBeans);
        custombusFrequencyGridAdapter.setDirection(isAscTime);
        gv_selectnormalfrequency.setAdapter(custombusFrequencyGridAdapter);
        gv_selectnormalfrequency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                custombusFrequencyGridAdapter.notifyDataSetChanged();
                if(isAscTime){
                    selectfrequency=ascTimeBeans.get(i).getRunTime();
                }else {
                    selectfrequency=descTimeBeans.get(i).getRunTime();
                }
            }
        });
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        gv_selectnormalfrequency = (GridView)findViewById(R.id.gv_selectnormalfrequency);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        tv_totalsite=(TextView)findViewById(R.id.tv_totalsite);
        iv_changedirectionsite=(ImageView)findViewById(R.id.iv_changedirectionsite);
        tv_startsite=(TextView)findViewById(R.id.tv_startsite);
        tv_pointsite=(TextView)findViewById(R.id.tv_pointsite);
        tv_ticketprice=(TextView)findViewById(R.id.tv_ticketprice);
        tv_distance=(TextView)findViewById(R.id.tv_distance);
        tv_busdescriptionpromt = (TextView)findViewById(R.id.tv_busdescriptionpromt);
        tv_busno=(TextView)findViewById(R.id.tv_busno);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.tv_busdescriptionpromt){
            if(TextUtils.isEmpty(lineListBean.getBusDescription())){
                return;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(lineListBean.getBusDescription())//内容
                    .create();
            alertDialog.show();
            return;
        }
        if(id==R.id.btn_submit){
            if(TextUtils.isEmpty(selectfrequency)){
                ToastUtils.showToast(this,getString(R.string.custombusselectfrequency_pleaseselect));
            }else {
                Bundle bundle=new Bundle();
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINEID_KEY,lineListBean.getLineId());
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_CHOOSETIME_KEY,selectfrequency);
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_TOTALLEN_KEY,tv_distance.getText().toString());
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_STARTSITE_KEY,lineListBean.getBeginStation());
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_POINTSITE_KEY,lineListBean.getEndStation());
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINENAME_KEY,lineListBean.getLineNum());
                if(isAscTime){
                    bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_KEY,GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_VALUE_ASC);
                }else {
                    bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_KEY,GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_VALUE_DESC);
                }
                transAty(ActivityCustomBusSelectDate.class,bundle);
                //finish();
            }
        }
        if(id==R.id.iv_changedirectionsite){
            isAscTime=!isAscTime;
            custombusFrequencyGridAdapter.setDirection(isAscTime);
            custombusFrequencyGridAdapter.notifyDataSetChanged();
            selectfrequency="";
            refreshOtherUI();
        }

    }
}
