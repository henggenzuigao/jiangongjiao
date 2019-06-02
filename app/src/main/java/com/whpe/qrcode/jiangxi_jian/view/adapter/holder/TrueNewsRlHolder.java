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
public class TrueNewsRlHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //声明MyItemClickListener
    public MyItemClickListener mListener;
    public TextView tv_maintitle,tv_info;
    public ImageView iv_icon;


    public TrueNewsRlHolder(View view, MyItemClickListener listener) {
        super(view);
        tv_maintitle=(TextView)view.findViewById(R.id.tv_maintitle);
        tv_info=(TextView)view.findViewById(R.id.tv_time);
        iv_icon= (ImageView) view.findViewById(R.id.iv_icon);

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
