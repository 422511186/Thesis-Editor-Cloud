package com.cmgzs.handler;

import com.alibaba.fastjson.JSON;
import com.cmgzs.constant.HttpStatus;
import com.cmgzs.domain.MyUserDetails;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.impl.TokenService;
import com.cmgzs.utils.ServletUtils;
import com.cmgzs.utils.text.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author hzy
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        MyUserDetails loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
       ServletUtils.renderString(response, JSON.toJSONString(ApiResult.error(HttpStatus.SUCCESS, "退出成功")));
    }
}
