package com.tt.multirabbitmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author cmlx
 * @Date 2020-4-13 0013 16:55
 */
@Slf4j
@Component
public class GuangzhouListener {

    @RabbitHandler
    @RabbitListener(queues = "guangzhou-queue",containerFactory = "guangzhouContainerFactory")
    public void chengduSender(@Payload String body) {
        System.out.println(body);
    }

}
