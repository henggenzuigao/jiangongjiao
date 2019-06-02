package com.whpe.qrcode.jiangxi_jian.view.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;


/**
 * Created by yang on 2018/4/16.
 */

// 定义内部类继承ViewHolder
public class CustombusSearchBuslineRlHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //声明MyItemClickListener
    public MyItemClickListener mListener;
    public TextView tv_busno,tv_startsite,tv_pointsite,tv_ticketprice;


    public CustombusSearchBuslineRlHolder(View view, MyItemClickListener listener) {
        super(view);
        tv_busno=(TextView)view.findViewById(R.id.tv_busno);
        tv_pointsite=(TextView)view.findViewById(R.id.tv_pointsite);
        tv_startsite=(TextView)view.findViewById(R.id.tv_startsite);
        tv_ticketprice=(TextView)view.findViewById(R.id.tv_ticketprice);

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
