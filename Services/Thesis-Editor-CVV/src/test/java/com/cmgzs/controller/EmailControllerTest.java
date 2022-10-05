package com.cmgzs.controller;

import com.alibaba.fastjson.JSON;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.vo.VerifyCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class EmailControllerTest {
    @Resource
    private EmailController emailController;


    @Test
    void getEmail() {
        String to = "2658096972@qq.com";
        ApiResult apiResult = emailController.getEmail(to);
        log.info("apiResult:{}", JSON.toJSONString(apiResult));
    }

    @Test
    void verifyCode() {
        VerifyCodeVo verifyCodeVo = new VerifyCodeVo();
        verifyCodeVo.setUuid("111");
        verifyCodeVo.setCode("22122");
        ApiResult apiResult = emailController.verifyCode(verifyCodeVo);
        log.info("apiResult:{}", JSON.toJSONString(apiResult));
    }
}