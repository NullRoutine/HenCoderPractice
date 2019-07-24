package com.nullroutine.hencoderpractice.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by tang.wangqiang on 2018/10/9.
 */

public class TestLinearLayout extends LinearLayout {
    public TestLinearLayout(Context context) {
        super(context);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "ViewGroup的dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {//返回true不往下分发
        Log.e("TAG", "ViewGroup的onInterceptTouchEvent");
//        return true;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "ViewGroup的onTouchEvent");
        return super.onTouchEvent(event);
    }
}
