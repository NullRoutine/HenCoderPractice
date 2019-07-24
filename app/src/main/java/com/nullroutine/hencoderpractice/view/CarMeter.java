package com.nullroutine.hencoderpractice.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;

/**
 * 汽车转速表
 * Created by tang.wangqiang on 2018/9/7.
 */

public class CarMeter extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint paintGuide = new Paint(Paint.ANTI_ALIAS_FLAG);//指针
    Path path = new Path();
    float speed;
    float radius = 20;
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "speed", 0f, 270f);

    public CarMeter(Context context) {
        super(context);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        invalidate();
    }

    public CarMeter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CarMeter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paintText.setTextSize(50);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setColor(Color.WHITE);
        paintGuide.setStyle(Paint.Style.FILL);
        paintGuide.setColor(Color.RED);
        objectAnimator.setDuration(15000);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        setBackgroundColor(Color.parseColor("#223039"));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        objectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        objectAnimator.end();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        canvas.save();
        canvas.translate(width, height);
        paint.setColor(Color.WHITE);
        canvas.drawArc(-500, -500, 500, 500, 135, 96, false, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(-500, -500, 500, 500, 231, 174, false, paint);
        canvas.rotate(-135, 0, 0);
//        canvas.drawLine(0, 0, 0, -360, paint);
        canvas.translate(-width, -height);
        for (int i = 0; i <= 45; i++) {
            canvas.save();
            canvas.translate(width, height);
            canvas.rotate(i * 6, 0, 0);
//            paint.setTextSize(16);
            if (i % 4 == 0) {
                if (i >= 16) {
                    paint.setColor(Color.RED);
                    paintText.setColor(Color.RED);
                } else {
                    paint.setColor(Color.WHITE);
                    paintText.setColor(Color.WHITE);
                }
                paint.setStrokeWidth(10);
                canvas.drawLine(0, -500, 0, -455, paint);
//                canvas.rotate(-i*6,0,0);
                canvas.drawText(i * 5 + "", 0, -400, paintText);
            } else {
                if (i >= 16) {
                    paint.setColor(Color.RED);
                } else {
                    paint.setColor(Color.WHITE);
                }
                paint.setStrokeWidth(5);
                canvas.drawLine(0, -500, 0, -470, paint);
            }
            canvas.translate(-width, -height);
            canvas.restore();
        }
        canvas.save();
        canvas.translate(width, height);
//        canvas.rotate(-135, 0, 0);
//        paint.setColor(Color.parseColor("#415762"));
        canvas.drawCircle(0, 0, radius, paintGuide);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, radius + 20, paint);
        canvas.rotate(speed);
        path.reset();//利用path画等腰三角形
        float lineStartX = radius * (float) Math.cos(45 / 180 * Math.PI);//圆弧中点的X坐标
        float lineStartY = radius * (float) Math.sin(45 / 180 * Math.PI);//圆弧中点的Y坐标
        path.moveTo(lineStartX, lineStartY);
        path.lineTo(0, -360);
        path.lineTo(-lineStartX, lineStartY);
        path.close();
        canvas.drawPath(path, paintGuide);
//        canvas.drawLine(0, 0, 0, -360, paintGuide);
        canvas.translate(-width, -height);
        canvas.restore();
        canvas.save();
        canvas.translate(width, height);
        canvas.rotate(135, 0, 0);
        DecimalFormat fnum = new DecimalFormat("##0");
        String dd = fnum.format(speed / 1.2);
        String speedStr = dd + " KM/H";
        Rect textBounds = new Rect();
        paintText.getTextBounds(speedStr, 0, speedStr.length(), textBounds);
        float yOffsets = -(textBounds.top + textBounds.bottom) / 2;
        paintText.setColor(Color.WHITE);
        canvas.drawText(speedStr, 0, 250 + yOffsets, paintText);
        canvas.translate(-width, -height);
        canvas.restore();
    }
}
