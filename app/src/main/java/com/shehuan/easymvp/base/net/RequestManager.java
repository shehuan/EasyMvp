package com.shehuan.easymvp.base.net;

import com.shehuan.easymvp.base.BasePresenter;
import com.shehuan.easymvp.base.BaseResponse;
import com.shehuan.easymvp.base.net.convert.ExceptionConvert;
import com.shehuan.easymvp.base.net.convert.ResponseConvert;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 单例类
 * <p>
 * 每一种execute()方法都有一个Disposable返回值，方便取消单个RxJava绑定关系
 */
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
    public <E> Disposable execute(BasePresenter presenter, Observable<BaseResponse<E>> observable, BaseObserver<E> observer) {
        observable
                .map(new ResponseConvert<E>())
                .onErrorResumeNext(new ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        presenter.addDisposable(observer.getDisposable());
        return observer.getDisposable();
    }

    /**
     * 通用耗时任务执行方法
     */
    public <E> Disposable commonExecute(BasePresenter presenter, final ExecuteListener<E> listener, BaseObserver<E> observer) {
        Observable.create(new ObservableOnSubscribe<E>() {
            @Override
            public void subscribe(ObservableEmitter<E> emitter) throws Exception {
                if (listener != null) {
                    emitter.onNext(listener.onExecute());
                }
                emitter.onComplete();
            }
        }).onErrorResumeNext(new ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        presenter.addDisposable(observer.getDisposable());
        return observer.getDisposable();
    }

    /**
     * 同时执行两个网络请求，统一处理请求结果
     */
    public <E1, E2, E3> Disposable zipExecute(BasePresenter presenter, Observable<BaseResponse<E1>> observable1, Observable<BaseResponse<E2>> observable2, final ZipExecuteListener<E1, E2, E3> listener, BaseObserver<E3> observer) {
        Observable
                .zip(observable1, observable2, new BiFunction<BaseResponse<E1>, BaseResponse<E2>, E3>() {
                    @Override
                    public E3 apply(BaseResponse<E1> baseResponse1, BaseResponse<E2> baseResponse2) throws Exception {
                        return listener.onExecuteResult(baseResponse1, baseResponse2);
                    }
                })
                .onErrorResumeNext(new ExceptionConvert<E3>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        presenter.addDisposable(observer.getDisposable());
        return observer.getDisposable();
    }

    /**
     * 依次执行两个网络请求
     */
    public <E1, E2> Disposable orderExecute(BasePresenter presenter, Observable<BaseResponse<E1>> observable1, final OrderExecuteListener<E1, E2> listener, BaseObserver<E2> observer) {
        observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<E1>, ObservableSource<BaseResponse<E2>>>() {
                    @Override
                    public ObservableSource<BaseResponse<E2>> apply(BaseResponse<E1> baseResponse) throws Exception {
                        return listener.onExecuteResult(baseResponse);
                    }
                })
                .map(new ResponseConvert<E2>())
                .onErrorResumeNext(new ExceptionConvert<E2>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        presenter.addDisposable(observer.getDisposable());
        return observer.getDisposable();
    }

    public interface ExecuteListener<E> {
        E onExecute();
    }

    public interface ZipExecuteListener<E1, E2, E3> {
        E3 onExecuteResult(BaseResponse<E1> baseResponse1, BaseResponse<E2> baseResponse2);
    }

    public interface OrderExecuteListener<E1, E2> {
        Observable<BaseResponse<E2>> onExecuteResult(BaseResponse<E1> baseResponse);
    }
}
