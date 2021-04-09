package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusLineInfoShowBean;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusTicketShowBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.CustombusQueryticketsRlHolder;

import java.util.ArrayList;

/**
 * Created by yang on 2019/1/13.
 */

public class CustomBusQueryTicketRlAdapter extends RecyclerView.Adapter<CustombusQueryticketsRlHolder>{
    private CustombusQueryticketsRlHolder.MyItemClickListener mItemClickListener;
    private Activity context;
    private ArrayList<BusTicketShowBean> busTicketShowBeanArrayList;


    public CustomBusQueryTicketRlAdapter(Activity contetx ) {
        this.context=contetx;
    }

    @Override
    public CustombusQueryticketsRlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custombus_querytickets,parent,false);
        CustombusQueryticketsRlHolder custombusQueryticketsRlHolder =new CustombusQueryticketsRlHolder(view,mItemClickListener);
        return custombusQueryticketsRlHolder;
    }
    public void setBuslineInfos(ArrayList<BusTicketShowBean> buslineInfos){
        busTicketShowBeanArrayList=buslineInfos;
    }


    @Override
    public void onBindViewHolder(CustombusQueryticketsRlHolder holder, final int position) {
        BusTicketShowBean busTicketShowBean=busTicketShowBeanArrayList.get(position);
        holder.tv_buslineandsite.setText(busTicketShowBean.getBuslineandsite());
        holder.tv_date.setText(busTicketShowBean.getDate());
        holder.tv_frequency.setText(busTicketShowBean.getFrequency());

    }

    @Override
    public int getItemCount() {
        if(busTicketShowBeanArrayList==null){
            return 0;
        }else {
            return busTicketShowBeanArrayList.size();
        }
    }

    public void setItemClickListener(CustombusQueryticketsRlHolder.MyItemClickListener itemClickListener){
        mItemClickListener=itemClickListener;
    }
}
