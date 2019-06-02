package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;

import java.math.BigDecimal;


/**
 * Created by yang on 2018/9/21.
 */

public class MoneyGridAdapter extends BaseAdapter {
    private Context mycontext;
    private String[] money_selects;
    private boolean isnet_money_select=false;

    public MoneyGridAdapter(Context context) {
        mycontext=context;
        money_selects=mycontext.getResources().getStringArray(R.array.paypurse_money_select);
        isnet_money_select=false;
    }

    public MoneyGridAdapter(Context context,String[] net_money_select) {
        mycontext=context;
        money_selects=net_money_select;
        isnet_money_select=true;
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
        if(isnet_money_select){
            String money=new BigDecimal(money_selects[i]).divide(new BigDecimal(100))
                    .toString();
            textView.setText(money+"元");
        }else {
            textView.setText(money_selects[i]+"元");
        }
        if(view.isSelected()){
            textView.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_select));
            //textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_select));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.white));
        }else {
            textView.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_noselect));
            //textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_noselect));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.comon_text_black_normal));
        }
        return view;
    }
}