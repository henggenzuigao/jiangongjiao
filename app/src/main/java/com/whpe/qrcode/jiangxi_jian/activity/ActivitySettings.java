package com.whpe.qrcode.jiangxi_jian.activity;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.RechargeCardError;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceOther;
import com.whpe.qrcode.jiangxi_jian.database.DBCustombusHelper;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.CheckVersionCodeAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.GetCheckVersioncodeBean;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yang on 2018/9/30.
 */

public class ActivitySettings extends NormalTitleActivity implements View.OnClickListener, CheckVersionCodeAction.Inter_CheckVersioninfo {

    private int isize;
    private String clearsSize;
    private String sSize;
    private RelativeLayout rl_clear_cache,rl_update;
    private TextView tv_cache;
    private TextView tv_version;
    private TextView tv_cancel_login;
    private SharePreferenceOther sharePreferenceOther;
    private DBCustombusHelper dbCustombusHelper;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        sharePreferenceOther=new SharePreferenceOther(this);
        dbCustombusHelper=new DBCustombusHelper(this);
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.settings_title));
        initCache();
        initVersion();
        rl_clear_cache.setOnClickListener(this);
        rl_update.setOnClickListener(this);
        tv_cancel_login.setOnClickListener(this);
    }

    private void initVersion() {
        tv_version.setText("V "+getVersionCustomName());
    }

    private void initCache() {
        Random random = new Random(System.currentTimeMillis());

        isize = random.nextInt(1000);
        clearsSize = String.format(getString(R.string.settings_clean_cache_size_format), isize / 1000.00);
        sSize = String.format(getString(R.string.settings_cache_size_format), isize / 1000.00);
        tv_cache.setText(sSize);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        rl_clear_cache = (RelativeLayout)findViewById(R.id.rl_clear_cache);
        rl_update=(RelativeLayout)findViewById(R.id.rl_update);
        tv_cache = (TextView)findViewById(R.id.tv_cache);
        tv_version = (TextView)findViewById(R.id.tv_version);
        tv_cancel_login = (TextView)findViewById(R.id.tv_cancel_login);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.rl_clear_cache){
            if(tv_cache.getText().toString().equals("0.00M")){
                ToastUtils.showToast(ActivitySettings.this,getString(R.string.settings_nomore_cache));
            }else {
                ToastUtils.showToast(ActivitySettings.this,clearsSize);
                tv_cache.setText("0.00M");
            }
        }else if(id==R.id.tv_cancel_login){
            sharePreferenceLogin.clear();
            sharePreferenceOther.clear();
            dbCustombusHelper.deletePasseners();
            finish();
        }else if(id==R.id.rl_update){
            showProgress();
            CheckVersionCodeAction checkVersionCodeAction=new CheckVersionCodeAction(this,this);
            checkVersionCodeAction.sendAction();
        }
    }

    @Override
    public void onCheckVersionSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            if(getinfo.get(0).equals(GlobalConfig.RESCODE_SUCCESS)){
                GetCheckVersioncodeBean getCheckVersioncodeBean=new GetCheckVersioncodeBean();
                getCheckVersioncodeBean= (GetCheckVersioncodeBean) JsonComomUtils.parseAllInfo(getinfo.get(2),getCheckVersioncodeBean);
                if(getCheckVersioncodeBean.getVersion()>Integer.parseInt(getLocalVersionName())){
                    final GetCheckVersioncodeBean finalGetCheckVersioncodeBean = getCheckVersioncodeBean;
                    showTwoButtonAlertDialog(getString(R.string.settings_havenewversion), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            useOkhttpDownload(URLDecoder.decode(finalGetCheckVersioncodeBean.getUrl()));
                        }
                    });
                }else {
                    ToastUtils.showToast(this,getString(R.string.settings_nothavenewversion));
                }
            }else {
                checkAllUpadate(getinfo.get(0),getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onCheckVersionFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this,resmsg);
    }
}
