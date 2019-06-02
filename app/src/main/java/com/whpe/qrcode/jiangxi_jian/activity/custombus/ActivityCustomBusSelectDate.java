package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.QueryTicketsByBusLineIdAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetBuslinePageBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetQueryTicketsByBuslineidBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusSelectDateBean;
import com.whpe.qrcode.jiangxi_jian.view.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yang on 2019/1/12.
 */

public class ActivityCustomBusSelectDate extends NormalTitleActivity implements QueryTicketsByBusLineIdAction.Inter_QueryTicketsByBusLineId, View.OnClickListener {
    private TextView tv_thismonth,tv_nextmonth;
    private CalendarView cv_thismonth,cv_nextmonth;
    private Button btn_submit;
    private String lineid;
    private String chooseTime;
    private String showSeq;
    private String startSite;
    private String pointSite;
    private String totalLen;
    private String linenum;
    private int monthindex;
    private int refreshIndex;
    private int thismonth=0;
    private int nextmonth=1;
    private GetQueryTicketsByBuslineidBean thisMonthTikcetsInfoBean=new GetQueryTicketsByBuslineidBean();
    private GetQueryTicketsByBuslineidBean nextMonthTicketsInfoBean=new GetQueryTicketsByBuslineidBean();
    private ArrayList<BusSelectDateBean> busSelectDateBeans_thismonth=new ArrayList<BusSelectDateBean>();
    private ArrayList<BusSelectDateBean> busSelectDateBeans_nextmonth=new ArrayList<BusSelectDateBean>();
    private BusSelectDateBean selectDateBean;

    @Override
    protected void afterLayout() {
        super.afterLayout();
        //requestForQueryTicketByBuslineid();
    }

    @Override
    protected void onStart() {
        super.onStart();
        monthindex=0;
        requestForQueryTicketByBuslineid();
    }

