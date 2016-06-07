package com.hu.customview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hu.R;

/**
 * Created by hu on 2016/5/3.
 * Description：
 */
public class CustomTimeShowView extends View{
    int rectPadding = 20;
    int linePath = 10;
    int totleZone = 100;
    int leftTimeZone = 35;
    int leftDateZone = 45;
    int bottomZone = 80;
    int width;
    int height;
    int rectBoldWidth = 10;
    int rectInPathWidth = 10;
    int monthTextSize;
    int dayTextSize;
    int weakTextSize;
    int dateTextSize;
    int moreTextSize;

    int topLeftTop;
    int topRightTop;

    public CustomTimeShowView(Context context) {
        this(context, null);
    }

    public CustomTimeShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimeShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);

        Resources resources = context.getResources();
        monthTextSize = resources.getDimensionPixelSize(R.dimen.monthTextSize);
        dayTextSize = resources.getDimensionPixelSize(R.dimen.dayTextSize);
        weakTextSize = resources.getDimensionPixelSize(R.dimen.weakTextSize);
        dateTextSize = resources.getDimensionPixelSize(R.dimen.dateTextSize);
        moreTextSize = resources.getDimensionPixelSize(R.dimen.moreTextSize);
    }

    Paint paint;
    Paint paintText;

    public void init(AttributeSet attrs, int defStyle) {
        paintText = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);

        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TimeAndDesZoneView, defStyle, 0);
        rectBoldWidth = a.getDimensionPixelSize(R.styleable.TimeAndDesZoneView_rectBoldWidth, rectBoldWidth);
        rectInPathWidth = a.getDimensionPixelSize(R.styleable.TimeAndDesZoneView_rectInPathWidth, rectInPathWidth);
        rectPadding = a.getDimensionPixelSize(R.styleable.TimeAndDesZoneView_rectPadding, rectPadding);
        linePath = a.getDimensionPixelSize(R.styleable.TimeAndDesZoneView_linePath, linePath);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            width = right - left;
            height = bottom - top;
        }

        topLeftTop = weakTextSize + dayTextSize + monthTextSize + rectPadding * 2;
        topLeftTop = (height * bottomZone / totleZone - topLeftTop - rectPadding * 3 / 2) / 2 + rectPadding;

        topRightTop = monthTextSize * 2 + rectPadding;
        topRightTop = (height * bottomZone / totleZone - topRightTop - rectPadding * 3 / 2) / 2 + rectPadding;
    }

    /**
     * 获取文字的总的高度
     */
    private int getHeight(Paint paint, String str){
        Rect rect  = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(rectBoldWidth);

        canvas.drawRect(rectPadding, rectPadding, width- rectPadding, height- rectPadding, paint);
        paint.setStrokeWidth(linePath);
        canvas.drawLine(rectPadding,  rectPadding * 3 / 2,
                width-rectPadding, rectPadding * 3 / 2, paint);
        canvas.drawLine(rectPadding, height * bottomZone / totleZone-rectPadding,
                width-rectPadding, height * bottomZone / totleZone-rectPadding, paint);
        canvas.drawLine(width * leftTimeZone / totleZone, rectPadding * 3 / 2,
                width * leftTimeZone / totleZone, height * bottomZone / totleZone-rectPadding, paint);
        canvas.drawLine(width * leftDateZone / totleZone, height * bottomZone / totleZone-rectPadding,
                width * leftDateZone / totleZone, height-rectPadding, paint);

        paintText.setTextSize(monthTextSize);
        Rect rect  = new Rect();
        paintText.getTextBounds("5月", 0, 2, rect);
        float left = (width * leftTimeZone / totleZone - rectPadding - rect.width())/2;
        canvas.drawText("5月", left + rectPadding, topLeftTop + monthTextSize, paintText);

        paintText.setTextSize(dayTextSize);
        Rect dayRect  = new Rect();
        paintText.getTextBounds("25", 0, 2, dayRect);
        left = (width * leftTimeZone / totleZone - rectPadding - dayRect.width())/2;
        canvas.drawText("25", left + rectPadding, topLeftTop + monthTextSize + dayTextSize, paintText);

        paintText.setTextSize(weakTextSize);
        Rect weekRect  = new Rect();
        paintText.getTextBounds("星期六", 0, 3, weekRect);
        left = (width * leftTimeZone / totleZone - rectPadding - weekRect.width())/2;
        canvas.drawText("星期六", left + rectPadding, topLeftTop + weakTextSize + dayTextSize + monthTextSize + rectPadding, paintText);
        

        paintText.setTextSize(monthTextSize);
        paintText.getTextBounds("宜", 0, 1, rect);
        left = (width - width * leftTimeZone / totleZone - rect.width() - rectPadding)/2 + width * leftTimeZone / totleZone;
        canvas.drawText("宜", left - rectBoldWidth / 2, topRightTop + monthTextSize, paintText);
        paintText.getTextBounds("赏花赏月赏秋香", 0, 7, rect);
        left = (width - width * leftTimeZone / totleZone - rect.width() - rectPadding)/2 + width * leftTimeZone / totleZone;
        canvas.drawText("赏花赏月赏秋香", left - rectBoldWidth / 2, topRightTop + monthTextSize * 2 + rectPadding, paintText);
//        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
//        paint.setStrokeWidth(1);
//        canvas.drawLine(0, topRightTop + fontMetrics.bottom, width, topRightTop + fontMetrics.bottom, paint);

        paintText.setTextSize(dateTextSize);
        Rect dateRect  = new Rect();
        paintText.getTextBounds("农历【十一月二十五】", 0, 10, dateRect);
        left = (width * leftDateZone / totleZone - rectPadding - dateRect.width())/2 + rectPadding;
        canvas.drawText("农历【十一月二十五】", left,
                (height - bottomZone * height / totleZone - rectPadding)/2 + bottomZone * height / totleZone, paintText);


        paintText.setTextSize(dateTextSize);
        Rect desRect  = new Rect();
        paintText.getTextBounds("今日线下展览10场", 0, 9, desRect);
        left = width * leftDateZone / totleZone + 20;
        canvas.drawText("今日线下展览10场", left,
                (height - bottomZone * height / totleZone - rectPadding)/2 + bottomZone * height / totleZone, paintText);



        Paint textPaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(55);
        textPaint.setColor( Color.GREEN);

        // FontMetrics对象 
        Paint.FontMetrics fontMetricss = textPaint.getFontMetrics();
        String text = "abcdefghijklmnopqrstu宜";

        // 计算每一个坐标 
        float baseX = 0;
        float baseY = 100;
        float topY = baseY + fontMetricss.top;
        float ascentY = baseY + fontMetricss.ascent;
        float descentY = baseY + fontMetricss.descent;
        float bottomY = baseY + fontMetricss.bottom;
        float leading = baseY + fontMetricss.leading;


        Log.d("fontMetricss", "TextSize    is:" + textPaint.getTextSize());
        Log.d("fontMetricss", "baseY     is:" + baseY);
        Log.d("fontMetricss", "topY     is:" + topY);
        Log.d("fontMetricss", "ascentY  is:" + ascentY);
        Log.d("fontMetricss", "descentY is:" + descentY);
        Log.d("fontMetricss", "bottomY  is:" + bottomY);
        Log.d("fontMetricss", "leading  is:" + leading);

        // 绘制文本 
        canvas.drawText(text, baseX, baseY, textPaint);

        // BaseLine描画 
        Paint baseLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);

        baseLinePaint.setColor( Color.RED);
        canvas.drawLine(0, baseY, canvas.getWidth(), baseY, baseLinePaint);

        // Base描画 
        canvas.drawCircle( baseX, baseY, 5, baseLinePaint);

        // TopLine描画 
        Paint topLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        topLinePaint.setColor( Color.LTGRAY);
        canvas.drawLine(0, topY, canvas.getWidth(), topY, topLinePaint);

        // AscentLine描画 
        Paint ascentLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        ascentLinePaint.setColor( Color.GREEN);
        canvas.drawLine(0, ascentY, canvas.getWidth(), ascentY, ascentLinePaint);

        // DescentLine描画 
        Paint descentLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        descentLinePaint.setColor( Color.YELLOW);
        canvas.drawLine(0, descentY, canvas.getWidth(), descentY, descentLinePaint);

        // ButtomLine描画 
        Paint bottomLinePaint = new Paint( Paint.ANTI_ALIAS_FLAG);
        bottomLinePaint.setColor( Color.BLACK);
        canvas.drawLine(0, bottomY, canvas.getWidth(), bottomY, bottomLinePaint);
    }
}
