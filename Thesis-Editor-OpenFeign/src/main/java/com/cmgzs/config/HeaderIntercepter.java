package com.cmgzs.config;

import com.cmgzs.utils.text.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class HeaderIntercepter implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String token = attributes.getRequest().getHeader("token");
            if (!StringUtils.isEmpty(token)) {
                log.info("feignClient requestHeader  add token:{}", token);
                requestTemplate.header("token", token);
            }
        }
    }
}