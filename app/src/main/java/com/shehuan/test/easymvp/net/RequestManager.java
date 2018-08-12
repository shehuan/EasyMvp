package com.shehuan.test.easymvp.net;

import com.shehuan.test.easymvp.base.BaseResponse;

import io.reactivex.Observable;
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

    public <E> Disposable execute(Observable<BaseResponse<E>> observable, CommonObserver<E> observer) {
        observable
                .map(new ResponseConvert<E>())
                .onErrorResumeNext(new ExceptionConvert<E>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        return observer.getDisposable();
    }

    public <E1, E2, E3> void zipExecute(Observable<BaseResponse<E1>> observable1, Observable<BaseResponse<E2>> observable2, Observer<E3> observer) {
        Observable
                .zip(observable1, observable2, new BiFunction<BaseResponse<E1>, BaseResponse<E2>, E3>() {
                    @Override
                    public E3 apply(BaseResponse<E1> baseResponse1, BaseResponse<E2> baseResponse2) throws Exception {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public <E1, E2> void linkExecute(Observable<BaseResponse<E1>> observable1, final Observable<BaseResponse<E2>> observable2, Observer<E2> observer) {
        observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BaseResponse<E1>>() {
                    @Override
                    public void accept(BaseResponse<E1> e1baseResponse) throws Exception {

                    }
                })
                .flatMap(new Function<BaseResponse<E1>, ObservableSource<BaseResponse<E2>>>() {
                    @Override
                    public ObservableSource<BaseResponse<E2>> apply(BaseResponse<E1> e1baseResponse) throws Exception {
                        return observable2;
                    }
                })
                .map(new ResponseConvert<E2>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
