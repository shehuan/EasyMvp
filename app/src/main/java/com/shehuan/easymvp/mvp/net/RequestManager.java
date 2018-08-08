package com.shehuan.easymvp.mvp.net;

import com.shehuan.easymvp.mvp.model.BackResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
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

    public <E> void subscribe(Observable<BackResult<E>> observable, Observer<E> observer) {
        observable
                .map(new Function<BackResult<E>, E>() {
                    @Override
                    public E apply(BackResult<E> backResult) throws Exception {
                        // 响应异常
                        if (!"success code".equals(backResult.getCode())) {
                            // 手动抛出异常
                            throw new ApiException(backResult.getCode(), backResult.getExceptions());
                        }

                        return backResult.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public <E1, E2, E3> void zipSubscribe(Observable<BackResult<E1>> observable1, Observable<BackResult<E2>> observable2, Observer<E3> observer) {
        Observable
                .zip(observable1, observable2, new BiFunction<BackResult<E1>, BackResult<E2>, E3>() {
                    @Override
                    public E3 apply(BackResult<E1> backResult1, BackResult<E2> backResult2) throws Exception {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public <E1, E2> void linkSubscribe(Observable<BackResult<E1>> observable1, final Observable<BackResult<E2>> observable2, Observer<E2> observer) {
        observable1
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<BackResult<E1>>() {
                    @Override
                    public void accept(BackResult<E1> e1BackResult) throws Exception {

                    }
                })
                .flatMap(new Function<BackResult<E1>, ObservableSource<BackResult<E2>>>() {
                    @Override
                    public ObservableSource<BackResult<E2>> apply(BackResult<E1> e1BackResult) throws Exception {
                        return observable2;
                    }
                })
                .map(new Function<BackResult<E2>, E2>() {
                    @Override
                    public E2 apply(BackResult<E2> backResult) throws Exception {
                        // 响应异常
                        if (!"success code".equals(backResult.getCode())) {
                            // 手动抛出异常
                            throw new ApiException(backResult.getCode(), backResult.getExceptions());
                        }

                        return backResult.getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
