package com.rabiitmq.demo.helloworld;

import com.rabbitmq.client.*;
import com.rabiitmq.demo.ConnectionUtil;

import java.io.IOException;

/**
 * 消费者从队列中获取消息
 */
public class Recv {

    private final static String QUEUE_NAME = "q_test_01";

    public static void main(String[] args) throws IOException, InterruptedException {

        //获取到连接以及MQ通道
        Connection connection = ConnectionUtil.getConnection();
        //从连接中创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //----------new api-----------------------------
        //定义消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            //获取到发送的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("new api recv : "+msg);

            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);


        //----------old api----------------------------

        /*//定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME, true, consumer);
        //获取消息
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }*/


    }
}
