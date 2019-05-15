package qunar.tc.qmq.test;


import qunar.tc.qmq.Message;
import qunar.tc.qmq.producer.MessageProducerProvider;

import java.util.concurrent.TimeUnit;

public class ProducerTest {
    public static void main(String[] args) {

        MessageProducerProvider producer = new MessageProducerProvider();
        producer.setAppCode("test");
        producer.setMetaServer("http://127.0.0.1:8080/meta/address");
        producer.init();

//每次发消息之前请使用generateMessage生成一个Message对象，然后填充数据
        Message message = producer.generateMessage("test");
        message.setProperty("keyt", "valuea");
//发送延迟消息
        message.setDelayTime(15, TimeUnit.DAYS);
        producer.sendMessage(message);
        System.out.println("发送成功！");
    }
}