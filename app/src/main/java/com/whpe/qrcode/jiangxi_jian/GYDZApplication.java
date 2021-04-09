package com.whpe.qrcode.jiangxi_jian;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusOrderInfo;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSearchBusline;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSelectDate;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusSelectFrequency;
import com.whpe.qrcode.jiangxi_jian.activity.custombus.ActivityCustomBusToPay;

import java.util.LinkedList;

/**
 * Created by yang on 2018/10/3.
 */

public class GYDZApplication extends Application {
    private LinkedList<Activity> mListAty = new LinkedList<>();
    private static GYDZApplication mIntanse;

    @Override
    public void onCreate() {
        super.onCreate();
        mIntanse = this;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    public static GYDZApplication getInstance(){
        return mIntanse;
    }

    public void addAty(Activity aty){
        mListAty.add(aty);
    }

    public void clearAllAty(){
        try {
            for(Activity aty : mListAty){
                if(null != aty){
                    aty.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * bus相关操作
     */
    //清除所有bus页面
    public void clearAllCustomBusAty(){
        try {
            for(Activity aty : mListAty){
                if(null != aty&&(aty instanceof ActivityCustomBusToPay||aty instanceof ActivityCustomBusSearchBusline||
                        aty instanceof ActivityCustomBusSelectDate||aty instanceof ActivityCustomBusSelectFrequency||
                        aty instanceof ActivityCustomBusOrderInfo)){
                    aty.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定制公交微信特别处理
    //private String

}
