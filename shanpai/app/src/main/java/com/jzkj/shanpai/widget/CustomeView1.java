package com.jzkj.shanpai.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.jzkj.shanpai.R;

public class CustomeView1 extends View {

    private Paint mArcPaint;
    private Paint mTextPaint;
    private Bitmap mBtimap;
    private float mProgress;

    public CustomeView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomeView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.BLACK);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(5);


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeCap(Paint.Cap.ROUND);
        mTextPaint.setStrokeWidth(10);
        mTextPaint.setTextSize(20);

        mBtimap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProgress++;
                    if(mProgress == 50){
                        mProgress = 0;
                    }
                    //在非UI线程使用
                    postInvalidate();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
        drawArc(canvas);
        drawText(canvas);
    }

    /**
     * 绘制图片
     */
    private void drawBitmap(Canvas canvas) {
        canvas.save();
        canvas.rotate(mProgress * 3.6f, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(mBtimap, 0, (getHeight() - mBtimap.getHeight())/2, null);
        canvas.restore();
    }

    /**
     * 绘制圆弧
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawArc(Canvas canvas) {
        canvas.drawArc(5, 5, getWidth() - 5, getHeight() - 5, 180, 180, false, mArcPaint);
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        String text = (int) mProgress * 2 + "%";
        Rect bouods = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bouods);
        canvas.drawText(text, (getWidth() - bouods.width()) / 2, (getHeight() - bouods.height()) / 2, mTextPaint);
    }

}
