package com.cmgzs.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/8
 */
@RestController
@RequestMapping("/mq")
public class Send2MqController {
    @Resource
    private AmqpTemplate amqpTemplate;
}
