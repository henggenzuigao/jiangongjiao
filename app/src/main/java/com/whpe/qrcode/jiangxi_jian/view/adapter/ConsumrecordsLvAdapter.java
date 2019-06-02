package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.toolbean.ConsumrecordsBean;

import java.util.ArrayList;

/**
 * Created by yang on 2018/9/30.
 */

public class ConsumrecordsLvAdapter extends BaseAdapter {
    private ArrayList<ConsumrecordsBean> consumrecordsBeans;
    private Context content;
    private int consumrecordtype;

    public ConsumrecordsLvAdapter(Context context,ArrayList<ConsumrecordsBean> consumrecordsBeans,int consumtype) {
        this.consumrecordsBeans = consumrecordsBeans;
        content=context;
        consumrecordtype=consumtype;
    }

    public void setconsunrecordtype(int type){
        consumrecordtype=type;
    }

    @Override
    public int getCount() {
        return consumrecordsBeans.size()+1;
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
        if(i==consumrecordsBeans.size()){
            TextView textView= (TextView) LayoutInflater.from(content).inflate(R.layout.item_consumrecords_lvnomore,viewGroup,false);
            return textView;
        }
        if(view==null||view instanceof TextView){
            view= LayoutInflater.from(content).inflate(R.layout.item_consumrecords_lv,viewGroup,false);
        }
        ConsumrecordsBean consumrecordsBean=consumrecordsBeans.get(i);
        ImageView iv_consumrecords_type=(ImageView)view.findViewById(R.id.iv_consumrecords_type);
        TextView tv_consumrecords_type=(TextView)view.findViewById(R.id.tv_consumrecords_type);
        TextView tv_consumrecords_date=(TextView)view.findViewById(R.id.tv_consumrecords_date);
        TextView tv_consumrecords_money=(TextView)view.findViewById(R.id.tv_consumrecords_money);
        TextView tv_consumrecords_paytype=(TextView)view.findViewById(R.id.tv_consumrecords_paytype);
        if(consumrecordtype==1){
            iv_consumrecords_type.setImageDrawable(MyDrawableUtils.getDrawble(content,R.drawable.aty_consumerecords_item_recharge));
            if(TextUtils.isEmpty(consumrecordsBean.getPayPurpose())){
                tv_consumrecords_type.setText("充值");
            }else if(consumrecordsBean.getPayPurpose().equals(GlobalConfig.PAYUNITY_TYPE_BALANCE_TOPAY)){
                tv_consumrecords_type.setText(content.getString(R.string.consumrecords_type_item_recharge));
            }else if(consumrecordsBean.getPayPurpose().equals(GlobalConfig.PAYUNITY_TYPE_DEPOSIT_TOPAY)){
                tv_consumrecords_type.setText(content.getString(R.string.consumrecords_type_item_deposit));
            }else if(consumrecordsBean.getPayPurpose().equals(GlobalConfig.PAYUNITY_TYPE_BUSCARDAMOUNT_TOPAY)){
                tv_consumrecords_type.setText(content.getString(R.string.consumrecords_type_item_rechargecard));
            }else if(consumrecordsBean.getPayPurpose().equals(GlobalConfig.PAYUNITY_TYPE_CUSTOMBUS_TOPAY)){
                tv_consumrecords_type.setText(content.getString(R.string.consumrecords_type_item_custombus));
            }else {
                tv_consumrecords_type.setText("充值");
            }
            tv_consumrecords_money.setTextColor(MyDrawableUtils.getColor(content,R.color.aty_consumerecords_item_money_recharge));
        }else {
            iv_consumrecords_type.setImageDrawable(MyDrawableUtils.getDrawble(content,R.drawable.aty_consumerecords_item_consume));
            tv_consumrecords_type.setText(content.getString(R.string.consumrecords_type_item_consum));
            tv_consumrecords_money.setTextColor(MyDrawableUtils.getColor(content,R.color.app_theme));
        }
        tv_consumrecords_money.setText(consumrecordsBean.getMoney());
        tv_consumrecords_date.setText(consumrecordsBean.getDate());
        tv_consumrecords_paytype.setText(consumrecordsBean.getPaytype());
        return view;
    }
}
