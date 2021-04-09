package com.whpe.qrcode.jiangxi_jian.view.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.whpe.qrcode.jiangxi_jian.R;

/**
 * Created by yang on 2018/12/14.
 */

public class StudentCardInfoHolder extends RecyclerView.ViewHolder {
    public TextView tv_mz,tv_jg,tv_sfzhm,tv_lxdh,tv_jdzz,tv_xxmc,tv_xjh,tv_xsbm,tv_xsxm,tv_xb,tv_xszt,tv_spreadandshrink;
    public LinearLayout ll_studentcardinfo_item3;

    public StudentCardInfoHolder(View view) {
        super(view);
        tv_mz=(TextView)view.findViewById(R.id.tv_mz);
        tv_jg=(TextView)view.findViewById(R.id.tv_jg);
        tv_sfzhm=(TextView)view.findViewById(R.id.tv_sfzhm);
        tv_lxdh=(TextView)view.findViewById(R.id.tv_lxdh);
        tv_jdzz=(TextView)view.findViewById(R.id.tv_jdzz);
        tv_xxmc=(TextView)view.findViewById(R.id.tv_xxmc);
        tv_xjh=(TextView)view.findViewById(R.id.tv_xjh);
        tv_xsbm=(TextView)view.findViewById(R.id.tv_xsbm);
        tv_xsxm=(TextView)view.findViewById(R.id.tv_xsxm);
        tv_xb=(TextView)view.findViewById(R.id.tv_xb);
        tv_xszt=(TextView)view.findViewById(R.id.tv_xszt);
        tv_spreadandshrink=(TextView)view.findViewById(R.id.tv_spreadandshrink);
        ll_studentcardinfo_item3=(LinearLayout)view.findViewById(R.id.ll_studentcardinfo_item3);
    }
}
