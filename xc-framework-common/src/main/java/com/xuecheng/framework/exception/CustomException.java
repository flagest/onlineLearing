package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 工程师自定义异常
 *
 * @author wu on 2020/1/27 0027
 */
public class CustomException extends RuntimeException {
    //错误代码
    ResultCode resultCode;

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;

    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
