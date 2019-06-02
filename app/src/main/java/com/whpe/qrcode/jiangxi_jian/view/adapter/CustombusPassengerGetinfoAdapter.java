package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.database.DBCustombusHelper;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.PassenerInfoBean;

import java.util.ArrayList;

/**
 * Created by yang on 2019/4/25.
 */

public class CustombusPassengerGetinfoAdapter extends BaseAdapter {
    private ParentActivity activity;
    private ArrayList<PassenerInfoBean> passenerInfoBeans;
    private DBCustombusHelper dbCustombusHelper;
    public CustombusPassengerGetinfoAdapter(DBCustombusHelper dbCustombusHelper, ParentActivity parentActivity) {
        activity=parentActivity;
        this.dbCustombusHelper=dbCustombusHelper;
        passenerInfoBeans=dbCustombusHelper.queryPasseners();
    }

    @Override
    public int getCount() {
        if(passenerInfoBeans==null){
            return 0;
        }
        return passenerInfoBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(activity).inflate(R.layout.item_custombus_passengergetinfo_lv,parent,false);
        }
        TextView tv_name=(TextView)convertView.findViewById(R.id.tv_name);
        TextView tv_idcard=(TextView)convertView.findViewById(R.id.tv_idcard);
        TextView tv_phone=(TextView)convertView.findViewById(R.id.tv_phone);
        TextView tv_sex=(TextView)convertView.findViewById(R.id.tv_sex);
        tv_name.setText(passenerInfoBeans.get(position).getName());
        tv_idcard.setText(String.format(activity.getString(R.string.custombuspassengerinfo_idcardbracket),passenerInfoBeans.get(position).getIdcard()));
        tv_sex.setText(passenerInfoBeans.get(position).getSex());
        tv_phone.setText(passenerInfoBeans.get(position).getPhone());
        ImageView iv_delete=(ImageView)convertView.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCustombusHelper.deletePasseners(passenerInfoBeans.get(position).getIdcard());
                passenerInfoBeans=dbCustombusHelper.queryPasseners();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
