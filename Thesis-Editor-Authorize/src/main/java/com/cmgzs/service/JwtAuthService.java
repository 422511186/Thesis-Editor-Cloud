package com.cmgzs.service;


import com.cmgzs.domain.auth.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAuthService {
    /**
     * 登录
     *
     * @param uuid  验证码uuid
     * @param code  验证码
     * @param username 用户名
     * @param password 密码
     * @return 返回token
     * @throws Exception
     */
    Object login(String uuid, String code, String username, String password);


    /**
     * 注册功能
     *
     * @param param 参数
     */
    void register(User param);

    /**
     * 解析token所对应的用户信息
     * @return
     */
    UserDetails getUser();

    /**
     * 更换access_token
     * @param refresh_token
     * @return
     */
    Object refreshToken(String refresh_token);
}
