package com.xuecheng.manage_course.client;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wu on 2020/2/14 0014
 */
@FeignClient(value = "XC-SERVICE-MANAGE-CMS") //指定远程调用服务名
public interface CmsPageClient {
    //根据页面id去查询页面信息，远程调用CMS数据请求
    @GetMapping("/cms/page/get/{id}") //通过GetMapping远程调用http方式类型
    public CmsPage findById(@PathVariable("id") String id);

    //course要远程调用CMS的客户端
    @PostMapping("/cms/page/save")
    public CmsPageResult saveCmsPage(@RequestBody CmsPage cmsPage);

    //调用一键发布
    @PostMapping("/cms/page/postPageQuak")
    public CmsPostPageResult postPageQuak(@RequestBody CmsPage cmsPage);
}
