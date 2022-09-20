package com.cmgzs.service;


import com.cmgzs.domain.auth.User;

public interface JwtAuthService {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回token
     * @throws Exception
     */
    String login(String username, String password);


    /**
     * 注册功能
     *
     * @param param 参数
     */
    void register(User param);

    /**
     * 修改密码
     *
     * @param param 参数
     */
    void updatePWD(User param);

    int isLegal(String token);
}