    private void requestForQueryTicketByBuslineid() {
        QueryTicketsByBusLineIdAction queryTicketsByBusLineIdAction=new QueryTicketsByBusLineIdAction(this,this);
        if(monthindex==0){
            if(progressIsShow()){
                return;
            }
            busSelectDateBeans_thismonth.clear();
            busSelectDateBeans_nextmonth.clear();
            cv_thismonth.clearSelectDate();
            cv_nextmonth.clearSelectDate();
            selectDateBean=null;
            showProgress();
            queryTicketsByBusLineIdAction.sendAction(lineid,DateUtils.getThisMonthFormat("yyyyMM"),chooseTime,showSeq);
        }else {
            queryTicketsByBusLineIdAction.sendAction(lineid,DateUtils.getNextMonthFormat("yyyyMM"),chooseTime,showSeq);
        }
    }



    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        refreshIndex=0;
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_selectdate);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombusselectdate_title));
        getMyInfoIntent();
        tv_nextmonth.setText(DateUtils.getNextMonthFormat("yyyy年MM月"));
        tv_thismonth.setText(DateUtils.getThisMonthFormat("yyyy年MM月"));
        btn_submit.setOnClickListener(this);
    }

    private void getMyInfoIntent() {
        Bundle bundle=getIntent().getExtras();
        lineid = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINEID_KEY);
        chooseTime = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_CHOOSETIME_KEY);
        showSeq = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_SHOWSEQ_KEY);
        startSite = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_STARTSITE_KEY);
        pointSite = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_POINTSITE_KEY);
        totalLen = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_TOTALLEN_KEY);
        linenum = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_SELECTFREQUENCYTODATE_LINENAME_KEY);
    }

    private void initCvNextmonth() {
        if(refreshIndex==0){
            // 指定显示的日期, 如当前月的下个月
            Calendar calendar = cv_nextmonth.getCalendar();
            calendar.add(Calendar.MONTH, 1);
            cv_nextmonth.setCalendar(calendar);
        }


        // 设置日期状态改变监听
        cv_nextmonth.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, boolean select, int year, int month, int day, BusSelectDateBean busSelectDateBean) {
                cv_thismonth.clearSelectDate();
                if(select){
                    selectDateBean=busSelectDateBean;
                    ToastUtils.showToast(ActivityCustomBusSelectDate.this
                            , "选中了：" + year + "年" + (month + 1) + "月" + day + "日");
                }
            }

        });
        // 设置是否能够改变日期状态
        cv_nextmonth.setChangeDateStatus(true);
        // 设置是否能够点击
        cv_nextmonth.setClickable(true);
        cv_nextmonth.setTicketForDate(busSelectDateBeans_nextmonth);

        refreshIndex++;
    }

    private void initCvThismonth() {

        // 设置日期状态改变监听
        cv_thismonth.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, boolean select, int year, int month, int day, BusSelectDateBean busSelectDateBean) {
                cv_nextmonth.clearSelectDate();
                if(select){
                    selectDateBean=busSelectDateBean;
                    ToastUtils.showToast(ActivityCustomBusSelectDate.this
                            , "选中了：" + year + "年" + (month + 1) + "月" + day + "日");
                }
            }
        });
        // 设置是否能够改变日期状态
        cv_thismonth.setChangeDateStatus(true);
        // 设置是否能够点击
        cv_thismonth.setClickable(true);
        cv_thismonth.setTicketForDate(busSelectDateBeans_thismonth);

    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        tv_thismonth=(TextView)findViewById(R.id.tv_thismonth);
        tv_nextmonth=(TextView)findViewById(R.id.tv_nextmonth);
        cv_thismonth=(CalendarView) findViewById(R.id.cv_thismonth);
        cv_nextmonth=(CalendarView) findViewById(R.id.cv_nextmonth);
        btn_submit = (Button)findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_submit){
            if(selectDateBean==null){
                ToastUtils.showToast(ActivityCustomBusSelectDate.this,getString(R.string.custombusselectdate_pleaseselect));
                return;
            }
            selectDateBean.setStartSite(startSite);
            selectDateBean.setPointSite(pointSite);
            selectDateBean.setTotalLen(totalLen);
            selectDateBean.setIsAsc(showSeq);
            selectDateBean.setChoosetime(chooseTime);
            selectDateBean.setLinenum(linenum);
            Bundle bundle=new Bundle();
            bundle.putString(GlobalConfig.INTENT_CUSTOMBUS_SELECTDATETOORDERINFO_KEY,JsonComomUtils.parseObject(selectDateBean));
            transAty(ActivityCustomBusOrderInfo.class,bundle);
            //finish();
        }
    }


    private void parseNextMonthTicketInfo() {
        busSelectDateBeans_nextmonth.clear();
        ArrayList<GetQueryTicketsByBuslineidBean.TicketListBean> ticketListBeans= (ArrayList<GetQueryTicketsByBuslineidBean.TicketListBean>) nextMonthTicketsInfoBean.getTicketList();
        if(ticketListBeans!=null&&ticketListBeans.size()>0){
            for(int index=0;index<ticketListBeans.size();index++){
                GetQueryTicketsByBuslineidBean.TicketListBean ticketListBean=ticketListBeans.get(index);
                BusSelectDateBean busSelectDateBean=new BusSelectDateBean();
                busSelectDateBean.setBusPrice(ticketListBean.getBusPrice());
                busSelectDateBean.setDayNum(ticketListBean.getDayNum());
                busSelectDateBean.setLineRunId(ticketListBean.getLineRunId());
                busSelectDateBean.setSaleStatus(ticketListBean.getSaleStatus());
                busSelectDateBean.setSupportVip(ticketListBean.getSupportVip());
                busSelectDateBean.setSurplusTicket(ticketListBean.getSurplusTicket());
                busSelectDateBean.setVipPrice(ticketListBean.getVipPrice());
                busSelectDateBean.setLayerNum(ticketListBean.getLayerNum());
                busSelectDateBean.setTicketsnum(ticketListBean.getLimitTicketNo());
                if(Integer.parseInt(ticketListBean.getSurplusTicket())>0){
                    busSelectDateBeans_nextmonth.add(busSelectDateBean);
                }
            }
        }
    }

    private void parseThisMonthTicketInfo() {
        busSelectDateBeans_thismonth.clear();
        ArrayList<GetQueryTicketsByBuslineidBean.TicketListBean> ticketListBeans= (ArrayList<GetQueryTicketsByBuslineidBean.TicketListBean>) thisMonthTikcetsInfoBean.getTicketList();
        if(ticketListBeans!=null&&ticketListBeans.size()>0){
            for(int index=0;index<ticketListBeans.size();index++){
                GetQueryTicketsByBuslineidBean.TicketListBean ticketListBean=ticketListBeans.get(index);
                BusSelectDateBean busSelectDateBean=new BusSelectDateBean();
                busSelectDateBean.setBusPrice(ticketListBean.getBusPrice());
                busSelectDateBean.setDayNum(ticketListBean.getDayNum());
                busSelectDateBean.setLineRunId(ticketListBean.getLineRunId());
                busSelectDateBean.setSaleStatus(ticketListBean.getSaleStatus());
                busSelectDateBean.setSupportVip(ticketListBean.getSupportVip());
                busSelectDateBean.setSurplusTicket(ticketListBean.getSurplusTicket());
                busSelectDateBean.setVipPrice(ticketListBean.getVipPrice());
                busSelectDateBean.setLayerNum(ticketListBean.getLayerNum());
                busSelectDateBean.setTicketsnum(ticketListBean.getLimitTicketNo());
                if(Integer.parseInt(ticketListBean.getSurplusTicket())>0){
                    busSelectDateBeans_thismonth.add(busSelectDateBean);
                }
            }
        }
    }

    @Override
    public void onQueryTicketsByBusLineIdSucces(ArrayList<String> getinfo) {
        if(monthindex==1){
            dissmissProgress();
        }
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                if(monthindex==0){
                    monthindex++;
                    thisMonthTikcetsInfoBean=(GetQueryTicketsByBuslineidBean) JsonComomUtils.parseAllInfo(getinfo.get(2),thisMonthTikcetsInfoBean);
                    parseThisMonthTicketInfo();
                    initCvThismonth();
                    requestForQueryTicketByBuslineid();
                    return;
                }
                if(monthindex==1){
                    nextMonthTicketsInfoBean=(GetQueryTicketsByBuslineidBean) JsonComomUtils.parseAllInfo(getinfo.get(2),nextMonthTicketsInfoBean);
                    parseNextMonthTicketInfo();
                    initCvNextmonth();
                    return;
                }
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            dissmissProgress();
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryTicketsByBusLineIdFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }


}
