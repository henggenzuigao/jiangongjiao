package com.whpe.qrcode.jiangxi_jian.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.whpe.qrcode.jiangxi_jian.R;

import java.util.ArrayList;

/**
 * Created by yang on 2019/1/12.
 */

public class CustombusOrderinfoDateTextview extends View {
    private ArrayList<String> list_dates=new ArrayList<String>();
    private Paint paint;
    private int mTextColor;
    private float mTextSize;
    private float mColumnspace;
    private float mRowspace;
    private int desiredWidth;
    private int desiredHeight;

    public CustombusOrderinfoDateTextview(Context context) {
        this(context, null);
    }

    public CustombusOrderinfoDateTextview(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustombusOrderinfoDateTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initAttrs(attrs);
    }

    private void jisuansize() {
        if(list_dates.size()==0){
            return;
        }
        paint.setTextSize(mTextSize);
        float textWidth = paint.measureText(list_dates.get(0));
        desiredWidth=(int) (textWidth*3)+(int)mColumnspace*2;
        int rownum=0;
        for(int i=0;i<list_dates.size();i++){
            rownum=i/3+1;
        }
        desiredHeight=(int) (mTextSize*(rownum)+mRowspace*rownum);

    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustombusOrderinfoDateTextview);

        int textColor = a.getColor(R.styleable.CustombusOrderinfoDateTextview_codate_textColor, Color.BLACK);
        setTextColor(textColor);
        float textSize = a.getDimension(R.styleable.CustombusOrderinfoDateTextview_codate_textSize, sp2px(14));
        setTextSize(textSize);

        float clounmspace = a.getDimension(R.styleable.CustombusOrderinfoDateTextview_codate_Columnspace, sp2px(14));
        setColumnspace(clounmspace);
        float rowspace = a.getDimension(R.styleable.CustombusOrderinfoDateTextview_codate_Rowspace, sp2px(14));
        setRowspace(rowspace);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(mTextSize);
        paint.setColor(mTextColor);
        if(list_dates.size()==0){
            return;
        }
        for(int i=0;i<list_dates.size();i++){
            float textWidth = paint.measureText(list_dates.get(i));
            int textnum=i%3;
            int rownum=i/3;
            int x = (int) (textWidth*textnum)+(int)mColumnspace*textnum;
            int y = (int) (mTextSize*(rownum+1)+mRowspace*rownum)+2;
            drawText(canvas,list_dates.get(i),mTextColor,mTextSize,x,y);
        }
        //Log.e("YC","宽度="+widthSize+"  高度="+heightSize);
    }

    //两个参数是父View给的测量建议值MeasureSpec,代码执行到onMeasure(w,h),说明MyCircleView的measure(w,h)在执行中
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        desiredWidth = 100;
        desiredHeight = 100;
        jisuansize();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
            Log.e("YC","1");
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
            Log.e("YC","2="+width);

        } else {
            //Be whatever you want
            width = desiredWidth;
            Log.e("YC","3");
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }


    private void drawText(Canvas canvas, String text, @ColorInt int color, float size, int x, int y){
        paint.setColor(color);
        paint.setTextSize(size);
        canvas.drawText(text, x, y, paint);
        TextPaint textPaint = new TextPaint();
        textPaint.setARGB(0xFF, 0, 0, 0);
        textPaint.setTextSize(20.0F);
        textPaint.setAntiAlias(true);
    }

    public void setDateList(ArrayList<String> list_date){
        list_dates=list_date;
        invalidate();
    }

    /**
     * 设置文字颜色.
     *
     * @param textColor 文字颜色 {@link ColorInt}.
     */
    public void setTextColor(@ColorInt int textColor){
        this.mTextColor = textColor;
    }

    /**
     * 设置文字大小.
     *
     * @param textSize 文字大小 (sp).
     */
    public void setTextSize(float textSize){
        this.mTextSize = textSize;
    }

    /**
     * 设置横部宽度间隔
     *
     * @param columnspace 文字大小 (sp).
     */
    public void setColumnspace(float columnspace){
        this.mColumnspace = columnspace;
    }

    /**
     * 设置纵部高度间隔
     *
     * @param rowspace 文字大小 (sp).
     */
    public void setRowspace(float rowspace){
        this.mRowspace = rowspace;
    }

    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getContext().getResources().getDisplayMetrics());
    }
}
