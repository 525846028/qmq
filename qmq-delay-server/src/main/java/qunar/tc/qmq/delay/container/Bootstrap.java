/*
 * Copyright 2018 Qunar, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package qunar.tc.qmq.delay.container;

import qunar.tc.qmq.delay.startup.ServerWrapper;

/**
 * qmq任意时间延迟理解
 * 首先延迟消息会发送到delay服务中
 * delay服务 Dispatcher 首先执行processLog执行数据，就会看看schedule_log下是否有该对应的文件
 * 没有就生产 之后放set里面  如果该消息时间在1小时半以内直接加入时间轮，否则不加入，消息索引写入
 *
 * 还有一个定时器 1分钟执行1次  提取半小时把下一个小时的文件给拉入到内存时间并且添加任务到时间轮里面
 *
 *
 * 添加到hashedwheeltimer里面，如果时间到，执行发送到server实时消息服务
 *
 *
 *
 * 加载一个时间段内的消息是不是需要占用太多的内存
 *
 * 实际上我们并不会将schedule log里完整的消息加载到内存，只会加载索引到内存，
 * 根据前面的介绍，每个索引是16个字节(实际大小可以参照代码，略有出入)。
 * 假设我们使用1G内存加载一个小时索引的话，
 * 则可以装载1G/16B = (1024M * 1024K * 1024B)/(16B) = 67108864 条消息索引。
 * 则每秒qps可以达到18641(67108864 / 60 / 60)。如果我们想每秒达到10万qps，
 * 每个小时一个刻度则需要5493MB，如果觉得内存占用过高，则可以相应的缩小时间段大小，
 * 比如10分钟一个时间段，则10万qps只需要占用915MB内存。通过计算可知这种设计方式还是在合理的范围内的。
 */
public class Bootstrap {
    public static void main(String[] args) {
        System.setProperty("qmq.conf","qmq-dist/conf");
        ServerWrapper wrapper = new ServerWrapper();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            wrapper.destroy();
        }));
        wrapper.start();
    }
}
