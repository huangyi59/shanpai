package com.jzkj.shanpai.study;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jzkj.shanpai.R;

import java.util.MissingResourceException;

/**
 * 属性动画是在Android3.0 API 11 后才提供的一种全新的动画模式
 * <p>
 * View动画分为3种 View动画 帧动画 属性动画
 * <p>
 * View动画是对View的影像做动画，并不是真的改变View的状态
 * <p>
 * 帧动画是顺序播放一组预先定义好的图片
 * <p>
 * 属性动画可以对任意对象的属性进行动画不仅仅是View,动画默认时间300ms，默认帧率10ms/帧
 * 属性动画要求动画作用的对象提供该属性的set方法，属性动画根据你传递的该属性的初始值和最终值，以动画的效果多次去调用set方法
 * </p>
 *
 * 使用profiler检测内存泄漏 垃圾桶代表执行垃圾回收机制
 * 打开profiler 点击类似下载箭头的图标(Dump Java heap) 然后就能看到java堆中内存分配的情况 选择查看方式，by package,
 * 找到我们可能出现内存泄漏的类，查看其引用关系 点击对应的类，右侧显示Intance View
 *
 */
public class AnimationActivity extends AppCompatActivity {
    private static final String TAG = AnimationActivity.class.getSimpleName();
    private ImageView mIvBg;
    private Button mBtn;
    private TextView mTvText;
    private IntEvaluator mEvaluator = new IntEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mIvBg = findViewById(R.id.tv_animal);
        mBtn = findViewById(R.id.btn);
        mTvText = findViewById(R.id.tv_test_animal);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //补间动画
        //alphaTest(2);
        //rotateTest();
        //scaleTest();
        //translationTest();
        //setTest();
        //testAnimator1();
        //testAnimator2();
        //testAnimator3();
        //testAnimator4();
        //testAnimator5();
        testAnimator6();
    }

    /**
     * 透明度
     */
    private void alphaTest(int method) {
        //xml
        if (method == 0) {
            Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_test);
            alphaAnimation.setDuration(3000);
            mIvBg.startAnimation(alphaAnimation);
        }

        //代码
        if (method == 1) {
            AlphaAnimation alphaAnimation1 = new AlphaAnimation(0, 1);
            alphaAnimation1.setDuration(3000);
            mIvBg.startAnimation(alphaAnimation1);
        }


        ObjectAnimator.ofFloat(mIvBg, "alpha", 0, 1).setDuration(3000).start();
    }

    /**
     * 旋转
     */
    private void rotateTest() {
        //degrees 角度
        RotateAnimation rotateAnimation = new RotateAnimation(0, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        mIvBg.startAnimation(rotateAnimation);

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "onAnimationRepeat");
            }
        });
    }

    /**
     * 缩放
     */
    private void scaleTest() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_test);
        mIvBg.startAnimation(animation);
    }

    /**
     * 平移
     */
    private void translationTest() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 200, 0, 200);
        translateAnimation.setDuration(3000);
        mIvBg.startAnimation(translateAnimation);
    }

    /**
     * set
     */
    private void setTest() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(5000);
        animationSet.addAnimation(new AlphaAnimation(0,1));
        animationSet.addAnimation(new TranslateAnimation(0,200,0,10));
        animationSet.addAnimation(new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
        mIvBg.startAnimation(animationSet);
    }

    /**
     * ViewGroup使用动画 LayoutAnimation
     */
    private void testLayoutAnimal() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.set0);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);//子View间隔150ms
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        ListView listView = new ListView(this);
        listView.setLayoutAnimation(controller);
    }

    /**
     * 属性动画实现平移
     */
    private void testAnimator1(){
        //属性动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvBg, "translationX", 0, 300, 0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    private void testAnimator2(){
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(mIvBg, "backgroundColor", Color.RED, Color.BLUE);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new FloatEvaluator());
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //每播放一帧，onAnimationUpdate就会被调用一次
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
    }

    private void testAnimator3(){
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mIvBg, "rotationX", 0, 360),
                ObjectAnimator.ofFloat(mIvBg, "rotationY", 0, 180),
                ObjectAnimator.ofFloat(mIvBg, "rotation", 0, -90),
                ObjectAnimator.ofFloat(mIvBg, "translationX", 0, 90),
                ObjectAnimator.ofFloat(mIvBg, "translationY", 0, 90),
                ObjectAnimator.ofFloat(mIvBg, "scaleX", 1, 1.5f),
                ObjectAnimator.ofFloat(mIvBg, "scaleY", 0, 0.5f),
                ObjectAnimator.ofFloat(mIvBg, "alpha", 1, 0.25f, 1));
        set.setDuration(5 * 1000).start();
    }

    private void testAnimator4(){
        ValueAnimator animator = ValueAnimator.ofInt(1, 500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获得当前动画的进度值，方便下面估值的时候使用
                int currentValue = (int) animation.getAnimatedValue();
                //获取当前进度占整个动画过程的比列
                //float fraction = animation.getAnimatedFraction();
                //mBtn.getLayoutParams().width = mEvaluator.evaluate(fraction,0,500);
                mBtn.getLayoutParams().width = currentValue;
                mBtn.requestLayout();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
        });
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void testAnimator5(){
        ViewWrapperHeight wrapper = new ViewWrapperHeight(mBtn);
        ObjectAnimator.ofInt(wrapper,"height",0,500).setDuration(5000).start();
    }


    private void testAnimator6() {
        ValueAnimator tvAnimator = ValueAnimator.ofInt(0, -500);
        tvAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                mTvText.scrollTo(currentValue, 0);
            }
        });
        tvAnimator.setDuration(5000);
        tvAnimator.setInterpolator(new LinearInterpolator());
        tvAnimator.start();
    }

    private static class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View mTarget) {
            this.mTarget = mTarget;
        }

        private void setWidth(int width) {
            mTarget.getLayoutParams().height = width;
            mTarget.requestLayout();
        }

        private int getWidth() {
            return mTarget.getLayoutParams().height;
        }
    }

    private static class ViewWrapperHeight {
        private View mTarget;

        public ViewWrapperHeight(View mTarget) {
            this.mTarget = mTarget;
        }

        private void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }

        private int getHeight() {
            return mTarget.getLayoutParams().height;
        }
    }

}
