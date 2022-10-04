package com.cmgzs.service;

import com.cmgzs.vo.VerifyCodeVo;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
public interface EmailService {
    /**
     * 通过邮箱发送验证码
     *
     * @param to
     * @return
     */
    String sendEmail(String to);

    /**
     * 验证 验证码是否正确
     */
    int verifyCode(String uuid, String code);
}
