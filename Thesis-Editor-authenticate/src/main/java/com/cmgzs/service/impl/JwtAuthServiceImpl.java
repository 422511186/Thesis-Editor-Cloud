package com.cmgzs.service.impl;


import com.cmgzs.domain.MyUserDetails;
import com.cmgzs.domain.auth.User;
import com.cmgzs.exception.CustomException;
import com.cmgzs.exception.UserNameExistedException;
import com.cmgzs.mapper.UserMapper;
import com.cmgzs.service.JwtAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
//@Transactional(rollbackFor = Exception.class)//开启事务
public class JwtAuthServiceImpl implements JwtAuthService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenService tokenService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 返回token
     * @throws Exception
     */
    public String login(String username, String password) {

        //使用用户名密码进行登录验证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = null;
        try {
            //认证
            authentication = authenticationManager.authenticate(upToken);
        } catch (AuthenticationException e) {
            throw new CustomException("账号或密码错误");
        }

        // 生成token
        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();
        return tokenService.createToken(loginUser);
    }


    /**
     * 注册功能
     *
     * @param param 用户信息参数
     */
    @Override
    public void register(User param) {

        User userByUserName = userMapper.getUserByUserName(param.getUserName());
        //如果当前用户名已存在
        if (userByUserName != null)
            throw new UserNameExistedException();
        String pwd = new BCryptPasswordEncoder().encode(param.getPassWord());
        param.setPassWord(pwd);
        userMapper.insertUser(param);

    }

    /**
     * 修改密码
     *
     * @param param
     */
    @Override
    public void updatePWD(User param) {
        if (param.getPassWord().equals(param.getNewPassWord())) {
            throw new CustomException("新密码与旧密码相同");
        }

        User user = userMapper.getUserByUserName(param.getUserName());
        if (!bCryptPasswordEncoder.matches(param.getPassWord(), user.getPassWord())) {
            throw new CustomException("旧密码错误");
        }

        String newPassWord = bCryptPasswordEncoder.encode(param.getNewPassWord());
        param.setNewPassWord(newPassWord);

        int i = userMapper.updateUser(param);

        if (i == 0)
            throw new CustomException("修改密码失败");
    }

    @Override
    public int isLegal(String token) {
        int legal = tokenService.isLegal(token);
        return legal;
    }


}
