package com.xuecheng.framework.domain.learning.respones;

import com.xuecheng.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/5.
 */
@ToString
public enum LearingCode implements ResultCode {
    LEARING_GETMEDIA_ERROR(false, 23001, "获取学习地址失败！")
    ;
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    private LearingCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
