package com.whpe.qrcode.jiangxi_jian.fragment.studentcardsearch;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityStudentCardSearch;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.studentcardsearch.SearchStudentCardInfoAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.GetOrderPayBean;
import com.whpe.qrcode.jiangxi_jian.net.getbean.studentcardsearch.GetStudentCardInfoBean;

import java.util.ArrayList;

/**
 * Created by yang on 2018/12/14.
 */

public class FrgStudentCardSearchToSearch extends Fragment implements SearchStudentCardInfoAction.Inter_SearchStudentCardInfo {
    private View content;
    private Context context;
    private ActivityStudentCardSearch activity;
    private Button btn_submit;
    private EditText et_name,et_idcard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_studentcardsearch_tosearch,container,false);
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
        btn_submit = (Button)content.findViewById(R.id.btn_submit);
        et_name = (EditText)content.findViewById(R.id.et_name);
        et_idcard=(EditText)content.findViewById(R.id.et_idcard);
    }

    private void initView() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=et_name.getText().toString().trim();
                String idcard=et_idcard.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(idcard)){
                    ToastUtils.showToast(context,getString(R.string.studentcard_search_tosearch_toast_pleaseinpoutnameandidcard));
                    return;
                }
                SearchStudentCardInfoAction searchStudentCardInfo=new SearchStudentCardInfoAction(activity,FrgStudentCardSearchToSearch.this);
                searchStudentCardInfo.sendAction(name,idcard);
            }
        });
    }


    @Override
    public void onSearchStudentCardInfoSucces(ArrayList<String> getinfo) {
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                activity.getStudentCardInfoBean= (GetStudentCardInfoBean) JsonComomUtils.parseAllInfo(getinfo.get(2),activity.getStudentCardInfoBean);

                if(!activity.getStudentCardInfoBean.getCode().equals("1")){
                    ToastUtils.showToast(context,getString(R.string.studentcard_search_tosearch_toast_searchfaild));
                    return;
                }
                if(activity.getStudentCardInfoBean.getData().size()==0){
                    ToastUtils.showToast(context,getString(R.string.studentcard_search_tosearch_toast_notinfo));
                    return;
                }
                activity.showResult();
            }else {
                activity.checkAllUpadate(rescode,getinfo);
            }
        }catch (Exception e){
            activity.showExceptionAlertDialog();
        }


    }

    @Override
    public void onSearchStudentCardInfoFaild(String resmag) {
        ToastUtils.showToast(context,resmag);
    }
}
