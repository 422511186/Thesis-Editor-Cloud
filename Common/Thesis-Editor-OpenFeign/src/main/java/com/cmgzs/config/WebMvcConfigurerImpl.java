package com.cmgzs.config;

import com.cmgzs.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author huangzhenyu
 * @date 2022/9/21
 */
@Configuration
public class WebMvcConfigurerImpl implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).
                addPathPatterns("/**");
    }
}