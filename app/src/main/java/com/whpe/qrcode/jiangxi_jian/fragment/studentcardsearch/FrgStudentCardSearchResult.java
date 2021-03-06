package com.whpe.qrcode.jiangxi_jian.fragment.studentcardsearch;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityStudentCardSearch;
import com.whpe.qrcode.jiangxi_jian.bigtools.BigUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.studentcardsearch.GetStudentCardInfoBean;
import com.whpe.qrcode.jiangxi_jian.view.adapter.StudentCardInfoRlAdapter;

import java.util.ArrayList;

/**
 * Created by yang on 2018/12/14.
 */

public class FrgStudentCardSearchResult extends Fragment {
    private View content;
    private Context context;
    private ActivityStudentCardSearch activity;
    private RecyclerView rl_content;
    private StudentCardInfoRlAdapter studentCardInfoRlAdapter;
    private ArrayList<GetStudentCardInfoBean.DataBean> realdataBeanList=new ArrayList<GetStudentCardInfoBean.DataBean>();
    private GetStudentCardInfoBean.DataBean realTopdata=new GetStudentCardInfoBean.DataBean();
    public TextView tv_mz,tv_jg,tv_sfzhm,tv_lxdh,tv_jdzz,tv_xxmc,tv_xjh,tv_xsbm,tv_xsxm,tv_xb,tv_xszt,tv_spreadandshrink;
    public LinearLayout ll_studentcardinfo_item3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_studentcardsearch_result,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ActivityStudentCardSearch) getActivity();
        bindView();
        initView();
    }

    private void bindView() {
        rl_content = (RecyclerView) content.findViewById(R.id.rl_content);
        tv_mz=(TextView)content.findViewById(R.id.tv_mz);
        tv_jg=(TextView)content.findViewById(R.id.tv_jg);
        tv_sfzhm=(TextView)content.findViewById(R.id.tv_sfzhm);
        tv_lxdh=(TextView)content.findViewById(R.id.tv_lxdh);
        tv_jdzz=(TextView)content.findViewById(R.id.tv_jdzz);
        tv_xxmc=(TextView)content.findViewById(R.id.tv_xxmc);
        tv_xjh=(TextView)content.findViewById(R.id.tv_xjh);
        tv_xsbm=(TextView)content.findViewById(R.id.tv_xsbm);
        tv_xsxm=(TextView)content.findViewById(R.id.tv_xsxm);
        tv_xb=(TextView)content.findViewById(R.id.tv_xb);
        tv_xszt=(TextView)content.findViewById(R.id.tv_xszt);
        tv_spreadandshrink=(TextView)content.findViewById(R.id.tv_spreadandshrink);
        ll_studentcardinfo_item3=(LinearLayout)content.findViewById(R.id.ll_studentcardinfo_item3);
    }

    private void initView() {
        /*activity.getStudentCardInfoBean=new GetStudentCardInfoBean();
        ArrayList<GetStudentCardInfoBean.DataBean> dataBeanList=new ArrayList<GetStudentCardInfoBean.DataBean>();
        for(int i=0;i<10;i++){
            GetStudentCardInfoBean.DataBean dataBean=new GetStudentCardInfoBean.DataBean();
            dataBean.setJdzz("??????????????????????????????11???2??????302");
            dataBean.setJg("????????????");
            dataBean.setLxdh("13979630046");
            dataBean.setMz("??????");
            dataBean.setSfzhm("360802200601190511");
            dataBean.setXb("???");
            dataBean.setXsxm("?????????"+i);
            dataBean.setXxmc("?????????????????????????????????");
            dataBean.setXjh("G360802200601190511");
            dataBean.setXxsbm("3436010143");
            dataBean.setXszt("??????");
            dataBeanList.add(dataBean);
        }
        activity.getStudentCardInfoBean.setData(dataBeanList);*/
        initTopContent();
        if(activity.getStudentCardInfoBean.getData().size()<=1){
            return;
        }
        initRLContent();
    }

    private void initTopContent() {
        realTopdata=activity.getStudentCardInfoBean.getData().get(0);
        String phone = realTopdata.getLxdh();
        String regularphone=phone.substring(0,3)+"****"+phone.substring(7,phone.length());
        //String regularphone=phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        String idCard = realTopdata.getSfzhm();
        //String regularidcard=idCard.substring(0,4)+"****"+idCard.substring(idCard.length()-4,idCard.length());
        String regularidcard= BigUtils.starForIdcard(idCard);

        tv_mz.setText(realTopdata.getMz());
        tv_jg.setText(realTopdata.getJg());
        tv_sfzhm.setText(regularidcard);
        tv_lxdh.setText(regularphone);
        tv_jdzz.setText(realTopdata.getJdzz());
        tv_xxmc.setText(realTopdata.getXxmc());
        tv_xjh.setText(realTopdata.getXjh());
        tv_xsbm.setText(realTopdata.getXxsbm());
        tv_xsxm.setText(realTopdata.getXsxm());
        tv_xb.setText(realTopdata.getXb());
        tv_xszt.setText(realTopdata.getXszt());
        tv_spreadandshrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tv_spreadandshrink.getText().toString().equals(context.getString(R.string.studentcard_search_result_itemspread))){
                    ll_studentcardinfo_item3.setVisibility(View.VISIBLE);
                    tv_spreadandshrink.setText(context.getString(R.string.studentcard_search_result_itemshrink));
                }else if(tv_spreadandshrink.getText().toString().equals(context.getString(R.string.studentcard_search_result_itemshrink))){
                    ll_studentcardinfo_item3.setVisibility(View.GONE);
                    tv_spreadandshrink.setText(context.getString(R.string.studentcard_search_result_itemspread));
                }
            }
        });
    }

    private void initRLContent() {
        initRealData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        //?????????????????????
        rl_content.setLayoutManager(layoutManager);
        //??????????????????????????????????????????
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //??????Adapter
        studentCardInfoRlAdapter = new StudentCardInfoRlAdapter(activity, realdataBeanList);
        rl_content.setAdapter(studentCardInfoRlAdapter);

    }

    private void initRealData() {
        realdataBeanList.clear();
        for(int index=1;index<activity.getStudentCardInfoBean.getData().size();index++){
            realdataBeanList.add(activity.getStudentCardInfoBean.getData().get(index));
        }
    }
}
