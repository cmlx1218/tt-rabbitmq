package com.tt.multirabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Desc
 * @Author cmlx
 * @Date 2020-4-13 0013 15:51
 */
@Component
public class MqSender {

    @Resource(name = "chengduRabbitTemplate")
    AmqpTemplate chengduRabbitTemplate;

    @Resource(name = "guangzhouRabbitTemplate")
    AmqpTemplate guangzhouRabbitTemplate;

    public void send(String queue,String context){
        this.chengduRabbitTemplate.convertAndSend(queue,context);
    }

    public void send(String exchange,String queue,String context){
        this.chengduRabbitTemplate.convertAndSend(exchange,queue,context);
    }

    public void send(String routingKey, String context, Long delayTime) {
        chengduRabbitTemplate.convertAndSend(routingKey, context, message -> {
            message.getMessageProperties().setHeader("x-delay", delayTime);
            return message;
        });
    }


    public void sendGuangzhou(String queue,String context){
        this.guangzhouRabbitTemplate.convertAndSend(queue,context);
    }

    public void sendGuangzhou(String exchange,String queue,String context){
        this.guangzhouRabbitTemplate.convertAndSend(exchange,queue,context);
    }

    public void sendGuangzhou(String routingKey, String context, Long delayTime) {
        guangzhouRabbitTemplate.convertAndSend(routingKey, context, message -> {
            message.getMessageProperties().setHeader("x-delay", delayTime);
            return message;
        });
    }




}
