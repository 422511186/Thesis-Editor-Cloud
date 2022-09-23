package com.cmgzs.interceptor;

import com.cmgzs.utils.text.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求时，带上请求头中的token
 *
 * @author huangzhenyu
 * @date 2022/9/23
 */
@Slf4j
public class HeaderInterceptor implements RequestInterceptor {

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