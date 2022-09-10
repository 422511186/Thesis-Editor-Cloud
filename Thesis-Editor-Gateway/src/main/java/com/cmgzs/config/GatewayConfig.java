package com.cmgzs.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author huangzhenyu
 * @date 2022/9/9
 */
@Configuration
public class GatewayConfig {
    /**
     * ip地址限流
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
            }
        };
    }

    /**
     * /abc
     * /category
     * 基于请求路径的限流
     */
//    @Bean
//    public KeyResolver pathKeyResolver() {
//        return new KeyResolver() {
//            @Override
//            public Mono<String> resolve(ServerWebExchange exchange) {
//                return Mono.just(exchange.getRequest().getPath().toString());
//            }
//        };
//    }

    /**
     * 基于请求参数的限流
     *
     * http://localhost:8001/admin/category/demo.do/1
     * 当请求的链接未带参数时候会报错500
     */
//    @Bean
//    public KeyResolver paramResolver() {
//        return new KeyResolver() {
//            @Override
//            public Mono<String> resolve(ServerWebExchange exchange) {
//                return Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//            }
//        };
//    }

    /**
     * 基于请求ip地址的限流
     * 暂时未能使用
     */

    /**
     * 基于用户的限流
     */
   /* @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getQueryParams().getFirst("user")
        );
    }*/
}
