package com.cmgzs.service;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
public interface EmailService {
    /**
     * 通过邮箱发送验证码
     *
     * @param receive
     * @return
     */
    void sendEmail(String receive);

    /**
     * 验证 验证码是否正确
     */
    int verifyCode(String uuid, String code);
}
