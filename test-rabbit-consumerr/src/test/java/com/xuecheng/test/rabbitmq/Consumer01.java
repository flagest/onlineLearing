package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 入门程序的消费者
 *
 * @author wu on 2020/2/2 0002
 */
public class Consumer01 {
    private static final String QUEUE1 = "hell world";

    public static void main(String[] args) throws IOException, TimeoutException {
        //建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.01");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机
        connectionFactory.setVirtualHost("/");


        //创建连接
        Connection connection = connectionFactory.newConnection();
//            创建通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE1, true, false, false, null);
        //实现消费方法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            /**
             *@Date:21:16 2020/2/2 0002
             *@Description:
             *@param * @param consumerTag 消费者标签，用来表示消费者的，在监听队列的时候设置   channel.basicConsume
             * @param envelope 信封，可以从信封中获取到很多东西
             * @param properties 消息属性 发送消息可以设置属性
             * @param body
             *@retrun void
             */

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                //获取交换机
                String exchange = envelope.getExchange();
                //消息id，在chanel中表示消息id
                long deliveryTag = envelope.getDeliveryTag();
                String message = new String(body, "UTF-8");

                System.out.println("review:" + message);
            }
        };
        /**
         参数说明
         queue 是队列名称
         autoAck true是自动回复，false需要通过自定义二代码实现
         callback 消费者方法，当消费者接受到消息需要执行方法
         */
        channel.basicConsume(QUEUE1, true, defaultConsumer);


    }
}
