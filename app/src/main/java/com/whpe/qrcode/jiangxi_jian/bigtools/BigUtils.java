package com.whpe.qrcode.jiangxi_jian.bigtools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.WindowManager;


import com.whpe.qrcode.jiangxi_jian.utils.CryptTool;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yang on 2018/9/10.
 */

public class BigUtils {
    /**
     * 调用拨号功能
     * @param phone 电话号码
     */
    public static void call(Activity activity,String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 将字符串转成MD5值
     *
     * @param string 需要转换的字符串
     * @return 字符串的MD5值
     */
    public static String stringToMD5(String string) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }


        return CryptTool.byteArrayToHexString(hash);
    }

    private static Integer system_Brightness;
    //设置屏幕亮度
    public static void setLight(Activity context, int brightness) {

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        context.getWindow().setAttributes(lp);
    }

    public static String format1(double value) {

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    //将身份证转换为星号打码
    public static String starForIdcard(String idcard){
        if(idcard.length()<8){
            return "";
        }
        StringBuffer star=new StringBuffer();
        for(int i=0;i<idcard.length()-8;i++){
            star.append("*");
        }
        String idcardregular=idcard.substring(0,4)+star.toString()+idcard.substring(idcard.length()-4,idcard.length());
        return idcardregular;
    }

}
