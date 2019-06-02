package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;


/**
 * Created by yang on 2018/9/21.
 */

public class MoneyGridRechargeCardAdapter extends BaseAdapter {
    private Context mycontext;
    private String[] money_selects;

    public MoneyGridRechargeCardAdapter(Context context) {
        mycontext=context;
        money_selects=mycontext.getResources().getStringArray(R.array.rechargecard_money_select);
    }

    @Override
    public int getCount() {
        return money_selects.length;
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
            view=LayoutInflater.from(mycontext).inflate(R.layout.item_moneyselect,viewGroup,false);
        }
        TextView textView=(TextView) view.findViewById(R.id.tv_money);
        textView.setText(money_selects[i]+"元");
        if(view.isSelected()){
            textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_rechargecard_money_select));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.white));
        }else {
            textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_rechargecard_money_noselect));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.comon_text_black_normal));
        }
        return view;
    }
}