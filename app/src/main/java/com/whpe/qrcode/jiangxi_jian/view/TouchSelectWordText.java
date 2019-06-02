package com.whpe.qrcode.jiangxi_jian.view;

/**
 * Created by yang on 2018/7/18.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuffXfermode;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @author xwl
 * @Date 10/13/15
 */
public class TouchSelectWordText extends TextView {

    private final String TWO_CHINESE_BLANK = "  ";
    private BreakIterator iterator;
    private float dxLfet, dxRight, height;
    private final int DX = 5;
    private int mViewWidth;
    private int textHeight;
    private int mLineY;
    private boolean select = true;

    private SparseArray<List<WordTouchBean>> wordlist = new SparseArray<List<WordTouchBean>>();

    public TouchSelectWordText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        iterator = BreakIterator.getWordInstance(Locale.US);
    }

    public TouchSelectWordText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchSelectWordText(Context context, boolean select) {
        this(context, null);
        this.select = select;
    }

    public TouchSelectWordText(Context context) {
        this(context, null);
    }

    /**
     * 在listview中清除其它item 选词
     */
    public void clear() {
        dxLfet = 0;
        dxRight = 0;
        height = 0;
    }

    /**
     * 开启选词
     */
    public void openSelect() {
        select = true;
    }

    /**
     * 关闭选词
     */
    public void closeSelect() {
        select = false;
    }

    /**
     * 判断是否开启选词
     *
     * @return false为关闭，否则为开启
     */
    public boolean isOpenSelect() {
        return select;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!select) {
            clear();
            return false;
        }
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            clear();
            Layout layout = getLayout();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (layout != null) {
                int line = layout.getLineForVertical(y);
                height = textHeight * line;
                // int offset = layout.getOffsetForHorizontal(line, x);
                for (WordTouchBean word : wordlist.get(line)) {
                    if (word.getStart() < x && x < word.getEnd()) {
                        dxLfet = word.getStart();
                        dxRight = word.getEnd();
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.MULTIPLY));
        paint.setStyle(Style.FILL);// 实心矩形框
        canvas.drawRect(dxLfet, height + DX, dxRight, height + textHeight + DX, paint);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        mViewWidth = getMeasuredWidth();
        String text = getText().toString();
        mLineY = 0;
        mLineY += getTextSize();
        Layout layout = getLayout();

        if (layout == null) {
            return;
        }
        Paint.FontMetrics fm = paint.getFontMetrics();
        textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout.getSpacingAdd());

        for (int i = 0; i < layout.getLineCount(); i++) {
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            float width = StaticLayout.getDesiredWidth(text, lineStart, lineEnd, getPaint());
            String line = text.substring(lineStart, lineEnd);

            if (i < layout.getLineCount() - 1) {
                if (needScale(line)) {
                    drawScaledText(canvas, lineStart, line, width, i, true);
                } else {
                    drawScaledText(canvas, lineStart, line, width, i, false);
                }
            } else {
                drawScaledText(canvas, lineStart, line, width, i, false);
            }
            mLineY += textHeight;
        }
    }

    private void drawScaledText(Canvas canvas, int lineStart, String line, float lineWidth,
                                int indexLine, boolean isScale) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line)) {
            canvas.drawText(TWO_CHINESE_BLANK, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(TWO_CHINESE_BLANK, getPaint());
            x += bw;
            line = line.substring(3);
        }

        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288 && line.charAt(1) == 12288) {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            canvas.drawText(substring, x, mLineY, getPaint());
            x += cw;
            i += 2;
        }
        float d = isScale ? (mViewWidth - lineWidth) / gapCount : 0;
        iterator.setText(line);
        int start = iterator.first();
        List<WordTouchBean> words = new ArrayList<WordTouchBean>();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String possibleWord = line.substring(start, end);
            for (; i < start; i++) {
                String c = String.valueOf(line.charAt(i));
                float cw = StaticLayout.getDesiredWidth(c, getPaint());
                canvas.drawText(c, x, mLineY, getPaint());
                x += cw + d;
            }
            if ((possibleWord.charAt(0) <= 'Z' && possibleWord.charAt(0) >= 'A')
                    || (possibleWord.charAt(0) <= 'z' && possibleWord.charAt(0) >= 'a')) {
                WordTouchBean word = new WordTouchBean(x, possibleWord);
                for (int j = 0; j < possibleWord.length(); j++) {
                    String c = String.valueOf(possibleWord.charAt(j));
                    float cw = StaticLayout.getDesiredWidth(c, getPaint());
                    canvas.drawText(c, x, mLineY, getPaint());
                    if (c.equals(".")) { // 可能存在分词不准确，出现xx.xx
                        WordTouchBean w =
                                new WordTouchBean(word.getStart(), possibleWord.substring(0, j));
                        w.setEnd(x);
                        words.add(w);
                        x += cw + d;
                        word.setStart(x);
                        word.setWordText(possibleWord.substring(j + 1));
                    } else {
                        x += cw + d;
                    }
                }
                word.setEnd(x);
                words.add(word);
                i = end;
            }
        }
        wordlist.put(indexLine, words);
        for (; i < line.length(); i++) {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' ' && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {
        if (line == null || line.length() == 0) {
            return false;
        } else {
            return line.charAt(line.length() - 1) != '\n';
        }
    }

}