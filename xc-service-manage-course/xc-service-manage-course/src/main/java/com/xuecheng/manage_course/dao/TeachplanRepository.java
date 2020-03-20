package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface TeachplanRepository extends JpaRepository<Teachplan,String> {
    //根据parentId==0和CourseId==“ ？”来查询Teachplan
    public List<Teachplan> findByCourseidAndParentid( String courseid,String parentid);
}
