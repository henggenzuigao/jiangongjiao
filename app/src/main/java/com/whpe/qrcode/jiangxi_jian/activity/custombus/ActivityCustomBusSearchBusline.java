package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.QueryBuslinePageAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetBuslinePageBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalNoTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusLineInfoShowBean;
import com.whpe.qrcode.jiangxi_jian.view.MyRefreshLayout;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustomBusSearchBuslineRlAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.CustombusSearchBuslineRlHolder;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yang on 2019/1/10.
 */

public class ActivityCustomBusSearchBusline extends NormalNoTitleActivity implements QueryBuslinePageAction.Inter_querybuslinepage, View.OnClickListener {

    private TextView tv_promt;
    private TextView tv_search;
    private RecyclerView rl_content;
    private MyRefreshLayout rl_refresh;
    private CustomBusSearchBuslineRlAdapter customBusSearchBuslineRlAdapter;
    private EditText et_searchbusline;
    private GetBuslinePageBean getBuslinePageBean=new GetBuslinePageBean();
    private ArrayList<BusLineInfoShowBean> busLineInfoShowBeans=new ArrayList<BusLineInfoShowBean>();

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_searchbusline);
    }

    @Override
    protected void afterLayout() {
        super.afterLayout();
        requestForSearchbusline("");
        /*ArrayList<BusLineInfoShowBean> busLineInfoShowBeans=new ArrayList<BusLineInfoShowBean>();
        for(int i=0;i<10;i++){
            BusLineInfoShowBean busLineInfoShowBean=new BusLineInfoShowBean();
            busLineInfoShowBean.setBusNo("119");
            busLineInfoShowBean.setStartSite("江汉路");
            busLineInfoShowBean.setPointSite("古田二路");
            busLineInfoShowBean.setTicketPrice("¥20.00购票");
            busLineInfoShowBeans.add(busLineInfoShowBean);
        }
        customBusSearchBuslineRlAdapter.setBuslineInfos(busLineInfoShowBeans);*/
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        tv_search.setOnClickListener(this);
        initRLBusline();
    }

    private void initRLBusline() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rl_content.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置Adapter
        customBusSearchBuslineRlAdapter = new CustomBusSearchBuslineRlAdapter(this);
        rl_content.setAdapter(customBusSearchBuslineRlAdapter);
        customBusSearchBuslineRlAdapter.setBuslineInfos(busLineInfoShowBeans);
        customBusSearchBuslineRlAdapter.setItemClickListener(new CustombusSearchBuslineRlHolder.MyItemClickListener() {

            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle=new Bundle();
                GetBuslinePageBean.LineListBean lineListBean=getBuslinePageBean.getLineList().get(postion);
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SEARCHBUSLINETOSELECTFREQUENCY_KEY,JsonComomUtils.parseObject(lineListBean));
                transAty(ActivityCustomBusSelectFrequency.class,bundle);
                //finish();
            }
        });
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        tv_promt = (TextView)findViewById(R.id.tv_promt);
        tv_search = (TextView)findViewById(R.id.tv_search);
        rl_content = (RecyclerView) findViewById(R.id.rl_content);
        et_searchbusline = (EditText)findViewById(R.id.et_searchbusline);
    }

    private void requestForSearchbusline(String queryParam){
        showProgress();
        String querytype="";
        if(TextUtils.isEmpty(queryParam)){
            querytype="";
        }else {
            querytype=GlobalConfig.CUSTOMBUS_QUERYBUSLINEPAGE_STATIONNAME;
        }
        QueryBuslinePageAction queryBuslinePageAction=new QueryBuslinePageAction(this,this);
        queryBuslinePageAction.sendAction(querytype,queryParam);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.tv_search){
            requestForSearchbusline(et_searchbusline.getText().toString());
        }
    }


    private void parseQueryBuslinepage(GetBuslinePageBean getBuslinePageBean) {
        busLineInfoShowBeans.clear();
        for(int num=0;num<getBuslinePageBean.getLineList().size();num++){
            GetBuslinePageBean.LineListBean lineListBean=getBuslinePageBean.getLineList().get(num);
            BusLineInfoShowBean busTicketShowBean=new BusLineInfoShowBean();
            String price=new BigDecimal(lineListBean.getBusPrice()).divide(new BigDecimal(100))
                    .toString();
            String showprice=String.format("%.2f",Double.parseDouble(price));
            busTicketShowBean.setTicketPrice(getString(R.string.app_rmb)+showprice+getString(R.string.custombussearchbusline_paytickets));
            busTicketShowBean.setStartSite(lineListBean.getBeginStation());
            busTicketShowBean.setPointSite(lineListBean.getEndStation());
            busTicketShowBean.setBusNo(lineListBean.getLineNum());
            busLineInfoShowBeans.add(busTicketShowBean);
        }
        customBusSearchBuslineRlAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuerybuslinepageSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                getBuslinePageBean= (GetBuslinePageBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getBuslinePageBean);
                parseQueryBuslinepage(getBuslinePageBean);
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQuerybuslinepageFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
