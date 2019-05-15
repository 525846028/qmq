package qunar.tc.qmq.test;


import qunar.tc.qmq.consumer.MessageConsumerProvider;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerTest2 {
    public static void main(String[] args) throws Exception {
        //推荐一个应用里只创建一个实例
        MessageConsumerProvider consumer = new MessageConsumerProvider();
        consumer.setAppCode("consumertest1");
        consumer.setMetaServer("http://127.0.0.1:8080/meta/address");
        consumer.init();

        //ThreadPoolExecutor根据实际业务场景进行配置
        consumer.addListener("test", "group1", (m) -> {
            System.out.println(m);
            //process message
        }, new ThreadPoolExecutor(2, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(100)));
        System.in.read();

    }
}