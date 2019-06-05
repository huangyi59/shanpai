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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
        if (hasFocus) {
            //补间动画
            RotateAnimation rotateAnimation = new RotateAnimation(0,359f,
                    Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateAnimation.setDuration(2000);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            mIvBg.startAnimation(rotateAnimation);
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.e(TAG,"onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e(TAG,"onAnimationEnd");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.e(TAG,"onAnimationRepeat");
                }
            });

            Animation animation = AnimationUtils.loadAnimation(this,R.anim.scale_test);
            mIvBg.startAnimation(animation);

        }
    }

    private void testLaoutAnimal(){
       Animation animation = AnimationUtils.loadAnimation(this,R.anim.set0);
       LayoutAnimationController controller = new LayoutAnimationController(animation);
       controller.setDelay(0.5f);//子View间隔150ms
       controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
       ListView listView = new ListView(this);
       listView.setLayoutAnimation(controller);
    }

    private void testObjectAnimator() {
        //属性动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIvBg, "translationX", 0, 300, 0);

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

        //ViewWrapperHeight wrapper = new ViewWrapperHeight(mBtn);
        //ObjectAnimator.ofInt(wrapper,"height",0,500).setDuration(5000).start();

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
            mTarget.getLayoutParams().width = width;
            mTarget.requestLayout();
        }

        private int getWidth() {
            return mTarget.getLayoutParams().width;
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
