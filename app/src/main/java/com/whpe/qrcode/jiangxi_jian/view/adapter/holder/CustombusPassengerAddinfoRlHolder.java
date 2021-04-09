package com.whpe.qrcode.jiangxi_jian.view.adapter.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;

/**
 * Created by yang on 2019/4/25.
 */

public class CustombusPassengerAddinfoRlHolder extends RecyclerView.ViewHolder{
    //声明MyItemClickListener
    public EditText et_name,et_idcard,et_phone;
    public View v_bottom;
    public RadioGroup radioGroup;



    public CustombusPassengerAddinfoRlHolder(View view) {
        super(view);
        et_name=(EditText) view.findViewById(R.id.et_name);
        et_idcard=(EditText)view.findViewById(R.id.et_idcard);
        et_phone=(EditText)view.findViewById(R.id.et_phone);
        v_bottom=view.findViewById(R.id.v_bottom);
        radioGroup=(RadioGroup)view.findViewById(R.id.rg_sex);
    }


}
