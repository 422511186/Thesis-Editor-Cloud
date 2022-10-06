package com.cmgzs.controller;


import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.auth.User;
import com.cmgzs.domain.auth.params.LoginParams;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.JwtAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredToken(value = false)
public class JwtAuthController extends BaseController {

    @Resource
    private JwtAuthService jwtAuthService;

    /**
     * 登录获取token凭证
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping(value = "/login")
    public ApiResult login(@RequestBody LoginParams param) {
        ApiResult ajax = ApiResult.success();
        Object tokens = jwtAuthService.login(param.getUuid(), param.getCode(), param.getUserName(), param.getPassWord());
        ajax.put("tokens", tokens);
        return ajax;
    }


    /**
     * 注册接口
     *
     * @param param
     * @return
     */
//    @PostMapping(value = "/register")
    public ApiResult register(@RequestBody User param) {
        jwtAuthService.register(param);
        return success();
    }

    /**
     * 解析token所对应的用户信息
     *
     * @return
     */
    @GetMapping(value = "/getUser")
    public ApiResult getUser() {
        UserDetails user = jwtAuthService.getUser();
        return ApiResult.success(user);
    }

    /**
     * 使用refresh_token更换access_token
     *
     * @return
     */
    @GetMapping(value = "/refreshToken")
    public ApiResult refreshToken(@RequestParam("refresh_token") String refresh_token) {
        Object access_token = jwtAuthService.refreshToken(refresh_token);
        return ApiResult.success(access_token);
    }

}
