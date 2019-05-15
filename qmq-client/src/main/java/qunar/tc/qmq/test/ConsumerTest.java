package qunar.tc.qmq.test;


import qunar.tc.qmq.Message;
import qunar.tc.qmq.PullConsumer;
import qunar.tc.qmq.consumer.MessageConsumerProvider;

import java.util.List;

public class ConsumerTest {
    public static void main(String[] args) {
        //推荐一个应用里只创建一个实例
        MessageConsumerProvider consumer = new MessageConsumerProvider();
        consumer.setAppCode("consumertest");
        consumer.setMetaServer("http://127.0.0.1:8080/meta/address");
        consumer.init();

        PullConsumer pullConsumer = consumer.getOrCreatePullConsumer("test", "group", false);
        List<Message> messages = pullConsumer.pull(100, 1000);
        for (Message message : messages) {

            System.out.println(message);
            //process message

            //对于pull的使用方式，pull到的每一条消息都必须ack，如果处理成功ack的第二个参数为null
            message.ack(1000, null);

            //处理失败，则ack的第二个参数传入Throwable对象
            //message.ack(1000, new Exception("消费失败"));
        }
    }
}