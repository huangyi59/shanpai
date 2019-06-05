package com.jzkj.shanpai.study.android;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.myview.MyTextView;
import com.jzkj.shanpai.study.android.myview.MyViewGroup;
import com.jzkj.shanpai.study.android.myview.ScroView;

/**
 * android3.0后增加了x、y、translationX、translationY
 * scrollTo、scrollBy实现View内容的滑动,不能改变View在布局中的位置，mScrollX、mScrollY控制位置移动的大小
 *
 * <p>
 * 所谓点击事件的事件分发，其实就是对MotionEvent事件的分发过程 点击事件用MotionEvent表示
 * 当一个点击事件产生后，它的传递过程遵循如下顺序： Activity ->Window ->View(View没有onTouchEvent方法)
 * public static final int ACTION_MASK             = 0xff; & 0xff保持二进制数据的一致性
 * </p>
 *
 * <p>
 * ViewGroup无法拦截除了ACTION_DOWN以外的其他点击事件，ViewGroup在事件分发时，如果是ACTION_DOWN就会重置FLAG_DISALLOW_INTERCEPT这个标记位
 * 总是询问自己的onInterceptTouchEvent方法来询问自己是否要拦截事件
 * DOWN -> MOVE -> UP 一系列事件的分发过程 DOWN requetDisallowDispatchTouchEvent设置无效 down时会重置disallowFlag属性
 * 同一个事件序列是指从手指接触屏幕的那一刻起，到手指离开屏幕的那一刻结束
 * View的onTouchEvent默认都会消耗事件（返回true），除非他是不可点击的（clickable和longClickable同时为false）
 * 顶级View也叫根View，一般来说都是ViewGroup
 * 考虑一种情况，如果一个View的onTouchEvent返回false，那么他的父容器的onTouchEvent将会被调用，以此类推，如果所有的元素都不处理这个事件，那么这个事件将会
 * 最终传递给Activity处理
 * </p>
 *
 * <p>
 * FLAG_DISALLOW_INTERCEPET
 * view(OnTouchEvent返回false) 第一层的ViewGroup走父类的dispatchTouchEvent —>调用自己的OnTouchEvent返回false dispatchTrasnformedTouchEvent返回false
 * 再调用自己的(OnTouchEvent返回false) if getWindow().superDispatchTouchEvent(ev) == fasle Activity调用自己的ontouchEvent方法
 * FLAG_DISALLOW_INTERCEPT(影响到父类是否会执行拦截的方法) dispatchTransformedTouchEvent（执行事件分发的过程）
 * clickable 和 longclickable 一个为true，那么改view就会消耗这个事件，既onTouchEvent方法返回true，不管它是不是disalbe状态
 *
 * activity - phonewindow - decroview - viewgroup - dispatch -
 * <p/>
 *
 * 使用内部拦截法解决滑动冲突时，MOVE时间第一次会分发下去，当子view检测到MOVE事件分发下来后，会调用requestDisallowInterceptTouchEvent交由父控件处理
 * requestDisllowInterceptTouchEvent Flag_disallow_intercept
 */
public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ViewActivity.class.getSimpleName();
    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;
    private int mCount = 1;

    private MyTextView mTextView;
    private ScroView mScroView;
    private Button mButton;
    private MyViewGroup mLayout;

    private ValueAnimator animator;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SCROLL_TO:
                    mCount++;
                    if (mCount < FRAME_COUNT) {
                        float fraction = mCount / (float) FRAME_COUNT;
                        int scrollX = (int) (fraction * 100);
                        mButton.scrollTo(scrollX, 0);
                        Log.e(TAG, "scrollX:" + scrollX);
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        //getWindow().getDecorView().findViewById();
        mTextView = findViewById(R.id.tv_text);
        mTextView.setEnabled(false);
        mTextView.setClickable(false);
        mScroView = findViewById(R.id.view);
        mButton = findViewById(R.id.btn);
        mLayout = findViewById(R.id.layout);
        mTextView.setOnClickListener(this);
        mScroView.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mLayout.setOnClickListener(this);
        //mScroView.smoothScrollTo(200,300);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }

    private void scoller(int code) {
        switch (code) {
            case 1:
                /**
                 * view在水平方向和竖直方向的滑动距离
                 */
                int scrollX = mTextView.getScrollX();
                int scrollY = mTextView.getScrollY();
                Log.e(TAG, "scrollX:" + scrollX + ",scrollY:" + scrollY);

                /**
                 * View左上角的坐标
                 * x = left +translationX
                 * y = right + translationY
                 */
                float x = mTextView.getX();
                float y = mTextView.getY();
                Log.e(TAG, "X:" + x + ",Y:" + y);

                /**
                 * view左上角相对于父容器的偏移量
                 */
                float translationX = mTextView.getTranslationX();
                float translationY = mTextView.getTranslationY();
                Log.e(TAG, "translationX:" + translationX + ",translationY:" + translationY);
                break;
            case 2:
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mTextView.getLayoutParams();
                params.width -= 100;
                params.leftMargin += 100;
                mTextView.requestLayout();
                mTextView.setLayoutParams(params);
                break;
            case 3:
                mTextView.scrollBy(500, 600);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text:
                int left = mTextView.getLeft();
                int right = mTextView.getRight();
                int top = mTextView.getTop();
                int bottom = mTextView.getBottom();
                Log.e(TAG, "left:" + left + ",right:" + right + ",top:" + top + ",bottom:" + bottom);
                //ObjectAnimator.ofFloat(mTextView, "translationX", 0, 600).setDuration(1000).start();
                mTextView.scrollTo(60, 0);
                scoller(1);
                Log.e(TAG,"testview-CLICK ");
                break;
            case R.id.view:
                mTextView.scrollBy(-10, 0);
                scoller(1);
                break;
            case R.id.btn:
                mTextView.scrollBy(-10, 0);
                scoller(1);
                //setScrollTo();
                //setScrollTo2();
                break;
            case R.id.layout:
                Log.e(TAG,"layout-CLICK ");
                break;
            default:
                break;
        }
    }

    private void setScrollTo() {
        mButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator = ValueAnimator.ofInt(0, 50, 0).setDuration(1000);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        mButton.scrollTo(value, 0);
                        Log.e(TAG, "VALUE:" + value);
                    }
                });
                animator.setRepeatCount(1);
                animator.start();
            }
        }, 1000);
    }

    private void setScrollTo2() {
        Message message = Message.obtain();
        message.what = MESSAGE_SCROLL_TO;
        mHandler.sendMessage(message);
    }

}
