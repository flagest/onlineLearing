package com.xuecheng.order.mq;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * SpringTask串行化，（在不加com.xuecheng.order.config.AsyncTaskConfig这个类的是时候是串行化，加了就变成了并行化了）
 *
 * @author wu on 2020/3/14 0014
 */
@Component
public class ChooseCourseTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);

    @Autowired
    TaskService taskService;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Scheduled(cron = "0 0/1 * * * *")//每隔一分钟去执行一次
    public void sendSechledTask() {
        //得到一分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);
        Date time = calendar.getTime();
        List<XcTask> byUpdateTime = taskService.findByUpdateTime(time, 100);
        System.out.println(byUpdateTime);
        for (XcTask xcTask : byUpdateTime) {
            if (taskService.getTask(xcTask.getId(), xcTask.getVersion()) > 0) {
                String ex = xcTask.getMqExchange();//获取交换机
                String routingkey = xcTask.getMqRoutingkey();//获取交换机的routingkey
                taskService.publish(xcTask, ex, routingkey);
            }
        }
    }

    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE)
    public void receiveFinishChoosecourseTask(XcTask xcTask){
        if(xcTask!=null && StringUtils.isNotEmpty(xcTask.getId())){
            taskService.finishTask(xcTask.getId());
        }
    }


    //    @Scheduled(cron = "0/3 * * * * *")//每隔3秒执行
    public void task() {
        LOGGER.info("=======任务调度1开始==================");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("========任务调度1结束===================");
    }

    //    @Scheduled(cron = "0/3 * * * * *")//每隔3秒执行
    public void task1() {
        LOGGER.info("=======任务调度2开始==================");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("========任务调度2结束===================");
    }
}
