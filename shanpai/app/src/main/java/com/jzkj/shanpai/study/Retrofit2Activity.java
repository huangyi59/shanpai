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
 *
 * Type是java编程语言中所有类型的公共高级接口，他们包括原始类型、参数化类型、数组类型、类型变量和基本类型
 * <p>
 * Observable被观察者 抽象类 常用实现者 LamdaObserable ObserablFromArray ObserableCreate
 * Observe 接口
 * suscibe 订阅方法 调用 scribleArtual抽象方法在子类实现调用
 * ObserableEmitter
 * 操作符 just（ObserableFromArray） flatMap（）
 * RxJava基于事件流的异步操作库
 * flatMap将一个发送事件的上游Observable变换为多个发送事件的Observables
 * RXJAVA的好处是帮我处理线程之间的切换，我们可以指定，订阅在那个线程，观察在那个线程
 * <p>
 * 边界使得你可以在用于泛型参数上设置限制条件
 * <?>无界通配符看起来以为着任何事物
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
        test1();
        //test4();
        test5();
        //test6();
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
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void test3() {
        Observable.just(1, 4, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 4;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                    }
                });
    }

    private void test4() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                return response(integer);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, s);
            }
        });
    }

    private void test5() {
        Observable
                .just("1")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return 1;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, integer + "秒----------------------");
            }
        });
    }

    private void test6() {
        Observable.just(5, 4, 3, 2, 1, 0)
                //.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .delay(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread thread = Thread.currentThread();
                        Log.e(TAG, integer + "秒" + thread.getName());
                    }
                });
    }

    private Observable<String> response(Integer integer) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
            }
        });
    }

}
