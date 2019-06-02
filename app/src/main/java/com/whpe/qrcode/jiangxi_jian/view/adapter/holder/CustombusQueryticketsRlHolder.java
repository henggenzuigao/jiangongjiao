package com.whpe.qrcode.jiangxi_jian.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;


/**
 * Created by yang on 2018/4/16.
 */

// 定义内部类继承ViewHolder
public class CustombusQueryticketsRlHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //声明MyItemClickListener
    public MyItemClickListener mListener;
    public TextView tv_buslineandsite,tv_frequency,tv_date;



    public CustombusQueryticketsRlHolder(View view, MyItemClickListener listener) {
        super(view);
        tv_buslineandsite=(TextView)view.findViewById(R.id.tv_buslineandsite);
        tv_frequency=(TextView)view.findViewById(R.id.tv_frequency);
        tv_date=(TextView)view.findViewById(R.id.tv_date);
        this.mListener = listener;
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(mListener != null){
            mListener.onItemClick(view,getPosition());
        }
    }

    //声明MyItemClickListener这个接口
    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
