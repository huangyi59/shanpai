package com.jzkj.shanpai.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.jzkj.shanpai.R;

/**
 * 明确自定的View的基本元素
 * 文本，进度条、圆角矩形、圆形、圆弧、是否要操作画布
 */
public class CustomeView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mDotPaint;
    private Paint mArcPaint;
    private Context mContext;
    private float mProgress;
    private Bitmap mBitmap;

    public CustomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CustomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        //刻度画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);

        //文字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(20);
        mTextPaint.setTextSize(30);

        //圆点画笔
        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setColor(Color.BLACK);
        mDotPaint.setStrokeWidth(20);

        //圆弧画笔
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.BLACK);
        mArcPaint.setStrokeWidth(5);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //低30位存放 SpecSize
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //高两位存放 SpecMode
        int widthMode = MeasureSpec.getMode(heightMeasureSpec);
        //子View的MeasureSpec 由父View的MeasureSpec和自身的LayoutParams决定
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArcScale(canvas);
        drawText(canvas);
        drawDot(canvas);
        drawArc(canvas);
        drawBitmap(canvas);
    }

    /**
     * 画刻度
     */
    private void drawArcScale(Canvas canvas) {
        //保存当前坐标系的初始位置和方向
        canvas.save();

        int mWidth = getWidth();
        int mHeight = getHeight();
        for (int i = 0; i < 100; i++) {
            //(x,y) -> (endX,endY)
            canvas.drawLine(mWidth / 2, 0, mHeight / 2, dip2px(mContext, 10), mPaint);
            // 旋转的度数 = 100 / 360
            canvas.rotate(3.6f, mWidth / 2, mHeight / 2);
        }

        //取出上一次保存的画笔
        canvas.restore();
    }

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        String text = (int) mProgress + "%";
        //获取文字所在的矩形范围
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        canvas.drawText(text, (getWidth() - width) / 2, (getHeight() - height) / 2, mTextPaint);
    }

    /**
     * 画圆点
     */
    private void drawDot(Canvas canvas) {
        canvas.save();
        //旋转的角度 围绕旋转的圆心
        canvas.rotate(mProgress * 3.6f, getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(getWidth() / 2, dip2px(mContext, 10 + 5), dip2px(mContext, 3), mDotPaint);
        canvas.restore();
    }

    /**
     * oval 圆弧所在区域
     * startAngle 弧线的起点的角度
     * sweepAngle 所画弧线的长度
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        canvas.save();
        canvas.drawArc(new RectF(0, 10, getWidth(), getHeight()), 180, 180, false, mArcPaint);
        canvas.restore();
    }

    private void drawBitmap(Canvas canvas) {
        try {
            canvas.save();
            canvas.rotate(mProgress * 3.6f, getWidth() / 2, getHeight() / 2);
            canvas.drawBitmap(mBitmap, 0, 0, null);
            canvas.restore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAnimator() {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(5000);
        //REVERSE RESTART
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                //UI线程调用
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private void start() {
        //子线程做耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 100) {
                        mProgress = 0;
                    }
                    //非UI现场调用
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

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
