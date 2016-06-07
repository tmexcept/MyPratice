package com.animator.hu.myapplicationobjectanimator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by hu on 2016/5/3.
 * Description：
 */
public class CustomTextShowMore extends TextView{
    int maxLines = 3;
    String textsss = "创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题" +
            "创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题创建展览副标题";
    

    public CustomTextShowMore(Context context) {
        this(context, null);
    }

    public CustomTextShowMore(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextShowMore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);

        Resources resources = context.getResources();
    }

    Paint paintText;

    public void init(AttributeSet attrs, int defStyle) {
        paintText = new Paint(); 
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TimeAndDesZoneView, defStyle, 0);
        maxLines = a.getDimensionPixelSize(R.styleable.CustomTextShowMore_maxLines, maxLines);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Layout layout = getLayout();
        int lineCount = layout.getLineCount();
        String text = (String) TextUtils.ellipsize(layout.getText(), getPaint(), 15, TextUtils.TruncateAt.START);
        String text2 = (String) TextUtils.ellipsize("dddd23ddddddd23dddd2ddddd23dddd3dddd32323", getPaint(), 15, TextUtils.TruncateAt.END);
        
        Log.e("customText", "  lineCount = "+lineCount+"   text="+text+"   text2="+text2);
    }
}
