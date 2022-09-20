package com.cmgzs.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


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
//            if (token == null || "".equals(token) || !isLegal(token)) {
            if (token == null || "".equals(token)) {
                log.info("非法token:{}", token == null ? "null" : token);
                return getResponseError(exchange, "无效token, 请重新登录");
            }
        }

        return chain.filter(exchange);
    }

    /**
     * 返回错误响应
     *
     * @param exchange
     * @param msg
     * @return
     */
    private Mono<Void> getResponseError(ServerWebExchange exchange, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        JSONObject message = new JSONObject();
        message.put("code", 401);
        message.put("message", msg);
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=utf-8");
        return response.writeWith(Mono.just(buffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
