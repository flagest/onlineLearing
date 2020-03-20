package com.xuecheng.test.rabbitmq;



import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wu on 2020/2/3 0003
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer05_topics_springboot {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public String message = "send to anyone message and chenchen";

    //使用rabbitTemplat发送邮件
    @Test
    public void testSentEmail() {
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.email",message);
    }
}
