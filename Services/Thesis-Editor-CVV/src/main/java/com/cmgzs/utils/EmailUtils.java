package com.cmgzs.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Component
public class EmailUtils {
    @Resource
    private JavaMailSender mailSender;
    //邮件发件人
    @Value("${spring.mail.username}")
    private String from;

    public void sendMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw e;
        }

    }

}
