package com.whpe.qrcode.jiangxi_jian.fragment.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMypurse;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityQrcode;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.qrCreate;


/**
 * Created by yang on 2018/10/3.
 * 专门用于预付费的二维码fragment
 */

public class FrgQrcodeshowPrePay extends Fragment implements View.OnClickListener {
    private View content;
    private Context context;
    private ActivityQrcode activity;
    private TextView tv_qrcode_cardnum;
    private ImageView iv_qrcode;
    private TextView tv_qrcode_paytype;
    private TextView tv_refresh;
    private CountDownTimer timer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_qrcode_show_prepay,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivityQrcode) getActivity();
        bindView();
        initView();
        startTimer();
    }



    private void initView() {
        Bundle bundle=getArguments();
        String cardno = bundle.getString(GlobalConfig.QRCODE_CARD_NO_KEY);
        tv_qrcode_cardnum.setText(getString(R.string.frg_qrcode_cardnum)+cardno);
        tv_qrcode_paytype.setOnClickListener(this);

        initIvQrcode();

        initPaytype();
    }

    //填充二维码支付方式，这里都是后付费，钱包模式是00的特殊后付费
    private void initPaytype() {
        if(!activity.qrcodeStatusBean.getBindWay().get(0).getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_LATERPAYBALANCE)){
            activity.showExceptionAlertDialog();
        }
    }

    //获取二维码后填充二维码
    private void initIvQrcode() {
        iv_qrcode.setOnClickListener(this);
        iv_qrcode.setClickable(false);
        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        String decode_qrdata=activity.getQrcodedata();

        Bitmap bt= qrCreate.createQR(decode_qrdata,width,width);
        iv_qrcode.setImageBitmap(bt);
    }

    private void bindView() {
        tv_qrcode_cardnum = (TextView)content.findViewById(R.id.tv_qrcode_cardnum);
        iv_qrcode = (ImageView) content.findViewById(R.id.iv_qrcode);
        tv_qrcode_paytype = (TextView)content.findViewById(R.id.tv_qrcode_paytype);
        tv_refresh = (TextView)content.findViewById(R.id.tv_refresh);
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.tv_qrcode_paytype){
            activity.transAty(ActivityMypurse.class);
        }else if(id==R.id.iv_qrcode){
            activity.requestForQrcode();
        }
    }

    //对刷新时间进行限制
    private void startTimer() {
        timer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                tv_refresh.setText(getString(R.string.frg_qrcode_please_refresh));
                tv_refresh.setCompoundDrawablesWithIntrinsicBounds(MyDrawableUtils.getDrawble(activity,R.drawable.frg_qrcode_pleaserefresh),null,null,null);
                iv_qrcode.setClickable(true);
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer=null;
    }
}
