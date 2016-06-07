package com.hu.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hu.R;

public class TextShowMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textshowmore);
        final TextView textView = (TextView) findViewById(R.id.textcustom);

        // 监听布局变化，直接获取显示的长度
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if(textView.getWidth()>0){
                    TextPaint paint  = textView.getPaint();

                    int paddingLeft = textView.getPaddingLeft();
                    int paddingRight= textView.getPaddingRight();

                    int bufferWidth =(int) paint.getTextSize()*3;//缓冲区长度，空出两个字符的长度来给最后的省略号及图片
                    // 计算出2行文字所能显示的长度
                    int availableTextWidth = (textView.getWidth() - paddingLeft - paddingRight) * 3- 100;
                    // 根据长度截取出剪裁后的文字//估计是根据实际显示到屏幕里的像素长度截取文字
                    String ellipsizeStr = (String) TextUtils.ellipsize(textView.getText(), (TextPaint) paint, availableTextWidth, TextUtils.TruncateAt.END);

                    Log.e("customText", "  textView.getText() = "+textView.getText());
                    Log.e("customText", availableTextWidth+"  ellipsizeStr = "+ellipsizeStr);

                    String text2 = (String) TextUtils.ellipsize("dddd23ddddddd23dddd2ddddd23dddd3dddd32323", (TextPaint) paint, 15, TextUtils.TruncateAt.END);
                    Log.e("customText", "   text2="+text2);
                    
                    String imgTag = "img";
                    int start = ellipsizeStr.length();
                    int end = start + imgTag.length();
                    SpannableStringBuilder ssBuilder = new SpannableStringBuilder(ellipsizeStr+imgTag);
                    // 插入图片
                    Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                    drawable.setBounds(0, 0, 50, 50);
                    ImageSpan imgSpan = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
                    ssBuilder.setSpan(imgSpan, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(ssBuilder);

                    if(Build.VERSION.SDK_INT>=16){
                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }else{
                        textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void testEllipsize() {
        CharSequence s1 = "The quick brown fox jumps over \u00FEhe lazy dog.";
        // CharSequence s2 = new Wrapper(s1);
        CharSequence s2 = "The quick brown fox jumps over \u00FEhe lazy dog.";
        Spannable s3 = new SpannableString(s1);
        s3.setSpan(new StyleSpan(0), 5, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextPaint p = new TextPaint();
        p.setFlags(p.getFlags() & ~p.DEV_KERN_TEXT_FLAG);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 3; j++) {
                TextUtils.TruncateAt kind = null;

                switch (j) {
                    case 0:
                        kind = TextUtils.TruncateAt.START;
                        break;

                    case 1:
                        kind = TextUtils.TruncateAt.END;
                        break;

                    case 2:
                        kind = TextUtils.TruncateAt.MIDDLE;
                        break;
                }

                String out1 = TextUtils.ellipsize(s1, p, i, kind).toString();
                String out2 = TextUtils.ellipsize(s2, p, i, kind).toString();
                String out3 = TextUtils.ellipsize(s3, p, i, kind).toString();

                String keep1 = TextUtils.ellipsize(s1, p, i, kind, true, null).toString();
                String keep2 = TextUtils.ellipsize(s2, p, i, kind, true, null).toString();
                String keep3 = TextUtils.ellipsize(s3, p, i, kind, true, null).toString();

                String trim1 = keep1.replace("\uFEFF", "");
                Log.e("textcustom", out1+"   "+out2+"   "+out3+"    "+keep1+"    "+keep2+"    "+keep3);
            }
        }

    }
}
