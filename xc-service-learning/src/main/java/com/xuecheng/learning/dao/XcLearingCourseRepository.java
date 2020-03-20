package com.xuecheng.learning.dao;

import com.xuecheng.framework.domain.learning.XcLearningCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author wu on 2020/3/15 0015
 */
public interface XcLearingCourseRepository extends JpaRepository<XcLearningCourse, String> {
    //根据公用户id和课程id去查询
    XcLearningCourse findByUserIdAndCourseId(@Param("userId") String userId,@Param("courseId") String courseId);
//    XcLearningCourse findBycAndCourseIdAndUserId(@Param("userId") String userId,@Param("courseId") String courseId);
}
