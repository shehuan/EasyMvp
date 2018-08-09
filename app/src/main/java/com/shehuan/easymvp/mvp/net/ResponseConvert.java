package com.shehuan.easymvp.mvp.net;


import com.shehuan.easymvp.mvp.model.BaseResponse;
import com.shehuan.easymvp.mvp.net.exceptions.ApiException;

import io.reactivex.functions.Function;

/**
 * 解析BaseResponse
 *
 * @param <E>
 */
public class ResponseConvert<E> implements Function<BaseResponse<E>, E> {
    @Override
    public E apply(BaseResponse<E> baseResponse) {
        // 响应异常
        if (!"your api success code".equals(baseResponse.getCode())) {
            // 手动抛出异常
            throw new ApiException(baseResponse.getCode(), baseResponse.getExceptions());
        }

        return baseResponse.getData();
    }
}
