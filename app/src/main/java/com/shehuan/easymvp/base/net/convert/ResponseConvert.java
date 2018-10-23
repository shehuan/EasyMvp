package com.shehuan.easymvp.base.net.convert;


import com.shehuan.easymvp.base.BaseResponse;
import com.shehuan.easymvp.base.net.exception.ApiException;

import io.reactivex.functions.Function;

/**
 * 解析BaseResponse
 */
public class ResponseConvert<E> implements Function<BaseResponse<E>, E> {
    @Override
    public E apply(BaseResponse<E> baseResponse) {
        if (!"0".equals(baseResponse.getErrorCode())) {
            // 手动抛出异常
            throw new ApiException(baseResponse.getErrorCode(), baseResponse.getErrorMsg());
        }

        return baseResponse.getData();
    }
}
