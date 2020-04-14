package com.tt.multirabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Desc
 * @Author cmlx
 * @Date 2020-4-13 0013 15:52
 */
@Configuration
public class RabbitMqConfig {

    // 注意这里使用的是primary
    // 声明连接工厂连接开发服务器
    @Primary
    @Bean(name = "chengduConnectionFactory")
    public ConnectionFactory chengduConnectionFactory(@Value("${spring.rabbitmq.chengdu.host}") String host,
                                                      @Value("${spring.rabbitmq.chengdu.port}") int port,
                                                      @Value("${spring.rabbitmq.chengdu.username}") String user,
                                                      @Value("${spring.rabbitmq.chengdu.password}") String password){
        //使用@Value直接读取配置文件中的信息
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        return connectionFactory;
    }


    @Bean(name = "guangzhouConnectionFactory")
    public ConnectionFactory guangzhouConnectionFactory(@Value("${spring.rabbitmq.guangzhou.host}") String host,
                                                      @Value("${spring.rabbitmq.guangzhou.port}") int port,
                                                      @Value("${spring.rabbitmq.guangzhou.username}") String user,
                                                      @Value("${spring.rabbitmq.guangzhou.password}") String password){
        //使用@Value直接读取配置文件中的信息
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);

        return connectionFactory;
    }

    // 声明成都开发服务器rabbitTemplate
    @Primary
    @Bean(name = "chengduRabbitTemplate")
    public RabbitTemplate chengduRabbitTemplate(@Qualifier("chengduConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    // 声明广州开发服务器rabbitTemplate

    @Bean(name = "guangzhouRabbitTemplate")
    public RabbitTemplate guangzhouRabbitTemplate(@Qualifier("guangzhouConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    /**
     * 声明chengduContainerFactory
     *
     * @param simpleRabbitListenerContainerFactoryConfigurer
     * @param connectionFactory
     * @return
     */
    @Bean(name = "chengduContainerFactory")
    public SimpleRabbitListenerContainerFactory chengduSimpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer,
            @Qualifier("chengduConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactoryConfigurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }

    /**
     * 声明guangzhouContainerFactory
     *
     * @param simpleRabbitListenerContainerFactoryConfigurer
     * @param connectionFactory
     * @return
     */
    @Bean(name = "guangzhouContainerFactory")
    public SimpleRabbitListenerContainerFactory guangzhouSimpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer,
            @Qualifier("guangzhouConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactoryConfigurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }

    @Bean(name = "chengduRabitAdmin")
    public RabbitAdmin chengduRabitAdmin(@Qualifier("chengduConnectionFactory") ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        Queue queue = new Queue("chengdu.queue",false,true,true);
        TopicExchange topicExchange = new TopicExchange("chengdu.topic",true,false);
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("chengdu.*");
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }


    // 多ip 的 RabbitMQ需要在代码里面创建交换机和队列，不然会创建到我们加了@Primary注解的地址上去
    // 且持久化的顺序为 exchange、queue、binding
    @Bean(name = "guangzhouRabitAdmin")
    public RabbitAdmin guangzhouRabitAdmin(@Qualifier("guangzhouConnectionFactory") ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        Queue queue = new Queue("guangzhou.queue",false,true,true);
        TopicExchange topicExchange = new TopicExchange("guangzhou.topic",true,false);
        Binding binding = BindingBuilder.bind(queue).to(topicExchange).with("guangzhou.*");
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        return rabbitAdmin;
    }

//    // chengdu
//    @Bean
//    public TopicExchange basicExchange(){
//        return new TopicExchange("chengdu.topic",true,false);
//    }
//
//    @Bean(name = "basicQueue")
//    public Queue basicQueue(){
//        return new Queue("chengdu-queue",false,true,true);
//    }
//
//    @Bean
//    public Binding basicBinding(){
//        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with("chengdu-queue");
//    }
//
//
//    //guangzhou
//    @Bean(name = "guangzhouExchange")
//    public TopicExchange guangzhouExchange(){
//        return new TopicExchange("guangzhou.topic",true,false);
//    }
//
//    @Bean(name = "guangzhouQueue")
//    public Queue guangzhouQueue(){
//        return new Queue("guangzhou-queue",false,true,true);
//    }
//
//    @Bean
//    public Binding guangzhouBinding(){
//        return BindingBuilder.bind(guangzhouQueue()).to(guangzhouExchange()).with("guangzhou-queue");
//    }


}
