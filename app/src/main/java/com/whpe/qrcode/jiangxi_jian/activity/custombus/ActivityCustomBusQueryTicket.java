package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.QueryUserTicketInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetQueryUserTicketInfoBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusTicketShowBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustomBusQueryTicketRlAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.CustombusQueryticketsRlHolder;

import java.util.ArrayList;

/**
 * Created by yang on 2019/1/10.
 */

public class ActivityCustomBusQueryTicket extends NormalTitleActivity implements QueryUserTicketInfoAction.Inter_QueryUserTicketInfo {

    private RecyclerView rl_content;
    private CustomBusQueryTicketRlAdapter customBusQueryTicketRlAdapter;
    private GetQueryUserTicketInfoBean getQueryUserTicketInfoBean=new GetQueryUserTicketInfoBean();
    private ArrayList<BusTicketShowBean> busTicketShowBeans=new ArrayList<BusTicketShowBean>();

    @Override
    protected void afterLayout() {
        super.afterLayout();
        /*ArrayList<BusTicketShowBean> busTicketShowBeans=new ArrayList<BusTicketShowBean>();
        for(int i=0;i<10;i++){
            BusTicketShowBean busTicketShowBean=new BusTicketShowBean();
            busTicketShowBean.setBuslineandsite("119 江汉路-古田二路");
            busTicketShowBean.setDate("2019年1月10日");
            busTicketShowBean.setFrequency("10:30");
            busTicketShowBeans.add(busTicketShowBean);
        }
        customBusQueryTicketRlAdapter.setBuslineInfos(busTicketShowBeans);
        customBusQueryTicketRlAdapter.notifyDataSetChanged();*/
        requestForUserTicketInfo();

    }

    private void requestForUserTicketInfo() {
        showProgress();
        QueryUserTicketInfoAction queryUserTicketInfoAction=new QueryUserTicketInfoAction(this,this);
        queryUserTicketInfoAction.sendAction();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_querytickets);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombusquerytickets_title));
        initTicketinfo();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        rl_content = (RecyclerView)findViewById(R.id.rl_content);
    }

    private void initTicketinfo() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rl_content.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置Adapter
        customBusQueryTicketRlAdapter = new CustomBusQueryTicketRlAdapter(this);
        rl_content.setAdapter(customBusQueryTicketRlAdapter);
        customBusQueryTicketRlAdapter.notifyDataSetChanged();
        customBusQueryTicketRlAdapter.setItemClickListener(new CustombusQueryticketsRlHolder.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Bundle bundle=new Bundle();
                bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_USERTICKETSTOTICKETINFO_TICKETID_KEY,busTicketShowBeans.get(postion).getTicketid());
                transAty(ActivityCustomBusTicketInfo.class,bundle);
            }
        });
    }

    private void parseGetQueryUserTicketInfo() {
        busTicketShowBeans.clear();
        busTicketShowBeans = new ArrayList<BusTicketShowBean>();
        ArrayList<GetQueryUserTicketInfoBean.DataListBean> dataListBeans= (ArrayList<GetQueryUserTicketInfoBean.DataListBean>) getQueryUserTicketInfoBean.getDataList();
        if(dataListBeans==null){
            return;
        }
        for(int i=0;i<dataListBeans.size();i++){
            BusTicketShowBean busTicketShowBean=new BusTicketShowBean();
            busTicketShowBean.setBuslineandsite(dataListBeans.get(i).getLineNum());
            busTicketShowBean.setDate(DateUtils.yyyyMMddToShowyyyyMMdd(dataListBeans.get(i).getChooseDate()));
            busTicketShowBean.setFrequency(DateUtils.HHmmToShowHHmm(dataListBeans.get(i).getChooseTime()));
            busTicketShowBean.setTicketid(dataListBeans.get(i).getTicketId());
            busTicketShowBeans.add(busTicketShowBean);
        }
        customBusQueryTicketRlAdapter.setBuslineInfos(busTicketShowBeans);
        customBusQueryTicketRlAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQueryUserTicketInfoSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                getQueryUserTicketInfoBean= (GetQueryUserTicketInfoBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getQueryUserTicketInfoBean);
                parseGetQueryUserTicketInfo();
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }


    @Override
    public void onQueryUserTicketInfoFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
