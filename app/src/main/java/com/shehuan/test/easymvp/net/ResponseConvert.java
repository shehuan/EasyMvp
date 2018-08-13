package com.shehuan.test.easymvp.net;


import com.shehuan.test.easymvp.base.BaseResponse;
import com.shehuan.test.easymvp.net.exception.ApiException;

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
        if (!"0".equals(baseResponse.getErrorCode())) {
            // 手动抛出异常
            throw new ApiException(baseResponse.getErrorCode(), baseResponse.getErrorMsg());
        }

        return baseResponse.getData();
    }
}
