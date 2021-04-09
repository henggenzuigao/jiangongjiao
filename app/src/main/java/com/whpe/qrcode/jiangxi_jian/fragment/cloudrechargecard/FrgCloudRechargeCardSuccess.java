package com.whpe.qrcode.jiangxi_jian.fragment.cloudrechargecard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

/**
 * Created by yang on 2018/12/12.
 */

public class FrgCloudRechargeCardSuccess extends Fragment {
    private View content;
    private Context context;
    private ParentActivity activity;
    private Button btn_submit_success;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.frg_cloudrechargecard_success,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        content=view;
        context=getContext();
        activity= (ParentActivity) getActivity();
        bindView();
        initView();
    }

    private void bindView() {
        btn_submit_success = (Button) content.findViewById(R.id.btn_submit_success);
    }

    private void initView() {
        btn_submit_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }
}
