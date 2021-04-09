package com.whpe.qrcode.jiangxi_jian.activity.custombus;


import androidx.fragment.app.FragmentTransaction;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;
import com.whpe.qrcode.jiangxi_jian.database.DBCustombusHelper;
import com.whpe.qrcode.jiangxi_jian.fragment.FrgHome;
import com.whpe.qrcode.jiangxi_jian.fragment.FrgMyself;
import com.whpe.qrcode.jiangxi_jian.fragment.custombuspassenerinfo.FrgAddPassengerInfo;
import com.whpe.qrcode.jiangxi_jian.fragment.custombuspassenerinfo.FrgShowPassengerinfo;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;

/**
 * Created by yang on 2019/4/24.
 */

public class ActivityCustomBusPassengerInfo extends NormalTitleActivity{
    private FrgShowPassengerinfo frgShowPassengerinfo;
    private FrgAddPassengerInfo frgAddPassengerInfo;
    private int frg_flag=0;
    public DBCustombusHelper dbCustombusHelper;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
        initDataBase();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_custombus_passengerinfo);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.custombuspassengerinfo_title));
        seeShow();
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
    }

    private void initDataBase() {
        dbCustombusHelper=new DBCustombusHelper(this);
    }

    public void seeAdd(){
        frg_flag=1;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgAddPassengerInfo = new FrgAddPassengerInfo();
        ft.replace(R.id.fl_content, frgAddPassengerInfo);
        ft.commitAllowingStateLoss();
    }

    public void seeShow(){
        frg_flag=0;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        frgShowPassengerinfo = new FrgShowPassengerinfo();
        ft.replace(R.id.fl_content, frgShowPassengerinfo);
        ft.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        if(frg_flag==0){
            finish();
        }
        if(frg_flag==1){
            seeShow();
        }
    }
}
