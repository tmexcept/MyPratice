package com.animator.hu.myapplicationobjectanimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by hu on 2016/3/10.
 * Description：
 */
public class AnimatorView extends View {
    public AnimatorView(Context context) {
        super(context);
    }

    public AnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Point mPoint;
    Paint mPaint;
    final int RADIU = 50;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPoint == null){
            mPoint = new Point(RADIU, RADIU);
            mPaint = new Paint();
            mPaint.setColor(0xff88ee99);
            drawCircle(canvas);
            startAnimator();
        } else {
            drawCircle(canvas);
        }
    }

    void drawCircle(Canvas canvas){
        canvas.drawCircle(mPoint.getX(), mPoint.getY(), RADIU, mPaint);
    }

    void startAnimator(){
        ValueAnimator animator = new ValueAnimator().ofObject(new PointTypeEveluator(), mPoint,
                new Point((int)(getWidth()- RADIU), (int) (getHeight()-RADIU)));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new BounceInterpolator());
//        animator.setDuration(3000);
//        animator.start();

        ObjectAnimator anim = ObjectAnimator.ofObject(this, "color", new ColorTypeEvaluator(),
                "#0000FF", "#FF0000");

        AnimatorSet set = new AnimatorSet();
        set.play(animator).with(anim);
        set.setDuration(5000);
        set.start();
    }

    class PointTypeEveluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int x = (int) (startValue.getX() + fraction*(endValue.getX() - startValue.getX()));
            int y = (int) (startValue.getY() + fraction*(endValue.getY() - startValue.getY()));

            return new Point(x, y);
        }
    }

    class ColorTypeEvaluator implements TypeEvaluator{
        private int mCurrentRed = -1;

        private int mCurrentGreen = -1;

        private int mCurrentBlue = -1;

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            String startColor = (String) startValue;
            String endColor = (String) endValue;
            int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
            // 初始化颜色的值
            if (mCurrentRed == -1) {
                mCurrentRed = startRed;
            }
            if (mCurrentGreen == -1) {
                mCurrentGreen = startGreen;
            }
            if (mCurrentBlue == -1) {
                mCurrentBlue = startBlue;
            }
            // 计算初始颜色和结束颜色之间的差值
            int redDiff = Math.abs(startRed - endRed);
            int greenDiff = Math.abs(startGreen - endGreen);
            int blueDiff = Math.abs(startBlue - endBlue);
            int colorDiff = redDiff + greenDiff + blueDiff;
            if (mCurrentRed != endRed) {
                mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                        fraction);
            } else if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                        redDiff, fraction);
            } else if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                        redDiff + greenDiff, fraction);
            }
            // 将计算出的当前颜色的值组装返回
            String currentColor = "#" + getHexString(mCurrentRed)
                    + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
            return currentColor;
        }

        /**
         * 根据fraction值来计算当前的颜色。
         */
        private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                    int offset, float fraction) {
            int currentColor;
            if (startColor > endColor) {
                currentColor = (int) (startColor - (fraction * colorDiff - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (fraction * colorDiff - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        /**
         * 将10进制颜色值转换成16进制。
         */
        private String getHexString(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            return hexString;
        }
    }
}
