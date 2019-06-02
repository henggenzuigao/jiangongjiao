package com.whpe.qrcode.jiangxi_jian.view.listener;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
import android.widget.ListView;

/**
 * Created by yang on 2019/4/18.
 */

public class NestListView extends ListView {
    public NestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestListView(Context context) {
        super(context);
    }

    public NestListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
