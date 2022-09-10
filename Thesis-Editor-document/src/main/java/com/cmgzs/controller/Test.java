package com.cmgzs.controller;

import com.alibaba.fastjson.JSONObject;
import com.cmgzs.utils.AuthUtils;
import com.cmgzs.utils.request.ServletUtils;
import com.cmgzs.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/9/10
 */
@RestController
@RequestMapping("/document")
public class Test {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AuthUtils authUtils;

    @GetMapping("/getUserInfo")
    public Object getUserInfo() {
        System.out.println("authUtils.getUserName() = " + authUtils.getUserName());
        System.out.println("authUtils.getUser() = " + authUtils.getUser());
        return authUtils.getAllInfo();
    }
}
