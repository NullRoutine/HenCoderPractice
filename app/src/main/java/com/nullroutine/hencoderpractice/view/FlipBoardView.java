package com.nullroutine.hencoderpractice.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.nullroutine.hencoderpractice.R;

/**
 * Created by tang.wangqiang on 2018/9/7.
 */

public class FlipBoardView extends View {

    private Bitmap bitmap;
    private Paint paint;
    Camera camera = new Camera();
    float yDegree;//绕y轴的角度
    float canvasDegree;//绕画布角度
    float y2Degree;//最后一部分旋转角度
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this, "yDegree", 0, -45);
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(this, "canvasDegree", 0, 270);
    ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(this, "y2Degree", 0, 30);
    AnimatorSet animatorSet = new AnimatorSet();
    private Handler handler = new Handler();

    public FlipBoardView(Context context) {
        super(context);
    }

    public FlipBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlipBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.flip_board);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
        objectAnimator1.setDuration(800);
        objectAnimator2.setDuration(1500);
        objectAnimator3.setDuration(800);
        animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        yDegree = 0;
                        canvasDegree = 0;
                        y2Degree = 0;
                        animatorSet.start();
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int x = width - bitmapWidth / 2;
        int y = height - bitmapHeight / 2;
        canvas.save();
        camera.save();
        canvas.translate(width, height);
        canvas.rotate(-canvasDegree);
        camera.rotateY(yDegree);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -height, width, height);//剪切矩形
        canvas.rotate(canvasDegree);
        canvas.translate(-width, -height);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
        canvas.save();
        canvas.translate(width, height);
        camera.save();
        canvas.rotate(-canvasDegree);
        canvas.clipRect(-width, -height, 0, height);
        camera.rotateY(y2Degree);
        camera.applyToCanvas(canvas);
        canvas.rotate(canvasDegree);
        canvas.translate(-width, -height);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }

    public float getyDegree() {
        return yDegree;
    }

    public void setYDegree(float yDegree) {
        this.yDegree = yDegree;
        invalidate();
    }

    public float getCanvasDegree() {
        return canvasDegree;
    }

    public void setCanvasDegree(float canvasDegree) {
        this.canvasDegree = canvasDegree;
        invalidate();
    }

    public float getY2Degree() {
        return y2Degree;
    }

    public void setY2Degree(float y2Degree) {
        this.y2Degree = y2Degree;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animatorSet.end();
    }
}
