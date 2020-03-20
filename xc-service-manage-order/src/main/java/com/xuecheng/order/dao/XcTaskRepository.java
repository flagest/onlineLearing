package com.xuecheng.order.dao;

import com.xuecheng.framework.domain.task.XcTask;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

import java.util.Date;

/**
 * @author wu on 2020/3/15 0015
 */
public interface XcTaskRepository extends JpaRepository<XcTask, String> {
    //查询某个时间之前的记录
    Page<XcTask> findByUpdateTimeBefore(Pageable pageable, Date updateTimeBefore);

    //根据id去更新时间
    @Modifying
    @Query("update XcTask t set t.updateTime= :updateTime where t.id = :id")
    public int updateTime(@Param("id") String id, @Param("updateTime") Date updateTime);

   /* @Modifying
    @Query("update XcTask t set t.version= :version+1 where t.id= :id and t.version= :version")
    public int updteTaskVersion(@Param(value = "id") String id, @Param(value = "version") int version);*/
   @Modifying
   @Query("update XcTask t set t.version = :version+1 where t.id = :id and t.version = :version")
   public int updteTaskVersion(@Param(value = "id") String id,@Param(value = "version") int version);
}
