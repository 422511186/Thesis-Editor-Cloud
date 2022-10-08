package com.cmgzs.service.impl;

import com.cmgzs.constant.RedisConstant;
import com.cmgzs.service.EmailService;
import com.cmgzs.service.RedisService;
import com.cmgzs.utils.EmailUtils;
import com.cmgzs.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private EmailUtils emailUtils;
    @Resource
    private RedisService redisService;

    /**
     * 通过邮箱发送验证码
     *
     * @param receive
     * @return
     */
    @Override
    public void sendEmail(String receive) {
        //生成邮箱验证码
        String code4String = ValidateCodeUtils.generateValidateCode4String(6);
        log.info("收件人:{}, 验证码:{}", receive, code4String);
        //发送邮箱验证码
        emailUtils.sendMessage(receive, "service", "验证码为: " + code4String + ",有效期为5分钟");
        //设置缓存
        redisService.setCacheObject(RedisConstant.EMAIL_PREFIX + receive, code4String, 5, TimeUnit.MINUTES);
    }

    /**
     * 验证 验证码是否正确
     */
    @Override
    public int verifyCode(String uuid, String code) {
        String cacheCode = redisService.getCacheObject(RedisConstant.EMAIL_PREFIX + uuid);
        if (cacheCode == null || !cacheCode.equals(code)) return 0;
        redisService.deleteObject(RedisConstant.EMAIL_PREFIX + uuid);
        return 1;
    }
}
