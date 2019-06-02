package com.whpe.qrcode.jiangxi_jian.fragment.custombuspassenerinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusPassengerInfo;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSearchBusline;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.PassenerInfoBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustombusPassengerGetinfoAdapter;

import java.util.ArrayList;

/**
 * Created by yang on 2019/4/24.
 */

public class FrgShowPassengerinfo extends Fragment implements View.OnClickListener {
    private View content;
    private Context context;
    private ActivityCustomBusPassengerInfo activity;
    private Button btn_submit;
    private ListView lv_passengerinfo;
    private TextView tv_add;
    private CustombusPassengerGetinfoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_custombus_showpassengerinfo,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivityCustomBusPassengerInfo) getActivity();
        bindView();
        initView();
    }

    private void bindView() {
        btn_submit = (Button) content.findViewById(R.id.btn_submit);
        lv_passengerinfo=(ListView)content.findViewById(R.id.lv_passengerinfo);
        tv_add=(TextView)content.findViewById(R.id.tv_add);
    }

    private void initView() {
        iniListPassenger();
        tv_add.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void iniListPassenger() {
        adapter = new CustombusPassengerGetinfoAdapter(activity.dbCustombusHelper,activity);
        lv_passengerinfo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_submit){
            ArrayList<PassenerInfoBean> passenerInfoBeans=activity.dbCustombusHelper.queryPasseners();
            if(passenerInfoBeans==null||passenerInfoBeans.size()==0){
                ToastUtils.showToast(activity,getString(R.string.custombuspassengerinfo_notinfo_promt));
            }else {
                activity.transAty(ActivityCustomBusSearchBusline.class);
            }
            return;
        }
        if(id==R.id.tv_add){
            activity.seeAdd();
        }
    }
}
