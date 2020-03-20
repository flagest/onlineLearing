package com.xuecheng.api.ucenter;

import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wu on 2020/3/12 0012
 */
@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {

    @ApiOperation("根据账户查询用户信息")
    public XcUserExt getUserext(String username);
}
