package com.cmgzs.service.impl;


import com.cmgzs.constant.Constants;
import com.cmgzs.domain.MyUserDetails;
import com.cmgzs.domain.auth.User;
import com.cmgzs.domain.auth.params.LoginParams;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.cvv.params.VerifyCodeParams;
import com.cmgzs.exception.AuthException;
import com.cmgzs.exception.CustomException;
import com.cmgzs.exception.UserNameExistedException;
import com.cmgzs.feign.EmailFeign;
import com.cmgzs.mapper.UserMapper;
import com.cmgzs.service.JwtAuthService;
import com.cmgzs.service.RedisService;
import com.cmgzs.utils.ServletUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import com.cmgzs.utils.id.UUID;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisService redisService;
    @Resource
    private EmailFeign emailFeign;
    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 登录
     *
     * @param email     验证码uuid
     * @param code     验证码
     * @param username 用户名
     * @param password 密码
     * @return 返回token
     * @throws Exception
     */
    public Object login(String email, String code, String username, String password) {

        VerifyCodeParams verifyCodeParams = new VerifyCodeParams();
        verifyCodeParams.setUuid(email);
        verifyCodeParams.setCode(code);
        ApiResult apiResult = emailFeign.verifyCode(verifyCodeParams);

        if (!apiResult.get(ApiResult.CODE_TAG).equals(200)) {
            throw new AuthException(apiResult.get(ApiResult.MSG_TAG).toString(), 500);
        }

        //使用用户名密码进行登录验证
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = null;
        try {
            //认证
            authentication = authenticationManager.authenticate(upToken);
        } catch (AuthenticationException e) {
            throw e;
        }

        MyUserDetails loginUser = (MyUserDetails) authentication.getPrincipal();

        loginUser.getUser().setPassWord(null);

        String access_token = tokenService.createToken(loginUser);

        String refreshToken = UUID.fastUUID().toString();
        redisService.setCacheObject(tokenService.getRefreshToken(refreshToken), loginUser, 2, TimeUnit.DAYS);

        HashMap<String, Object> tokens = new HashMap<>();
        tokens.put("refreshToken", refreshToken);
        tokens.put("access_token", access_token);
        return tokens;
    }

    @Override
    public Object loginBy_email(String email, String code) {
        VerifyCodeParams verifyCodeParams = new VerifyCodeParams();
        verifyCodeParams.setUuid(email);
        verifyCodeParams.setCode(email);
        ApiResult apiResult = emailFeign.verifyCode(verifyCodeParams);

        if (!apiResult.get(ApiResult.CODE_TAG).equals(200))
            throw new AuthException(apiResult.get(ApiResult.MSG_TAG).toString(), 500);

        return null;
    }


    /**
     * 注册功能
     *
     * @param param 用户信息参数
     */
    @Override
    public void register(LoginParams param) {

        VerifyCodeParams verifyCodeParams = new VerifyCodeParams();
        verifyCodeParams.setUuid(param.getEmail());
        verifyCodeParams.setCode(param.getCode());
        ApiResult apiResult = emailFeign.verifyCode(verifyCodeParams);

        if (!apiResult.get(ApiResult.CODE_TAG).equals(200))
            throw new AuthException(apiResult.get(ApiResult.MSG_TAG).toString(), 500);

        User userByUserName = userMapper.getUserByUserName(param.getUserName());
        //如果当前用户名已存在
        if (userByUserName != null)
            throw new UserNameExistedException();

        String pwd = bCryptPasswordEncoder.encode(param.getPassWord());
        User user = new User();
        user.setId(SnowFlakeUtil.getSnowFlakeId().toString());
        user.setPassWord(pwd);
        user.setEmail(param.getEmail());
        userMapper.insertUser(user);
    }

    /**
     * 解析token所对应的用户信息
     *
     * @return
     */
    @Override
    public UserDetails getUser() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader("token");
        if (token == null || "".equals(token))
            throw new CustomException("非法token");
        UserDetails userDetails = tokenService.getLoginUser(request);
        if (userDetails == null)
            throw new AuthException("token 已经失效，请重新登录", Constants.access_token_expire);
        return userDetails;
    }

    /**
     * 更换access_token
     *
     * @param refresh_token
     * @return
     */
    @Override
    public Object refreshToken(String refresh_token) {
        MyUserDetails loginUser = redisService.getCacheObject(tokenService.getRefreshToken(refresh_token));
        if (loginUser == null)
            throw new AuthException("refresh_token 已经过期，请重新登录", Constants.refresh_token_expire);
        /**删除原来的 refresh_token */
        redisService.deleteObject(tokenService.getRefreshToken(refresh_token));
        /*重新获取新的refresh_token*/
        String refreshToken = UUID.fastUUID().toString();
        redisService.setCacheObject(tokenService.getRefreshToken(refreshToken), loginUser, 2, TimeUnit.HOURS);

        String access_token = tokenService.createToken(loginUser);

        HashMap<String, Object> tokens = new HashMap<>();
        tokens.put("refreshToken", refreshToken);
        tokens.put("access_token", access_token);
        return tokens;
    }


}
