package com.whpe.qrcode.jiangxi_jian.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;


/**
 * Created by yang on 2017/3/20.
 */

public class SharePreferenceLogin {
    private Context context;
    private SharedPreferences mShare          = null;
    private static final String SHAREDATA       = "com.currency.gydz.sharedate_login"+ GlobalConfig.APPID;
    private static final String KEY_LOGINSTATUS = "com.currency.gydz.loginstatus"+ GlobalConfig.APPID;
    private static final String KEY_LOGIN_PHONE = "com.currency.gydz.login.phone"+ GlobalConfig.APPID;
    private static final String KEY_TOKEN       = "com.currency.gydz.login.token"+ GlobalConfig.APPID;
    private static final String KEY_UID         = "com.pyqr.gydz.login.uid"+ GlobalConfig.APPID;



    private SharedPreferences.Editor mEditor = null;

    public SharePreferenceLogin(Context ctx) {
        if (null == ctx) throw new NullPointerException("context cannot be null!");

        context=ctx;
        mShare = ctx.getSharedPreferences(SHAREDATA, Context.MODE_PRIVATE);
        mEditor = mShare.edit();
    }


    public boolean saveLoginStatus(boolean islogin) throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.putBoolean(KEY_LOGINSTATUS, islogin);
        return mEditor.commit();
    }

    public boolean getLoginStatus() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");
        if(TextUtils.isEmpty(getToken())||TextUtils.isEmpty(getUid())||TextUtils.isEmpty(getLoginPhone())){
            return false;
        }

        return mShare.getBoolean(KEY_LOGINSTATUS, false);
    }

    public boolean saveLoginPhone(String loginphone) throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");
        mEditor.putString(KEY_LOGIN_PHONE, DataEncrypt.encode(context,loginphone));
        //mEditor.putString(KEY_LOGIN_PHONE, loginphone);
        return mEditor.commit();
    }

    public String getLoginPhone() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        return DataEncrypt.decode(context,mShare.getString(KEY_LOGIN_PHONE, ""));
        //return mShare.getString(KEY_LOGIN_PHONE, "");
    }

    public boolean saveToken(String sToken) {
        if (TextUtils.isEmpty(sToken))
            throw new NullPointerException("sUserName can not be null or empty");
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");
        mEditor.putString(KEY_TOKEN, DataEncrypt.encode(context,sToken));
        //mEditor.putString(KEY_TOKEN, sToken);
        return mEditor.commit();
    }

    public String getToken() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        return DataEncrypt.decode(context,mShare.getString(KEY_TOKEN, ""));
        //return mShare.getString(KEY_TOKEN, "");//"13410006063");

    }

    //save uid
    public boolean saveUid(String sUid){
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.putString(KEY_UID, DataEncrypt.encode(context,sUid));
        //mEditor.putString(KEY_UID, sUid);
        return mEditor.commit();
    }

    //get  uid
    public String getUid() throws NullPointerException{
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        return DataEncrypt.decode(context,mShare.getString(KEY_UID, ""));
        //return mShare.getString(KEY_UID, "");
    }


    public boolean clear() throws NullPointerException {
        if (null == mShare) throw new NullPointerException("sharepreference error");
        if (null == mEditor)
            throw new NullPointerException("must have a SharePreferenceLogin instance first");

        mEditor.clear();
        return mEditor.commit();
    }

}
