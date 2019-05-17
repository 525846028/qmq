package qunar.tc.qmq.test;


import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageSendStateListener;
import qunar.tc.qmq.producer.MessageProducerProvider;

import java.util.concurrent.TimeUnit;

public class ProducerTest {
    public static void main(String[] args) {

        MessageProducerProvider producer = new MessageProducerProvider();
        producer.setAppCode("producerTest");
        producer.setMetaServer("http://127.0.0.1:8080/meta/address");
        producer.init();

        //每次发消息之前请使用generateMessage生成一个Message对象，然后填充数据
        for (int i = 0; i < 2; i++) {

            Message message = producer.generateMessage("test");
            message.setProperty("keytsdfs" + i, "valuea:");
            //发送延迟消息
            message.setDelayTime(2, TimeUnit.SECONDS);
            producer.sendMessage(message, new MessageSendStateListener() {
                @Override
                public void onSuccess(Message message) {
                    System.out.println(message);
                }

                @Override
                public void onFailed(Message message) {
                    System.out.println("error" + message);
                }
            });
        }
        System.out.println("发送成功！");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}