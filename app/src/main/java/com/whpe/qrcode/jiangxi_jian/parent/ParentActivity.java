package com.whpe.qrcode.jiangxi_jian.parent;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.android.tu.loadingdialog.LoadingDailog;
import com.whpe.qrcode.jiangxi_jian.GYDZApplication;
import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityMain;
import com.whpe.qrcode.jiangxi_jian.activity.ActivityQrcode;
import com.whpe.qrcode.jiangxi_jian.bigtools.GlobalConfig;
import com.whpe.qrcode.jiangxi_jian.bigtools.RechargeCardError;
import com.whpe.qrcode.jiangxi_jian.bigtools.ToastUtils;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceLogin;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceOther;
import com.whpe.qrcode.jiangxi_jian.data.SharePreferenceParam;
import com.whpe.qrcode.jiangxi_jian.database.DBCustombusHelper;
import com.whpe.qrcode.jiangxi_jian.download.DownloadUtils;
import com.whpe.qrcode.jiangxi_jian.net.action.LoadQrcodeParamAction;
import com.whpe.qrcode.jiangxi_jian.view.AlertDialog;
import com.whpe.qrcode.jiangxi_jian.view.DownloadProgressView;


import org.json.JSONObject;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by yang on 2018/9/29.
 */

public abstract class ParentActivity extends AppCompatActivity implements LoadQrcodeParamAction.Inter_loadqrcodeparam {
    private LoadingDailog.Builder loadBuilder;
    private LoadingDailog dialog;
    private TextView tv_title;
    private DownloadManager downloadManager;
    private DownloadProgressView downloadProgressView;
    private AlertDialog alertDialog;
    public SharePreferenceLogin sharePreferenceLogin;
    public SharePreferenceParam sharePreferenceParam;
    private SharePreferenceOther msharePreferenceOther;
    private DBCustombusHelper mdbCustombusHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ((GYDZApplication)getApplicationContext()).addAty(this);
        sharePreferenceLogin=new SharePreferenceLogin(this);
        sharePreferenceParam=new SharePreferenceParam(this);
        msharePreferenceOther=new SharePreferenceOther(this);
        mdbCustombusHelper=new DBCustombusHelper(this);
        initProgress();
        initDownloadProgress();
        initAlertDialog();
        judgeInitParamisHave();
        beforeLayout();
        setActivityLayout();
        onCreatebindView();
        onCreateInitView();
        afterLayout();
    }

    protected abstract void afterLayout() ;

    protected abstract void beforeLayout() ;


    protected abstract void setActivityLayout();

    protected abstract void onCreateInitView();

    protected abstract void onCreatebindView() ;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharePreferenceParam=null;
        sharePreferenceLogin=null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isGrantExternalRW(this);
    }

    private void initAlertDialog() {
        alertDialog=new AlertDialog(this).builder().setCancelable(false);
    }

    private void initDownloadProgress() {
        downloadProgressView = new DownloadProgressView(this).builder().setCancelable(false);
    }

    private void initProgress() {
        loadBuilder = new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(false);
        dialog = loadBuilder.create();

    }

    protected boolean showProgress(){
        if(dialog!=null&&!dialog.isShowing()){
            dialog.show();
            return true;
        }else {
            return false;
        }
    }

    protected void dissmissProgress(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    public boolean showExceptionAlertDialog(){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            initAlertDialog();
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(getString(R.string.app_alertdialog_exception_msg))
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean showExceptionAlertDialog(String msg){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            initAlertDialog();
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean showAlertDialog(String msg, View.OnClickListener onClickListener){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            initAlertDialog();
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setPositiveButton("确定", onClickListener).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean showTwoButtonAlertDialog(String msg, View.OnClickListener onClickListener){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            initAlertDialog();
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setNegativeButton("取消",new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setPositiveButton("确定", onClickListener).show();
            return true;
        }else {
            return false;
        }
    }

    public boolean showFinishAndMoreAlertDialog(String msg, View.OnClickListener onClickListener){
        if(alertDialog!=null&&!alertDialog.isShowing()){
            initAlertDialog();
            alertDialog.setTitle(getString(R.string.app_alertdialog_title)).setMsg(msg)
                    .setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .setPositiveButton("确定", onClickListener).show();
            return true;
        }else {
            return false;
        }
    }

    protected boolean progressIsShow(){
        return dialog.isShowing();
    }

    protected boolean alertDialogIsShow(){
        return alertDialog.isShowing();
    }



    private void judgeInitParamisHave() {
        boolean paramstatus=sharePreferenceParam.getParamStatus();
        if(!paramstatus){
            if(this instanceof ActivityMain){
                showProgress();
                LoadQrcodeParamAction loadQrcodeParamAction=new LoadQrcodeParamAction(this,this);
                loadQrcodeParamAction.sendAction();
            }else {
                ((GYDZApplication)getApplicationContext()).clearAllAty();
                transAty(ActivityMain.class);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } //保持竖屏
    }

    public void titleback(View v){
        finish();
    }

    protected void setTitle(String title){
        if(tv_title==null){
            tv_title = (TextView) findViewById(R.id.tv_title);
        }
        tv_title.setText(title);
    }


    public void transAty(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    public void transAty(Class<?> clazz, Bundle data) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(data);
        startActivity(intent);
    }

    /**
     * 获取本地软件版本号名称
     */
    public String getLocalVersionName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            localVersion = packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    /**
     * 获取本地软件自定义版本名称
     */
    public String getVersionCustomName() {
        String localVersion = "";
        try {
            PackageInfo packageInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    //安装apk
    protected void installApk() {
        File file = new File(Environment.getExternalStorageDirectory()+"/Download/"+ GlobalConfig.APP_APKNAME);
        //File file = new File("/storage/emulated/0/Download/wbg.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //Android 7.0及以上
            // 参数2 清单文件中provider节点里面的authorities ; 参数3  共享的文件,即apk包的file类
            Uri apkUri = FileProvider.getUriForFile(this, "com.whpe.qrcode.jiangxi_jian.fileprovider", file);
            //对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }


    /**
     * 解决安卓6.0以上版本不能读取外部存储权限的问题
     * @param activity
     * @return
     */
    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            showExceptionAlertDialog(getString(R.string.app_no_permission));
                        }
                    }
                }
        }
    }

    public boolean checkIsNeedLogin(String rescode,ArrayList<String> getinfo){
        try {
            if(rescode.equals(GlobalConfig.RESCODE_PLEASE_RELOGIN)){
                showAlertDialog(getinfo.get(1), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharePreferenceLogin.clear();
                        msharePreferenceOther.clear();
                        mdbCustombusHelper.deletePasseners();
                        ((GYDZApplication)getApplication()).clearAllAty();
                        transAty(ActivityMain.class);
                    }
                });
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean checkIsNeedLoadParamAgain(String rescode,ArrayList<String> getinfo){
        try {
            if(rescode.equals(GlobalConfig.RESCODE_PLEASE_RELOADPARAM)){
                showAlertDialog(getinfo.get(1), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharePreferenceParam.clear();
                        ((GYDZApplication)getApplication()).clearAllAty();
                        transAty(ActivityMain.class);
                    }
                });
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean checkIsNeedUpdate(String rescode,ArrayList<String> getinfo){
        try {
            if(rescode.equals(GlobalConfig.RESCODE_PLEASE_UPDATE)){
                JSONObject jsonObject=new JSONObject(getinfo.get(2));
                final String updateurl=jsonObject.getString("url");
                showFinishAndMoreAlertDialog(getinfo.get(1), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        useOkhttpDownload(URLDecoder.decode(updateurl));
                    }
                });
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean checkRechargeCardError(String rescode,ArrayList<String> getinfo){
        try {
            if(rescode.equals(GlobalConfig.RESCODE_RECHARGECARD_SPEACIAL)){
                String errorvalue=getinfo.get(1);
                ToastUtils.showToast(this,RechargeCardError.getErrorByCode(errorvalue));
                //showExceptionAlertDialog(RechargeCardError.getErrorByCode(errorvalue));
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    //除登录与验证码页面以外所有校验app更新，预加载参数更新，以及需要重新登录
    public void checkAllUpadate(String rescode,ArrayList<String> getinfo){
        if(checkIsNeedUpdate(rescode,getinfo)){
            return;
        }
        if(checkIsNeedLoadParamAgain(rescode,getinfo)){
            return;
        }
        if(checkIsNeedLogin(rescode,getinfo)){
            return;
        }
        if(checkRechargeCardError(rescode,getinfo)){
            return;
        }
        showExceptionAlertDialog(getinfo.get(1));
    }

    //登录与验证码页面校验app更新，预加载参数更新，以及需要重新登录
    public void loginCheckAllUpadate(String rescode,ArrayList<String> getinfo){
        if(checkIsNeedUpdate(rescode,getinfo)){
            return;
        }
        if(checkIsNeedLoadParamAgain(rescode,getinfo)){
            return;
        }
        if(checkIsNeedLogin(rescode,getinfo)){
            return;
        }
    }


    public void toQrcodeActivity(ArrayList<String> getinfo){
        showAlertDialog(getinfo.get(1), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transAty(ActivityQrcode.class);
                finish();
            }
        });
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    protected void useOkhttpDownload(String url){
        Log.e("YC","下载url="+url);
        try {
            downloadProgressView.show();
            DownloadUtils.get().download(url, new DownloadUtils.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(final File file) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        downloadProgressView.dissmiss();
                        downloadProgressView.setMsg(0);
                        downloadProgressView.setProgress(0);
                        installApk();
                        finish();
                        }
                    });

                }
                @Override
                public void onDownloading(final int progress) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        downloadProgressView.setMsg(progress);
                        downloadProgressView.setProgress(progress);
                        }
                    });
                }
                @Override
                public void onDownloadFailed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        downloadProgressView.dissmiss();
                        downloadProgressView.setMsg(0);
                        downloadProgressView.setProgress(0);
                        ToastUtils.showToast(ParentActivity.this,"更新失败");
                        finish();
                        }
                    });
                }
            });
        }catch (Exception e){
            dissmissProgress();
            ToastUtils.showToast(ParentActivity.this,"更新失败");
            finish();
        }
    }

    @Override
    public void onLoadqrcodeparamSucces(ArrayList<String> getinfo) {
        dissmissProgress();
        try {
            if(getinfo==null){
                showExceptionAlertDialog();
                return;
            }
            String rescode=getinfo.get(0);
            if(rescode.equals(GlobalConfig.RESCODE_SUCCESS)){
                sharePreferenceParam.saveParamInfos(getinfo.get(2));
                sharePreferenceParam.saveParamStatus(true);
                return;
            }
            if(rescode.equals(GlobalConfig.RESCODE_SYSTEMERROR)){
                showExceptionAlertDialog(getString(R.string.app_loading_qrparam_false));
                return;
            }
            checkAllUpadate(getinfo.get(0),getinfo);
        }catch (Exception e){
            showExceptionAlertDialog();
        }
    }

    @Override
    public void onLoadqrcodeparamFaild(String resmsg) {
        Log.e("YC","错误="+resmsg);
        dissmissProgress();
        showExceptionAlertDialog(getString(R.string.app_loading_qrparam_false));
    }
}
