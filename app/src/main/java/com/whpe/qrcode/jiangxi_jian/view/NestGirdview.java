package com.whpe.qrcode.jiangxi_jian.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by yang on 2019/4/18.
 */

public class NestGirdview extends GridView {
    public NestGirdview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestGirdview(Context context) {
        super(context);
    }

    public NestGirdview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
