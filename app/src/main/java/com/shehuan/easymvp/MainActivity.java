package com.shehuan.easymvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//                emitter.onNext(4);
//                emitter.onNext(5);
//
//                Log.e(TAG, "emit end");
//
//            }
//        }).subscribe(new Observer<Integer>() {
//            Disposable disposable;
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG, "subscribe");
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Log.e(TAG, "" + value);
//
//                if (value == 3){
//                    disposable.dispose();
//                    Log.e(TAG, "disposed");
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "error");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "complete");
//            }
//        });

//        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//                emitter.onNext(4);
//                emitter.onNext(5);
//
//                Log.e(TAG, "emit end");
//
//            }
//        }).map(new Function<Integer, String>() {
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return integer + "...";
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, s);
//            }
//        });

//        Disposable disposable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
////                emitter.onError(new RuntimeException("big exception"));
//                emitter.onNext(4);
//                emitter.onNext(5);
//
//                Log.e(TAG, "emit end");
//
//            }
//        }).doOnNext(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e(TAG, "doOnNext:" + integer);
//            }
//        }).doOnError(new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.e(TAG, throwable.getMessage());
//            }
//        }).flatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                final List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add("I am value " + integer);
//                }
//                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, s);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.e(TAG, "error");
//            }
//        });

//        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onComplete();
//                emitter.onNext(4);
//                emitter.onNext(5);
//
//                Log.e(TAG, "emit end");
//
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                Log.e(TAG, "" + integer);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Log.e(TAG, "error");
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.e(TAG, "complete");
//            }
//        }, new Consumer<Disposable>() {
//            @Override
//            public void accept(Disposable disposable) throws Exception {
//                Log.e(TAG, "subscribe");
//            }
//        });

//        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                Log.e(TAG, "emit1 1");
//                emitter.onNext(1);
//                Log.e(TAG, "emit1 2");
//                emitter.onNext(2);
//                Log.e(TAG, "emit1 3");
//                emitter.onNext(3);
//                Log.e(TAG, "emit1 4");
//                emitter.onNext(4);
//                Log.e(TAG, "emit1 5");
//                emitter.onNext(5);
//                emitter.onComplete();
//                Log.e(TAG, "emit1 onComplete");
//
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                Log.e(TAG, "emi2 a");
//                emitter.onNext("a");
//                Log.e(TAG, "emi2 b");
//                emitter.onNext("b");
//                Log.e(TAG, "emi2 c");
//                emitter.onNext("c");
//                emitter.onComplete();
//                Log.e(TAG, "emit2 onComplete");
//
//            }
//        }).subscribeOn(Schedulers.io());
//
//        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer + s;
//            }
//        }).subscribe(new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG, "onNext " + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG, "complete");
//            }
//        });


//        Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; ; i++) {  //无限循环发送事件
//                    emitter.onNext(i);
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.d(TAG, "" + integer);
//                    }
//                });

        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                for (int i = 0; i < 139; i++) {
//                    Log.e(TAG, "emit " + i);
//                    emitter.onNext(i);
//                }

                Log.e(TAG, emitter.requested()+"");
            }
        }, BackpressureStrategy.BUFFER)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e(TAG, integer+"");
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
