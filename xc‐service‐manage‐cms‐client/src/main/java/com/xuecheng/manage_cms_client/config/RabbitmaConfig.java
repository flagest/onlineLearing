package com.xuecheng.manage_cms_client.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wu on 2020/2/6 0006
 */
@Configuration
public class RabbitmaConfig {
    //队列Bean的名称
    public static final String QUEUE_CMS_POSTPAGE = "queue_cms_postpage";
    //交换机的名称
    public static final String EX_ROUTING_CMS_POSTPAGE = "ex_routing_cms_postpage";
    //队列名称
    @Value("${xuecheng.mq.queue}")
    public String queue_cms_postpage_name;
    //routingkey 即站点名
    @Value("${xuecheng.mq.routingKey}")
    public String routingKey;

    /*
     * 交换机配置使用direct类型
     * */
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange EX_ROUTING_CMS_POSTPAGE() {
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }

    /*声明队列
     * */
    @Bean(QUEUE_CMS_POSTPAGE)
    public Queue QUEUE_CMS_POSTPAGE() {
        Queue queue = new Queue(queue_cms_postpage_name);
        return queue;
    }

    /**
     * @param *        @param queue
     * @param exchange
     * @Date:18:40 2020/2/6 0006
     * @Description:将交换机，队列，routingKey绑定在一起
     * @retrun org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding BINDING_QUEUE_INFROM_SMS(@Qualifier(QUEUE_CMS_POSTPAGE) Queue queue,
                                            @Qualifier(EX_ROUTING_CMS_POSTPAGE) Exchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();

    }
}
