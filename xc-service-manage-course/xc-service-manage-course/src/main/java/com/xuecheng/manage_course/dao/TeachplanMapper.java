package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wu on 2020/2/11 0011
 */
@Mapper
public interface   TeachplanMapper {

    //课程查询计划
    public TeachplanNode selectList(String courseId);

}
