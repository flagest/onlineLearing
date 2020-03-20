package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

import java.io.Reader;

/**
 * 封装错误代码
 *
 * @author wu on 2020/1/27 0027
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }
}
