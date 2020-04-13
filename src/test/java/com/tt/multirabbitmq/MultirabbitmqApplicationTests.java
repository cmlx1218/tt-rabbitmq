package com.tt.multirabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultirabbitmqApplication.class)
public class MultirabbitmqApplicationTests {

    @Autowired
    MqSender mqSender;

    @Test
    public void setSender(){
        mqSender.send("chengdu.topic","chengdu-queue","政审是傻逼");

    }
}
