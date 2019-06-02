package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.toolbean.PaytypePrepayBean;

import java.util.ArrayList;

/**
 * Created by yang on 2018/9/21.
 */

public class PaypursePaytypeLvAdapter extends BaseAdapter {
    private Context mycontext;
    //private String[] paytype_selects;
    //private int[] paytype_selects_img;
    private int int_paytype_select=0;
    private TypedArray typedArray;
    private ArrayList<PaytypePrepayBean> paytypePrepayBeans;

    public PaypursePaytypeLvAdapter(Context context, ArrayList<PaytypePrepayBean> paytypePrepayBeans) {
        mycontext=context;
        //paytype_selects=mycontext.getResources().getStringArray(R.array.paypurse_paytype_select);
        //typedArray=mycontext.getResources().obtainTypedArray(R.array.paypurse_paytype_select_img);
        //paytype_selects_img=new int[typedArray.length()];
        /*for(int i=0;i<typedArray.length();i++){
            paytype_selects_img[i] = typedArray.getResourceId(i, 0);
        }*/
        this.paytypePrepayBeans=paytypePrepayBeans;
    }

    public void setPaytypePosition(int i){
        int_paytype_select=i;
    }

    public String getPaytypeCode(){
        return paytypePrepayBeans.get(int_paytype_select).getPayWayCode();
    }

    @Override
    public int getCount() {
        return paytypePrepayBeans.size();
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
            view=LayoutInflater.from(mycontext).inflate(R.layout.item_paypurse_paytype,viewGroup,false);
        }
        PaytypePrepayBean paytypePrepayBean=paytypePrepayBeans.get(i);
        ImageView iv_paytype=(ImageView) view.findViewById(R.id.iv_paytype);
        if(paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYWEICHAT)){
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.aty_paypurse_paytype_weichat));
        }else if(paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYCCB)){
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.aty_paypurse_paytype_ccb));
        }else if(paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYUNION)){
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.aty_paypurse_paytype_union));
        }else if(paytypePrepayBean.getPayWayCode().equals(GlobalConfig.LOADPARAM_QROPAYTYPE_PREPAYALIPAY)){
            iv_paytype.setImageDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.aty_paypurse_paytype_alipay));
        }
        TextView tv_paytype=(TextView)view.findViewById(R.id.tv_paytype);
        tv_paytype.setText(paytypePrepayBean.getPayWayName());
        ImageView iv_paytype_select=(ImageView)view.findViewById(R.id.iv_paytype_select);
        if(int_paytype_select==i){
            iv_paytype_select.setImageDrawable(mycontext.getResources().getDrawable(R.drawable.aty_paypurse_paytype_select));
        }else {
            iv_paytype_select.setImageDrawable(mycontext.getResources().getDrawable(R.drawable.aty_paypurse_paytype_noselect));
        }
        return view;
    }
}