package com.whpe.qrcode.jiangxi_jian.bigtools;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by yang on 2018/9/29.
 */

public class MyDrawableUtils {
    public static Drawable getDrawble(Context context,int drawid){
        Drawable drawable;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            drawable=context.getDrawable(drawid);
        }else {
            drawable=context.getResources().getDrawable(drawid);
        }
        return drawable;
    }

    public static int getColor(Context context,int colorid){
        int color;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            color=context.getColor(colorid);
        }else {
            color=context.getResources().getColor(colorid);
        }
        return color;
    }
}
