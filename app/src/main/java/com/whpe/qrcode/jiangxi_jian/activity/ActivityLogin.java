package com.whpe.qrcode.jiangxi_jian.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.IDUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.GetSmsAction;
import com.whpe.qrcode.jiangxi_jian.net.action.LoginAction;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoginBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;
import com.whpe.qrcode.jiangxi_jian.view.CountDownTimerUtils;
import com.whpe.qrcode.jiangxi_jian.view.listener.LoginPhoneEditextChangeListner;

import java.util.ArrayList;

/**
 * Created by yang on 2018/10/4.
 */

public class ActivityLogin extends ParentActivity implements View.OnClickListener, GetSmsAction.Inter_querysms, LoginAction.Inter_login {


    private TextView btn_getverify;
    private EditText et_mobile;
    private EditText et_verify;
    private ImageView iv_delete_phone;
    private Button btn_submit;
    private String st_phone;

    @Override
    protected void afterLayout() {

    }

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onCreateInitView() {
        et_mobile.addTextChangedListener(new LoginPhoneEditextChangeListner(this,btn_submit,iv_delete_phone));
        iv_delete_phone.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_getverify.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        btn_getverify = (TextView)findViewById(R.id.btn_getverify);
        et_mobile = (EditText)findViewById(R.id.et_mobile);
        et_verify = (EditText)findViewById(R.id.et_verify);
        iv_delete_phone = (ImageView)findViewById(R.id.iv_delete_phone);
        btn_submit = (Button)findViewById(R.id.btn_submit);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        st_phone = et_mobile.getText().toString();
        String st_vertify= et_verify.getText().toString();
        if(id==R.id.iv_delete_phone){
            et_mobile.setText("");
        }else if(id==R.id.btn_submit){
            if(TextUtils.isEmpty(st_phone)||TextUtils.isEmpty(st_vertify)){
                ToastUtils.showToast(this,getString(R.string.login_promt_notinputinfo));
                return;
            }
            if(!IDUtils.isMobileNO(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_phoneform_error));
                return;
            }
            if(st_vertify.length()!=6){
                ToastUtils.showToast(this,getString(R.string.login_promt_vertifyform_error));
                return;
            }
            showProgress();
            LoginAction loginAction=new LoginAction(this,this);
            loginAction.sendAction(st_phone,st_vertify);
        }else if(id==R.id.btn_getverify){
            if(TextUtils.isEmpty(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_notinputphone));
                return;
            }
            if(!IDUtils.isMobileNO(st_phone)){
                ToastUtils.showToast(this,getString(R.string.login_promt_phoneform_error));
                return;
            }
            CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(btn_getverify, 60000, 1000);
            countDownTimerUtils.start();
            showProgress();
            GetSmsAction getSmsAction=new GetSmsAction(this,this);
            getSmsAction.sendAction(st_phone);
        }
    }

    @Override
    public void onQuerySmsSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            if(getinfo.get(0).equals(GlobalConfig.RESCODE_SUCCESS)){
                ToastUtils.showToast(this,getString(R.string.login_sendsms_true));
            }else {
                ToastUtils.showToast(this,getinfo.get(1));
                loginCheckAllUpadate(getinfo.get(0),getinfo);
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onQuerySmsFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this,getString(R.string.login_sendsms_false));
    }

    @Override
    public void onLoginSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                LoginBean loginBean=new LoginBean();
                loginBean= (LoginBean) JsonComomUtils.parseAllInfo(getinfo.get(2),loginBean);
                sharePreferenceLogin.saveLoginStatus(true);
                sharePreferenceLogin.saveLoginPhone(st_phone);
                sharePreferenceLogin.saveUid(loginBean.getUid());
                sharePreferenceLogin.saveToken(loginBean.getToken());
                finish();
            }else {
                if(TextUtils.isEmpty(getinfo.get(1))){
                    ToastUtils.showToast(this,getString(R.string.login_login_false));
                }else {
                    ToastUtils.showToast(this,getinfo.get(1));
                    loginCheckAllUpadate(rescode,getinfo);
                }
            }
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onLoginFaild(String resmsg) {
        dissmissProgress();
        ToastUtils.showToast(this,getString(R.string.login_login_false));
    }

}
