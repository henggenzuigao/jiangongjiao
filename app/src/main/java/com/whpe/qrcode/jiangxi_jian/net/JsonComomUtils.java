package com.whpe.qrcode.jiangxi_jian.net;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yang on 2018/10/10.
 */

public class JsonComomUtils {
    public static ArrayList<String> parseJson(String json){
        ArrayList<String> list=new ArrayList<String>();
        try {
            JSONObject jsonObject=new JSONObject(json);
            list.add(jsonObject.getString("respCode"));
            list.add(jsonObject.getString("respMsg"));
            list.add(jsonObject.getString("data"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static Object parseAllInfo( String getinfo, Object getbean){
        Gson gson=new Gson();
        try {
            getbean=gson.fromJson(getinfo,getbean.getClass());
        } catch (Exception e) {
            return null;
        }
        return getbean;
    }

    public static String parseObject( Object getbean){
        Gson gson=new Gson();
        String json="";
        try {
            json=gson.toJson(getbean);
        } catch (Exception e) {
            return null;
        }
        return json;
    }
}
