package com.whpe.qrcode.jiangxi_jian.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;


/**
 * Created by yang on 2018/12/18.
 */

public class SharePreferenceOther {
    private Context context;
    private SharedPreferences mShare          = null;
    private static final String SHAREDATA       = "com.currency.gydz.sharedata_other"+ GlobalConfig.APPID;
    private static final String KEY_CUSTOMBUSTICKETNOTICE = "com.currency.gydz.custombusticketnotice"+ GlobalConfig.APPID;



    private SharedPreferences.Editor mEditor = null;

    public SharePreferenceOther(Context ctx) {
        if (null == ctx) throw new NullPointerException("context cannot be null!");

        context=ctx;
        mShare = ctx.getSharedPreferences(SHAREDATA, Context.MODE_PRIVATE);
        mEditor = mShare.edit();
    }


    public boolean saveTicketNoticeStatus(boolean islogin) throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.putBoolean(KEY_CUSTOMBUSTICKETNOTICE, islogin);
        return mEditor.commit();
    }

    public boolean getTicketNoticeStatus() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        return mShare.getBoolean(KEY_CUSTOMBUSTICKETNOTICE, false);
    }


    public boolean clear() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.clear();
        return mEditor.commit();
    }
}
