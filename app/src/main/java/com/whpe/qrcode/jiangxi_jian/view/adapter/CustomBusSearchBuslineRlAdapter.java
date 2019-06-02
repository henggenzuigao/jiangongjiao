package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.BusLineInfoShowBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.CustombusSearchBuslineRlHolder;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.NewsRlHolder;

import java.util.ArrayList;


/**
 * Created by yang on 2018/9/9.
 */

public class CustomBusSearchBuslineRlAdapter extends RecyclerView.Adapter<CustombusSearchBuslineRlHolder>{
    private CustombusSearchBuslineRlHolder.MyItemClickListener mItemClickListener;
    private Activity context;
    private ArrayList<BusLineInfoShowBean> busLineInfoShowBeanArrayList;

    public CustomBusSearchBuslineRlAdapter(Activity contetx ) {
        this.context=contetx;
    }

    @Override
    public CustombusSearchBuslineRlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custombus_searchbusline,parent,false);
        CustombusSearchBuslineRlHolder custombusSearchBuslineRlHolder =new CustombusSearchBuslineRlHolder(view,mItemClickListener);
        return custombusSearchBuslineRlHolder;
    }

    public void setBuslineInfos(ArrayList<BusLineInfoShowBean> buslineInfos){
        busLineInfoShowBeanArrayList=buslineInfos;
    }

    @Override
    public void onBindViewHolder(CustombusSearchBuslineRlHolder holder, final int position) {
        BusLineInfoShowBean busLineInfoShowBean=busLineInfoShowBeanArrayList.get(position);
        holder.tv_busno.setText(busLineInfoShowBean.getBusNo());
        holder.tv_startsite.setText(busLineInfoShowBean.getStartSite());
        holder.tv_pointsite.setText(busLineInfoShowBean.getPointSite());
        holder.tv_ticketprice.setText(busLineInfoShowBean.getTicketPrice());

    }

    @Override
    public int getItemCount() {
        if(busLineInfoShowBeanArrayList==null){
            return 0;
        }else {
            return busLineInfoShowBeanArrayList.size();
        }
    }

    public void setItemClickListener(CustombusSearchBuslineRlHolder.MyItemClickListener itemClickListener){
        mItemClickListener=itemClickListener;
    }
}
