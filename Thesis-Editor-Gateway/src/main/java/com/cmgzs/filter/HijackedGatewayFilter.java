package com.cmgzs.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.domain.MyCachedBodyOutputMessage;
import com.cmgzs.utils.AccessRequestCheck;
import com.cmgzs.utils.FilterUtils;
import com.cmgzs.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.cmgzs.utils.AccessRequestCheck.checkSign;


/**
 * @author huangzhenyu
 * @date 2022/9/22
 */
//@Component
public class HijackedGatewayFilter implements GlobalFilter, Ordered {


    public static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANDiaN46OUQZfLwt19KiYKVX2CzW29kSrwtBVsvgBYuq8h+1fOG2kZPRJGttwJqPJoZVZwvxJ/9vZtqkmtLX6Gq5/neTKUbHOE5RIuZ9zfwKxh66Uod36Q03GEmfvJnS0NgoHTO+mrL7e+TRaJ4eEeeRpTPmE8Xhphbz3vi/WK6VAgMBAAECgYAU6JTWqb1Rs7tomq4fx2ElK8XXtyoKcHRVDBVEEwh7EoFp6yC09zFbOnQKzNGapvmUOLg32cvHJb+F4zQcJsB8v/rxUZG0fFFqe2ZtrHewQl7/XnaAlp+0NRtSoi//52pQMzQEIFpdIVLEhL8wfQhZwYxBvx0EdtWwBYVyOxUjgQJBAPdqSidKhjbClzbHYIKAEO4eJvNrslQJlbbbneMbWrf3MEXkX/YTicDnX0nEoPql6iHmv0ArdoptpsNxTD+YYycCQQDYId0C2sUDHgkFt5Q6yG03iK4ysSwK4spbapLbvyK3zD8VQeg3unWSn4hbyd9QqEp5f+NYiAOjn9HH8R4eDwXjAkALTJZgXv3sKEzhmo9kxlZ/mW7r9QIq5lkpBbSbN5eYCTjyKDDduxyya56lbs5vQ/6CV9hqJNIAFmvkRxtVWC9HAkEAotUIbKEjstCLHZqMe6kK178K9rgSpXTt3eeyEwqyfmTL1hkceffponi8w+KYc20HBvi58LYwf7Ll2swm06Cf3wJBAOTSJVnvCyEQtBx+9nXfv3L4o9R0W9pypPlfRRX9gquKfgBHf1czjN5N0dQskdinHYOoschJoQVafkLGx4LwPN4=";


    //设置一个开关，进行控制是否开启报文签名校验
//    @Value("${gateway.openSign}")
    private Boolean openSign = Boolean.TRUE;

    @Override
    public int getOrder() {
        return -1000;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //如果不开启的话就直接放行
        if (Boolean.FALSE.equals(openSign)) {
            return chain.filter(exchange);
        }
        // 获取请求参数
        ServerHttpRequest request = exchange.getRequest();

        // 获取时间戳
        Long dateTimestamp = AccessRequestCheck.getDateTimestamp(exchange.getRequest().getHeaders());
        // 获取RequestId
        String requestId = AccessRequestCheck.getRequestId(exchange.getRequest().getHeaders());
        // 获取签名
        String sign = AccessRequestCheck.getSign(exchange.getRequest().getHeaders());

        // 修改请求参数,并获取请求参数
        Map<String, Object> paramMap;
        try {
            paramMap = AccessRequestCheck.updateRequestParam(exchange);
        } catch (Exception e) {
            return FilterUtils.invalidUrl(exchange);
        }

        // 获取请求体,修改请求体
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap((String body) -> {
            String encrypt = RSAUtils.decrypt(body, PRIVATE_KEY);
            JSONObject jsonObject = JSON.parseObject(encrypt);
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                paramMap.put(entry.getKey(), entry.getValue());
            }
            checkSign(sign, dateTimestamp, requestId, paramMap);
            return Mono.just(encrypt);
        });


        //创建BodyInserter修改请求体
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();

        headers.putAll(exchange.getRequest().getHeaders());

//        headers.remove(HttpHeaders.CONTENT_LENGTH);

        //创建CachedBodyOutputMessage并且把请求param加入,初始化校验信息
        MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(exchange, headers);

        outputMessage.initial(paramMap, requestId, sign, dateTimestamp);

        return bodyInserter
                .insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            Flux<DataBuffer> body = outputMessage.getBody();
                            if (body.equals(Flux.empty())) {
                                //验证签名
                                checkSign(outputMessage.getSign(), outputMessage.getDateTimestamp(),
                                        outputMessage.getRequestId(), outputMessage.getParamMap());
                            }
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }
}
