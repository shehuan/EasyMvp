package com.shehuan.easymvp.mvp.net;

import com.shehuan.easymvp.mvp.net.exceptions.ExceptionHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * 用发送异常数据的Observable来接管原始Observable
 *
 * @param <E>
 */
public class ExceptionConvert<E> implements Function<Throwable, ObservableSource<? extends E>> {
    @Override
    public ObservableSource<? extends E> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handle(throwable));
    }
}
