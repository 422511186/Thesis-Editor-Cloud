package com.cmgzs.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.domain.MyCachedBodyOutputMessage;
import com.cmgzs.utils.AccessRequestCheck;
import com.cmgzs.utils.RSAUtils;
import io.netty.buffer.ByteBufAllocator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.cmgzs.filter.HijackedGatewayFilter.PRIVATE_KEY;
import static com.cmgzs.utils.AccessRequestCheck.checkSign;


/**
 * 参数加密传输
 *
 * @author huangzhenyu
 * @date 2022/9/23
 */
@Slf4j
@Component
public class ParamsEncryptionFilter implements GlobalFilter, Ordered {


    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取时间戳
        Long dateTimestamp = AccessRequestCheck.getDateTimestamp(exchange.getRequest().getHeaders());
        // 获取RequestId
        String requestId = AccessRequestCheck.getRequestId(exchange.getRequest().getHeaders());
        // 获取签名
        String sign = AccessRequestCheck.getSign(exchange.getRequest().getHeaders());

        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();

//        String encryptionType = serverHttpRequest.getHeaders().getFirst("encryptionType");
        String encryptionType = "RSA";

        URI uri = serverHttpRequest.getURI();
        MediaType mediaType = serverHttpRequest.getHeaders().getContentType();
        if (encryptionType != null) {
            if (method == HttpMethod.POST || method == HttpMethod.PUT) {
                // 获取请求体,修改请求体
                Map<String, Object> paramMap = new HashMap<>();
                ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

                Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap((String body) -> {
                    String encrypt = RSAUtils.decrypt(body, PRIVATE_KEY);
                    JSONObject jsonObject = JSON.parseObject(encrypt);
                    if (jsonObject != null) {
                        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                            paramMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    checkSign(sign, dateTimestamp, requestId, paramMap);
                    return Mono.just(encrypt);
                });


                //创建BodyInserter修改请求体
                BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(exchange.getRequest().getHeaders());
                headers.remove(HttpHeaders.CONTENT_LENGTH);

                MyCachedBodyOutputMessage outputMessage = new MyCachedBodyOutputMessage(exchange, headers);
                outputMessage.initial(paramMap, requestId, sign, dateTimestamp);
                Mono<Void> voidMono = bodyInserter.insert(outputMessage, new BodyInserterContext())
                        .then(Mono.defer(() -> {
                            ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(serverHttpRequest) {
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

                                @Override
                                public HttpHeaders getHeaders() {
                                    return headers;
                                }
                            };
                            return chain.filter(exchange.mutate().request(decorator).build());
                        }));
                return voidMono;

//                return chain.filter(exchange.mutate().request(serverHttpRequest).build());

            } else if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
                try {
                    MultiValueMap<String, String> requestQueryParams = serverHttpRequest.getQueryParams();
                    log.info("GET OR DELETE ciphertext  parameters： " + requestQueryParams.get("params").get(0));
                    String params = requestQueryParams.get("params").get(0);
                    //解密
                    params = decodeParamsBytype(encryptionType, params);
                    //非法非法加密处理，不进行解密操作
                    if ("-1".equals(params)) {
                        return chain.filter(exchange);
                    }
                    log.info("GET OR DELETE plaintext parameters： " + params);

                    //校验参数是否被篡改
                    Map<String, Object> paramMap = AccessRequestCheck.getParamMap(params);
                    checkSign(sign, dateTimestamp, requestId, paramMap);

                    // 封装URL
                    URI plaintUrl = new URI(uri.getScheme(), uri.getAuthority(),
                            uri.getPath(), params, uri.getFragment());
                    //封装request，传给下一级
                    ServerHttpRequest request = serverHttpRequest.mutate().uri(plaintUrl).build();
                    log.info("get OR delete plaintext request.getQueryParams()： " + request.getQueryParams());
                    return chain.filter(exchange.mutate().request(request).build());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return chain.filter(exchange);

    }

    /**
     * 根据请求方法类型获取密文
     */
    private String getCiphertextByMediaType(String bodyStr, MediaType mediaType) throws UnsupportedEncodingException {

        //json请求
        if (mediaType.equals(MediaType.APPLICATION_JSON)) {
            JSONObject bodyJson = JSONObject.parseObject(bodyStr);
            return bodyJson.getString("params");
        }
        //form请求
        else if (mediaType.equals(MediaType.MULTIPART_FORM_DATA) || mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
            Map<String, String> keyValues = urlSplit(bodyStr);
            return URLDecoder.decode(keyValues.get("params"), "UTF-8");
        } else {
            return "-1";
        }
    }

    /**
     * 解密过滤器必须在所有过滤器之前，否后后续过滤器获取参数会报错
     * 如果有的其他的过滤器添加请调整过滤器顺序
     */
    @Override
    public int getOrder() {
        return -88;
    }

    //根据类型进行解密
    private String decodeParamsBytype(String type, String params) throws Exception {
        if ("BA".equals(type)) {
            //BASE64解密
            return new String(Base64.getDecoder().decode(params));
        }
//        else if ("AE".equals(type)) {
//            //AES128解密
//            return AESUtil.aesDecrypt(params);
//        }
//        else if ("SM".equals(type)) {
//            //SM4解密
//            return Sm4Util.decryptEcb(params);
//        }
        else if ("RSA".equals(type)) {
            return RSAUtils.decrypt(params, PRIVATE_KEY);
        } else {
            //非法解密
            return "-1";
        }
    }

    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();

        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    @SneakyThrows
    private String resolveBodyFromRequest(ServerWebExchange exchange) {
        // 获取请求体,修改请求体
        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());

        Mono<String> modifiedBody = serverRequest
                .bodyToMono(String.class)
                .flatMap((String body) -> {
                    String encrypt = RSAUtils.decrypt(body, PRIVATE_KEY);
                    JSONObject jsonObject = JSON.parseObject(encrypt);
                    Map<String, Object> paramMap = AccessRequestCheck.getParamMap(encrypt);
                    for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        paramMap.put(entry.getKey(), entry.getValue());
                    }
                    return Mono.just(encrypt);
                });
        String block = modifiedBody.toFuture().get();
        return block;
    }


    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param params url地址
     * @return url请求参数部分
     */
    private static Map<String, String> urlSplit(String params) {
        Map<String, String> mapRequest = new HashMap<>();
        String[] arrSplit = null;
        if (params == null) {
            return mapRequest;
        }
        arrSplit = params.split("&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("=");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else if (arrSplitEqual[0] != "") {
                //只有参数没有值，不加入
                mapRequest.put(arrSplitEqual[0], "");
            }
        }
        return mapRequest;
    }

}