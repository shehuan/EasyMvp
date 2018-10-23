package com.shehuan.easymvp.base.net.convert;


import com.shehuan.easymvp.base.net.exception.ExceptionHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * 用发送异常数据的Observable来接管原始Observable
 */
public class ExceptionConvert<E> implements Function<Throwable, ObservableSource<? extends E>> {
    @Override
    public ObservableSource<? extends E> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handle(throwable));
    }
}
