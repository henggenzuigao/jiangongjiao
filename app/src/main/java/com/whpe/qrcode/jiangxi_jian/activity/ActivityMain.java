package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.bigtools.ParamGSONUtils;
import com.whpe.qrcode.jiangxi_jian.fragment.FrgHome;
import com.whpe.qrcode.jiangxi_jian.fragment.FrgMyself;
import com.whpe.qrcode.jiangxi_jian.net.JsonComomUtils;
import com.whpe.qrcode.jiangxi_jian.net.getbean.LoadQrcodeParamBean;
import com.whpe.qrcode.jiangxi_jian.parent.ParentActivity;


/**
 * Created by yang on 2018/9/30.
 */

public class ActivityMain extends ParentActivity implements View.OnClickListener {
    private TextView tv_home,tv_my;
    private FrgHome frgHome;
    private FrgMyself frgMy;
    private ImageView iv_qrcode;
    private LoadQrcodeParamBean loadQrcodeParamBean=new LoadQrcodeParamBean();

    @Override
    protected void afterLayout() {
        Log.e("YC","PAEAM="+ ParamGSONUtils.beanToString(loadQrcodeParamBean));
        Log.e("YC","param="+sharePreferenceParam.getParamInfos());
    }

    @Override
    protected void beforeLayout() {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        loadQrcodeParamBean = (LoadQrcodeParamBean) JsonComomUtils.parseAllInfo(sharePreferenceParam.getParamInfos(),loadQrcodeParamBean);
    }

    @Override
    protected void setActivityLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onCreateInitView() {
        showHome();
        tv_home.setOnClickListener(this);
        tv_my.setOnClickListener(this);
        iv_qrcode.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        tv_home=(TextView)findViewById(R.id.tv_home);
        tv_my=(TextView)findViewById(R.id.tv_my);
        iv_qrcode = (ImageView)findViewById(R.id.iv_qrcode);
    }

    private void showHome(){
        tv_home.setTextColor(getResources().getColor(R.color.app_theme));
        tv_my.setTextColor(getResources().getColor(R.color.comon_text_black_less));
        tv_home.setCompoundDrawablesWithIntrinsicBounds(null, MyDrawableUtils.getDrawble(this,R.drawable.aty_main_home_select),null,null);
        tv_my.setCompoundDrawablesWithIntrinsicBounds(null,MyDrawableUtils.getDrawble(this,R.drawable.aty_main_myselfnoselect),null,null);

        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgHome = new FrgHome();
        ft.replace(R.id.frame_content, frgHome);
        ft.commitAllowingStateLoss();*/
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(frgHome==null){
            frgHome = new FrgHome();
            ft.add(R.id.frame_content, frgHome);
        }
        if(frgMy!=null){
            ft.hide(frgMy);
        }
        ft.show(frgHome);
        ft.commitAllowingStateLoss();

    }

    private void showMy(){
        tv_home.setTextColor(getResources().getColor(R.color.comon_text_black_less));
        tv_my.setTextColor(getResources().getColor(R.color.app_theme));
        tv_home.setCompoundDrawablesWithIntrinsicBounds(null,MyDrawableUtils.getDrawble(this,R.drawable.aty_main_home_noselect),null,null);
        tv_my.setCompoundDrawablesWithIntrinsicBounds(null,MyDrawableUtils.getDrawble(this,R.drawable.aty_main_myselfselect),null,null);

        /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgMy = new FrgMyself();
        ft.replace(R.id.frame_content, frgMy);
        ft.commitAllowingStateLoss();*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(frgMy==null){
            frgMy = new FrgMyself();
            ft.add(R.id.frame_content, frgMy);
        }
        if(frgHome!=null){
            ft.hide(frgHome);
        }
        ft.show(frgMy);
        ft.commitAllowingStateLoss();

    }




    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.tv_home){
            showHome();
        }else if(id==R.id.tv_my){
            showMy();
        }else if(id==R.id.iv_qrcode){
            //useOkhttpDownload();
            if(sharePreferenceLogin.getLoginStatus()){
                transAty(ActivityQrcode.class);
            }else {
                transAty(ActivityLogin.class);
            }
        }
    }
}
