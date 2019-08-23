package com.jzkj.shanpai.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.JavaBean;
import com.jzkj.shanpai.study.android.impl.AccessApiService;
import com.jzkj.shanpai.util.RetrofitUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Chain.process(request) ->Intercept.intercept(chain) chain.process ->Intercept.intercept(chain)
 * <p>
 * Type是java编程语言中所有类型的公共高级接口，他们包括原始类型、参数化类型、数组类型、类型变量和基本类型
 * <p>
 * 创建操作符 crate just fromIterable fromArray range timer interval intervalRange
 * 转换操作符
 */
public class Retrofit2Activity extends AppCompatActivity {

    private static final String TAG = Retrofit2Activity.class.getName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @BindView(R.id.btn)
    Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        ButterKnife.bind(this);
        //test1();
        //test2();
        //test4();
        //test5();
        //test6();
        //test7();
    }

    private void test() throws IOException {
        AccessApiService service = RetrofitUtil.retrofit.create(AccessApiService.class);
        service.getCall().enqueue(new Callback<JavaBean>() {
            @Override
            public void onResponse(Call<JavaBean> call, Response<JavaBean> response) {
                JavaBean javaBean = response.body();
            }

            @Override
            public void onFailure(Call<JavaBean> call, Throwable t) {

            }
        });
    }


    private void test1() {
        //Observable 抽象类 implements ObserbleSource subcribe subscribeActual（）抽象方法
        //ObservableOnSubscribe(可观察订阅) 接口 subscribe(ObservableEmitter<T> e) 发射器
        //ObservableCreate final类 Observable的子类
        //被观察者 发射器

        //被观察者
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe" + "    " + Thread.currentThread().getName());
                //获取发射器实例
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    private Disposable mDisposable;
                    private int anInt;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        mCompositeDisposable.add(d);
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, "onNext" + integer + "    " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    private void test2() {
        //观察者通知被观察者 某件事发生了改变
        Observable.just(1, 2, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
    }

    private void test3() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        Observable
                .fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept" + s);
                    }
                });
    }

    private void test4() {
        Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept" + aLong);
                    }
                });
    }

    private void test5() {
        Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "onNext" + aLong);
                        if (aLong == 5) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
    }

    private void test6() {
        Observable.intervalRange(2, 10, 5, 1, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept" + aLong);
                    }
                });
    }

    private void test7() {
        // Observable ->A Observable ->B
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Log.e(TAG, "accept" + o);
            }
        });
    }

    private void test8(){
        //只有subscribe 调用了 ，才能调用发射器
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {

            @Override
            public ObservableSource<String> apply(Integer o) throws Exception {
                return null;
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
