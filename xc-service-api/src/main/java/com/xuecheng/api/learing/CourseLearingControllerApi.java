package com.xuecheng.api.learing;

import com.xuecheng.framework.domain.learning.respones.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wu on 2020/3/10 0010
 */
@Api(value = "录播课程学习管理", description = "录播课程学习管理")
public interface CourseLearingControllerApi {

    @ApiOperation("获取课程学习地址")
    public GetMediaResult getmedia(String courseId, String teachplanId);

}
