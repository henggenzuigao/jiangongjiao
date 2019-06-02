package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.DateUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.custombus.GetBuslinePageBean;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by yang on 2018/9/21.
 */

public class CustombusFrequencyGridAdapter extends BaseAdapter {
    private Context mycontext;
    private ArrayList<GetBuslinePageBean.LineListBean.AscTimeBean> ascTimeBeanArrayList;
    private ArrayList<GetBuslinePageBean.LineListBean.DescTimeBean> descTimeBeanArrayList;

    private boolean direction=true;


    public CustombusFrequencyGridAdapter(Context context, ArrayList<GetBuslinePageBean.LineListBean.AscTimeBean> ascTimeBeans,
                                         ArrayList<GetBuslinePageBean.LineListBean.DescTimeBean> descTimeBeans) {
        mycontext=context;
        ascTimeBeanArrayList=ascTimeBeans;
        descTimeBeanArrayList=descTimeBeans;
    }

    @Override
    public int getCount() {
        if(direction){
            return ascTimeBeanArrayList.size();
        }else {
            return descTimeBeanArrayList.size();
        }
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
            view=LayoutInflater.from(mycontext).inflate(R.layout.item_frequencyselect,viewGroup,false);
        }
        TextView tv_frequency=(TextView) view.findViewById(R.id.tv_frequency);
        if(direction){
            tv_frequency.setText(DateUtils.HHmmToShowHHmm(ascTimeBeanArrayList.get(i).getRunTime()));
        }else {
            tv_frequency.setText(DateUtils.HHmmToShowHHmm(descTimeBeanArrayList.get(i).getRunTime()));
        }
        if(view.isSelected()){
            tv_frequency.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_selectfrequency_select));
            tv_frequency.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.white));
        }else {
            tv_frequency.setBackground(MyDrawableUtils.getDrawble(mycontext,R.drawable.shape_aty_selectfrequency_noselect));
            tv_frequency.setTextColor(MyDrawableUtils.getColor(mycontext,R.color.comon_text_black_less));
        }
        return view;
    }

    public void setDirection(boolean isAscTime){
        direction=isAscTime;
    }
}