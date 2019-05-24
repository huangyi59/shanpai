package com.jzkj.shanpai.study.android.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jzkj.shanpai.R;

/**
 * mesure完成以后，getMeasuredWidth,getMeasuredHeight获取View测量后的宽高
 *
 * layout决定了View的四个顶点的坐标和实际View的宽高 通过getTop getBottom getLeft getRight拿到4个顶点的位置
 * 通过getWidth getHeight拿到View最终的宽高
 *
 * daraw方法完成后View的内容才能呈现在屏幕上
 *
 * LayoutParams需要和父容器一起才能决定View的MeasureSpec
 *
 * View的measure过程
 *
 * ViewGroup的measuer过程
 */
public class CustomView extends View {

    private int mColor = Color.RED;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
       this(context,attrs,0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //obtainStyledAttributes
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColor = array.getColor(R.styleable.CircleView_circle_color,Color.RED);
        array.recycle();
        init();
    }

    private void init(){
        mPaint.setColor(mColor);
    }

    /**
     * layout_width：wrap_content 和使用 match_parent没有任何区别 对于直接继承View的空间需要对wrap_content做特殊处理
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
    }

}
