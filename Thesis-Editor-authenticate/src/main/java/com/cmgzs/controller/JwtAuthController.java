package com.cmgzs.controller;


import com.cmgzs.constant.Constants;
import com.cmgzs.domain.User;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.JwtAuthService;
import com.cmgzs.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/auth")
public class JwtAuthController extends BaseController {

    @Resource
    private JwtAuthService jwtAuthServiceImpl;

    /**
     * 登录接口
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping(value = "/login")
    public ApiResult login(@Validated @RequestBody User param) {
        ApiResult ajax = ApiResult.success();
        String token = jwtAuthServiceImpl.login(param.getUserName(), param.getPassWord());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    /**
     * 注册接口
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/register")
    public ApiResult register(@Validated @RequestBody User param) {
        jwtAuthServiceImpl.register(param);
        return success();
    }

    /**
     * 修改密码
     *
     * @param param 入参数
     * @return 结果
     */
    @PostMapping(value = "/updatePWD")
    public ApiResult updatePWD(@Validated @RequestBody User param) {
        String newPassWord = param.getParams().get("newPassWord").toString();
        if (newPassWord != null) {
            newPassWord = newPassWord.trim();
            if (newPassWord.length() < 16 && newPassWord.length() > 6)
                jwtAuthServiceImpl.updatePWD(param);
            return success();
        }
        return error();
    }

    @GetMapping("/getPermissions")
    public Object getPermissions() {
        List<GrantedAuthority> permissions = SecurityUtils.getUser().getPermissions();
        return ApiResult.success(permissions.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

}
