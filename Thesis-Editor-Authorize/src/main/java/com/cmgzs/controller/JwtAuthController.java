package com.cmgzs.controller;


import com.cmgzs.domain.auth.params.LoginParams;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.JwtAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/auth")
public class JwtAuthController extends BaseController {

    @Resource
    private JwtAuthService jwtAuthService;

    /**
     * 通过账号密码登录
     * 登录获取token凭证
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping(value = "/login")
    public ApiResult login(@RequestBody @Validated LoginParams param) {
        ApiResult ajax = ApiResult.success();
        Object tokens = jwtAuthService.login(param.getEmail(), param.getCode(), param.getUserName(), param.getPassWord());
        ajax.put("tokens", tokens);
        return ajax;
    }


    /**
     * 通过邮箱登录
     * 登录获取token凭证
     *
     * @param param 参数
     * @return 结果
     */
//    @PostMapping(value = "/login")
    public ApiResult loginBy_email(@RequestBody  LoginParams param) {
        ApiResult ajax = ApiResult.success();

        return ajax;
    }

    /**
     * 注册接口
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/register")
    public ApiResult register(@RequestBody @Validated LoginParams param) {
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
