package com.whpe.qrcode.jiangxi_jian.parent;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.whpe.qrcode.jiangxi_jian.R;
import com.whpe.qrcode.jiangxi_jian.bigtools.MyDrawableUtils;


/**
 * Created by yang on 2018/9/29.
 */

public class BackgroundTitleActivity extends ParentActivity {

    private RelativeLayout rl_title;

    @Override
    protected void afterLayout() {
        rl_title = findViewById(R.id.rl_title);
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) rl_title.getLayoutParams();
        lp.setMargins(0,result,0,0);
    }

    @Override
    protected void beforeLayout() {

    }

    @Override
    protected void setActivityLayout() {

    }

    @Override
    protected void onCreateInitView() {

    }

    @Override
    protected void onCreatebindView() {

    }

    protected void setMyTitleColor(int color){
        if(rl_title==null){
            rl_title = findViewById(R.id.rl_title);
        }
        rl_title.setBackgroundColor(MyDrawableUtils.getColor(this,color));
    }


}
