package com.nullroutine.hencoderpractice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by tang.wangqiang on 2018/10/9.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "Activity的onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "Activity的dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}
