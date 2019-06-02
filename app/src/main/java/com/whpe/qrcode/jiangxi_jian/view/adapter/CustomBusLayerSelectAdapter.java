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

public class CustomBusLayerSelectAdapter extends BaseAdapter {
    private Context mycontext;
    private int layernum;


    public CustomBusLayerSelectAdapter(Context context, int num) {
        mycontext=context;
        layernum=num;
    }

    @Override
    public int getCount() {
        return layernum;
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
            view=LayoutInflater.from(mycontext).inflate(R.layout.item_layerselect,viewGroup,false);
        }
        TextView textView=(TextView) view.findViewById(R.id.tv_layer);
        textView.setText((i+1)+"层");
        if(layernum<=2){
            if(i==0){
                textView.setText("下层");
            }else {
                textView.setText("上层");
            }
        }
        if(view.isSelected()){
            textView.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_custombus_layer_select));
            //textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_select));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.white));
        }else {
            textView.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_custombus_layer_notselect));
            //textView.setBackgroundDrawable(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_paypurse_money_noselect));
            textView.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.comon_text_black_less));
        }
        return view;
    }
}