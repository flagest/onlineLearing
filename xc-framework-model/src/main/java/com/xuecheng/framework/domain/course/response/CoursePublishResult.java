package com.xuecheng.framework.domain.course.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wu on 2020/2/15 0015
 */
@Data
@NoArgsConstructor
@ToString
public class CoursePublishResult extends ResponseResult {
    //页面预览url，必须得到页面id才可以拼装
    private String previewUri;

    public CoursePublishResult(ResultCode resultCode, String previewUri) {
        super(resultCode);
        this.previewUri = previewUri;
    }
}
