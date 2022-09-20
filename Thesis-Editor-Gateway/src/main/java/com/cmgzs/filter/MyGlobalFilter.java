package com.cmgzs.filter;

import com.alibaba.fastjson.JSONObject;
import com.cmgzs.service.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @author huangzhenyu
 * @date 2022/9/9
 */
@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Resource
    private AuthClient authClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();

        log.info("URI ==> {}, address ==> {}, 请求的参数为 ==> {}", request.getURI(), JSONObject.toJSON(request.getRemoteAddress()), JSONObject.toJSON(params));

        String token = request.getHeaders().getFirst("token");
        if (!request.getURI().getPath().startsWith("/authenticate/auth/")) {
            if (token == null || "".equals(token) || !isLegal(token)) {
                log.info("非法token:{}", token == null ? "null" : token);
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                return response.setComplete();
            }
        }

        return chain.filter(exchange);
    }

    private boolean isLegal(String token) {

        Future<Object> isLegal = authClient.isLegal(token);
        Object res = null;
        try {
            res = isLegal.get();
            if (res == null)
                return Boolean.FALSE;
            JSONObject parseObject = JSONObject.parseObject(JSONObject.toJSONString(res));
            if (parseObject.getIntValue("code") == 200) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
