package com.whpe.qrcode.jiangxi_jian.activity;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.tomyang.whpe.qrcode.bean.ack.QueryNewsContentAckBody;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListAckBody;
import com.tomyang.whpe.qrcode.bean.ack.QueryNewsListItem;
import com.tomyang.whpe.qrcode.utils.RichText2Html;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusPassengerInfo;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSearchBusline;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusQueryTicket;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceOther;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.QueryNewsContentAction;
import com.whpe.qrcode.jiangxi_jian.net.action.ShowNewsContentListAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.BackgroundTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.TrueNewsBean;
import com.whpe.qrcode.jiangxi_jian.view.CustombusTicketNoticeDialog;

import java.util.ArrayList;

/**
 * Created by yang on 2019/1/10.
 */

public class ActivityCustomBusTotal extends BackgroundTitleActivity implements View.OnClickListener,ShowNewsContentListAction.Inter_ShowNewsContentList,QueryNewsContentAction.Inter_QueryNewsContent{
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private RelativeLayout rl_toquerytickets;
    private RelativeLayout rl_tocustombusline;
    private SharePreferenceOther sharePreferenceOther;
    private TrueNewsBean trueNewsBean;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
        sharePreferenceOther=new SharePreferenceOther(this);
    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_custombustotal);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setMyTitleColor(R.color.transparency);
        setTitle(getString(R.string.custombustotal_title));
        rl_tocustombusline.setOnClickListener(this);
        rl_toquerytickets.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        rl_toquerytickets = (RelativeLayout)findViewById(R.id.rl_toquerytickets);
        rl_tocustombusline = (RelativeLayout)findViewById(R.id.rl_tocustombusline);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.rl_tocustombusline){
            /*if(!sharePreferenceOther.getTicketNoticeStatus()){
                requestForTicketNoticeList();
                return;
            }
            transAty(ActivityCustomBusSearchBusline.class);
            return;*/
            requestForTicketNoticeList();
            return;
        }
        if(id==R.id.rl_toquerytickets){
            transAty(ActivityCustomBusQueryTicket.class);
            return;
        }
    }

    public void requestForTicketNoticeList() {
        showProgress();
        ShowNewsContentListAction showNewsContentListAction=new ShowNewsContentListAction(this,this);
        String phone="";
        if(sharePreferenceLogin.getLoginStatus()){
            phone=sharePreferenceLogin.getLoginPhone();
        }
        showNewsContentListAction.sendAction(GlobalConfig.NEWSANDADVERLIST_PAGECHOOSE_RECHARGEPAGE,phone,GlobalConfig.NEWSANDADVERLIST_SPACEID_22);
    }

    private void requestForNewsContent() {
        QueryNewsContentAction queryNewsContentAction=new QueryNewsContentAction(this,this);
        queryNewsContentAction.sendAction(trueNewsBean.getContentid());
    }

    @Override
    public void onShowNewsContentListSucces(QueryNewsListAckBody getinfo) {
        try {
            ArrayList<QueryNewsListItem> queryNewsListItems=getinfo.getContentList();
            trueNewsBean=new TrueNewsBean();
            trueNewsBean.setContentid(queryNewsListItems.get(0).getContentId());
            trueNewsBean.setTitle(queryNewsListItems.get(0).getContentName());
            trueNewsBean.setInfo(queryNewsListItems.get(0).getContentDesc());
            trueNewsBean.setImg(queryNewsListItems.get(0).getContentImage());
            requestForNewsContent();
        }catch (Exception e){
            dissmissProgress();
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onShowNewsContentListFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog();
    }

    @Override
    public void onQueryQueryNewsContentSucces(QueryNewsContentAckBody getinfo) {
        dissmissProgress();
        try {
            if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_IMAGE)){
                Log.e("YC","img购票须知");
                new CustombusTicketNoticeDialog(this).builder().setCancelable(true).
                        setAgreeClickListner(null).setWebIMGAgreement(getinfo.getContent()).show();
                return;
            }
            if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_URL)){
                Log.e("YC","url购票须知");
                new CustombusTicketNoticeDialog(this).builder().setCancelable(true).
                        setAgreeClickListner(null).setWebAgreement(getinfo.getContent()).show();
                return;
            }
            if(getinfo.getContentType().equals(GlobalConfig.NEWSANDADVER_CONTENTTYPE_HTML)){
                Log.e("YC","html购票须知");
                String content= RichText2Html.INSTANCE.transContent(getinfo.getContent());
                RichText2Html.INSTANCE.creatHtml(Environment.getExternalStorageDirectory()+"/Download/"+getPackageName(),trueNewsBean.getContentid(),content);
                new CustombusTicketNoticeDialog(this).builder().setCancelable(true).
                        setAgreeClickListner(null).setWebAgreement("file://"+Environment.getExternalStorageDirectory()+"/Download/"+getPackageName()+"/html/"+trueNewsBean.getContentid()+".html").show();
                return;
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQueryQueryNewsContentFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog();
    }
}
