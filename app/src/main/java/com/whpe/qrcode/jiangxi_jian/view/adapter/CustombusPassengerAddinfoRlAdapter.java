package com.whpe.qrcode.jiangxi_jian.view.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.PassenerInfoBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.holder.CustombusPassengerAddinfoRlHolder;

import java.util.ArrayList;

/**
 * Created by yang on 2018/9/9.
 */

public class CustombusPassengerAddinfoRlAdapter extends RecyclerView.Adapter<CustombusPassengerAddinfoRlHolder>{
    private Activity context;
    private ArrayList<PassenerInfoBean> passenerInfoBeans;
    public CustombusPassengerAddinfoRlAdapter(Activity contetx,ArrayList<PassenerInfoBean> passenerInfoBeans) {
        this.context=contetx;
        this.passenerInfoBeans=passenerInfoBeans;
    }

    @Override
    public CustombusPassengerAddinfoRlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custombus_passengeraddinfo_rl
                ,parent,false);
        CustombusPassengerAddinfoRlHolder holder =new CustombusPassengerAddinfoRlHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustombusPassengerAddinfoRlHolder holder, int position) {
        if(position==0){
            holder.v_bottom.setVisibility(View.GONE);
        }
        final PassenerInfoBean passenerInfoBean=passenerInfoBeans.get(position);
        passenerInfoBean.setSex(context.getString(R.string.app_man));
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_man){
                    passenerInfoBean.setSex(context.getString(R.string.app_man));
                }else {
                    passenerInfoBean.setSex(context.getString(R.string.app_women));
                }
            }
        });
        holder.et_idcard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    passenerInfoBean.setIdcard("");
                    return;
                }
                passenerInfoBean.setIdcard(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    passenerInfoBean.setName("");
                    return;
                }
                passenerInfoBean.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        holder.et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    passenerInfoBean.setPhone("");
                    return;
                }
                passenerInfoBean.setPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return passenerInfoBeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
