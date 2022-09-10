package com.cmgzs.controller;

import com.cmgzs.utils.request.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangzhenyu
 * @date 2022/9/9
 */
@RestController
public class Test {
    @GetMapping("/test01")
    public String test01() {
        String requestURI = ServletUtils.getRequest().getRequestURI();
        return requestURI + " : Test#test01";
    }
}
