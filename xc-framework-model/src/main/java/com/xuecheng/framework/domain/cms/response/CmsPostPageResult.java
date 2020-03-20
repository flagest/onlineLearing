package com.xuecheng.framework.domain.cms.response;

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
public class CmsPostPageResult extends ResponseResult {
    private String pageUrl;

    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        //调用父类中构造方法
        super(resultCode);
        this.pageUrl = pageUrl;

    }
}
