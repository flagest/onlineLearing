package com.xuecheng.api.config.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author wu on 2019/12/21 0021
 */
@Api(value = "cms页面管理接口", description = "cms页面管理接口，实现增，删，改，查")
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每一页记录数", required = true, paramType = "path", dataType = "int")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    //新增页面
    @ApiOperation("新增页面")
    CmsPageResult add(CmsPage cmsPage);

    //根据id查询页面信息
    @ApiOperation("根据id查询页面信息")
    CmsPage findById(String id);

    //修改页面
    @ApiOperation("修改页面")
    CmsPageResult edit(String id, CmsPage cmsPage);

    //删除页面
    @ApiOperation("删除页面")
    ResponseResult delete(String id);

    //发布页面
    @ApiOperation("发布页面")
    ResponseResult post(String pageId);

    //保存页面
    @ApiOperation("保存页面")
    CmsPageResult save(CmsPage cmsPage);

    //页面一键发布
    @ApiOperation("页面一键发布")
    CmsPostPageResult postPageQuak(CmsPage cmsPage);
}
