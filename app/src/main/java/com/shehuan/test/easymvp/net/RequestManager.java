package com.shehuan.test.easymvp.net;

import com.shehuan.test.easymvp.base.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RequestManager {
    private RequestManager() {

    }

    public static RequestManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RequestManager INSTANCE = new RequestManager();
    }

    /**
     * 通用网络请求方法
     */
    public <E> Disposable execute(Observable<BaseResponse<E>> observable, BaseObserver<E> observer) {
        observable
                .map(new ResponseConvert<E>())
                .onErrorResumeNext(new ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return observer.getDisposable();
    }

    /**
     * 通用耗时任务执行方法
     */
    public <E> Disposable execute(final TaskListener<E> listener, BaseObserver<E> observer) {
        Observable.create(new ObservableOnSubscribe<E>() {
            @Override
            public void subscribe(ObservableEmitter<E> emitter) throws Exception {
                if (listener != null) {
                    emitter.onNext(listener.doTask());
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return observer.getDisposable();
    }

    /**
     * 同时执行两个网络请求，统一处理请求结果
     */
    public <E1, E2, E3> void zipExecute(Observable<BaseResponse<E1>> observable1, Observable<BaseResponse<E2>> observable2, final ZipResultListener<E1, E2, E3> listener, Observer<E3> observer) {
        Observable
                .zip(observable1, observable2, new BiFunction<BaseResponse<E1>, BaseResponse<E2>, E3>() {
                    @Override
                    public E3 apply(BaseResponse<E1> baseResponse1, BaseResponse<E2> baseResponse2) throws Exception {
                        return listener.zipResult(baseResponse1, baseResponse2);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public <E1, E2> void linkExecute(Observable<BaseResponse<E1>> observable1, final LinkListener<E1, E2> listener, Observer<E2> observer) {
        observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseResponse<E1>>() {
                    @Override
                    public void accept(BaseResponse<E1> e1BaseResponse) throws Exception {

                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<E1>, ObservableSource<BaseResponse<E2>>>() {
                    @Override
                    public ObservableSource<BaseResponse<E2>> apply(BaseResponse<E1> baseResponse) throws Exception {
                        return listener.link(baseResponse);
                    }
                })
                .map(new ResponseConvert<E2>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public interface TaskListener<E> {
        E doTask();
    }

    public interface ZipResultListener<E1, E2, E3> {
        E3 zipResult(BaseResponse<E1> baseResponse1, BaseResponse<E2> baseResponse2);
    }

    public interface LinkListener<E1, E2> {
        Observable<BaseResponse<E2>> link(BaseResponse<E1> baseResponse);
    }
}
