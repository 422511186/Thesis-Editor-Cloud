package com.cmgzs.controller;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.cvv.params.VerifyCodeParams;
import com.cmgzs.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
@RestController
@RequestMapping("/CVV/email")
public class EmailController {

    @Resource
    private EmailService emailService;
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 发送邮件 验证码
     *
     * @param toEmail
     * @return
     */
    @GetMapping(value = "/sendEmail")
    public ApiResult getEmail(@RequestParam(value = "toEmail") String toEmail) {
        //通过mq发送验证码，异步提速
        amqpTemplate.convertAndSend("emailExchange", "email", toEmail);
        return ApiResult.success("发送邮箱成功");
    }

    /**
     * 验证验证码是否正确
     */
    @GetMapping(value = "/verifyCode")
    public ApiResult verifyCode(VerifyCodeParams verifyCodeParams) {
        String uuid = verifyCodeParams.getUuid();
        String code = verifyCodeParams.getCode();
        if (uuid == null || code == null) return ApiResult.error("参数不合法");
        if ("".equals(uuid) || "".equals(code)) return ApiResult.error("参数不合法");

        if (emailService.verifyCode(uuid, code) == 0) {
            return ApiResult.error("验证码错误");
        }
        return ApiResult.success();
    }

}
