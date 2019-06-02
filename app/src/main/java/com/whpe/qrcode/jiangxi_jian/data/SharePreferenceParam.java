package com.whpe.qrcode.jiangxi_jian.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;


/**
 * Created by yang on 2017/3/20.
 */

public class SharePreferenceParam {
    private Context context;
    private SharedPreferences mShare          = null;
    private static final String SHAREDATA       = "com.currency.gydz.sharedate_param"+ GlobalConfig.APPID;
    private static final String KEY_PARAMSTATUS = "com.currency.gydz.paramstatus"+ GlobalConfig.APPID;
    private static final String KEY_PARAMINFOS  = "com.currency.gydz.paraminfos"+ GlobalConfig.APPID;

    /*//二维码城市参数版本
    private static final String PARAM_cityQrParamVersion="com.currency.gydz.cityQrParamVersion";

    //押金额度
    private static final String PARAM_needDeposit ="com.currency.gydz.needDeposit";

    //付费类型
    private static final String PARAM_qrPayType ="com.currency.gydz.qrPayType ";

    //支付方式
    private static final String PARAM_payWay ="com.currency.gydz.payWay";

    private static final String PARAM_payWay_info="com.currency.gydz.payWay_info";

    //支付方式解绑类型
    private static final String PARAM_payWayUnbindType="com.currency.gydz.payWayUnbindType";

    //退押金解绑类型
    private static final String PARAM_rebackDepositType="com.currency.gydz.rebackDepositType";

    //允许欠费额度
    private static final String PARAM_allowOweAmt ="com.currency.gydz.allowOweAmt";*/




    private SharedPreferences.Editor mEditor = null;

    public SharePreferenceParam(Context ctx) {
        if (null == ctx) throw new NullPointerException("context cannot be null!");
        context=ctx;
        mShare = ctx.getSharedPreferences(SHAREDATA, Context.MODE_PRIVATE);
        mEditor = mShare.edit();
    }

    public boolean saveParamStatus(boolean islogin) throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.putBoolean(KEY_PARAMSTATUS, islogin);
        return mEditor.commit();
    }

    public boolean getParamStatus() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");
        if(TextUtils.isEmpty(mShare.getString(KEY_PARAMINFOS, ""))){
            return false;
        }

        return mShare.getBoolean(KEY_PARAMSTATUS, false);
    }

    public boolean saveParamInfos(String paraminfos) throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");
        mEditor.putString(KEY_PARAMINFOS,DataEncrypt.encode(context,paraminfos));
        //mEditor.putString(KEY_PARAMINFOS, paraminfos);
        return mEditor.commit();
    }

    public String getParamInfos() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        return DataEncrypt.decode(context,mShare.getString(KEY_PARAMINFOS, ""));
    }

    public boolean clear() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.clear();
        return mEditor.commit();
    }

}
