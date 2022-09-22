package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.UserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangzhenyu
 * @date 2022/9/21
 */
@RestController
@RequestMapping("/archive")
public class Test {

    @GetMapping("/Test1")
    @RequiredToken
    public Object Test1() {
        return UserContext.get();
    }

    @GetMapping("/Test2")
    @RequiredToken(value = false)
    public Object Test2() {
        return UserContext.get();
    }
}
