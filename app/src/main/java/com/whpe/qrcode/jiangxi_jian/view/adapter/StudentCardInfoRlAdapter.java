package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.BigUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.studentcardsearch.GetStudentCardInfoBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.NewsRlHolder;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.StudentCardInfoHolder;

import java.util.ArrayList;


/**
 * Created by yang on 2018/9/9.
 */

public class StudentCardInfoRlAdapter extends RecyclerView.Adapter<StudentCardInfoHolder>{
    private Activity context;
    private ArrayList<GetStudentCardInfoBean.DataBean> dataBeanList;

    public StudentCardInfoRlAdapter(Activity contetx ,ArrayList<GetStudentCardInfoBean.DataBean> dataBeans) {
        this.context=contetx;
        dataBeanList=dataBeans;
    }

    @Override
    public StudentCardInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frgresult_studentcardinfo,parent,false);
        StudentCardInfoHolder studentCardInfoHolder =new StudentCardInfoHolder(view);
        return studentCardInfoHolder;
    }

    //String st_show_phone=st_phone.substring(0,3)+"****"+st_phone.substring(7,st_phone.length());
    @Override
    public void onBindViewHolder(final StudentCardInfoHolder holder, final int position) {
        String phone = dataBeanList.get(position).getLxdh();
        String regularphone=phone.substring(0,3)+"****"+phone.substring(7,phone.length());
        //String regularphone=phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        String idCard = dataBeanList.get(position).getSfzhm();
        //String regularidcard=idCard.substring(0,4)+"****"+idCard.substring(idCard.length()-4,idCard.length());
        String regularidcard= BigUtils.starForIdcard(idCard);

        holder.tv_mz.setText(dataBeanList.get(position).getMz());
        holder.tv_jg.setText(dataBeanList.get(position).getJg());
        holder.tv_sfzhm.setText(regularidcard);
        holder.tv_lxdh.setText(regularphone);
        holder.tv_jdzz.setText(dataBeanList.get(position).getJdzz());
        holder.tv_xxmc.setText(dataBeanList.get(position).getXxmc());
        holder.tv_xjh.setText(dataBeanList.get(position).getXjh());
        holder.tv_xsbm.setText(dataBeanList.get(position).getXxsbm());
        holder.tv_xsxm.setText(dataBeanList.get(position).getXsxm());
        holder.tv_xb.setText(dataBeanList.get(position).getXb());
        holder.tv_xszt.setText(dataBeanList.get(position).getXszt());
        holder.tv_spreadandshrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tv_spreadandshrink.getText().toString().equals(context.getString(R.string.studentcard_search_result_itemspread))){
                    holder.ll_studentcardinfo_item3.setVisibility(View.VISIBLE);
                    holder.tv_spreadandshrink.setText(context.getString(R.string.studentcard_search_result_itemshrink));
                }else if(holder.tv_spreadandshrink.getText().toString().equals(context.getString(R.string.studentcard_search_result_itemshrink))){
                    holder.ll_studentcardinfo_item3.setVisibility(View.GONE);
                    holder.tv_spreadandshrink.setText(context.getString(R.string.studentcard_search_result_itemspread));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

}
