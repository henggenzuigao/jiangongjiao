package com.whpe.qrcode.jiangxi_jian.bigtools;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityPayWeb;
import com.whpe.qrcode.jiangxi_jian.net.getbean.payunity.WeichatBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;

import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yang on 2018/10/12.
 */

public class PayUtils {
    public static void ccbPay(Activity context,String url){
        Bundle bundle=new Bundle();
        bundle.putString(GlobalConfig.INTENT_TOPAYWEB_PARAM,url);
        bundle.putString(GlobalConfig.INTENT_TOPAYWEB_TYPE, GlobalConfig.INTENT_TOPAYWEB_TYPE_URL);
        ((ParentActivity)context).transAty(ActivityPayWeb.class,bundle);
    }

    public static void aliPay(final Activity context, final String orderInfo, final Handler alihandler){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String,String> result=alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = GlobalConfig.SDK_PAY_FLAG;
                msg.obj = result;
                alihandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    public static void unionPay(Activity context,String tn){
        UPPayAssistEx.startPay(context,null,null,tn, GlobalConfig.UNION_SERVERMODE);
    }

    public static boolean weichatPay(Activity context,WeichatBean weichatBean){
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context,null);
        // 将该app注册到微信
        WeichatBean.PayParamBean payParamBean=weichatBean.getPayParam();
        msgApi.registerApp(payParamBean.getAppid());
        PayReq request = new PayReq();
        request.appId = payParamBean.getAppid();
        request.partnerId = payParamBean.getMch_id();
        request.prepayId= payParamBean.getPrepay_id();
        request.packageValue = "Sign=WXPay";
        request.nonceStr= payParamBean.getNonce_str();
        request.timeStamp= DateUtils.getWeichatTimestamp();
        //签名
        LinkedHashMap<String, String> signParams = new LinkedHashMap<>();
        signParams.put("appid", request.appId);
        signParams.put("noncestr", request.nonceStr);
        signParams.put("package", request.packageValue);
        signParams.put("partnerid", request.partnerId);
        signParams.put("prepayid", request.prepayId);
        signParams.put("timestamp", request.timeStamp);
        request.sign = genPackageSign(signParams, GlobalConfig.WEICHAT_KEY);
        return msgApi.sendReq(request);
    }

    public static boolean weichatPay(Activity context,WeichatBean weichatBean,String type){
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context,null);
        // 将该app注册到微信
        WeichatBean.PayParamBean payParamBean=weichatBean.getPayParam();
        msgApi.registerApp(payParamBean.getAppid());
        PayReq request = new PayReq();
        request.appId = payParamBean.getAppid();
        request.partnerId = payParamBean.getMch_id();
        request.prepayId= payParamBean.getPrepay_id();
        request.packageValue = "Sign=WXPay";
        request.nonceStr= payParamBean.getNonce_str();
        request.timeStamp= DateUtils.getWeichatTimestamp();
        request.extData=type;
        //签名
        LinkedHashMap<String, String> signParams = new LinkedHashMap<>();
        signParams.put("appid", request.appId);
        signParams.put("noncestr", request.nonceStr);
        signParams.put("package", request.packageValue);
        signParams.put("partnerid", request.partnerId);
        signParams.put("prepayid", request.prepayId);
        signParams.put("timestamp", request.timeStamp);
        request.sign = genPackageSign(signParams, GlobalConfig.WEICHAT_KEY);
        return msgApi.sendReq(request);
    }


    /**
     * 调起微信APP支付，签名
     * 生成签名
     */
    private static String genPackageSign(LinkedHashMap<String, String> params, String key) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(key);

        System.out.println("---> besign = " + sb.toString());
        String packageSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
        System.out.println("----> sign = " + packageSign);
        return packageSign;
    }

    /**
     * md5加密
     *
     * @param buffer
     * @return
     */
    private static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
