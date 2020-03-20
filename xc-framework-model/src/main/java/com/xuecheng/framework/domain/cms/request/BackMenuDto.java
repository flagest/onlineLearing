package com.xuecheng.framework.domain.cms.request;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author wu on 2019/11/30 0030
 */
@Data
@ToString
public class BackMenuDto {
    private Integer id;
    private String path;
    private String name;
    private String component;
    private String title;
    private String icon;
    private Integer parentId;
    private Integer hidden;
    private Integer breadcrumb;
    private Integer alwaysShow;

    private String redirect;

    private String activeMenu;
    /* List<BackgroundMenuDto> children;*/
    List<BackMenuDto> children;
}
