package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.auth.User;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/15
 */

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
    @RequiredToken
    @RequestMapping(method = RequestMethod.PUT)
    public ApiResult updateUserInfo(@RequestBody User user) {

        int i = userInfoService.updateUserInfo(user);
        if (i == 0) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }

    /**
     * 通过用户Id获取昵称
     *
     * @param userIds
     * @return
     */
    @RequiredToken(value = false)
    @RequestMapping(value = "/getNickNames", method = RequestMethod.GET)
    public ApiResult getNickNames(@RequestParam String[] userIds) {
        return ApiResult.success(userInfoService.getNickNames(userIds));
    }

}
