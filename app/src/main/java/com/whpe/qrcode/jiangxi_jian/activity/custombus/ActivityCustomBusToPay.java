package com.whpe.qrcode.jiangxi_jian.activity.custombus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whpe.qrcode.jiangxi_jian.GYDZApplication;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.AliPayResult;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;

import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.PayUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.PayUnifyAction;
import com.whpe.qrcode.jiangxi_jian.net.action.cusbtombus.RemoveTicketOrderInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetGenerateTicketOrderInfoBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.AlipayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.UnionBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.WeichatBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypeCustombusBean;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypePrepayBean;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusSelectDateBean;
import com.whpe.qrcode.jiangxi_jian.view.AlertDialog;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustombusPaytypeLvAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.MoneyGridAdapter;
import com.whpe.qrcode.jiangxi_jian.view.adapter.PaypursePaytypeLvAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yang on 2019/1/13.
 */

public class ActivityCustomBusToPay extends NormalTitleActivity implements RemoveTicketOrderInfoAction.Inter_removeTicketOrderInfo, View.OnClickListener, PayUnifyAction.Inter_queryqruserinfo {
    public LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();
    private ArrayList<PaytypeCustombusBean> paytypeCustombusBeans=new ArrayList<>();
    private CustombusPaytypeLvAdapter custombusPaytypeLvAdapter;
    private Button btn_submit;
    private ListView lv_paytype;
    private boolean payflag=false;
    private CountDownTimer timer;
    private TextView tv_remaintime;
    private TextView tv_ticketprice;
    private GridView gv_seat;
    private AlertDialog thisalertdialog;
    private GetGenerateTicketOrderInfoBean getGenerateTicketOrderInfoBean=new GetGenerateTicketOrderInfoBean();
    private TextView tv_ticketseat;
    private String paycode="";


    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
        initCustombusPaytype();
        thisalertdialog=new AlertDialog(this).builder().setCancelable(false);
    }

    private void initOhterUI() {
        String price=new BigDecimal(getGenerateTicketOrderInfoBean.getTotalTicketPrice()).divide(new BigDecimal(100))
                .toString();
        String showprice=String.format("%.2f",Double.parseDouble(price));
        tv_ticketprice.setText(getString(R.string.app_rmb)+showprice);
        initGridSeat();
        /*tv_ticketseat.setText(getString(R.string.custombustopay_layer)+getGenerateTicketOrderInfoBean.getTicketList().get(0).getLayerNo()
                        +getString(R.string.custombustopay_seat)+getGenerateTicketOrderInfoBean.getTicketList().get(0).getSeatNo());*/
        timer = new CountDownTimer(180000,1000) {
            @Override
            public void onTick(long l) {
                long seconds=l/1000;
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                tv_remaintime.setText(getString(R.string.custombustopay_remaintime)+minutes+":"+remainingSeconds);
            }

            @Override
            public void onFinish() {
                showThisExceptionAlertDialog(getString(R.string.custombustopay_overtime));
            }
        }.start();
        btn_submit.setOnClickListener(this);
    }

    private void initGridSeat() {
        SeatGridAdapter seatGridAdapter = new SeatGridAdapter(this,getGenerateTicketOrderInfoBean);
        gv_seat.setAdapter(seatGridAdapter);
    }

    //填充预付费的充值数据(从预加载数据中获取)
    private void initCustombusPaytype() {
        paytypeCustombusBeans.clear();
        List<LoadQrcodeParamBean.CityQrParamConfigBean.PayWayBean> payWayBeans=loadQrcodeParamBean.getCityQrParamConfig().getPayWay();
        for(int i=0;i<payWayBeans.size();i++){
            if(payWayBeans.get(i).getPayWayType().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAY)){
                PaytypeCustombusBean paytypeCustombusBean=new PaytypeCustombusBean();
                paytypeCustombusBean.setPayWayCode(payWayBeans.get(i).getPayWayCode());
                paytypeCustombusBean.setPayWayName(payWayBeans.get(i).getPayWayName());
                paytypeCustombusBeans.add(paytypeCustombusBean);
            }
        }
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_topay);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombustopay_title));
        getMyInfoIntent();
        initListViewPaytype();
        initOhterUI();
    }

    private void getMyInfoIntent() {
        Bundle bundle=getIntent().getExtras();
        String getinfo = bundle.getString(GlobalConfig.INTENT_CUSTOMBUS_ORDERINFOTOPAY_KEY);
        Log.e("YC","getinfo="+getinfo);
        getGenerateTicketOrderInfoBean = new GetGenerateTicketOrderInfoBean();
        getGenerateTicketOrderInfoBean = (GetGenerateTicketOrderInfoBean) JsonComomUtils.parseAllInfo(getinfo, getGenerateTicketOrderInfoBean);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        btn_submit = (Button)findViewById(R.id.btn_submit);
        lv_paytype = (ListView)findViewById(R.id.lv_paytype);
        tv_remaintime = (TextView)findViewById(R.id.tv_remaintime);
        tv_ticketprice = (TextView)findViewById(R.id.tv_ticketprice);
        tv_ticketseat = (TextView)findViewById(R.id.tv_ticketseat);
        gv_seat=(GridView)findViewById(R.id.gv_seat);
    }

    //填充支付方式选择目录
    private void initListViewPaytype() {
        custombusPaytypeLvAdapter = new CustombusPaytypeLvAdapter(this,paytypeCustombusBeans);
        lv_paytype.setAdapter(custombusPaytypeLvAdapter);
        lv_paytype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                custombusPaytypeLvAdapter.setPaytypePosition(i);
                custombusPaytypeLvAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(payflag/*&&paycode==GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT*/){
            Log.e("YC","微信回调");
            ((GYDZApplication)getApplication()).clearAllCustomBusAty();
        }
    }

    //支付宝回调
    private Handler aliHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if(payflag){
                Log.e("YC","支付宝回调");
                ((GYDZApplication)getApplication()).clearAllCustomBusAty();
            }
        };
    };


    private void startToPay(ArrayList<String> getinfo) {
        payflag = true;
        if(custombusPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION)){
            paycode=GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION;
            UnionBean unionBean=new UnionBean();
            unionBean= (UnionBean) JsonComomUtils.parseAllInfo(getinfo.get(2),unionBean);
            PayUtils.unionPay(this,unionBean.getPayParam().getTn());
            return;
        }
        if(custombusPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY)){
            paycode=GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY;
            AlipayBean alipayBean=new AlipayBean();
            alipayBean= (AlipayBean) JsonComomUtils.parseAllInfo(getinfo.get(2),alipayBean);
            PayUtils.aliPay(this,alipayBean.getPayParam().getOrderStr(),aliHandler);
            return;
        }
        if(custombusPaytypeLvAdapter.getPaytypeCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT)){
            paycode=GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT;
            WeichatBean weichatBean=new WeichatBean();
            weichatBean= (WeichatBean) JsonComomUtils.parseAllInfo(getinfo.get(2),weichatBean);
            PayUtils.weichatPay(this,weichatBean);
            return;
        }
        showExceptionAlertDialog(getString(R.string.app_function_notopen));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(payflag/*&&paycode==GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION*/){
            Log.e("YC","银联回调");
            ((GYDZApplication)getApplication()).clearAllCustomBusAty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer=null;
    }

    @Override
    public void onBackPressed() {
        showTwoButtonAlertDialog(getString(R.string.custombustopay_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveTicketOrderInfoAction removeTicketOrderInfoAction=new RemoveTicketOrderInfoAction(ActivityCustomBusToPay.this,ActivityCustomBusToPay.this);
                removeTicketOrderInfoAction.sendAction(getGenerateTicketOrderInfoBean.getPreOrderId());
                finish();
            }
        });
    }

    public boolean showThisExceptionAlertDialog(String msg){
        if(thisalertdialog!=null&&!thisalertdialog.isShowing()){
            thisalertdialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
            return true;
        }else {
            return false;
        }
    }

    public void titleback(View v){
        showTwoButtonAlertDialog(getString(R.string.custombustopay_back), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveTicketOrderInfoAction removeTicketOrderInfoAction=new RemoveTicketOrderInfoAction(ActivityCustomBusToPay.this,ActivityCustomBusToPay.this);
                removeTicketOrderInfoAction.sendAction(getGenerateTicketOrderInfoBean.getPreOrderId());
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_submit){
            requestForPayUnify();
        }
    }

    class SeatGridAdapter extends BaseAdapter {
        private Context mycontext;
        private GetGenerateTicketOrderInfoBean getGenerateTicketOrderInfoBean;

        public SeatGridAdapter(Context context, GetGenerateTicketOrderInfoBean getGenerateTicketOrderInfoBean) {
            mycontext=context;
            this.getGenerateTicketOrderInfoBean=getGenerateTicketOrderInfoBean;
        }

        @Override
        public int getCount() {
            return getGenerateTicketOrderInfoBean.getTicketList().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view= LayoutInflater.from(mycontext).inflate(R.layout.item_custombustopay_seatinfo,viewGroup,false);
            }
            TextView tv_seat=(TextView)view.findViewById(R.id.tv_seat);
            tv_seat.setText(String.format(getString(R.string.custombustopay_layerandseat),getGenerateTicketOrderInfoBean.getTicketList().get(i).getLayerNo(),getGenerateTicketOrderInfoBean.getTicketList().get(i).getSeatNo()));
            if(getGenerateTicketOrderInfoBean.getTicketList().get(i).getLayerNo().equals("1")){
                tv_seat.setText(String.format(getString(R.string.custombustopay_layerandseat),"下",getGenerateTicketOrderInfoBean.getTicketList().get(i).getSeatNo()));
            }
            if(getGenerateTicketOrderInfoBean.getTicketList().get(i).getLayerNo().equals("2")){
                tv_seat.setText(String.format(getString(R.string.custombustopay_layerandseat),"上",getGenerateTicketOrderInfoBean.getTicketList().get(i).getSeatNo()));
            }
            return view;
        }
    }

    private void requestForPayUnify() {
        showProgress();
        PayUnifyAction payUnifyAction=new PayUnifyAction(this,this);
        payUnifyAction.sendAction(getGenerateTicketOrderInfoBean.getTotalTicketPrice(),GlobalConfig.PAYUNITY_TYPE_CUSTOMBUS_TOPAY
                            ,"",custombusPaytypeLvAdapter.getPaytypeCode(),sharePreferenceLogin.getLoginPhone(),"","","",getGenerateTicketOrderInfoBean.getPreOrderId());
    }

    @Override
    public void onRemoveTicketOrderInfoSucces(ArrayList<String> getinfo) {

    }

    @Override
    public void onRemoveTicketOrderInfoFaild(String resmsg) {

    }

    @Override
    public void onPayUnifySucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                startToPay(getinfo);
            }else {
                checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onPayUnifyFaild(String resmsg) {
        dissmissProgress();
        showExceptionAlertDialog(resmsg);
    }
}
