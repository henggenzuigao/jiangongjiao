package com.whpe.qrcode.jiangxi_jian.bigtools;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by yang on 2018/10/9.
 */

public class ParamGSONUtils {
    public static String beanToString(Object bean){
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();  ;
        String info=gson.toJson(bean);
        return info;
    }
}
