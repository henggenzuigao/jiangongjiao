package com.whpe.qrcode.jiangxi_jian.view.listener;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;


/**
 * Created by yang on 2018/10/8.
 */

public class LoginPhoneEditextChangeListner implements TextWatcher {
    Button btn_submit;
    Context context;
    ImageView iv_delete_phone;
    public LoginPhoneEditextChangeListner(Context context1, Button btn_submit1, ImageView iv_delete_phone1) {
        this.btn_submit=btn_submit1;
        context=context1;
        iv_delete_phone=iv_delete_phone1;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.toString().length()==0){
            btn_submit.setBackground(MyDrawableUtils.getDrawble(context, R.drawable.shape_aty_login_notinputphonebutton));
            iv_delete_phone.setVisibility(View.GONE);
        }else {
            btn_submit.setBackground(MyDrawableUtils.getDrawble(context, R.drawable.shape_common_appthmebutton));
            iv_delete_phone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
