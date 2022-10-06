package com.cmgzs.filter;

import com.cmgzs.utils.ApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 简单验证请求是否携带token，以及token的合理性
 *
 * @author huangzhenyu
 * @date 2022/9/9
 */
@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -100;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("token");
        /**
         * 判断是否要进入认证服务或者验证码服务
         */
        if (!request.getURI().getPath().startsWith("/authenticate/auth/") && !request.getURI().getPath().startsWith("/CVV/")) {
            if (token == null || "".equals(token)) {
                return ApiUtil.getResponseError(exchange, "token不能为空");
            }
        }
        return chain.filter(exchange);
    }


}
