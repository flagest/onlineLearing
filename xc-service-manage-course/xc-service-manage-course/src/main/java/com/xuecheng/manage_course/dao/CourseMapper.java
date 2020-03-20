package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
    CourseBase findCourseBaseById(String id);

    //pageHlper插件配置
    Page<CourseBase> fingCourseBase();

    //我的课程查询列表
    Page<CourseInfo> fingCourseListPage(CourseListRequest courseListRequest);
}
