package com.xuecheng.framework.domain.cms.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BackgroundMenu {
    private Integer id;

    private String title;

    private String name;

    private String icon;

    private String component;

    private String path;

    private Integer parentId;

    private Integer status;

    private Integer hidden;

    private Integer order;

    private String creatName;

    private Integer breadcrumb;

    private String redirect;

    private String activeMenu;
    private String alwaysShow;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String ylOne;

    private String ylTwo;

    private String ylThree;
    List<BackgroundMenu> children;
    private BackgroundMenu parent;

}