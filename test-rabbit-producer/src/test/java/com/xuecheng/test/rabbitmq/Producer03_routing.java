package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author wu on 2020/2/3 0003
 */
public class Producer03_routing {
    //定义队列
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    private static final String ROUTINGKEY_EMAL = "inform_email";
    private static final String ROUTINGKEY_SMS = "inform_sms";

    public static void main(String[] args) {
        //创建工厂连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setVirtualHost("/");


        //创建连接
        Connection connection = null;
        //    创建回话通道
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            //    创建回话通道
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);
            /*
             * 参数说明：
             * 1:交换机名称
             * 2:交换机类型
             * 1 ）：fanout对应工作模式publish /subsricbe
             * 2): direct 对应routing模式
             * 3）：topic对应通配符模式
             * 4）：headers 对应header转发器模式
             * */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);
            //交换机和对列进行绑定
            /*参数说明
             * Queue.BindOk queueBind(String queue, String exchange, String routingKey) throws IOException;
             * queue是对列名称
             * exchange 交换机名称
             * routingKey 是路由key 作用是将消息key发布到指定对列中，在发布订阅模式中调用空串
             * */
            channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAL);
            channel.queueBind(QUEUE_INFORM_SMS, EXCHANGE_ROUTING_INFORM, ROUTINGKEY_SMS);
            for (int i = 0; i < 5; i++) {
                //发送指定消息的rountingkey
                String message = "sent Rounting key message to chenchen";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM, ROUTINGKEY_EMAL, null, message.getBytes());
                System.out.println("sent Ok" + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
