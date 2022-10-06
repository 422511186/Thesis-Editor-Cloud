package com.cmgzs.feign;

import com.cmgzs.constant.serverConstants;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.cvv.params.VerifyCodeParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Component
@FeignClient(value = serverConstants.thesis_editor_cvv, path = "/CVV/email")
public interface EmailFeign {


    /**
     * 发送邮件 验证码
     *
     * @param toEmail
     * @return
     */
    @GetMapping(value = "/sendEmail")
    ApiResult getEmail(@RequestParam(value = "toEmail") String toEmail);

    /**
     * 验证验证码是否正确
     */
    @GetMapping(value = "/verifyCode")
    ApiResult verifyCode(@SpringQueryMap VerifyCodeParams verifyCodeParams);

}
