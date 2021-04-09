package com.whpe.qrcode.jiangxi_jian.fragment.custombuspassenerinfo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusPassengerInfo;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.toolbean.custombus.PassenerInfoBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.CustombusPassengerAddinfoRlAdapter;

import java.util.ArrayList;

/**
 * Created by yang on 2019/4/24.
 */

public class FrgAddPassengerInfo extends Fragment implements View.OnClickListener{
    private View content;
    private Context context;
    private ActivityCustomBusPassengerInfo activity;
    private Button btn_submit;
    private RecyclerView rl_passengerinfo;
    private TextView tv_add;
    private CustombusPassengerAddinfoRlAdapter adapter;
    private ArrayList<PassenerInfoBean> passenerInfoBeans=new ArrayList<PassenerInfoBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_custombus_addpassengerinfo,container,false);
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
        rl_passengerinfo=(RecyclerView) content.findViewById(R.id.rl_passengerinfo);
        tv_add=(TextView)content.findViewById(R.id.tv_add);
    }

    private void initView() {
        iniRlPassenger();
        tv_add.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void iniRlPassenger() {
        passenerInfoBeans.clear();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //设置布局管理器
        rl_passengerinfo.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rl_passengerinfo.setNestedScrollingEnabled(false);
        //设置Adapter
        PassenerInfoBean passenerInfoBean=new PassenerInfoBean();
        passenerInfoBeans.add(passenerInfoBean);
        adapter = new CustombusPassengerAddinfoRlAdapter(activity,passenerInfoBeans);
        adapter.setHasStableIds(true);
        rl_passengerinfo.setAdapter(adapter);

    }

    private void solveParam() {
        boolean iuput=true;
        boolean query=true;
        boolean repeat=true;
        solve:for(PassenerInfoBean passenerInfoBean:passenerInfoBeans){
            //判断所有信息不能为空
            if(TextUtils.isEmpty(passenerInfoBean.getIdcard())||TextUtils.isEmpty(passenerInfoBean.getName())||
                    TextUtils.isEmpty(passenerInfoBean.getPhone())||TextUtils.isEmpty(passenerInfoBean.getSex())){
                iuput=false;
                break solve;
            }
            //判断是否有重复身份证
            int     int_repeat=0;
            query:for(PassenerInfoBean bean:passenerInfoBeans){
                if(TextUtils.isEmpty(bean.getIdcard())||TextUtils.isEmpty(bean.getName())||
                        TextUtils.isEmpty(bean.getPhone())||TextUtils.isEmpty(bean.getSex())){
                    break query;
                }
                if(bean.getIdcard().equals(passenerInfoBean.getIdcard())){
                    int_repeat++;
                }
                if(int_repeat>1){
                    repeat=false;
                    break solve;
                }
            }
            //判断库内是否有重复身份证
            if(activity.dbCustombusHelper.queryPasseners(passenerInfoBean.getIdcard()).size()>0){
                query=false;
                break solve;
            }
        }
        if(!iuput){
            ToastUtils.showToast(activity,getString(R.string.custombuspassengerinfo_notinputall));
            return;
        }
        if(!repeat){
            ToastUtils.showToast(activity,getString(R.string.custombuspassengerinfo_repeatidcard));
            return;
        }
        if(!query){
            ToastUtils.showToast(activity,getString(R.string.custombuspassengerinfo_idcardisthere));
            return;
        }
        activity.dbCustombusHelper.insertPasseners(passenerInfoBeans);
        activity.seeShow();
    }

    private void checkIsEmpty() {
        boolean iuput=true;
        solve:for(PassenerInfoBean passenerInfoBean:passenerInfoBeans){
            //判断所有信息不能为空
            if(TextUtils.isEmpty(passenerInfoBean.getIdcard())||TextUtils.isEmpty(passenerInfoBean.getName())||
                    TextUtils.isEmpty(passenerInfoBean.getPhone())||TextUtils.isEmpty(passenerInfoBean.getSex())){
                iuput=false;
                break solve;
            }
        }
        if(!iuput){
            ToastUtils.showToast(activity,getString(R.string.custombuspassengerinfo_notinputall));
            return;
        }
        PassenerInfoBean passenerInfoBean=new PassenerInfoBean();
        passenerInfoBeans.add(passenerInfoBean);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.btn_submit){
            solveParam();
            return;
        }
        if(id==R.id.tv_add){
            checkIsEmpty();
        }
    }



}
