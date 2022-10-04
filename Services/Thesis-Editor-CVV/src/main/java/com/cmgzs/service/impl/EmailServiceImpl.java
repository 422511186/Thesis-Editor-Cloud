package com.cmgzs.service.impl;

import com.cmgzs.constant.RedisConstant;
import com.cmgzs.service.EmailService;
import com.cmgzs.service.RedisService;
import com.cmgzs.utils.EmailUtils;
import com.cmgzs.utils.ValidateCodeUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
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
     * @param to
     * @return
     */
    @Override
    public String sendEmail(String to) {
        String uuid = SnowFlakeUtil.getSnowFlakeId().toString();
        String code4String = ValidateCodeUtils.generateValidateCode4String(6);

        log.info("收件人:{}, 验证码:{}, uuid:{}", to, code4String, uuid);

        try {
            redisService.setCacheObject(RedisConstant.email_prefix + uuid, code4String, 5, TimeUnit.MINUTES);
            emailUtils.sendMessage(to, "service", "验证码为: " + code4String + ",有效期为5分钟");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        redisService.setCacheObject(RedisConstant.email_prefix + uuid, code4String, 5, TimeUnit.MINUTES);
        return uuid;
    }

    /**
     * 验证 验证码是否正确
     */
    @Override
    public int verifyCode(String uuid, String code) {
        String cacheCode = redisService.getCacheObject(RedisConstant.email_prefix + uuid);
        if (cacheCode == null || !cacheCode.equals(code)) return 0;
        redisService.deleteObject(RedisConstant.email_prefix + uuid);
        return 1;
    }
}
