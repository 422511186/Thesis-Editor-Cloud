package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.auth.User;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.UserInfoService;
import com.mysql.cj.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/15
 */
@RequiredToken
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 用户信息更新
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ApiResult updateUserInfo(@RequestBody User user) {

        int i = userInfoService.updateUserInfo(user);
        if (i == 0) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }
}
