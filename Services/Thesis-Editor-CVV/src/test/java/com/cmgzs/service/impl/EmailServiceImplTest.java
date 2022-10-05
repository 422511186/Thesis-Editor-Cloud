package com.cmgzs.service.impl;

import com.cmgzs.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class EmailServiceImplTest {
    @Autowired
    private EmailService emailService;
    @Test
    void sendEmail() {
        String toEmail = "422511186@qq.com";
        String s = emailService.sendEmail(toEmail);
        log.info("s:{}", s);
    }

    @Test
    void verifyCode() {
    }
}