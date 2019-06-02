package com.whpe.qrcode.jiangxi_jian.data;

import android.content.Context;
import android.text.TextUtils;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.utils.DESUtils;


/**
 * Created by yang on 2018/10/8.
 */

public class DataEncrypt {
    public static String encode(Context context,String data){
        if(TextUtils.isEmpty(data)){
            return "";
        }
        String st= DESUtils.encode(data,context.getString(R.string.data_password));
        return st;
    }

    public static String decode(Context context,String data){
        if(TextUtils.isEmpty(data)){
            return "";
        }
        String st=DESUtils.decode(data,context.getString(R.string.data_password));
        return st;
    }
}
