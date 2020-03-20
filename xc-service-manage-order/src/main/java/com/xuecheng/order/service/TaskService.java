package com.xuecheng.order.service;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author wu on 2020/3/15 0015
 */
@Service
public class TaskService {
    @Autowired
    XcTaskRepository xcTaskRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;//注入Rabbit的Bean
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;
    public List<XcTask> findByUpdateTime(Date updateTime, int szie) {
        //设置分页参数
        PageRequest pageRequest = new PageRequest(0, szie);
        Page<XcTask> byUpdateTimeBefore = xcTaskRepository.findByUpdateTimeBefore(pageRequest, updateTime);
        return byUpdateTimeBefore.getContent();
    }

    //发布消息
    public void publish(XcTask task, String ex, String routingKey) {
        Optional<XcTask> optional = xcTaskRepository.findById(task.getId());
        if (optional.isPresent()) {
            rabbitTemplate.convertAndSend(ex, routingKey, task);
            XcTask xcTask = optional.get();
            //设置更新时间
            xcTask.setUpdateTime(new Date());
            xcTaskRepository.save(xcTask);
        }
    }

    //获取任务
    @Transactional
    public int getTask(String id, int version) {
        //通过乐观锁表示数据库更新，如果count>0表示可以更新
        int count = xcTaskRepository.updteTaskVersion(id, version);
        return count;
    }
    //完成任务
    @Transactional
    public void finishTask(String taskId){
        Optional<XcTask> optionalXcTask = xcTaskRepository.findById(taskId);
        if(optionalXcTask.isPresent()){
            //当前任务
            XcTask xcTask = optionalXcTask.get();
            //历史任务
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
            xcTaskRepository.delete(xcTask);
        }
    }

}
