package com.cmgzs.controller;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.EmailService;
import com.cmgzs.vo.VerifyCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 发送邮件 验证码
     *
     * @param toEmail
     * @return
     */
    @GetMapping(value = "/sendEmail")
    public ApiResult getEmail(@RequestParam(value = "toEmail") String toEmail) {
        String s = emailService.sendEmail(toEmail);
        if (s == null) return ApiResult.error("发送邮箱失败");
        return ApiResult.success("发送邮箱成功", s);
    }

    /**
     * 验证验证码是否正确
     */
    @GetMapping(value = "/verifyCode")
    public ApiResult verifyCode(VerifyCodeVo verifyCodeVo) {
        String uuid = verifyCodeVo.getUuid();
        String code = verifyCodeVo.getCode();
        if (uuid == null || code == null) return ApiResult.error("参数不合法");
        if ("".equals(uuid) || "".equals(code)) return ApiResult.error("参数不合法");

        if (emailService.verifyCode(uuid, code) == 0) {
            return ApiResult.error("验证码错误");
        }
        return ApiResult.success();
    }

}
