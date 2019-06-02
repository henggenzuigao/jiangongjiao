package com.whpe.qrcode.jiangxi_jian.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.parent.NormalTitleActivity;


/**
 * Created by yang on 2018/11/7.
 */

public class ActivityAboutUs extends NormalTitleActivity implements View.OnClickListener {

    private TextView tv_version;
    private RelativeLayout rl_phone;
    private RelativeLayout rl_web;

    @Override
    protected void afterLayout() {
        super.afterLayout();
    }

    @Override
    protected void beforeLayout() {
        super.beforeLayout();
    }

    @Override
    protected void setActivityLayout() {
        super.setActivityLayout();
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    protected void onCreateInitView() {
        super.onCreateInitView();
        setTitle(getString(R.string.aboutus_title));
        tv_version.setText("V"+getVersionCustomName());
        rl_phone.setOnClickListener(this);
        rl_web.setOnClickListener(this);
    }

    @Override
    protected void onCreatebindView() {
        super.onCreatebindView();
        tv_version = (TextView)findViewById(R.id.tv_version);
        rl_phone = (RelativeLayout)findViewById(R.id.rl_phone);
        rl_web = (RelativeLayout)findViewById(R.id.rl_web);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if(id==R.id.rl_phone){
            Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.aboutus_phone)));
            startActivity(intent);
        }/*else if(id==R.id.rl_web){
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://"+getString(R.string.aboutus_weburl));
            intent.setData(content_url);
            startActivity(intent);

        }*/
    }
}
