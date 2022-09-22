package com.cmgzs.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.UserContext;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.exception.CustomException;
import com.cmgzs.feign.JwtAuthController;
import com.cmgzs.utils.SpringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口Token验证的拦截类
 *
 * @author hzy
 * @date 2022/9/21
 */
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private volatile JwtAuthController jwtAuthController;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查方法上是否有RequiredToken注解
        if (method.isAnnotationPresent(RequiredToken.class)) {
            // 若方法上有，则判断设置的是否false
            RequiredToken requiredToken = method.getAnnotation(RequiredToken.class);
            if (!requiredToken.value()) {
                // 若设置的required = false 则跳过验证
                return true;
            }

        } else if (handlerMethod.getBeanType().isAnnotationPresent(RequiredToken.class)) {
            // 若方法上没有注解则判断该方法的类上是否有RequiredToken注解
            // 若类上有，则判断设置的是否false
            RequiredToken requiredToken = handlerMethod.getBeanType().getAnnotation(RequiredToken.class);
            if (!requiredToken.value()) {
                // 若设置的required = false 则跳过验证
                return true;
            }

        }
        /*注入依赖*/
        addJwtAuthController();

        //设置用户信息
        ApiResult result = jwtAuthController.getUser();
        JSONObject resJson = JSONObject.parseObject(JSONObject.toJSONString(result));
        if (resJson.getIntValue("code") != 200) {
            throw new CustomException(resJson.getString("message"));
        }
        JSONObject data = resJson.getJSONObject("data");
        UserContext.setUserName(data.getJSONObject("user").getString("userName"));
        UserContext.setUserId(data.getJSONObject("user").getString("id"));
        UserContext.setToken(httpServletRequest.getHeader("token"));
        return true;
    }

    /**
     * 同步注入依赖
     */
    public void addJwtAuthController() {
        if (jwtAuthController == null) {
            synchronized (AuthenticationInterceptor.class) {
                if (jwtAuthController == null)
                    jwtAuthController = SpringUtils.getBean(JwtAuthController.class);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // ThreadLocal值清除，防止内存泄漏
        UserContext.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}