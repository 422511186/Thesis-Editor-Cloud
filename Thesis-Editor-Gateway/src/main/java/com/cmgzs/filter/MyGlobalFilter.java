package com.cmgzs.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author huangzhenyu
 * @date 2022/9/9
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();

        log.info("URI ==> {}, address ==> {}, 请求的参数为 ==> {}", request.getURI(), JSONObject.toJSON(request.getRemoteAddress()), JSONObject.toJSON(params));
        
        String token = request.getHeaders().getFirst("token");
        if (!request.getURI().getPath().startsWith("/authenticate/auth/")) {
            if (token == null || "".equals(token)) {
                log.info("非法用户token:{}", token == null ? "null" : token);
                exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
