package com.tt.multirabbitmq.controller;

import com.tt.multirabbitmq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Desc
 * @Author cmlx
 * @Date 2020-4-13 0013 18:23
 */
@Controller
@RequestMapping("/demo")
public class TestSenderController {

    @Autowired
    MqSender mqSender;


    @RequestMapping("/testMqSender")
    public void testMqSender() {
        mqSender.send("chengdu.topic", "chengdu-queue", "政审是傻逼");
        return;
    }

    @RequestMapping("/guangzhouMqSender")
    public void guangzhouMqSender() {
        mqSender.sendGuangzhou("chengdu.topic", "chengdu-queue", "广州人ss");
        return;
    }

}
