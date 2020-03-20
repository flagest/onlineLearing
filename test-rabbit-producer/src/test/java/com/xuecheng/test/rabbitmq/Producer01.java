package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * rabbit入门程序
 *
 * @author wu on 2020/1/30 0030
 */
public class Producer01 {
    private static final String QUEUE1 = "hell world";

    public static void main(String[] args) {
        //创建工厂的连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");//设置主机IP地址
        connectionFactory.setPort(5672);//设置客户端，端口注意不是web端15672端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机，一个MQ可以有多个虚拟机，一个虚拟机相当于一个MQ
        connectionFactory.setVirtualHost("/");
        Connection connection = null;
        Channel channel = null;
        //创建连接
        try {
            connection = connectionFactory.newConnection();
            //创建回话通道，生产者和MQ都会在这个通道中完成
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE1, true, false, false, null);
            String message = "Hello world 陈星言、";
            channel.basicPublish("", QUEUE1, null, message.getBytes());
            System.out.println("sent " + message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {

            try {
                //关闭通道
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                //关闭连接
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
