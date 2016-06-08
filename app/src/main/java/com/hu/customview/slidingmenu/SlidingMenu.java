package com.hu.customview.slidingmenu;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.List;


public class SlidingMenu extends LinearLayout {

    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mMenuWidth;
    private int mContentWidth;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mMenuRightPadding;
    private Scroller mScroller;
    private int mLastX;
    private int mLastY;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private float scale;
    private boolean isOpen;

    public SlidingMenu(Context context) {
        this(context, null, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        //获取屏幕的宽和高
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        //设置Menu距离屏幕右侧的距离，convertToDp是将代码中的100转换成100dp
        mMenuRightPadding = convertToDp(context,100);
        mScroller = new Scroller(context);
        isOpen = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //拿到Menu，Menu是第0个孩子
        mMenu = (ViewGroup) getChildAt(0);
        //拿到Content，Content是第1个孩子
        mContent = (ViewGroup) getChildAt(1);
        //设置Menu的宽为屏幕的宽度减去Menu距离屏幕右侧的距离
        mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
        //设置Content的宽为屏幕的宽度
        mContentWidth = mContent.getLayoutParams().width = mScreenWidth;
        //测量Menu
        measureChild(mMenu,widthMeasureSpec,heightMeasureSpec);
        //测量Content
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec);
        //测量自己，自己的宽度为Menu宽度加上Content宽度，高度为屏幕高度
        setMeasuredDimension(mMenuWidth + mContentWidth, mScreenHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放Menu的位置，根据上面图可以确定上下左右的坐标
//        mMenu.layout(-mMenuWidth, 0, 0, mScreenHeight);
        mMenu.layout(0, 0, mMenuWidth, mScreenHeight);
        //摆放Content的位置
//        mContent.layout(0, 0, mScreenWidth, mScreenHeight);
        mContent.layout(mMenuWidth, 0, mScreenWidth + mMenuWidth, mScreenHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) ev.getX() - mLastXIntercept;
                int deltaY = (int) ev.getY() - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)){//横向滑动
                    intercept = true;
                }else{//纵向滑动
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    //// TODO: 2016/6/7 调用View的scrollTo()和scrollBy()是用于滑动View中的内容，而不是把某个View的位置进行改变。如果想改变莫个View在屏幕中的位置，可以使用如下的方法。
        //调用public void offsetLeftAndRight(int offset)用于左右移动方法或public void offsetTopAndBottom(int offset)用于上下移动。
        //如：button.offsetLeftAndRignt(300)表示将button控件向左移动300个像素。
    
    //// TODO: 2016/6/7 scrollTo(int x, int y) 是将View中内容滑动到相应的位置，
        // 参考的坐标系原点为parent View的左上角。
        // scrollTo(100, 100)是向左向上移动100像素，如果是负数则是向右向下移动view的内容
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();
                //拿到x方向的偏移量
                int dx = currentX - mLastX;
                Log.e("volley", dx+"  "+"   getScrollX() = "+getScrollX());
                if (dx > 0){//手指向右滑动
                    //边界控制，如果Menu已经完全显示，再滑动的话
                    //Menu左侧就会出现白边了,进行边界控制
                    if (getScrollX() - dx <= 0) {
                        //直接移动到（0，0）位置，不会出现白边
                        scrollTo(0, 0);
                    } else {//Menu没有完全显示呢
                        //其实这里dx还是-dx，大家不用刻意去记
                        //大家可以先使用dx，然后运行一下，发现
                        //移动的方向是相反的，那么果断这里加个负号就可以了
                        scrollBy(-dx, 0);
                    }
                    slidingMemu();

                }else{//手指向左滑动
                    //边界控制，如果Content已经完全显示，再滑动的话
                    //Content右侧就会出现白边了，进行边界控制
                    if (getScrollX() + Math.abs(dx) >= mMenuWidth) {
                        //直接移动到（mMenuWidth,0）位置，不会出现白边
                        scrollTo(mMenuWidth, 0);
                    } else {//Content没有完全显示呢 根据手指移动
                        scrollBy(-dx, 0);
                    }
                    slidingMemu();

                }
                mLastX = currentX;
                mLastY = currentY;
                scale = Math.abs((float)getScrollX()) / (float) mMenuWidth;
                break;

            case MotionEvent.ACTION_UP:
                if (getScrollX() > mMenuWidth / 2){//打开Menu
                    //调用startScroll方法，第一个参数是起始X坐标，第二个参数
                    //是起始Y坐标，第三个参数是X方向偏移量，第四个参数是Y方向偏移量
                    mScroller.startScroll(getScrollX(), 0, mMenuWidth - getScrollX(), 0, 300);
                    //设置一个已经打开的标识，当实现点击开关自动打开关闭功能时会用到
                    isOpen = true;
                    //一定不要忘了调用这个方法重绘，否则没有动画效果
                    invalidate();
                }else{//关闭Menu 同上
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 300);
                    isOpen = false;
                    invalidate();
                }

                break;
        }
        return true;
    }

    /**
     * 将传进来的数转化为dp
     */
    private int convertToDp(Context context , int num){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,num,context.getResources().getDisplayMetrics());
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            scale = Math.abs((float)getScrollX()) / (float) mMenuWidth;
            slidingMemu();
            invalidate();
        }
    }

    private void slidingMemu(){
        //translationX滑动是，向右滑动传正值，向左滑动传负值与Scroll相反
        mMenu.setTranslationX(getScrollX());
    }

    private void slidingMode3(){
        mMenu.setTranslationX(mMenuWidth + getScrollX() - (mMenuWidth/2)*(1.0f-scale));
        mMenu.setScaleX(0.7f + 0.3f*scale);
        mMenu.setScaleY(0.7f + 0.3f*scale);
        mMenu.setAlpha(scale);

        mContent.setScaleX(1 - 0.3f*scale);
        mContent.setPivotX(0);
        mContent.setScaleY(1.0f - 0.3f * scale);
    }

    /**
     * 点击开关，开闭Menu，如果当前menu已经打开，则关闭，如果当前menu已经关闭，则打开
     */
    public void toggleMenu(){
        if (isOpen){
            closeMenu();
        }else{
            openMenu();
        }
    }

    /**
     * 关闭menu
     */
    private void closeMenu() {
        //也是使用startScroll方法，dx和dy的计算方法一样
        mScroller.startScroll(getScrollX(),0, mMenuWidth,0,500);
        invalidate();
        isOpen = false;
    }

    /**
     * 打开menu
     */
    private void openMenu() {
        mScroller.startScroll(getScrollX(),0,-mMenuWidth,0,500);
        invalidate();
        isOpen = true;
    }
    
    public List<ViewPager> mViewPagers = new LinkedList<ViewPager>();
    public void addViewPager(ViewPager viewPager){
        if(viewPager != null){
            mViewPagers.add(viewPager);
        }
    }
    /**
     * 返回我们touch的ViewPager
     */
    private ViewPager getTouchViewPager(List<ViewPager> mViewPagers,
                                        MotionEvent ev) {
        if (mViewPagers == null || mViewPagers.size() == 0) {
            return null;
        }
        Rect mRect = new Rect();
        for (ViewPager v : mViewPagers) {
            v.getHitRect(mRect);

            if (mRect.contains((int) ev.getX(), (int) ev.getY())) {
                return v;
            }
        }
        return null;
    }
}
